package core;

import interfaces.IModel;

import java.util.*;
import javafx.beans.property.*;

import java.io.File;

import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;

/**
 * This class provides fundamental logic
 */
public final class Model extends Observable implements IModel, Observer {
    private Locale locale;
    private SimpleBooleanProperty state;
    private SongList allSongs, queue;
    private Song nextSong;
    private Player player;

    /**
     * Constructor of this class
     */
    public Model(){
        allSongs = new SongList();
        queue = new SongList();
        locale = new Locale();
        state = new SimpleBooleanProperty(false);
        player = Player.getInstance();
        player.addObserver(this);
    }

    private void seekLengthChanged(double newLegnth) {
        System.out.println(newLegnth);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Set the nex playable song
     * @param nextSong song which should be played next
     */
    public void setNextSong(Song nextSong){
        this.nextSong = new Song(nextSong);
    }

    /**
     * Get the next playable song
     * @return next playable song
     */
    public Song getNextSong(){
        return nextSong;
    }

    /**
     * Current state as property
     * @return current state
     */
    public SimpleBooleanProperty stateProperty() {
        return state;
    }

    /**
     * Gets every localization string
     * @return localization object
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Every loaded song
     * @return every song as a ISongList object
     */
    public SongList getAllSongs(){
        return allSongs;
    }

    /**
     * Every song inside current queue
     * @return queue as a ISongList object
     */
    public SongList getQueue(){
        return queue;
    }

    /**
     * This method loads any mp3 file from hard disk, multi selection is possible
     */
    public void loadDirectory() {

        try {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
            List<File> selectedFiles = fc.showOpenMultipleDialog(null);
            if (selectedFiles == null) return;

            int counter = 0;

//         iterate over every file inside the directory
            for(File f : selectedFiles){
                Song song = new Song();
                Media m = new Media(f.toURI().toString());

                song.pathProperty().set(f.toURI().toString());
                song.idProperty().set(counter);
                song.titleProperty().set(m.getMetadata().getOrDefault("title",f.getName()).toString());
                song.interpretProperty().set(m.getMetadata().getOrDefault("artist","Unknown Interpret").toString());
                song.albumProperty().set(m.getMetadata().getOrDefault("album","Unknown album").toString());

                // adding metadata
                m.getMetadata().addListener(new MapChangeListener<String, Object>() {
                    @Override
                    public void onChanged(Change<? extends String, ?> change) {
                        String key = change.getKey();
                        if(key.equals("album")) song.albumProperty().set(change.getValueAdded().toString());
                        else if(key.equals("artist")) song.interpretProperty().set(change.getValueAdded().toString());
                        else if(key.equals("title")) song.titleProperty().set(change.getValueAdded().toString());
                        else if(key.equals("image")) song.setCover((Image)change.getValueAdded());
                    }
                });

                allSongs.add(song);
                counter++;
            }
        } catch (Exception ex) {
            System.err.println("[E] Exception occurred in core.Model.loadDirectory");
            System.err.println(" *  " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Plays any mp3 media
     */
    public void play(){
        player.play();
    }

    /**
     * Pause current playing mp3 media
     */
    public void pause(){
        player.pause();
    }

    /**
     * Set the first playable song => first queue element is assigned to nextSong variable
     */
    public void setFirstSong(){
        nextSong = (Song)queue.get(0);
        player.init(nextSong);
       }

    @Override
    public void update(Observable o, Object arg) {
       skip();
    }

    /**
     * Access volume property
     * @return volume property
     */
    public DoubleProperty volumeProperty(){
        return player.volumeProperty();
    }

    /**
     * Adds a time change listener
     * @param e method which should be invoked
     */
    public void addTimeChangeListener(ChangeListener<Duration> e){
        player.addTimeChangeListener(e);
    }

    /**
     * Adds on playing listener
     * @param value Runnable value which should be invoked
     */
    public void addOnPlaying(Runnable value){
        player.setOnPlaying(value);
    }

    /**
     * Add on ready listener
     * @param value Runnable value which should be invoked
     */
    public void addOnReady(Runnable value){
        player.setOnReady(value);
    }

    public double getLength(){
        return player.getLength();
    }

    public void skip() {
        if(getQueue().size() <= 1){
            player.stop();
            getQueue().remove(0);
            return;
        }
        getQueue().remove(0);
        nextSong = (Song)getQueue().get(0);
        player.skip(nextSong);

        setChanged();
        notifyObservers();
    }
}
