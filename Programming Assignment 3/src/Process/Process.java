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

    // pause logic
    private final Object pauseLock = new Object();
    private boolean paused = false;


    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        lastTimePoked = arrivalTime;
    }

  // Getters and Setters
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


    // run logic
    @Override
    public void run(){
            synchronized (pauseLock){
                while (paused){
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }

    }

    //priority logic
    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.remainingTime, other.remainingTime);
    }


    // Pause/Resume logic
    public void pause() {
        synchronized (pauseLock) {
            paused = true;
            System.out.println("Process " + id + " paused");
        }
    }

    public void resumeProcess() {
        synchronized (pauseLock) {
            paused = false;
            System.out.println("Process " + id + " resumed");
            pauseLock.notifyAll(); 
        }
    }


}
