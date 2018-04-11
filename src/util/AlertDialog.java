/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author toanten
 */
public class AlertDialog {
    public static void infoBox(AlertType type,String title, String message, String headerMessage)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
