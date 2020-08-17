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

    private ArrayList<File> files = new ArrayList<>();
    private TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap = new TreeMap<>();

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
    private JFXButton btnSearchFile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void cleanFileName(ActionEvent event) {

    }

    @FXML
    void loadFile(ActionEvent event) {

        File file = new File(textFileName.getText());
        files.add(file);
        System.out.println("Fichero leido. Tamaño de la lista -> " + files.size());
    }

    @FXML
    void searchFile(ActionEvent event) {
        Stage stage= new Stage();
        FileChooser fc= new FileChooser();
        File file;

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documento CSV","*csv"));
        //fc.showOpenDialog(stage);

        textFileName.setText(fc.showOpenDialog(stage).getPath());
        System.out.println("Fichero encontrado");
    }
}
