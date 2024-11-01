package es.guillearana.ejerciciof;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal para la gestión de personas en la aplicación.
 * Hereda de {@link Application} para ejecutar una aplicación de JavaFX.
 * Esta clase contiene el método `start` que configura y muestra la interfaz gráfica de usuario (GUI).
 */
public class GestionPersonas extends Application {

    /**
     * Método de entrada para iniciar la interfaz gráfica de la aplicación.
     * Este método se ejecuta automáticamente cuando la aplicación es lanzada usando JavaFX.
     * Carga la interfaz definida en el archivo FXML, establece el título de la ventana y ajusta la escena.
     *
     * @param stage La ventana principal (o "escenario") de la aplicación, proporcionada por el sistema JavaFX.
     * @throws IOException Si ocurre algún error al cargar el archivo FXML, como si el archivo no existe.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML que define la interfaz de usuario.
        FXMLLoader fxmlLoader = new FXMLLoader(GestionPersonas.class.getResource("ejercicioF.fxml"));

        // Crear una nueva escena a partir del contenido cargado del FXML.
        Scene scene = new Scene(fxmlLoader.load(), 967, 591);

        // Establecer el título de la ventana.
        stage.setTitle("Personas");

        // Añadir un icono a la ventana. Cambia el nombre del archivo por el de tu icono en la ruta correcta.
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/agenda.png")));

        // Establecer las dimensiones mínimas para la ventana principal.
        stage.setMinWidth(550);
        stage.setMinHeight(550);

        // Establecer la escena en la ventana principal.
        stage.setScene(scene);

        // Mostrar la ventana.
        stage.show();
    }

    /**
     * Método main que lanza la aplicación JavaFX.
     * Este es el punto de entrada de la aplicación. Llama al método {@link #launch(String...)} que inicia la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos (si los hay).
     */
    public static void main(String[] args) {
        launch(args);  // Inicia la aplicación JavaFX.
    }
}