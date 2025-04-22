package nc.presentacion;

import nc.datos.EstudianteDAO;
import nc.dominio.Estudiante;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);
        //Se crea instancia de clase de servicio EstudianteDao
        var estudianteDAO = new EstudianteDAO();
        while(!salir){
            try {
                mostraMenu();
                //salir = ejecutarOpciones(consola, estudianteDao);
            }catch(Exception e){
                System.out.println("Ocurrio un error al ejecutar operacion: " e.getMessage());
            }
            System.out.println();
        }

    }

    private static void mostrarMenu(){
        System.out.println("""
                *** Sistema de estudiantes ***
                1. Listar estudiantes
                2. Buscar estudiante
                3. Agregar Estudiante
                4. Modificar estudiante
                5. Eliminar estudiante
                6. Salir
                Elige una opcion:
                """);
    }

    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch(opcion){
            case 1 -> { //Listar estudiantes
                System.out.println("Listado de estudiantes..");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
            }

            case 2 -> {
                System.out.println("Introduce el Id a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                if(encontrado)
                    System.out.println("Estudiante encontrado: " + estudiante);
                else
                    System.out.println("Estudiante no encontrado" + estudiante);
            }

            case 3 -> {
                System.out.println("Agregar estudiante: ");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                // crear obj estudiante sin el id
                var estudiante = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);
                if(agregado)
                    System.out.println("Estudiante agregado: " + estudiante);
                else
                    System.out.println("Estudiante no agregado: " + estudiante);
            }

            case 4 -> {
                System.out.println("Modificar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //Crear obj estudiante a modificar
                var estudiante = new Estudiante(idEstudiante, nombre, apellido, telefono, email);
                var modificado = estudianteDAO.modificarEstudiante(estudiante);
                if(modificado) System.out.println("estudiante modificado: " + estudiante);
                else System.out.println("estudiante no midificado: " + estudiante);
            }

            case 5 -> {
                System.out.println("Eliminar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var eliminado = estudianteDAO.eliminarEstudiante(estudiante);
                if(eliminado){
                    System.out.println("Estudiante eliminado: " + estudiante);
                }else{
                    System.out.println("Estudiante no eliminado: " + estudiante);
                }
            }

            case 6 -> {
                System.out.println("Hasta pronto...");
                salir = true;
            }

            default -> {
                System.out.println("opcion no reconocida");
            }
        }
        return salir;
    }
}