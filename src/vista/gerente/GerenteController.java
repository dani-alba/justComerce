package vista.gerente;

import Datos.ConexionBD;
import Datos.IncidenciaDAO;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TiendasProductosDAO;
import Datos.TrabajadorDAO;
import Modelo.Alerta.Alerta;
import Modelo.Incidencia;
import Modelo.Producto;
import Modelo.Trabajador;
import Modelo.ValidadorDNI;
import impl.com.calendarfx.view.NumericTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jfxtras.scene.control.LocalTimePicker;
import org.jasypt.util.password.StrongPasswordEncryptor;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

    private TrabajadorDAO trabajador;

    private TiendaDAO tienda;
    private ProductoDAO producto;
    private TiendasProductosDAO tiendasProductos;
    private IncidenciaDAO incidencia;
    /*IDEAL PARA GERENTE DE VARIAS TIENDAS
    private ObservableList<Tienda> listaTiendas;*/
    private ObservableList<Trabajador> listaObservableTrabajadores;
    private ObservableList<Producto> listaProductos;
    private ObservableList<String> categorias;
    private Alerta estiloAlerta;
    private Trabajador gerenteActual;
    private ValidadorDNI validadorDni;
    private String eleccion;
    private boolean campoDespedirVacio;
    private List<Incidencia> listaDeIncidencias;
    /*ATRIBUTOS FXML*/
    @FXML
    private AnchorPane ac_gerente;
    @FXML
    private Pane pn_general;
    @FXML
    private Pane pn_titulo;
    @FXML
    private Button bt_home;
    @FXML
    private Button bt_perfil;
    @FXML
    private Button bt_ayuda;
    @FXML
    private Button bt_cerrarSesion;
    @FXML
    private Button bt_despedirPersonal;
    @FXML
    private Button bt_contratarPersonal;
    @FXML
    private Button bt_atras;
    @FXML
    private Button bt_incidencias;
    @FXML
    private Button bt_productos;
    @FXML
    private Button bt_tienda;
    @FXML
    private Button bt_personal;
    @FXML
    private Pane pn_inicio;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_apellido1;
    @FXML
    private TextField tf_apellido2;
    @FXML
    private TextField tf_nick;
    @FXML
    private TextField tf_dni;
    @FXML
    private Button bt_contratar;
    @FXML
    private Pane pn_menuTrabajadores;
    @FXML
    private Pane pn_contratar;
    @FXML
    private Pane pn_productos;
    @FXML
    private TableView<Producto> tv_productos;
    @FXML
    private Button bt_atrasContratar;
    @FXML
    private Button bt_atrasProductos;
    @FXML
    private Pane pn_despedir;
    @FXML
    private Button bt_atrasDespedir;
    @FXML
    private Button bt_despedir;
    @FXML
    private DatePicker dp_fecha;
    @FXML
    private Label lb_id;
    /*IDEAL PARA EL GERENTE DE VARIAS TIENDAS
    private ComboBox<Tienda> cb_tiendas;*/
    @FXML
    private TableColumn<Producto, Integer> tb_referencia;
    @FXML
    private TableColumn<Producto, String> tb_nombre;
    @FXML
    private TableColumn<Producto, String> tb_categoria;
    @FXML
    private TableColumn<Producto, String> tb_descripcion;
    @FXML
    private TableColumn<Producto, Double> tb_precioCompra;
    @FXML
    private TableColumn<Producto, Double> tb_precioVenta;
    @FXML
    private TableColumn<Producto, Double> tb_iva;
    @FXML
    private LocalTimePicker dp_horaEntrada;
    @FXML
    private Label lb_fondoHoraEntrada;
    @FXML
    private Label lb_fondoHoraSalida;
    @FXML
    private LocalTimePicker dp_horaSalida;
    @FXML
    private NumericTextField nf_salario;
    @FXML
    private TextField tf_busquedaTexto;
    @FXML
    private ComboBox<String> cb_busquedaDespedir;
    @FXML
    private Button bt_buscar;
    @FXML
    private TableView<Trabajador> tv_empleado;
    @FXML
    private TableColumn<Trabajador, Integer> tc_id;
    @FXML
    private TableColumn<Trabajador, String> tc_dni;
    @FXML
    private TableColumn<Trabajador, String> tc_nombre;
    @FXML
    private TableColumn<Trabajador, String> tc_apellido1;
    @FXML
    private TableColumn<Trabajador, String> tc_apellido2;
    @FXML
    private TableColumn<Trabajador, String> tc_puesto;
    @FXML
    private TableColumn<Trabajador, Double> tc_salario;
    @FXML
    private TableColumn<Trabajador, LocalDate> tc_fechaAlta;
    @FXML
    private TableColumn<Trabajador, String> tc_nick;
    @FXML
    private TableColumn<Trabajador, TableColumn> tc_horario;
    @FXML
    private TableColumn<Trabajador, LocalTime> tc_horaEntrada;
    @FXML
    private TableColumn<Trabajador, LocalTime> tc_horaSalida;
    @FXML
    private TableColumn<Trabajador, Integer> tc_idTienda;
    @FXML
    private TextArea ta_datosTrabajador;
    @FXML
    private NumericTextField nf_busquedaNumerica;
    @FXML
    private Pane pn_tienda;
    @FXML
    private Button bt_atrasVerTrabajadores;
    @FXML
    private Button bt_irPaneDespedir;
    @FXML
    private Button bt_irVerTrabajadores;
    @FXML
    private ComboBox<String> cb_puesto;
    @FXML
    private TableColumn<Producto, Integer> tc_stock;
    @FXML
    private Button bt_irPanelAnadirProducto;
    @FXML
    private Pane pn_añadirProductos;
    @FXML
    private Button bt_anadirProducto;
    @FXML
    private Button bt_atrasAñadirProductos;
    @FXML
    private Label lb_referenciaProducto;
    @FXML
    private TextField tf_nombreProducto;
    @FXML
    private TextArea ta_descripcionProducto;
    @FXML
    private TextField tf_precioCompraProducto;
    @FXML
    private TextField tf_precioVentaProducto;
    @FXML
    private TextField tf_ivaProducto;
    @FXML
    private NumericTextField nf_cantidad;
    @FXML
    private ComboBox<String> cb_categoriasExistentes;
    @FXML
    private Button bt_descripcion;
    @FXML
    private Pane pn_incidencias;
    @FXML
    private TableView<Incidencia> tv_incidencias; // RELLENAR LOS DATOS
    @FXML
    private Button bt_atrasIncidencias;
    @FXML
    private TableColumn<Incidencia, Integer> tc_idTrabajadorIncidencia;
    @FXML
    private TableColumn<Incidencia, String> tc_tipoIncidencia;
    @FXML
    private TableColumn<Incidencia, LocalDate> tc_fechaIncidencia;
    @FXML
    private Label lb_numIncidencias;
    @FXML
    private TextArea ta_descripcionIncidencia;
    @FXML
    private RadioButton rb_leidas;
    @FXML
    private ToggleGroup incidencias;
    @FXML
    private RadioButton rb_noLeidas;
    @FXML
    private Button bt_limpiarProductos;
    @FXML
    private Button bt_nuevaCategoria;
    @FXML
    private Label lb_textoId;
    @FXML
    private Label lb_datosPersonales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alert errorCarga;
        ObservableList<String> tiposBusqueda = FXCollections.observableArrayList("ID", "DNI");
        ObservableList<String> puestos = FXCollections.observableArrayList("Gerente", "Dependiente");
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        producto = new ProductoDAO(ConexionBD.conexion);
        incidencia = new IncidenciaDAO(ConexionBD.conexion);
        tiendasProductos = new TiendasProductosDAO(ConexionBD.conexion);
        validadorDni = new ValidadorDNI();
        gerenteActual = ConexionBD.actualUser;
        estiloAlerta = new Alerta();
        pn_menuTrabajadores.setVisible(false);
        pn_contratar.setVisible(false);
        pn_productos.setVisible(false);
        pn_despedir.setVisible(false);
        pn_tienda.setVisible(false);
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        cb_busquedaDespedir.setItems(tiposBusqueda);
        ta_datosTrabajador.setVisible(false);
        ta_datosTrabajador.setDisable(true);
        ta_datosTrabajador.setVisible(false);
        cb_puesto.setItems(puestos);
        cb_puesto.setPromptText("Puesto");
        cb_puesto.setValue("Puesto");
        pn_añadirProductos.setVisible(false);
        pn_incidencias.setVisible(false);
        lb_numIncidencias.setVisible(false);
        rb_leidas.setUserData("Leidas");
        rb_noLeidas.setUserData("No leidas");
        rb_noLeidas.setSelected(true);
        ta_descripcionProducto.setWrapText(true);
        ta_datosTrabajador.setWrapText(true);
        bt_perfil.setText("Gerente: " + gerenteActual.getNombre());
        tb_referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
        tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tb_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tb_precioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
        tb_precioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        tb_iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        tc_stock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarProductos();

        /*
        try {
            ESTO SERÍA IDEAL SI CONTEMPLASEMOS QUE UN GERENTE REGENTA VARIAS TIENDAS
            listaTiendas = FXCollections.observableArrayList(tienda.cargarDatos());
            cb_tiendas.setItems(listaTiendas);

        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Carga Tiendas");
            errorCarga.setHeaderText("Error al cargar la lista de tiendas \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }*/
        try {
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(gerenteActual.getIdTienda()));
            tv_productos.setItems(listaProductos);
            tb_referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_precioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
            tb_precioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            tb_iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
            tc_stock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        tc_apellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        tc_puesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        tc_salario.setCellValueFactory(new PropertyValueFactory<>("salarioBrutoAnual"));
        tc_fechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tc_nick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        tc_horaEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tc_horaSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tc_idTienda.setCellValueFactory(new PropertyValueFactory<>("idTienda"));

        cargarTooltips();

        tc_idTrabajadorIncidencia.setCellValueFactory(new PropertyValueFactory<>("idTrabajador"));
        tc_tipoIncidencia.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tc_fechaIncidencia.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        try {
            cargarIncidencias();
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Incidenicas");
            errorCarga.setHeaderText("Error al cargar la lista de incidencias \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void cargarProductos() {
        Alert errorCarga;
        try {
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(gerenteActual.getIdTienda()));
            tv_productos.setItems(listaProductos);

        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void cargarTooltips() {
        Tooltip tt_personal, tt_contratar, tt_despedir, tt_atras, tt_incidencias,
                tt_productos, tt_tienda, tt_anadirProducto, tt_limpiarProducto, tt_buscarTrabajador,
                tt_listaTrabajadores;

        tt_personal = new Tooltip("Personal");
        tt_personal.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_personal, tt_personal);

        tt_incidencias = new Tooltip("Incidencias");
        tt_incidencias.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_incidencias, tt_incidencias);

        tt_productos = new Tooltip("Productos");
        tt_productos.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_productos, tt_productos);

        tt_tienda = new Tooltip("Tienda");
        tt_tienda.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_tienda, tt_tienda);

        tt_contratar = new Tooltip("Contratar trabajador");
        tt_contratar.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_contratarPersonal, tt_contratar);
        Tooltip.install(bt_contratar, tt_contratar);

        tt_despedir = new Tooltip("Despedir trabajador");
        tt_despedir.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_despedirPersonal, tt_despedir);
        Tooltip.install(bt_despedir, tt_despedir);
        Tooltip.install(bt_irPaneDespedir, tt_despedir);

        tt_atras = new Tooltip("Volver");
        tt_atras.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_atras, tt_atras);
        Tooltip.install(bt_atrasDespedir, tt_atras);
        Tooltip.install(bt_atrasContratar, tt_atras);
        Tooltip.install(bt_atrasProductos, tt_atras);
        Tooltip.install(bt_atrasAñadirProductos, tt_atras);
        Tooltip.install(bt_atrasVerTrabajadores, tt_atras);

        tt_anadirProducto = new Tooltip("Añadir productos");
        tt_anadirProducto.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_irPanelAnadirProducto, tt_anadirProducto);
        Tooltip.install(bt_anadirProducto, tt_anadirProducto);

        tt_limpiarProducto = new Tooltip("Limpiar");
        tt_limpiarProducto.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_limpiarProductos, tt_limpiarProducto);

        tt_buscarTrabajador = new Tooltip("Buscar trabajador");
        tt_buscarTrabajador.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_buscar, tt_buscarTrabajador);

        tt_listaTrabajadores = new Tooltip("Lista de trabajadores");
        tt_listaTrabajadores.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_irVerTrabajadores, tt_listaTrabajadores);

    }

    @FXML
    private void trabajadoresAction(ActionEvent event) {

        if (bt_personal.isFocused()) {
            pn_inicio.setVisible(false);
            pn_menuTrabajadores.setVisible(true);
        }

        if (bt_atras.isFocused()) {
            pn_inicio.setVisible(true);
            pn_menuTrabajadores.setVisible(false);
        }

    }

    @FXML
    private void CloseAction(ActionEvent event) {
        Alert salir = new Alert(AlertType.CONFIRMATION);
        salir.setTitle("Cerrar sesion");
        salir.setHeaderText("¿Desea cerrar la sesión?");
        estiloAlerta.darleEstiloAlPanel(salir);
        Optional<ButtonType> resultado = salir.showAndWait();

        if (resultado.get() == ButtonType.OK) {

            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
                ac_gerente.getChildren().setAll(pane);
                ConexionBD.desconectar();
            } catch (IOException ex) {
                Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            salir.close();
        }
    }

    @FXML
    private void contratarAction(ActionEvent event) {
        Object evento = event.getSource();
        Alert errorCarga;
        if (evento == bt_contratarPersonal) { // ACCEDE AL MENU DE INTRODUCCION DE DATOS
            pn_menuTrabajadores.setVisible(false);
            pn_contratar.setVisible(true);
            dp_fecha.setValue(LocalDate.now());
            try {
                lb_id.setText(" " + String.valueOf(trabajador.mostrarSiguienteID()));
            } catch (SQLException ex) {
                errorCarga = new Alert(AlertType.ERROR);
                errorCarga.setTitle("Error Id");
                errorCarga.setHeaderText("Error al cargar el siguiente id.");
                errorCarga.setContentText("Error :" + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorCarga);
                errorCarga.showAndWait();
            }
        }

        if (evento == bt_atrasContratar) {
            pn_menuTrabajadores.setVisible(true);
            pn_contratar.setVisible(false);
            limpiarCamposContratar();
        }

        if (evento == bt_contratar) { // INSERTA EN LA BD
            contratar();
        }
    }

    public void contratar() {
        Alert camposRestantes, errorIngresar, contratoCorrecto, dniIncorrecto;
        StrongPasswordEncryptor passwordEncryptor;
        String passEncriptada = null;
        List<String> camposVacios = new ArrayList<>();
        String dni = tf_dni.getText(), nombre = tf_nombre.getText(), apellido1 = tf_apellido1.getText(),
                apellido2 = tf_apellido2.getText(), puesto = cb_puesto.getValue(), nick = tf_nick.getText(),
                salariotext = nf_salario.getText();
        Integer idTienda = gerenteActual.getIdTienda();
        Double salario = null;
        LocalDate fecha;
        LocalTime horaEntrada = dp_horaEntrada.getLocalTime(), horaSalida = dp_horaSalida.getLocalTime();

        try {

            if (nombre.isEmpty()) {
                camposVacios.add("Nombre");
            }

            if (dni.isEmpty()) {
                camposVacios.add("DNI");
            }

            if (puesto.equalsIgnoreCase("Puesto")) {
                camposVacios.add("Puesto");
            }

            if (dp_fecha.getValue() == null) {
                fecha = LocalDate.now();
            } else {
                fecha = dp_fecha.getValue();
            }

            if (salariotext.isEmpty()) {
                camposVacios.add("Salario");
            } else {
                salario = Double.parseDouble(salariotext);
            }

            if (nick.isEmpty()) {
                camposVacios.add("Nick");
            } else {
                passwordEncryptor = new StrongPasswordEncryptor();
                passEncriptada = passwordEncryptor.encryptPassword(dni);

            }

            /* IDEAL GERENTE VARIAS TIENDAS
            if (cb_tiendas.getSelectionModel().isEmpty()) {
                camposVacios.add("Tienda");
            } else {
                idTienda = cb_tiendas.getSelectionModel().getSelectedItem().getId();
            }*/
            if (!camposVacios.isEmpty()) {
                camposRestantes = new Alert(AlertType.WARNING);
                camposRestantes.setTitle("Error Ingresar");
                camposRestantes.setHeaderText("Rellene los campos obligatorios (naranja).");
                camposRestantes.setContentText("Campos Vacios: " + camposVacios.toString());
                estiloAlerta.darleEstiloAlPanel(camposRestantes);
                camposRestantes.showAndWait();

            } else if (horaEntrada.equals(LocalTime.MIDNIGHT) || horaSalida.equals(LocalTime.MIDNIGHT)) {
                camposRestantes = new Alert(AlertType.INFORMATION);
                camposRestantes.setTitle("Horarios");
                camposRestantes.setHeaderText("Revise los horarios.");
                camposRestantes.setContentText("No pueden estar a 00:00.");
                estiloAlerta.darleEstiloAlPanel(camposRestantes);
                camposRestantes.showAndWait();

            } else if (!validadorDni.validar(dni)) {
                dniIncorrecto = new Alert(AlertType.ERROR);
                dniIncorrecto.setTitle("DNI ERRROR");
                dniIncorrecto.setHeaderText("DNI incorrecto");
                dniIncorrecto.setContentText("Por favor, introduzca un dni con "
                        + "formato correcto, ej;(12345678P).\n"
                        + "Además debe de ser un DNI válido.");
                estiloAlerta.darleEstiloAlPanel(dniIncorrecto);
                dniIncorrecto.showAndWait();

            } else {
                Trabajador trabajador = new Trabajador(this.trabajador.mostrarSiguienteID(),
                        dni,
                        nombre,
                        apellido1,
                        apellido2,
                        puesto,
                        salario,
                        fecha,
                        nick,
                        passEncriptada,
                        horaEntrada,
                        horaSalida,
                        idTienda);
                this.trabajador.insertar(trabajador);
                contratoCorrecto = new Alert(AlertType.INFORMATION);
                contratoCorrecto.setTitle("Contratado");
                contratoCorrecto.setHeaderText("Ingresado correctamente.");
                contratoCorrecto.setContentText(trabajador.getNombre() + " " + trabajador.getApellido1() + " "
                        + trabajador.getApellido2() + " ahora pertence a nuestra plantilla"
                        + " desenpeñando el cargo de " + trabajador.getPuesto() + ".\n Le deseamos"
                        + " suerte en su nueva etapa.");
                estiloAlerta.darleEstiloAlPanel(contratoCorrecto);
                contratoCorrecto.showAndWait();
                limpiarCamposContratar();
                lb_id.setText(" " + this.trabajador.mostrarSiguienteID());
            }

        } catch (NumberFormatException e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error Tipo dato");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();

        } catch (NullPointerException e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Vacio");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();

        } catch (SQLException ex) {

            if (ex.getErrorCode() == 1062) {
                errorIngresar = new Alert(AlertType.ERROR);
                errorIngresar.setTitle("Error");
                errorIngresar.setHeaderText("Error en DNI o NICK");
                errorIngresar.setContentText("El DNI o el nick ya están en uso.\n"
                        + "Por favor, elija otro.");
                estiloAlerta.darleEstiloAlPanel(errorIngresar);
                errorIngresar.showAndWait();
            } else {
                errorIngresar = new Alert(AlertType.ERROR);
                errorIngresar.setTitle("Error Introducir");
                errorIngresar.setContentText(ex.getMessage() + " " + ex.getErrorCode());
                estiloAlerta.darleEstiloAlPanel(errorIngresar);
                errorIngresar.showAndWait();
            }

        } catch (Exception e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();
        }
    }

    @FXML
    private void despedirAction(ActionEvent event) {
        Object evento = event.getSource();

        if (evento == bt_despedirPersonal) { // ACCEDE AL MENU DE INTRODUCCION DE DATOS
            pn_menuTrabajadores.setVisible(false);
            pn_despedir.setVisible(true);
            cb_busquedaDespedir.setValue("ID");
            elegirDespedir();
        }

        if (evento == bt_atrasDespedir) {
            pn_menuTrabajadores.setVisible(true);
            pn_despedir.setVisible(false);
            limpiarCamposDespedir();
        }

        if (evento == bt_despedir) { // ELIMINA EN LA BD
            despedir();
        }

    }

    @FXML
    private void elegirDespedirAction(ActionEvent event) {
        elegirDespedir();
    }

    public void elegirDespedir() {
        /* PARECE QUE ESTE METODOD ESTA REPETIDO CON EL "BuscarDespedirAction"
        , PERO ESTA HECHO ASÍ PORQUE ESTE QUIERO QUE SOLO SE USE AL ENTRAR EN EL 
        MENU O AL CAMBIAR DE ELECCIÓN Y ASÍ HABLITAR O DESHABILITAR EL 
        TEXTFIELD CORRESPONDIENTE*/
        eleccion = cb_busquedaDespedir.getValue();

        if (eleccion.equalsIgnoreCase("ID")) {
            nf_busquedaNumerica.setVisible(true);
            nf_busquedaNumerica.setDisable(false);
            tf_busquedaTexto.setVisible(false);
            tf_busquedaTexto.setDisable(true);
            nf_busquedaNumerica.setPromptText(eleccion);
            limpiarCamposDespedir();
        } else {
            nf_busquedaNumerica.setVisible(false);
            nf_busquedaNumerica.setDisable(true);
            tf_busquedaTexto.setVisible(true);
            tf_busquedaTexto.setDisable(false);
            tf_busquedaTexto.setPromptText(eleccion);
            limpiarCamposDespedir();
        }

    }

    @FXML
    private void buscarDespedirAction(ActionEvent event) {
        Object evento = event.getSource();

        if (evento == bt_buscar) {
            buscarDespedir();
        }

        if (evento == bt_despedir) {
            despedir();
        }

        if (evento == bt_irVerTrabajadores) {
            pn_despedir.setVisible(false);
            pn_tienda.setVisible(true);
            verTrajadores();
        }
    }

    private void buscarDespedir() {
        Trabajador trabajador;
        String busqueda = "";
        campoDespedirVacio = false;
        Alert campoVacio, errorBusqueda;

        try {
            if (eleccion.equalsIgnoreCase("ID")) {
                busqueda = nf_busquedaNumerica.getText();
                if (!busqueda.isEmpty()) {
                    trabajador = this.trabajador.cargarTrabajador(busqueda, 1, gerenteActual.getIdTienda());
                    ta_datosTrabajador.setText(trabajador.verDatos());
                    ta_datosTrabajador.setVisible(true);
                    ta_datosTrabajador.setDisable(false);
                } else {
                    campoDespedirVacio = true;
                }
            } else {
                busqueda = tf_busquedaTexto.getText();
                if (!busqueda.isEmpty()) {
                    trabajador = this.trabajador.cargarTrabajador(busqueda, 2, gerenteActual.getIdTienda());
                    ta_datosTrabajador.setText(trabajador.verDatos());
                    ta_datosTrabajador.setVisible(true);
                    ta_datosTrabajador.setDisable(false);
                } else {
                    campoDespedirVacio = true;
                }
            }

        } catch (SQLException e) {
            errorBusqueda = new Alert(Alert.AlertType.ERROR);
            errorBusqueda.setTitle("Busqueda");
            errorBusqueda.setHeaderText("Error en la busqueda");
            errorBusqueda.setContentText("El trabajador con " + eleccion + ": "
                    + busqueda + " no existe.");
            estiloAlerta.darleEstiloAlPanel(errorBusqueda);
            errorBusqueda.showAndWait();
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);
        }

        if (campoDespedirVacio) {
            campoVacio = new Alert(AlertType.ERROR);
            campoVacio.setTitle("Busqueda");
            campoVacio.setHeaderText("Error en la busqueda");
            campoVacio.setContentText("Por favor rellene el campo de busqueda.");
            estiloAlerta.darleEstiloAlPanel(campoVacio);
            campoVacio.showAndWait();
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);
        }
    }

    private void despedir() {
        Alert sinBuscar, errorDespedir, confirmacionBorrado, despedirCorrecto;
        String buscando;
        int modo;

        if (eleccion.equalsIgnoreCase("ID")) {
            buscando = nf_busquedaNumerica.getText();
            modo = 1;
        } else {
            buscando = tf_busquedaTexto.getText();
            modo = 2;
        }

        if (ta_datosTrabajador.isVisible()) {
            try {
                confirmacionBorrado = new Alert(AlertType.CONFIRMATION);
                confirmacionBorrado.setTitle("Despedir");
                confirmacionBorrado.setHeaderText("¿Esta seguro?");
                confirmacionBorrado.setContentText("¿Quiere despedir al trabajor con "
                        + eleccion + ": " + buscando + " y por consiguiente borrarlo"
                        + " de la base de datos?");
                estiloAlerta.darleEstiloAlPanel(confirmacionBorrado);
                Optional<ButtonType> resultado = confirmacionBorrado.showAndWait();

                if (resultado.get() == ButtonType.OK) {
                    if (!trabajador.cargarTrabajador(buscando, modo, gerenteActual.getIdTienda()).getDni().equalsIgnoreCase(gerenteActual.getDni())) {
                        trabajador.despedir(buscando, modo);
                        despedirCorrecto = new Alert(AlertType.INFORMATION);
                        despedirCorrecto.setTitle("Despedir");
                        despedirCorrecto.setHeaderText("Despido efectuado");
                        despedirCorrecto.setContentText(null);
                        estiloAlerta.darleEstiloAlPanel(despedirCorrecto);
                        despedirCorrecto.showAndWait();
                        limpiarCamposDespedir();
                    } else {
                        errorDespedir = new Alert(AlertType.ERROR);
                        errorDespedir.setTitle("Despedir");
                        errorDespedir.setHeaderText("Error al despedir");
                        errorDespedir.setContentText("No puedes despedirte a ti mismo.\n"
                                + "Sal de la aplicación y solicita que otro gerente de la misma tienda "
                                + "te despida.");
                        estiloAlerta.darleEstiloAlPanel(errorDespedir);
                        errorDespedir.showAndWait();
                    }

                } else if (resultado.get() == ButtonType.CANCEL) {
                    confirmacionBorrado.close();
                }

            } catch (SQLException ex) {

                errorDespedir = new Alert(AlertType.ERROR);
                errorDespedir.setTitle("Despedir");
                errorDespedir.setHeaderText("Error al despedir");
                errorDespedir.setContentText("No se ha podido borrar el trabajador de la "
                        + "base de datos.\n"
                        + "Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorDespedir);
                errorDespedir.showAndWait();
            }
        } else {
            sinBuscar = new Alert(AlertType.ERROR);
            sinBuscar.setTitle("Busqueda");
            sinBuscar.setHeaderText("Error en la busqueda");
            sinBuscar.setContentText("Primero debe buscar el trabajador.");
            estiloAlerta.darleEstiloAlPanel(sinBuscar);
            sinBuscar.showAndWait();
        }

    }

    @FXML
    private void inicioAction(ActionEvent event) { // VUELVE AL INICIO
        pn_inicio.setVisible(true);
        pn_contratar.setVisible(false);
        pn_menuTrabajadores.setVisible(false);
        pn_productos.setVisible(false);
        pn_despedir.setVisible(false);
        pn_incidencias.setVisible(false);
        incidencias.selectToggle(rb_noLeidas);
        limpiarCamposContratar();
        limpiarCamposDespedir();
        salirVerTrabajadores();
        limpiarProductos();
        salirProductos();
        limpiarIncidencias();

    }

    public void limpiarCamposContratar() {
        tf_dni.clear();
        tf_nombre.clear();
        tf_apellido1.clear();
        tf_apellido2.clear();
        cb_puesto.setValue("Puesto");
        nf_salario.clear();
        tf_nick.clear();
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        dp_fecha.setValue(LocalDate.now());

    }

    public void limpiarCamposDespedir() {
        tf_busquedaTexto.clear();
        nf_busquedaNumerica.clear();
        ta_datosTrabajador.setVisible(false);
        ta_datosTrabajador.setDisable(true);
    }

    @FXML
    private void teclaBuscarDespedirAction(KeyEvent event) {
        String campo = tf_busquedaTexto.getText(), campoNumerico = nf_busquedaNumerica.getText();
        if (event.getCode() == KeyCode.ENTER) {
            buscarDespedir();
        }

        if (campo.isEmpty() && campoNumerico.isEmpty()) {
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);

        }

    }

    @FXML
    private void verTrabajadoresAction(ActionEvent event) {
        Object evento = event.getSource();
        verTrajadores();

        if (evento == bt_atrasVerTrabajadores) {
            salirVerTrabajadores();

        }

        if (evento == bt_tienda) {
            pn_tienda.setVisible(true);
            pn_inicio.setVisible(false);
        }

        if (evento == bt_irPaneDespedir) {
            pn_tienda.setVisible(false);
            pn_despedir.setVisible(true);
            cb_busquedaDespedir.setValue("ID");
        }

    }

    public void verTrajadores() {
        List<Trabajador> listaTrabajadores;
        Alert errorCarga;

        try {
            listaTrabajadores = trabajador.cargarTrabajadoresTienda(gerenteActual.getIdTienda());
            listaObservableTrabajadores = FXCollections.observableArrayList(listaTrabajadores);
            tv_empleado.setItems(listaObservableTrabajadores);
            tv_empleado.setVisible(true);
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Carga");
            errorCarga.setHeaderText("Error en la carga de los trabajadores");
            errorCarga.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void salirVerTrabajadores() {
        tv_empleado.setVisible(false);
        pn_inicio.setVisible(true);
        pn_tienda.setVisible(false);
    }

    @FXML
    private void productosAction(ActionEvent event) {
        Object evento = event.getSource();
        Alert cargaReferencia, cargaCategorias;
        String botonDescripcion = bt_descripcion.getText();

        if (evento == bt_productos) { // ACCEDE A VER LOS PRODUCTOS
            pn_productos.setVisible(true);
            pn_inicio.setVisible(false);

        } else if (evento == bt_irPanelAnadirProducto) { // ACCEDE AL MENU DE AÑADIR PRODUCTOS
            pn_productos.setVisible(false);
            pn_añadirProductos.setVisible(true);
            try {
                lb_referenciaProducto.setText(String.valueOf(producto.mostrarSiguienteReferencia()));
            } catch (SQLException ex) {
                cargaReferencia = new Alert(AlertType.ERROR);
                cargaReferencia.setTitle("Error Carga");
                cargaReferencia.setHeaderText("Error al cargar la siguiente referencia.");
                cargaReferencia.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(cargaReferencia);
                cargaReferencia.showAndWait();
            }

            try {
                categorias = FXCollections.observableArrayList(producto.categoriasExistentes());
                cb_categoriasExistentes.setItems(categorias);
                cb_categoriasExistentes.setValue("Categoria");
            } catch (SQLException ex) {
                cargaCategorias = new Alert(AlertType.ERROR);
                cargaCategorias.setTitle("Error Carga");
                cargaCategorias.setHeaderText("Error al cargar las categorias existentes.");
                cargaCategorias.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(cargaCategorias);
                cargaCategorias.showAndWait();
            }

        } else if (evento == bt_descripcion && botonDescripcion.equalsIgnoreCase("+")) {
            bt_descripcion.setLayoutX(522);
            bt_descripcion.setText("-");
            ta_descripcionProducto.setPrefWidth(486);
            ta_descripcionProducto.setPrefHeight(65);
//            tf_precioCompraProducto.setLayoutY(252);
//            tf_precioVentaProducto.setLayoutY(292);
//            tf_ivaProducto.setLayoutY(333);
//            nf_cantidad.setLayoutY(371);

        } else if (evento == bt_descripcion && botonDescripcion.equalsIgnoreCase("-")) {
            bt_descripcion.setLayoutX(185);
            bt_descripcion.setText("+");
            ta_descripcionProducto.setPrefWidth(150);
            ta_descripcionProducto.setPrefHeight(32);
//            tf_precioCompraProducto.setLayoutY(232);
//            tf_precioVentaProducto.setLayoutY(272);
//            tf_ivaProducto.setLayoutY(313);
//            nf_cantidad.setLayoutY(351);

        } else if (evento == bt_atrasProductos) {
            pn_productos.setVisible(false);
            pn_inicio.setVisible(true);
            limpiarProductos();

        } else if (evento == bt_atrasAñadirProductos) {
            pn_añadirProductos.setVisible(false);
            pn_productos.setVisible(true);
            limpiarProductos();

        }

    }

    @FXML
    private void AñadirProductosAction(ActionEvent event) {

        Object evento = event.getSource();
        List<String> camposVacios = new ArrayList<>();
        String nombre = tf_nombreProducto.getText(),
                precioCompraProducto = tf_precioCompraProducto.getText(),
                precioVentaProducto = tf_precioVentaProducto.getText(),
                ivaProducto = tf_ivaProducto.getText(),
                cantidadProducto = nf_cantidad.getText(),
                categoria = cb_categoriasExistentes.getValue(),
                descripcion = ta_descripcionProducto.getText();
        Double iva, precioCompra = null, precioVenta = null;
        Integer cantidad = null;
        Alert faltaCampos, errorCarga, errorInsertar, insertado, errorCargarTienda,
                existeProducto;
        Producto producto = null;
        Integer siguienteId = null;
        boolean productoInsertado = false, conCantidad = false, existe = false;

        try {
            siguienteId = this.producto.mostrarSiguienteReferencia();
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Id");
            errorCarga.setHeaderText("Error al cargar el siguiente id.");
            errorCarga.setContentText("Error :" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

        if (nombre.isEmpty()) {
            camposVacios.add("Nombre");
        }

        if (categoria.equalsIgnoreCase("Categoria")) {
            camposVacios.add("Categoria");
        }

        if (precioCompraProducto.isEmpty()) {
            camposVacios.add("Precio compra");
        } else {
            precioCompra = Double.valueOf(precioCompraProducto);
        }

        if (precioVentaProducto.isEmpty()) {
            camposVacios.add("precio venta");
        } else {
            precioVenta = Double.valueOf(precioVentaProducto);
        }

        if (ivaProducto.isEmpty()) {
            iva = 0.0;
        } else {
            iva = Double.valueOf(ivaProducto);
        }
        try {
            if (!camposVacios.isEmpty()) {

                faltaCampos = new Alert(AlertType.ERROR);
                faltaCampos.setTitle("Error Añadir");
                faltaCampos.setHeaderText("Complete los campos obligatorios(en naranja).");
                faltaCampos.setContentText("Campos vacios " + camposVacios.toString());
                estiloAlerta.darleEstiloAlPanel(faltaCampos);
                faltaCampos.showAndWait();

            } else if (cantidadProducto.isEmpty()) {

                producto = new Producto(siguienteId,
                        nombre,
                        categoria,
                        descripcion,
                        precioCompra,
                        precioVenta,
                        iva);

                if (!this.producto.existeProducto(producto)) {
                    this.producto.anadirProducto(producto);
                    productoInsertado = true;
                    limpiarProductos();
                } else {
                    existe = true;
                }

            } else if (!cantidadProducto.isEmpty()) {
                cantidad = Integer.valueOf(cantidadProducto);

                producto = new Producto(siguienteId,
                        nombre,
                        categoria,
                        descripcion,
                        precioCompra,
                        precioVenta,
                        iva,
                        cantidad);

                if (!this.producto.existeProducto(producto)) {
                    this.tiendasProductos.anadirProducto(gerenteActual.getIdTienda(),
                            producto,
                            cantidad);
                    productoInsertado = true;
                    conCantidad = true;
                    limpiarProductos();
                } else {
                    existe = true;
                }
            }
        } catch (SQLException ex) {
            errorInsertar = new Alert(AlertType.ERROR);
            errorInsertar.setTitle("Error");
            errorInsertar.setHeaderText("No se ha podido insertar el producto.");
            errorInsertar.setContentText("Error :" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorInsertar);
            errorInsertar.showAndWait();
        }

        if (productoInsertado) {
            insertado = new Alert(AlertType.INFORMATION);
            insertado.setTitle("Insertado");
            insertado.setHeaderText("Producto " + producto.getNombre()
                    + " insertado correctamente.");
            if (conCantidad) {
                try {
                    insertado.setContentText("La tienda " + tienda.nombreTienda(gerenteActual.getIdTienda())
                            + " dispone de una cantidad de " + cantidadProducto + " unidades del nuevo producto.");
                } catch (SQLException ex) {
                    errorCargarTienda = new Alert(AlertType.ERROR);
                    errorCargarTienda.setTitle("Error");
                    errorCargarTienda.setHeaderText("No se ha podido insertar el producto.");
                    errorCargarTienda.setContentText("Error :" + ex.getMessage());
                    estiloAlerta.darleEstiloAlPanel(errorCargarTienda);
                    errorCargarTienda.showAndWait();
                }
            }
            estiloAlerta.darleEstiloAlPanel(insertado);
            insertado.showAndWait();
        } else if (existe) {
            existeProducto = new Alert(AlertType.ERROR);
            existeProducto.setTitle("Error Añadir");
            existeProducto.setHeaderText("El producto" + producto.getNombre()
                    + " ya existe en nuestro almacen.");
            estiloAlerta.darleEstiloAlPanel(existeProducto);
            existeProducto.showAndWait();
        }

    }

    public void limpiarProductos() {
        cb_categoriasExistentes.setValue("Categoria");
        tf_nombreProducto.clear();
        ta_descripcionProducto.clear();
        tf_precioCompraProducto.clear();
        tf_precioVentaProducto.clear();
        tf_ivaProducto.clear();
        nf_cantidad.clear();
        cargarProductos();
    }

    public void limpiarIncidencias() {
        Alert errorCarga;
        lb_numIncidencias.setVisible(false);
        ta_descripcionIncidencia.clear();
        ta_descripcionIncidencia.setPromptText("Seleccione una incidencia para ver su descripcion.");
        try {
            cargarIncidencias();
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Incidenicas");
            errorCarga.setHeaderText("Error al cargar la lista de incidencias.");
            errorCarga.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void salirProductos() {
        pn_productos.setVisible(false);
        pn_inicio.setVisible(true);
        pn_añadirProductos.setVisible(false);
        tf_nombreProducto.clear();
    }

    @FXML
    private void controlNumericoAction(KeyEvent event) {
        Object evento = event.getSource();
        String texto = null;
        Character caracter;
        int tamaño, campo = 0, contador = 0;
        boolean esDigito;

        if (evento == tf_precioCompraProducto) {
            texto = tf_precioCompraProducto.getText();
            campo = 1;
        } else if (evento == tf_precioVentaProducto) {
            texto = tf_precioVentaProducto.getText();
            campo = 2;
        } else if (evento == tf_ivaProducto) {
            texto = tf_ivaProducto.getText();
            campo = 3;
        }

        tamaño = texto.length();

        if (!texto.isEmpty()) {
            caracter = texto.charAt(tamaño - 1);
            esDigito = Character.isDigit(caracter);

            if (event.getCode().equals(KeyCode.PERIOD) && !texto.startsWith(".")) {
                for (int i = 0; i < texto.length(); i++) {
                    if (texto.charAt(i) == '.') {
                        contador++;
                    }
                }

                if (contador > 1) {
                    switch (campo) {
                        case 1:
                            tf_precioCompraProducto.setText(texto.substring(0, tamaño - 1));
                            tf_precioCompraProducto.positionCaret(tamaño);
                            break;
                        case 2:
                            tf_precioVentaProducto.setText(texto.substring(0, tamaño - 1));
                            tf_precioVentaProducto.positionCaret(tamaño);
                            break;
                        case 3:
                            tf_ivaProducto.setText(texto.substring(0, tamaño - 1));
                            tf_ivaProducto.positionCaret(tamaño);

                            break;
                        default:
                            throw new AssertionError();
                    }
                }
            } else if (esDigito != true) {
                switch (campo) {
                    case 1:
                        tf_precioCompraProducto.setText(texto.substring(0, tamaño - 1));
                        tf_precioCompraProducto.positionCaret(tamaño);
                        break;
                    case 2:
                        tf_precioVentaProducto.setText(texto.substring(0, tamaño - 1));
                        tf_precioVentaProducto.positionCaret(tamaño);

                        break;
                    case 3:
                        tf_ivaProducto.setText(texto.substring(0, tamaño - 1));
                        tf_ivaProducto.positionCaret(tamaño);

                        break;
                    default:
                        throw new AssertionError();
                }

            }
        }
    }

    @FXML
    private void IncidenciasAction(ActionEvent event) {

        if (bt_incidencias.isFocused()) {
            pn_inicio.setVisible(false);
            pn_incidencias.setVisible(true);
        }

        if (bt_atrasIncidencias.isFocused()) {
            pn_incidencias.setVisible(false);
            pn_inicio.setVisible(true);
            incidencias.selectToggle(rb_noLeidas);
            limpiarIncidencias();
        }

    }

    @FXML
    private void cargarIncidencias() throws SQLException {
        int modo;
        ta_descripcionIncidencia.clear();

        if (incidencias.getSelectedToggle().getUserData().equals("No leidas")) {
            modo = 1;
        } else {
            modo = 0;
        }

        listaDeIncidencias = incidencia.cargarIncidencias(gerenteActual.getIdTienda(), modo);
        ObservableList<Incidencia> listaIncidencias = FXCollections.observableArrayList(listaDeIncidencias);
        tv_incidencias.setItems(listaIncidencias);

        if (!listaDeIncidencias.isEmpty()) {
            lb_numIncidencias.setText(String.valueOf(listaDeIncidencias.size()));
            lb_numIncidencias.setVisible(true);

        } else {
            lb_numIncidencias.setText(String.valueOf(listaDeIncidencias.size()));
        }

    }

    @FXML
    private void visualizandoIncidenciasAction(MouseEvent event) {
        Alert errorLeer, errorCarga;
        Incidencia seleccionada = tv_incidencias.getFocusModel().getFocusedItem();

        try {
            if (incidencias.getSelectedToggle().getUserData().equals("No leidas") && seleccionada != null) {
                incidencia.cambiarAleida(seleccionada);
            }
            if (seleccionada != null) {
                ta_descripcionIncidencia.setText(seleccionada.getDescripcion());
                ta_descripcionIncidencia.setWrapText(true);
            }

        } catch (SQLException ex) {
            errorLeer = new Alert(Alert.AlertType.ERROR);
            errorLeer.setTitle("Error");
            errorLeer.setHeaderText("Error al cambiar el estado de la incidencia.");
            errorLeer.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorLeer);
            errorLeer.showAndWait();
        }

//        try {
//            cargarIncidencias();
//
//        } catch (SQLException ex) {
//            errorCarga = new Alert(Alert.AlertType.ERROR);
//            errorCarga.setTitle("Error Carga Incidenicas");
//            errorCarga.setHeaderText("Error al cargar la lista de incidencias.");
//            errorCarga.setContentText("Error: " + ex.getMessage());
//            estiloAlerta.darleEstiloAlPanel(errorCarga);
//            errorCarga.showAndWait();
//        }
    }

    @FXML
    private void limpiarCamposProductosAction(ActionEvent event) {
        limpiarProductos();
    }

    @FXML
    private void nuevaCategoriaAction(ActionEvent event) {
        Alert nuevaCategoria, errorBusqueda, existe, creada, vacia;
        String categoriaBuscada;

        nuevaCategoria = new Alert(AlertType.CONFIRMATION);
        nuevaCategoria.setTitle("Nueva categoria");
        nuevaCategoria.setHeaderText(null);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tf_categoria = new TextField();
        tf_categoria.setPromptText("Categoria");
        grid.add(tf_categoria, 1, 0);
        nuevaCategoria.getDialogPane().setContent(grid);
        estiloAlerta.darleEstiloAlPanel(nuevaCategoria);
        Optional<ButtonType> resultado;
        resultado = nuevaCategoria.showAndWait();

        if (resultado.get() == ButtonType.OK) {
            categoriaBuscada = tf_categoria.getText();
            try {
                if (!categoriaBuscada.isEmpty()) {
                    if (!this.producto.existeCategoria(categoriaBuscada)) {
                        categorias.add(categoriaBuscada);
                        cb_categoriasExistentes.setItems(categorias);
                        cb_categoriasExistentes.setValue(categoriaBuscada);
                        creada = new Alert(Alert.AlertType.INFORMATION);
                        creada.setTitle("Creada");
                        creada.setHeaderText("La categoria " + categoriaBuscada
                                + " ha sido creada.");
                        estiloAlerta.darleEstiloAlPanel(creada);
                        creada.showAndWait();
                    } else {
                        existe = new Alert(Alert.AlertType.ERROR);
                        existe.setTitle("Existe");
                        existe.setHeaderText("La categoria " + categoriaBuscada
                                + " ya existe.");
                        estiloAlerta.darleEstiloAlPanel(existe);
                        existe.showAndWait();
                    }
                } else {
                    vacia = new Alert(Alert.AlertType.ERROR);
                    vacia.setTitle("Vacia");
                    vacia.setHeaderText("No has introducido ninguna categoria.");
                    estiloAlerta.darleEstiloAlPanel(vacia);
                    vacia.showAndWait();
                }
            } catch (SQLException ex) {
                errorBusqueda = new Alert(Alert.AlertType.ERROR);
                errorBusqueda.setTitle("Error");
                errorBusqueda.setHeaderText("No se ha podido buscar si existe.");
                errorBusqueda.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorBusqueda);
                errorBusqueda.showAndWait();
            }
        } else {
            tf_categoria.clear();
            cb_categoriasExistentes.setValue("Categoria");
        }

    }

    @FXML
    private void manualAction(ActionEvent event) {
        WebView wv_web = new WebView();
        WebEngine webEngine = wv_web.getEngine();
        webEngine.load("https://justcomerceweb.wordpress.com/");
        Dialog web = new Dialog();
        web.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        web.getDialogPane().setContent(wv_web);
        web.setTitle("Manual de usuario");
        web.show();
    }

}
