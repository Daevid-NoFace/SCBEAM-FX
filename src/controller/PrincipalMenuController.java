package controller;

import com.jfoenix.controls.*;
import com.opencsv.CSVReader;
import file_management.FileReading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
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
    MenuController menuController;

    @FXML
    private AnchorPane principalPane;

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

    @FXML
    private JFXButton btnDeleteNonProcessedFile;

    @FXML
    private  JFXButton btnDeleteProcessedFile;

    @FXML
    private JFXButton btnContinue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<File> files = Controller.getSingletonController().getUnprocessedFiles();
        listLoadFiles.setItems(FXCollections.observableList(files));
        listLoadFiles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listProcessedFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getProcessedFiles()));
        labelLoad.setVisible(false);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
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

        labelLoad.setVisible(true);

        Task<Void> longTask = new Task<Void> () {
            @Override
            protected Void call() throws Exception {
                Controller.getSingletonController().getTimeTemperaturesTreeMap().put(file, fileReading.readFile());
                return null;
            }
        };

        longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                principalPane.getChildren().remove(progressBar);
                labelLoad.setText("Finalizado");
                principalPane.getChildren().remove(labelLoad);
                Controller.getSingletonController().getProcessedFiles().add(file);
                listLoadFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getUnprocessedFiles()));
                listProcessedFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getProcessedFiles()));
            }
        });

        progressBar.progressProperty().bind(longTask.progressProperty());
        new Thread(longTask).start();
        labelLoad.setText("Cargando");
    }

    public void deleteNonProcessedFile(ActionEvent event) {
        int index = listLoadFiles.getSelectionModel().getSelectedIndex();
        Controller.getSingletonController().getUnprocessedFiles().remove(index);
        listLoadFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getUnprocessedFiles()));
    }

    public void deleteProcessedFile(ActionEvent event) {
        int index = listProcessedFiles.getSelectionModel().getSelectedIndex();
        Controller.getSingletonController().getProcessedFiles().remove(index);
        listProcessedFiles.setItems(FXCollections.observableList(Controller.getSingletonController().getProcessedFiles()));
    }

    public void continueAction(ActionEvent event) throws IOException {
        menuController.createPage(new StructuresController(), menuController.getPrincipalPane(), "/visual/Structures.fxml");
    }
}
