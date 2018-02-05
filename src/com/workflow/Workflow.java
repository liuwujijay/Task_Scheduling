package com.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.util.Utility;

public class Workflow extends FlowResourceManager {
	public static Job[] jobs;
	public static int[][] dependencies;
	public static double[] exe_time;
	public static Workload_and_data wd;
	public static double ED;
	public static int[] critical_modules;
	
	static double init_datasize = 10d;
	static double init_workload = 100d;
	static double split_size = 5;

	public enum Workload {

		N1("n", 1), N2("n^2", 2), N3("n^3", 3), LOGN("LOGN", 4), NLOGN("NLOGN", 5);

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		private String name;
		private int index;

		private Workload(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static String getName(int index) {
			for (Workload wl : Workload.values()) {
				if (wl.getIndex() == index) {
					return wl.name;
				}
			}
			return null;
		}
	}
	private double first_mdata;
	private double first_rdata;
	private double first_rworkload;
	private double first_mworkload;

	// How we generate a DAG
	// 1.lay out all modules sequentially along a pipeline
	// 2.for each module (except the first and the last one),
	// we randomly assigning them a proceeding module and a succeeding module.
	// 3.randomly pick up two modules from the pipeline and add a directed edge
	// between them.
	// Always keep in mind that the first module only has succeeding modules.
	// and the last module module only has proceeding modules.
	public void initialize_workFlow(int num_of_modules,int num_of_edges){
		create_DAG(num_of_modules, num_of_edges);
		assignJobs(dependencies);
		estimate_workload_and_data_size(first_mdata, first_mworkload, first_rdata, first_rworkload);
		calculate_critical_path(calculateLink_delay(), exe_time, dependencies);
	}
	
	public void create_DAG(int num_of_modules, int num_of_edges) {
		if (num_of_modules - 1 > num_of_edges)
			return;
		int[][] dependencies = new int[num_of_modules][num_of_modules];

		// 1st and 2nd steps: To make it easier, we just link them sequentially.
		for (int i = 0; i < num_of_modules - 1; i++) {
			dependencies[i][i + 1] = 1;
		}

		// 3rd STEP
		int current_links = num_of_modules - 1;
		while (current_links < num_of_edges) {
			Random random = new Random();
			int temp1 = random.nextInt(num_of_modules - 1);
			int temp2 = random.nextInt(num_of_modules - temp1 - 1) + temp1 + 1;
			if ((temp1 == temp2) || dependencies[temp1][temp2] == 1)
				continue;
			dependencies[temp1][temp2] = 1;
			current_links = current_links + 1;
		}

		Workflow.dependencies = dependencies;
	}

	public void assignJobs(int[][] dependencies) {
		Job job = new Job();
		JobDAO dao = new JobDAO();
		int first_module = 0;
		for (int i = 0; i < dependencies.length; i++) {
			double add = 0;
			for (int j = 0; j < dependencies.length; j++) {
				add = dependencies[j][i] + add;
			}
			if (add == 0) {
				first_module = i;
				break;
			}
		}
		Workflow.jobs[first_module] = new Job();
		Workflow.jobs[first_module].mapDatasizeResult = dao.getFunction();
		Workflow.jobs[first_module].reduceDatasizeResult = dao.getFunction();
		Workflow.jobs[first_module].reduceWorkloadResult = dao.getFunction();
		Workflow.jobs[first_module].mapWorkloadResult = dao.getFunction();

		for (int i = 0; i < dependencies.length; i++) {
			if (i == first_module) {
				continue;
			}
			job.mapDatasizeResult = dao.calculateMapDatasize(i, dependencies);
			job.reduceDatasizeResult = dao.calculateReduceDatasize(i, dependencies);
			job.reduceWorkloadResult = dao.calculateReduceWorkload(i, dependencies);
			job.mapWorkloadResult = dao.calculateMapWorkload(i, dependencies);

			ArrayList<Integer> temp = new ArrayList<>();
			for (int k = 0; k < dependencies[i].length; k++) {
				if (dependencies[k][i] != 0) {
					temp.add(k);
				}
			}
			int[] result = new int[temp.size()];
			for (int m = 0; m < temp.size(); m++) {
				result[m] = (int) temp.get(m);
			}
			job.datasizeToWorkResult = dao.datasizeToWorkload(result);
			Workflow.jobs[i] = job;
		}
	}

