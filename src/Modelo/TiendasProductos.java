/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author daw
 */
public class TiendasProductos {

    private final IntegerProperty idTienda;
    private final IntegerProperty idProducto;
    private final IntegerProperty stock;

    public TiendasProductos(int idTienda, int idProducto, int stock) {
        
        this.idTienda = new SimpleIntegerProperty(idTienda) ;
        this.idProducto = new SimpleIntegerProperty(idProducto); 
        this.stock = new SimpleIntegerProperty(stock);
        
    }
    
    // * * * * * * * * * * GET AND SET * * * * * * * * * * * * 
    
    public int getStock() {
        return stock.get();
    }

    public void setStock(int value) {
        stock.set(value);
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
    
    public int getIdProducto() {
        return idProducto.get();
    }

    public void setIdProducto(int value) {
        idProducto.set(value);
    }

    public IntegerProperty idProductoProperty() {
        return idProducto;
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
        
}
