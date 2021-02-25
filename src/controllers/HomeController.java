package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.UserSession;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeController {

    public BorderPane mainPane;
    public Button btnUser;
    public Button btnEmployees;
    public Button btnReport;


    @FXML
    public void initialize() {
        btnUser.setText(String.valueOf(UserSession.getUserName()));
        onHome(null);
        if(UserSession.getPrivileges()) {
            btnEmployees.setDisable(false);
            btnReport.setDisable(false);
        }
        else {
            btnEmployees.setDisable(true);
            btnReport.setDisable(true);
        }
    }

    public void onTI(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/zakazivanjeTP1.fxml"), ResourceBundle.getBundle("Translation"));
            TP1Controller voziloController = new TP1Controller(mainPane);
            loader.setController(voziloController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            mainPane.setCenter(root);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void onEmployees(ActionEvent actionEvent) {
        System.out.println("Uposlenici");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminPristupKorisnicima.fxml"), ResourceBundle.getBundle("Translation"));
            AdminPristupKorisnicimaController administratorGlavniController = new AdminPristupKorisnicimaController();
            loader.setController(administratorGlavniController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            mainPane.setCenter(root);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void onReport(ActionEvent actionEvent) {
        System.out.println("Izvjestaji");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/izvjestaji.fxml"), ResourceBundle.getBundle("Translation"));
            IzvjestajiController izvjestajiController = new IzvjestajiController();
            loader.setController(izvjestajiController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            mainPane.setCenter(root);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void onSearch(ActionEvent actionEvent) {
        System.out.println("Pretraga");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pretraga.fxml"), ResourceBundle.getBundle("Translation"));
            PretragaController pretragaController = new PretragaController(mainPane);
            loader.setController(pretragaController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            mainPane.setCenter(root);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void onHome(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tehnickipregledi.fxml"), ResourceBundle.getBundle("Translation"));
            TehnickiPregledController tehnickiPregledController = new TehnickiPregledController(mainPane);
            loader.setController(tehnickiPregledController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.setMaximized(true);
            mainPane.setCenter(root);


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void onbtnLogOut(ActionEvent actionEvent) {
        UserSession.cleanUserSession();
        Stage novastage = (Stage) btnUser.getScene().getWindow();
        novastage.close();

        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), ResourceBundle.getBundle("Translation"));
            LogInController ctrl = new LogInController();
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("MeCARnic");
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.setScene(new Scene(root, 335, 169));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void choiceLanguageEng(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("eng"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        try {
            Stage pstage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
            HomeController ctrl = new HomeController();
            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();
            stage.setTitle("MeCARnic");
            stage.setScene(new Scene(root, 396, 311));
            stage.setWidth(1200);
            stage.setHeight(730);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void choiceLanguageBs(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        try {
            Stage pstage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
            HomeController ctrl = new HomeController();
            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();
            stage.setTitle("MeCARnic");
            stage.setScene(new Scene(root, 396, 311));
            stage.setWidth(1200);
            stage.setHeight(730);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
