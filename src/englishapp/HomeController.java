/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.DBConnection;
import util.time;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.EnglishAssistantUtil;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class HomeController implements Initializable {

    private Connection connect;
    private Statement statement;
    private ResultSet rs;

    @FXML
    private Label datePicker;
    @FXML
    private Label timePicker;
    @FXML
    private Label lblName;

    @FXML
    private ImageView imgSetting;
    @FXML
    private ImageView imgPicture;
    @FXML
    private ImageView imgGrammar;
    @FXML
    private ImageView imgAudio;
    @FXML
    private ImageView imgExit;

    private String firstName, lastName, quyenId;
    static String name; // get name
    static Integer idCurrent; // get id
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load time
        bindToTime();
        time time = new time();
        timePicker.setText(time.tanggal());
        // load name
        connect = DBConnection.englishConnection();
        try {
            statement = connect.createStatement();
            rs = statement.executeQuery("select * "
                    + "from users where userid='" + idCurrent + "'");
            rs.next();
            firstName = rs.getString("firstname");
            lastName = rs.getString("lastname");
            quyenId = rs.getString("quyenid");
            lblName.setText("Xin chào : " + firstName + " " + lastName);
            // phan quyen
            if ("U".equals(quyenId)) {
                imgSetting.setDisable(true);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }
    // load time and date

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), (ActionEvent actionEvent) -> {
                    Calendar time = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    datePicker.setText(simpleDateFormat.format(time.getTime()));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void handleAction(MouseEvent event) throws IOException {
        if (event.getSource() == imgExit) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Do you want to close application ?");
            alert.setContentText(null);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    exit(0);
                } else {
                    alert.close();
                }
            });
        } else if (event.getSource() == imgSetting) {
            stage = (Stage) imgSetting.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/Admin.fxml",
                    "Administrator");
        } else if (event.getSource() == imgAudio) {
            stage = (Stage) imgAudio.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/DienKhuyet.fxml",
                    "Blank Grammar");
        } else if (event.getSource() == imgGrammar) {
            stage = (Stage) imgGrammar.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/NguPhap.fxml",
                    "Grammar Practice");
        } else if (event.getSource() == imgPicture) {
            stage = (Stage) imgPicture.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/HinhAnh.fxml",
                    "Picture Practice");
        }

    }

}
