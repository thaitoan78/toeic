/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.AlertDialog;
import util.EnglishAssistantUtil;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class HinhAnhController implements Initializable {

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
    private Text txtKetQua;

    private Connection connect;
    private Statement statement;
    private ResultSet rs;
    @FXML
    private JFXButton btnExit;

    @FXML
    private JFXRadioButton radA;
    @FXML
    private ToggleGroup tongle;
    @FXML
    private JFXRadioButton radB;
    @FXML
    private JFXRadioButton radC;
    @FXML
    private JFXRadioButton radD;
    @FXML
    private Text score;
    private Stage stage;
    private String number, currentAnswer;// so cau
    private Integer currentPoint; //diem hien tai

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
        currentPoint = 0;
        btnKetQua.setDisable(true);
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
                // last row
                if (rs.isAfterLast()) {

                    String sqlUpdate = "update points set diem= ? where name = ?";
                    PreparedStatement preS = connect.prepareStatement(sqlUpdate);
                    preS.setInt(1, currentPoint);
                    preS.setString(2, HomeController.name);
                    int i = preS.executeUpdate();
                    if (i == 1) {
                        textField();
                        AlertDialog.infoBox(Alert.AlertType.INFORMATION, "Dialog!",
                                "Click Result button to show result",
                                "Congratulation !. You finished your test ^^");
                        btnKetQua.setDisable(false);
                    }
                    return;

                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

            try {
                txtNumber.setText(rs.getString("id"));
                txtContent.setText(rs.getString("content"));
                radA.setText(rs.getString("a"));
                radB.setText(rs.getString("b"));
                radC.setText(rs.getString("c"));
                radD.setText(rs.getString("d"));
                currentAnswer = rs.getString("answer");

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    @FXML
    private void handleResult(ActionEvent event) throws IOException {
        stage = (Stage) btnKetQua.getScene().getWindow();
        stage.hide();
        EnglishAssistantUtil tf = new EnglishAssistantUtil();
        tf.transferForm("englishapp/KetQua.fxml",
                "Result");
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
                    stage = (Stage) btnExit.getScene().getWindow();
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

    @FXML
    private void handleAnswerA(ActionEvent event) {
        if ("a".equals(currentAnswer)) {
            currentPoint += 1;
        }
        updateScore(currentPoint);
        next();
    }

    private void updateScore(Integer passedscore) {
        score.setText(passedscore.toString());
    }

    @FXML
    private void handleAnswerB(ActionEvent event) {
        if ("b".equals(currentAnswer)) {
            currentPoint += 1;
        }
        updateScore(currentPoint);
        next();
    }

    @FXML
    private void handleAnswerC(ActionEvent event) {
        if ("c".equals(currentAnswer)) {
            currentPoint += 1;
        }
        updateScore(currentPoint);
        next();
    }

    @FXML
    private void handleAnswerD(ActionEvent event) {
        if ("d".equals(currentAnswer)) {
            currentPoint += 1;
        }
        updateScore(currentPoint);
        next();
    }

    public void textField() {
        btnNext.setDisable(true);
        radA.setDisable(true);
        radB.setDisable(true);
        radC.setDisable(true);
        radD.setDisable(true);
        radA.setSelected(false);
        radB.setSelected(false);
        radC.setSelected(false);
        radD.setSelected(false);
        radA.setText("No answer");
        radB.setText("No answer");
        radC.setText("No answer");
        radD.setText("No answer");
        txtNumber.setText("0");
        txtTotal.setText("Total question: 0");
        currentPoint = 0;
        txtContent.setText("No question");

    }

}
