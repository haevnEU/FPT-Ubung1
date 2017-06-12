package core.util.serializingStrategy;

import core.interfaces.Song;
import core.util.SongList;

import java.io.*;
import java.rmi.RemoteException;

/**
 * Created by thahnen on 10.06.17.
 */
public class Binary implements core.interfaces.SerializableStrategy {

    ObjectOutputStream osSongs;
    ObjectOutputStream osPl;
    ObjectInputStream isSongs;
    ObjectInputStream isPl;

    String dateinameSongs, dateinamePl;

    BinarySongList bsonglist;
    BinarySongList bplaylist;

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
        // Handlen, dass nix leeres abgespeichert werden kann! Muss eigentlich woanders passieren

        this.dateinameSongs = songDatei;
        this.dateinamePl = plDatei;

        if (songList != null) { this.bsonglist = new BinarySongList(songList);
        } else { this.bsonglist = null; }
        if (plList != null) { this.bplaylist = new BinarySongList(plList);
        } else { this.bplaylist = null; }
    }


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

    public void openReadableSongs() throws IOException {
        try {
            this.isSongs = new ObjectInputStream(new FileInputStream(this.dateinameSongs));
            this.openSongsIn = true;
        } catch (IOException e) {
            this.openSongsIn = false;
        }
    }

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
        PrintWriter datenLoescher = new PrintWriter(this.dateinameSongs);
        datenLoescher.write("");
        datenLoescher.close();
        this.osSongs.writeObject(this.bsonglist);
    }

    public SongList readSongs() throws IOException {
        // Aus Datei in bsongList einlesen
        try {
            this.bsonglist = (BinarySongList) this.isSongs.readObject();
            return this.bsonglist.getSongList();
        } catch (ClassNotFoundException e) {
            this.closeReadable();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Wird aufgerufen, um etwas mit der Song-Datei zu tun, lesen oder schreiben
     * @throws IOException
     */
    public void writePl() throws IOException {
        this.osPl.writeObject(this.bplaylist);
    }

    public SongList readPl() throws IOException {
        // Aus Datei in blaylist einlesen
        try {
            this.bplaylist = (BinarySongList) this.isPl.readObject();
            return this.bplaylist.getSongList();
        } catch (ClassNotFoundException e) {
            this.closeReadable();
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Das wird am Ende gemacht!
     */
    public void closeReadable() {
        if (this.openSongsIn || this.openPlIn) {
            try {
                osSongs.close();
                osPl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeWriteable() {
        if (this.openSongsOut || this.openPlOut) {
            try {
                isSongs.close();
                isPl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void loescheDateiInhalt(String dateiname) throws IOException {
        PrintWriter l = new PrintWriter(dateiname);
        l.write("");
        l.close();
    }


    /**
     * UNNÖTIG!
     * @param s Ist der Song, der in die BinarySongList geschrieben wird
     * @throws IOException
     */
    public void writeSong(Song s) throws IOException {
        throw new IOException();
    }

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
