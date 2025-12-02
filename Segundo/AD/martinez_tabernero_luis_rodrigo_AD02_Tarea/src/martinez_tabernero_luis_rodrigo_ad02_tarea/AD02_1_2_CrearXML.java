package martinez_tabernero_luis_rodrigo_ad02_tarea;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class AD02_1_2_CrearXML {
  public static void main(String args[]) throws IOException{
      
   // Usamos ruta relativa
   File fichero = new File("EMPLEADOS.DAT");   
   RandomAccessFile file = new RandomAccessFile(fichero, "r");
   
   int codigo; 
   int pos = 0; // Nos posicionamos al principio del fichero aleatorio        
   float salario, comision;
   
   // Arrays auxiliares para leer los caracteres de los Strings
   char nombre[] = new char[20], auxNombre;
   char direccion[] = new char[30], auxDireccion;
     
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  
   try{
     DocumentBuilder builder = factory.newDocumentBuilder();
     DOMImplementation implementation = builder.getDOMImplementation();
     
     // Creamos el nodo raíz del XML: <Empleados>
     Document document = (Document) implementation.createDocument(null, "Empleados", null);
     document.setXmlVersion("1.0"); 
     
        // Bucle infinito que rompemos con el break
        for(;;) {
            file.seek(pos); // Fijamos la primera posición del puntero
            
            // 1. Leer CODIGO
            codigo = file.readInt();       
            
            // 2. Leer NOMBRE (recorremos char a char)
            for (int i = 0; i < nombre.length; i++) {
                auxNombre = file.readChar(); 
                nombre[i] = auxNombre;        
            }
            String nombreS = new String(nombre); // Convertimos array a String
            
            // 3. Leer DIRECCION (recorremos char a char)
            for (int i = 0; i < direccion.length; i++) {
                auxDireccion = file.readChar(); 
                direccion[i] = auxDireccion;        
            }
            String direccionS = new String(direccion); // Convertimos array a String
            
            // 4. Leer SALARIO y COMISION
            salario = file.readFloat();  
            comision = file.readFloat();      
            
            // Si el código es válido, creamos el nodo en el XML
            if(codigo > 0) { 
                Element raiz = document.createElement("empleado"); // Nodo individual
                document.getDocumentElement().appendChild(raiz); // Lo pegamos a la raíz grande                       
                
                // Usamos la función auxiliar para llenar los datos
                CrearElemento("codigo", Integer.toString(codigo), raiz, document);
                CrearElemento("nombre", nombreS.trim(), raiz, document); // .trim() quita los espacios sobrantes
                CrearElemento("direccion", direccionS.trim(), raiz, document); 
                CrearElemento("salario", Float.toString(salario), raiz, document); 
                CrearElemento("comision", Float.toString(comision), raiz, document); 
            }   
            
            // ¡IMPORTANTE! Avanzamos el puntero 112 bytes (tamaño total del registro)
            pos = pos + 112;         
            
            // Si el puntero llega al final del archivo, salimos del bucle
            if (file.getFilePointer() == file.length()) break; 
            
     } // Fin del for
     
     // Crear el archivo XML físico
     Source source = new DOMSource(document);
     Result result = new StreamResult(new java.io.File("EMPLEADOS.XML"));      
     Transformer transformer = TransformerFactory.newInstance().newTransformer();
     transformer.transform(source, result);
     
     // Mostrar también por consola 
     Result console= new StreamResult(System.out);
     transformer.transform(source, console);
     
        }catch(ParserConfigurationException | DOMException | IOException | TransformerException e){System.err.println("Error: "+e);}
   
    file.close();  // Cierre del fichero aleatorio    
 } 
   
 // Método auxiliar para insertar los datos(como el que hay en el ejemplo ficheros)
 static void CrearElemento(String datoEmple, String valor, Element raiz, Document document){
       Element elem = document.createElement(datoEmple); 
       Text text = document.createTextNode(valor); 
       raiz.appendChild(elem); 
       elem.appendChild(text);        
 }  
}