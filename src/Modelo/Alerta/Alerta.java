/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Alerta;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class Alerta {

    public Alerta() {
    }

    public void darleEstiloAlPanel(Alert alerta) {
        DialogPane dialogPane;
        Stage alertaStage;
        dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("estilosAlertas.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alertaStage = (Stage) alerta.getDialogPane().getScene().getWindow();
        alertaStage.getIcons().add(new Image("/vista/login/images/icon.png"));
    }

}
