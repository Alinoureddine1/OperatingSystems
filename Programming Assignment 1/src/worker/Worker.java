package worker;
import support.LevenshteinDistance;
import master.Master;

public class Worker extends Thread {
  private int workerNumber;
  private String valnerabilityPattern;
  private int patternLength;
  private String logField;
  private LevenshteinDistance levenshteinDistance;
  private Master master;

  public Worker(int workerNumber, String logInputData, String valnerabilityPattern, Master master) {
    this.workerNumber = workerNumber;
    this.valnerabilityPattern = valnerabilityPattern;
    this.patternLength = valnerabilityPattern.length();
    this.logField =  extractLogPart(logInputData);
    this.levenshteinDistance = new LevenshteinDistance();
    this.master = master;
  }
 
  private String extractLogPart(String input) {
    String logPrefix = "Log:";
    int logStartIndex = input.indexOf(logPrefix);
    
    if (logStartIndex != -1) {
        // Add the length of "Log:" to start index to skip the prefix itself
        return input.substring(logStartIndex + logPrefix.length()).trim();
    } else {
        // Handle the case where "Log:" is not found, if necessary
        return null;
    }
}


private void processLogWithPattern(String logPart, String vulnerabilityPattern) {
  for (int i = 0; i <= logPart.length() - patternLength; i++) {
      String substring = logPart.substring(i, i + patternLength);  
      levenshteinDistance.Calculate(substring, vulnerabilityPattern);
      System.out.println("Worker " + workerNumber + " is processing " + substring + " with " + vulnerabilityPattern + " and the change ratio is " + levenshteinDistance.Change_Ratio);
      if(levenshteinDistance.isAcceptable_change()){
        System.out.println("Worker " + workerNumber + " found a match");
        master.incrementCount();
        break;
      }
  }
}

@Override
public void run() {
  System.out.println("Worker " + workerNumber + " is running");
    processLogWithPattern(logField, valnerabilityPattern);
}


}
