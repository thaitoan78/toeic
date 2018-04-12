/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXTextField;
/**
 *
 * @author toanten
 */
public class KiemTraTextField {

    // kiem tra textfield
    public static boolean kiemTraTextFieldEmpty(JFXTextField tf) {
        boolean b = false;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty()) {
            b = true;
        }
        return b;
    }

    public static boolean kiemTraTextFieldEmpty(JFXTextField tf, Label lbl, String errMsg) {
        boolean b = true;
        String msg = null;
        if (!kiemTraTextFieldEmpty(tf)) {
            b = false;
            msg = errMsg;
        }
        lbl.setText(msg);
        return b;
    }

    // kiem tra nhap so
    public static boolean kiemTraTextFieldNumber(JFXTextField tf) {
        boolean b = false;
        if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+")||tf.getText().length() <=10 ) {
            b = true;
        }
        return b;
    }

    public static boolean kiemTraTextFieldNumber(JFXTextField tf, Label lbl, String errMsg) {
        boolean b = true;
        String msg = null;
        if (!kiemTraTextFieldNumber(tf)) {
            b = true;
            msg = errMsg;
        }
        lbl.setText(msg);
        return b;
    }

    // kiem tra email
    public static boolean isValidEmail(JFXTextField tf) {
        boolean b = false;
        String email = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (tf.getText().matches(email)) {
            b = true;
        }
        return b;
    }

    public static boolean isValidEmail(JFXTextField tf, Label lbl, String errMsg) {
        boolean b = true;
        String msg = null;
        if (!isValidEmail(tf)) {
            b = false;
            msg = errMsg;
        }
        lbl.setText(msg);
        return b;
    }
    // kiem tra combobox
    public static boolean kiemTraCombobox(ComboBox cmb) {
        boolean b = true;
        if (cmb.getSelectionModel().isEmpty()) {
            b = false;
        }
        return b;
    }

    public static boolean kiemTraCombobox(ComboBox cmb, Label lbl, String errMsg) {
        boolean b = true;
        String msg = null;
        if (!kiemTraCombobox(cmb)) {
            b = false;
            msg = errMsg;
        }
        lbl.setText(msg);
        return b;
    }

    public static boolean kiemTraPasswordFieldEmpty(JFXPasswordField tf) {
        boolean b = false;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty() || tf.getText().length() == 10) {
            b = true;
        }
        return b;
    }
    public static boolean kiemTraPasswordFieldEmpty(JFXPasswordField tf, Label lbl, String errMsg) {
        boolean b = true;
        String msg = null;
        if (!kiemTraPasswordFieldEmpty(tf)) {
            b = false;
            msg = errMsg;
        }
        lbl.setText(msg);
        return b;
    }
}
