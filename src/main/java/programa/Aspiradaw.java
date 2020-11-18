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
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author raquel
 */
public class Aspiradaw {

    /* Variables a utilizar por los métodos */
    static final String USUARIO = "raquel";
    static final String PASSWORD = "aspiradaw";
    /* Para que el programa se repita siempre a no ser que
    salgamos del programa */
    static boolean repetir = true;
    static final double MINIMO_BATERIA = 0.3;
    /* Para guardar la posición del robot
    Por defecto va a estar en la base de carga */
    static String localizacionAspiradora = "Base de carga";

    /* ArrayList donde guardar las habitaciones que el usuario indica que quiere limpiar
    en el modo dependencias */
    static ArrayList<String> habitacionesIndicadas;

    /* Método que solicita un usuario y contraseña hasta que se introduzcan correctamente */
    public static void autentificacion() {

        /* Variables donde se almacenan los datos de user y pass introducidos por el usuario */
        String user = JOptionPane.showInputDialog("Introduce tu usuario:");
        String pass = JOptionPane.showInputDialog("Introduce tu contraseña:");

        /* Si la primera vez que se introducen los datos éstos son erróneos sale una 
        ventana de error diciendo que no son válidos. Se vuelven a solicitar los datos */
        while (!user.equals(USUARIO) || !pass.equals(PASSWORD)) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña no válidos");
            user = JOptionPane.showInputDialog("Introduce tu usuario:");
            pass = JOptionPane.showInputDialog("Introduce tu contraseña:");
        }

    }

    /* Método que pregunta si deseas salir del programa */
    public static boolean menuSalir() {

        /* Se pregunta al usuario si desea salir (opciones: si o no)
        Devuelve como resultado 0 o 1 dependiendo del yes o no */
        int opcion = JOptionPane.showConfirmDialog(null, "¿Salir del programa?", "Salida del programa", JOptionPane.YES_NO_OPTION);

        /* Si marcamos SI */
        if (opcion == JOptionPane.YES_OPTION) {
            /* repetir es false --> se para la ejecución del programa */
            repetir = false;

        }

        return repetir;
    }

    /* Método que inicializa la estructura de la casa por defecto */
    public static ArrayList<String> casaPredeterminada() {

        /* ArrayList al que se van a añadir uno a uno los elementos de la casa predeterminada */
        ArrayList<String> casaPredeterminada = new ArrayList<>();

        casaPredeterminada.add("Cocina");
        casaPredeterminada.add("Salón");
        casaPredeterminada.add("Baño");
        casaPredeterminada.add("Dormitorio 1");
        casaPredeterminada.add("Dormitorio 2");

        /* Devuelve un ArrayList con los nombres de las dependencias de la casa */
        return casaPredeterminada;
    }

    /* Método para configurar una casa personalizada */
    public static ArrayList<String> configuraCasa() {

        /* ArrayList donde se va a guardar la configuración de la casa personalizada */
        ArrayList<String> casaPersonalizada = new ArrayList<>();

        /* Se pregunta al usuario el número de dependencias por cada tipo */
        int numCocinas = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantas cocinas tiene tu casa?"));
        int numDormitorios = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos dormitorios tiene tu casa?"));
        int numBanos = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos baños tiene tu casa?"));
        int numSalones = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos salones tiene tu casa?"));

        /* Con el .add vamos añadiendo a la lista el tipo de estancia que se está preguntando seguido
        de un índice para distinguir entre dependencias del mismo tipo
        (Si tuvieramos varios dormitorios por ejemplo se distinguirían como: Dormitorio 1, Dormitorio 2, etc)*/
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

        /* Devuelve un ArrayList con los nombres de las dependencias de la casa personalizada */
        return casaPersonalizada;
    }

    /* Método que solicita los metros cuadrados de una dependencia que
    recibe como parámetro */
    public static double pedirMetrosDependencia(String dependencia) {

        /* Variable donde guardar el dato que introduzca el usuario */
        double metrosCuadradosDependencia;

        /* Se controla que el valor esté entre 1 y 100 */
        do {

            metrosCuadradosDependencia = Double.parseDouble(JOptionPane.showInputDialog("Introduce los metros cuadrados de la dependencia "
                    + dependencia + "\n"
                    + "Entre 1 y 100"));

        } while (metrosCuadradosDependencia < 1 || metrosCuadradosDependencia > 100);

        /* Se devuelve el valor de los metros cuadrados de la dependencia */
        return metrosCuadradosDependencia;
    }

    /* Método que establece el nivel de carga de batería con 
    el valor que indica el usuario */
    public static double estableceCarga() {

        /* Variable donde se almacena el valor de la carga escrito por el usuario */
        double carga;

        /* Se controla que los valores introducidos estén entre 0 y 100 */
        do {

            carga = Double.parseDouble(JOptionPane.showInputDialog("Establece el nivel de carga del robot aspiradora\n"
                    + "(Valores válidos: 0% - 100%)"));

        } while (carga < 0 || carga > 100);

        /* Se devuelve el valor de la carga establecido */
        return carga;
    }

    /* Método que realiza el modo completo
    Recibe por parámetros: nivel de carga, array de dependencias,
    array de m2 y % que pierde la bateria
    Sirve tanto para ASPIRACION como para FREGADO */
    public static double modoCompleto(double carga, Double[] metrosCuad, String[] dependencias, double porcentaje) {

        /* Este método va a limpiar el piso entero en función de la batería.
        
        Lista donde ir guardando las habitaciones que limpia el robot */
        ArrayList<String> habitacionesAspiradas = new ArrayList<>();

        /* Variable donde se guarda la bateria que se gasta en este modo */
        double bateriaSeGastaCompleto;

        /* Se recorre la lista de las dependencias de la casa para ir limpiando en orden */
        for (int i = 0; i < dependencias.length; i++) {

            /* La bateria que se gasta por habitacion es: los m2 de
            la habitacion por el porcentaje de gasto */
            bateriaSeGastaCompleto = metrosCuad[i] * porcentaje;

            /* Antes de empezar a limpiar la habitacion comprobamos que tenemos la 
            bateria suficiente como para acabar de limpiarla (que sea > 3%) */
            if ((carga - bateriaSeGastaCompleto) > MINIMO_BATERIA) {

                /* Una vez comprobado lo anterior se actualiza la bateria */
                carga = carga - bateriaSeGastaCompleto;

                /* Van saliendo ventanas informando de la dependencia que se está limpiando en el momento */
                JOptionPane.showMessageDialog(null, "Limpiando..." + dependencias[i]);

                /* Se van añadiendo habitaciones a la lista de las habitaciones aspiradas */
                habitacionesAspiradas.add(dependencias[i]);

                /* La localización de la batería va cambiando según la habitación en la que esté */
                localizacionAspiradora = dependencias[i];

            } else {

                /* Si no hay suficiente batería para seguir limpiando */
                JOptionPane.showMessageDialog(null, "No hay batería suficiente para seguir limpiando\n"
                        + "Se han podido limpiar las siguientes habitaciones\n"
                        + Arrays.toString(habitacionesAspiradas.toArray()));

                /* La aspiradora entra en la habitación que no puede limpiar y se queda ahí parada */
                localizacionAspiradora = dependencias[i];
                break;
            }
        }

        /* Devuelve el valor actualizado de la carga después de finalizar de limpiar */
        return carga;

    }
    
    /* Metodo que muestra una ventana de seleccion multiple
    La voy a usar en el modo dependencias */
    public static void menuSeleccionMultiple(String[] estructuraCasa) {
        
        /* JList muestra un cjto de objetos y permite al usuario seleccionar
        uno o mas */
        JList<String> jlist = new JList<>(estructuraCasa);
        JOptionPane.showMessageDialog(null, "A continuación se muestra una lista con las dependencias de la casa\n"
                + "Elija una o más (CTRL + clic para selección múltiple)");
        JOptionPane.showMessageDialog(null, jlist, "Elige las dependencias a limpiar...", JOptionPane.PLAIN_MESSAGE); //plain_message no muestra icono
        /* getSelectedIndices devuelve los indices seleccionados por el usuario y los almacenamos en un array de valores */
        int[] valores = jlist.getSelectedIndices();
        for (int i = 0; i < valores.length; i++) {
            JOptionPane.showMessageDialog(null, estructuraCasa[valores[i]]);
        }
        
    }

    public static double modoDependencias(double carga, Double[] metrosCuad, String[] dependencias, double porcentaje) {

        ArrayList<String> habitacionesAspiradas = new ArrayList<>();

        //Switch con una serie de opciones para seleccionar las habitaciones a limpiar
        int opcionesALimpiar;

        JOptionPane.showMessageDialog(null, Arrays.toString(dependencias));

        return carga;
    }

    /* Método que devuelve el robot a su base de carga y carga su bateria hasta el 100% */
    public static double baseDeCarga() {

        /* Variable donde guardar la carga */
        double carga;

        /* Sale una ventana que indica que se está cargando la batería */
        JOptionPane.showMessageDialog(null, "Cargando batería...");

        /* Se asigna el valor 100 a la carga para cargar la bateria al completo */
        carga = 100;

        /* Ventana que informa de que la batería se ha cargado por completo */
        JOptionPane.showMessageDialog(null, "¡Carga completada! Nivel de carga: "
                + carga + " %");

        /* Se devuelve el valor actualizado de la carga */
        return carga;

    }

    /* Metodo que devuelve la decha actual */
    public static String devuelveFecha() {

        /* LocalDate representa una fecha */
        LocalDate fecha = LocalDate.now(); // .now() para obtener la fecha actual
        /* Se le da formato a esa fecha */
        DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaActual = fecha.format(fechaFormat);

        /* Se devuelve la fecha como un String */
        return fechaActual;

    }

    /* Metodo que devuelve la hora actual */
    public static String devuelveHora() {

        /* LocalTime representa  la hora */
        LocalTime hora = LocalTime.now(); // .now() para obtener la hora actual
        /* Se le da formato a la hora */
        DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaActual = hora.format(horaFormat);

        /* Se devuelve la hora como un String */
        return horaActual;
    }

    /* Metodo que suma los elementos de un ¿¿array/objetos Double?? que se pasa como parámetro */
    public static double sumarElementosArray(Double[] array) {

        /* Para guardar el resultado de la suma */
        double suma = 0;

        for (Double array1 : array) {
            suma += array1;
        }

        return suma;
    }

    public static void main(String[] args) {

        /* Declaracion de variables a usar en main */
        final double PORCENTAJE_PIERDE_ASPIRANDO = 0.15;
        final double PORCENTAJE_PIERDE_FREGANDO = 0.225;
        double nivelCarga = 0; // Nivel de la bateria
        Double[] metrosCuadDependencias = new Double[0]; // Valores de m2 de cada habitacion (mismas posiciones que la lista de dependencias)
        String[] dependenciasCasa = new String[0]; // Dependencias de la casa
        double metrosTotalesCasa = 0; // m2 cuadrados totales de la casa


        /* Lo primero que debe hacer el programa es el login con usuario y contraseña */
        autentificacion();

        do {

            /* El menú principal sólo permite introducir valores entre 1 y 7 */
            int opcionesMenu;

            do {

                opcionesMenu = Integer.parseInt(JOptionPane.showInputDialog("MENÚ PRINCIPAL\n"
                        + "Marca la opción deseada\n"
                        + "1. CONFIGURAR EL SISTEMA\n"
                        + "2. CARGA\n"
                        + "3. ASPIRACION\n"
                        + "4. ASPIRACION Y FREGADO\n"
                        + "5. ESTADO GENERAL\n"
                        + "6. BASE DE CARGA\n"
                        + "7. SALIR"));

            } while (opcionesMenu < 1 || opcionesMenu > 7);


            /* switch para el menú principal. Cada case es una opción del menú */
            switch (opcionesMenu) {

                /* OPCION 1: Configuración de la casa */
                case 1:

                    /* Se inicializa la lista con la distribución predeterminada */
                    ArrayList<String> estructuraCasa = casaPredeterminada();

                    /* Se pregunta al usuario si desea configurar la casa o si
                    prefiere quedarse con la distribución predeterminada.
                    Nos aseguramos de que los valores son solo 1 o 2 */
                    int respuestaPersonalizacion;
                    do {

                        respuestaPersonalizacion = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de la casa por defecto: \n"
                                + Arrays.toString(estructuraCasa.toArray()) + "\n¿Deseas personalizarla?\n"
                                + "1. - Sí\n"
                                + "2. - No"));

                    } while (respuestaPersonalizacion < 1 || respuestaPersonalizacion > 2);

                    /* Si el usuario elige personalizar la casa */
                    if (respuestaPersonalizacion == 1) {

                        /* Se modifica estructuraCasa con el resultado del metodo que configura la casa personalizada */
                        estructuraCasa = configuraCasa();

                        /* Una vez configurada la casa le mostramos al usuario lo que ha configurado
                        y le preguntamos si esta de acuerdo con la distribucion que ha establecido.
                        Nos aseguramos de que los valores introducidos son solo 1 o 2 */
                        int respuestaCorrecto;

                        do {

                            respuestaCorrecto = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de su casa: \n"
                                    + Arrays.toString(estructuraCasa.toArray()) + "\n¿Es correcto?\n"
                                    + "1. - Sí\n"
                                    + "2. - No"));

                        } while (respuestaCorrecto < 1 || respuestaCorrecto > 2);

                        /* Mientras el usuario marque la opción de que los datos son incorrectos se 
                        volverá a configurar la casa */
                        while (respuestaCorrecto != 1) {

                            estructuraCasa = configuraCasa();

                            do {

                                respuestaCorrecto = Integer.parseInt(JOptionPane.showInputDialog("Esta es la estructura de su casa: \n"
                                        + Arrays.toString(estructuraCasa.toArray()) + "\n¿Es correcto?\n"
                                        + "1. - Sí\n"
                                        + "2. - No"));

                            } while (respuestaCorrecto < 1 || respuestaCorrecto > 2);

                        }

                    }

                    /* Ahora toca pedir al usuario los metros cuadrados que mide cada estancia.
                    Se debe recorrer el ArrayList y asociar un valor a cada estancia.
                    
                    ArrayList que guarda los valores de los m2 */
                    ArrayList<Double> metrosCuadradosCasa = new ArrayList<>();

                    /* Para iterar sobre un ArrayList se usa size() en vez de length()
                    El entorno lo corrige a un enhanced for loop
                    Este for recorre una a una cada habitacion (elemento) del arrayList */
                    for (String habitacion : estructuraCasa) {

                        /* Se usa el metodo pedirMetrosDependencia para cada habitacion
                        Este metodo ya tiene la limitacion de valores entre 1 y 100 */
                        double metrosCuadrados = pedirMetrosDependencia(habitacion);
                        /* Se va llenando el ArrayList de los metros cuadrados */
                        metrosCuadradosCasa.add(metrosCuadrados);
                    }

                    /* Una vez estructurada la casa y teniendo las medidas de cada habitacion
                    convierto los dos ArrayList a arrays para simplificar 
                    En el argumento del toArray es necesario pasar un array vacio
                    para forzar a ArrayList a crear un nuevo array y devolverlo
                    Este array va a ser una copia de los elementos del arraylist */
                    metrosCuadDependencias = metrosCuadradosCasa.toArray(new Double[0]);
                    dependenciasCasa = estructuraCasa.toArray(new String[0]);

                    /* Calculo de los m2 totales de la casa */
                    metrosTotalesCasa = sumarElementosArray(metrosCuadDependencias);

                    break;

                /* OPCION 2: Establece el nivel de bateria entre 0 y 100 */
                case 2:
                    /* El metodo estableceCarga se usa para actualizar el valor del nivel de la
                    bateria al que indique el usuario */
                    nivelCarga = estableceCarga();
                    JOptionPane.showMessageDialog(null, "Nivel de carga..." + nivelCarga + " %");

                    break;

                /* OPCION 3: Aspiracion */
                case 3:

                    /* En caso de que aun no se haya establecido una distribucion de la casa o un nivel de batería
                    saltará un mensaje de advertencia que pida al usuario que introduzca esos datos para poder aspirar */
                    if (dependenciasCasa.length != 0 && nivelCarga != 0) {

                        /* Hay dos modos de aspiracion */
                        int modoAspiracion;

                        do {

                            modoAspiracion = Integer.parseInt(JOptionPane.showInputDialog("Selecciona el modo de aspiración:\n"
                                    + "1. - Modo completo\n"
                                    + "2. - Modo dependencias"));

                        } while (modoAspiracion < 1 || modoAspiracion > 2);

                        /* Switch para seleccionar entre los dos modos */
                        switch (modoAspiracion) {

                            case 1:

                                /* Este modo limpia el piso entero en funcion de la bateria */
                                nivelCarga = modoCompleto(nivelCarga, metrosCuadDependencias, dependenciasCasa, PORCENTAJE_PIERDE_ASPIRANDO);

                                break;
                            case 2:
                                //nivelCarga = modoDependencias(nivelCarga, metrosCuadDependencias, dependenciasCasa, PORCENTAJE_PIERDE_ASPIRANDO);
                                menuSeleccionMultiple(dependenciasCasa);
                                break;

                        }
                        /* Advertencias por si el usuario aun no ha pulsado las opciones 1 y 2 */
                    } else if (dependenciasCasa.length == 0) {
                        JOptionPane.showMessageDialog(null, "¡Advertencia! Su domicilio aún no está configurado\n"
                                + "Por favor, pulse 1 para configurar las dependencias de su domicilio");
                    } else if (nivelCarga == 0) {
                        JOptionPane.showMessageDialog(null, "¡Advertencia! El estado de la batería no se ha configurado\n"
                                + "Por favor, pulse 2 para configurar el nivel de batería");
                    }

                    break;

                /* OPCION 4: ASPIRACION Y FREGADO */
                case 4:

                    /* En caso de que aun no se haya establecido una distribucion de la casa o un nivel de batería
                    saltará un mensaje de advertencia que pida al usuario que introduzca esos datos para poder aspirar */
                    if (dependenciasCasa.length != 0 && nivelCarga != 0) {

                        int modoFregado;

                        do {

                            modoFregado = Integer.parseInt(JOptionPane.showInputDialog("Selecciona el modo de aspiración y fregado:\n"
                                    + "1. - Modo completo\n"
                                    + "2. - Modo dependencias"));

                        } while (modoFregado < 1 || modoFregado > 2);

                        /* Switch para seleccionar entre los dos modos */
                        switch (modoFregado) {

                            case 1:

                                /* Este modo limpia el piso entero en funcion de la bateria */
                                nivelCarga = modoCompleto(nivelCarga, metrosCuadDependencias, dependenciasCasa, PORCENTAJE_PIERDE_FREGANDO);

                                break;
                            case 2:

                                break;

                        }
                    } else if (dependenciasCasa.length == 0) {
                        JOptionPane.showMessageDialog(null, "¡Advertencia! Su domicilio aún no está configurado\n"
                                + "Por favor, pulse 1 para configurar las dependencias de su domicilio");
                    } else if (nivelCarga == 0) {
                        JOptionPane.showMessageDialog(null, "¡Advertencia! El estado de la batería no se ha configurado\n"
                                + "Por favor, pulse 2 para configurar el nivel de batería");
                    }

                    break;

                /* Mostrar el estado general */
                case 5:
                    /* Se muestran:
                    - Fecha y hora actuales
                    - Nivel de bateria
                    - Lugar donde está parado el robot
                    - Dependencias y m2 */

                    String fechaAhora = devuelveFecha();
                    String horaAhora = devuelveHora();
                    JOptionPane.showMessageDialog(null, "ESTADO GENERAL:\n"
                            + "Fecha actual: " + fechaAhora + "\nHora actual: " + horaAhora
                            + "\nNivel de batería: " + nivelCarga + " %"
                            + "\nEl robot se encuentra en: " + localizacionAspiradora
                            + "\nDependencias de la casa: " + Arrays.toString(dependenciasCasa)
                            + "\n Metros cuadrados totales de la casa: " + metrosTotalesCasa + " m2");
                    break;

                /* Para que el robot vuelva a la base de carga */
                case 6:
                    nivelCarga = baseDeCarga();
                    localizacionAspiradora = "Base de carga"; //La localizacion del robot sera la de base de carga
                    break;

                /* Salir del programa */
                case 7:
                    menuSalir();
                    break;

            }

        } while (repetir);

    }

}
