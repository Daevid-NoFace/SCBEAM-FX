package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import file_management.FileReading;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import source.Bar;
import source.Controller;
import source.Structure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateStructureController implements Initializable {

    MenuController menuController;
    Structure newStructure;

    @FXML
    private Spinner<Double> spinnerWidth;

    @FXML
    private Spinner<Double> spinnerHeight;

    @FXML
    private Spinner<Double> spinnerCovering;

    @FXML
    private Spinner<Double> spinnerDiameterLong;

    @FXML
    private Spinner<Double> spinnerLimitLong;

    @FXML
    private JFXComboBox<String> comboExpos;

    @FXML
    private JFXComboBox<String> comboReforce;

    @FXML
    private JFXButton btnCreateReforcementLong;

    @FXML
    private Spinner<Double> spinnerDiameterTransversal;

    @FXML
    private Spinner<Double> spinnerLimitTransversal;

    @FXML
    private JFXButton btnCreateReforcementTrans;

    @FXML
    private JFXButton btnShowStructure;

    @FXML
    private JFXButton btnCreateGeometricStructure;

    @FXML
    private AnchorPane graphPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnCreateStructure;

    @FXML
    private JFXTextArea textAreaStructureData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            comboReforce.setItems(FXCollections.observableList(FileReading.readTxt("D:/Tesis Lachi David/GitHub Online/SCBEAM-FX/src/icons/fiberTypeComboBox.txt")));
            comboExpos.setItems(FXCollections.observableList(FileReading.readTxt("D:/Tesis Lachi David/GitHub Online/SCBEAM-FX/src/icons/expoTypeComboBox.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    void backToStructure(javafx.event.ActionEvent event) throws IOException {
        AnchorPane structureOver = menuController.getPrincipalPane();
        menuController.createPage(new StructuresController(), structureOver, "/visual/Structures.fxml");
    }

    @FXML
    public void createDimensions(javafx.event.ActionEvent event) {

        //work separately
        //Geometric Data
        System.out.println("Geometric data");
        System.out.println("**************");

        double width = spinnerWidth.getValue();
        System.out.println("Width => " + width);

        double height = spinnerHeight.getValue();
        System.out.println("Height => " + height);

        double covering = spinnerCovering.getValue();
        System.out.println("Covering => " + covering);

        String structureName = "Beam" + String.valueOf(width) + "x" + String.valueOf(height) + " R = " + String.valueOf(covering) + " cm";
        System.out.println("Structure name => " + structureName);

        newStructure = new Structure(structureName, width, height);
        newStructure.setCovering(covering);

        //show data in text area
        textAreaStructureData.appendText(newStructure.getId() + "\n");
        textAreaStructureData.appendText("Width " + newStructure.getWidth() + " cm" + "\n");
        textAreaStructureData.appendText("Height " + newStructure.getHeight() + " cm" +  "\n");
        textAreaStructureData.appendText("Covering " + newStructure.getWidth() + " cm" +  "\n");
    }

    public void createLongitudinalBar(ActionEvent event) {
        //Longitudinal Reinforcement data
        System.out.println("\n" + "Longitudinal Reinforcement data");
        System.out.println("*******************************");

        double diameter = spinnerDiameterLong.getValue();
        System.out.println("longitudinal reinforcement diameter => " + diameter);
        newStructure.getLongitudinalBar().setDiameter(diameter);

        double tensileStrength = spinnerLimitLong.getValue();
        System.out.println("longitudinal reinforcement tensile strength => " + tensileStrength);
        newStructure.getLongitudinalBar().setTensileStrength(tensileStrength);

        String expoType = comboExpos.getSelectionModel().getSelectedItem();
        System.out.println("longitudinal reinforcement exposition type => " + expoType);

        String fiberType = comboReforce.getSelectionModel().getSelectedItem();
        System.out.println("longitudinal reinforcement fiber type => " + fiberType);

        if (expoType.equalsIgnoreCase("Protegido"))
            newStructure.getLongitudinalBar().setExposureType('p');
        else if (expoType.equalsIgnoreCase("Desprotegido"))
            newStructure.getLongitudinalBar().setExposureType('d');

        if (fiberType.equalsIgnoreCase("PRF-Vidrio"))
            newStructure.getLongitudinalBar().setFiberType('v');
        else if (fiberType.equalsIgnoreCase("PRF-Aramida"))
            newStructure.getLongitudinalBar().setFiberType('a');
        else if (fiberType.equalsIgnoreCase("PRF-Carbon"))
            newStructure.getLongitudinalBar().setFiberType('c');
        else if (fiberType.equalsIgnoreCase("Acero"))
            newStructure.getLongitudinalBar().setFiberType('s');

        //show data in text area
        textAreaStructureData.clear();
        textAreaStructureData.appendText("Longitudinal Reinforcement" + "\n");
        textAreaStructureData.appendText("      Diameter " + newStructure.getLongitudinalBar().getDiameter() + "\n");
        textAreaStructureData.appendText("      Covering " + newStructure.getLongitudinalBar().getTensileStrength() + " MPa" +  "\n");
        textAreaStructureData.appendText("      Exposure Type " + newStructure.getLongitudinalBar().getExposureType() + "\n");
        textAreaStructureData.appendText("      Fiber Type " + newStructure.getLongitudinalBar().getFiberType() +  "\n");
    }

    public void createCrossReinforcement(ActionEvent event) {

        //Cross Reinforcement data
        System.out.println("\n" + "Cross Reinforcement data");
        System.out.println("************************");

        double diameter = spinnerDiameterTransversal.getValue();
        System.out.println("cross reinforcement diameter => " + diameter);

        double tensionStrength = spinnerLimitTransversal.getValue();
        System.out.println("cross reinforcement tensile strength => " + tensionStrength);

        newStructure.getCrossBars().add(new Bar(diameter, tensionStrength, 1, 'd', 'v'));

        //show data in text area
        textAreaStructureData.clear();
        textAreaStructureData.appendText("Cross Reinforcement" + "\n");
        textAreaStructureData.appendText("Number of bars " + newStructure.getCrossBars().size() + " bar(s)" + "\n");
        textAreaStructureData.appendText("      Diameter " + newStructure.getLongitudinalBar().getDiameter() + "\n");
        textAreaStructureData.appendText("      Covering " + newStructure.getLongitudinalBar().getTensileStrength() + " MPa" +  "\n");
        textAreaStructureData.appendText("      Exposure Type " + newStructure.getLongitudinalBar().getExposureType() + "\n");
        textAreaStructureData.appendText("      Fiber Type " + newStructure.getLongitudinalBar().getFiberType() +  "\n");
    }

    public void showStructureData(ActionEvent event) {
        textAreaStructureData.clear();
        textAreaStructureData.appendText(newStructure.getId() + "\n");
        textAreaStructureData.appendText("Width " + newStructure.getWidth() + " cm" + "\n");
        textAreaStructureData.appendText("Height " + newStructure.getHeight() + " cm" +  "\n");
        textAreaStructureData.appendText("Covering " + newStructure.getWidth() + " cm" +  "\n");
        textAreaStructureData.appendText("Longitudinal Reinforcement" + "\n");
        textAreaStructureData.appendText("      Diameter " + newStructure.getLongitudinalBar().getDiameter() + "\n");
        textAreaStructureData.appendText("      Covering " + newStructure.getLongitudinalBar().getTensileStrength() + " MPa" +  "\n");
        textAreaStructureData.appendText("      Exposure Type " + newStructure.getLongitudinalBar().getExposureType() + "\n");
        textAreaStructureData.appendText("      Fiber Type " + newStructure.getLongitudinalBar().getFiberType() +  "\n");
        textAreaStructureData.appendText("Cross Reinforcement" + "\n");
        textAreaStructureData.appendText("Number of bars " + newStructure.getCrossBars().size() + " bar(s)" + "\n");
        textAreaStructureData.appendText("      Diameter " + newStructure.getLongitudinalBar().getDiameter() + "\n");
        textAreaStructureData.appendText("      Covering " + newStructure.getLongitudinalBar().getTensileStrength() + " MPa" +  "\n");
        textAreaStructureData.appendText("      Exposure Type " + newStructure.getLongitudinalBar().getExposureType() + "\n");
        textAreaStructureData.appendText("      Fiber Type " + newStructure.getLongitudinalBar().getFiberType() +  "\n");
    }

    public void createStructure(ActionEvent event) {
        Controller.getSingletonController().getNonMeshedStructures().add(newStructure);
        System.out.println("Estructura creada. Tamano de lista Estructuras no malladas => " + Controller.getSingletonController().getNonMeshedStructures().size());
    }

    public void handleSpinnerClicks(MouseEvent event) {

        if (event.getSource() == btnCreateGeometricStructure) {

        }
    }
}
