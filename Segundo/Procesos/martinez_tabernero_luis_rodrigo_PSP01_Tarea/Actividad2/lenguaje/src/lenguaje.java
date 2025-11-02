import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * Genera palabras aleatorias y las guarda en un fichero.
 * Cada palabra se escribe en una línea independiente.
 * 
 * Uso: java lenguaje 40 palabras.txt
 * 
 * @author Rodrigo Martínez Tabernero
 * @version 1.0
 */
public class lenguaje {
    
    // Configuración de las palabras generadas
    private static final int LONGITUD_MIN = 3;
    private static final int LONGITUD_MAX = 10;
    
    /**
     * Genera una palabra aleatoria con letras minúsculas
     * @param random generador de números aleatorios
     * @return la palabra generada
     */
    private static String generarPalabra(Random random) {
        int longitud = random.nextInt(LONGITUD_MAX - LONGITUD_MIN + 1) + LONGITUD_MIN;
        StringBuilder palabra = new StringBuilder();
        
        for (int i = 0; i < longitud; i++) {
            char letra = (char) ('a' + random.nextInt(26));
            palabra.append(letra);
        }
        
        return palabra.toString();
    }
    
    /**
     * Punto de entrada del programa
     * @param args [0] número de palabras, [1] nombre del fichero
     */
    public static void main(String[] args) {
        // Comprobar que tenemos los argumentos necesarios
        if (args.length < 2) {
            System.err.println("Uso: java lenguaje <numPalabras> <nombreFichero>");
            System.err.println("Ejemplo: java lenguaje 40 miFicheroDeLenguaje.txt");
            System.exit(1);
        }
        
        int numPalabras = Integer.parseInt(args[0]);
        String nombreFichero = args[1];
        Random random = new Random();
        
        // Generar cada palabra y escribirla en el fichero
        for (int i = 0; i < numPalabras; i++) {
            String palabra = generarPalabra(random);
            
            RandomAccessFile raf = null;
            FileLock bloqueo = null;
            
            try {
                File archivo = new File(nombreFichero);
                raf = new RandomAccessFile(archivo, "rw");
                
                // SECCIÓN CRÍTICA: bloqueo para acceso concurrente seguro
                bloqueo = raf.getChannel().lock();
                
                // Ir al final del fichero para añadir la nueva palabra
                raf.seek(raf.length());
                
                // Escribir la palabra y un salto de línea
                raf.writeBytes(palabra + "\n");
                
                // Liberar el bloqueo
                bloqueo.release();
                bloqueo = null;
                
            } catch (IOException e) {
                System.err.println("Error al escribir: " + e.getMessage());
            } finally {
                // Cerrar recursos
                try {
                    if (bloqueo != null) bloqueo.release();
                    if (raf != null) raf.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar: " + e.getMessage());
                }
            }
            
            // Pausa breve entre escrituras
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("Completado: " + numPalabras + " palabras en " + nombreFichero);
    }
}