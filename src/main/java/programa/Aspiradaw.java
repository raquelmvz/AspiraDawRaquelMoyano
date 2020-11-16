/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author raquel
 */
public class Aspiradaw {

    //Declaracion de todas las variables a utilizar a lo largo del programa
    //El usuario y la contraseña se guardan como final
    static final String USUARIO = "raquel";
    static final String PASSWORD = "aspiradaw";
    static boolean repetir = true; // Para que el programa se repita siempre a no ser que salgamos del programa 
    static final double PORCENTAJE_PIERDE_ASPIRANDO = 0.15;
    static final double PORCENTAJE_PIERDE_FREGANDO = 0.225;
    static final double MINIMO_BATERIA = 0.3;
    //En esta variable se va a guardar el nivel de la bateria
    static double nivelCarga;
    //metrosCuadDependencias va a guardar los valores de los m^2 para cada habitacion
    //(Cada posicion corresponde con la misma posicion de habitaciones)
    static Double[] metrosCuadDependencias;
    //dependenciasCasa va a guardar la lista de dependencias de la casa
    static String[] dependenciasCasa;
    //metros cuadrados de la casa en total
    static double metrosTotalesCasa;
    //Va a guardar la posicion del robot (Por defecto la vamos a poner en la base de carga)
    static String localizacionAspiradora = "Base de carga";
    //Variable donde guardar el gasto de bateria durante la limpieza
    static double bateriaSeGasta;
    //ArrayList donde ir guardando las habitaciones que se van aspirando durante el proceso
    static ArrayList<String> habitacionesAspiradas = new ArrayList<>();

