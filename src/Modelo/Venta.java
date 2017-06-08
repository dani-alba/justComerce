package Modelo;

import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class Venta {

    private final IntegerProperty idVenta;
    private final IntegerProperty idTienda;
    private final IntegerProperty idTrabajador;
    private final ObjectProperty<LocalDate> fecha;
    private final ListProperty<DetalleVenta> detalles;

    public Venta(int idVenta, int idTienda, int idtrabajdor, LocalDate fecha, List<DetalleVenta> detalle) {
        this.idVenta = new SimpleIntegerProperty(idVenta);
        this.idTienda = new SimpleIntegerProperty(idTienda);
        this.idTrabajador = new SimpleIntegerProperty(idtrabajdor);
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.detalles = new SimpleListProperty<>();

    }

    /*--------------------------- GET AND SET ----------------------------- */
    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate value) {
        fecha.set(value);
    }

    public ObjectProperty fechaProperty() {
        return fecha;
    }

    public int getIdTrabajador() {
        return idTrabajador.get();
    }

    public void setIdTrabajador(int value) {
        idTrabajador.set(value);
    }

    public IntegerProperty idTrabajadorProperty() {
        return idTrabajador;
    }

    public int getIdTienda() {
        return idTienda.get();
    }

    public void setIdTienda(int value) {
        idTienda.set(value);
    }

    public IntegerProperty idTiendaProperty() {
        return idTienda;
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

    public ObservableList getDetalles() {
        return detalles.get();
    }

    public void setDetalles(ObservableList value) {
        detalles.set(value);
    }

    public ListProperty detallesProperty() {
        return detalles;
    }

    @Override
    public String toString() {
        return "idVenta=" + idVenta + ", idTienda=" + idTienda
                + ", idTrabajador=" + idTrabajador + ", fecha="
                + fecha + ", detalles=" + detalles.toString() + '}';
    }

}
