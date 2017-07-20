package server;

import core.*;
import java.io.*;
import java.rmi.*;
import java.net.*;
import java.util.*;

import interfaces.IModel;

/**
 * This class provides the login handling
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
 final class TCPServer implements interfaces.IServer {

	private Model model;
	private List<IModel> clients;
	// This variable is used to track the connected client, to much connection from the same IP could be a attack => security
	//private Map<InetAddress, Integer> connectionsByClient = new HashMap<>();

	TCPServer( Model model, List<IModel> clients) {

		this.model = model;
		this.clients = clients;
	}

	@Override
	public void run() {
		// Prepare server socket to listen. backlog => Maximum connection stack, ust used to use the IntetAddress.getByName(str)
		try (ServerSocket serverSocket = new ServerSocket(CVars.getLoginPort(), 50, InetAddress.getByName(CVars.getServerIp()))) {
			System.out.println("[INFO] Login server listen on IP: " + InetAddress.getByName(CVars.getServerIp()) + " at " + serverSocket.getLocalPort());

			// Start listening
			while (true) {
				System.out.println("[INFO] Waiting for an incoming connection");
				// Create a client socket and handle the client in its own thread
				try (Socket clientSocket = serverSocket.accept()) {
					InetAddress ip = clientSocket.getInetAddress();

					System.out.println("[INFO] Client connected: " + clientSocket.getInetAddress().getHostName());
					Thread handleThread = new Thread(new TCPRunner(clientSocket, model, clients));
					handleThread.setDaemon(true);
					handleThread.run();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			System.out.println("[CRIT] Critical state occurred in " + this.getClass().getSimpleName() + "  possible failed to launch");
			e.printStackTrace();
		}
	}


}

// This class hold login logic
final class TCPRunner implements Runnable{

	// TODO Replace with useful stuff
	private static final String USERNAME = "root", PASSWORD = "root";
	private Model model;
	private Socket clientSocket = null;
	private List<IModel> clients;

	private TCPRunner(){}
	TCPRunner(Socket clientSocket, Model model, List<IModel> clients){
		this();

		this.model = model;
		this.clientSocket = clientSocket;
		this.clients = clients;
	}

	@Override
	public void run() {
		System.out.println("[INFO] Handling client " + clientSocket.getInetAddress().getHostName());
		try(InputStream input = clientSocket.getInputStream(); OutputStream output = clientSocket.getOutputStream()) {
			StringBuilder in = new StringBuilder();
			int c;
			while((c = input.read()) >= 0 && c != ';') in.append((char)c);

			if(checkUser(in.toString())){
				output.write(0);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String username = in.substring(0, in.toString().indexOf('-'));
				IModel stub = (IModel) Naming.lookup(username);
				clients.add(stub);
				try {
					RawList r = new RawList(model.getQueue());
					stub.updateQueueView(r);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				System.out.println("[LOGIN] new connection from \"" + in.toString().substring(0, in.toString().indexOf('-'))
						+ "\" Address: \"" + clientSocket.getInetAddress().getHostName() +"\"");
			}else {
				output.write(1);
				System.out.println("[LOGIN] Connection denied from \"" + in.toString().substring(0, in.toString().indexOf('-'))
						+ "\" Address: \"" + clientSocket.getInetAddress().getHostName()+"\"");
			}
		} catch (IOException e) {
			System.out.println("[CRIT] IOException occurred in " + this.getClass().getSimpleName() + " client possible not connected");
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private boolean checkUser(String s) {
		// s should be something like "USERNAME:PASSWORD;
		String password = s.substring(s.indexOf('-') + 1, s.length());
		System.out.println(password);
		return PASSWORD.equals(password);
	}
}