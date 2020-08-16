package controller;

import com.jfoenix.controls.*;
import com.opencsv.CSVReader;
import file_management.FileReading;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class PrincipalMenuController implements Initializable {

    private File file;
    private TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap;

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

        timeTemperaturesTreeMap = FileReading.readFile(file);

        for(Map.Entry<Double,ArrayList<Integer>> entry : timeTemperaturesTreeMap.entrySet()) {
            double key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            System.out.println(key + " => " + value);
        }


    }

    @FXML
    void showCurve(ActionEvent event) {

    }

    @FXML
    void showCurveNiu(ActionEvent event) {

    }
}
