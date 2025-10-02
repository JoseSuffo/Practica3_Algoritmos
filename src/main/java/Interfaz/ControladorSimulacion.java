package Interfaz;

import Costco.Caja;
import Costco.SimulacionCostco;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControladorSimulacion {
    private String tipoSimulacion;
    private SimulacionCostco simulacion;

    private BorderPane principal;
    private HBox cajas = new HBox(16);
    private Button botonCancelaryVolver = new Button("Cancelar y Volver");
    private Label etiquetaTiempo = new Label();

    public ControladorSimulacion(String tipoSimulacion, BorderPane principal) {
        this.tipoSimulacion = tipoSimulacion;
        this.simulacion = new SimulacionCostco(tipoSimulacion);
        this.principal = principal;
        crearInterfaz();
        comenzarSimulacion();
    }

    public void crearInterfaz(){
        principal.setStyle("-fx-background-color: #f0f0f0;");
        botonCancelaryVolver.setOnAction(e -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/vistas/menu.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Costco Mexicali");
            newStage.show();

            Node node = (Node) e.getSource();
            Stage actual = (Stage) node.getScene().getWindow();
            actual.close();
        });
        etiquetaTiempo.setText("Tiempo: " + simulacion.getTiempo());
        VBox filaInferior = new VBox(8);
        filaInferior.setAlignment(Pos.CENTER);
        filaInferior.setAlignment(Pos.CENTER);
        filaInferior.getChildren().addAll(etiquetaTiempo, botonCancelaryVolver);
        principal.setBottom(filaInferior);
        principal.setTop(cajas);
        mostrarCajas();
    }

    public void mostrarCajas() {
        cajas.getChildren().clear();
        for (Caja caja : simulacion.getCajas()) {
            VBox cajaVisual = new VBox(4);
            cajaVisual.setAlignment(Pos.CENTER);

            Label titulo = new Label("Caja " + caja.getNumCaja());
            Label estado = new Label(caja.cajaAbierta() ? "Abierta" : "Cerrada");
            Label clientes = new Label("Clientes: " + caja.getNumClientes());

            ImageView imagenCaja = new ImageView(
                    new Image(getClass().getResourceAsStream(
                            caja.cajaAbierta() ? "/imagenes/cajaAbierta.png" : "/imagenes/cajaCerrada.png"
                    ))
            );
            imagenCaja.setFitWidth(60);
            imagenCaja.setFitHeight(60);

            cajaVisual.getChildren().addAll(titulo, imagenCaja, estado, clientes);
            cajas.getChildren().add(cajaVisual);
        }
    }

    public void actualizarVista() {
        etiquetaTiempo.setText("Tiempo: " + simulacion.getTiempo());
        mostrarCajas();
    }

    public void comenzarSimulacion() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            simulacion.flujoSimulacion();
            actualizarVista();
        }));
        timeline.setCycleCount(600);
        timeline.play();
    }
}