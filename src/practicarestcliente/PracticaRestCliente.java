    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicarestcliente;

import clases.Agenda;
import clases.Persona;
import java.util.Scanner;
import metodos.ImportarExportar;
import servicios.AgendaServicio;
import servicios.PersonaServicio;
import servicios.ValidarAgendaServicio;
import servicios.ValidarPersonaServicio;
import servicios.VerPersonaServicio;

/**
 *
 * @author y9d1ru
 */
public class PracticaRestCliente {

    static Agenda a;
    static ImportarExportar guardador;
    static Scanner scanner;

    public static void main(String[] args) {
        a = new Agenda();
        guardador = new ImportarExportar("Agenda.xml");
        scanner = new Scanner(System.in);
        crearMenu();
    }
    
    
    public static void crearMenu() {
        System.out.println("1. Añadir contacto");
        System.out.println("2. Mostrar contacto");
        System.out.println("3. Mostrar agenda");
        System.out.println("4. Validar agenda");
        System.out.println("5. Validar persona");
        System.out.println("6. Salir");
        int opcion = scanner.nextInt();
        comprobar(opcion);
}
    
    public static void comprobar(int opcion) {
        switch (opcion) {
            case 1:
                crearContacto();
                break;
            case 2:
                verPersona();
                break;
            case 3:
                verAgenda();
                break;
            case 4:
                validarAgenda();
                break;
            case 5:
                validarPersona();
                break;
            case 6:
                System.exit(0);
        }
    }

    
    public static void guardar(){
        guardador.guardarAgenda(a);
        crearMenu();
    }
    
    
    public static void verAgenda() {
        AgendaServicio agendaServicio = new AgendaServicio();
        Agenda a = agendaServicio.getXml(Agenda.class);
        for (Persona p : a.getPersonas()) {
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Email: " + p.getEmail());
            System.out.println("--------------");
        }
        crearMenu();
    }
    
    public static void validarAgenda(){
        ValidarAgendaServicio validarAgenda = new ValidarAgendaServicio();
        AgendaServicio agendaServicio = new AgendaServicio();
        Agenda a = agendaServicio.getXml(Agenda.class);
        System.out.println(validarAgenda.postXML(a));
        crearMenu();
    }
     
    public static void crearContacto() {
        PersonaServicio personaServicio = new PersonaServicio();
        System.out.println("Introduzca el nombre:");
        String nombre = scanner.next();
        String restriccionEmail = "[_\\-a-zA-z0-9\\.\\+]+@[a-zA-z0-9](\\.?[\\-a-zA-z0-9]*[a-zA-z0-9])*";

        System.out.println("Introduzaca el numero de telefono:");
        String telefono = scanner.next();
        System.out.println("Introduzca el email:");
        String email = scanner.next();
        while(!email.matches(restriccionEmail) && email.length()<255){
            System.out.println("Por favor, introduzca un email válido");
            email = scanner.next();
        }
        Persona p = new Persona(nombre, telefono, email);
        personaServicio.postAgenda(p);
        //a.anadirPersona(p);
        crearMenu();
}
    public static void validarPersona(){
        PersonaServicio personaServicio = new PersonaServicio();
        System.out.println("Introduce el nombre de la persona a validar:");
        String nombre = scanner.next();
        Persona p = personaServicio.getXml(Persona.class, nombre);
        ValidarPersonaServicio validarPersona = new ValidarPersonaServicio();
        String respuesta = validarPersona.postXML(p);
        if(p!= null){
        if(respuesta.equals("true")){
            System.out.println("Esta persona es válida");
        }else{
            System.out.println("-----------------------");
            System.out.println("La persona no es válida");
            System.out.println("-----------------------");
        }
        }
        crearMenu();
    }
    
    public static void verPersona() {
        VerPersonaServicio verPersonaServicio = new VerPersonaServicio();
        System.out.println("Introduce el nombre: ");
        String nombre = scanner.next();
        Persona p = verPersonaServicio.getXml(Persona.class, nombre);
        if (p != null) {
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Email: " + p.getEmail());
        }
        crearMenu();
    }
}
