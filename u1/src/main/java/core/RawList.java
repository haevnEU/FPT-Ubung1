package core;

import interfaces.ISong;

import java.io.Serializable;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class RawList implements Serializable {

	private ArrayList<SongWrapper> songs;

	/**
	 * SongList mit ObservableValues in serialisierbare Version umwandeln.
	 *
	 * @param songList
	 */
	public RawList(SongList songList) throws RemoteException {
		this.songs = new ArrayList<>();
		for (ISong item : songList.getList()) this.songs.add(new SongWrapper(item));
	}


	public SongList getSongList() {
		ArrayList<interfaces.ISong> nsongs = new ArrayList<>();
		for (SongWrapper item : this.songs)
			nsongs.add(new core.Song(item.getPath(), item.getTitle(), item.getArtist(), item.getAlbum(), item.getId()));

		return new SongList(nsongs);
	}
}
