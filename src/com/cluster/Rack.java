package com.cluster;

import com.util.Utility;
import com.workflow.FlowResourceManager;

public class Rack extends FlowResourceManager{
    public int rackId;
    public Node[] node;
    public static int nodeId;
    //inter -rack bw int[][];
    public Rack(int nodeNum) {
        FlowResourceManager.nodeSum = FlowResourceManager.nodeSum + nodeNum;
        this.node = new Node[nodeSum];
        for (int i = 0; i < nodeNum; i++) {
            this.node[i] = new Node(Utility.generate("Upstream_Bandwidth"),
                    Utility.generate("Downstream_bandwidth"),
                    (int)Utility.generate("Number_Of_CPU_Cores"),
                    (int)Utility.generate("CPU_Speed"),
                    (int)Utility.generate("Memory_Size"),
                    (int)Utility.generate("storage capacity"),i);
            FlowResourceManager.ubwSum += this.node[i].getUpstream_bw();
            FlowResourceManager.dbwSum += this.node[i].getDownstream_bw();
            FlowResourceManager.CPU_num += this.node[i].getNum_of_CPU_cores();
            FlowResourceManager.CPU_speed_sum += this.node[i].getCPU_speed();

        }
    }
}
