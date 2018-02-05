package com.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utility {
    public enum NodeInfo {
        RACKINFO("Rack_Info", 10, 2),
        UBANDWIDTH("Upstream_Bandwidth", 50, 75),
        DBANDWIDTH("Downstream_bandwidth", 75,100),
        CPUNUM("Number_Of_CPU_Cores", 1, 16),
        CPUSPEED("CPU_Speed", 1000,5000),
        MEMORYSIZE("Memory_Size", 50, 100),
        SCAPACITY("storage capacity", 500, 1000);
        private final String name;
        private final int upperBound;
        private final int lowerBound;

        NodeInfo(String name, int upperBound, int lowerBound) {
            this.name = name;
            this.upperBound = upperBound;
            this.lowerBound = lowerBound;
        }

        public String getName() {
            return name;
        }
    }

    public int[][] node_info;
    public int[][] inter_rack_bw;

    public static long generate(String input){
        for (NodeInfo node : NodeInfo.values()) {
            if(node.name.toUpperCase().equals(input.toUpperCase())){
                return generateRam(node.lowerBound,node.upperBound);
            }
        }
        return 0;
    }
    public Utility(int num_of_racks, int num_per_rack) {
        super();
        this.node_info = generate_node_info(num_of_racks, num_per_rack);
        this.inter_rack_bw = generate_inter_rack_bw(num_of_racks);
    }

    /**
     * @param num_of_racks
     * @param num_per_rack
     * @return
     */
    private int[][] generate_node_info(int num_of_racks, int num_per_rack) {
        // TODO Customizable Parameters.. Some of them are unrealistic

        int[][] node_info = new int[num_of_racks * num_per_rack][NodeInfo.values().length + 1];
        for (int i = 0; i < num_of_racks * num_per_rack; i++) {
            node_info[i][0] = i / num_per_rack + 1;
            for (NodeInfo node : NodeInfo.values()) {
                node_info[i][node.ordinal() + 1] = generateRam(node.lowerBound, node.upperBound);
            }
        }
        return node_info;
    }

    /**
     * @param lowerBound
     * @param upperBound
     * @return
     */
    private static int generateRam(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    /**
     * @param num_of_racks
     * @return
     */
    public static int[][] generate_inter_rack_bw(int num_of_racks) {
        // TODO Customizable Parameters.. Some of them are unrealistic
        int min_bw = 200;
        int max_bw = 400;

        int[][] inter_rack_bw = new int[num_of_racks][num_of_racks];
        Random random = new Random();
        for (int i = 0; i < num_of_racks; i++) {
            for (int j = 0; j < num_of_racks; j++) {
                if(i==j){
                    inter_rack_bw[i][j] = 0;
                }else if(inter_rack_bw[i][j] == 0){
                    inter_rack_bw[i][j] = random.nextInt(max_bw - min_bw) + min_bw;
                    inter_rack_bw[j][i] = inter_rack_bw[i][j];
                }
            }
        }
        return inter_rack_bw;
    }
    public static double calculate_datasize(double data_size, String function_form) {
        double workload = 0d;
        if (function_form == "n")
            return data_size;
        if (function_form == "n^2")
            return Math.pow(data_size, 2);
        if (function_form == "n^3")
            return Math.pow(data_size, 3);
        if (function_form == "logn")
            return Math.log(data_size);
        if (function_form == "nlogn")
            return Math.log(data_size) * data_size;
        return workload;
    }
    public static List<Integer> calPrecursor(int[][] dependencies, int col) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < dependencies.length; i++) {
            if (dependencies[i][col] != 0) {
                res.add(i);
            }
        }
        return res;
    }



    public static double calculate_workload(double data_size, String function_form) {
        double workload = 0d;
        if (function_form == "n")
            return data_size;
        if (function_form == "n^2")
            return Math.pow(data_size, 2);
        if (function_form == "n^3")
            return Math.pow(data_size, 3);
        if (function_form == "logn")
            return Math.log(data_size);
        if (function_form == "nlogn")
            return Math.log(data_size) * data_size;
        return workload;
    }

    @Override
    public String toString() {
        return "Utility [node_info=" + Arrays.toString(node_info) + ", inter_rack_bw=" + Arrays.toString(inter_rack_bw)
                + "]";
    }

}
