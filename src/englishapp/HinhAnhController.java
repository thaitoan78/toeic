/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.DBConnection;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class HinhAnhController implements Initializable {

    @FXML
    private JFXButton btnA;
    @FXML
    private JFXButton btnB;
    @FXML
    private JFXButton btnC;
    @FXML
    private JFXButton btnD;
    @FXML
    private JFXButton btnKetQua;
    @FXML
    private JFXButton btnNext;

    @FXML
    private Text txtNumber;
    @FXML
    private Text txtTotal;
    @FXML
    private Text txtContent;
    private Text lblKetQua;
    @FXML
    private Text txtKetQua;

    private Connection connect;
    private Statement statement;
    private ResultSet rs = null;
    @FXML
    private JFXButton btnExit;
    private Integer currentScore;
    String number;

    /**
     * Initializes the controller class.
     *
     * @param stage
     * @param url
     * @param rb
     * @throws java.io.IOException
     */
    public void start(final Stage stage) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("HinhAnh.fxml"));
        Scene homescene = new Scene(home);
        homescene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Picture");
        stage.setScene(homescene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connect = DBConnection.englishConnection();
        currentScore = 0;
        try {

            String sql = "Select count(*) from question where qimage IS NOT NULL";
            statement = connect.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                number = rs.getString(1);
                txtTotal.setText("Total question |" + " " + number);
            }
            rs.close();
            rs = statement.executeQuery("Select * from question where qimage IS NOT NULL");
            next();
        } catch (SQLException ex) {
            Logger.getLogger(HinhAnhController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void next() {
        try {

            rs.next();
            try {
                txtContent.setText(rs.getString("content"));
                btnA.setText(rs.getString("a"));
                btnB.setText(rs.getString("b"));
                btnC.setText(rs.getString("c"));
                btnD.setText(rs.getString("d"));
                txtKetQua.setText(rs.getString("answer"));
                txtNumber.setText(rs.getString("id"));
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HinhAnhController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleResult(ActionEvent event) {
    }

    @FXML
    private void handleNext(ActionEvent event) {
        next();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Comfirm");
        alert.setHeaderText("Exit!!!!!!!!!!");
        alert.setContentText("Do you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(HinhAnhController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                alert.close();
            }
        });
    }

}
