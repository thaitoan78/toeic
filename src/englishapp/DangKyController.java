/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.AlertDialog;
import util.KiemTraTextField;
import util.DBConnection;
import models.Role;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class DangKyController implements Initializable {

    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXButton btnDangKy;
    @FXML
    private JFXButton btnQuayLai;
    private PreparedStatement pre;
    private Connection connect;
    private ResultSet rs;
    @FXML
    private ComboBox<Role> cmbQuyen;
    private ObservableList<Role> role;
    @FXML
    private Label errUsername;
    @FXML
    private Label errPassword;
    @FXML
    private Label errEmail;
    @FXML
    private Label errFirstname;
    @FXML
    private Label errLastname;
    private String roleID;
    @FXML
    private Label errCmb;

    /**
     * Initializes the controller class.
     *
     * @param stage
     * @throws java.io.IOException
     */
    public void start(final Stage stage) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("DangKy.fxml"));
        Scene homescene = new Scene(home);
        homescene.setFill(Color.TRANSPARENT);
        stage.setTitle("Sign Up");
        stage.setResizable(false);
        stage.setScene(homescene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load comboox
        connect = DBConnection.englishConnection();
        role = FXCollections.observableArrayList();
        try {
            PreparedStatement preStament = connect.
                    prepareStatement("Select * from phanquyen");
            rs = preStament.executeQuery();
            while (rs.next()) {
                role.add(new Role(rs.getString(1), rs.getString(2)));
            }
            cmbQuyen.setItems(role);

        } catch (SQLException ex) {
            Logger.getLogger(DangKyController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        // hien thi 
        cmbQuyen.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role object) {
                return object.getName();
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });
        // get value
        cmbQuyen.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                roleID = newValue.getId();

            }
        });
    }
// dang ky thanh vien

    @FXML
    private void regButton(ActionEvent event) {

        boolean isValidEmail = KiemTraTextField.isValidEmail(
                txtEmail, errEmail, "Email valid");
        boolean kiemtraUsername = KiemTraTextField.kiemTraTextFieldEmpty(
                txtUserName, errUsername, "Enter your username");
        boolean kiemtraPassword = KiemTraTextField.kiemTraPasswordFieldEmpty(
                txtPassword, errPassword, "Enter your password");
        boolean kiemtraFirstname = KiemTraTextField.kiemTraTextFieldEmpty(
                txtFirstName, errFirstname, "Enter your firstname");
        boolean kiemtraLastname = KiemTraTextField.kiemTraTextFieldEmpty(
                txtLastName, errLastname, "Enter your lastname");
        boolean kiemtraQuyen = KiemTraTextField.kiemTraCombobox(
                cmbQuyen, errCmb, "Choose your permission");

        if (isValidEmail && kiemtraFirstname &&
                kiemtraLastname && kiemtraPassword &&
                kiemtraUsername && kiemtraQuyen) {
            try {
                connect.setAutoCommit(false);
                String userID = UUID.randomUUID().toString();
                String insert = "INSERT INTO users(mauser,"
                        + " username,pass,"
                        + "firstname,lastname,"
                        + "email,quyenid) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                pre = this.connect.prepareStatement(insert);
                pre.setString(1, userID);
                pre.setString(2, txtUserName.getText().trim());
                pre.setString(3, txtPassword.getText().trim());
                pre.setString(4, txtFirstName.getText().trim());
                pre.setString(5, txtLastName.getText().trim());
                pre.setString(6, txtEmail.getText().trim());
                pre.setString(7, roleID.trim());
                int i = pre.executeUpdate();
                String insertSql = "INSERT INTO points(name) values (?)";
                pre = this.connect.prepareStatement(insertSql);
                pre.setString(1, txtUserName.getText().trim());
                int j = pre.executeUpdate();
                
                if (i ==1 && j == 1) {
                    AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                            "Add user successfully!", "Information!", "Insert!");
                    clearText();

                } else {
                    AlertDialog.infoBox(Alert.AlertType.ERROR,
                            "Add user failed", "Fail!", "Inserted");
                    txtUserName.requestFocus();
                }
                connect.commit();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }

    }
// back 

    @FXML
    private void backButton(ActionEvent event) throws IOException {
        if (event.getSource() == btnQuayLai) {
            clearText();
        }
    }

    public void clearText() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtUserName.setText("");

    }
}
