package core;

/**
 * This class provides IDs for each loaded song
 */
public final class IDGenerator {
	private IDGenerator(){}

	// Dynamic maximum
	private static final long MAX = 9999;

	// current id
	private static long idPointer = 0L;

	/**
	 * This static method returns the next free ID
	 * @return next possible id in range form 0 to 9999
	 * @throws IDOverFlowException occurred if the next id exceed the maximum value of 9999
	 */
	public static long getNextID() throws IDOverFlowException {
		idPointer++;
		if(idPointer > MAX) throw new IDOverFlowException("next id would exceed the current maximum from " + MAX);
		return idPointer;
	}

	/**
	 *
	 * @return true if there is any free id
	 */
	public static boolean hasNextID(){
		return idPointer < MAX;
	}
}
