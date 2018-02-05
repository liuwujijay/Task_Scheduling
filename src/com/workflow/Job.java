package com.workflow;

import java.util.ArrayList;
public class Job {

    private int mapWorkload;
    private int mapDatasize;
    private int reduceWorkload;
    private int reduceDatasize;
    public Job  succeedingJob;
    public ArrayList<String> datasizeToWorkResult;
    public ArrayList<String> mapWorkloadResult;
    public ArrayList<String> reduceWorkloadResult;
    public ArrayList<String> mapDatasizeResult;
    public ArrayList<String> reduceDatasizeResult ;
    public int jobId;
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



}
