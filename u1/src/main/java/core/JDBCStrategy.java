package core;

import java.io.*;
import java.sql.*;
import applicationException.*;

import interfaces.ISong;
import javafx.scene.control.Alert;

/**
 * This class provides database access using JDBC
 *
 * written by Nils Milewski (nimile)
 */
public class JDBCStrategy implements interfaces.IDatabaseUtils {

	private LoginCredentials loginCredentials;
	private String tableName, dbPath;

	public static JDBCStrategy getInstance(LoginCredentials loginCredentials, SelectedSongList tableName, String dbPath) throws DatabaseException{
		if(loginCredentials.getUsername().contains(";") || loginCredentials.getPw().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");
		return new JDBCStrategy(loginCredentials,  tableName, dbPath);
	}

	@SuppressWarnings("unused")
	private JDBCStrategy(){}

	/**
	 * Internal usage
	 * @param loginCredentials user infos
	 * @param tableName table name which should be used as worling table
	 * @throws DatabaseException DBException if there is any malicious actions
	 */
	private JDBCStrategy(LoginCredentials loginCredentials, SelectedSongList tableName, String dbPath) throws DatabaseException {
		this.dbPath = dbPath;
		System.out.println("[INFO] DB PATH: " + dbPath);
		this.loginCredentials = loginCredentials;
		this.tableName = tableName.toString();
		System.out.println("[INFO] Working on table: " + this.tableName);
		System.out.println("[INFO] User: " + this.loginCredentials.getUsername() + " connected at " + Util.getUnixTimeStamp());
	}


	/**
	 * Saves a Song List from database
	 * @throws SQLException if there exist any problems with the database
	 * @throws DatabaseException General database exception => not specified
	 * @throws IOException General IO exception => not specified
	 */
	public void writeSongList(SongList songList) throws SQLException, DatabaseException, IOException {
		for (interfaces.ISong s : songList) {
			writeSong(s);
		}
	}

	@Override
	public void writeSong(ISong s) throws IOException {

		// try-resource is used to minimize access problems => not closing after executing
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbPath, loginCredentials.getUsername(), loginCredentials.getPw())) {
			System.out.println("[INFO] Connection opened at " + Util.getUnixTimeStamp());
			// same as above try-resource is used to minimize problems
			try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (ID, Title, Artist, Album, Path) VALUES(?, ?, ?, ?, ?)")) {
				System.out.println("[INFO] Created prepared statement \"INSERT INTO \"" + tableName + "\" (ID, Title, Artist, Album, Path) VALUES(?, ?, ?, ?, ?)\"");

				pstmt.setLong(1, s.getId());
				pstmt.setString(2, Util.convertToHex(s.getTitle()));
				pstmt.setString(3, Util.convertToHex(s.getInterpret()));
				pstmt.setString(4, Util.convertToHex(s.getAlbum()));
				pstmt.setString(5, Util.convertToHex(s.getPath()));

				System.out.println("[INFO] Values added");
				pstmt.executeUpdate();
				System.out.println("[INFO] executed query");
			} catch (SQLException ex) {
				// insert has failed => maybe values changed
				try (PreparedStatement pstmt = con.prepareStatement("UPDATE " + tableName + " SET ID = ?, Title = ?, Artist = ?, Album = ?, Path = ? WHERE ID=" + s.getId())) {
					System.out.println("[INFO] Created prepared statement \"UPDATE " + tableName + "SET ID = ?, Title = ?, Artist = ?, Album = ?, Path = ? WHERE ID = " + s.getId() + "\"");

					pstmt.setLong(1, s.getId());
					pstmt.setString(2, Util.convertToHex(s.getTitle()));
					pstmt.setString(3, Util.convertToHex(s.getInterpret()));
					pstmt.setString(4, Util.convertToHex(s.getAlbum()));
					pstmt.setString(5, Util.convertToHex(s.getPath()));

					pstmt.executeUpdate();
					System.out.println("[INFO] Values updated");
				} catch (SQLException ex2) {
					// nope ==> another db exception which must be thrown
					System.err.println("[EXCEPTION] SQL Exception thrown at " + Util.getUnixTimeStamp());
					System.err.println("\tStatement: " + ex2.getSQLState());
					System.err.println("\tMessage: " + ex2.getMessage());
					ex.printStackTrace(System.err);
					Util.showAlert("Database exception occurred.", Alert.AlertType.INFORMATION);
				}
			}
		} catch (SQLException ex) {
			// ==> this is a connection error
			System.err.println("[EXCEPTION] SQL Exception thrown at " + Util.getUnixTimeStamp());
			System.err.println("\tStatement: " + ex.getSQLState());
			System.err.println("\tMessage: " + ex.getMessage());
			ex.printStackTrace(System.err);
			Util.showAlert("Database exception occurred.", Alert.AlertType.INFORMATION);
		}
		System.out.println("[INFO] Finished query at " + Util.getUnixTimeStamp());
	}

	/**
	 * Reads a Song List from database
	 * @return Song list
	 * @throws SQLException if there exist any problems with the database
	 * @throws DatabaseException General database exception => not specified
	 */
	public SongList readTable() throws SQLException, DatabaseException {
		// documentation above inside writeSong
		SongList ret = new SongList();
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbPath, loginCredentials.getUsername(), loginCredentials.getPw())) {
			System.out.println("[INFO] Connection opened at " + Util.getUnixTimeStamp());

			try (PreparedStatement stmt = con.prepareStatement("SELECT ID , Title , Artist, Album, Path FROM " + tableName)) {
				System.out.println("[INFO] Reading data from db");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Long id = rs.getLong("ID");
					String title = Util.convertToString(rs.getString("Title"));
					String interpret = Util.convertToString(rs.getString("Artist"));
					String album = Util.convertToString(rs.getString("Album"));
					String path = Util.convertToString(rs.getString("Path"));

					Song s = new Song(path, title, interpret, album, id);
					System.out.println("[INFO] Found: " + s.toString());
					ret.add(s);
				}
				System.out.println("[INFO] Finished query at " + Util.getUnixTimeStamp());
				return ret;
			}
		}
	}

	@Deprecated
	@Override
	public void openWriteableSongs() throws IOException {}


	@Deprecated
	@Override
	public void openReadableSongs() throws IOException {}

	@Deprecated
	@Override
	public void openWriteablePlaylist() throws IOException {}

	@Deprecated
	@Override
	public void openReadablePlaylist() throws IOException {}

	@Deprecated
	@Override
	public void closeReadable() {}

	@Deprecated
	@Override
	public void closeWriteable() {}

	@Deprecated
	@Override
	public ISong readSong() throws IOException, ClassNotFoundException {
		throw new IOException();
	}

}