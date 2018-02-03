package com.workflow;

import java.util.ArrayList;

public class Job {

    private int mapWorkload;
    private int mapDatasize;
    private int reduceWorkload;
    private int reduceDatasize;

    public int getMapWorkload() {
        return mapWorkload;
    }

    public int getMapDatasize() {
        return mapDatasize;
    }

    public int getReduceWorkload() {
        return reduceWorkload;
    }

    public int getReduceDatasize() {
        return reduceDatasize;
    }

    public void setMapWorkload(int mapWorkload) {
        this.mapWorkload = mapWorkload;
    }

    public void setMapDatasize(int mapDatasize) {
        this.mapDatasize = mapDatasize;
    }

    public void setReduceWorkload(int reduceWorkload) {
        this.reduceWorkload = reduceWorkload;
    }

    public void setReduceDatasize(int reduceDatasize) {
        this.reduceDatasize = reduceDatasize;
    }


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


    public ArrayList datasizeToWorkload(int[] datasizeArray){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < datasizeArray.length; i++){
            result.add(Workflow.Workload.getName(datasizeArray[i]));
        }
        return result;
    }

    public ArrayList calculateMapWorkload(int jobID, int[] precursorNum){
        int number = precursorNum[jobID];
        ArrayList mapWorkloadResult = new ArrayList();
        for(int i = 0; i < number; i++){
            mapWorkloadResult.add(Workflow.Workload.getName(i + 1));
            //MapWorkload += Workflow.Workload.getName(i + 1);
        }
        return mapWorkloadResult;
    }

    public ArrayList calculateReduceWorkload(int jobID, int[] precursorNum){
        int number = precursorNum[jobID];
        ArrayList<String> reduceWorkloadResult = new ArrayList<>();
        for(int i = 0; i < number; i++){
            reduceWorkloadResult.add(Workflow.Workload.getName(i + 1));
            //MapWorkload += Workflow.Workload.getName(i + 1);
        }
        return reduceWorkloadResult;
    }

    public ArrayList calculateMapDatasize(int jobID, int[] precursorNum){
        //TODO call function in Utils to get random number in a certain range
        //int mapDatasize = 0;
        int number = precursorNum[jobID];
        ArrayList<String> mapDatasizeResult = new ArrayList<>();
        for(int i = 0; i < number; i++){
            mapDatasizeResult.add(Workflow.Workload.getName(i + 1));
        }
        return mapDatasizeResult;
    }

    public ArrayList calculateReduceDatasize(int jobID, int[] precursorNum){
        //int mapDatasize = 0;
        //TODO call function in Utils to get random number in a certain range
        int number = precursorNum[jobID];
        ArrayList<String> reduceDatasizeResult = new ArrayList<>();
        for(int i = 0; i < number; i++){
            reduceDatasizeResult.add(Workflow.Workload.getName(i + 1));
        }
        return reduceDatasizeResult;
    }






}
