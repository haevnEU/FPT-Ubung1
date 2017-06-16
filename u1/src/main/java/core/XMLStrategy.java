package core;

import core.SongList;
import interfaces.ISerializableStrategy;
import interfaces.ISong;

import java.beans.Encoder;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alex on 14.06.2017.
 */
public class XMLStrategy implements ISerializableStrategy {

    private SongList playList;
    private SongList songList;
    private FileOutputStream fos;
    private FileInputStream fis;
    private XMLEncoder encoder;
    private XMLDecoder decoder;

    public XMLStrategy() {
    }

    public XMLStrategy(SongList playList, SongList songList) {
        this.playList = playList;
        this.songList = songList;
    }

    @Override
    public void openWriteableSongs() throws IOException {
        fos = new FileOutputStream("XMLBeansSerSongs");
        encoder = new XMLEncoder(fos);
    }

    @Override
    public void openReadableSongs() throws IOException {
        fis = new FileInputStream("XMLBeansSerSongs");
        decoder = new XMLDecoder(fis);
    }
    @Override
    public void openWriteablePlaylist() throws IOException {
        fos =  new FileOutputStream("XMLBeansSerPlayList");
        encoder = new XMLEncoder(fos);
    }

    @Override
    public void openReadablePlaylist() throws IOException {
        fis = new FileInputStream("XMLBeansSerPlayList");
        decoder = new XMLDecoder(fis);
    }

    @Override
    public void writeSong(ISong s) throws IOException {

        encoder.writeObject(s);
    }

    @Override
    public ISong readSong() throws IOException, ClassNotFoundException {
        return (ISong) decoder.readObject();
    }

    @Override
    public void closeReadable() {
        encoder.flush();
        encoder.close();
    }

    @Override
    public void closeWriteable() {
        decoder.close();
    }

}
