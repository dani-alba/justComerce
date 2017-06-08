package Datos;

import Modelo.Incidencia;
import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private Connection conexion;

    public IncidenciaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void crearIncidencia(Incidencia incidencia, Trabajador trabajador) throws SQLException {
        PreparedStatement psIncidencias;
        psIncidencias = conexion.prepareStatement("INSERT INTO incidencias "
                + "(idIncidencia,idTienda,idTrabajador,tipo,fecha,descripcion,leido) "
                + " VALUES(?,?,?,?,?,?,?);");

        psIncidencias.setNull(1, java.sql.Types.NULL);
        psIncidencias.setInt(2, trabajador.getIdTienda());
        psIncidencias.setInt(3, trabajador.getId());
        psIncidencias.setString(4, incidencia.getTipo());
        psIncidencias.setDate(5, Date.valueOf(incidencia.getFecha()));
        psIncidencias.setString(6, incidencia.getDescripcion());
        psIncidencias.setString(7, incidencia.getLeido());
        psIncidencias.executeUpdate();

    }

    public List<Incidencia> cargarIncidencias(int idTienda, int modo) throws SQLException {
        PreparedStatement psIncidencias = null;
        ResultSet rsIncidencias;
        Incidencia incidencia;
        List<Incidencia> listaIncidencias = new ArrayList<>();
        boolean elegido = false;

        switch (modo) {
            case 0:
                psIncidencias = conexion.prepareStatement("SELECT * FROM incidencias WHERE idTienda=? AND leido='Leido' ORDER BY fecha asc;;");
                psIncidencias.setInt(1, idTienda);
                elegido = true;
                break;
            case 1:
                psIncidencias = conexion.prepareStatement("SELECT * FROM incidencias WHERE idTienda=? AND leido='No leido' ORDER BY fecha asc;;");
                psIncidencias.setInt(1, idTienda);
                elegido = true;
                break;
        }

        if (elegido) {
            rsIncidencias = psIncidencias.executeQuery();
            while (rsIncidencias.next()) {
                incidencia = new Incidencia(rsIncidencias.getInt("idIncidencia"),
                        rsIncidencias.getInt("idTienda"),
                        rsIncidencias.getInt("idTrabajador"),
                        rsIncidencias.getString("tipo"),
                        rsIncidencias.getDate("fecha").toLocalDate(),
                        rsIncidencias.getString("descripcion"),
                        rsIncidencias.getString("leido"));

                listaIncidencias.add(incidencia);
            }
        }
        return listaIncidencias;
    }

    public void cambiarAleida(Incidencia incidencia) throws SQLException {
        PreparedStatement psIncidencias;
        psIncidencias = conexion.prepareStatement("UPDATE incidencias "
                + "SET leido = 'Leido' "
                + "WHERE idIncidencia = ?;");
        psIncidencias.setInt(1, incidencia.getId());
        psIncidencias.executeUpdate();
    }
}
