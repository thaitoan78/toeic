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
import models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class EditUserController implements Initializable {

    @FXML
    private JFXTextField txtMauser;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtFirstname;
    @FXML
    private JFXTextField txtLastname;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXComboBox<Role> cmbType;
    @FXML
    private TableView<User> tableUser;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnClear;

    @FXML
    private TableColumn<?, ?> colNo;
    @FXML
    private TableColumn<?, ?> colFirst;
    @FXML
    private TableColumn<?, ?> colLast;
    @FXML
    private TableColumn<?, ?> colUser;
    @FXML
    private TableColumn<?, ?> colPass;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colStatus;

    private ObservableList<Role> listRole;
    private ObservableList<User> listUser;

    private Connection connect;
    private PreparedStatement preS;
    private ResultSet rs;

    private String maquyen;

    @FXML
    private Label errFirstname,errLastname,errEmail,errUsername,errPass,errCombo;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = DBConnection.englishConnection();
        listRole = FXCollections.observableArrayList();
        listUser = FXCollections.observableArrayList();
        loadDatabase();
        loadColumn();
        setCellValueFromTableToTextField();
        loadCombobox();
    }

    private void loadCombobox() {
        try {
            preS = connect.prepareStatement("Select * from phanquyen");
            rs = preS.executeQuery();
            while (rs.next()) {
                listRole.add(new Role(rs.getString(1), rs.getString(2)));
            }
            cmbType.setItems(listRole);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // hien thi name
        cmbType.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role object) {
                //
                return object.getName();
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });
        // get value
        cmbType.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                maquyen = newValue.getId();

            }

        });
    }

    private void loadDatabase() {
        listUser.clear();
        try {
            String sql = "SELECT q.*,l.NAME "
                    + "FROM users q INNER JOIN phanquyen l "
                    + "ON q.quyenid = l.quyenid";
            preS = connect.prepareStatement(sql);
            rs = preS.executeQuery();
            while (rs.next()) {
                listUser.add(new User(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(9)));
            }
            tableUser.setItems(listUser);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void loadColumn() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("userid"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        colFirst.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLast.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    private void setCellValueFromTableToTextField() {
        try {
            tableUser.setOnMouseClicked(e -> {
                User userList = tableUser.getItems().get(tableUser.
                        getSelectionModel().getSelectedIndex());
                txtID.setText(userList.getUserid());
                txtMauser.setText(userList.getMauser());
                txtUsername.setText(userList.getUsername());
                txtPassword.setText(userList.getPass());
                txtFirstname.setText(userList.getFirstname());
                txtLastname.setText(userList.getLastname());
                txtEmail.setText(userList.getEmail());
                txtName.setText(userList.getName());

            });
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
           
        }

    }

    @FXML
    private void handleAction(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            String id = txtID.getText();
            String maUser = txtMauser.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            String email = txtEmail.getText();
            String status = txtName.getText();

            boolean isMyComboBoxEmpty = KiemTraTextField.
                    kiemTraCombobox(cmbType, errCombo, "*");
            boolean kiemTraLoaiCauHoi = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtUsername, errUsername, "*");
            boolean kiemtraA = KiemTraTextField.
                    kiemTraPasswordFieldEmpty(txtPassword, errPass, "*");
            boolean kiemtraB = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtFirstname, errFirstname, "*");
            boolean kiemtraC = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtLastname, errLastname, "*");
            boolean kiemtraD = KiemTraTextField.
                    isValidEmail(txtEmail, errEmail, "*");

            String updateSql = "update users "
                    + "set mauser = ? ,"
                    + "  username = ?,"
                    + " pass = ?, firstname= ? ,"
                    + "lastname = ?, email= ?,"
                    + " quyenid = ?"
                    + "where userid= ?";
            if (kiemTraLoaiCauHoi && kiemtraA
                    && kiemtraB && kiemtraC
                    && kiemtraD && isMyComboBoxEmpty) {

                try {
                    connect.setAutoCommit(false);
                    preS = connect.prepareStatement(updateSql);
                    preS.setString(1, maUser);
                    preS.setString(2, username);
                    preS.setString(3, password);
                    preS.setString(4, firstname);
                    preS.setString(5, lastname);
                    preS.setString(6, email);
                    preS.setString(7, maquyen);
                    preS.setString(8, id);
                    int i = preS.executeUpdate();
                    String insertSql = "INSERT INTO points(name) values (?)";
                    preS = this.connect.prepareStatement(insertSql);
                    preS.setString(1, txtUsername.getText().trim());
                    int j = preS.executeUpdate();
                    if (i == 1 && j == 1) {
                        AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                                "Update successfully!", null, "Update");
                        loadDatabase();
                        clearText();
                    } else {
                        AlertDialog.infoBox(Alert.AlertType.ERROR,
                                "Update failed", null, "Error!");
                    }
                    connect.commit();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }

            } else {
                txtUsername.getStyleClass().add("errLogin");
                txtPassword.getStyleClass().add("errLogin");
                txtFirstname.getStyleClass().add("errLogin");
                txtLastname.getStyleClass().add("errLogin");
                txtEmail.getStyleClass().add("errLogin");
                cmbType.getStyleClass().add("errLogin");
            }
        } else if (event.getSource() == btnClear) {
            clearText();
        }

    }

    public void clearText() {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtPassword.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
    }

}
