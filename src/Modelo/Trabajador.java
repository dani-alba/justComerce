/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class Trabajador {

    private final IntegerProperty id;
    private final StringProperty dni;
    private final StringProperty nombre;
    private StringProperty apellido1;
    private StringProperty apellido2;
    private final StringProperty puesto;    
    private final DoubleProperty salarioBrutoAnual;
    private ObjectProperty<LocalDate> fechaAlta;
    private final StringProperty nick;
    private final StringProperty pass;
    private final ObjectProperty<LocalTime> horaEntrada;
    private final ObjectProperty<LocalTime> horaSalida;
    private final IntegerProperty idTienda;

    public Trabajador(int id, String dni, String nombre, String apellido1, String apellido2,
            String puesto, Double salario, LocalDate fecha, String nick, String pass,
            LocalTime horaEntrada,LocalTime horaSalida, int idTienda) {
        
        this.id = new SimpleIntegerProperty(id);
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido1 = new SimpleStringProperty(apellido1);
        this.apellido2 = new SimpleStringProperty(apellido2);
        this.puesto  = new SimpleStringProperty(puesto);
        this.salarioBrutoAnual = new SimpleDoubleProperty(salario);
        this.fechaAlta  = new SimpleObjectProperty<>(fecha);
        this.nick = new SimpleStringProperty(nick);
        this.pass = new SimpleStringProperty(pass);
        this.horaEntrada = new SimpleObjectProperty<>(horaEntrada);
        this.horaSalida = new SimpleObjectProperty<>(horaSalida);
        this.idTienda = new SimpleIntegerProperty(idTienda);
        
    }
    
    public Trabajador(int id, String dni, String nombre,
            String puesto, Double salario, String nick, String pass,
            LocalTime horaEntrada,LocalTime horaSalida, int idTienda) {
        
        this.id = new SimpleIntegerProperty(id);
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.puesto  = new SimpleStringProperty(puesto);
        this.salarioBrutoAnual = new SimpleDoubleProperty(salario);
        this.nick = new SimpleStringProperty(nick);
        this.pass = new SimpleStringProperty(pass);
        this.horaEntrada = new SimpleObjectProperty<>(horaEntrada);
        this.horaSalida = new SimpleObjectProperty<>(horaSalida);
        this.idTienda = new SimpleIntegerProperty(idTienda);
        
        
    }

    
    // * * * * * * * * * * GET AND SET * * * * * * * * * * 
    
    public int getIdTienda() {
        return idTienda.get();
    }

    public void setIdTienda(int value) {
        idTienda.set(value);
    }

    public IntegerProperty idTiendaProperty() {
        return idTienda;
    }

    public LocalTime getHoraSalida() {
        return horaSalida.get();
    }

    public void setHoraSalida(LocalTime value) {
        horaSalida.set(value);
    }

    public ObjectProperty horaSalidaProperty() {
        return horaSalida;
    }
    
    public LocalTime getHoraEntrada() {
        return horaEntrada.get();
    }

    public void setHoraEntrada(LocalTime value) {
        horaEntrada.set(value);
    }

    public ObjectProperty horaEntradaProperty() {
        return horaEntrada;
    }
    
    public String getPass() {
        return pass.get();
    }

    public void setPass(String value) {
        pass.set(value);
    }

    public StringProperty passProperty() {
        return pass;
    }
    

    public String getNick() {
        return nick.get();
    }

    public void setNick(String value) {
        nick.set(value);
    }

    public StringProperty nickProperty() {
        return nick;
    }
    

    public LocalDate getFechaAlta() {
        return fechaAlta.get();
    }

    public void setFechaAlta(LocalDate value) {
        fechaAlta.set(value);
    }

    public ObjectProperty fechaAltaProperty() {
        return fechaAlta;
    }
    

    public double getSalarioBrutoAnual() {
        return salarioBrutoAnual.get();
    }

    public void setSalarioBrutoAnual(double value) {
        salarioBrutoAnual.set(value);
    }

    public DoubleProperty salarioBrutoAnualProperty() {
        return salarioBrutoAnual;
    }
    
    public String getPuesto() {
        return puesto.get();
    }

    public void setPuesto(String value) {
        puesto.set(value);
    }

    public StringProperty puestoProperty() {
        return puesto;
    }
    
    public String getApellido2() {
        return apellido2.get();
    }

    public void setApellido2(String value) {
        apellido2.set(value);
    }

    public StringProperty apellido2Property() {
        return apellido2;
    }
    

    public String getApellido1() {
        return apellido1.get();
    }

    public void setApellido1(String value) {
        apellido1.set(value);
    }

    public StringProperty apellido1Property() {
        return apellido1;
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
    

    public String getDni() {
        return dni.get();
    }

    public void setDni(String value) {
        dni.set(value);
    }

    public StringProperty dniProperty() {
        return dni;
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
    public String toString() {
        return this.nombre.get();
    }
    
    public String verDatos(){
        return ("ID: " + this.id.get()  + "\n" +
                "DNI: " + this.dni.get() + "\n" + 
                "Nombre: " + this.nombre.get() + "\n" + 
                "Apellido/s: " + this.apellido1.get() + " " + this.apellido2.get() + "\n" + 
                "Puesto: " + this.puesto.get() + "\n" + 
                "Salario: " + this.salarioBrutoAnual.get() + "\n" + 
                "Fecha de alta: " + this.fechaAlta.get() + "\n" + 
                "Nick: " + this.nick.get() + "\n" +
                "Horarios :\n" +
                "         └ Hora de entrada: " + this.horaEntrada.get() + "\n" +
                "         └ Hora de salida: " + this.horaSalida.get() + "\n" +
                "Id tienda: " + this.idTienda.get() + "\n") ;
    }

    
    
}
