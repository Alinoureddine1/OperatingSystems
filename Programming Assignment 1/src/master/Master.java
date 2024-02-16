package master;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import worker.Worker;



public class Master extends Thread {
    private static String[] dataset;    // The dataset to be processed
    private static int nbOfLines;    // The number of lines in the dataset
    private String valnerabilityPattern;    // The vulnerability pattern to be searched for
    private int workerNumber;   // The number of workers
    private int count;  // The number of vulnerabilities found
    private double avg; // The average number of vulnerabilities found per line /or previous approximate average
    private double approximateAvg;  // The approximate average number of vulnerabilities found per line

    // Constructor
    public Master(){
        valnerabilityPattern = "V04K4B63CL5BK0B";
        workerNumber = 2;
        count = 0;
        avg = 0;
        approximateAvg = 0;
        try {
            // Get the absolute path of the dataset file (had issues running it from other devices without the absolute path)
            String relativePath = "OperatingSystems/Programming Assignment 1/src/dataset/vm_1.txt";
            String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
            // Read the dataset from the file
            List<String> lines = Files.readAllLines(Paths.get(absolutePath));
            dataset = lines.toArray(new String[0]);
            nbOfLines = lines.size();
        } catch (IOException e) { // Handle the case where the file is not found
            e.printStackTrace();
        }

    }
    
    // Run the master thread
    @Override
    public void run(){
        System.out.println("Master is running");
        int processedLines = 0;
        // Loop through the dataset and process the lines
        while(processedLines < nbOfLines){
           List <Worker> workers = new ArrayList<>();

            // Create workers
           for (int i = 0; i < workerNumber && processedLines < nbOfLines; i++, processedLines++) {
            workers.add(new Worker(i, dataset[processedLines], valnerabilityPattern, this));
        }
            // Start workers
            for(Worker worker: workers){
                worker.start();
            }
            // Join workers to wait for them to finish
            for(Worker worker: workers){
                    try {
                        worker.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            
            // Calculate the approximate average number of vulnerabilities found per line
            approximateAvg = count/(double)nbOfLines;
            if(Math.abs(approximateAvg-avg)/avg >0.2){
                avg = approximateAvg;
                workerNumber+=2;    // Increase the number of workers by 2
                try{
                    Thread.sleep(2000); // Sleep for 2 seconds
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

        }
        // Log the results
        System.out.println("\n\nThe number of vulnerabilities found is: " + count);
        System.out.println("The average number of vulnerabilities found per line is: " + approximateAvg);
        System.out.println("The number of workers is: " + workerNumber);

       


    }
   
    // Function to increment the count of vulnerabilities found
    public synchronized void incrementCount() {
        count++;
    }

 

    
}

