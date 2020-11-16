/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programa;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import static programa.Aspiradaw.casaPredeterminada;
import static programa.Aspiradaw.configuraCasa;
import static programa.Aspiradaw.estableceCarga;
import static programa.Aspiradaw.nivelCarga;
import static programa.Aspiradaw.pedirMetrosDependencia;

/**
 *
 * @author raque
 */
public class prueba {
    static final double PORCENTAJE_PIERDE_ASPIRANDO = 0.15;
    static final double PORCENTAJE_PIERDE_FREGANDO = 0.225;
    static final double MINIMO_BATERIA = 0.3;

    public static void main(String[] args) {

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

        int modoAspiracion;
        modoAspiracion = Integer.parseInt(JOptionPane.showInputDialog("Selecciona el modo de aspiración:\n"
                + "1. - Modo completo\n"
                + "2. - Modo dependencias"));

        nivelCarga = estableceCarga();
        JOptionPane.showMessageDialog(null, "Nivel de carga..." + nivelCarga + " %");

        double bateriaSeGasta;
        //Attay con las habitaciones limpiadas
        ArrayList<String> habAspiradas = new ArrayList<>();
        //switch para seleccionar entre los dos modos
        switch (modoAspiracion) {

            case 1:

                //Este modo limpia el piso entero en funcion de la bateria
                for (int i = 0; i < dependenciasCasa.length; i++) {

                    if (nivelCarga > MINIMO_BATERIA) {
                        JOptionPane.showMessageDialog(null, "Limpiando... " + dependenciasCasa[i]);
                        bateriaSeGasta = metrosCuadDependencias[i] * PORCENTAJE_PIERDE_ASPIRANDO;
                        nivelCarga = nivelCarga - bateriaSeGasta;
                        habAspiradas.add(dependenciasCasa[i]);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede seguir limpiando\n"
                                + "Se han limpiado las siguientes habitaciones\n"
                                + Arrays.toString(habAspiradas.toArray()));
                        break;
                        
                        
                    }
                }

                break;
            case 2:

                break;

        }
    }

}
