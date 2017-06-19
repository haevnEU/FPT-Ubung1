package core;

import javax.persistence.*;

import view.EmptyView;
import interfaces.ISong;
import java.io.IOException;
import ApplicationException.DatabaseException;

/**
 * This class provides OpenJPA functionality
 * <p>
 * Created by Nils Milewsi (nimile) on 19.06.17
 */
public class OpenJPAStrategy implements interfaces.ISerializableStrategy {

	private LoginCredentials loginCredentials = null;
	private String dbPath = null;
	private SelectedSongList tableName;
	private EntityManagerFactory fac;
	private EntityManager e;

	private static OpenJPAStrategy instance;
	private OpenJPAStrategy(LoginCredentials loginCredentials, SelectedSongList tableName) throws DatabaseException {
		dbPath = EmptyView.getFile("SQL Database", "*.db").getPath();
		if(dbPath.contains(";"))throw new DatabaseException("SQL INJECTION DETECTED");
		System.out.println("[INFO] DB PATH: " + dbPath);
		this.loginCredentials = loginCredentials;
		this.tableName = tableName;
		System.out.println("[INFO] Working on table: " + this.tableName);
		System.out.println("[INFO] User: " + this.loginCredentials.getUsername() + " connected at " + Util.getUnixTimeStamp());
	}

	private OpenJPAStrategy(SelectedSongList tableName) throws DatabaseException {
		this.tableName = tableName;
		System.out.println("[INFO] Working on table: " + this.tableName);
		System.out.println("[INFO] User: anonymus connected at " + Util.getUnixTimeStamp());
	}

	/**
	 * Gets an instance of this strategy
	 * @param loginCredentials Login credentials which should be used
	 * @param tableName Tablename which should be worked with
	 * @return Instance
	 * @throws DatabaseException if there are malicious input
	 */
	public static OpenJPAStrategy getInstance(LoginCredentials loginCredentials, SelectedSongList tableName) throws DatabaseException {
		if(loginCredentials.getUsername().contains(";") || loginCredentials.getPw().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");
		if(instance == null) instance = new OpenJPAStrategy(loginCredentials,  tableName);
		return instance;
	}

	/**
	 * Gets an instance of this strategy
	 * @param tableName Tablename which should be worked with
	 * @return Instance
	 * @throws DatabaseException if there are malicious input
	 */
	public static OpenJPAStrategy getInstance(SelectedSongList tableName) throws DatabaseException {
		if(instance == null) instance = new OpenJPAStrategy(tableName);
		return instance;
	}

	// Opening stuff

	/**
	 * Opens a connection
	 */
	public void open(){
		System.out.println("[INFO] Opening DB using JPA");
		fac = Persistence.createEntityManagerFactory("openjpa", System.getProperties());
		e = fac.createEntityManager();
		if(dbPath != null){
			// TODO set DB-Path inside OpenJPA
			System.out.println("[INFO] Set DB to " + dbPath);
		}
		if(loginCredentials != null){
			// TODO set DB-Login credential inside OpenJPA
			System.out.println("[INFO] Set loging credentials to user \"" + loginCredentials.getUsername() + "\"");
		}

		System.out.println("[INFO] opened");
	}

	/**
	 * Consider using open
	 */
	@Override
	public void openWriteableSongs() throws IOException {
		System.out.println("[WARN] Legacy method called \"openWriteableSongs()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		open();
	}

	/**
	 * Consider using open
	 */
	@Deprecated
	@Override
	public void openWriteablePlaylist() throws IOException {
		System.out.println("[WARN] Legacy method called \"openWriteablePlaylist()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		open();
	}

	/**
	 * Consider using open
	 */
	@Deprecated
	@Override
	public void openReadableSongs() throws IOException {
		System.out.println("[WARN] Legacy method called \"openReadableSongs()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		open();
	}

	/**
	 * Consider using open
	 */
	@Deprecated
	@Override
	public void openReadablePlaylist() throws IOException {
		System.out.println("[WARN] Legacy method called \"openReadablePlaylist()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		open();
	}

	// Closing stuff

	/**
	 * Closes the connection
	 */
	public void close(){
		System.out.println("[INFO] Closing DB using JPA");
		if(e != null && e.isOpen()) e.close();
		if(fac != null && fac.isOpen()) fac.close();
		System.out.println("[INFO] Closed");
	}

	/**
	 * Consider using close
	 */
	@Deprecated
	@Override
	public void closeReadable() {
		System.out.println("[WARN] Legacy method called \"closeReadable()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		close();
	}

	/**
	 * Consider using close
	 */
	@Deprecated
	@Override
	public void closeWriteable() {
		System.out.println("[WARN] Legacy method called \"closeWriteable()\" at " + Util.getUnixTimeStamp());
		System.out.println("[INFO] Invoking method \"close\"");
		close();
	}

	// Logic stuff

	/**
	 * Writes a song into DB
	 */
	@Override
	public void writeSong(ISong s) throws IOException {
		open();
		System.out.println("[INFO] Write song \"" + s.getTitle() + "\". Starttime: " + Util.getUnixTimeStamp());
		EntityTransaction t = e.getTransaction();
		t.begin();
		try {
			e.persist(s);
			t.commit();
			System.out.println("[INFO] Added \"" + s.getTitle() + "\" successfully");
		}
		catch(Exception ex) {
			try {
				System.out.println("[INFO] \"" + s.getTitle() + "\" could not added, updating values");
				e.refresh(s);
				t.commit();
				System.out.println("[INFO] Values form \"" + s.getTitle() + "\" successfully updated");
			}
			catch (Exception ex2) {
				System.err.println("[CRIT] Exception occurred at writing \"" + s.getTitle() + "\" at " + Util.getUnixTimeStamp());
				ex.printStackTrace(System.err);
			}
		}

		System.out.println("[INFO] Write Committed and finished at " + Util.getUnixTimeStamp());
		close();
	}

	/**
	 * This method fetches Song data from a database with pregiven library-type
	 * @return Songlist which was fetched
	 */
	public SongList readSongs() {
		SongList s = new SongList();

		open();
		System.out.println("[INFO] Reading data from DB using JPA");
		System.out.println("[INFO] started at: " + Util.getUnixTimeStamp());
		EntityTransaction t = e.getTransaction();
		t.begin();

		// Legacy
		if (tableName == SelectedSongList.PlayList) tableName = SelectedSongList.PlayList;

		for (Object o : e.createQuery("SELECT x FROM " + tableName + " x").getResultList()) {
			System.out.println("[INFO] Reading started at " + Util.getUnixTimeStamp());

			System.out.println("[INFO] Adding to " + tableName + " Value: " + ((ISong) o).getTitle());
			switch (tableName) {
				case Library:
					s.add((new Library((ISong) o, true)).getSong());
					break;
				case PlayList:
					s.add((new PlayList((ISong) o, true)).getSong());
					break;
			}
			System.out.println("[INFO] Reading Done at: " + Util.getUnixTimeStamp());
		}

		t.commit();
		System.out.println("[INFO] Committed and Finished reading at " + Util.getUnixTimeStamp());
		close();
		return s;
	}




	// Stuff which is useless

	/**
	 * Not supported, returns each time it is called a null constant
	 */
	@Deprecated
	@Override
	public ISong readSong() throws IOException, ClassNotFoundException {
		System.err.println("[CRIT] Invoked ISong readSOng() at " + Util.getUnixTimeStamp());
		return null;
	}
}
