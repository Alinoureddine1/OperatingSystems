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
	private boolean[] talking;
	private boolean[] chopsticks;
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numberOfPhilosophers = piNumberOfPhilosophers;
		state = new State[numberOfPhilosophers];
		talking = new boolean[numberOfPhilosophers];
		chopsticks = new boolean[numberOfPhilosophers];
		for (int i = 0; i < numberOfPhilosophers; i++) {
			state[i] = State.THINKING;
			talking[i] = false;
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
	public synchronized void pickUp(final int piTID)
	{
		
		// ...
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF
