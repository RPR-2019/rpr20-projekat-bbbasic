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

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class HomeController {

    public BorderPane mainPane;
    public Button btnUser;

    @FXML
    public void initialize() {
        btnUser.setText(String.valueOf(UserSession.getKorisnickoIme()));
    }



    public void onBtnTehnickiPregled(ActionEvent actionEvent) {
        System.out.println("Tehnicki pregled");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/zakazivanjeTP1.fxml"));
            TP1Controller voziloController = new TP1Controller(mainPane);
            loader.setController(voziloController);
            root = loader.load();
            stage.setTitle("Vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/icon.jpg"));
            stage.setWidth(900);
//            stage.setHeight(580);
            //MIJENJANJE
            mainPane.setCenter(root);

            //stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void onBtnUposlenici(ActionEvent actionEvent) {
        System.out.println("Uposlenici");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/administratorglavni.fxml"));
            AdministratorGlavniController administratorGlavniController = new AdministratorGlavniController();
            loader.setController(administratorGlavniController);
            root = loader.load();
            stage.setTitle("Vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/icon.jpg"));
//            stage.setWidth(440);
//            stage.setHeight(483);
            //MIJENJANJE
            mainPane.setCenter(root);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void onBtnIzvjestaj(ActionEvent actionEvent) {
        System.out.println("Trenutno nemamo nista za Izvjestaje");
    }

    public void onBtnPretraga(ActionEvent actionEvent) {
        System.out.println("Trenutno nemamo nista za Pretragu");
    }

    public void onBtnGlavnaForma(ActionEvent actionEvent) {
        System.out.println("Trenutno nemamo nista za glavnu formu.");
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tehnickipregledi.fxml"));
            TehnickiPregledController tehnickiPregledController = new TehnickiPregledController();
            loader.setController(tehnickiPregledController);
            root = loader.load();
            //stage.setTitle("TehnickiPregled");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
//            stage.setWidth(440);
//            stage.setHeight(483);
            //MIJENJANJE
            stage.setMaximized(true);
            mainPane.setCenter(root);


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void onbtnLogOut(ActionEvent actionEvent) throws IOException {
        UserSession.cleanUserSession();
        Stage novastage = (Stage) btnUser.getScene().getWindow();
        novastage.close();

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("MeCARnic");
        stage.getIcons().add(new Image("/img/glavna.png"));
        stage.setScene(new Scene(root, 335, 169));
        stage.setResizable(false);
        stage.show();

    }
}
