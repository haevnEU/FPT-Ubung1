package core;

import interfaces.ISong;

import java.io.Serializable;
import java.util.ArrayList;
import java.rmi.RemoteException;

/**
 * This class is used as a temporary wrapper for serialization
 *
 * Written by Nils Milewski (nimile)
 */
public class RawList implements Serializable {

	private ArrayList<SongWrapper> songs;

	/**
	 * Convert local SongList into one which can be serialized.
	 *
	 * @param songList SongList which should be serialized
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
