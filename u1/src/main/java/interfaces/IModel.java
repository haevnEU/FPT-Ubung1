package interfaces;

import core.BinarySongList;
import core.RawList;
import core.SongList;
import core.SongWrapper;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IModel extends Remote{

	void skip() throws RemoteException;
	void stop() throws RemoteException;
	void togglePlayPause() throws RemoteException;
	boolean getPlaying() throws RemoteException;
	RawList getRawLibrary() throws RemoteException;
	void updateQueueView(RawList list) throws RemoteException;
	void addToQueue(SongWrapper song) throws RemoteException;
	void notifySongChange(SongWrapper song) throws RemoteException;
	RawList getRawQueue() throws RemoteException;
}
