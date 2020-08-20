package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
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
import source.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listNonMeshedStructures.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfNonMeshedStructures()));
        fileComboBox.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfProcessedFiles()));
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setNode(Node node) {

        principalPane.getChildren().clear();
        principalPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(2000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    public void createPage(AnchorPane home, String loc) throws IOException {
        home = FXMLLoader.load(getClass().getResource(loc));
        setNode(home);
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


}
