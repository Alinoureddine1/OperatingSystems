package Scheduler;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Process.Process;

public class Scheduler {
    private List <Process> processes;
    private int quantum;
    private PriorityQueue<Process> processQueue;

    public Scheduler(List<Process> processes, int quantum) {
        this.processes = new ArrayList<>(processes);
        this.quantum = quantum;
        processQueue = new PriorityQueue<>(processes);
    }
    public void startScheduling(){
        int currentTime = 0;
        processQueue.addAll(processes);
        while (!processQueue.isEmpty()){

            Process currentProcess = processQueue.poll();

            if(!currentProcess.isStarted()){
                currentProcess.setStarted(true);
                currentProcess.setWaitingTime(currentTime - currentProcess.getArrivalTime());
                System.out.println("Time "+ currentTime + " Process "+ currentProcess.getId() + " Started");
            
            }else{
                System.out.println("Time "+ currentTime + " Process "+ currentProcess.getId() + " R esumed");
            }
            

            currentProcess.executeProcess(quantum);
            currentTime += quantum;
            if (!currentProcess.isDone()){
                processQueue.add(currentProcess);
            }

        }
    }
}
