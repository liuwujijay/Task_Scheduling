import com.cluster.Cluster;
import com.workflow.Workflow;

public class TaskSchedulingMain {
    public static void main(String[] args) {
        System.out.println("Starting to initialize cluster...");

        Cluster cluster = new Cluster();

        if (cluster.initializeCluster()) {
            System.out.println("Initialize cluster successfully.");
        }else{
            System.out.println("Initialize cluster Failed!");
        }

        System.out.println("Starting to initialize Rack...");

        System.out.println("Starting to initialize Nodes...");

        System.out.println("Starting to initialize DAG");

        Workflow workflow = new Workflow();

        if (workflow.initializeWorkflow()){
            System.out.println("Initialize workflow successfully.");
        }else{
            System.out.println("Initialize workflow Failed!");
        }
    }

}
