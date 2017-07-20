package core;

import java.util.*;
import javax.persistence.*;

import interfaces.ISong;
import java.io.IOException;
import applicationException.DatabaseException;
import org.apache.openjpa.persistence.OpenJPAPersistence;

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
	private OpenJPAStrategy(LoginCredentials loginCredentials, String dbPath,SelectedSongList tableName) throws DatabaseException {
		System.out.println("[INFO] DB PATH: " + dbPath);
		this.loginCredentials = loginCredentials;
		this.tableName = tableName;
		this.dbPath = dbPath;
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
	public static OpenJPAStrategy getInstance(LoginCredentials loginCredentials, String dbPath, SelectedSongList tableName) throws DatabaseException {
		if(loginCredentials.getUsername().contains(";") || loginCredentials.getPw().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");
		if(instance == null) instance = new OpenJPAStrategy(loginCredentials, dbPath,  tableName);
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
	private void open(){
		System.out.println("[INFO] Opening DB using JPA");
		fac = Persistence.createEntityManagerFactory("openjpa", System.getProperties());

        // if the custom path is enabled this should be executed
        // => Allows to add custom path and username / PW for DB Access -> DB Server access
		if(Model.isCustomDBFeaturesEnabled()) {
		    Map<String, String> map = new HashMap<String, String>();
            map.put("openjpa.ConnectionURL", "jdbc:sqlite:" + dbPath);
			map.put("openjpa.ConnectionDriverName", "org.sqlite.JDBC");
			map.put("openjpa.ConnectionUserName", loginCredentials.getUsername());
			map.put("openjpa.ConnectionPassword", loginCredentials.getPw());
			map.put("openjpa.RuntimeUnenhancedClasses", "supported");
			map.put("openjpa.jdbc.SynchronizeMappings", "false");

			List<Class<?>> types = new ArrayList<Class<?>>();
			types.add(PlayList.class);
			types.add(Library.class);

			if (!types.isEmpty()) {
				StringBuilder buf = new StringBuilder();
				for (Class<?> c : types) {
					if (buf.length() > 0)
						buf.append(";");
					buf.append(c.getName());
				}
				// <class>Producer</class>
				map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString() + ")");
			}

			fac = OpenJPAPersistence.getEntityManagerFactory(map);
		}
		e = fac.createEntityManager();
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
	private void close(){
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
