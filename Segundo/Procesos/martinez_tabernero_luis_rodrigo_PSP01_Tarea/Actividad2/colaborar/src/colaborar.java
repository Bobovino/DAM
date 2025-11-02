import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lanza múltiples instancias de lenguaje para que trabajen juntas
 * generando un fichero con palabras aleatorias.
 * 
 * Se lanzan 10 procesos que generan 10, 20, 30... hasta 100 palabras.
 * Total: 550 palabras en el fichero final.
 * 
 * @author Rodrigo Martínez Tabernero
 * @version 1.0
 */
public class colaborar {
    
    private static final int NUM_INSTANCIAS = 10;
    private static final int INCREMENTO = 10;
    private static final String FICHERO_SALIDA = "palabrasColaborativas.txt";
    
    // IMPORTANTE: ajustar esta ruta a la ubicación real del JAR
    private static final String RUTA_JAR = "/home/bobovino/Desktop/DAM/Segundo/Procesos/lenguaje/dist/lenguaje.jar";
    
    
    /**
     * Lanza los procesos y espera a que todos terminen
     * @param args no se usan
     */
    public static void main(String[] args) {
        List<Process> procesos = new ArrayList<>();
        
        try {
            // Borrar el fichero anterior si existe
            File fichero = new File(FICHERO_SALIDA);
            if (fichero.exists()) {
                fichero.delete();
                System.out.println("Fichero anterior borrado");
            }
            
            System.out.println("Lanzando " + NUM_INSTANCIAS + " procesos...\n");
            
            // Lanzar cada proceso
            for (int i = 1; i <= NUM_INSTANCIAS; i++) {
                int numPalabras = i * INCREMENTO;
                
                // Construir el comando
                String comando = "java -jar " + RUTA_JAR + " " + numPalabras + " " + FICHERO_SALIDA;
                
                // Ejecutar
                Process p = Runtime.getRuntime().exec(comando);
                procesos.add(p);
                
                System.out.println("Proceso " + i + ": " + numPalabras + " palabras");
            }
            
            System.out.println("\nEsperando a que terminen...\n");
            
            // Esperar a que cada proceso termine
            int totalPalabras = 0;
            for (int i = 0; i < procesos.size(); i++) {
                Process p = procesos.get(i);
                int codigo = p.waitFor();
                int palabras = (i + 1) * INCREMENTO;
                totalPalabras += palabras;
                
                System.out.println("Proceso " + (i + 1) + " terminado (código " + codigo + ")");
            }
            
            System.out.println("\n=== FINALIZADO ===");
            System.out.println("Total esperado: " + totalPalabras + " palabras");
            System.out.println("Fichero: " + FICHERO_SALIDA);
            System.out.println("\nVerificar con: wc -l " + FICHERO_SALIDA);
            
            // Intentar verificar automáticamente
            verificarLineas();
            
        } catch (IOException e) {
            System.err.println("Error al lanzar procesos: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido");
        }
    }
    
    /**
     * Verifica que el fichero tiene las 550 líneas esperadas
     */
    private static void verificarLineas() {
        try {
            Process p = Runtime.getRuntime().exec("wc -l " + FICHERO_SALIDA);
            p.waitFor();
            
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(p.getInputStream()));
            String resultado = reader.readLine();
            
            if (resultado != null) {
                System.out.println("\nVerificación: " + resultado);
                
                String[] partes = resultado.trim().split("\\s+");
                int lineas = Integer.parseInt(partes[0]);
                
                if (lineas == 550) {
                    System.out.println("¡CORRECTO! 550 líneas");
                } else {
                    System.out.println("Atención: se esperaban 550, hay " + lineas);
                }
            }
        } catch (Exception e) {
            System.out.println("Verificar manualmente: wc -l " + FICHERO_SALIDA);
        }
    }
}