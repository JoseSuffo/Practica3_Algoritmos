package Interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorTipoPregunta {
    @FXML
    private Button filaUnica;
    private Button filasMultiples;
    private Button volver;

    @FXML
    public void filaUnica(ActionEvent event) {
        BorderPane principal = new BorderPane();
        ControladorSimulacion controlador = new ControladorSimulacion("Unica",principal);

        Scene simulador = new Scene(principal, 1200,600);
        Stage stage = new Stage();
        stage.setScene(simulador);
        stage.setResizable(false);
        stage.setTitle("Simulador de cajas con fila única. Costco Mexicali");
        stage.show();

        Node node = (Node) event.getSource();
        Stage actualStage = (Stage) node.getScene().getWindow();
        actualStage.close();
    }

    @FXML
    public void filasMultiples(ActionEvent event) {
        BorderPane principal = new BorderPane();
        ControladorSimulacion controlador = new ControladorSimulacion("Multiples", principal);

        Scene simulador = new Scene(principal, 1200,600);
        Stage stage = new Stage();
        stage.setScene(simulador);
        stage.setResizable(false);
        stage.setTitle("Simulador de cajas con filas multiples. Costco Mexicali");
        stage.show();

        Node node = (Node) event.getSource();
        Stage actualStage = (Stage) node.getScene().getWindow();
        actualStage.close();
    }

    @FXML
    public void volver(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/menu.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Costco Mexicali");
        newStage.show();

        Node node = (Node) event.getSource();
        Stage actual = (Stage) node.getScene().getWindow();
        actual.close();
    }
}
