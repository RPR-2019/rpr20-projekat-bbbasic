package controllers;

import dao.DBConnection;
import dao.TechnicalInspectionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JRException;
import reports.ValidTIReport;
import reports.NonValidTIReport;
import reports.AllTIReport;


public class IzvjestajiController {
    public TechnicalInspectionDAO technicalInspectionDAO;


    @FXML
    public void initialize() {

    }

    public void clickIspravna(ActionEvent actionEvent)  {
        try {
            new ValidTIReport().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void clickNeispravna(ActionEvent actionEvent) {
        try {
            new NonValidTIReport().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
    public void clickMjesecni(ActionEvent actionEvent) {
        try {
            new AllTIReport().showReport(DBConnection.getSession());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
