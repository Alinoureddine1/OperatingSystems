package worker;
import support.LevenshteinDistance;
import master.Master;

public class Worker extends Thread {
  private int workerNumber;   // Worker id number in launch order
  private String valnerabilityPattern;  // The vulnerability pattern to be searched for
  private int patternLength;  // The length of the vulnerability pattern
  private String logField;  // The log part of the input data
  private LevenshteinDistance levenshteinDistance;  // The Levenshtein distance object
  private Master master;  // The master thread

  // Constructor
  public Worker(int workerNumber, String logInputData, String valnerabilityPattern, Master master) {
    this.workerNumber = workerNumber;
    this.valnerabilityPattern = valnerabilityPattern;
    this.patternLength = valnerabilityPattern.length();
    this.logField =  extractLogPart(logInputData);
    this.levenshteinDistance = new LevenshteinDistance();
    this.master = master;
  }
 
// Function to extract the log part from the input data
  private String extractLogPart(String input) {
    String logPrefix = "Log:";
    int logStartIndex = input.indexOf(logPrefix);
    
    if (logStartIndex != -1) {
        // Add the length of "Log:" to start index to skip the prefix itself
        return input.substring(logStartIndex + logPrefix.length()).trim();
    } else {
        // Handle the case where "Log:" is not found
        return null;
    }
}

// Function to process the log versus the vulnerability pattern.
private void processLogWithPattern(String logPart, String vulnerabilityPattern) {
  // Loop through all the possible substrings of the log part
  for (int i = 0; i <= logPart.length() - patternLength; i++) {
      String substring = logPart.substring(i, i + patternLength);  
      levenshteinDistance.Calculate(substring, vulnerabilityPattern); // Calculate the Levenshtein distance
      // System.out.println("Worker " + workerNumber + " is processing " + substring + " with " + vulnerabilityPattern + " and the change ratio is " + levenshteinDistance.Change_Ratio); // Print the change ratio(for testing)
      
      if(levenshteinDistance.isAcceptable_change() && levenshteinDistance.Change_Ratio ==1.0 ){ // Match is found
        // Increment the count of matches in the master thread
        master.incrementCount();
        break;
      }
  }
}

// Run the worker thread
@Override
public void run() {
  processLogWithPattern(logField, valnerabilityPattern);
}


}
