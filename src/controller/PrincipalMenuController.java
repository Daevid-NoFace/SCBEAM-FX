package controller;

import com.jfoenix.controls.*;
import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrincipalMenuController implements Initializable {

    private File file;

    @FXML
    private JFXTextField textFileName;

    @FXML
    private JFXButton btnLoadFile;

    @FXML
    private JFXButton btnCleanFileName;

    @FXML
    private JFXListView<?> listCreatedStrcutures;

    @FXML
    private JFXListView<?> listNettingStructure;

    @FXML
    private JFXButton btnCleanStructures;

    @FXML
    private JFXButton btnStructure;

    @FXML
    private JFXComboBox<?> comboBarMaterial;

    @FXML
    private JFXRadioButton rbtnMethodP;

    @FXML
    private JFXRadioButton rbtnProm;

    @FXML
    private Spinner<?> timeSpinner;

    @FXML
    private JFXButton btnCalculate;

    @FXML
    private JFXButton btnCurve;

    @FXML
    private JFXButton btnCurveNiu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @FXML
    void calculate(ActionEvent event) {

    }

    @FXML
    void cleanFileName(ActionEvent event) {

    }

    @FXML
    void cleanStructures(ActionEvent event) {

    }

    @FXML
    void createStructure(ActionEvent event) {

    }

    @FXML
    void loadFile(ActionEvent event) {

        Stage stage= new Stage();
        FileChooser fc= new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documento CSV","*csv"));
        file = fc.showOpenDialog(stage);

        //Ejemplo de como leer el CSV
        ArrayList<String> teams = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file.toString()), StandardCharsets.ISO_8859_1));
            String[]  nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String team1    = nextLine[0];
                teams.add(team1);
            }
            reader.close();
            textFileName.setText(file.toString());
        } catch (IOException e) {
            e.getMessage();
        }
        for (int i = 0; i < teams.size() ; i++) {
            System.out.println(teams.get(i));
        }


    }

    @FXML
    void showCurve(ActionEvent event) {

    }

    @FXML
    void showCurveNiu(ActionEvent event) {

    }
}
