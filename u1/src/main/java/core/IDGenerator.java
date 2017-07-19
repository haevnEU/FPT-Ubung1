package core;

import applicationException.IDOverFlowException;
import applicationException.UnknownApplicationException;

public final class IDGenerator {

	// No instances allowed
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
	@SuppressWarnings("WeakerAccess")
	public static long getNextID() throws IDOverFlowException {
		idPointer++;
		if(idPointer > MAX) throw new IDOverFlowException("Next id would exceed the current maximum from " + MAX);
		System.out.println("[WARN][IDGenerator] Increased idPointer value: " + idPointer);
		return idPointer;
	}

	/**
	 * @return true if there is any free id
	 */
	@SuppressWarnings("unused")
	public static boolean hasNextID(){
		return idPointer < MAX;
	}

	/**
	 * Checks if an ID already exists
	 * @param id id which should be checked
	 * @return true if the id exists => id is smaller than current idPointer
	 */
	public static boolean existsID(Long id){
		return id < idPointer;
	}

	/**
	 * Set the current maximum
	 * @param id new maximum
	 * @throws UnknownApplicationException if id is doubled
	 */
	@SuppressWarnings("WeakerAccess")
	public static void addId(long id) throws UnknownApplicationException{
		if(idPointer > id) throw new UnknownApplicationException("ID already exists");
		idPointer = id;
		System.out.println("[WARN][IDGenerator] maximum set to " + id);
	}
}
