package com.cluster;

import java.util.Random;

import com.util.Utility;
import com.workflow.FlowResourceManager;

public class Cluster extends FlowResourceManager{
    public Rack[] rack;
    public int[][] inner_rack_bw;

    public boolean initializeCluster(int rackNum, int nodesNum) {
        try {
            for (int i = 0; i < rackNum; i++) {
                Random ram = new Random();
                this.rack[i] = new Rack(ram.nextInt(2) - 1 + nodesNum);
            }
            inner_rack_bw = Utility.generate_inter_rack_bw(rack.length);
            FlowResourceManager.cluster = new Cluster();
            FlowResourceManager.m_bw = Math.min(FlowResourceManager.dbwSum, FlowResourceManager.ubwSum) /FlowResourceManager.nodeSum;
            FlowResourceManager.aver_CPU_speed = FlowResourceManager.CPU_speed_sum / FlowResourceManager.nodeSum;
            FlowResourceManager.aver_num_CPU = FlowResourceManager.CPU_num / FlowResourceManager.nodeSum;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
            // TODO: handle exception
        }

        return true;
    }

}
