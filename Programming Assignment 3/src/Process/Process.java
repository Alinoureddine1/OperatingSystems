 package Process;
 
 public class Process implements Comparable<Process>{

    private final int id;
    private final int arrivalTime;
    private int burstTime;
    private volatile int remainingTime;
    private volatile int waitingTime;
    private boolean isStarted = false;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
    }

    public synchronized void executeProcess(int quantum) {
        if (quantum > remainingTime) {
            quantum = remainingTime;
        }
        remainingTime -= quantum;
    }

    public synchronized boolean isDone() {
        return remainingTime <= 0;
    }


  //ToDo: Implement setters and getters
  public int getId() { return id; }
  public int getArrivalTime() { return arrivalTime; }
  public int getRemainingTime() { return remainingTime; }
  public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
  public boolean isStarted() { return isStarted; }
  public void setStarted(boolean started) { isStarted = started; }
  public int getWaitingTime() { return waitingTime; }
  public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }



    //priority logic
    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.remainingTime, other.remainingTime);
    }


}
