package view;

import interfaces.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.beans.property.*;

import javafx.scene.input.MouseEvent;

import java.time.Duration;

/**
 * This class provides the main view, it is the main stage
 */
public class MainView extends BorderPane implements interfaces.IView{


    private static MainView instance;
    /**
     * Get the instance of view.MainView
     * @return view.MainView instance
     */
    public static MainView getInstance() {
        if(instance != null) return null;
        instance = new MainView();
        return instance;
    }
    @Override
    public void destroy() {
        instance = null;
    }

    private Label lbArtist, lbAlbum, lbTitle;
    private ImageView imgCover;
    private Button btDelete, btSkip;
    private ToggleButton btPlayPause;
    private Slider sliderPosition;
    private ListView listViewQueue;
    private SimpleDoubleProperty spacing = new SimpleDoubleProperty(10);
    private SimpleDoubleProperty padding = new SimpleDoubleProperty(10);

    /**
     * Initialize routine
     * <b>This routine must be called</b>
     */
    public void init(){
        try{
            // each text is bind later
            lbArtist = new Label("--");
            lbAlbum = new Label("--");
            lbTitle = new Label("--");
            imgCover = new ImageView();
            btDelete = new Button("--");
            btSkip = new Button("--");
            btPlayPause = new ToggleButton("--");
            listViewQueue = new ListView();
            sliderPosition = new Slider();

            setPadding(new Insets(padding.doubleValue()));


            VBox topControl = new VBox(lbTitle, lbAlbum,lbArtist);
            topControl.setSpacing(10);
            topControl.setAlignment(Pos.CENTER);
            VBox centerControl = new VBox(imgCover, topControl);
            centerControl.setAlignment(Pos.CENTER);
            centerControl.setSpacing(10);
            setCenter(centerControl);
            VBox leftControl = new VBox(listViewQueue, btDelete);
            leftControl.setSpacing(10);
            setLeft(leftControl);


            HBox buttonControl = new HBox(btPlayPause,btSkip);
            buttonControl.setPadding(new Insets(padding.doubleValue()));
            buttonControl.setSpacing(spacing.doubleValue());
            buttonControl.setAlignment(Pos.CENTER);
            VBox bottom = new VBox(buttonControl, sliderPosition);
            setBottom(bottom);



        } catch (Exception ex){
            System.err.println("[E] Exception occurred in view.MainView.init");
            System.err.println(" *  " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Set the view of songs to an observable value
     * @param songs ISongList which should be uses as binding
     */
    public void bindListViewQueue(ISongList songs){
        listViewQueue.itemsProperty().set(songs);
    }

    // Following: adding event handling and text properties

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event is fired
     */
    public void addListViewQueueClickedEventHandler(EventHandler<MouseEvent> eventHandler){
        listViewQueue.setOnMouseClicked(eventHandler);
    }

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event is fired
     */
    public void addButtonDeleteCLickEventHandler(EventHandler<ActionEvent> eventHandler){
        btDelete.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event is fired
     */
    public void addButtonSkipClickEventHandler(EventHandler<ActionEvent> eventHandler){
        btSkip.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event is fired
     */
    public void addButtonPlayPauseClickEventHandler(EventHandler<ActionEvent> eventHandler){
        btPlayPause.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Bind the button to a observable value
     * @param text which should be used as binding
     */
    public void bindButtonDeleteText(SimpleStringProperty text){
        btDelete.textProperty().bind(text);
    }


    // Adding text to each control

    /**
     * Bind the button to a observable value
     * @param text which should be used as binding
     */
    public void bindButtonSkipText(SimpleStringProperty text){
        btSkip.textProperty().bind(text);
    }

    /**
     * Bind the button to a observable value
     * @param text which should be used as binding
     */
    public void bindButtonPlayPauseText(SimpleStringProperty text){
        btPlayPause.textProperty().bind(text);
    }

    /**
     * Bind the label to a observable value
     * @param text which should be used as binding
     */
    public void bindLabelInterpretText(SimpleStringProperty text){
        lbArtist.textProperty().bind(text);
    }

    /**
     * Bind the label to a observable value
     * @param text which should be used as binding
     */
    public void bindLabelAlbumText(SimpleStringProperty text){
        lbAlbum.textProperty().bind(text);
    }

    /**
     * Bind the label to a observable value
     * @param text which should be used as binding
     */
    public void bindLabelTitleText(SimpleStringProperty text){
        lbTitle.textProperty().bind(text);
    }

    public void bindImageCover(Image image){
        imgCover.setImage(image);
    }

    /**
     * Bind the maximum to a observable value
     * @param max which should be used as binding
     */
    public void bindSliderSeekMaximum(SimpleDoubleProperty max){
        sliderPosition.maxProperty().bind(max);
    }

    /**
     * Bind the seek position to a observable value.
     * Note this value ist bind as bidirectional that means this slider can change the value
     * @param volume which should be used as binding
     */
    public void bindSliderSeekPosition(ReadOnlyObjectProperty<Duration> volume){

    }

    // normal methods

    /**
     * Get the selected Item as ISong object
     * @return selected ISong
     */
    public ISong getSelectedQueueItem() {
        return (ISong)listViewQueue.getSelectionModel().getSelectedItem();
    }

    /**
     * Updates the UI
     * @param currentSeek Current position inside the track
     */
    public void update(double currentSeek){
        this.sliderPosition.setValue(currentSeek);
    }

    /**
     * Set the new maximum of the seek slider
     * @param sliderMax new maximum for the seek slider
     */
    public void setSliderSeekMax(double sliderMax){
        sliderPosition.setMax(sliderMax);
    }
}
