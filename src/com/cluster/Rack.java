package com.cluster;

import com.util.Utility;

public class Rack {

    public Node[] node;
    public Rack(int nodeNum) {
        for (int i = 0; i < nodeNum; i++) {
            this.node[i] = new Node(Utility.generate("Upstream_Bandwidth"),
                    Utility.generate("Downstream_bandwidth"),
                    (int)Utility.generate("Number_Of_CPU_Cores"),
                    (int)Utility.generate("CPU_Speed"),
                    (int)Utility.generate("Memory_Size"),
                    (int)Utility.generate("storage capacity"));
        }
    }
}
