/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{



	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	enum State {THINKING, HUNGRY, EATING, TALKING};
	private State[] state;
	private int numberOfPhilosophers;
	private boolean talking;
	private boolean[] pepper	= {true, true};
	private int[] shakerAssignedTo = {-1, -1};
	private boolean[] chopsticks;
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{

		numberOfPhilosophers = piNumberOfPhilosophers;
		state = new State[numberOfPhilosophers];
		chopsticks = new boolean[numberOfPhilosophers];
		talking = false;
		for (int i = 0; i < numberOfPhilosophers; i++) {
			state[i] = State.THINKING;
			chopsticks[i] = true;
		}

	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * You may need to add more procedures for task 5
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */

	 // Task 2 - Complete the pickUp() method
	public synchronized void pickUp(final int piTID)
	{
		int iD = piTID - 1;
		state[iD] = State.HUNGRY;
		while(
			state[(iD + 1) % numberOfPhilosophers] == State.EATING ||
			state[(iD + numberOfPhilosophers - 1) % numberOfPhilosophers] == State.EATING
		) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		state[iD] = State.EATING;

	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */

	 // Task 2 - Complete the putDown() method
	public synchronized void putDown(final int piTID)
	{
		int iD = piTID - 1;
		state[iD] = State.THINKING;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */

	 // Task 3 - Complete the requestTalk() method
	public synchronized void requestTalk(int piTID )
	{
		int iD = piTID - 1;
		while(talking || state[iD]==State.EATING) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		talking = true;
		state[iD] = State.TALKING;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */

	 // Task 3 - Complete the endTalk() method
	public synchronized void endTalk(int piTID)
	{
		int iD = piTID - 1;
		talking = false;
		state[iD] = State.THINKING;
		notifyAll();
	}

	// Task 5 - Complete the requestPepper() method
	public synchronized void requestPepper(int piTID)
	{
		int iD = piTID - 1; 
		while(state[iD] != State.EATING && (!pepper[0] || !pepper[1]) ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(pepper[0] && state[iD] == State.EATING){
			pepper[0] = false;
			shakerAssignedTo[0]= iD;

		}
		else if(pepper[1] && state[iD] == State.EATING){
			pepper[1] = false;
			shakerAssignedTo[1]= iD;
		}

	}
	
	// Task 5 - Complete the releasePepper() method
	public synchronized void releasePepper(int piTID)
	{
		int iD = piTID - 1;
		if(shakerAssignedTo[0] == iD){
			pepper[0] = true;
			shakerAssignedTo[0] = -1;
		}
		else if(shakerAssignedTo[1] == iD){
			pepper[1] = true;
			shakerAssignedTo[1] = -1;
		}

		notifyAll();
	}
	
}

// EOF
