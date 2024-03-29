 package Process;
 
 public class Process extends Thread implements Comparable<Process>{

    private final int id;
    private final int arrivalTime;
    private int burstTime;
    private volatile int remainingTime;
    private volatile int waitingTime;
    private volatile int lastTimePoked;
    private  boolean isStarted = false;
    private  volatile boolean isRuning = false;


    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        lastTimePoked = arrivalTime;
    }

  public long getId() { return id; }
  public int getArrivalTime() { return arrivalTime; }
  public int getRemainingTime() { return remainingTime; }
  public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }
  public boolean isStarted() { return isStarted; }
  public void setStarted(boolean started) { isStarted = started; }
  public boolean isRuning() { return isRuning; }
  public void setRuning(boolean runing) { isRuning = runing; }
  public int getWaitingTime() { return waitingTime; }
  public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
  public int getLastTimePoked() { return lastTimePoked; }
  public void setLastTimePoked(int lastTimePoked) { this.lastTimePoked = lastTimePoked; }


    //priority logic
    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.remainingTime, other.remainingTime);
    }


}
