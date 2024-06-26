import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */

	 //Task 1 - Implement the eat() method
	public void eat()
	{
		try
		{
			System.out.println("Philosopher " + getTID() + " has started eating");

			// Task 5 - Implement Pepper logic
			if(Math.random() > 0.5){
				DiningPhilosophers.soMonitor.requestPepper(getTID());
				usePepperShaker();
				DiningPhilosophers.soMonitor.releasePepper(getTID());
				System.out.println("Philosopher " + getTID() + " has finished using the pepper shaker");
			}
			Thread.yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			
			
			System.out.println("Philosopher " + getTID() + " has finished eating");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */

	 //Task 1 - Implement the think() method
	public void think()
	{	

		try
		{
			System.out.println("Philosopher " + getTID() + " has started thinking");
			Thread.yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			Thread.yield();
			System.out.println("Philosopher " + getTID() + " has finished thinking");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}

	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */

	 //Task 1 - Implement the talk() method
	public void talk()
	{
		System.out.println("Philosopher " + getTID() + " has started talking");
		Thread.yield();
		saySomething();
		Thread.yield();
		System.out.println("Philosopher " + getTID() + " has finished talking");
		
	}

	/**
	 * The act of using the pepper shaker
	 */

	 //Task 5 - Implement the usePepperShaker() method

	 public void usePepperShaker(){
		System.out.println("Philosopher " + getTID() + " has used the pepper shaker");
		try{
			Thread.yield();
			Thread.sleep((long)(Math.random() * TIME_TO_WASTE/2));
			Thread.yield();
		} catch(InterruptedException e){
			System.err.println("Philosopher.usePepperShaker():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	 }

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */

	// Task 1 - Complete the run() method
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			DiningPhilosophers.soMonitor.pickUp(getTID());
			eat();
			
			DiningPhilosophers.soMonitor.putDown(getTID());
			think();
			// Task 2 - Fixed the TID input
			if(Math.random() > 0.5){
				DiningPhilosophers.soMonitor.requestTalk(getTID());
				talk();
				DiningPhilosophers.soMonitor.endTalk(getTID());
			}
			Thread.yield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
