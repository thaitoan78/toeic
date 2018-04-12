/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package englishapp;

import util.AlertDialog;
import util.KiemTraTextField;
import models.QuestionList;
import util.DBConnection;
import models.LoaiCauHoi;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import util.EnglishAssistantUtil;

/**
 * FXML Controller class
 *
 * @author toanten
 */
public class SuaCauHoiController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCauHoi;
    @FXML
    private TableColumn<?, ?> colA;
    @FXML
    private TableColumn<?, ?> colB;
    @FXML
    private TableColumn<?, ?> colC;
    @FXML
    private TableColumn<?, ?> colD;
    @FXML
    private TableColumn<?, ?> colDapAn;

    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnReset;
    @FXML
    private JFXButton btnBack;

    private Connection connect;
    private PreparedStatement preS;
    private ResultSet rs;

    @FXML
    private JFXTextField txtCauHoi;
    @FXML
    private JFXTextField txtA;
    @FXML
    private JFXTextField txtB;
    @FXML
    private JFXTextField txtC;
    @FXML
    private JFXTextField txtD;
    @FXML
    private JFXTextField txtDapAn;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtMaCauHoi;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableColumn<?, ?> colId;
    private TableColumn<?, ?> colLoaiCauHoi;
    @FXML
    private ComboBox<LoaiCauHoi> cmbType;
    private ObservableList<LoaiCauHoi> listLoaiCauHoi;
    @FXML
    private TableView<QuestionList> tableView;
    private ObservableList<QuestionList> listCauHoi;
    private String maloai;
    @FXML
    private Label errCauHoi;
    @FXML
    private Label errCombobox;
    @FXML
    private Label errA;
    @FXML
    private Label errB;
    @FXML
    private Label errC;
    @FXML
    private Label errDapAn;
    @FXML
    private Label errD;
    @FXML
    private JFXTextField txtName;
    @FXML
    private TableColumn<?, ?> colName;

    private Stage stage;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     *
     * @throws java.io.IOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connect = DBConnection.englishConnection();
        listCauHoi = FXCollections.observableArrayList();
        listLoaiCauHoi = FXCollections.observableArrayList();
        loadDatabase();
        loadColumn();
        setCellValueFromTableToTextField();
        loadCombobox();

    }

    //load combobox
    private void loadCombobox() {
        try {
            preS = connect.prepareStatement("Select * from loaicauhoi");
            rs = preS.executeQuery();
            while (rs.next()) {
                listLoaiCauHoi.add(new LoaiCauHoi(rs.getString(1),
                        rs.getString(2)));
            }
            cmbType.setItems(listLoaiCauHoi);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // hien thi name
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
        // get value
        cmbType.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                maloai = newValue.getMaloai();

            }
        });
    }
// load cot 

    private void loadColumn() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        colCauHoi.setCellValueFactory(new PropertyValueFactory<>("content"));
        colA.setCellValueFactory(new PropertyValueFactory<>("a"));
        colB.setCellValueFactory(new PropertyValueFactory<>("b"));
        colC.setCellValueFactory(new PropertyValueFactory<>("c"));
        colD.setCellValueFactory(new PropertyValueFactory<>("d"));
        colDapAn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