	public void estimate_workload_and_data_size(double first_mdata, double first_mworkload,
			double first_rdata, double first_rworkload) {
		String[][] map_datasize = new String[FlowResourceManager.nodeSum][FlowResourceManager.nodeSum];
		int first_module = 0;
		int[] visited = new int[FlowResourceManager.nodeSum];
		double[][] workload = new double[FlowResourceManager.nodeSum][2];
		double[][] datasize = new double[FlowResourceManager.nodeSum][2];
		for (int i = 0; i < FlowResourceManager.nodeSum; i++) {
			int add = 0;
			for (int j = 0; j < FlowResourceManager.nodeSum; j++) {
				add = Workflow.dependencies[j][i] + add;
			}
			if (add == 0) {
				first_module = i;
				break;
			}
		}
		datasize[first_module][0] = first_mdata;
		datasize[first_module][1] = first_rdata;
		workload[first_module][0] = first_rworkload;
		workload[first_module][1] = first_rworkload;
		visited[first_module] = 1;
		boolean flag = true; // the value of flag is determined by visited
		while (flag) {
			for (int i = 0; i < FlowResourceManager.nodeSum; i++) {
				if (visited[i] == 1)
					continue;
				boolean flag2 = true;
				for (int j = 0; j < FlowResourceManager.nodeSum; j++) {
					if (map_datasize[i][j] != null & visited[j] == 0)
						flag2 = false;
				}
				if (flag2) {
					visited[i] = 1;
					List<Integer> succeeds = Utility.calPrecursor(dependencies, i);
					for (int j = 0; j < succeeds.size(); j++) {

						for (int k = 0; k < Workflow.jobs[j].datasizeToWorkResult.size(); k++) {
							double temp_data_size = Utility.calculate_datasize(datasize[j][1],
									Workflow.jobs[j].datasizeToWorkResult.get(k));
							datasize[k][0] = datasize[k][0] + temp_data_size;
							temp_data_size = Utility.calculate_datasize(datasize[j][1],
									Workflow.jobs[j].reduceDatasizeResult.get(k));
							datasize[i][1] = datasize[i][1] + temp_data_size;
							workload[i][1] = workload[i][1]
									+ Utility.calculate_workload(temp_data_size, Workflow.jobs[j].reduceWorkloadResult.get(k));

						}
					}
				}
			}
			// whether we have find all datasize and workload
			int add = 0;
			for (int i = 0; i < FlowResourceManager.nodeSum; i++) {
				add = visited[i] + add;
			}
			if (add == FlowResourceManager.nodeSum)
				flag = false;
		}
		Workload_and_data wd = new Workload_and_data();
		wd.datasize = datasize;
		wd.workload = workload;
		Workflow.wd =  wd;
	}

	public double[][] calculateLink_delay() {
		double[][] link_delay = new double[jobs.length][jobs.length];
		for (int i = 0; i < jobs.length; i++) {
			for (int j = 0; j < jobs.length; j++) {
				if (dependencies[i][j] != 0) {
					link_delay[i][j] = wd.datasize[i][1] / FlowResourceManager.m_bw;
				}
			}
		}
		return link_delay;
	}
	public void calExeTime() {
		// calculate link_delay and execution time
		double[] exe_time = new double[FlowResourceManager.nodeSum];
		for (int i = 0; i < FlowResourceManager.nodeSum; i++) {
			double map_time = Math.floor(wd.workload[i][0] / split_size) / aver_num_CPU / aver_CPU_speed;
			double transfer_time = Math.floor(wd.datasize[i][1] / split_size) / FlowResourceManager.m_bw;
			double reduce_time = wd.workload[i][1] / aver_num_CPU / aver_CPU_speed;
			exe_time[i] = map_time + transfer_time + reduce_time;
		}
		Workflow.exe_time = exe_time;
	}
	
