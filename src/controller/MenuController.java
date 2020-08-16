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

    @FXML
    void showPrincipalMenu(ActionEvent event) {
        try {
            this.createPage(principalPane, "/visual/PrincipalMenu.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void showStructuresMenu(ActionEvent event) {
        try {
            this.createPage(principalPane,  "/visual/CreateStructure.fxml");
        }catch (Exception e){
            e.printStackTrace();
        }
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

}
