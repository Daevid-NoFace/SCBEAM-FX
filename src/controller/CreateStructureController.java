package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateStructureController implements Initializable {

    @FXML
    private Spinner<?> spinnerWidth;

    @FXML
    private Spinner<?> spinnerHeigth;

    @FXML
    private Spinner<?> spinnerRecub;

    @FXML
    private Spinner<?> spinnerDiameterLong;

    @FXML
    private Spinner<?> spinnerLimitLong;

    @FXML
    private JFXComboBox<?> comboExpos;

    @FXML
    private JFXComboBox<?> comboReforce;

    @FXML
    private JFXButton btnCreateReforcementLong;

    @FXML
    private Spinner<?> spinnerDiameterTransversal;

    @FXML
    private Spinner<?> spinnerLimitTransversal;

    @FXML
    private JFXButton btnCreateReforcementTrans;

    @FXML
    private JFXButton btnShowStructure;

    @FXML
    private JFXButton btnCreateStructure;

    @FXML
    private AnchorPane graphPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
