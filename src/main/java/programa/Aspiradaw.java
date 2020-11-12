/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;

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

    // Método que solicite los metros cuadrados de una dependencia con JOption
    public static double pedirMetrosDependencia() {

        double metrosCuadradosDependencia = 0;

        //Los valores de metros no pueden ser ni inferior a 1 ni superiores a 100
        do {

            String metrosDependencia = JOptionPane.showInputDialog("Introduce los metros cuadrados de la dependencia\n"
                    + "Entre 1 y 100");
            metrosCuadradosDependencia = Double.parseDouble(metrosDependencia);

        } while (metrosCuadradosDependencia < 1 || metrosCuadradosDependencia > 100);

        return metrosCuadradosDependencia;
    }

    // Metodo que crea un array vacio de una longitud determinada
    // Para cada estancia vamos a crear un array vacio para poder guardar
    // los metros cuadrados de cada habitacion de esa dependencia
//    public static int[] devuelveArray(int longitud) {
//
//        int[] contadorCadaDependencia = new int[longitud];
//
//        return contadorCadaDependencia;
//    }
    public static void main(String[] args) {

        //AUTENTIFICACION MEDIANTE USUARIO Y CONTRASEÑA
        autentificacion();

        do {

            // MENU PRINCIPAL debe tener:
            //CONFIGURAR EL SISTEMA
            //CARGA
            //ASPIRACION
            //ASPIRACION Y FREGADO
            //ESTADO GENERAL
            //BASE DE CARGA
            //SALIR
            // Configuración del sistema
            // Numero de dependencias de la casa por defecto
            int numCocinas = 1;
            int numSalones = 1;
            int numCuartosBano = 1;
            int numDormitorios = 2;

            // + : Configuracion num dependencias 
            JOptionPane.showMessageDialog(null, "COCINA");
            double metrosCuadradosCocina = pedirMetrosDependencia();
            JOptionPane.showMessageDialog(null, "SALÓN");
            double metrosCuadradosSalon = pedirMetrosDependencia();
            JOptionPane.showMessageDialog(null, "BAÑO");
            double metrosCuadradosBano = pedirMetrosDependencia();
            JOptionPane.showMessageDialog(null, "DORMITORIO 1");
            double metrosCuadradosDorm1 = pedirMetrosDependencia();
            JOptionPane.showMessageDialog(null, "DORMITORIO 2");
            double metrosCuadradosDorm2 = pedirMetrosDependencia();

            menuSalir();

        } while (repetir);

    }

}
