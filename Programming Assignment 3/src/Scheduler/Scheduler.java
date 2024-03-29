package Scheduler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import Process.Process;

public class Scheduler extends Thread {
    private List <Process> processes;
    private int quantum;
    private PriorityQueue<Process> readyQueue;
    private Map<Long, Integer> waitingTimes= new HashMap<>();
    private int currentTime = 1;
    private BufferedWriter writer;


    
    public Scheduler( String input, String output) {
        List<Process> processes = new ArrayList<>(); try {
            Files.lines(Paths.get(input)).forEach(line -> {

                // Split the line by whitespace
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    int arrivalTime = Integer.parseInt(parts[0]);
                    int burstTime = Integer.parseInt(parts[1]);
                    // Create a new process and add it to the list
                    Process process = new Process(processes.size() + 1, arrivalTime, burstTime);
                    processes.add(process);
                }
            });
        } catch (Exception e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            System.exit(1);
        }

            this.processes = new ArrayList<>(processes);

            // Set the priority queue to sort by remaining time and arrival time
            this.readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime).thenComparingInt(Process::getArrivalTime));

            try{
                writer = new BufferedWriter(new FileWriter(output));

            }catch (IOException e) {
            System.out.println("An error occurred while creating the file writer.");
            e.printStackTrace();
        }
        }

    @Override
    public void run(){

        System.out.println("Starting Scheduler");
        
        while (!readyQueue.isEmpty() || !processes.isEmpty()){


            // Add processes to the ready queue
             processes.removeIf(p -> {
                if (p.getArrivalTime() <= currentTime) {
                    readyQueue.add(p);
                    return true;
                }
                return false;
            });
            Process currentProcess = readyQueue.poll();


            if(!currentProcess.isStarted()){
                currentProcess.setStarted(true);
                currentProcess.start();
                currentProcess.setWaitingTime(currentTime - currentProcess.getArrivalTime());
                writeToFile("Time "+ currentTime + " Process "+ currentProcess.getId() + " Started\n");
            
            }else{
                currentProcess.resumeProcess();
                writeToFile("Time "+ currentTime + " Process "+ currentProcess.getId() + " Resumed\n");
                currentProcess.setWaitingTime( currentProcess.getWaitingTime() + currentTime - currentProcess.getLastTimePoked());
            }
            
            quantum = (int) Math.ceil(currentProcess.getRemainingTime() * 0.1);
            int executionTime = Math.min(quantum, currentProcess.getRemainingTime());
            currentTime += executionTime;

           // currentTime += quantum;
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);

            if (currentProcess.getRemainingTime() > 0) {
                currentProcess.pause();
                writeToFile("Time " + currentTime + " Process " + currentProcess.getId() + " Paused\n");
                currentProcess.setLastTimePoked(currentTime);
                readyQueue.add(currentProcess);
            } else {
                writeToFile("Time " + currentTime + " Process " + currentProcess.getId() + " Finished\n");      
                waitingTimes.put(currentProcess.getId(), currentProcess.getWaitingTime());

        }
    }
    printWaitingTimes();
    closeWriter();
    System.out.println("Scheduler Finished");
}

private void printWaitingTimes() {
    writeToFile("-------------------------\nWaiting Times:\n");
    for (Map.Entry<Long, Integer> entry : waitingTimes.entrySet()) {
        writeToFile("Process " + entry.getKey() + ": " + entry.getValue() + "\n");
    }
}

private void writeToFile(String text) {
    try {
        writer.write(text);
    } catch (IOException e) {
        System.out.println("An error occurred while writing to the file.");
        e.printStackTrace();
    }
}

private void closeWriter() {
    try {
        writer.close();
    } catch (IOException e) {
        System.out.println("An error occurred while closing the file writer.");
        e.printStackTrace();
    }
}



}
