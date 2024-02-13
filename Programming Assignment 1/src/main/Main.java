package main;
import master.Master;
import support.LevenshteinDistance;


public class Main {
    public static void main(String[] args) {
        Master master = new Master();
        master.start();
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        String valnerabilityPattern = "V04K4B63CL5BK0B";
        String logInputData = "V04K4B63CL5BK0B";
        levenshteinDistance.Calculate(logInputData, valnerabilityPattern);
        // System.out.println("The change ratio is: " + levenshteinDistance.Change_Ratio);
        // System.out.println("Is the change acceptable? " + levenshteinDistance.isAcceptable_change());
    }


}
