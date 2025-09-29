package Interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ControladorMenu {
    @FXML private Button botonSimular;
    @FXML private Button botonCreditos;
    @FXML private Button botonSalir;

    @FXML
    public void botonSimular(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/tipoPregunta.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Elegir tipo de simulación");
        newStage.show();

        Node node = (Node) event.getSource();
        Stage actual = (Stage) node.getScene().getWindow();
        actual.close();
    }

    //Creación del evento al presionar el boton Creditos
    @FXML
    public void botonCreditos(ActionEvent actionEvent){
        //Se envia una alerta de información que muestra mi nombre
        Alert creditos = new Alert(Alert.AlertType.INFORMATION);
        creditos.setTitle("Creditos del programa");
        creditos.setHeaderText("Creditos del programa");
        creditos.setContentText("Hecho por José Ramón Suffo Peimbert.");
        creditos.show();
    }

    //Creación del evento al presionar el boton Salir
    @FXML
    public void botonSalir(javafx.event.ActionEvent actionEvent) {
        //Salida del programa
        System.exit(0);
    }
}