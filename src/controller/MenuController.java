package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController  implements Initializable {

    @FXML
    private JFXButton principalBtn;

    @FXML
    private JFXButton structureBtn;

    @FXML
    private JFXButton helpBtn;

    @FXML
    private AnchorPane principalPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AnchorPane getPrincipalPane() {
        return this.principalPane;
    }

    @FXML
    void showPrincipalMenu(ActionEvent event) {
        try {
            this.createPage(new PrincipalMenuController(), principalPane, "/visual/PrincipalMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void showStructuresMenu(ActionEvent event) {
        try {

            createPage(new StructuresController(), principalPane, "/visual/Structures.fxml");
            /*
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MenuController.class.getResource("/visual/Structures.fxml"));
            AnchorPane ap = loader.load();
            StructuresController structuresController = loader.getController();
            structuresController.setMenuController(this);
            //this.createPage(principalPane,  "/visual/Structures.fxml");
            this.setNode(ap);

             */

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createPage(Object instance, AnchorPane home, String loc) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MenuController.class.getResource(loc));
        home = loader.load();

        if (instance instanceof StructuresController) {
            instance = loader.getController();
            ((StructuresController) instance).setMenuController(this);
        } else if (instance instanceof CreateStructureController) {
            instance = loader.getController();
            ((CreateStructureController) instance).setMenuController(this);
        }

        setNode(home);
    }

    public void setNode(Node node) {
        principalPane.getChildren().clear();
        principalPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(2000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }




}
