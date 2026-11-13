package com.seguridad;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class MainApp extends Application {

    private final PersonalDAO dao = new PersonalDAO();
    private final ObservableList<Personal> datos = FXCollections.observableArrayList();

    private TableView<Personal> tabla;
    private TextField txtId, txtNombres, txtApellidos, txtDni, txtTelefono;
    private ComboBox<String> cbCargo, cbTurno, cbEstado;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Empresa de Seguridad - Gestión de Personal");

        VBox root = new VBox(15, crearTitulo(), crearFormulario(), crearBotones(), crearTabla());
        root.setPadding(new Insets(20));
        VBox.setVgrow(tabla, Priority.ALWAYS);

        stage.setScene(new Scene(root, 950, 650));
        stage.show();

        cargarTabla();
    }

    // ---------- Construcción de la interfaz ----------

    private Label crearTitulo() {
        Label lbl = new Label("Gestión de Personal de Seguridad");
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0284c7;");
        return lbl;
    }

    private GridPane crearFormulario() {
        txtId = new TextField();
        txtId.setEditable(false);
        txtId.setPromptText("Automático");
        txtNombres = new TextField();
        txtApellidos = new TextField();
        txtDni = new TextField();
        txtDni.setPromptText("8 dígitos");
        txtTelefono = new TextField();
        txtTelefono.setPromptText("9 dígitos");

        cbCargo = new ComboBox<>(FXCollections.observableArrayList(
                "Vigilante", "Guardia", "Supervisor", "Jefe de seguridad", "Operador de monitoreo"));
        cbTurno = new ComboBox<>(FXCollections.observableArrayList("Diurno", "Nocturno", "Mixto"));
        cbEstado = new ComboBox<>(FXCollections.observableArrayList("Activo", "Inactivo"));
        cbCargo.setMaxWidth(Double.MAX_VALUE);
        cbTurno.setMaxWidth(Double.MAX_VALUE);
        cbEstado.setMaxWidth(Double.MAX_VALUE);

        GridPane g = new GridPane();
        g.setHgap(12);
        g.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(12);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(38);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(12);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(38);
        g.getColumnConstraints().addAll(c1, c2, c3, c4);

        g.addRow(0, new Label("ID:"), txtId, new Label("DNI:"), txtDni);
        g.addRow(1, new Label("Nombres:"), txtNombres, new Label("Apellidos:"), txtApellidos);
        g.addRow(2, new Label("Teléfono:"), txtTelefono, new Label("Cargo:"), cbCargo);
        g.addRow(3, new Label("Turno:"), cbTurno, new Label("Estado:"), cbEstado);
        return g;
    }

    private HBox crearBotones() {
        Button btnRegistrar = new Button("Registrar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");

        btnRegistrar.setOnAction(e -> registrar());
        btnActualizar.setOnAction(e -> actualizar());
        btnEliminar.setOnAction(e -> eliminar());
        btnLimpiar.setOnAction(e -> limpiar());

        for (Button b : new Button[]{btnRegistrar, btnActualizar, btnEliminar, btnLimpiar}) {
            b.setPrefWidth(120);
        }
        btnRegistrar.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #0284c7; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white;");

        HBox box = new HBox(12, btnRegistrar, btnActualizar, btnEliminar, btnLimpiar);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    @SuppressWarnings("unchecked")
    private TableView<Personal> crearTabla() {
        tabla = new TableView<>(datos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.getColumns().addAll(
                columna("ID", "idPersonal"),
                columna("Nombres", "nombres"),
                columna("Apellidos", "apellidos"),
                columna("DNI", "dni"),
                columna("Teléfono", "telefono"),
                columna("Cargo", "cargo"),
                columna("Turno", "turno"),
                columna("Estado", "estado"));

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, p) -> {
            if (p != null) llenarFormulario(p);
        });
        return tabla;
    }

    private TableColumn<Personal, ?> columna(String titulo, String propiedad) {
        TableColumn<Personal, Object> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        return col;
    }

    // ---------- Operaciones CRUD ----------

    private void cargarTabla() {
        try {
            datos.setAll(dao.listar());
        } catch (SQLException ex) {
            error("No se pudo conectar o consultar la base de datos.\n"
                + "Verifica que MySQL esté encendido, que ejecutaste sql/empresa_seguridad.sql "
                + "y que usuario/contraseña en Conexion.java sean correctos.\n\nDetalle: " + ex.getMessage());
        }
    }

    private void registrar() {
        Personal p = leerFormulario(false);
        if (p == null) return;
        try {
            dao.insertar(p);
            info("Registro agregado correctamente.");
            limpiar();
            cargarTabla();
        } catch (SQLIntegrityConstraintViolationException ex) {
            error("Ya existe un registro con ese DNI.");
        } catch (SQLException ex) {
            error("Error al registrar: " + ex.getMessage());
        }
    }

    private void actualizar() {
        if (txtId.getText().isBlank()) {
            error("Selecciona un registro de la tabla para actualizar.");
            return;
        }
        Personal p = leerFormulario(true);
        if (p == null) return;
        try {
            dao.actualizar(p);
            info("Registro actualizado correctamente.");
            limpiar();
            cargarTabla();
        } catch (SQLIntegrityConstraintViolationException ex) {
            error("Ya existe otro registro con ese DNI.");
        } catch (SQLException ex) {
            error("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminar() {
        if (txtId.getText().isBlank()) {
            error("Selecciona un registro de la tabla para eliminar.");
            return;
        }
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Seguro que deseas eliminar este registro?", ButtonType.YES, ButtonType.NO);
        conf.setHeaderText(null);
        Optional<ButtonType> r = conf.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.YES) {
            try {
                dao.eliminar(Integer.parseInt(txtId.getText()));
                info("Registro eliminado correctamente.");
                limpiar();
                cargarTabla();
            } catch (SQLException ex) {
                error("Error al eliminar: " + ex.getMessage());
            }
        }
    }

    // ---------- Utilidades ----------

    private Personal leerFormulario(boolean conId) {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String dni = txtDni.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty()) {
            error("Nombres y apellidos son obligatorios.");
            return null;
        }
        if (!dni.matches("\\d{8}")) {
            error("El DNI debe tener exactamente 8 dígitos numéricos.");
            return null;
        }
        if (!telefono.isEmpty() && !telefono.matches("\\d{9}")) {
            error("El teléfono debe tener 9 dígitos numéricos (o déjalo vacío).");
            return null;
        }
        if (cbCargo.getValue() == null || cbTurno.getValue() == null || cbEstado.getValue() == null) {
            error("Selecciona cargo, turno y estado.");
            return null;
        }

        Personal p = new Personal();
        if (conId) p.setIdPersonal(Integer.parseInt(txtId.getText()));
        p.setNombres(nombres);
        p.setApellidos(apellidos);
        p.setDni(dni);
        p.setTelefono(telefono.isEmpty() ? null : telefono);
        p.setCargo(cbCargo.getValue());
        p.setTurno(cbTurno.getValue());
        p.setEstado(cbEstado.getValue());
        return p;
    }

    private void llenarFormulario(Personal p) {
        txtId.setText(String.valueOf(p.getIdPersonal()));
        txtNombres.setText(p.getNombres());
        txtApellidos.setText(p.getApellidos());
        txtDni.setText(p.getDni());
        txtTelefono.setText(p.getTelefono() == null ? "" : p.getTelefono());
        cbCargo.setValue(p.getCargo());
        cbTurno.setValue(p.getTurno());
        cbEstado.setValue(p.getEstado());
    }

    private void limpiar() {
        txtId.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtDni.clear();
        txtTelefono.clear();
        cbCargo.setValue(null);
        cbTurno.setValue(null);
        cbEstado.setValue(null);
        tabla.getSelectionModel().clearSelection();
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }

    private void error(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
