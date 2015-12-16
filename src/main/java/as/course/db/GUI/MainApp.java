package as.course.db.GUI;

import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.Optional;


/**
 * Created by RTCCD on 13.10.2015.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        String fxmlFile = "/fxml.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setController(controller);
        Parent root = loader.load();

        primaryStage.setTitle("Train database");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->{
            if(!Controller.isCommitted & controller.isDataChanged()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Yhe changes are not committed. They will be lost:(");
                alert.setContentText("Do you want to commit?");
                ButtonType yesButton = new ButtonType("Yes!");
                ButtonType noButton = new ButtonType("Noooo");

                alert.getButtonTypes().setAll(yesButton,noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == yesButton){

                    //После коммита закрываем окно
                    primaryStage.close();
                }else if(result.get() == noButton){
                    //просто закрываем окно
                    primaryStage.close();
                }

            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
