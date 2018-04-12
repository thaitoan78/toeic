/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.AlertDialog;
import util.DBConnection;
import util.EnglishAssistantUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class DangNhapController implements Initializable {

    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXButton btnLogin;
    private JFXButton btnCancel;
    @FXML
    private JFXPasswordField txtPassword;
    private PreparedStatement pStm;
    private Connection connect;
    private ResultSet resultSet;
    @FXML
    private JFXButton btnSignin;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param stage
     * @param url
     * @param rb
     * @throws java.io.IOException
     */
    public void start(final Stage stage) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("DangNhap.fxml"));
        Scene homescene = new Scene(home);
        homescene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.setScene(homescene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = DBConnection.englishConnection();

    }

    @FXML
    private void loginButton(ActionEvent event) throws IOException {
        try {

            String query = "SELECT * "
                    + "FROM users "
                    + "WHERE username= ? and pass= ?";
            pStm = this.connect.prepareStatement(query);
            pStm.setString(1, txtUserName.getText().trim());
            pStm.setString(2, txtPassword.getText().trim());

            resultSet = pStm.executeQuery();
            if (resultSet.next()) {
                //get id , name
                HomeController.idCurrent=resultSet.getInt("userid");
                HomeController.name=resultSet.getString("username");
                stage = (Stage) btnLogin.getScene().getWindow();
                stage.hide();
                EnglishAssistantUtil tf = new EnglishAssistantUtil();
                tf.transferForm("englishapp/Home.fxml",
                        "Home");

            } else {
                if (txtUserName.getText().length() == 0
                        || txtPassword.getText().isEmpty()) {
                    AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                            "Login", null,
                            "Username and password is not null");
                    txtUserName.getStyleClass().add("errLogin");
                    txtPassword.getStyleClass().add("errLogin");
                    txtUserName.requestFocus();
                } else {

                    AlertDialog.infoBox(
                            Alert.AlertType.ERROR, "Login", null,
                            "Username and password incorrect");
                    txtUserName.getStyleClass().add("errLogin");
                    txtPassword.getStyleClass().add("errLogin");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    private void cancelButton(ActionEvent event) {
        if (event.getSource() == btnCancel) {
            exit(0);
        }

    }

    @FXML
    private void signinButton(ActionEvent event) throws IOException {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("DangKy.fxml"), "Register", null);
    }

}
