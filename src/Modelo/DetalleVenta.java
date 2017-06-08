package Modelo;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DetalleVenta {

    private final IntegerProperty idVenta;
    private final IntegerProperty referencia;
    private final IntegerProperty cantidad;
    private final DoubleProperty precio;
    private ProductoDAO producto;

    public DetalleVenta(int idVenta, int referencia, int cantidad, Double precio) {
        this.idVenta = new SimpleIntegerProperty(idVenta);
        this.referencia = new SimpleIntegerProperty(referencia);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio = new SimpleDoubleProperty(precio);
        this.producto = new ProductoDAO(ConexionBD.conexion);
    }

    /*--------------------------- GET AND SET ----------------------------- */
    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double value) {
        precio.set(value);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int value) {
        cantidad.set(value);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public int getReferencia() {
        return referencia.get();
    }

    public void setReferencia(int value) {
        referencia.set(value);
    }

    public IntegerProperty referenciaProperty() {
        return referencia;
    }

    public int getIdVenta() {
        return idVenta.get();
    }

    public void setIdVenta(int value) {
        idVenta.set(value);
    }

    public IntegerProperty idVentaProperty() {
        return idVenta;
    }

    @Override
    public String toString() {
        String texto = "Error al visualizar";
        try {
            texto = String.format("%-20s  %-20s  %-20s", producto.nombreProducto(this.referencia.get()), this.cantidad.get(), this.precio.get());
        } catch (SQLException ex) {
            Logger.getLogger(DetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        return texto;
    }

}
