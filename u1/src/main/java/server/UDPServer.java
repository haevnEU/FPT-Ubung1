package server;

import core.*;
import java.net.*;

import java.io.IOException;

/**
 * This class provides time handling
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
final class UDPServer implements interfaces.IServer {

	private Model model;


	private UDPServer() {}

	UDPServer(Model m){
		this();
		this.model = m;
	}

	@Override
	public void run() {
		try{
			DatagramSocket serverSocket = new DatagramSocket(CVars.getTimePort(), InetAddress.getByName(CVars.getServerIp()));
			System.out.println("[INFO] Time server listen on IP: " + InetAddress.getByName(CVars.getServerIp()) + " at " + CVars.getTimePort());
				while(true){
				byte[] buffer = new byte["{CMD:TIME}".length()];
				DatagramPacket  packet = new DatagramPacket(buffer, buffer.length -1);
				try{
					serverSocket.receive(packet);
					UDPRunner client = new UDPRunner(packet, serverSocket, model);
					Thread handle = new Thread(client, "udp-client");
					handle.setDaemon(true);
					handle.start();
				} catch (IOException e) {
					System.out.println("[CIRT] IO Exception occurred in " + this.getClass().getSimpleName());
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException | SocketException e) {
			System.out.println("[SYS CRIT] Fatal error occurred in " + this.getClass().getSimpleName() + " the server is possible offline!");
			e.printStackTrace();
		}

	}
}

final class UDPRunner implements  Runnable{

	private DatagramPacket packet;
	private DatagramSocket socket;
	private Model model;


	private UDPRunner(){}

	UDPRunner(DatagramPacket packet, DatagramSocket serverSocket, Model model) {
		this();

		this.socket = serverSocket;
		this.packet = packet;
		this.model = model;
	}


	@Override
	public void run() {
		InetAddress clientIP = packet.getAddress();
		int clientPort = packet.getPort();
		try{
			StringBuilder builder = new StringBuilder();
			for(byte b : packet.getData()) builder.append((char)b);

			System.out.println("[INFO] Client: " + packet.getAddress().getHostName() + " requested following command " +builder.toString());
			byte[] buffer = "{RESULT:ERROR}".getBytes();
			packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			if(builder.toString().contains("CMD:TIME")) {
				System.out.println("[INFO] Sending current time to client: " + packet.getAddress().getHostName());
				buffer = (model.getTime()).getBytes();
				packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			}
			socket.send(packet);
		} catch (IOException e) {
			System.out.println("[CRIT] Something went terrible wrong in " + this.getClass().getSimpleName() + " Exception message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
