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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class LoaiCauHoiController implements Initializable {

    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnClear;
    @FXML
    private TableView<LoaiCauHoi> tableTypeQuestion;

    @FXML
    private TableColumn<LoaiCauHoi, String> colLoaiCauHoi;

    private ObservableList<LoaiCauHoi> list;

    private PreparedStatement preS;
    private ResultSet rs;
    private Connection connect;

    @FXML
    private Label errLoaiCauHoi;
    private Stage stage;
    @FXML
    private JFXTextField txtTenLoai;
    @FXML
    private JFXTextField txtMaLoai;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = DBConnection.englishConnection();
        list = FXCollections.observableArrayList();
        loadDatabase();
        loadColumn();
        setCellValueFromTableToTextField();
    }

    private void loadColumn() {
        colLoaiCauHoi.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

    private void loadDatabase() {
        list.clear();
        try {
            String sql = "SELECT * from loaicauhoi";
            preS = connect.prepareStatement(sql);
            rs = preS.executeQuery();
            while (rs.next()) {
                list.add(new LoaiCauHoi(
                        rs.getString(1),
                        rs.getString(2)));
            }
            tableTypeQuestion.setItems(list);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void setCellValueFromTableToTextField() {
        tableTypeQuestion.setOnMouseClicked(e -> {
            LoaiCauHoi loaiCauHoi = tableTypeQuestion.getItems().
                    get(tableTypeQuestion.getSelectionModel().getSelectedIndex());
            txtTenLoai.setText(loaiCauHoi.getName());
            txtMaLoai.setText(loaiCauHoi.getMaloai());

        });
    }

    public void clearText() {
        txtTenLoai.setText("");

    }

    @FXML
    private void handleAction(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            boolean kiemTraLoaiCauHoi = KiemTraTextField.kiemTraTextFieldEmpty(
                    txtTenLoai, errLoaiCauHoi, "Please type question");
            if (kiemTraLoaiCauHoi) {

                String maloai = UUID.randomUUID().toString();
                String ten = txtTenLoai.getText().trim();
                String insertSql = "INSERT "
                        + "INTO loaicauhoi(maloai, name) "
                        + "VALUES (? ,?)";
                try {
                    preS = connect.prepareStatement(insertSql);
                    preS.setString(1, maloai);
                    preS.setString(2, ten);
                    int i = preS.executeUpdate();
                    if (i == 1) {

                        AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                                "Insert successfully!", null, "Insert");
                        loadDatabase();

                    } else {

                        AlertDialog.infoBox(Alert.AlertType.ERROR,
                                "Insert failed", null, "Error!");
                    }
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
            } else {
                txtTenLoai.getStyleClass().add("errLogin");
            }
        } else if (event.getSource() == btnClear) {
            clearText();
        } else if (event.getSource() == btnUpdate) {
            String maLoai = txtMaLoai.getText().trim();
            String tenloai = txtTenLoai.getText().trim();
            boolean kiemTraLoaiCauHoi = KiemTraTextField.kiemTraTextFieldEmpty(
                    txtTenLoai, errLoaiCauHoi, "Please fill type question");
            String updateSql = "update loaicauhoi "
                    + "set name = ? where maloai= ?";
            if (kiemTraLoaiCauHoi) {

                try {
                    preS = connect.prepareStatement(updateSql);
                    preS.setString(1, tenloai);
                    preS.setString(2, maLoai);
                    int i = preS.executeUpdate();
                    if (i == 1) {

                        AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                                "Update successfully!", null, "Update");
                        loadDatabase();

                    } else {
                        AlertDialog.infoBox(Alert.AlertType.ERROR,
                                "Update failed", null, "Error!");
                    }
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
            } else {
                txtTenLoai.getStyleClass().add("errLogin");
                txtTenLoai.requestFocus();
            }
        }
    }

}
