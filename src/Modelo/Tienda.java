/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class Tienda {

    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty direccion;
    private final StringProperty ciudad;

    public Tienda(int id, String nombre, String direccion, String ciudad) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.ciudad = new SimpleStringProperty(ciudad);
    }
    
    
    // * * * * * * * * * * GET AND SET * * * * * * * * * * * * 

    public String getCiudad() {
        return ciudad.get();
    }

    public void setCiudad(String value) {
        ciudad.set(value);
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }
    

    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String value) {
        direccion.set(value);
    }

    public StringProperty direccionProperty() {
        return direccion;
    }
    

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String value) {
        nombre.set(value);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }
    

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty IdProperty() {
        return id;
    }
    
    @Override
    public String toString() {
        return this.nombre.get();
        
    }
    
}
