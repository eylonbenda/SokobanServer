package com.mile.sokoServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.AdminModel;
import model.ClientsInfo;

public class Server {

	private int port;
	private ClientHandler ch;
	private boolean iStopped;
	private ExecutorService threadPool;
	ClientsInfo client = null;

	public Server(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		this.iStopped = false;
		this.threadPool = Executors.newFixedThreadPool(3);

	}

	public void runServer() throws IOException {
		// check this
		ServerSocket server = new ServerSocket(port);
		System.out.println("server is alive and waiting for clients");
		int connections = 0;

		while (!iStopped) {
			try {
				Socket aClient = server.accept(); // blocking call
				
				 client = new ClientsInfo(connections++,aClient.getInetAddress().toString(), aClient.getPort(), "connect","get-solution",aClient);
				AdminModel model=AdminModel.getInstance();
				model.addClient(client);

				threadPool.execute(new Runnable() { 

					@Override
					public void run() {
						try {
							ch.handleClient(aClient,new ObjectInputStream(aClient.getInputStream()),
							new ObjectOutputStream(aClient.getOutputStream()));
							aClient.getInputStream().close();
							aClient.getOutputStream().close();
							aClient.close();
							model.updateStateClient(client);
							server.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
				
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			}
			

		}

	}

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runServer();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void stop() {
		
		
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(5, TimeUnit.SECONDS);
	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.iStopped = true;
	}

}
