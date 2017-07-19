package core;

import interfaces.IModel;

import javax.swing.text.html.ListView;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class provides
 * <p>
 * Created by Nils Milewsi (nimile) on 16.07.17
 */
public class RMIBroadcaster {
	List<IModel> clients;
	Model model;
	private RMIBroadcaster(){}

	public RMIBroadcaster(List<IModel> clients, Model model){
		this.clients = clients;
		this.model = model;
	}
	public void broadcastQueueChange(){
		// TODO Call Stub.updateQueueVIew
		for(IModel s : clients){
			try {
				s.updateQueueView(new RawList(model.getQueue()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void broadcastSongChange(SongWrapper song){
		for(IModel s : clients){
			try{
				s.notifySongChange(song);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


}
