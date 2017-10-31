package view;

import com.mile.sokoServer.ClientHandler;
import com.mile.sokoServer.Server;
import com.mile.sokoServer.SolverClientHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AdminModel;
import view_model.AdminViewModel;


public class App extends Application
{
    public static void main( String[] args )
    {
//    	ClientHandler ch = new SolverClientHandler();
//		Server myServer = new Server(3434, ch);
//		
//		myServer.start();
		
		launch(args);
    }
    
   

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ClientHandler ch = new SolverClientHandler();
		Server myServer = new Server(3434, ch);
		
		myServer.start();
		
		
		FXMLLoader fxl=new FXMLLoader();
		BorderPane root = fxl.load(getClass().getResource("adminServer.fxml").openStream());
		
        
        AdminModel model = AdminModel.getInstance();
        model.setSokoServer(myServer);
		AdminViewModel viewModel = new AdminViewModel(model);
		AdminController view = fxl.getController();
		view.setViewModel(viewModel);
		model.addObserver(viewModel);
		viewModel.addObserver(view);
        
        Scene scene = new Scene(root, 500, 500);
    
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
}
