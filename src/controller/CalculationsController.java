package controller;

import calculations.Calculations;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import source.Controller;
import source.Quadrant;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class CalculationsController implements Initializable {

    private MenuController menuController;

    @FXML
    private JFXListView structuresMeshedList;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXRadioButton radioBtnMethodP;

    @FXML
    private JFXRadioButton radioBtnAverageMethod;

    @FXML
    private TableView tableResults;

    @FXML
    private JFXButton btnCalculate;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        structuresMeshedList.setItems(FXCollections.observableList(Controller.getSingletonController().getNamesOfMeshedStructures()));
    }

    public void calculateBtn(javafx.event.ActionEvent event) {
        TreeMap<Double, ArrayList<Double>> timeVsResults = Calculations.cuttingMethod(Controller.getSingletonController().getMeshStructures().get(structuresMeshedList.getSelectionModel().getSelectedIndex()), 'p');

        for(Map.Entry<Double, ArrayList<Double>> entry : timeVsResults.entrySet()) {
            Double key = entry.getKey();
            ArrayList<Double> values = entry.getValue();

            System.out.println("Time => " + key * 60 + " minutes");
            System.out.println("    Values => Vc: " + values.get(0) + " Vfv: " + values.get(1) + " Vn: " + values.get(2));
        }
    }
}
