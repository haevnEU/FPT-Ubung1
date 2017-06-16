package core;

import core.Song;
import core.SongList;
import interfaces.ISerializableStrategy;
import interfaces.ISong;
import interfaces.ISongList;

import java.beans.Encoder;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Alex on 14.06.2017.
 */
public class XMLStrategy implements ISerializableStrategy { // you did not implement Serializable

	private SongList playList;
	private SongList songList;
	private FileOutputStream fos;
	private FileInputStream fis;
	private XMLEncoder encoder;
	private XMLDecoder decoder;
	private String playListPath, libraryPath;

	public XMLStrategy() {
	}

	public XMLStrategy(String playListPath, String libraryPath) {
		this.playListPath = playListPath;
		this.libraryPath = libraryPath;
	}

	@Override
	public void openWriteableSongs() throws IOException {
		fos = new FileOutputStream(libraryPath); //"XMLBeansSerSongs");
		encoder = new XMLEncoder(fos);
	}

	@Override
	public void openReadableSongs() throws IOException {
		fis = new FileInputStream(libraryPath); //"XMLBeansSerSongs");
		decoder = new XMLDecoder(fis);
	}

	@Override
	public void openWriteablePlaylist() throws IOException {
		fos = new FileOutputStream(playListPath); //"XMLBeansSerPlayList");
		encoder = new XMLEncoder(fos);
	}

	@Override
	public void openReadablePlaylist() throws IOException {
		fis = new FileInputStream(playListPath); //"XMLBeansSerPlayList");
		decoder = new XMLDecoder(fis);
	}

	@Override
	public void writeSong(ISong s) throws IOException {
		encoder.writeObject(new SongWrapper(s));
	}

	@Override
	public ISong readSong() throws IOException, ClassNotFoundException {
		SongWrapper s = (SongWrapper) decoder.readObject();
		return new Song(s.getPath(), s.getTitle(), s.getArtist(), s.getAlbum(), s.getId());
	}

	@Override
	public void closeWriteable() {
		encoder.flush();
		encoder.close();
	}

	@Override
	public void closeReadable() {
		decoder.close();
	}


	/**
	 * Get the deserialized playlist
	 *
	 * @return playlist
	 */
	public ISongList getPlayList() {
		try {
			playList = new SongList();
			// Theoretical this loop is leaved if there occurred an exception
			while (true) playList.add(readSong());
		} catch (IndexOutOfBoundsException ex) {
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println("[CRIT] IOException or Class not found occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
		return playList;
	}

	/**
	 * Get the deserialized library
	 *
	 * @return library
	 */
	public ISongList getLibrary() {
		try {
			songList = new SongList();
			// Theoretical this loop is leaved if there occurred an exception
			while (true) songList.add(readSong());
		} catch (IndexOutOfBoundsException ex) {
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println("[CRIT] IOException or Class not found occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
		return songList;
	}
}
