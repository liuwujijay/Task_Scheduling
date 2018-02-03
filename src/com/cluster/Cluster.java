package com.cluster;

import java.util.Random;

public class Cluster {
    public Rack[] rack;
    public Cluster(int rackNum, int nodesNum) {
        for (int i = 0; i < rackNum; i++) {
            Random ram = new Random();
            this.rack[i] = new Rack(ram.nextInt(2) -1 + nodesNum);
        }
    }


}




