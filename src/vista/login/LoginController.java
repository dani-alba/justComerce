package vista.login;

import Modelo.Alerta.Alerta;
import Datos.ConexionBD;
import Datos.TrabajadorDAO;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.Timer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class LoginController implements Initializable {

    private boolean mayusculasActivadas = false;
    private ConexionBD conexion;
    private TrabajadorDAO trabajadorDAO;
    private Alerta estiloAlerta;
    private String user;

    /*ATRIBUTOS FXML*/
    @FXML
    private TextField tf_user;
    @FXML
    private Label lbl_inciarSesion;
    @FXML
    private Button bt_iniciarSesion;
    @FXML
    private Label lbl_bienvendio;
    @FXML
    private Separator separator;
    @FXML
    private Pane pn_inciarSesion;
    @FXML
    private Pane pn_principalBienvenida;
    @FXML
    private PasswordField pf_contraseña;
    @FXML
    private Label lb_errorIniciar;
    @FXML
    private ImageView iv_bloqMayus;
    @FXML
    private AnchorPane paneLogin;
    @FXML
    private Pane pn_Bienvenida;
    @FXML
    private Label lb_user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new ConexionBD();
        estiloAlerta = new Alerta();
        FadeTransition transicion = new FadeTransition(Duration.millis(4000), pn_principalBienvenida);
        transicion.setAutoReverse(true);
        transicion.setFromValue(1.0);
        transicion.setToValue(0.0);
        pn_Bienvenida.setVisible(false);

        Timer timer = new Timer(1000, new ActionListener() { // Genera el difuminación del panel principal
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                transicion.play();
            }
        });
        timer.setRepeats(false);
        timer.start();

        Timer tiempoTransicion = new Timer(5000, new ActionListener() { // Permite que podemaos interactuar con el segundo panel
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                pn_principalBienvenida.setVisible(false);

            }
        });
        tiempoTransicion.setRepeats(false);
        tiempoTransicion.start();

        Image img_warnning = new Image("/vista/login/images/warningIcon3.png");
        iv_bloqMayus.setImage(img_warnning);
        iv_bloqMayus.setDisable(true);
        iv_bloqMayus.setVisible(false);
    }

    @FXML
    private void inciarSesionAction(ActionEvent event) {
        iniciarSesion();
    }

    public void iniciarSesion() {
        user = tf_user.getText();
        String pass = pf_contraseña.getText();
        StrongPasswordEncryptor passwordEncryptor;
        String passEncriptada = null;
        Alert bienvenida;
        try {

            if (!user.isEmpty() && !pass.isEmpty()) {

                if (conexion.conectar("jdbc:mysql://localhost:3306/justComerce", "root", "root", user)) {
                    passwordEncryptor = new StrongPasswordEncryptor();
                    passEncriptada = passwordEncryptor.encryptPassword(pass);

                    if (passwordEncryptor.checkPassword(pass, conexion.contraseña(user))) {

                        switch (conexion.puesto(user)) {
                            case "Gerente":
                                if (conexion.cambiarContraseña(user)) {
                                    if (cambiarContraseña(user)) {
                                        bienvenida = bienvenida();
                                        lb_errorIniciar.setVisible(false);
                                        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/gerente/GerenteFXML.fxml"));
                                        paneLogin.getChildren().setAll(pane);
                                        cerrarBienvendia(bienvenida);

                                    }
                                } else {
                                    bienvenida = bienvenida();
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/gerente/GerenteFXML.fxml"));
                                    paneLogin.getChildren().setAll(pane);
                                    cerrarBienvendia(bienvenida);
                                }
                                break;

                            case "Dependiente":
                                if (conexion.cambiarContraseña(user)) {
                                    if (cambiarContraseña(user)) {
                                        bienvenida = bienvenida();
                                        lb_errorIniciar.setVisible(false);
                                        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/Empleado/EmpleadoFXML.fxml"));
                                        paneLogin.getChildren().setAll(pane);
                                        cerrarBienvendia(bienvenida);
                                    }
                                } else {
                                    bienvenida = bienvenida();
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/Empleado/EmpleadoFXML.fxml"));
                                    paneLogin.getChildren().setAll(pane);
                                    cerrarBienvendia(bienvenida);
                                }
                                break;
                            default:
                                throw new AssertionError();
                        }

                    } else {
                        lb_errorIniciar.setText("Usuario o contraseña erroneos");
                        lb_errorIniciar.setVisible(true);
                        lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.7);"
                                + " -fx-border-radius:2px;");
                    }

                } else {
                    lb_errorIniciar.setText("Error en la conexión");
                    lb_errorIniciar.setVisible(true);
                    lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.7);"
                            + " -fx-border-radius:2px;");
                }

            } else {
                lb_errorIniciar.setVisible(true);
                lb_errorIniciar.setText("Rellene los campos");
                lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.7);"
                        + " -fx-border-radius:2px;");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Alert bienvenida() {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Bienvenido");
        alerta.setHeaderText("Bienvenido a JustComerce " + user);
        alerta.setGraphic(null);
        estiloAlerta.darleEstiloAlPanel(alerta);

        alerta.show();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alerta;
    }

    public boolean cambiarContraseña(String user) throws SQLException {
        trabajadorDAO = new TrabajadorDAO(ConexionBD.conexion);
        Alert alerta, subAlerta;
        String password, comprobante;
        boolean cambiado = false, salir = false;
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String passEncriptada;

        alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Modificar contraseña");
        alerta.setHeaderText("Por favor introduza su contraseña");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField pass1 = new PasswordField();
        pass1.setPromptText("Contraseña");
        PasswordField pass2 = new PasswordField();
        pass2.setPromptText("Contraseña");
        Label lbl = new Label();

        grid.add(new Label("Contraseña:"), 0, 0);
        grid.add(pass1, 1, 0);
        grid.add(new Label("Repetir contraseña:"), 0, 1);
        grid.add(pass2, 1, 1);
        grid.add(lbl, 1, 2);
        alerta.getDialogPane().setContent(grid);
        estiloAlerta.darleEstiloAlPanel(alerta);

        do {
            Optional<ButtonType> resultado = alerta.showAndWait();
            ButtonType marcado = resultado.get();
            password = pass1.getText();
            comprobante = pass2.getText();

            if (marcado == ButtonType.OK) {
                if (password.isEmpty() || comprobante.isEmpty()) {
                    lbl.setText("Rellena los dos campos");
                    lbl.setStyle("-fx-text-fill:red;");

                } else if (!password.equalsIgnoreCase(comprobante)) {
                    lbl.setText("Las contraseñas no coinciden");
                    lbl.setStyle("-fx-text-fill:red;");

                } else {
                    passEncriptada = passwordEncryptor.encryptPassword(password);
                    trabajadorDAO.cambiarContraseña(user, passEncriptada);
                    cambiado = true;
                    salir = true;
                }

            } else if (marcado == ButtonType.CANCEL) {
                salir = true;
                limpiarCampos();
                subAlerta = new Alert(AlertType.INFORMATION);
                subAlerta.setTitle("Información");
                subAlerta.setHeaderText("Inicio de sesión");
                subAlerta.setContentText("Por concidiones de seguridad es "
                        + "obligatorio cambiar de contraseña la primera vez"
                        + " que se ingresa en el sistema.");
                estiloAlerta.darleEstiloAlPanel(subAlerta);
                subAlerta.showAndWait();
            }
        } while (salir != true);
        alerta.close();

        return cambiado;
    }

    public void cerrarBienvendia(Alert alerta) {
        alerta.close();
    }

    public void limpiarCampos() {
        tf_user.clear();
        pf_contraseña.clear();
        lb_errorIniciar.setText("");
        lb_errorIniciar.setVisible(false);

    }

    @FXML
    private void conectarAction(KeyEvent event) {
        KeyCode tecla = event.getCode();

        if (tecla == KeyCode.ENTER) {
            iniciarSesion();
        }
        
        if(tecla == KeyCode.BACK_SPACE){
            lb_errorIniciar.setVisible(false);
        }
//        boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
//        System.out.println(isOn);
//        if (isOn) {
//            mayusculasActivadas = true;
//        }
//        
//        
//
//        if (mayusculasActivadas && pf_contraseña.isFocused()) {
//            iv_bloqMayus.setDisable(false);
//            iv_bloqMayus.setVisible(true);
//            mayusculasActivadas = false;
//            tf_user.setText("0");
//
//        } else if (!mayusculasActivadas && pf_contraseña.isFocused()) {
//            iv_bloqMayus.setDisable(false);
//            iv_bloqMayus.setVisible(true);
//            mayusculasActivadas = true;
//            tf_user.setText("1");
//
//        } else if (tecla == KeyCode.CAPS && pf_contraseña.isFocused() && mayusculasActivadas == true) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = false;
//            tf_user.setText("2");
//
//        } else if (tecla == KeyCode.CAPS && pf_contraseña.isFocused() && mayusculasActivadas == false) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = true;
//            tf_user.setText("3");
//
//        } else if (!pf_contraseña.isFocused() && mayusculasActivadas == true) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = false;
//            tf_user.setText("4");
//
//        } else if (!pf_contraseña.isFocused() && mayusculasActivadas == false) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = true;
//            tf_user.setText("5");
//
//        } else if (mayusculasActivadas == false) {
//            mayusculasActivadas = true;
//            tf_user.setText("6");
//
//        } else if (mayusculasActivadas == true) {
//            mayusculasActivadas = false;
//            tf_user.setText("7");
//        }
    }

}
