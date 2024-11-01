package es.guillearana.ejerciciof.controlador;

import es.guillearana.ejerciciof.model.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para la ventana modal de agregar o modificar una persona.
 * Permite al usuario ingresar o modificar los datos de una persona y los valida antes de guardarlos.
 */
public class ControllerModalEjerF {

    /** Botón para cancelar la acción y cerrar la ventana modal. */
    @FXML
    private Button btnCancelar;

    /** Botón para guardar la persona ingresada en la ventana modal. */
    @FXML
    private Button btnGuardar;

    /** Campo de texto para ingresar los apellidos de la persona. */
    @FXML
    private TextField txtApellidos;

    /** Campo de texto para ingresar la edad de la persona. */
    @FXML
    private TextField txtEdad;

    /** Campo de texto para ingresar el nombre de la persona. */
    @FXML
    private TextField txtNombre;

    /** Objeto Persona que almacena los datos ingresados en la ventana modal. */
    private Persona persona;

    /**
     * Acción para cancelar la operación y cerrar la ventana modal.
     * Se ejecuta al hacer clic en el botón "Cancelar".
     *
     * @param event el evento de acción del botón "Cancelar"
     */
    @FXML
    void cancelarPersona(ActionEvent event) {
        cerrarVentana();
    }

    /**
     * Acción para guardar la persona ingresada.
     * Valida los datos y, si son válidos, crea una nueva Persona y cierra la ventana modal.
     *
     * @param event el evento de acción del botón "Guardar"
     */
    @FXML
    void guardarPersona(ActionEvent event) {
        if (datosValidos()) {
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            int edad = Integer.parseInt(txtEdad.getText().trim());

            persona = new Persona(nombre, apellidos, edad);
            cerrarVentana();
        }
    }

    /**
     * Cierra la ventana modal.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Obtiene la persona creada o modificada en la ventana modal.
     *
     * @return la persona creada/modificada
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Configura la persona seleccionada para su modificación.
     * Rellena los campos de texto con los datos de la persona seleccionada.
     *
     * @param personaSeleccionada la persona seleccionada para modificar
     */
    public void setPersona(Persona personaSeleccionada) {
        this.persona = personaSeleccionada;
        if (personaSeleccionada != null) {
            txtNombre.setText(personaSeleccionada.getNombre());
            txtApellidos.setText(personaSeleccionada.getApellidos());
            txtEdad.setText(String.valueOf(personaSeleccionada.getEdad()));
        }
    }

    /**
     * Valida los datos ingresados en los campos de texto.
     * Muestra una alerta si se detectan errores en los datos.
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean datosValidos() {
        String errores = "";

        if (txtNombre.getText().isEmpty()) {
            errores += "El nombre no puede estar vacío.\n";
        }

        if (txtApellidos.getText().isEmpty()) {
            errores += "Los apellidos no pueden estar vacíos.\n";
        }

        if (txtEdad.getText().isEmpty()) {
            errores += "La edad no puede estar vacía.\n";
        } else {
            try {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                if (edad < 0) {
                    errores += "La edad no puede ser negativa.\n";
                }
            } catch (NumberFormatException e) {
                errores += "La edad debe ser un número válido.\n";
            }
        }

        if (!errores.isEmpty()) {
            mostrarAlerta(errores);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Muestra una alerta informativa.
     * Se utiliza para mostrar mensajes de error o información al usuario.
     *
     * @param mensaje el mensaje a mostrar en la alerta
     */
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
