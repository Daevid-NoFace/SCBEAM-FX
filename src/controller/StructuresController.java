package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import file_management.FileReading;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import source.BuildBeam;
import source.Controller;
import source.Structure;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StructuresController implements Initializable {

    //Reference to the MenuPrincipalController
    MenuController menuController;

    @FXML
    private AnchorPane principalPane;

    @FXML
    private JFXButton btnCreateStructure;

    @FXML
    private JFXComboBox<String> fileComboBox;

    @FXML
    private JFXListView listNonMeshedStructures;

    @FXML
    private JFXButton btnDeleteNonMeshedStructure;

    @FXML
    private JFXButton btnMeshStructure;

    @FXML
    private JFXListView listMeshedStructures;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listNonMeshedStructures.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfNonMeshedStructures()));
        listMeshedStructures.setItems(FXCollections.observableList(Controller.getSingletonController().getMeshStructures()));
        fileComboBox.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfProcessedFiles()));
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    void showCreateStructure(ActionEvent actionEvent) throws IOException {

        AnchorPane structureOver = menuController.getPrincipalPane();
        menuController.createPage(new CreateStructureController(), structureOver, "/visual/CreateStructure.fxml");
    }

    public void deleteNonMeshedStructure(ActionEvent event) {
        int index = listNonMeshedStructures.getSelectionModel().getSelectedIndex();
        Controller.getSingletonController().getNonMeshedStructures().remove(index);
        System.out.println("Estructura no mallada eliminada. Tamano de lista Estructuras no malladas => " + Controller.getSingletonController().getNonMeshedStructures().size());
        listNonMeshedStructures.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfNonMeshedStructures()));
    }

    public void meshStructure(ActionEvent event) {
        int indexStructure = listNonMeshedStructures.getSelectionModel().getSelectedIndex();
        Structure currentStructure = Controller.getSingletonController().getNonMeshedStructures().get(indexStructure);
        int indexFile = fileComboBox.getSelectionModel().getSelectedIndex();
        File file = Controller.getSingletonController().getProcessedFiles().get(indexFile);
        TreeMap<Double, ArrayList<Integer>> timeTemperaturesTreeMap = Controller.getSingletonController().getTimeTemperaturesTreeMap().get((File) file);

        BuildBeam buildBeam = new BuildBeam(currentStructure, timeTemperaturesTreeMap);
        System.out.println("current thread before build mesh => " + Thread.currentThread().getName());
        buildBeam.start();
    }
}
