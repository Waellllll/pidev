/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.sidebar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author chaim
 */
public class AdministrationController implements Initializable {

    private BorderPane BorderPane;
    @FXML
    private HBox root;
    @FXML
    private AnchorPane side_menu;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView profilePic;
    @FXML
    private Label username;
    @FXML
    private AnchorPane anchoreview;
    @FXML
    private Button btncontart;
    @FXML
    private Button btnaddcontrat;
    @FXML
    private Button btntype;
    @FXML
    private Button btnaddtype;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MainPage();
    }

    private void MainPage() {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../contrat/contrat.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btncontrat(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../contrat/contrat.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnaddcontrat(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../contrat/contratadd.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btntype(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../typecontrat/typecontrat.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnshowtype(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("../typecontrat/typecontratadd.fxml"));
            anchoreview.getChildren().clear();
            anchoreview.getChildren().add(view);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setView(AnchorPane view) {
        anchoreview.getChildren().setAll(view.getChildren());
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        anchoreview.getChildren().add(view);
    }
}
