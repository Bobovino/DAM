package martinez_tabernero_luis_rodrigo_ad02_tarea;

import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

//Voy a hacerlo como en el ejemplo, pero me salta el warning de que:
/*
Deprecated
It is recommended to use javax.xml.parsers.SAXParserFactory instead
*/

public class AD02_2_2_LeerLibrosSAX {
    
    public static void main(String[] args) throws SAXException, IOException{
        
        // Creamos el procesador SAX
        XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
        
        // Asignamos nuestra propia clase gestora que define qué hacer con los datos
        GestionContenido gestor = new GestionContenido();
        procesadorXML.setContentHandler(gestor);
        
        // Indicamos el fichero a leer (Ruta relativa)
        InputSource fileXML = new InputSource("libros.xml");
        procesadorXML.parse(fileXML);
    }

    // Clase para manejar los eventos del XML (requiere más trabajo que DOM porque con esta técnica leemos uno a uno
    private static class GestionContenido extends DefaultHandler {

        // Variables para guardar datos temporalmente
        private String etiquetaActual = "";
        private String nombreAutor = "";
        private String apellidoAutor = "";

        public GestionContenido() {
            super();
        }

        @Override
        public void startDocument(){
        System.out.println("Listado de Libros:");
        }
        
        @Override
        public void endDocument(){
        System.out.println("Fin del listado.");
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
             
             // Guardamos la etiqueta que se acaba de abrir para usarla en 'characters'
             etiquetaActual = qName;

             // Cuando el año es un atributo de la etiqueta <libro> triggereamos:
             if (qName.equals("libro")) {
                 String anio = attributes.getValue("año");
                 System.out.println("Año: " + anio);
             }
        }       
        
        @Override
        public void characters(char[] ch, int inicio, int longitud){
            
            // Obtenemos el texto que hay dentro de la etiqueta
            String contenido = new String(ch, inicio, longitud).trim();
            
            // Solo procesamos si hay texto real (evitamos saltos de línea vacíos)
            if (contenido.length() > 0) {
                
                if (etiquetaActual.equals("titulo")) {
                    System.out.println("Título: " + contenido);
                } 
                else if (etiquetaActual.equals("editorial")) {
                    System.out.println("Editorial: " + contenido);
                } 
                else if (etiquetaActual.equals("precio")) {
                    System.out.println("Precio: " + contenido);
                }
                // Si es parte del autor, lo guardamos en variable, no lo imprimimos aún
                else if (etiquetaActual.equals("nombre")) {
                    nombreAutor = contenido;
                }
                else if (etiquetaActual.equals("apellido")) {
                    apellidoAutor = contenido;
                }
            }
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) {
            
            // Cuando se cierra la etiqueta </autor>, imprimimos el nombre completo
            if (qName.equals("autor")) {
                System.out.println("Autor: " + nombreAutor + " " + apellidoAutor);
                // Reseteamos las variables por seguridad
                nombreAutor = "";
                apellidoAutor = "";
            }
            
            // Cuando termina un libro, ponemos una línea en blanco para separar
            if (qName.equals("libro")) {
                System.out.println(); 
            }
            
            // Reseteamos la etiqueta actual para no procesar espacios en blanco entre etiquetas
            etiquetaActual = "";
        }
    }
}