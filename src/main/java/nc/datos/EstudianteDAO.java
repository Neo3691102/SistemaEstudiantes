package nc.datos;

import nc.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static nc.conexion.Conexion.getConexion;

//DAO - Data Access Object
public class EstudianteDAO {

    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps; //ayuda a preparar la sentencia SQL que lanzaremos a la BD
        ResultSet rs; //ayuda a manejar el resultado obtenido de la base de datos
        Connection con = getConexion(); // objeto para conectarse a la base de datos
        String sql =  "SELECT * FROM estudiante ORDER BY id_estudiante";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); // ejecuta el query y almacena resultado de la base de datos
            while(rs.next()){//next() itera cada uno de los registros
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error al seleccionar datos: " + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar conexion" + e.getMessage());
            }
        }
        return estudiantes;
    }

    public boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute(); //execute es cuando no recibimos info de la BD y executeQuery para cuando solicitamos info
            return true;
        }catch(Exception e){
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion" + e.getMessage());
            }
        }
        return false;
    }
    // Insert
    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                " VALUES(?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute(); //insercion es metodo execute
            return true;
        }catch(Exception e){
            System.out.println("Ocurrio un error al gregar estudiante" + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexion");
            }
        }
        return false;
    }

    // findById
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if(rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        }catch(Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: " + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Ocurrio un error al cerrar conexion" + e.getMessage());
            }

        }
        return false;
    }
    // Update
    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, email=?" +
                "WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar estudiante: " + e.getMessage());
        }
        finally{
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion");
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var estudianteDao = new EstudianteDAO();

        //Agregar estudiante
//        var nuevoEstudiante = new Estudiante("Carlos", "Lara", "55117788", "carlos@mail.com");
//        var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
//        if(agregado)
//            System.out.println("estudiante agregado: " + nuevoEstudiante);
//        else System.out.println("No se agrego el estudiante: " + nuevoEstudiante);

        // Modificamos un estudiante exsistente id = 3
//        Estudiante estudianteModificar = new Estudiante(3, "Esteban", "Juarez",
//                "66998877", "esteban@mail.com");
//        var modificado = estudianteDao.modificarEstudiante(estudianteModificar);
//        if(modificado) System.out.println("estudiante modificado: " + estudianteModificar);
//        else System.out.println("No se modifico estudiante: " + estudianteModificar);

        //Eliminar estudiante id = 3
        var estudianteEliminar = new Estudiante(5);
        var eliminado = estudianteDao.eliminarEstudiante(estudianteEliminar);
        if(eliminado)
            System.out.println("Estudiante eliminado: " + estudianteEliminar);
        else
            System.out.println("Error al eliminar estudiante: " + estudianteEliminar);

        // Listar los estudiantes
        System.out.println("Listado de estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        // Buscar por ID
//        var estudiante1 = new Estudiante(1);
//        System.out.println("estudiante antes de la busqueda: " + estudiante1);
//        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
//        if(encontrado){
//            System.out.println("estudiante encontrado: " + estudiante1);
//
//        }else{
//            System.out.println("No se encontro estudiante: " + estudiante1.getIdEstudiante());
//        }
    }
}
