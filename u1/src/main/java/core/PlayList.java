package core;

import javax.persistence.*;

import interfaces.ISong;
import java.io.Serializable;
import javafx.beans.value.ObservableValue;


/**
 * This class provides
 * <p>
 * Created by Nils Milewsi (nimile) on 19.06.17
 */
@Entity
@Table(name = "PlayList")
public class PlayList implements ISong, Serializable{

	private static final long serialVersionUID = 0xa47cd6a4c169076bL;

	@Column(name = "Title")
	private String title;

	@Column(name = "Path")
	private String path;

	@Column(name = "Artist")
	private String interpret;

	@Column(name = "Album")
	private String album;

	@Id
	@Column(name = "ID")
	private Long id;


	@SuppressWarnings("unused")
	public PlayList() {}

	public PlayList(ISong s, boolean isEncoded){
		if(isEncoded){
			path = Util.convertToString(s.getPath());
			title = Util.convertToString(s.getTitle());
			interpret = Util.convertToString(s.getInterpret());
			album = Util.convertToString(s.getAlbum());
			id = s.getId();

		}else {
			this.title = Util.convertToHex(s.getTitle());
			this.path = Util.convertToHex(s.getPath());
			this.interpret = Util.convertToHex(s.getInterpret());
			this.album = Util.convertToHex(s.getAlbum());
			this.id = s.getId();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getId() {
		return id;
	}


	@Transient
	@Override
	public ObservableValue<String> pathProperty() {
		return null;
	}

	@Transient
	@Override
	public ObservableValue<String> albumProperty() {
		return null;
	}

	@Transient
	@Override
	public ObservableValue<String> interpretProperty() {
		return null;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ISong getSong(){
		return new Song(getPath(), getTitle(), getInterpret(),getAlbum(), getId());
	}

}
