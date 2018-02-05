package com.cluster;

import java.util.HashMap;

public class Node {

    // node id
    // rackNum;
    // status
    int nodeId;
    int rackNum;
    HashMap<String, Integer> status;
    double upstream_bw;
    double downstream_bw;
    int num_of_CPU_cores;
    int CPU_speed;
    int memory_size;
    int storage_capacity;

    public Node(double upstream_bw, double downstream_bw, int num_of_CPU_cores, int cPU_speed, int memory_size,
                int storage_capacity,int nodeId) {
        super();
        this.upstream_bw = upstream_bw;
        this.downstream_bw = downstream_bw;
        this.num_of_CPU_cores = num_of_CPU_cores;
        this.CPU_speed = cPU_speed;
        this.memory_size = memory_size;
        this.storage_capacity = storage_capacity;
        this.status = initialStatus();
        this.nodeId = nodeId;
    }

    private HashMap<String, Integer> initialStatus() {
        HashMap<String, Integer> status = new HashMap<>();
        status.put("MEMORY", 0);
        status.put("STORAGE", 0);
        status.put("UTASK", 0);
        status.put("DTASK", 0);
        return status;
    }

    public void setUpstream_bw(double upstream_bw) {
        this.upstream_bw = upstream_bw;
    }

    public void setDownstream_bw(double downstream_bw) {
        this.downstream_bw = downstream_bw;
    }

    public void setNum_of_CPU_cores(int num_of_CPU_cores) {
        this.num_of_CPU_cores = num_of_CPU_cores;
    }

    public void setCPU_speed(int CPU_speed) {
        this.CPU_speed = CPU_speed;
    }

    public void setMemory_size(int memory_size) {
        this.memory_size = memory_size;
    }

    public void setStorage_capacity(int storage_capacity) {
        this.storage_capacity = storage_capacity;
    }

    public double getUpstream_bw() {
        return upstream_bw;
    }

    public double getDownstream_bw() {
        return downstream_bw;
    }

    public int getNum_of_CPU_cores() {
        return num_of_CPU_cores;
    }

    public int getCPU_speed() {
        return CPU_speed;
    }

    public int getMemory_size() {
        return memory_size;
    }

    public int getStorage_capacity() {
        return storage_capacity;
    }

}
