package com.workflow;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.util.Random;

public class Workflow {

    /*public String[][] workload_N_datasize_function(int[][] dependencies) {
        // Customizable Parameters, check the data size changing function
        int num_of_possible_forms = 5;
        String[] possible_form = new String[num_of_possible_forms];
        possible_form[0] = "n";
        possible_form[1] = "n^2";
        possible_form[2] = "logn";
        possible_form[3] = "n^3";
        possible_form[4] = "nlogn";

        int num_of_modules = dependencies.length;
        System.out.println("dependency length: " + dependencies.length);
        String[][] workload_functions = new String[num_of_modules][num_of_modules];
        Random random = new Random();
        for (int i = 0; i < num_of_modules; i++) {
            for (int j = 0; j < num_of_modules; j++) {
                if (dependencies[i][j] != 0) {
                    int temp = random.nextInt(num_of_possible_forms);
                    workload_functions[j][i] = possible_form[temp];
                }
            }
        }
        return workload_functions;
    }*/

    public enum Workload{

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

        private Workload(String name, int index){
            this.name = name;
            this.index = index;
        }

        public static String getName(int index){
            for(Workload wl : Workload.values()){
                if(wl.getIndex() == index){
                    return wl.name;
                }
            }
            return null;
        }
    }

    //	How we generate a DAG
    //	1.lay out all modules sequentially along a pipeline
    //	2.for each module (except the first and the last one),
    //	  we randomly assigning them a proceeding module and a succeeding module.
    //	3.randomly pick up two modules from the pipeline and add a directed edge between them.
    //	Always keep in mind that the first module only has succeeding modules.
    //	and the last module module only has proceeding modules.
    public int[][] create_DAG(int num_of_modules, int num_of_edges) {
        if (num_of_modules - 1 > num_of_edges) return new int[0][0];
        int[][] dependencies = new int[num_of_modules][num_of_modules];

        //1st and 2nd steps: To make it easier, we just link them sequentially.
        for (int i = 0; i < num_of_modules - 1; i++) {
            dependencies[i][i + 1] = 1;
        }

        //3rd STEP
        int current_links = num_of_modules - 1;
        while (current_links < num_of_edges) {
            Random random = new Random();
            int temp1 = random.nextInt(num_of_modules - 1);
            int temp2 = random.nextInt(num_of_modules - temp1 - 1) + temp1 + 1;
            if ((temp1 == temp2) || dependencies[temp1][temp2] == 1) continue;
            dependencies[temp1][temp2] = 1;
            current_links = current_links + 1;
        }

        return dependencies;
    }


}