	public void calculate_critical_path(double[][] link_delay, double[] exe_time, int[][] dependencies) {
		// Calculate critical path
		double ED = 0;
		List<Integer> precursorResult;
		int first_module = 0;
		double[] forward_time = new double[link_delay.length];
		double[] backward_time = new double[link_delay.length];
		int[] visited = new int[link_delay.length];
		// calculate forward time
		//find the first module

		precursorResult = Schedual.calPrecursor(dependencies,0);
		for(int i = 0; i < precursorResult.size(); i++){
			if(precursorResult.get(i) == 0){
				visited[i] = 1;
				first_module = i;
				break;
			}
		}

		forward_time[first_module] = exe_time[first_module];
		boolean flag = true;  // the value of flag is determined by visited
		while(flag) {
			for(int i = 0; i < visited.length; i++) {
				if(visited[i] == 1) continue;
				boolean flag2 = true;
				for(int j = 0; j < link_delay.length;j++) {
					if(dependencies[j][i] != 0 & visited[j] == 0) flag2 = false;
				}
				if(flag2) {
					double max_time = 0;
					visited[i] = 1;
					for(int j = 0; j < link_delay.length;j++) {
						if(dependencies[j][i] != 0 & visited[j] == 1) {
							if(link_delay[j][i] + forward_time[j] + exe_time[i] > max_time) {
								max_time = link_delay[j][i] + forward_time[j] + exe_time[i];
							}
						}
					}
					forward_time[i] = max_time;
				}
			}

			// whether we have find all the forward time
			int add = 0;
			for(int i = 0; i< visited.length;i++) {
				add = visited[i] + add;
			}
			if(add == visited.length) flag = false;
		}

		// calculate backward time
		for(int i = 0; i < dependencies.length; i++){
			visited[i] = 0;
		}
		int last_module = 0;
		for(int i = 0; i < dependencies.length; i++) {

			double add = 0;
			for(int j = 0; j < link_delay.length; j++) {
				add = dependencies[i][j] + add;
			}
			if(add == 0) {
				last_module = i;
				break;
			}
		}
		visited[last_module] = 1;
		backward_time[last_module] = forward_time[last_module];

		flag = true;  // the value of flag is determined by visited
		while(flag) {
			for(int i = 0; i < visited.length; i++) {
				if(visited[i] == 1) continue;
				boolean flag2 = true;
				for(int j = 0; j < link_delay.length;j++) {
					if(dependencies[i][j] != 0 & visited[j] == 0) flag2 = false;
				}
				if(flag2) {
					double min_time = forward_time[last_module];visited[i] = 1;
					for(int j = 0; j < link_delay.length;j++) {
						if(dependencies[i][j] != 0 & visited[j] == 1) {
							if(backward_time[j] - link_delay[i][j] - exe_time[j] < min_time) {
								min_time = backward_time[j] - link_delay[i][j] - exe_time[j];
							}
						}
					}
					backward_time[i] = min_time;
				}
			}

			// whether we have find all the forward time
			int add = 0;
			for(int i = 0; i< visited.length;i++) {
				add = visited[i] + add;
			}
			if(add == visited.length) flag = false;
		}

		System.out.println("forward_time: \n");
		for (int i = 0; i < forward_time.length; i++){
			System.out.print(forward_time[i] + " ");
		}
		System.out.println();

		System.out.println("backward_time: \n");
		for (int i = 0; i < backward_time.length; i++) {
			System.out.print(backward_time[i] + " ");
		}
		System.out.println();

		ArrayList<Integer> temp_cp = new ArrayList<>();
		for(int i = 0; i < link_delay.length; i++) {
			if(forward_time[i] == backward_time[i]) {
				temp_cp.add(i);
			}else {
				continue;
			}
		}

		System.out.println("temp_cp: \n");
		System.out.println("" + temp_cp.size());
		for (int i = 0; i < temp_cp.size(); i++){
			System.out.print(temp_cp.get(i) + " ");
		}
		System.out.println();
		int k = temp_cp.size();
		int[] critical_path = new int[k];
		double[] for_time = new double[k];
		for(int i = 0; i < k; i++) {
			for_time[i] = forward_time[temp_cp.get(i)];
		}
		Arrays.sort(for_time);
		for(int i = 0; i < k; i ++) {
			for(int j = 0; j < k; j++) {
				if(for_time[i] == forward_time[temp_cp.get(j)]) {
					critical_path[i] = temp_cp.get(j);
				}
			}
		}
		ED = forward_time[last_module];
		Workflow.ED = ED;
		Workflow.critical_modules = critical_path;
	}
}
