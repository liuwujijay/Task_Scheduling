package com.workflow;

import java.util.ArrayList;
import java.util.Random;

public class JobDAO {
    public JobDAO  succeedingJob;
    public int[] calculatePrecursor(int[][] DAGInfo){
        //get the number of precursor of all Jobs
        int counter = 0;
        int[] precursorNum = new int[DAGInfo.length];

        for (int i = 0; i < DAGInfo.length; i++) {
            for (int j = 0; j < DAGInfo[i].length; j++) {
                if (DAGInfo[i][j] == 1) {
                    counter++;
                }
                precursorNum[i] = counter;
                counter = 0;
            }
        }
        return precursorNum;
    }


    /**
     * @param datasizeArray contains jobIds of precursor
     * @return
     */
    public ArrayList<String> datasizeToWorkload(int[] datasizeArray){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < datasizeArray.length; i++){
            result.add(Workflow.Workload.getName(datasizeArray[i]));
        }
        return result;
    }
    public ArrayList<String> getFunction(){
        Random r = new Random();
        ArrayList<String> list4 = new ArrayList<String>();

        list4.add(Workflow.Workload.getName(r.nextInt() % 5 + 1));
        return list4;
    }

    public ArrayList<String> calculateMapWorkload(int jobID, int[][] dependencies){
        //int number = precursorNum[jobID];
        ArrayList<String> mapWorkloadResult = new ArrayList<String>();
        for (int i = 0; i < dependencies[jobID].length; i++) {
            if(dependencies[i][jobID] != 0){
                // for each precursor we do give a function to it.
                Random r =  new Random();
                mapWorkloadResult.add(Workflow.Workload.getName(r.nextInt()%5 + 1));
            }
        }
        return mapWorkloadResult;
    }

    public ArrayList<String> calculateReduceWorkload(int jobID, int[][] dependencies){
        ArrayList<String> reduceWorkloadResult = new ArrayList<String>();
        for (int i = 0; i < dependencies[jobID].length; i++) {
            if(dependencies[i][jobID] != 0){
                // for each precursor we do give a function to it.
                Random r =  new Random();
                reduceWorkloadResult.add(Workflow.Workload.getName(r.nextInt()%5 + 1));
            }
        }
        return reduceWorkloadResult;
    }

    public ArrayList<String> calculateMapDatasize(int jobID, int[][] dependencies){
        //TODO call function in Utils to get random number in a certain range
        //int mapDatasize = 0;
        ArrayList<String> mapDatasizeResult = new ArrayList<String>();
        for (int i = 0; i < dependencies[jobID].length; i++) {
            if(dependencies[i][jobID] != 0){
                // for each precursor we do give a function to it.
                Random r =  new Random();
                mapDatasizeResult.add("" + (r.nextFloat() + 0.5));
            }
        }

        return mapDatasizeResult;
    }

    public ArrayList<String> calculateReduceDatasize(int jobID, int[][] dependencies){
        //int mapDatasize = 0;
        //TODO call function in Utils to get random number in a certain range
        ArrayList<String> reduceDatasizeResult = new ArrayList<String>();
        for (int i = 0; i < dependencies[jobID].length; i++) {
            if(dependencies[i][jobID] != 0){
                // for each precursor we do give a function to it.
                Random r =  new Random();
                reduceDatasizeResult.add( "" +(r.nextFloat() + 0.5));
            }
        }
        return reduceDatasizeResult;
    }







}
