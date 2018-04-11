package englishapp;

import util.DBConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.EnglishAssistantUtil;

public class KetQuaController implements Initializable {

    static String name;
    private Stage stage;

    @FXML
    private Label lblKetQua;
    @FXML
    private Label lblTen;

    private Connection connect;
    private Statement statement;
    private ResultSet rs;

    @FXML
    private JFXButton txtAgain;
    @FXML
    private JFXButton txtHome;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == txtAgain) {
            stage = (Stage) txtAgain.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/NguPhap.fxml",
                    "Grammar");

        } else if (event.getSource() == txtHome) {
            stage = (Stage) txtHome.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/Home.fxml",
                    "Home");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connect = DBConnection.englishConnection();
        String ten;
        String diem;
        try {
            statement = connect.createStatement();
            rs = statement.executeQuery("select * from points "
                    + "where name='" + name + "'");
            rs.next();
            ten = rs.getString("name");
            diem = rs.getString("diem");
            lblTen.setText("Congratulation! : " + ten);
            lblKetQua.setText("Correct answer : " + diem);
        } catch (SQLException ex) {
            Logger.getLogger(KetQuaController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

}
