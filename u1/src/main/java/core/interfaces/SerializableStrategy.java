package core.interfaces;

import java.io.IOException;

/**
 * This interface ist used uhm yeah idk wtf is going?!
 * #hordeFTW #mercyForYou
 */
public interface SerializableStrategy {

    /*
      Setup Serializing the library
     */
    void openWriteableSongs() throws IOException;

    /*
      Setup Deserializing the library
     */
    void openReadableSongs() throws IOException;

    /*
      Setup Serializing the Playlist
     */
    void openWriteablePlaylist() throws IOException;

    /*
      Setup Deserializing the Playlist
     */
    void openReadablePlaylist() throws IOException;

    /*
      Write a song
     */
    void writeSong(Song s) throws IOException;

    /*
     Read a song
     */
    Song readSong() throws IOException, ClassNotFoundException;

    /*
     Finish writing/reading by closing all Streams
     */
    void closeReadable();

    void closeWriteable();

}
