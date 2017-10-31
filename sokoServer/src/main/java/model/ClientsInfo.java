package model;

import java.net.Socket;

public class ClientsInfo {

	private Socket socket;
	private int id;

	private String ip;

	private int port;

	private String state;

	private String task;

	public ClientsInfo(int id, String ip, int port, String state,String task,Socket socket) {

		this.id = id;
		this.ip = ip;
		this.port = port;
		this.state = state;
		this.task = task;
		this.setSocket(socket);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