    //Metodo que solicita un usuario y contraseña hasta que se introduce correctamente
    public static void autentificacion() {

        String user = JOptionPane.showInputDialog("Introduce tu usuario:");
        String pass = JOptionPane.showInputDialog("Introduce tu contraseña:");

        //Si la primera vez que se introducen los datos éstos son erróneos sale una ventana
        //de error diciendo que no son válidos
        //Se vuelve a solicitar hasta que sean correctos
        while (!user.equals(USUARIO) || !pass.equals(PASSWORD)) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña no válidos");
            user = JOptionPane.showInputDialog("Introduce tu usuario:");
            pass = JOptionPane.showInputDialog("Introduce tu contraseña:");
        }

    }

    //Metodo que pregunta si deseas salir del programa
    public static boolean menuSalir() {
        //Se pregunta si quieres salir
        //Devuelve 0 o 1 dependiendo del YES o NO
        int opcion = JOptionPane.showConfirmDialog(null, "¿Salir del programa?", "Salida del programa", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            //Si marcamos YES
            repetir = false; // Si repetir es false paramos la ejecucion del programa

        }

        return repetir;
    }

    //Metodo que inicializa la estructura de la casa por defecto
    public static ArrayList<String> casaPredeterminada() {

        ArrayList<String> casaPredeterminada = new ArrayList<>();

        //Inicializo el Array List con los valores que tendrá la casa por defecto
        casaPredeterminada.add("Cocina");
        casaPredeterminada.add("Salón");
        casaPredeterminada.add("Baño");
        casaPredeterminada.add("Dormitorio 1");
        casaPredeterminada.add("Dormitorio 2");

        return casaPredeterminada;
    }

    // Metodo para configurar una casa personalizada
    // Se pregunta al usuario el numero de dependencias por cada tipo y se añaden a un Array List
    //El metodo devuelve un Array List con la informacion de las habitaciones de la casa
    public static ArrayList<String> configuraCasa() {

        ArrayList<String> casaPersonalizada = new ArrayList<>();

        int numCocinas = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantas cocinas tiene tu casa?"));
        int numDormitorios = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos dormitorios tiene tu casa?"));
        int numBanos = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos baños tiene tu casa?"));
        int numSalones = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos salones tiene tu casa?"));

        // Con el .add añadimos a la lista de casaPersonalizada el tipo de estancia que se está preguntado seguido de un 
        // índice para poder distinguir entre diferentes habitaciones del mismo tipo
        // Si tenemos varios dormitorios se distinguiran como: Dormitorio 1, Dormitorio 2, etc.
        for (int i = 1; i <= numCocinas; i++) {
            casaPersonalizada.add("Cocina " + i);
        }

        for (int i = 1; i <= numDormitorios; i++) {
            casaPersonalizada.add("Dormitorio " + i);
        }
        for (int i = 1; i <= numBanos; i++) {
            casaPersonalizada.add("Baño " + i);
        }

        for (int i = 1; i <= numSalones; i++) {
            casaPersonalizada.add("Salon " + i);
        }

        return casaPersonalizada;
    }

    // Método que solicite los metros cuadrados de una dependencia con JOption
    public static double pedirMetrosDependencia(String dependencia) {

        double metrosCuadradosDependencia;

        //Los valores de metros no pueden ser ni inferior a 1 ni superiores a 100
        do {

            String metrosDependencia = JOptionPane.showInputDialog("Introduce los metros cuadrados de la dependencia "
                    + dependencia + "\n"
                    + "Entre 1 y 100");
            metrosCuadradosDependencia = Double.parseDouble(metrosDependencia);

        } while (metrosCuadradosDependencia < 1 || metrosCuadradosDependencia > 100);

        return metrosCuadradosDependencia;
    }

    //Metodo que establece el nivel de carga de la bateria
    public static double estableceCarga() {

        double carga;

        //Mientras los valores introducidos sean inferiores a 0 o superiores a 100 se vuelve a mostrar el mensaje por ventana
        do {

            carga = Double.parseDouble(JOptionPane.showInputDialog("Establece el nivel de carga del robot aspiradora\n"
                    + "(Valores válidos: 0% - 100%)"));

        } while (carga < 0 || carga > 100);

        return carga;
    }

    //Metodo que realiza el modo completo
    //Necesita recibir por parametro el nivel de carga, el array de las dependencias,
    //el array de los metros cuadrados y el porcentaje que pierde de bateria para 
    //que sirva tanto para aspirado como para fregado
    public static double modoCompleto(double carga, Double[] metrosCuad, String[] dependencias, double porcentaje) {
        //Este modo va a limpiar el piso entero en funcion de la bateria
        for (int i = 0; i < dependencias.length; i++) {
            bateriaSeGasta = metrosCuad[i] * porcentaje;//la bateria baja hasta mas de 3% ARREGLAR
            
            if ((carga - bateriaSeGasta) > MINIMO_BATERIA) {
                carga = carga - bateriaSeGasta;
                JOptionPane.showMessageDialog(null, "Limpiando..." + dependencias[i]);
                habitacionesAspiradas.add(dependencias[i]);
                localizacionAspiradora = dependencias[i];

            } else {
                JOptionPane.showMessageDialog(null, "No hay batería suficiente para seguir limpiando\n"
                        + "Se han podido limpiar las siguientes habitaciones\n"
                        + Arrays.toString(habitacionesAspiradas.toArray()));
                localizacionAspiradora = dependencias[i];
                break;
            }
        }
        return carga;

    }

    //Metodo para devolver el robot a su base de carga y cargar la bateria hasta el 100%
    public static double baseDeCarga() {

        double carga;
        JOptionPane.showMessageDialog(null, "Cargando batería...");
        carga = 100;
        JOptionPane.showMessageDialog(null, "¡Carga completada! Nivel de carga: "
                + carga + " %");

        return carga;

    }

    //Metodo que devuelve la fecha actual
    public static String devuelveFecha() {

        //LocalDate representa una fecha
        LocalDate fecha = LocalDate.now(); // Para obtener la fecha actual
        //Para darle formato
        DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaActual = fecha.format(fechaFormat);

        return fechaActual;

    }

    //Metodo que devuelve la hora actual
    public static String devuelveHora() {

        //LocalTime representa tiempo
        LocalTime hora = LocalTime.now();
        DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaActual = hora.format(horaFormat);

        return horaActual;
    }

    //Metodo que suma los elementos de un array que se pasa como parámetro
    public static double sumarElementosArray(Double[] array) {

        double suma = 0;

        for (int i = 0; i < array.length; i++) {
            suma += array[i];

        }
        return suma;
    }

    // Metodo que crea un array tipo double vacio de una longitud determinada
