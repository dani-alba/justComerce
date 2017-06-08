/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.DetalleVenta;
import Modelo.Venta;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 *
 * @author Rasul
 */
public class VentaDAO {

    Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarVenta(Venta venta, List<DetalleVenta> listaDetalles) throws SQLException {

        PreparedStatement psInsertar;

        psInsertar = conexion.prepareStatement("INSERT INTO ventas "
                + "(idVenta,idTienda,idTrabajador,fechaVenta) "
                + "VALUES(?,?,?,now());");
        psInsertar.setInt(1, venta.getIdVenta());
        psInsertar.setInt(2, venta.getIdTienda());
        psInsertar.setInt(3, venta.getIdTrabajador());
        psInsertar.executeUpdate();

    }

    public int mostrarSiguienteID() throws SQLException {
        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(idVenta) AS 'siguienteVenta' FROM ventas;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();
        return resultado.getInt("siguienteVenta") + 1;
    }

    public Double calcularTotal(List<DetalleVenta> listaDetalles) {
        Double precioTotal = 0.0;
        for (DetalleVenta detalle : listaDetalles) {
            precioTotal = precioTotal + detalle.getPrecio();
        }

        return precioTotal;
    }

    public Path generarTicket(Venta venta, List<DetalleVenta> detalle) throws IOException, SQLException {
        String texto = "";
        PreparedStatement ps;
        ResultSet rs;
        int referencia, cantidad;
        double precio, total = 0.0, totalRound = 0.0;
        String nombre, dia = null, hora;
        String fecha = null;

        ps = conexion.prepareStatement("CALL listaProducto(?);");
        ps.setInt(1, venta.getIdVenta());
        rs = ps.executeQuery();

        texto += "╔═════════════════════════════════════════════════════╗ \n";
        texto += "║                                                                                      ║ \n";
        texto += "║                                     JustComerce                                      ║ \n";
        texto += "║                                    ¯¯¯¯¯¯¯¯¯¯¯¯¯                                     ║ \n";
        texto += "║                                                                    c/ Colon 16       ║ \n";
        texto += "║                                                                    46080 Valencia    ║ \n";
        texto += "║                                                                    Valencia, España  ║ \n";
        texto += "║                                                                    Tel.: 962336111   ║ \n";
        texto += "║                                                                    CIF: E-48250773   ║ \n";
        texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";
        texto += "║  REFERENCIA        NOMBRE                  CANTIDAD        PRECIO      FECHA VENTA   ║ \n";
        texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";

        while (rs.next()) {
            referencia = rs.getInt("referencia");
            nombre = rs.getString("nombre");
            cantidad = rs.getInt("cantidad");
            precio = rs.getDouble("precio");
            fecha = rs.getString("fechaVenta");

            StringTokenizer st = new StringTokenizer(fecha, " ");

            while (st.hasMoreTokens()) {
                dia = st.nextToken();
                hora = st.nextToken();
            }

            texto += String.format("║%-13s | %-25s | %-12s | %-10s | %-11s   ║\n", referencia, nombre, cantidad, precio + "€", dia);

            total = total + precio;
            totalRound = Math.rint(total * 100) / 100;
        }
        texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";
        texto += String.format("║                                     TOTAL: %-25s                 ║ \n", totalRound + "€");

        Path fichero = Paths.get(dia + "-" + venta.getIdVenta() + "-ticket.txt");
        String destino = ".\\src\\vista\\Empleado\\Tickets\\" + fichero;
        Path directorio = Paths.get(destino);
        
        try (BufferedWriter salida = Files.newBufferedWriter(directorio.toAbsolutePath(), StandardOpenOption.CREATE)) {

            salida.write(texto + "╚═════════════════════════════════════════════════════╝");

        }
        
        return directorio;
        
    }

    public String leerTicket(Path directorio) {
        String resultado = "";
        

        try (Stream<String> datos = Files.lines(directorio, StandardCharsets.ISO_8859_1)) {
            Iterator<String> it = datos.iterator();
            while (it.hasNext()) {
                resultado = resultado + it.next() + "\n";
            }
        } catch (Exception e) {
            /*Ninguno de los StandartCharsets me permite visualizar el simbolo del euro*/
        }
        return resultado;
    }
}

