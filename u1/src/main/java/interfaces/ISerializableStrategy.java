package interfaces;

import java.io.IOException;

public interface ISerializableStrategy {


    /*
      Setup Serializing the library
     */
    void openWriteableSongs() throws IOException;

    /*
      Setup Deserializing the library
     */
    void openReadableSongs() throws IOException;

    /*
      Setup Serializing the PlayList
     */
    void openWriteablePlaylist() throws IOException;

    /*
      Setup Deserializing the PlayList
     */
    void openReadablePlaylist() throws IOException;

    /*
      Write a song
     */
    void writeSong(ISong s) throws IOException;

    /*
     Read a song
     */
    ISong readSong() throws IOException, ClassNotFoundException;

    /*
     Finish writing/reading by closing all Streams
     */
    void closeReadable();

    void closeWriteable();

}
