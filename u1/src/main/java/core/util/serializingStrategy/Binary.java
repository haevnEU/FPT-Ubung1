package core.util.serializingStrategy;

import core.interfaces.Song;
import core.util.SongList;

import java.io.*;

/**
 * Created by thahnen on 10.06.17.
 */
public class Binary implements core.interfaces.SerializableStrategy {

    /**
     * Mal gucken was davon ich nehme!
     */
    FileOutputStream os;
    FileInputStream is;
    boolean fileType; // True -> Input, False -> Output

    public Binary(String datei, SongList songList) {

    }

    /*
      Setup Serializing the library
     */
    public void openWriteableSongs() throws IOException {

    }

    /*
      Setup Deserializing the library
     */
    public void openReadableSongs() throws IOException {

    }

    /*
      Setup Serializing the Playlist
     */
    public void openWriteablePlaylist() throws IOException {

    }

    /*
      Setup Deserializing the Playlist
     */
    public void openReadablePlaylist() throws IOException {

    }

    /*
      Write a song
     */
    public void writeSong(Song s) throws IOException {

    }

    /*
     Read a song
     */
    public Song readSong() throws IOException, ClassNotFoundException {
        throw new IOException();
    }

    /*
     Finish writing/reading by closing all Streams
     */
    public void closeReadable() {

    }

    public void closeWriteable() {

    }
}
