package server;

import core.Model;
import core.CVars;
import interfaces.IModel;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.*;

/**
 * This class provides
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
public final class TCPServer implements interfaces.IServer{

	private static final String SERVER_IP = "localhost";

	private Model model;
	// TODO REPLACE ?!
	private OwnSyncArrayList log;
	List<IModel> clients;
	// This variable is used to track the connected client, to much connection from the same IP could be a attack => security
	//private Map<InetAddress, Integer> connectionsByClient = new HashMap<>();

	public TCPServer(OwnSyncArrayList log, Model model, List<IModel> clients){

		this.log = log;
		this.model = model;
		this.clients = clients;
	}

	@Override
	public void run() {
		// Prepare server socket to listen. backlog => Maximum connection stack, ust used to use the IntetAddress.getByName(str)
		try(ServerSocket serverSocket = new ServerSocket(CVars.getLoginPort(), 50,InetAddress.getByName(CVars.getServerIp()))){
			log.add("[INFO] Login server listen on IP: " + InetAddress.getByName(CVars.getServerIp()) + " at " + serverSocket.getLocalPort());

			// Start listening
			while(true){
				log.add("[INFO] Waiting for an incoming connection");
				// Create a client socket and handle the client in its own thread
				try(Socket clientSocket = serverSocket.accept()){
					InetAddress ip = clientSocket.getInetAddress();
					// TODO ACTIVATE
					// if(testIP(ip)) continue;
					log.add("[INFO] Client connected: " +clientSocket.getInetAddress().getHostName());
					Thread handleThread = new Thread(new TCPRunner(clientSocket, log, model, clients));
					handleThread.setDaemon(true);
					handleThread.run();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			log.add("[CRIT] Critical state occurred in " + this.getClass().getSimpleName() + "  possible failed to launch");
			e.printStackTrace();
		}
	}

	/**
	 * This method checks a ip address. Reason is a resource problem, to many connection from one ip could be a malicious thing
	 * @param ip IP which should be checked
	 * @return true if the IP is "clean" that means no entry or less than the maximum tries.
	 */
    // TODO ENABLE AGAIN
	/*
     private boolean testIP(InetAddress ip){
	    if(connectionsByClient.containsKey(ip)){
		    // TODO Replace 5 with a variable, 5 is used as temporary maximum connections by IP
		    if(connectionsByClient.get(ip) >= 5){
			    log.add("[CRIT] Detected possible attack from " + ip.getHostName());
			    return true;
		    }
		    else connectionsByClient.replace(ip, connectionsByClient.get(ip) + 1);
	    }
	    else connectionsByClient.put(ip, 0);
	    return false;
	}
	*/
}

// This class hold login logic
final class TCPRunner implements Runnable{

	// TODO Replace with useful stuff
	private static final String USERNAME = "root", PASSWORD = "root";
	private Model model;
	private Socket clientSocket = null;
	private OwnSyncArrayList log;
	List<IModel> clients;

	private TCPRunner(){}
	TCPRunner(Socket clientSocket, OwnSyncArrayList log, Model model, List<IModel> clients){
		this();

		this.model = model;
		this.clientSocket = clientSocket;
		this.log = log;
		this.clients = clients;
	}

	@Override
	public void run() {
		log.add("[INFO] Handling client " + clientSocket.getInetAddress().getHostName());
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
				log.add("[LOGIN] new connection from \"" + in.toString().substring(0, in.toString().indexOf('-'))
						+ "\" Address: \"" + clientSocket.getInetAddress().getHostName() +"\"");
			}else {
				output.write(1);
				log.add("[LOGIN] Connection denied from \"" + in.toString().substring(0, in.toString().indexOf('-'))
						+ "\" Address: \"" + clientSocket.getInetAddress().getHostName()+"\"");
			}
		} catch (IOException e) {
			log.add("[CRIT] IOException occurred in " + this.getClass().getSimpleName() + " client possible not connected");
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