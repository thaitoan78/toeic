package englishapp;

import util.DBConnection;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
import javafx.scene.image.ImageView;
import util.EnglishAssistantUtil;

public class KetQuaController implements Initializable {

    
    private Stage stage;
 
    @FXML
    private Label lblKetQua,lblTen;
    

    private Connection connect;
    private Statement statement;
    private ResultSet rs;

    @FXML
    private JFXButton txtAgain,txtHome;
    
    @FXML
    private ImageView img;

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
        String firstname;
        String lastname;
        String diem;
        try {
            statement = connect.createStatement();
            rs = statement.executeQuery("select u.* , p.diem "
                    + "from points p , users u "
                    + "where p.name= u.username and u.userid='" + HomeController.idCurrent + "'");

            rs.next();
            firstname = rs.getString("u.firstname");
            lastname = rs.getString("u.lastname");
            diem = rs.getString("p.diem");
            lblTen.setText("Congratulation! : " + firstname + " " + lastname);
            lblKetQua.setText("Correct answer : " + diem);
            // load gif
            String stringName;
            if (Integer.valueOf(diem) < 5) {
                stringName = "bad";
                String url = String.format("images/%s.gif", stringName);
                Image i = new Image(url);
                img.setImage(i);

            } else {
                stringName = "cong";
                String url = String.format("images/%s.gif", stringName);
                Image i = new Image(url);
                img.setImage(i);
            }

        } catch (SQLException ex) {
            Logger.getLogger(KetQuaController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

}
