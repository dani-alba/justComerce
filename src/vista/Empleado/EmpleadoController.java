package vista.Empleado;

import Datos.ConexionBD;
import Datos.DetallesVentaDAO;
import Datos.IncidenciaDAO;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Datos.VentaDAO;
import Modelo.Alerta.Alerta;
import Modelo.DetalleVenta;
import Modelo.Incidencia;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import Modelo.Venta;
import impl.com.calendarfx.view.NumericTextField;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class EmpleadoController implements Initializable {

    private TiendaDAO tienda;
    private ProductoDAO producto;
    private TrabajadorDAO trabajador;
    private VentaDAO venta;
    private IncidenciaDAO incidencia;
    private DetallesVentaDAO detallesVenta;
    private Alerta estiloAlerta;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Trabajador> listaTrabajadores;
    private List<DetalleVenta> listaDetalles = new ArrayList<>();
    private LocalDateTime date;
    private String formato;
    private Integer idSiguienteVenta = null;
    private Trabajador empleadoActual;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");
    private ObservableList<String> tiposInciencias = FXCollections.observableArrayList("Robo", "Cliente", "Stock", "Otros");
    private List<String> listaNombre = new ArrayList<>();
    private List<Integer> listaCantidad = new ArrayList<>();
    private List<Double> listaPrecio = new ArrayList<>();
    private DecimalFormat redondear = new DecimalFormat("##.00");

    /*ATRIBUTOS FXML*/
    @FXML
    private Button bt_Inicio;
    @FXML
    private Button bt_Perfil;
    @FXML
    private Button bt_Ayuda;
    @FXML
    private Button bt_LogOut;
    @FXML
    private AnchorPane ac_empleado;
    @FXML
    private Pane pn_titulo;
    @FXML
    private Pane pn_productos;
    @FXML
    private TableView<Producto> tv_productos;
    @FXML
    private Button bt_atrasProductos;
    @FXML
    private Button bt_atrasVentas;
    @FXML
    private Pane pn_ventas;
    @FXML
    private Button bt_regVenta;
    @FXML
    private Pane pn_fondoIconos;
    @FXML
    private Button bt_ventas;
    @FXML
    private Button bt_productos;
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
    private TableColumn<Producto, Integer> tb_stock;
    @FXML
    private Button bt_incidencias;
    @FXML
    private Pane pn_incidencias;
    @FXML
    private Button bt_anadirIncidencia;
    @FXML
    private Button bt_atrasIncidencias;
    @FXML
    private ComboBox<String> cb_tipoIncidencia;
    @FXML
    private TextArea ta_descripcionIncidencia;
    @FXML
    private DatePicker dp_fechaInciendia;
    @FXML
    private TextField tf_especificarTipoIncidencia;
    @FXML
    private TableView<Producto> tv_productosVenta;
    @FXML
    private TableColumn<Producto, String> tb_nombreVenta;
    @FXML
    private TableColumn<Producto, String> tb_categoriaVenta;
    @FXML
    private TableColumn<Producto, Double> tb_precioVentaVenta;
    @FXML
    private TableColumn<Producto, Integer> tb_stockVenta;
    @FXML
    private Label lb_total;
    @FXML
    private Label lb_TotalTicket;
    @FXML
    private ListView<DetalleVenta> lv_detalleVentas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = new ProductoDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        venta = new VentaDAO(ConexionBD.conexion);
        incidencia = new IncidenciaDAO(ConexionBD.conexion);
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        detallesVenta = new DetallesVentaDAO(ConexionBD.conexion);
        empleadoActual = ConexionBD.actualUser;
        bt_Perfil.setText("Dependiente: " + empleadoActual.getNombre());
        lb_TotalTicket.setText("0");
        estiloAlerta = new Alerta();
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        pn_incidencias.setVisible(false);
        cb_tipoIncidencia.setItems(tiposInciencias);
        cb_tipoIncidencia.setPromptText("Tipos de Incidencia");
        cb_tipoIncidencia.setValue("Tipos de Incidencia");
        dp_fechaInciendia.setValue(LocalDate.now());
        tf_especificarTipoIncidencia.setVisible(false);
        actualizarTicketAtiempoReal(listaDetalles);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");

        cargarProductos();

        tb_nombreVenta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tb_categoriaVenta.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tb_precioVentaVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        tb_stockVenta.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarTooltips();
    }

    @FXML
    private void CloseAction(ActionEvent event) throws SQLException {
        Alert salir = new Alert(AlertType.CONFIRMATION);
        salir.setTitle("Cerrar sesion");
        salir.setHeaderText("¿Desea cerrar la sesión?");
        estiloAlerta.darleEstiloAlPanel(salir);
        Optional<ButtonType> resultado = salir.showAndWait();

        if (resultado.get() == ButtonType.OK) {

            if (limpiarVentas(false)) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
                    ac_empleado.getChildren().setAll(pane);
                    ConexionBD.desconectar();
                } catch (IOException ex) {
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            salir.close();
        }

    }

    @FXML
    private void inicioAction(ActionEvent event) throws SQLException {

        if (limpiarVentas(false)) {
            pn_fondoIconos.setVisible(true);
            pn_productos.setVisible(false);
            pn_ventas.setVisible(false);
            pn_incidencias.setVisible(false);
        }

    }

    @FXML
    private void productosAction(ActionEvent event) {
        Object evento = event.getSource();
        pn_fondoIconos.setVisible(false);
        pn_productos.setVisible(true);

        if (evento == bt_productos) {
            cargarProductos();
        }

        if (evento == bt_atrasProductos) {
            pn_fondoIconos.setVisible(true);
            pn_productos.setVisible(false);
        }

    }

    public void cargarProductos() {
        Alert errorCarga;
        try {
            /* TABLE VIEW PRODUCTOS */
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(empleadoActual.getIdTienda()));
            tv_productos.setItems(listaProductos);
            tb_referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_precioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
            tb_precioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            tb_iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
            tb_stock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos");
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void alertCamposVacios() {
        Alert faltaCampos;
        faltaCampos = new Alert(Alert.AlertType.ERROR);
        faltaCampos.setTitle("Error Añadir");
        faltaCampos.setHeaderText("Por favor rellene todos los campos.");
        estiloAlerta.darleEstiloAlPanel(faltaCampos);
        faltaCampos.showAndWait();

    }

    @FXML
    private void IncidenciasAction(ActionEvent event) {
        Object evento = event.getSource();

        pn_fondoIconos.setVisible(false);
        pn_incidencias.setVisible(true);

        if (evento == bt_atrasIncidencias) {
            pn_fondoIconos.setVisible(true);
            pn_incidencias.setVisible(false);
            limpiarIncidencias();
        }
    }

    @FXML
    private void añadirIncidenciaAction(ActionEvent event) {
        Object evento = event.getSource();
        Incidencia incidencia;
        String tipo = cb_tipoIncidencia.getValue(), especifico = tf_especificarTipoIncidencia.getText(),
                descripcion = ta_descripcionIncidencia.getText();
        Alert errorTipo, errorInsertar, incidenciaCreada, sinDescripcion;

        if (tipo.equalsIgnoreCase("Otros")) {
            ta_descripcionIncidencia.setLayoutY(148);
            tf_especificarTipoIncidencia.setVisible(true);

        } else if (!tipo.equalsIgnoreCase("Otros")) {
            ta_descripcionIncidencia.setLayoutY(107);
            tf_especificarTipoIncidencia.setVisible(false);
        }

        if (evento == bt_anadirIncidencia) {

            if (tipo.equalsIgnoreCase("Tipos de Incidencia")) {

                errorTipo = new Alert(AlertType.ERROR);
                errorTipo.setTitle("Incidencias Error");
                errorTipo.setHeaderText("Por favor, eliga el tipo de inciencia.");
                estiloAlerta.darleEstiloAlPanel(errorTipo);
                errorTipo.showAndWait();

            } else if (tipo.equalsIgnoreCase("Otros") && especifico.isEmpty()) {

                errorTipo = new Alert(AlertType.ERROR);
                errorTipo.setTitle("Incidencias Error");
                errorTipo.setHeaderText("Por favor, especifique el tipo de "
                        + "incidencia ocurrido.");
                errorTipo.setContentText("Normalmente con una o dos palabras basta");
                estiloAlerta.darleEstiloAlPanel(errorTipo);
                errorTipo.showAndWait();

            } else if (descripcion.isEmpty()) {
                sinDescripcion = new Alert(AlertType.ERROR);
                sinDescripcion.setTitle("Incidencias Error");
                sinDescripcion.setHeaderText("Por favor, introduzca una descripción.");
                estiloAlerta.darleEstiloAlPanel(sinDescripcion);
                sinDescripcion.showAndWait();

            } else {
                /*(idIncidencia,idTienda,idTrabajador,tipo,fecha,descripcion,leido)*/
                if (tf_especificarTipoIncidencia.isVisible()) {
                    descripcion = especifico + ": " + descripcion;

                }

                incidencia = new Incidencia(java.sql.Types.NULL,
                        empleadoActual.getIdTienda(),
                        empleadoActual.getId(),
                        tipo,
                        dp_fechaInciendia.getValue(),
                        descripcion,
                        "No leido");

                try {
                    this.incidencia.crearIncidencia(incidencia, empleadoActual);
                    incidenciaCreada = new Alert(AlertType.INFORMATION);
                    incidenciaCreada.setTitle("Incidencias");
                    incidenciaCreada.setHeaderText("Incidencia creada con exito.");
                    incidenciaCreada.setContentText(empleadoActual.getNombre()
                            + ", gracias por informar de la incidencia");
                    estiloAlerta.darleEstiloAlPanel(incidenciaCreada);
                    incidenciaCreada.showAndWait();

                } catch (SQLException ex) {
                    errorInsertar = new Alert(AlertType.ERROR);
                    errorInsertar.setTitle("Error Insertar");
                    errorInsertar.setHeaderText("No se ha podido insertar la incidencia.");
                    errorInsertar.setContentText("Error: " + ex.getMessage());
                    estiloAlerta.darleEstiloAlPanel(errorInsertar);
                    errorInsertar.showAndWait();
                }
            }
        }

    }

    public void limpiarIncidencias() {
        ta_descripcionIncidencia.clear();
        dp_fechaInciendia.setValue(LocalDate.now());
        ta_descripcionIncidencia.setLayoutY(107);
        tf_especificarTipoIncidencia.setVisible(false);
        cb_tipoIncidencia.setPromptText("Tipos de Incidencia");
        cb_tipoIncidencia.setValue("Tipos de Incidencia");
        tf_especificarTipoIncidencia.clear();
    }

    @FXML
    private void perfilAction(ActionEvent event) {

    }

    @FXML
    private void ventasAction(ActionEvent event) {
        Object evento = event.getSource();

        if (evento == bt_ventas) {
            pn_fondoIconos.setVisible(false);
            pn_ventas.setVisible(true);
            cargarProductosParaVenta();
            idSiguienteVenta = siguienteIdVenta();
        } else if (evento == bt_atrasVentas && limpiarVentas(false)) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
        } else if (evento == bt_Inicio && limpiarVentas(false)) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
        }

    }

    public void cargarProductosParaVenta() {
        Alert errorCarga;
        try {
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(empleadoActual.getIdTienda()));
            tv_productosVenta.setItems(listaProductos);
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error");
            errorCarga.setHeaderText("No se ha podido cargar los productos de tienda.");
            errorCarga.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

    }

    public boolean limpiarVentas(boolean creada) {
        Alert ventaEnCurso;
        boolean limpiar = false;

        if (listaDetalles.isEmpty()) {
            limpiar = true;
            actualizarTicketAtiempoReal(listaDetalles);
//            lb_TotalTicket.setText(redondear.format(venta.calcularTotal(listaDetalles)) + " €");
            lb_TotalTicket.setText("0 €");
        } else if (creada) {
            limpiar = true;
            listaDetalles.clear();
            actualizarTicketAtiempoReal(listaDetalles);
//            lb_TotalTicket.setText(redondear.format(venta.calcularTotal(listaDetalles)) + " €");
            lb_TotalTicket.setText("0 €");
            cargarProductosParaVenta();
        } else {
            ventaEnCurso = new Alert(AlertType.CONFIRMATION);
            ventaEnCurso.setTitle("Venta");
            ventaEnCurso.setHeaderText("Venta en curso.");
            ventaEnCurso.setContentText(empleadoActual.getNombre()
                    + ", hay una venta en curso, ¿está seguro de salir?.\n"
                    + "Se cancelara la venta actual.");
            estiloAlerta.darleEstiloAlPanel(ventaEnCurso);

            Optional<ButtonType> resultado = ventaEnCurso.showAndWait();

            if (resultado.get() == ButtonType.OK) {
                limpiar = true;
                listaDetalles.clear();
                actualizarTicketAtiempoReal(listaDetalles);
//                lb_TotalTicket.setText(redondear.format(venta.calcularTotal(listaDetalles)) + " €");
                lb_TotalTicket.setText("0 €");
            }
        }

        return limpiar;
    }

    @FXML
    private void añadirProductoAction(MouseEvent event) {
        Object evento = event.getSource();
        Producto seleccionado = tv_productosVenta.getFocusModel().getFocusedItem();
        MouseButton click = event.getButton();
        Alert elegirCantidad, errorCantidad, errorSeleccion, cambiarCantidad;
        DetalleVenta detalle;
        int cantidad, stock = 0;
        Double precioTotal, precioFormateado;
        ButtonType marcado;
        boolean modificarCantidad = false, cantidadDesbordada = false,
                cambioCantidad = false;

        try {
            stock = producto.cantidad(empleadoActual.getIdTienda(), seleccionado);
        } catch (SQLException ex) {
            errorCantidad = new Alert(AlertType.ERROR);
            errorCantidad.setTitle("Error");
            errorCantidad.setHeaderText("No se ha podido comprobar las cantidades.");
            errorCantidad.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCantidad);
            errorCantidad.showAndWait();
        }

        if (click.equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (detallesVenta.contieneProducto(listaDetalles, seleccionado)) {

                    errorSeleccion = new Alert(AlertType.CONFIRMATION);
                    errorSeleccion.setTitle("Error");
                    errorSeleccion.setHeaderText("Este producto ya ha sido seleccionado");
                    errorSeleccion.setContentText("¿Deseas modificar la cantidad o elminar el producto?");
                    ButtonType modificar = new ButtonType("Modificar");
                    ButtonType borrar = new ButtonType("Borrar");
                    ButtonType cancelar = new ButtonType("Cancelar");
                    errorSeleccion.getButtonTypes().setAll(modificar, borrar, cancelar);
                    estiloAlerta.darleEstiloAlPanel(errorSeleccion);

                    Optional<ButtonType> resultado = errorSeleccion.showAndWait();
                    marcado = resultado.get();

                    if (marcado == modificar) {

                        modificarCantidad = true;
                        cambioCantidad = true;

                    } else if (marcado == borrar) {
                        listaDetalles = detallesVenta.borrarProducto(listaDetalles, seleccionado);

                    } else if (marcado == cancelar) {
                        errorSeleccion.close();
                    }

                } else if (stock == 0) {
                    errorCantidad = new Alert(AlertType.CONFIRMATION);
                    errorCantidad.setTitle("Cantidad");
                    errorCantidad.setHeaderText("Sin stock.");
                    errorCantidad.setContentText("El articulo " + seleccionado.getNombre()
                            + " no tiene stock disponible.\n "
                            + empleadoActual.getNombre()
                            + ", diculpa las molestias. "
                            + "¿Quiere notificar al gerente?"
                    );
                    estiloAlerta.darleEstiloAlPanel(errorCantidad);
                    Optional<ButtonType> subResultado = errorCantidad.showAndWait();

                    if (subResultado.get() == ButtonType.OK) {
                        solicitarStock(seleccionado);
                    }

                } else {
                    modificarCantidad = true;
                }

                if (modificarCantidad) {

                    elegirCantidad = new Alert(AlertType.CONFIRMATION);
                    elegirCantidad.setTitle("Cantidad");
                    elegirCantidad.setHeaderText("Producto selecciondo : " + seleccionado.getNombre());

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    NumericTextField nf_cantidad = new NumericTextField();
                    nf_cantidad.setPromptText("Cantidad");
                    grid.add(nf_cantidad, 1, 0);
                    elegirCantidad.getDialogPane().setContent(grid);
                    estiloAlerta.darleEstiloAlPanel(elegirCantidad);
                    Optional<ButtonType> resultado;
                    resultado = elegirCantidad.showAndWait();

                    if (resultado.get() == ButtonType.OK) {
                        cantidad = Integer.valueOf(nf_cantidad.getText());

                        if (cantidad <= stock) {
                            if (cambioCantidad) {
                                listaDetalles = detallesVenta.borrarProducto(listaDetalles, seleccionado);
                            }

                            precioTotal = seleccionado.getPrecioVenta() * cantidad;
                            precioFormateado = Math.round(precioTotal * 100.0) / 100.0;


                            /*(int idVenta, int referencia, int cantidad, Double precio)*/
                            detalle = new DetalleVenta(idSiguienteVenta,
                                    seleccionado.getReferencia(),
                                    cantidad,
                                    precioFormateado);

                            listaDetalles.add(detalle);

                        } else {
                            errorCantidad = new Alert(AlertType.WARNING);
                            errorCantidad.setTitle("Cantidad");
                            errorCantidad.setHeaderText("Cantidad mayor a la disponible"
                                    + " en el almacen.");
                            errorCantidad.setContentText(empleadoActual.getNombre()
                                    + ", diculpa las molestias.");
                            estiloAlerta.darleEstiloAlPanel(errorCantidad);
                            errorCantidad.showAndWait();
                        }
                    }

                    if (resultado.get() == ButtonType.CANCEL) {

                    }
                }
                actualizarTicketAtiempoReal(listaDetalles);
                lb_TotalTicket.setText(redondear.format(venta.calcularTotal(listaDetalles)) + " €");
            }
        }
    }

    public void actualizarTicketAtiempoReal(List<DetalleVenta> listaDetalle) {
        ObservableList<DetalleVenta> detalle = FXCollections.observableArrayList(listaDetalle);
        lv_detalleVentas.setItems(detalle);

    }

    public void solicitarStock(Producto producto) {
        Alert aviso;

        Incidencia sinStock = new Incidencia(java.sql.Types.NULL,
                empleadoActual.getIdTienda(),
                empleadoActual.getId(),
                "Stock",
                dp_fechaInciendia.getValue(),
                "El producto " + producto.getNombre() + " no tenia stock disponible"
                + " a fecha de " + LocalDate.now().toString(),
                "No leido");
        try {
            incidencia.crearIncidencia(sinStock, empleadoActual);
            aviso = new Alert(AlertType.INFORMATION);
            aviso.setTitle("Sin stock");
            aviso.setHeaderText("Se ha avisado al gerente.");
            aviso.setContentText(empleadoActual.getNombre()
                    + ", en las próximas horas se repondrán las unidades.");
            estiloAlerta.darleEstiloAlPanel(aviso);
            aviso.showAndWait();

        } catch (SQLException ex) {
            aviso = new Alert(AlertType.ERROR);
            aviso.setTitle("Error");
            aviso.setHeaderText("No se ha podido avisar al gerente.");
            aviso.setContentText(empleadoActual.getNombre()
                    + ", diculpa las molestias.");
            estiloAlerta.darleEstiloAlPanel(aviso);
            aviso.showAndWait();
        }
    }

    public Integer siguienteIdVenta() {
        Alert errorCargaId;
        Integer idVenta = null;

        try {
            idVenta = this.venta.mostrarSiguienteID();
        } catch (SQLException ex) {
            errorCargaId = new Alert(AlertType.ERROR);
            errorCargaId.setTitle("Error");
            errorCargaId.setHeaderText("No se ha podido cargar el id de la siguiente venta");
            errorCargaId.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCargaId);
            errorCargaId.showAndWait();
        }

        return idVenta;
    }

    @FXML
    private void generarVentaAction(ActionEvent event) {
        Alert sinProductos, errorVenta, ventaRealizada, errorGenerarTicket;
        Venta venta;
//        TextArea textoTicket;
//        GridPane contenidoExpansible;
//        Path directorioTicket;

        if (!listaDetalles.isEmpty()) {
            /*(int idVenta, int idTienda, int idtrabajdor, LocalDate fecha, List<DetalleVenta> detalle)*/
            venta = new Venta(idSiguienteVenta,
                    empleadoActual.getIdTienda(),
                    empleadoActual.getId(),
                    LocalDate.now(),
                    listaDetalles);

            try {
                this.venta.insertarVenta(venta, listaDetalles);
                detallesVenta.detallesVenta(listaDetalles, empleadoActual.getIdTienda());
                this.venta.generarTicket(venta, listaDetalles);
                ventaRealizada = new Alert(AlertType.INFORMATION);
                ventaRealizada.setTitle("Venta");
                ventaRealizada.setHeaderText("La venta con id: " + venta.getIdVenta()
                        + " se ha realizado con exito.");
                limpiarVentas(true);

//                textoTicket = new TextArea(this.venta.leerTicket(directorioTicket));
//
//                textoTicket.setEditable(false);
//                textoTicket.setWrapText(true);
//                textoTicket.setMaxWidth(Double.MAX_VALUE);
//                textoTicket.setMaxHeight(Double.MAX_VALUE);
//                contenidoExpansible = new GridPane();
//                contenidoExpansible.setMaxWidth(Double.MAX_VALUE);
//                contenidoExpansible.add(textoTicket, 0, 1);
//                contenidoExpansible.getStylesheets().add(getClass().getResource("empleado.css").toExternalForm());
//                ventaRealizada.getDialogPane().setExpandableContent(contenidoExpansible);
                estiloAlerta.darleEstiloAlPanel(ventaRealizada);
                ventaRealizada.showAndWait();
            } catch (SQLException ex) {
                errorVenta = new Alert(AlertType.ERROR);
                errorVenta.setTitle("Error Venta");
                errorVenta.setHeaderText("No se ha podido generar la venta.");
                errorVenta.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorVenta);
                errorVenta.showAndWait();

            } catch (IOException ex) {
                errorGenerarTicket = new Alert(AlertType.ERROR);
                errorGenerarTicket.setTitle("Error Ticket");
                errorGenerarTicket.setHeaderText("No se ha podido generar el ticket.");
                errorGenerarTicket.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorGenerarTicket);
                errorGenerarTicket.showAndWait();
            }

        } else {
            sinProductos = new Alert(AlertType.ERROR);
            sinProductos.setTitle("Error");
            sinProductos.setHeaderText("Seleccione un producto antes.");
            estiloAlerta.darleEstiloAlPanel(sinProductos);
            sinProductos.showAndWait();
        }

    }

    public void cargarTooltips() {
        Tooltip tt_atras, tt_incidencias, tt_productos, tt_anadirIncidencia, tt_ventas,
                tt_finCompra;

        tt_incidencias = new Tooltip("Incidencias");
        tt_incidencias.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_incidencias, tt_incidencias);

        tt_productos = new Tooltip("Productos");
        tt_productos.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_productos, tt_productos);

        tt_atras = new Tooltip("Volver");
        tt_atras.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_atrasProductos, tt_atras);
        Tooltip.install(bt_atrasVentas, tt_atras);
        Tooltip.install(bt_atrasIncidencias, tt_atras);

        tt_anadirIncidencia = new Tooltip("Añadir incidencia");
        tt_anadirIncidencia.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_anadirIncidencia, tt_anadirIncidencia);

        tt_ventas = new Tooltip("Ventas");
        tt_ventas.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_ventas, tt_ventas);

        tt_finCompra = new Tooltip("Finalizar compra");
        tt_finCompra.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_regVenta, tt_finCompra);

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
