import controllers.LogInController;
import dao.InitDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.VIN;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        InitDB initDB = new InitDB();
        try {
            initDB.obrisiSve();
        }
        catch (SQLException e) {

        }
        initDB.kreirajBazu();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("MeCARnic");
        primaryStage.getIcons().add(new Image("/img/glavna.png"));
        primaryStage.setScene(new Scene(root, 335, 169));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
