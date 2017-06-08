package Datos;

import Modelo.DetalleVenta;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class DetallesVentaDAO {

    private Connection conexion;
    private Double totalPrecioDetalles;

    public DetallesVentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void detallesVenta(List<DetalleVenta> listaDetalles, int idTienda) throws SQLException {
        PreparedStatement psDetalles, psRestarCantidad;
        DetalleVenta detalle;
        Double precioTotal;
        totalPrecioDetalles = 0.0;

        psDetalles = conexion.prepareStatement("INSERT INTO detallesventa "
                + " (idVenta,referencia,cantidad,precio) "
                + "VALUES(?,?,?,?);");

        psRestarCantidad = conexion.prepareStatement("UPDATE tiendas_productos"
                + "	SET stock = stock - ?"
                + "    WHERE idTienda = ? AND idProducto = ?;");

        for (DetalleVenta listaDetalle : listaDetalles) {
            detalle = listaDetalle;
            precioTotal = detalle.getPrecio();

            psDetalles.setInt(1, detalle.getIdVenta());
            psDetalles.setInt(2, detalle.getReferencia());
            psDetalles.setInt(3, detalle.getCantidad());
            psDetalles.setDouble(4, precioTotal);

            totalPrecioDetalles = totalPrecioDetalles + precioTotal;
            psDetalles.executeUpdate();

            psRestarCantidad.setInt(1, detalle.getCantidad());
            psRestarCantidad.setInt(2, idTienda);
            psRestarCantidad.setInt(3, detalle.getReferencia());
            psRestarCantidad.executeUpdate();

        }

    }

    public boolean contieneProducto(List<DetalleVenta> listaDetalle, Producto producto) {
        boolean existe = false;

        for (DetalleVenta detalle : listaDetalle) {

            if (detalle.getReferencia() == producto.getReferencia()) {
                existe = true;
            }
        }

        return existe;
    }

    public List<DetalleVenta> borrarProducto(List<DetalleVenta> listaDetalles, Producto producto) {
        boolean existe = false, siguiente = true;

        Iterator<DetalleVenta> it = listaDetalles.iterator();
        while (it.hasNext() && siguiente) {
            DetalleVenta detalle = it.next();
            if (detalle.getReferencia() == producto.getReferencia()) {
                listaDetalles.remove(detalle);
                siguiente = false;
            }
        }

        return listaDetalles;
    }

}
