package server;

import core.Model;
import core.CVars;

import java.net.*;
import java.io.IOException;

/**
 * This class provides
 * <p>1^^   ^
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
public final class UDPServer implements interfaces.IServer {

	private Model model;

	// TODO REPLACE ?!
	private OwnSyncArrayList log;

	private UDPServer() {}

	UDPServer(OwnSyncArrayList log, Model m){
		this();
		this.log = log;
		this.model = m;
	}

	@Override
	public void run() {
		try{
			DatagramSocket serverSocket = new DatagramSocket(CVars.getTimePort(), InetAddress.getByName(CVars.getServerIp()));
		log.add("[INFO] Time server listen on IP: " + InetAddress.getByName(CVars.getServerIp()) + " at " + CVars.getTimePort());
				while(true){
				byte[] buffer = new byte["{CMD:TIME}".length()];
				DatagramPacket  packet = new DatagramPacket(buffer, buffer.length -1);
				try{
					serverSocket.receive(packet);
					UDPRunner client = new UDPRunner(packet, serverSocket, model, log);
					Thread handle = new Thread(client, "udp-client");
					handle.setDaemon(true);
					handle.start();
				} catch (IOException e) {
					log.add("[CIRT] IO Exception occurred in " + this.getClass().getSimpleName());
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException | SocketException e) {
			log.add("[SYS CRIT] Fatal error occurred in " + this.getClass().getSimpleName() + " the server is possible offline!");
			e.printStackTrace();
		}

	}
}

final class UDPRunner implements  Runnable{

	private DatagramPacket packet;
	private DatagramSocket socket;
	private Model model;

	// TODO REPLACE ?!
	private OwnSyncArrayList log;

	private UDPRunner(){}

	UDPRunner(DatagramPacket packet, DatagramSocket serverSocket, Model model, OwnSyncArrayList log) {
		this();

		this.socket = serverSocket;
		this.packet = packet;
		this.model = model;
		this.log = log;
	}


	@Override
	public void run() {
		InetAddress clientIP = packet.getAddress();
		int clientPort = packet.getPort();
		try{
			StringBuilder builder = new StringBuilder();
			for(byte b : packet.getData()) builder.append((char)b);

			log.add("[INFO] Client: " + packet.getAddress().getHostName() + " requested following command " +builder.toString());
			byte[] buffer = "{RESULT:ERROR}".getBytes();
			packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			if(builder.toString().contains("CMD:TIME")) {
				log.add("[INFO] Sending current time to client: " + packet.getAddress().getHostName());
				buffer = (model.getTime()).getBytes();
				packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			}
			else if(builder.toString().contains("CMD:INIT")){

			}
			socket.send(packet);
		} catch (IOException e) {
			log.add("[CRIT] Something went terrible wrong in " + this.getClass().getSimpleName() + " Exception message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
