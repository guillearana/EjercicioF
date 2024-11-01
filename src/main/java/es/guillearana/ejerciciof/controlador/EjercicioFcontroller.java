package es.guillearana.ejerciciof.controlador;

import java.io.IOException;

import es.guillearana.ejerciciof.model.Persona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.FileChooser;
import java.io.*;

/**
 * Controlador para la gestión de la vista principal de la aplicación de personas.
 * Este controlador maneja la lógica para agregar, modificar, eliminar, exportar e
 * importar personas en una lista, y actualiza la vista de la tabla.
 */
public class EjercicioFcontroller {

    /** Botón para agregar una nueva persona. */
    @FXML
    private Button btnAgregar;

    /** Botón para eliminar una persona seleccionada. */
    @FXML
    private Button btnEliminar;

    /** Botón para exportar los datos de personas a un archivo CSV. */
    @FXML
    private Button btnExportar;

    /** Botón para importar los datos de personas desde un archivo CSV. */
    @FXML
    private Button btnImportar;

    /** Botón para modificar una persona seleccionada. */
    @FXML
    private Button btnModificar;

    /** Columna de la tabla que muestra los apellidos de las personas. */
    @FXML
    private TableColumn<Persona, String> colApellidos;

    /** Columna de la tabla que muestra la edad de las personas. */
    @FXML
    private TableColumn<Persona, Integer> colEdad;

    /** Columna de la tabla que muestra el nombre de las personas. */
    @FXML
    private TableColumn<Persona, String> colNombre;

    /** Tabla para mostrar la información de las personas. */
    @FXML
    private TableView<Persona> tableInfo;

    /** Campo de texto para filtrar personas por nombre. */
    @FXML
    private TextField txtNombre;

    /** Lista observable que contiene las personas. */
    private ObservableList<Persona> personasData = FXCollections.observableArrayList();

    /**
     * Acción para agregar una nueva persona.
     * Abre una ventana modal para ingresar los datos de la nueva persona
     * y la agrega a la lista si es válida.
     *
     * @param event el evento de acción del botón "Agregar"
     */
    @FXML
    void accionAgregar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/guillearana/ejerciciof/ejerFmodal.fxml"));
            Parent root = loader.load();
            ControllerModalEjerF controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Agregar Persona");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Persona nuevaPersona = controller.getPersona();

            if (nuevaPersona != null) {
                if (personasData.contains(nuevaPersona)) {
                    mostrarAlerta("La persona ya está en la lista.");
                } else {
                    personasData.add(nuevaPersona);
                    mostrarAlerta("Persona añadida con éxito.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Acción para eliminar la persona seleccionada en la tabla.
     * Muestra una alerta de confirmación antes de eliminar.
     *
     * @param event el evento de acción del botón "Eliminar"
     */
    @FXML
    void accionEliminar(ActionEvent event) {
        Persona selected = tableInfo.getSelectionModel().getSelectedItem();
        if (selected != null) {
            personasData.remove(selected);
            mostrarAlerta("Persona eliminada con éxito.");
        } else {
            mostrarAlerta("Seleccione una persona para eliminar.");
        }
    }

    /**
     * Acción para exportar los datos de personas a un archivo CSV.
     * Muestra un cuadro de diálogo para elegir la ubicación del archivo
     * y guarda la lista de personas en formato CSV.
     *
     * @param event el evento de acción del botón "Exportar"
     */
    @FXML
    void exportarPulsado(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Nombre,Apellidos,Edad\n");
                for (Persona persona : tableInfo.getItems()) {
                    writer.write(String.format("\"%s\",\"%s\",%d\n",
                            persona.getNombre(),
                            persona.getApellidos(),
                            persona.getEdad()));
                }
                mostrarAlerta("Datos exportados con éxito.");
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarAlerta("Error al exportar los datos: " + ex.getMessage());
            }
        }
    }

    /**
     * Acción para importar datos de personas desde un archivo CSV.
     * Permite al usuario seleccionar un archivo CSV e importa los datos
     * a la lista de personas, validando el formato y duplicados.
     *
     * @param event el evento de acción del botón "Importar"
     */
    @FXML
    void importarPulsado(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine(); // Ignorar la primera línea (cabecera)
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length != 3) {
                        mostrarAlerta("Error: formato de línea inválido - " + line);
                        continue;
                    }
                    try {
                        String nombre = data[0].trim();
                        String apellidos = data[1].trim();
                        int edad = Integer.parseInt(data[2].trim());

                        Persona persona = new Persona(nombre, apellidos, edad);
                        if (!personasData.contains(persona)) {
                            personasData.add(persona);
                        } else {
                            mostrarAlerta("La persona " + nombre + " " + apellidos + " ya existe en la tabla.");
                        }
                    } catch (NumberFormatException e) {
                        mostrarAlerta("Error: la edad debe ser un número válido en la línea - " + line);
                    }
                }
                mostrarAlerta("Datos importados con éxito.");
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarAlerta("Error al importar los datos: " + ex.getMessage());
            }
        }
    }

    /**
     * Acción para modificar una persona seleccionada en la tabla.
     * Abre una ventana modal para editar los datos de la persona seleccionada.
     *
     * @param event el evento de acción del botón "Modificar"
     */
    @FXML
    void accionModificar(ActionEvent event) {
        Persona personaSeleccionada = tableInfo.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/guillearana/ejerciciof/ejerFmodal.fxml"));
                Parent root = loader.load();
                ControllerModalEjerF controller = loader.getController();
                controller.setPersona(personaSeleccionada);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Editar Persona");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                Persona personaModificada = controller.getPersona();
                if (personaModificada != null) {
                    personaSeleccionada.setNombre(personaModificada.getNombre());
                    personaSeleccionada.setApellidos(personaModificada.getApellidos());
                    personaSeleccionada.setEdad(personaModificada.getEdad());
                    tableInfo.refresh();
                    mostrarAlerta("Persona modificada con éxito.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inicializa el controlador configurando las columnas de la tabla y el filtro de búsqueda.
     */
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        colEdad.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : Integer.toString(item));
                setAlignment(Pos.CENTER_RIGHT);
            }
        });

        FilteredList<Persona> filteredData = new FilteredList<>(personasData, p -> true);
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(persona -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return persona.getNombre().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Persona> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableInfo.comparatorProperty());
        tableInfo.setItems(sortedData);
    }

    /**
     * Muestra una alerta informativa al usuario.
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
