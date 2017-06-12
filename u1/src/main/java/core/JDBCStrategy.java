package core;

import java.io.*;
import java.sql.*;

import interfaces.ISong;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import view.LoadFiles;

import javax.imageio.ImageIO;

public class JDBCStrategy implements interfaces.ISerializableStrategy {

	private SongList songList;
	private LoginCredentials loginCredentials;
	private String tableName, dbPath;



	public JDBCStrategy(LoginCredentials loginCredentials, SongList songList, String tableName) {

		dbPath = LoadFiles.getFile("SQL Database", "*.db").getPath();
		System.out.println("[INFO] DB PATH: " + dbPath);
		this.songList = songList;
		this.loginCredentials = loginCredentials;
		this.tableName = tableName;
		System.out.println("[INFO] Working on table: " + this.tableName);
		System.out.println("[INFO] User: " + this.loginCredentials.getUsername() + " connected at " + Util.getUnixTimeStamp());
	}

	@Override
	public void openWriteableSongs() throws IOException {}

	@Override
	public void openReadableSongs() throws IOException {}

	@Override
	public void openWriteablePlaylist() throws IOException {}

	@Override
	public void openReadablePlaylist() throws IOException {}

	@Override
	public void writeSong(ISong s) throws IOException {

		try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + dbPath, loginCredentials.getUsername(), loginCredentials.getPw())) {
			System.out.println("[INFO] Connection opened at " + Util.getUnixTimeStamp());
			try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (ID, Title, Artist, Album, Path, Cover) VALUES(?, ?, ?, ?, ?, ?)")) {
				System.out.println("[INFO] Created prepared statement \"INSERT INTO \"" + tableName + "\" (ID, Title, Artist, Album, Path, Cover) VALUES(?, ?, ?, ?, ?, ?)\"");
				String title = s.getTitle();
				String artist = s.getInterpret();
				String album = s.getAlbum();
				String path = s.getPath();

				pstmt.setLong(1, s.getId());
				pstmt.setString(2, title);
				pstmt.setString(3, artist);
				pstmt.setString(4, album);
				pstmt.setString(5, path);

				System.out.println("[INFO] Values added");

				if(((Song)s).getCover() != null) {
					System.out.println("[INFO] found cover prepare cover for db");
					// Load the Image into a Java FX Image Object //

					Image img = ((Song)s).getCover();

					// Cache Width and Height to 'int's (because getWidth/getHeight return Double) and getPixels needs 'int's //

					int w = (int)img.getWidth();
					int h = (int)img.getHeight();

					// Create a new Byte Buffer, but we'll use BGRA (1 byte for each channel) //

					byte[] buf = new byte[w * h * 4];

					img.getPixelReader().getPixels(0, 0, w, h, PixelFormat.getByteBgraInstance(), buf, 0, w * 4);

					InputStream inputStream = new ByteArrayInputStream(buf);
					pstmt.setBinaryStream(6, (InputStream) inputStream, (int) (buf.length));
					System.out.println("[INFO] Cover added to db");
				}
				pstmt.executeUpdate();
				System.out.println("[INFO] executed query");
			} catch (SQLException ex) {
				System.err.println("[EXCEPTION] SQL Exception thrown at " + Util.getUnixTimeStamp());
				System.err.println("\tStatement: " + ex.getSQLState());
				System.err.println("\tMessage: " + ex.getMessage());
				ex.printStackTrace();
			}
		} catch (SQLException ex) {
			System.err.println("[EXCEPTION] SQL Exception thrown at " + Util.getUnixTimeStamp());
			System.err.println("\tStatement: " + ex.getSQLState());
			System.err.println("\tMessage: " + ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println("[INFO] Finished query at " +Util.getUnixTimeStamp() );
	}

	@Override
	public ISong readSong() throws IOException, ClassNotFoundException {
		return null;
	}

	@Override
	public void closeReadable() {}

	@Override
	public void closeWriteable() {}
}