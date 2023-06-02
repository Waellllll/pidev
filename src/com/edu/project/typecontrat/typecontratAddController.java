/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.typecontrat;

import static DBCnx.MyConnection.MyConnection;
import com.edu.project.entities.TypeContrat;
import com.edu.project.entities.TypeContrat;
import com.edu.project.contrat.contratController;
import com.edu.project.entities.contrat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class typecontratAddController implements Initializable {

    typecontratController obj = new typecontratController();
    @FXML
    private Button btnajouter;

    contrat selectedcontrat;
   

 
    @FXML
    private TextField nomtf;
    @FXML
    private Label dateDebut;
    @FXML
    private ComboBox<contrat> nomcontrat;
    @FXML
    private ImageView imageview;

 private void insert() {
    String nom = nomtf.getText();
    int idcontrat = selectedcontrat.getId();

    validateFields();

    String query = "INSERT INTO `type_contrat`( `nom`, `contrat_id`) VALUES (?, ?)";

    try (Connection conn = MyConnection();
         PreparedStatement statement = conn.prepareStatement(query)) {
        statement.setString(1, nom);
        statement.setInt(2, idcontrat);
        statement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TypeContrat");
        alert.setHeaderText(null);
        alert.setContentText("added successfully");
        alert.showAndWait();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            test();
        } catch (SQLException ex) {
            Logger.getLogger(typecontratAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateFields() {
        if (nomtf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void OnCreate(ActionEvent event) {
        if (event.getSource() == btnajouter) {
            if (validateFields()) {
                insert();
                obj.showTypeContrats();
            }

        }
    }

    private void test() throws SQLException {
        List<contrat> fournis = retrievefournisseursFromDatabase();
        nomcontrat.getItems().addAll(fournis);
        nomcontrat.setPromptText("Select contrat");
// Set the cell factory to display only the name of the reclamations
        nomcontrat.setCellFactory(c -> new ListCell<contrat>() {
            @Override
            protected void updateItem(contrat fourni, boolean empty) {
                super.updateItem(fourni, empty);
                if (empty || fourni == null) {
                    setText(null);
                } else {
                    setText(fourni.getNomcontrat());
                }
            }
        });

// Set the button cell to display only the name of the reclamations
        nomcontrat.setButtonCell(new ListCell<contrat>() {
            @Override
            protected void updateItem(contrat cont, boolean empty) {
                super.updateItem(cont, empty);
                if (empty || cont == null) {
                    setText(null);
                } else {
                    setText(cont.getNomcontrat());
                }
            }
        });
        // Add an event listener to the ComboBox to display the selected reclamation
        nomcontrat.setOnAction(e -> {
            selectedcontrat = nomcontrat.getSelectionModel().getSelectedItem();
            if (selectedcontrat != null) {
                System.out.println("Selected contrat: " + selectedcontrat.getNomcontrat()+ ", ID: " + selectedcontrat.getId());
            }
        });
    }

    private List<contrat> retrievefournisseursFromDatabase() throws SQLException {
        List<contrat> fournis = new ArrayList<>();
        Connection conn = MyConnection();
        // Create a statement and execute the query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, nomcontrat FROM contrat");

        // Iterate over the result set and create TypeContrat objects
        while (rs.next()) {
            int id = rs.getInt("id");
            String nomCont = rs.getString("nomcontrat");
            contrat fourni = new contrat(id, nomCont);
            fournis.add(fourni);
        }

        // Close the statement and result set
        rs.close();
        stmt.close();

        return fournis;
    }

    private void executeQuery(String query) {
        Connection conn = MyConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }


}
