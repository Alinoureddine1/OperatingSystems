package master;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import worker.Worker;

public class Master extends Thread {
    private static String[] dataset;
    private static int nbOfLines;
    private String valnerabilityPattern;
    private int workerNumber;
    private int count;
    private double avg;
    private double approximateAvg;

    public Master(){
        valnerabilityPattern = "V04K4B63CL5BK0B";
        workerNumber = 2;
        count = 0;
        avg = 0;
        approximateAvg = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get("programming Assignment 1/src/dataset/vm_2.txt"));
            dataset = lines.toArray(new String[0]);
            nbOfLines = lines.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void run(){
        int processedLines = 0;
        while(processedLines < nbOfLines){
           List <Worker> workers = new ArrayList<>();

           for (int i = 0; i < workerNumber && processedLines < nbOfLines; i++, processedLines++) {
            workers.add(new Worker(i, dataset[processedLines], valnerabilityPattern, this));
        }

            for(Worker worker: workers){
                worker.start();
            }

            for(Worker worker: workers){
                    try {
                        worker.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
    
            approximateAvg = count/(double)nbOfLines;
            if(Math.abs(approximateAvg-avg)/avg >0.2){
                avg = approximateAvg;
                workerNumber+=2;
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

        }
        System.out.println("The number of vulnerabilities found is: " + count);
        System.out.println("The average number of vulnerabilities found per line is: " + approximateAvg);
        System.out.println("The number of workers is: " + workerNumber);

       


    }
    public String returnVulnerabilityPattern(){
        return valnerabilityPattern;
    }
    public synchronized void incrementCount() {
        count++;
    }

 

    
}

