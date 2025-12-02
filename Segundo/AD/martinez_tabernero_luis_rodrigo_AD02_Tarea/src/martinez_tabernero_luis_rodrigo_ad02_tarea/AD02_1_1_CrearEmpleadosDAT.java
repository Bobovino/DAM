package martinez_tabernero_luis_rodrigo_ad02_tarea;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AD02_1_1_CrearEmpleadosDAT {
 public static void main(String[] args) throws IOException {      
   
   // Declaración del fichero (Usamos ruta relativa para que funcione al comprimir y enviar)
   File fichero = new File("EMPLEADOS.DAT"); 
   
   // Si existe lo borramos para empezar limpio 
   if(fichero.exists()){
       fichero.delete();
   }

   RandomAccessFile file = new RandomAccessFile(fichero, "rw");
   
   // Declaración de los arrays que contienen los datos (5 empleados)
   int codigo[] = {1, 2, 3, 4, 5};
   String nombre[] = {"Juan Garcia", "Maria Lopez", "Luis Ruiz", "Ana Martin", "Pedro Gil"};
   String direccion[] = {"Calle Luz, 10", "Calle Sol, 20", "Calle Luna, 30", "Calle Fantasía, 40", "Calle Catecúmeno, 50"};
   float salario[] = {1200.50f, 1500.00f, 1800.25f, 2100.00f, 1300.75f};
   float comision[] = {100.0f, 200.0f, 150.0f, 300.0f, 120.0f};

   StringBuffer buffer = null; // Buffer para almacenar strings (Nombre y Dirección)
   int n = codigo.length;      // Número de elementos que contiene el array
   
   System.out.println("--- Inicio escritura EMPLEADOS.DAT ---");

   for (int i=0; i<n; i++){     // Recorrido de los arrays             
     
     // 1. CODIGO 
     file.writeInt(codigo[i]);   
     
     // 2. NOMBRE 
     buffer = new StringBuffer(nombre[i]);      
     buffer.setLength(20);     // Fijo el tamaño a 20 chars
     file.writeChars(buffer.toString()); 

     // 3. DIRECCION 
     buffer = new StringBuffer(direccion[i]);      
     buffer.setLength(30);     // Fijo el tamaño a 30 chars
     file.writeChars(buffer.toString()); 

     // 4. SALARIO y COMISION 
     file.writeFloat(salario[i]);         
     file.writeFloat(comision[i]);      

     System.out.println("Escribiendo Empleado " + codigo[i] + ": " + nombre[i] + ", dirección: " + direccion[i] + ", salario " + salario[i] + "€"+ ", comisión " + comision[i] + "€");
   }     
   
   file.close();   // Cierre del fichero 
   System.out.println("--- Fin del proceso ---");
 }  
}