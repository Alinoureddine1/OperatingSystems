## Dining Philosophers

It extends the classical Dining Philosophers problem to include additional synchronization challenges, including philosopher conversations and shared pepper shakers. It's implemented in Java, utilizing the language's synchronization primitives.


## Content 
BaseThread.java: Base class for threads, providing foundational thread behavior.
DiningPhilosophers.java: Main class responsible for initializing the simulation, including creating philosophers and starting their threads.
Philosopher.java: Represents philosophers with capabilities to eat, think, talk, and manage life cycles.
Monitor.java: Synchronizes philosopher actions to avoid deadlocks and starvation, ensuring exclusive access to shared resources (chopsticks and pepper shakers).

