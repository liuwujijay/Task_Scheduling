public class Utils {

    public static void printArray(){

    }

    public static double generateDoubleRandomNumber(){
        double randomMin =1.5;
        double randomMax = 3.5;
        return (double)(1 + Math.random() * (randomMax - randomMin + 1));
    }

    public static double generateIntRandomNumber(){
        double randomMin =1;
        double randomMax = 3;
        return (int)(1 + Math.random() * (randomMax - randomMin + 1));
    }

}
