package com.mile.sokoServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public interface ClientHandler {
	
	
	void handleClient(Socket socket,InputStream inFromClient, OutputStream outToClient);

}
