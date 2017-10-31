package view_model;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import model.AdminModel;
import model.ClientsInfo;

public class AdminViewModel extends Observable implements Observer {

	private AdminModel model;
	private ObservableList<ClientsInfo> data;

	public AdminViewModel(AdminModel model) {

		this.model = model;
	}

	public AdminModel getModel() {
		return model;
	}

	public void setModel(AdminModel model) {
		this.model = model;
	}

	public ObservableList<ClientsInfo> getData() {
		return data;
	}

	public void setData(ObservableList<ClientsInfo> data) {
		this.data = data;
	}

	
	@Override
	public void update(Observable o, Object arg) {

		if (o == model) {
			data.clear();
			data.addAll(model.getClients());
			
		}
		
		

	}
	
	public void disconnectClient(ClientsInfo client){
		model.disconnectClient(client);
	}
	
	public void close(){
		model.stop();
	}
}
