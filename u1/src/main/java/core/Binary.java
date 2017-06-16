package core;


import java.io.*;
import interfaces.*;

import java.rmi.RemoteException;

/**
 * Created by thahnen on 10.06.17.
 */
public class Binary implements interfaces.ISerializableStrategy {

	private ObjectOutputStream osSongs;
	private ObjectOutputStream osPl;
	private ObjectInputStream isSongs;
	private ObjectInputStream isPl;

	private String dateinameSongs, dateinamePl;

	private BinarySongList bsonglist;
	private BinarySongList bplaylist;

	boolean openSongsOut = false;
	boolean openSongsIn = false;
	boolean openPlOut = false;
	boolean openPlIn = false;


	/**
	 * Umwandlung von normaler SongList mit Properties in BinarySongList (serializing-ready)
	 * @param songDatei Dateiname der Datei wo die Songs abgespeichert werden (unnötig, nur wegen Aufgabenstellung dabei)
	 * @param plDatei Dateiname der Datei, wo die Playlist abgespeichert wird (nötig)
	 * @param songList Liste der Songs, die vom Musikplayer-Programm geladen werden!
	 * @param plList Playlist, die vom Musikplayer-Programm geladen werden!
	 * @throws RemoteException
	 */
	public Binary(String songDatei, String plDatei, SongList songList, SongList plList) throws RemoteException {
		
		this.dateinameSongs = songDatei;
		this.dateinamePl = plDatei;

		if (songList != null) this.bsonglist = new BinarySongList(songList);
		else this.bsonglist = null;

		if (plList != null)  this.bplaylist = new BinarySongList(plList);
		else  this.bplaylist = null;
	}


	@Deprecated
	@Override
	/**
	 * Passiert am Anfang, es werden die Songs-Datei geöffnet, zum lesen oder schreiben
	 * @throws IOException
	 */
	public void openWriteableSongs() throws IOException {
		try {
			this.osSongs = new ObjectOutputStream(new FileOutputStream(this.dateinameSongs));
			this.openSongsOut = true;
		} catch (IOException e) {
			this.openSongsOut = false;
		}
	}

	@Deprecated
	@Override
	public void openReadableSongs() throws IOException {
		try {
			this.isSongs = new ObjectInputStream(new FileInputStream(this.dateinameSongs));
			this.openSongsIn = true;
		} catch (IOException e) {
			this.openSongsIn = false;
		}
	}

	@Deprecated
	@Override
	/**
	 * Passiert am Anfang, es werden die Playlist-Datei geöffnet, zum lesen oder schreiben
	 * @throws IOException
	 */
	public void openWriteablePlaylist() throws IOException {
		try {
			this.osPl = new ObjectOutputStream(new FileOutputStream(this.dateinamePl));
			this.openPlOut = true;
		} catch (IOException e) {
			this.openPlOut = false;
		}
	}

	@Deprecated
	@Override
	public void openReadablePlaylist() throws IOException {
		try {
			this.isPl = new ObjectInputStream(new FileInputStream(this.dateinamePl));
			this.openPlIn = true;
		} catch (IOException e) {
			this.openPlIn = false;
		}
	}

	/**
	 * Wird aufgerufen, um etwas mit der Song-Datei zu tun, lesen oder schreiben
	 * @throws IOException
	 */
	public void writeSongs() throws IOException {
		// Entweder so, d.h. 2 Klassen greifen auf gleiche Datei zu!
		//  => oder datei schliessen, dann löschen, dann neuöffnen
		//  => Problem: this.closeWritable() schliesst einfach alle -> danke Interface!
		// PrintWriter datenLoescher = new PrintWriter(this.dateinameSongs);
		// datenLoescher.write("");
		// datenLoescher.close();
		openWriteableSongs();
		this.osSongs.writeObject(this.bsonglist);
		closeWriteable();
	}

	public SongList readSongs() throws IOException {
		// Aus Datei in bsongList einlesen

		// NOTE
		// Change a little bit
		// first change is the returning object, ive set bplaylist to null
		//      reason is that we can return it in the finally clause
		//      if there is something malicious with the class the object bplaylsit is never set to an actual value
		//      otherwise it will set to the input value
		openReadableSongs();
		bsonglist = null;
		// Added to test if the stream is open or closed
		if(!openSongsIn)return null;
		try {
			this.bsonglist = (BinarySongList) this.isSongs.readObject();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace(System.err);
		}finally {
			closeReadable();
			return bsonglist.getSongList();
		}
	}

	/**
	 * Wird aufgerufen, um etwas mit der Song-Datei zu tun, lesen oder schreiben
	 * @throws IOException
	 */
	public void writePl() throws IOException {
		openWriteablePlaylist();
		this.osPl.writeObject(this.bplaylist);
		closeWriteable();
	}

	public SongList readPl() throws IOException {
		// Aus Datei in blaylist einlesen

		// NOTE
		// Change a little bit
		// first change is the returning object, ive set bplaylist to null
		//      reason is that we can return it in the finally clause
		//      if there is something malicious with the class the object bplaylsit is never set to an actual value
		//      otherwise it will set to the input value
		openReadablePlaylist();
		bplaylist = null;                       // HOTCHANGE
		// Added to test if the stream is open or closed
		if(!openPlIn) return null;
		try {
			this.bplaylist = (BinarySongList) this.isPl.readObject();
			// return this.bplaylist.getSongList();                     // HOTCHANE
		} catch (ClassNotFoundException ex) {
			// this.closeReadable();                                    // HOTCHANE
			ex.printStackTrace(System.err);
			// return null;                                             // HOTCHANGE
		}
		finally {                                                       // HOTCHANGE
			closeReadable();                                            // HOTCHANGE
			return bplaylist.getSongList();                             // HOTCHANGE
		}                                                               // HOTCHANGE
	}


	@Deprecated
	@Override
	/**
	 * Das wird am Ende gemacht!
	 */
	public void closeReadable() {
		// NOTE
		// split closing into two different parts
		// reason could be found in the method below (closeWriteable())
		if (this.openSongsIn && isSongs != null)
		try { isSongs.close(); }
		catch (IOException ex) { ex.printStackTrace(System.err); }

		if(openPlIn && isPl != null)
		try { isPl.close(); }
		catch (IOException ex) {ex.printStackTrace(System.err);}
	}

	@Deprecated
	@Override
	public void closeWriteable() {  // REWORKED
		// NOTE
		// split closing into two different parts
		// closeReadable and closeWriteable were switched i have corrected it
		// First it is detecting if the Playlist is open and the writer exist
		if (this.openPlOut && osPl != null) {
			try { this.osPl.close(); }
			catch (Exception ex) { ex.printStackTrace(System.err); }
		}
		// after this it does the same thing with the library (songs)
		if(openSongsOut && osSongs != null)
			try { this.osSongs.close(); }
			catch (Exception ex) { ex.printStackTrace(System.err); }

	}


	@Deprecated
	private void loescheDateiInhalt(String dateiname) throws IOException {
		PrintWriter l = new PrintWriter(dateiname);
		l.write("");
		l.close();
	}

	@Deprecated
	@Override
	/**
	 * UNNÖTIG!
	 * @param s Ist der Song, der in die BinarySongList geschrieben wird
	 * @throws IOException
	 */
	public void writeSong(ISong s) throws IOException {
		throw new IOException();
	}

	@Deprecated
	@Override
	/**
	 * UNNÖTIG!
	 * @return Song aus der BinarySongList
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Song readSong() throws IOException, ClassNotFoundException {
		throw new IOException();
	}
}
