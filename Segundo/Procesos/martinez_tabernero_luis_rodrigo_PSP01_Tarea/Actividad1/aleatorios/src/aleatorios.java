import java.util.Random;

/**
 * Aplicación que genera números aleatorios entre 0 y 100
 * y los escribe en la salida estándar.
 * 
 * @author Rodrigo
 * @version 1.0
 */
public class aleatorios {
    
    //Número de valores aleatorios a generar(40).
    private static final int CANTIDAD_NUMEROS = 40;
    
    /**
     * Valor mínimo del rango de números aleatorios (inclusivo).
     */
    private static final int VALOR_MINIMO = 0;
    
    /**
     * Valor máximo del rango de números aleatorios (inclusivo).
     */
    private static final int VALOR_MAXIMO = 100;
    
    /**
     * Método principal que genera números aleatorios y los
     * escribe en la salida estándar.
     *
     */
    public static void main(String[] args) {
        // Generador de números aleatorios
        Random random = new Random();
        
        // Generar y mostrar los números aleatorios
        for (int i = 0; i < CANTIDAD_NUMEROS; i++) {
            // Generar un número aleatorio en el rango [VALOR_MINIMO, VALOR_MAXIMO]
            int numeroAleatorio = random.nextInt(VALOR_MAXIMO - VALOR_MINIMO + 1) + VALOR_MINIMO;
            
            // Escribir el número en la salida estándar
            System.out.println(numeroAleatorio);
        }
    }
}