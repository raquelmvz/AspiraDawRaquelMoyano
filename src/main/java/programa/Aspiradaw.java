/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;

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
    static final double PORCENTAJE_PIERDE_ASPIRANDO = -0.15;
    static final double PORCENTAJE_PIERDE_FREGANDO = -0.225;
    static final double MINIMO_BATERIA = 0.3;

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

        double nivelCarga;

        //Mientras los valores introducidos sean inferiores a 0 o superiores a 100 se vuelve a mostrar el mensaje por ventana
        do {

            nivelCarga = Double.parseDouble(JOptionPane.showInputDialog("Establece el nivel de carga del robot aspiradora\n"
                    + "(Valores válidos: 0% - 100%)"));

        } while (nivelCarga < 0 || nivelCarga > 100);

        return nivelCarga;
    }

    // Metodo que crea un array tipo double vacio de una longitud determinada
//    public static double[] devuelveArray(int longitud) {
//        
//        double[] array;
//        array = new double[longitud];
//        return array;
//    }
    //Metodo que cargue la bateria hasta el 100%
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
                    Double[] metrosCuadDependencias = metrosCuadradosCasa.toArray(new Double[0]);
                    String[] dependenciasCasa = estructuraCasa.toArray(new String[0]);

                    for (int i = 0; i < dependenciasCasa.length; i++) {
                        System.out.println(dependenciasCasa[i] + " " + metrosCuadDependencias[i]);
                    }

                //La opcion 2 establece el nivel de bateria entre 0% y 100%
                case 2:
                    double nivelCarga = estableceCarga();
                    JOptionPane.showMessageDialog(null, "Nivel de carga..." + nivelCarga + " %");

                case 3:

                case 4:

                case 5:

                case 6:

                case 7:

            }

            // Configuración del sistema
            //Recorro el array de la estructura de la casa y para cada una de las dependencias pido los metros
//            for (int i = 0; i < estructuraCasa.length; i++) {
//
//                String dependencia = estructuraCasa[i];
//                metrosCuadradosDependencias[i] = pedirMetrosDependencia(dependencia);
//
//            }
            // + : Configuracion num dependencias 
//            JOptionPane.showMessageDialog(null, "COCINA");
//            double metrosCuadradosCocina = pedirMetrosDependencia();
//            JOptionPane.showMessageDialog(null, "SALÓN");
//            double metrosCuadradosSalon = pedirMetrosDependencia();
//            JOptionPane.showMessageDialog(null, "BAÑO");
//            double metrosCuadradosBano = pedirMetrosDependencia();
//            JOptionPane.showMessageDialog(null, "DORMITORIO 1");
//            double metrosCuadradosDorm1 = pedirMetrosDependencia();
//            JOptionPane.showMessageDialog(null, "DORMITORIO 2");
//            double metrosCuadradosDorm2 = pedirMetrosDependencia();
            menuSalir();

        } while (repetir);

    }

}
