import controllers.LogInController;
import dao.InitDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        InitDB initDB = new InitDB();
        initDB.obrisiSve();
        initDB.kreirajBazu();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Prijava");
        primaryStage.setScene(new Scene(root, 290, 130));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
