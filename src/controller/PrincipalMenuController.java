package controller;

import com.jfoenix.controls.*;
import com.opencsv.CSVReader;
import file_management.FileReading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import source.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrincipalMenuController implements Initializable {

    List<File> loadFiles;
    private TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap = new TreeMap<>();

    @FXML
    private JFXTextField textFileName;

    @FXML
    private JFXButton btnLoadFile;

    @FXML
    private JFXButton btnCleanFileName;

    @FXML
    private JFXListView<File> listLoadFiles;

    @FXML
    private  JFXListView<File> listProcessedFiles;

    @FXML
    private JFXButton btnProcessFile;

    @FXML
    private JFXButton btnSearchFile;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label labelLoad;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<File> files = Controller.getSingletonController().getUnprocessedFiles();
        listLoadFiles.setItems(FXCollections.observableList(files));
        listLoadFiles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listProcessedFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getProcessedFiles()));
    }

    @FXML
    void cleanFileName(ActionEvent event) {
        textFileName.clear();
    }

    @FXML
    void loadFile(ActionEvent event) {

        if (!textFileName.getText().equals("")) {
            File file = new File(textFileName.getText());
            Controller.getSingletonController().getUnprocessedFiles().add(file);
            System.out.println("Fichero leido.");

            listLoadFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getUnprocessedFiles()));
        }
    }

    @FXML
    void searchFile(ActionEvent event) {
        Stage stage= new Stage();
        FileChooser fc= new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documento CSV","*csv"));

        try {
            System.out.println("Buscando...");
            textFileName.setText(fc.showOpenDialog(stage).getPath());
            System.out.println("Fichero encontrado");
        } catch (Exception e) {
            System.out.println("Fichero no seleccionado");
            e.printStackTrace();
        }
    }

    @FXML
    void processFile(ActionEvent event) {

        int index = listLoadFiles.getSelectionModel().getSelectedIndex();
        File file = Controller.getSingletonController().getUnprocessedFiles().remove(index);

        FileReading fileReading = new FileReading(file);
        System.out.println("Hilo principal => " + Thread.currentThread().getName());
        labelLoad.textProperty().bind(fileReading.messageProperty());

        fileReading.setOnRunning((succeesesEvent) -> {
            progressBar.progressProperty().bind(fileReading.progressProperty());
        });

        fileReading.setOnSucceeded((succeesesEvent) -> {
            Controller.getSingletonController().getProcessedFiles().add(file);
            Controller.getSingletonController().setTimeTemperaturesTreeMap(fileReading.getValue());
            listLoadFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getUnprocessedFiles()));
            listProcessedFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getProcessedFiles()));
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(fileReading);
        executorService.shutdown();
    }
}
