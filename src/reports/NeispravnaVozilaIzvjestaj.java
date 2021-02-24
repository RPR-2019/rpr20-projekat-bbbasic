package reports;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class NeispravnaVozilaIzvjestaj  extends JFrame {
    public void showReport(Connection conn) throws JRException {
        try {
            // izgleda zbog razmaka izmedju Emina Basic krahira sve, mislim daj e ovo do biblioteke jasper
            // da pogresno protumaci ovaj razmak on iz nekog razloga ovo Emina%20Basic pogresno protumaci skroz

            String reportSrcFile = getClass().getResource("/reports/izvjestajNeispravni.jrxml").getFile();
            String reportsDir = getClass().getResource("/reports/").getFile();
            //zbog moje mutanje Emina Basic
            reportSrcFile = reportSrcFile.replace("%20", " ");

            System.out.println(reportSrcFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("reportsDirPath", reportsDir);
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            list.add(parameters);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setSize(700, 500);
            this.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            throw new JRException(".");
        }
    }


}
