/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.AlertDialog;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.EnglishAssistantUtil;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class DienKhuyetController implements Initializable {

    @FXML
    private JFXButton btnKetQua, btnNext, btnExit;

    @FXML
    private Text score, txtNumber, txtTotal, txtContent; // diem

    private Statement statement;
    private Connection connect;
    private ResultSet rs;

    private String number, currentAnswer;// so cau

    private Integer currentPoint; //diem hien tai
    private Stage stage;
    @FXML
    private JFXRadioButton radA;
    @FXML
    private JFXRadioButton radB;
    @FXML
    private JFXRadioButton radC;
    @FXML
    private JFXRadioButton radD;
    @FXML
    private ToggleGroup tongle;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentPoint = 0;
        btnKetQua.setDisable(true);
        connect = DBConnection.englishConnection();

        try {
            statement = connect.createStatement();
            String sqlCount = "Select count(*) from question q, loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai "
                    + "and l.name='Fill Scentences'";
            rs = statement.executeQuery(sqlCount);
            rs.next();
            number = rs.getString(1);

            txtTotal.setText("Total question |" + " " + number);
            rs.close();
            String sql = "Select * from question q, loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai "
                    + "and l.name='Fill Scentences' order by rand()";
            rs = statement.executeQuery(sql);
            next();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DienKhuyetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void next() throws IOException {
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
                resetRadButton();

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
    private void handleNext(ActionEvent event) throws IOException {
        next();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Exit!!!!!!!!!!");
        alert.setContentText("Do you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    stage = (Stage) btnExit.getScene().getWindow();
                    stage.hide();
                    EnglishAssistantUtil tf = new EnglishAssistantUtil();
                    tf.transferForm("englishapp/Home.fxml",
                            "Home");
                } catch (IOException ex) {
                    Logger.getLogger(DienKhuyetController.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } else {
                alert.close();
            }
        });
    }

    private void updateScore(Integer passedscore) {
        score.setText(passedscore.toString());
    }

    @FXML
    private void handleAnswerA(ActionEvent event) throws IOException {
        if ("a".equals(currentAnswer)) {
            currentPoint += 1;
        }
        updateScore(currentPoint);

    }

    @FXML
    private void handleAnswerB(ActionEvent event) throws IOException {
        if ("b".equals(currentAnswer)) {
            currentPoint += 1;

        }
        updateScore(currentPoint);

    }

    @FXML
    private void handleAnswerC(ActionEvent event) throws IOException {
        if ("c".equals(currentAnswer)) {
            currentPoint += 1;

        }
        updateScore(currentPoint);

    }

    @FXML
    private void handleAnswerD(ActionEvent event) throws IOException {
        if ("d".equals(currentAnswer)) {
            currentPoint += 1;

        }
        updateScore(currentPoint);

    }
// clear

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

    public void resetRadButton() {
        if (radA.isSelected()) {
            radA.setSelected(false);
        } else if (radB.isSelected()) {
            radB.setSelected(false);
        } else if (radC.isSelected()) {
            radC.setSelected(false);
        } else {
            radD.setSelected(false);
        }

    }
}
