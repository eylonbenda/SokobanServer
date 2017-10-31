package view;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ClientsInfo;
import model.dataBase.Record;
import view_model.AdminViewModel;

public class AdminController implements Initializable , Observer {
	
	
	@FXML
	private TableView<ClientsInfo> table;
	private ObservableList<ClientsInfo> data;
	private AdminViewModel viewModel;
	
	
	
    @FXML
    private void handleButtonAction(ActionEvent event) {
    	close();
    }

    @SuppressWarnings("unchecked")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
      
    	
    	
    	data = FXCollections.observableArrayList();
		table.setEditable(true);

		
		// Record table columns Initialization
		TableColumn<ClientsInfo, String> column1 = new TableColumn<>("Client IP");
		column1.setCellValueFactory(new PropertyValueFactory<>("ip"));
		column1.setMinWidth(100);
		column1.setMaxWidth(100);

		TableColumn<ClientsInfo, Integer> column2 = new TableColumn<>("Client Port");
		column2.setCellValueFactory(new PropertyValueFactory<>("port"));
		column2.setMinWidth(100);
		column2.setMaxWidth(100);

		TableColumn<ClientsInfo, String> column3 = new TableColumn<>("State");
		column3.setCellValueFactory(new PropertyValueFactory<>("state"));
		column3.setMinWidth(100);
		column3.setMaxWidth(100);
		
		TableColumn<ClientsInfo, String> column4 = new TableColumn<>("Task");
		column4.setCellValueFactory(new PropertyValueFactory<>("task"));
		column4.setMinWidth(200);
		column4.setMaxWidth(200);
		
		
		// Columns set up
		table.getColumns().addAll(column1, column2, column3, column4);
		table.setItems(data);
		
		// disconnect client from server when click on him
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				
				ClientsInfo client=table.getSelectionModel().getSelectedItem();
				if(client!=null)
					viewModel.disconnectClient(client);
			}
		});
    	
    	
    }
    

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public AdminViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(AdminViewModel viewModel) {
		this.viewModel = viewModel;
		if(this.viewModel != null){
			this.viewModel.setData(data);
		}
	}
    
	public void close(){
		
		viewModel.close();
	}

}
