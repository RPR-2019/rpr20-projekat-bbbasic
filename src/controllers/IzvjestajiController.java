package controllers;

import dao.DBConnection;
import dao.TehnickiPregledDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JRException;
import reports.IspravnaVozilaIzvjestaj;
import reports.NeispravnaVozilaIzvjestaj;
import reports.SviTehnicki;


public class IzvjestajiController {
    public TehnickiPregledDAO tehnickiPregledDAO;


    @FXML
    public void initialize() {

    }

    public void clickIspravna(ActionEvent actionEvent)  {
        try {
            new IspravnaVozilaIzvjestaj().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void clickNeispravna(ActionEvent actionEvent) {
        try {
            new NeispravnaVozilaIzvjestaj().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
    public void clickMjesecni(ActionEvent actionEvent) {
        try {
            new SviTehnicki().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
