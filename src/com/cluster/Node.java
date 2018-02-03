package com.cluster;

public class Node {

    double upstream_bw;
    double downstream_bw;
    int num_of_CPU_cores;
    int CPU_speed;
    int memory_size;
    int storage_capacity;

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

