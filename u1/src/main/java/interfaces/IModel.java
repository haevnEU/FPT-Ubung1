package interfaces;

import core.RawList;
import core.SongWrapper;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used for future models
 *
 * written by Nils Milewski (nimile)
 */
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
	void notifyPlay() throws RemoteException;
	void notifyPause() throws RemoteException;
}
