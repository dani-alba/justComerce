package vista.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Login extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        
        stage.getIcons().add(new Image("/vista/login/images/icon.png")); 
        stage.setTitle("JustComerce"); 
        stage.setResizable(false);
        stage.sizeToScene(); // AJUSTAR LA VENTANA AL TAMAÑO DEL PANE (PARA QUE NO HAYAN BORDES BLANCOS EN LA VENTANA)        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
