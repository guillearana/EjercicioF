
/**
 * Módulo principal para la aplicación EjercicioF, que gestiona y controla la información de personas.
 */
module es.guillearana.ejerciciof {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens es.guillearana.ejerciciof to javafx.fxml;
    exports es.guillearana.ejerciciof;
    exports es.guillearana.ejerciciof.controlador;
    opens es.guillearana.ejerciciof.controlador to javafx.fxml;
    opens es.guillearana.ejerciciof.model to javafx.fxml, javafx.base; // Permite acceso a clases del paquete model desde javafx.fxml y javafx.base.
}