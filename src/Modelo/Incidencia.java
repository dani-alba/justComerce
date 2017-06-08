package Modelo;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Incidencia {

    private final IntegerProperty id;
    private final IntegerProperty idTienda;
    private final IntegerProperty idTrabajador;
    private final StringProperty tipo;
    private final ObjectProperty<LocalDate> fecha;
    private final StringProperty descripcion;
    private final StringProperty leido;

    public Incidencia(int id, int idTienda, int idTrabajador, String tipo, LocalDate fecha, String descripcion, String leido) {
        this.id = new SimpleIntegerProperty(id);
        this.idTienda = new SimpleIntegerProperty(idTienda);
        this.idTrabajador = new SimpleIntegerProperty(idTrabajador);
        this.tipo = new SimpleStringProperty(tipo);
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.leido = new SimpleStringProperty(leido);
    }
    
    /*--------------------------- GET AND SET ---------------------------------*/

    public String getLeido() {
        return leido.get();
    }

    public void setLeido(String value) {
        leido.set(value);
    }

    public StringProperty leidoProperty() {
        return leido;
    }
    

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String value) {
        descripcion.set(value);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }
    

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate value) {
        fecha.set(value);
    }

    public ObjectProperty fechaProperty() {
        return fecha;
    }
    

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String value) {
        tipo.set(value);
    }

    public StringProperty tipoProperty() {
        return tipo;
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
    

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.id.get());
    }
    
}
