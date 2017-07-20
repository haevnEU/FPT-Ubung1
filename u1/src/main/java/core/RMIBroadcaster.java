package core;

import interfaces.IModel;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This class provides broadcasting over RMI, required connected clients
 * <p>
 * Created by Nils Milewsi (nimile) on 16.07.17
 */
class RMIBroadcaster {
	private List<IModel> clients;
	private Model model;
	private RMIBroadcaster(){}

	RMIBroadcaster(List<IModel> clients, Model model) {
		this();
		this.clients = clients;
		this.model = model;
	}

	void broadcastQueueChange(){
		// TODO Call Stub.updateQueueVIew
		for(IModel s : clients){
			try {
				s.updateQueueView(new RawList(model.getQueue()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	void broadcastSongChange(SongWrapper song){
		for(IModel s : clients){
			try{
				s.notifySongChange(song);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	void broadcastPlayPause(boolean b) {
		for(IModel s : clients){
			try {
				if(b)s.notifyPlay();
				else s.notifyPause();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
