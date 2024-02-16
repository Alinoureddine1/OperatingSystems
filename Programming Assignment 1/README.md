## Multithreaded Zero-Day Attack

Is a Java application designed to efficiently process large datasets of logs to identify potential vulnerabilities. It uses a multithreaded approach, with dynamic thread management, to optimize the analysis of logs for specific vulnerability patterns. This application demonstrates the use of the Levenshtein Distance algorithm for pattern matching and employs synchronized methods for thread safety.

## Features

- **Multithreaded Processing**: Leverages Java's threading capabilities to parallelize log analysis.
- **Dynamic Thread Adjustment**: Automatically adjusts the number of threads based on processing needs.
- **Vulnerability Pattern Detection**: Uses the Levenshtein Distance algorithm to find potential vulnerabilities within logs.
- **Thread Safety**: Ensures data integrity with synchronized methods.

## Content
- src/master: Contains the master class that orchestrates log processing. It reads the dataset, spawns worker threads, and dynamically adjusts the number of threads.zes 
- src/worker: Contains the worker class that is tasked with analyzing the logs for vulnerability patterns.
- src/support: Contsins the Levenshtein class that measures distance between two strings, aiding in the detection of vulnerabilities.
- src/dataset: Contains the dataset that will be used to analayze the logs
- src/main: Contains main.java file used to intialize and run the master class

