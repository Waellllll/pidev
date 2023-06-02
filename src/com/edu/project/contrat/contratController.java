/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.contrat;

import DBCnx.MyConnection;
import static DBCnx.MyConnection.MyConnection;
import com.edu.project.entities.contrat;
import com.edu.project.entities.contrat;
import com.edu.project.entities.contrat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class contratController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private Button updatebutton;

    @FXML
    private Button updatebutton1;
    @FXML
    private TextField montanttf;
    @FXML
    private TextField imagetf;
    @FXML
    private DatePicker datesignaturetf;
    @FXML
    private DatePicker dateexpirationtf;
    @FXML
    private Button btnbrowse;
    @FXML
    private TextField nomcontrattf;
    @FXML
    private ListView<contrat> listcontrat;
    @FXML
    private Button statbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        showcontrats();
        //showRec();
        //searchRec();
    }

    public ObservableList<contrat> getcontratList() {
        ObservableList<contrat> compteList = FXCollections.observableArrayList();
        Connection conn = MyConnection();
        String query = "SELECT * FROM contrat";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            contrat contrat;

            while (rs.next()) {
                contrat = new contrat(rs.getInt("id"), rs.getInt("montant"), rs.getDate("datesignature"), rs.getDate("dateexpiration"), rs.getString("imagecontrat"), rs.getString("nomcontrat"));
                compteList.add(contrat);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return compteList;
    }

    private boolean validateFields() {
        LocalDate datedeb = datesignaturetf.getValue();
        LocalDate datefin = dateexpirationtf.getValue();
        LocalDate today = LocalDate.now();
        if (imagetf.getText().isEmpty()) {
            showAlert("Error", "image field is required.");
            return false;
        }

        if (montanttf.getText().isEmpty()) {
            showAlert("Error", "Prix field is required.");
            return false;
        } else if (!montanttf.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Error", "Prix must be a number.");
            return false;
        }
        if (datedeb == null) {
            showAlert("Error", "Please select a start date.");
            return false;
        }

        if (datedeb.isBefore(today)) {
            showAlert("Error", "The start date must be in the future.");
            return false;
        }

        if (datefin == null) {
            showAlert("Error", "Please select an end date.");
            return false;
        }

        if (datefin.isBefore(today)) {
            showAlert("Error", "The end date must be in the future.");
            return false;
        }

        if (datefin.isBefore(datedeb)) {
            showAlert("Error", "The end date must be after the start date.");
            return false;
        }
        return true;
    }

    public void showcontrats() {
        ObservableList<contrat> list = getcontratList();

        listcontrat.setCellFactory(param -> new ListCell<contrat>() {
            @Override
            protected void updateItem(contrat item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    VBox vBox = new VBox();
                    Label nomcontratLabel = new Label(item.getNomcontrat());
                    nomcontratLabel.setStyle("-fx-font-weight: bold; -fx-padding: 0 10px 0 0;");

                    Label dateLabel = new Label("Signature: " + item.getDatesignature().toString() + " | Expiration: " + item.getDateexpiration().toString());
                    dateLabel.setStyle("-fx-padding: 0 10px 0 0;");

                    FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                    editIcon.setStyle("-fx-cursor: hand; -glyph-size: 20px; -fx-fill: #00E676;");
                    editIcon.setOnMouseClicked(event -> {
                        montanttf.setText(String.valueOf(item.getMontant()));
                        imagetf.setText(String.valueOf(item.getImagecontrat()));
                        nomcontrattf.setText(String.valueOf(item.getNomcontrat()));
                        datesignaturetf.setValue(item.getDatesignature().toLocalDate());
                        dateexpirationtf.setValue(item.getDateexpiration().toLocalDate());
                    });

                    FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 20px; -fx-fill: #ff1744;");
                    deleteIcon.setOnMouseClicked(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Delete Contract");
                        alert.setContentText("Are you sure you want to delete this contract?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            try {
                                PreparedStatement ps = null;
                                String query = "DELETE FROM `contrat` WHERE id =" + item.getId();
                                Connection conn = MyConnection();
                                ps = conn.prepareStatement(query);
                                ps.execute();
                                showcontrats();
                            } catch (SQLException ex) {
                                Logger.getLogger(contratController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    FontAwesomeIconView generateQRButton = new FontAwesomeIconView(FontAwesomeIcon.QRCODE);
                    generateQRButton.setOnMouseClicked(event -> {
                        // Generate the QR code using the product data
                        String qrCodeData = item.getMontant() + ", " + item.getNomcontrat() + ", " + item.getImagecontrat() + ", " + item.getDatesignature() + ", " + item.getDateexpiration();
                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
                        try {
                            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.AZTEC.QR_CODE, 200, 200);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
                            byte[] imageData = out.toByteArray();

                            // Display the QR code in a JavaFX ImageView
                            Image qrCodeImage = new Image(new ByteArrayInputStream(imageData));
                            ImageView qrCodeImageView = new ImageView(qrCodeImage);
                            qrCodeImageView.setFitWidth(200);
                            qrCodeImageView.setFitHeight(200);
                            vBox.getChildren().add(qrCodeImageView);

                            // Download the QR code
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Save QR Code");
                            fileChooser.setInitialFileName("qr_code.png");
                            Window primaryStage = null;
                            File file = fileChooser.showSaveDialog(primaryStage);
                            if (file != null) {
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(imageData);
                                    fos.flush();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } catch (WriterException | IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    hbox.getChildren().addAll(nomcontratLabel, dateLabel, editIcon, deleteIcon,generateQRButton);
                    setGraphic(hbox);
                }
            }
        });

        listcontrat.setItems(list);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onupdate(ActionEvent event) {
        if (event.getSource() == updatebutton) {
            if (validateFields()) {
                Integer montat = Integer.valueOf(montanttf.getText());
                String image = imagetf.getText();
                String nom = nomcontrattf.getText();
                String datedeb = String.valueOf(datesignaturetf.getValue());
                String datefin = String.valueOf(dateexpirationtf.getValue());

                contrat contrat = listcontrat.getSelectionModel().getSelectedItem();
                String query = "UPDATE contrat SET nomcontrat = ?, datesignature = ?, dateexpiration = ?, montant = ?, imagecontrat = ? WHERE id = ?";
                Connection conn = MyConnection();
                try (PreparedStatement statement = conn.prepareStatement(query)) {
                    statement.setString(1, nom);
                    statement.setString(2, datedeb);
                    statement.setString(3, datefin);
                    statement.setInt(4, montat);
                    statement.setString(5, image);
                    statement.setInt(6, contrat.getId());
                    statement.executeUpdate();
                    showcontrats();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
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

    @FXML
    private void handle(ActionEvent event) {
        Document document = new Document();
        try {
            // Create a temporary file with a unique name to store the PDF
            File tempFile = File.createTempFile("table", ".pdf");

            // Set the file to be deleted on exit
            tempFile.deleteOnExit();

            // Write the PDF to the temporary file
            PdfWriter.getInstance(document, new FileOutputStream(tempFile));
            document.open();

            // Add a title to the PDF
            Paragraph title = new Paragraph("Contrat Data");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            addRows(document, listcontrat);
            document.close();

            // Create a new FileChooser to allow the user to choose where to save the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("table.pdf");

            // Set the initial directory for the FileChooser to the user's home directory
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            // Show the FileChooser and wait for the user to select a file
            File file = fileChooser.showSaveDialog(listcontrat.getScene().getWindow());

            // If the user selected a file, copy the contents of the temporary file to the selected file
            if (file != null) {
                Files.copy(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addRows(Document document, ListView<contrat> listView) throws DocumentException {
        ObservableList<contrat> items = listView.getItems();
        for (int i = 0; i < items.size(); i++) {
            contrat item = items.get(i);
            document.add(new Paragraph("ID: " + item.getId()));
            document.add(new Paragraph("Date signature: " + item.getDatesignature()));
            document.add(new Paragraph("Date expiration: " + item.getDateexpiration()));
            document.add(new Paragraph("Montant: " + item.getMontant()));
            document.add(new Paragraph("Nom contrat: " + item.getNomcontrat()));
            if (i < items.size() - 1) {
                document.add(new Chunk("------------------------------"));
            }
            document.add(Chunk.NEWLINE);
        }
    }

    @FXML
    private void browse(ActionEvent event) throws FileNotFoundException {
        FileChooser filechooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

        //filechooser.setInitialDirectory(new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Notex\\src\\com\\gn\\module\\evenements\\image"));
        filechooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        File file = filechooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(new FileInputStream(file));
            imagetf.setText(file.getName());
            // set fit height and width of ImageView to the image size

            // set the image view in your UI, e.g.
        }
    }

public ObservableList<PieChart.Data> getPieChartData() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    Connection conn = MyConnection();
        String query = "SELECT t.nom, SUM(c.montant) AS total_price FROM type_contrat t JOIN contrat c ON t.contrat_id = c.id GROUP BY t.nom";
    Statement st;
    ResultSet rs;
    try {
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            String typeName = rs.getString("nom");
            double totalPrice = rs.getDouble("total_price");
            pieChartData.add(new PieChart.Data(typeName, totalPrice));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return pieChartData;
}

    @FXML
    private void stat(ActionEvent event) {
        ObservableList<PieChart.Data> pieChartData = getPieChartData();
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Contrat ");
        Scene scene = new Scene(new Group(chart), 500, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    

}
