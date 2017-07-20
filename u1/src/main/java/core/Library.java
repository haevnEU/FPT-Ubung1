package core;

import interfaces.ISong;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;

/**
 * This class is a temporary used class to stroe songs in a DB using OpenJPA
 *
 * Created by Nils Milewsi (nimile) on 19.06.17
 */
@Entity
@Table(name = "Library")
public class Library implements ISong, java.io.Serializable {

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
	public Library(){}

	public Library(ISong s, boolean isEncoded){
		if(isEncoded){
			this.path = Util.convertToString(s.getPath());
			this.title = Util.convertToString(s.getTitle());
			this.interpret = Util.convertToString(s.getInterpret());
			this.album = Util.convertToString(s.getAlbum());
			this.id = s.getId();

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
		return new Song(getPath(), getTitle(), getInterpret(), getAlbum(), getId());
	}

}
