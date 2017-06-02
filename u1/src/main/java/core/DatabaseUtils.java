package core;

import interfaces.ISong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class provides database functionality
 */
public final class DatabaseUtils {

	private Connection sqlConnection;

	private static DatabaseUtils instance;
	private DatabaseUtils(){}

	/**
	 * Get a DatabaseUtil object
	 * @return new DataBaseUtil object if there exist none otherwise it will returned the existing one
	 */
	public static DatabaseUtils getInstance() {
		if(instance == null) instance = new DatabaseUtils();
		return instance;
	}


	/**
	 * This static method creates a new database
	 * @return true if the database was created successfully
	 * @throws DataBaseException if the connection isn initalized
	 */
	public boolean createDatabase()	throws DataBaseException {
			if(sqlConnection != null) throw new DataBaseException();
			return true;
	}

	/**
	 * This static class inserts a new song into the database
	 * @param song which should be inserted
	 * @throws DataBaseException if the connection is not initialized
	 */
	public void insertSong(ISong song, String table) throws DataBaseException {
		if(sqlConnection != null) throw new DataBaseException();
		String path = song.getPath();
		String album = song.getAlbum();
		String title = song.getTitle();
		String id = "" + song.getId();
		String artist = song.getInterpret();

		try (PreparedStatement statement = sqlConnection.prepareStatement("INSERT INTO " + table + " (ID, Album , Title, Artist, Path) VALUES(?,?,?,?,?)")) {

			statement.setString(1, id);
			statement.setString(2, album);
			statement.setString(3, title);
			statement.setString(4, artist);
			statement.setString(5, path);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Could not insert Song, try again later");
			e.printStackTrace();
		}
	}

	/**
	 * This static class searched for a song with given ID
	 * @param ID song ID which should be used as searching parameter
	 * @return null if there exists none song with given ID
	 * @throws DataBaseException if the connection isn initalized
	 */
	public ISong findSongById(long ID) throws DataBaseException {
		if(sqlConnection == null) throw new DataBaseException();

		return null;
	}

	/**
	 * This static method deletes a song with given ID
	 * @param ID this ID represent the song which should be deleted
	 * @throws DataBaseException if the connection isn initalized
	 */
	public void deleteSong(long ID) throws DataBaseException {
		if(sqlConnection == null) throw new DataBaseException();

	}

	/**
	 * Opens a database connection
	 * @return true if the database could be opened
	 */
	public boolean openDB(String url) throws SQLException{
		sqlConnection = DriverManager.getConnection(url);
		return sqlConnection.isClosed();
	}

	/**
	 * Commits changes
	 * @throws SQLException if there is any problems while the commit process
	 */
	public void commit() throws SQLException, DataBaseException {
		if(sqlConnection == null) throw new DataBaseException();
		sqlConnection.commit();
	}

	/**
	 * Tidy up used resources
	 */
	public void destroy() throws DataBaseException {
		if(sqlConnection == null) throw new DataBaseException();
		try {
			// dirty solution but its guaranteed to be closed
			// skip if the connection is closed otherwise try to close and test again
			while(!sqlConnection.isClosed())sqlConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