// load du lieu 

    private void loadDatabase() {
        listCauHoi.clear();
        try {
            String sql = "SELECT q.*,l.name "
                    + "FROM question q INNER JOIN loaicauhoi l "
                    + "ON q.loaicauhoi = l.maloai";
            preS = connect.prepareStatement(sql);
            rs = preS.executeQuery();
            while (rs.next()) {
                listCauHoi.add(new QuestionList(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(10)));
            }
            tableView.setItems(listCauHoi);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
// load len textField

    private void setCellValueFromTableToTextField() {
        try {
            tableView.setOnMouseClicked(e -> {
                QuestionList questionList = tableView.getItems().get(tableView.
                        getSelectionModel().getSelectedIndex());
                txtID.setText(questionList.getId());
                txtMaCauHoi.setText(questionList.getLoaicauhoi());
                txtCauHoi.setText(questionList.getContent());
                txtA.setText(questionList.getA());
                txtB.setText(questionList.getB());
                txtC.setText(questionList.getC());
                txtD.setText(questionList.getD());
                txtDapAn.setText(questionList.getAnswer());
                txtName.setText(questionList.getName());
                
            });
        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());

        }

    }

    @FXML
    private void handleButton(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            stage = (Stage) btnBack.getScene().getWindow();
            stage.hide();
            EnglishAssistantUtil tf = new EnglishAssistantUtil();
            tf.transferForm("englishapp/Admin.fxml",
                    "Administrator");
        } else if (event.getSource() == btnReset) {
            clearText();
        } else if (event.getSource() == btnUpdate) {
            String id = txtID.getText();
            String cauhoi = txtCauHoi.getText();
            String a = txtA.getText();
            String b = txtB.getText();
            String c = txtC.getText();
            String d = txtD.getText();
            String dapAn = txtDapAn.getText();
            boolean isMyComboBoxEmpty = KiemTraTextField.
                    kiemTraCombobox(cmbType, errCombobox,
                            "Please choose type question");
            boolean kiemTraCauHoi = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtCauHoi, errCauHoi,
                            "Please fill question");
            boolean kiemtraA = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtA, errA, "Please fill answer");
            boolean kiemtraB = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtB, errB, "Please fill answer");
            boolean kiemtraC = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtC, errC, "Please fill answer");
            boolean kiemtraD = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtD, errD, "Please fill answer");
            boolean kiemtraDapAn = KiemTraTextField.
                    kiemTraTextFieldEmpty(txtDapAn, errDapAn, "Please fill result");

            String updateSql = "update question "
                    + "set loaicauhoi = ? ,"
                    + "  content = ?,"
                    + " a = ?, b= ? ,"
                    + "c = ?, d= ?,"
                    + " answer = ?"
                    + "where id= ?";
            if (kiemtraDapAn && kiemTraCauHoi
                    && kiemtraA && kiemtraB && kiemtraC
                    && kiemtraD && isMyComboBoxEmpty) {

                try {

                    preS = connect.prepareStatement(updateSql);
                    preS.setString(1, maloai);
                    preS.setString(2, cauhoi);
                    preS.setString(3, a);
                    preS.setString(4, b);
                    preS.setString(5, c);
                    preS.setString(6, d);
                    preS.setString(7, dapAn);
                    preS.setString(8, id);
                    int i = preS.executeUpdate();
                    if (i == 1) {
                        AlertDialog.infoBox(Alert.AlertType.INFORMATION,
                                "Update successfully!", null, "Information!");
                        loadDatabase();
                        clearText();
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
                txtCauHoi.getStyleClass().add("errLogin");
                txtA.getStyleClass().add("errLogin");
                txtB.getStyleClass().add("errLogin");
                txtC.getStyleClass().add("errLogin");
                txtD.getStyleClass().add("errLogin");
                txtDapAn.getStyleClass().add("errLogin");
            }

        }
    }

    //
    public void clearText() {
        txtCauHoi.setText("");
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        txtD.setText("");
        txtDapAn.setText("");
        cmbType.getSelectionModel().clearSelection();

    }
// tim kiem bang union

    @FXML
    private void handleSearch(KeyEvent event) {

        if (txtSearch.getText().equals("")) {
            loadDatabase();
        } else {
            listCauHoi.clear();
            String searchSql = "Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and q.content "
                    + "like '%" + txtSearch.getText() + "%'"
                    + "UNION Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and q.a "
                    + "like '%" + txtSearch.getText() + "%'"
                    + "UNION Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and q.b "
                    + "like '%" + txtSearch.getText() + "%'"
                    + "UNION Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and q.c "
                    + "like '%" + txtSearch.getText() + "%'"
                    + "UNION Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and q.d "
                    + "like '%" + txtSearch.getText() + "%'"
                    + "UNION Select q.* , l.name from question q , loaicauhoi l "
                    + "where q.loaicauhoi=l.maloai and l.name "
                    + "like '%" + txtSearch.getText() + "%'";
            try {
                preS = connect.prepareStatement(searchSql);
                rs = preS.executeQuery();
                while (rs.next()) {
                    listCauHoi.add(new QuestionList(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(10)
                    ));
                }
                tableView.setItems(listCauHoi);
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }

        }
    }

}
