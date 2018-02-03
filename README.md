# Storage-aware task scheduling on big data workflows in distributed environments 

## Workflow Generator

What we want to do here is to generate a Big Data workflow.

Input:  \# of modules, \# of links

Output:
 * Dependencies \(size =  n\*n, int \)
 * Map_data_function \(size = n\*n, String \)
 * Reduce_data_function \(size = n\*n, String \)
 * Map_workload_function \(size = n\*n, String \)
 * Reduce_workload_function \(size = n\*n, String \)

Descriptions:
 * Dependencies\[i\]\[j\] = 1 means that there is a directed edge from module i to module j
 * Map_data_function\[i\]\[j\] = "linear" means that for module i, if its input data is from module j, then the output data size of module j will be a linear function of its input data size coming from module i.
 * Similarly for the remaining functions.


## Hierarchy structured heterogenous cluster genetor

The objective of this tiny part is to generate a hierarchy structured cluster consisting of heterogenous computing nodes

Input: \# of racks, \# of nodes for each rack

Output:
 + A two dimensional matrix, each row represents a node, and each column is a property
   * for each node, rack information of a node  \(1 dimension\)
   * for each node, bandwidth \(up, down, in total 2 dimensions \)
   * for each node, \# of CPU cores, CPU speed, memory size, storage capacity\(4 dimensions\) 
 + A two dimensional matrix
   * for each rack, inter rack bandwidth

Descriptions:
 + Node_info, size of \(\# of racks \* \# of nodes for each rack\) \* 7.
   * the 1st column is its rack info
   * the 2nd column is its upstream bandwidth
   * the 3rd column is its downstream bandwidth
   * the 4th column is its number of CPU cores
   * the 5th column is its CPU speed
   * the 6th column is its memory size
   * the 7th column is its storage capacity
 + Inter_rack_bw\[i\]\[j\] represents the bandwidth between rack i and rack j.