//    public static double[] devuelveArray(int longitud) {
//        
//        double[] array;
//        array = new double[longitud];
//        return array;
//    }
    public static void main(String[] args) {

        //Autentificacion mediante usuario y contraseña al empezar el programa
        autentificacion();

        do {

            int opcionesMenu = Integer.parseInt(JOptionPane.showInputDialog("MENÚ PRINCIPAL\n"
                    + "Marca la opción deseada\n"
                    + "1. CONFIGURAR EL SISTEMA\n"
                    + "2. CARGA\n"
                    + "3. ASPIRACION\n"
                    + "4. ASPIRACION Y FREGADO\n"
                    + "5. ESTADO GENERAL\n"
                    + "6. BASE DE CARGA\n"
                    + "7. SALIR"));

            switch (opcionesMenu) {

                //Si marcamos 1 elegimos configurar la casa
                case 1:
                    //Se pregunta al usuario si desea configurar la casa o si prefiere quedarse
                    //con la distribucion predeterminada
                    ArrayList<String> estructuraCasa = casaPredeterminada();

                    int respuestaPersonalizacion = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de la casa por defecto: \n"
                            + Arrays.toString(estructuraCasa.toArray()) + "\n¿Deseas personalizarla?\n"
                            + "1. - Sí\n"
                            + "2. - No"));

                    //Si el usuario elige personalizar la casa:
                    if (respuestaPersonalizacion == 1) {
                        estructuraCasa = configuraCasa();

                        //Una vez configurada la casa le mostramos al usuario lo que ha configurado
                        //y le preguntamos si esta de acuerdo con la distribucion que ha establecido
                        int respuestaCorrecto;

                        respuestaCorrecto = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de su casa: \n"
                                + Arrays.toString(estructuraCasa.toArray()) + "\n¿Es correcto?\n"
                                + "1. - Sí\n"
                                + "2. - No"));

                        //Mientras el usuario marque la opcion de que los datos son incorrectos se volvera a 
                        //configurar la casa
                        while (respuestaCorrecto != 1) {
                            estructuraCasa = configuraCasa();

                            respuestaCorrecto = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de su casa: \n"
                                    + Arrays.toString(estructuraCasa.toArray()) + "\n¿Es correcto?\n"
                                    + "1. - Sí\n"
                                    + "2. - No"));
                        }

                    }

                    // Ahora toca pedir al usuario los metros cuadrados que mide cada estancia
                    //Se debe recorrer el ArrayList y asociar un valor a cada estancia
                    //ArrayList que guarda los valores de los m^2
                    ArrayList<Double> metrosCuadradosCasa = new ArrayList<>();

                    //Para iterar sobre un ArrayList se usa size() en vez de length()
                    //El entorno lo corrige a un enhanced for loop
                    //Este for recorre uno a uno cada habitacion (elemento) del arraylist
                    for (String habitacion : estructuraCasa) {
                        //Se usa el metodo pedirMetrosDependencia para cada habitacion
                        //Este metodo ya tiene la limitacion de valores entre 1 y 100
                        double metrosCuadrados = pedirMetrosDependencia(habitacion);
                        //Se va llenando el ArrayList de los metros cuadrados
                        metrosCuadradosCasa.add(metrosCuadrados);
                    }

                    //Una vez estructurada la casa y teniendo las medidas de cada habitacion
                    //convierto los dos ArrayList a arrays para simplificar 
                    //En el argumento del toArray es necesario pasar un array vacio
                    //para forzar a ArrayList a crear un nuevo array y devolverlo
                    //Este array va a ser una copia de los elementos del arraylist
                    metrosCuadDependencias = metrosCuadradosCasa.toArray(new Double[0]);
                    dependenciasCasa = estructuraCasa.toArray(new String[0]);

                    //Calculo de los m2 totales de la casa:
                    metrosTotalesCasa = sumarElementosArray(metrosCuadDependencias);

                    break;

                //La opcion 2 establece el nivel de bateria entre 0% y 100%
                case 2:
                    nivelCarga = estableceCarga();
                    JOptionPane.showMessageDialog(null, "Nivel de carga..." + nivelCarga + " %");

                    break;

                // Aspiracion
                //Hay dos modos
                case 3:

                    int modoAspiracion;
                    modoAspiracion = Integer.parseInt(JOptionPane.showInputDialog("Selecciona el modo de aspiración:\n"
                            + "1. - Modo completo\n"
                            + "2. - Modo dependencias"));

                    //switch para seleccionar entre los dos modos
                    switch (modoAspiracion) {

                        case 1:

                            //Este modo limpia el piso entero en funcion de la bateria
                            nivelCarga = modoCompleto(nivelCarga, metrosCuadDependencias, dependenciasCasa, PORCENTAJE_PIERDE_ASPIRANDO);
//                            
                            break;
                        case 2:

                            break;

                    }
                    break;

                case 4:

                //Mostrar estado general
                case 5:
                    //Mostrar fecha y hora actuales
                    //Nivel de bateria
                    //Lugar donde esta parado el robot
                    //Dependencias y m2

                    String fechaAhora = devuelveFecha();
                    String horaAhora = devuelveHora();
                    JOptionPane.showMessageDialog(null, "ESTADO GENERAL:\n"
                            + "Fecha actual: " + fechaAhora + "\nHora actual: " + horaAhora
                            + "\nNivel de batería: " + nivelCarga + " %"
                            + "\nEl robot se encuentra en: " + localizacionAspiradora
                            + "\nDependencias de la casa: " + Arrays.toString(dependenciasCasa)
                            + "\n Metros cuadrados totales de la casa: " + metrosTotalesCasa + " m2");
                    break;

                case 6:
                    nivelCarga = baseDeCarga();
                    break;

                case 7:
                    menuSalir();
                    break;

            }

        } while (repetir);

    }

}
