package com.mile.sokoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mile.sokoServer.sokoban_solver.SokobanSolver;

import model.AdminModel;
import model.ClientsInfo;
import model.data.Level;
import searchLib.Action;

public class SolverClientHandler implements ClientHandler {

	@Override
	public void handleClient(Socket socket,InputStream inFromClient, OutputStream outToClient) {
		
		
		SokobanSolver solver = null;
		List<Action> moves = null;
		Level level = null;
		ObjectInputStream fromClient = null;
		ObjectOutputStream outClient = null;
		try {
			// initialize objects
			fromClient = (ObjectInputStream) inFromClient;
			outClient = (ObjectOutputStream) outToClient;
			
			
			level = (Level) fromClient.readObject();
			solver = new SokobanSolver(level);
			String levelName = level.getLevelName();
			
			String solution = this.getSolutionFromService(levelName);
			if (solution == null) {// call model to calculate solution,and
									// update the solution-Service
							     	// and the client too.
				moves = solver.solve();
				String sol = this.parseListActionsToString(moves);
				this.addSolutionToService(levelName, sol);

			} // solution found in the data base ,send it back to client
			else{
				moves=this.solutionToActionList(solution);
			}

			outClient.writeObject(moves);
			outClient.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // close the stream input output
			try {
				
				if (fromClient != null)
					fromClient.close();
				if (outClient != null)
					outClient.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void addSolutionToService(String levelName , String solution) {
		
		System.out.println(levelName);
		String url = "http://localhost:8080/serviceSolution/webapi/solutions";
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);
		
		Form form = new Form();
		form.param("name", levelName);
		form.param("solution", solution);
		
		Response response = webTarget.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

		if (response.getStatus() == 204) {
			System.out.println("solution added successfully");
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
	

	}

	private String getSolutionFromService(String name) {
		String url = "http://localhost:8080/serviceSolution/webapi/solutions?name=" + name;
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
		if (response.getStatus() == 200) {
			String solution = response.readEntity(new GenericType<String>() {
			});
			System.out.println("solution: " + solution);
			return solution;
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
		return null;
	}
	
	private String parseListActionsToString(List<Action> list){
		
		String res = null;
		
		Map<String,String> mapParse =new  HashMap<>();
		mapParse.put("move up", "u");
		mapParse.put("move down", "d");
		mapParse.put("move right", "r");
		mapParse.put("move left", "l");
		
		
		for (Action action : list) {
			
			switch (action.getNameAction()) {
			case "move up":
				res += "U";
				break;
			case "move down":
				res += "D";
				break;
			case "move right":
				res += "R";
				break;
			case "move left":
				res += "L";
				break;
			default:
				break;
			}
		}
		
		return res;
		
	}
	
	
	public List<Action> solutionToActionList(String sol){
		char arr[] = sol.toCharArray();
		List<Action> list = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			switch (arr[i]) {
			case 'D':
				list.add(new Action("move down"));
				break;
			case 'U':
				list.add(new Action("move up"));
				break;
			case 'R':
				list.add(new Action("move right"));
				break;
			case 'L':
				list.add(new Action("move left"));
				break;
			default:
				break;
			}
		}
		return list;
	}

}
