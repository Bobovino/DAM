import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Aplicación que ordena un conjunto indeterminado de números
 * recibidos a través de la entrada estándar y muestra el resultado
 * ordenado en la salida estándar.
 * 
 * @author Rodrigo
 * @version 1.0
 */
public class ordenarNumeros {
    
    /**
     * Método principal que lee números de la entrada estándar,
     * los ordena y los muestra por la salida estándar.
     * 
     */
    public static void main(String[] args) {
        // Lista para almacenar los números leídos
        ArrayList<Integer> numeros = new ArrayList<>();
        
        System.out.println("Introduce números para que sean ordenados: (cualquier otro carácter para finalizar)");
        
        // Scanner para leer de la entrada estándar
        Scanner scanner = new Scanner(System.in);
        
        // Leer todos los números disponibles en la entrada estándar
        while (scanner.hasNextInt()) {
            int numero = scanner.nextInt();
            numeros.add(numero);
        }
        
        // Cerrar el scanner
        scanner.close();
        
        // Ordenar los números de menor a mayor
        Collections.sort(numeros);
        
        // Mostrar los números ordenados en la salida estándar
        System.out.println("Números ordenados:");
        for (Integer numero : numeros) {
            System.out.println(numero);
        }
    }
}