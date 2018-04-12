/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.DBConnection;
import util.EnglishAssistantUtil;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class AdminController implements Initializable {

    @FXML
    private JFXButton btnThem, btnEdit, btnUser;

    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label lblName, lblEmail;

    private Connection connect;
    private Statement statement;
    private ResultSet rs;

    private String firstname, lastname, email;
    

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = DBConnection.englishConnection();

        try {
            statement = connect.createStatement();
            rs = statement.executeQuery("select *"
                    + "from users where userid='" + HomeController.idCurrent + "'");
            rs.next();
            firstname = rs.getString("firstname");
            lastname = rs.getString("lastname");
            email = rs.getString("email");
            lblName.setText("Xin chào: " + firstname + " " + lastname);
            lblEmail.setText("Email: " + email);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleButton(ActionEvent event) throws IOException {

        if (event.getSource() == btnThem) {

        } else if (event.getSource() == btnUser) {

        } else if (event.getSource() == btnEdit) {

        }

    }

    private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }

    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = getStage();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void handleMenuClose(ActionEvent event) {
        getStage().close();
    }

    @FXML
    private void handleAddQuestion(ActionEvent event) {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("ThemCauHoi.fxml"), "Add New Question", null);
    }

    @FXML
    private void handleAdduser(ActionEvent event) {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("DangKy.fxml"), "Add User", null);
    }

    @FXML
    private void handleUserList(ActionEvent event) {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("LoaiCauHoi.fxml"), "Add New Type Question", null);
    }

    @FXML
    private void handelQuestionList(ActionEvent event) {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("SuaCauHoi.fxml"), "Edit Question", null);
    }

    @FXML
    private void handelQuestionUserList(ActionEvent event) {
        EnglishAssistantUtil.loadWindow(getClass().
                getResource("EditUser.fxml"), "Edit user", null);
    }

    @FXML
    private void handelAbout(ActionEvent event) {
    }

    @FXML
    private void handleAbout(ActionEvent event) {
    }

}
