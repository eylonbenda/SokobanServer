package model;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.mile.sokoServer.Server;

import commands.ExitCommand;

public class AdminModel extends Observable {

	
	private List<ClientsInfo> clients = new ArrayList<>();
	private Server sokoServer;
	
	private static final AdminModel instance = new AdminModel();
	
	private AdminModel() {}
	public static AdminModel getInstance() {
		return instance;
	}
	
	public void addClient(ClientsInfo client){
		clients.add(client);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void updateStateClient(ClientsInfo client){
		
		client.setState("offline");
		client.setTask("end-task");
		//clients.add(client);
		this.setChanged();
		this.notifyObservers();
		
	}
	
	public List<ClientsInfo> getClients(){
		
		return clients;
	}
	
	public void stop(){
		this.sokoServer.stop();
		System.exit(1);
		
	}
	public Server getSokoServer() {
		return sokoServer;
	}
	public void setSokoServer(Server sokoServer) {
		this.sokoServer = sokoServer;
	}
	
	public void disconnectClient(ClientsInfo client){
		
		Socket socketClient = client.getSocket();
		try {
			socketClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	

}
