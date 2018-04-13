/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.AlertDialog;
import util.KiemTraTextField;
import util.DBConnection;
import models.LoaiCauHoi;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import util.EnglishAssistantUtil;

/**
 *
 * @author Admin
 */
public class ThemCauHoiController implements Initializable {

    @FXML
    private JFXTextField txtQuestion, txtChoiceA,
            txtChoiceB, txtChoiceC, txtChoiceD, txtDapAn;

    @FXML
    private Button btnThem, btnBack;

    @FXML
    private Label errCauhoi, errA, errB, errC, errD, errDapAn, errCombobox;

    @FXML
    private ComboBox<LoaiCauHoi> cmbType;
    private ObservableList<LoaiCauHoi> list;
    private String maloai; // ma loai

    @FXML
    private VBox pane;
    private Stage stage;
    private Connection connect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = DBConnection.englishConnection();
        // load combobox 
        loadCombobox();

    }

    public void loadCombobox() {
        list = FXCollections.observableArrayList();
        try {
            PreparedStatement preStament = connect.
                    prepareStatement("Select * from loaicauhoi");
            ResultSet rs = preStament.executeQuery();
            while (rs.next()) {
                list.add(new LoaiCauHoi(rs.getString(1), rs.getString(2)));

            }
            cmbType.setItems(list);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // get name
        cmbType.setConverter(new StringConverter<LoaiCauHoi>() {
            @Override
            public String toString(LoaiCauHoi object) {
                //
                return object.getName();
            }

            @Override
            public LoaiCauHoi fromString(String string) {
                return null;
            }
        });
        // get id
        cmbType.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                maloai = newValue.getMaloai();
            }
        });
    }

    @FXML
    public void addAction(ActionEvent event) throws IOException, SQLException {
        boolean isMyComboBoxEmpty = KiemTraTextField.
                kiemTraCombobox(cmbType, errCombobox, "Please choose type question");
        boolean kiemTraCauHoi = KiemTraTextField.
                kiemTraTextFieldEmpty(txtQuestion, errCauhoi, "Please fill question");
        boolean kiemtraA = KiemTraTextField.
                kiemTraTextFieldEmpty(txtChoiceA, errA, "Please fill answer");
        boolean kiemtraB = KiemTraTextField.
                kiemTraTextFieldEmpty(txtChoiceB, errB, "Please fill answer");
        boolean kiemtraC = KiemTraTextField.
                kiemTraTextFieldEmpty(txtChoiceC, errC, "Please fill answer");
        boolean kiemtraD = KiemTraTextField.
                kiemTraTextFieldEmpty(txtChoiceD, errD, "Please fill answer");
        boolean kiemtraDapAn = KiemTraTextField.
                kiemTraTextFieldEmpty(txtDapAn, errDapAn, "Please fill result");

        String query = "INSERT INTO question(loaicauhoi, content, a ,b ,c ,d ,answer) "
                + "VALUES(? ,? ,? ,? ,? ,?, ?)";
        if (kiemTraCauHoi && kiemtraA
                && kiemtraB && kiemtraC
                && kiemtraD && kiemtraDapAn
                && isMyComboBoxEmpty) {
            try {
                connect.setAutoCommit(false);
                PreparedStatement pStm = this.connect.prepareStatement(query);
                pStm.setString(1, maloai);
                pStm.setString(2, txtQuestion.getText().trim());
                pStm.setString(3, txtChoiceA.getText().trim());
                pStm.setString(4, txtChoiceB.getText().trim());
                pStm.setString(5, txtChoiceC.getText().trim());
                pStm.setString(6, txtChoiceD.getText().trim());
                pStm.setString(7, txtDapAn.getText().trim());
                int i = pStm.executeUpdate();

                if (i == 1) {
                    AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                            "Insert successfully ", null, "Thông báo");
                    clearText();
                    txtQuestion.requestFocus();
                } else {
                    AlertDialog.infoBox(Alert.AlertType.ERROR,
                            "Insert failed ", null, "Thông báo");
                }
                connect.commit();

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        } else {
            txtQuestion.getStyleClass().add("errLogin");
            txtChoiceA.getStyleClass().add("errLogin");
            txtChoiceB.getStyleClass().add("errLogin");
            txtChoiceC.getStyleClass().add("errLogin");
            txtChoiceD.getStyleClass().add("errLogin");
            txtDapAn.getStyleClass().add("errLogin");
            cmbType.getStyleClass().add("errLogin");
        }

    }

    @FXML
    public void resetAction(ActionEvent event) throws IOException {
        stage = (Stage) btnBack.getScene().getWindow();
        stage.hide();
        EnglishAssistantUtil tf = new EnglishAssistantUtil();
        tf.transferForm("englishapp/Admin.fxml",
                "Administrator");

    }

    public void clearText() {
        txtQuestion.setText("");
        txtChoiceA.setText("");
        txtChoiceB.setText("");
        txtChoiceC.setText("");
        txtChoiceD.setText("");
        txtDapAn.setText("");

    }
}
