package martinez_tabernero_luis_rodrigo_ad02_tarea;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AD02_2_1_LeerLibrosDOM {

    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Usamos ruta relativa "libros.xml" 
            Document document = builder.parse(new File("libros.xml"));
            document.getDocumentElement().normalize();

            // Obtenemos la lista de todos los libros
            NodeList libros = document.getElementsByTagName("libro");

            // Recorremos la lista
            for(int i=0; i<libros.getLength(); i++) {
                Node nodo = libros.item(i); // Obtener un nodo libro

                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo; 

                    // 1. Obtener el AÑO (He cambiado el código porque en ficheros, el ejemplo era una etiqueta hija)
                    String anio = elemento.getAttribute("año");
                    System.out.println("Año: " + anio);
                    
                    // 2. Obtener Título, Editorial y Precio usando la función auxiliar
                    System.out.println("Título: " + getNodo("titulo", elemento));
                    System.out.println("Editorial: " + getNodo("editorial", elemento));
                    System.out.println("Precio: " + getNodo("precio", elemento));
                    
                    // 3. Obtener AUTORES
                    // Como puede haber varios autores, sacamos una sub-lista de autores dentro de ESTE libro
                    NodeList listaAutores = elemento.getElementsByTagName("autor");
                    
                    for (int j = 0; j < listaAutores.getLength(); j++) {
                        Element autor = (Element) listaAutores.item(j);
                        // Sacamos nombre y apellido de cada autor
                        String nombre = getNodo("nombre", autor);
                        String apellido = getNodo("apellido", autor);
                        
                        System.out.println("Autor: " + nombre + " " + apellido);
                    }
                    
                    System.out.println(); // Salto de línea entre libros 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para encontrar la información de un nodo
    private static String getNodo(String etiqueta, Element elemen) {
        // Accedemos al primer elemento con esa etiqueta y sacamos su hijo (el texto)
        NodeList nodo = elemen.getElementsByTagName(etiqueta).item(0).getChildNodes();
        Node valornodo = (Node) nodo.item(0);
        return valornodo.getNodeValue(); 
    }
}