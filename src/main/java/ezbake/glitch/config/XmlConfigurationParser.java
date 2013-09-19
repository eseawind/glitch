package ezbake.glitch.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * Responsible for parsing the error handling framework's configuration file in
 * the XML format.
 * </p>
 * <p>
 * NOTE: This will be redone because the current implementation, while working,
 * is not very readable. Instead of using the DOM parser, the XPATH parser will
 * be used.
 * </p>
 */
public class XmlConfigurationParser {
   
   private static final String CONFIG_FILENAME = "glitch-config.xml";
   private Logger logger = LoggerFactory.getLogger(XmlConfigurationParser.class);
   private Configuration configuration;
   private Document document;
   
   public XmlConfigurationParser() {
      this.configuration = new Configuration();
   }

   public static void main(String[] args) {
      
      try {
         XmlConfigurationParser parser = new XmlConfigurationParser();
         parser.read();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static Configuration load() {
   
      XmlConfigurationParser parser = new XmlConfigurationParser();
      parser.read();
      return parser.configuration;
   }
   
   public void read() {
      
      loadConfigurationFile();
      configureDefaultHandlers();
      configureExceptionHandlers();      
   }
   
   private void loadConfigurationFile() {
      
      ClassLoader loader = this.getClass().getClassLoader();
      InputStream configInputStream = loader.getResourceAsStream(CONFIG_FILENAME);
      
      if (configInputStream == null) {
         logger.warn("The exception handling configuration file," + CONFIG_FILENAME + ", was not found.");
      } else {
         try {
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setIgnoringComments(true);
            document = builderFactory.newDocumentBuilder().parse(configInputStream);
         } catch (Exception e) {
            logger.error("An error occurred while parsing the " + CONFIG_FILENAME + " configuration file.", e);
         }
      }
   }
   
   private void configureDefaultHandlers() {
      
      if (document == null) return;
      
      NodeList nodeList = document.getElementsByTagName("defaultHandlers");
      
      if (nodeList.getLength() == 0) return;
      
      NodeList defHandlerNodeList = nodeList.item(0).getChildNodes();
      
      for (int i = 0; i < defHandlerNodeList.getLength(); i++) {
         Node node = defHandlerNodeList.item(i);
         if ("classname".equals(node.getNodeName())) {
            Node classnameNode = node.getFirstChild();
            if (classnameNode != null) {
               this.configuration.addDefaultHandler(classnameNode.getNodeValue());
            }
         }
      }
  }
   
   private void configureExceptionHandlers() {
      
      if (document == null) return;
      
      NodeList nodeList = document.getElementsByTagName("handler");
      
      if (nodeList.getLength() == 0) return;
      
      for (int i = 0; i < nodeList.getLength(); i++) {
         
         Node handlerNode = nodeList.item(i);
         String handler = parseHandlerClassname(handlerNode);
         Collection<String> exceptions = parseHandlerExceptions(handlerNode);
         System.out.println("handler = " + handler + ", exceptions = " + exceptions);
         configuration.addHandlerExceptions(handler, exceptions);
      }
   }
   
   private String parseHandlerClassname(Node handlerNode) {
      
      NodeList handlerChildNodeList = handlerNode.getChildNodes();
      for (int j = 0; j < handlerChildNodeList.getLength(); j++) {
         Node handlerChildNode = handlerChildNodeList.item(j);
         if ("classname".equals(handlerChildNode.getNodeName())) {
            Node classnameNode = handlerChildNode.getFirstChild();
            return classnameNode == null ? null : classnameNode.getNodeValue();
         }
      }
      
      return null;
   }
   
   private Collection<String> parseHandlerExceptions(Node handlerNode) {

      ArrayList<String> exceptionList = new ArrayList<String>();
      NodeList handlerChildNodeList = handlerNode.getChildNodes();
      
      for (int i = 0; i < handlerChildNodeList.getLength(); i++) {
         Node handlerChildNode = handlerChildNodeList.item(i);
         if ("exceptions".equals(handlerChildNode.getNodeName())) {
            NodeList exceptionChildNodeList = handlerChildNode.getChildNodes();
            for (int j = 0; j < exceptionChildNodeList.getLength(); j++) {
               Node exceptionChildNode = exceptionChildNodeList.item(j);
               if ("classname".equals(exceptionChildNode.getNodeName())) {
                  Node classnameNode = exceptionChildNode.getFirstChild();
                  if (classnameNode != null) {
                     exceptionList.add(classnameNode.getNodeValue());
                  }
               }
            }
         }
      }
      
      return exceptionList;
   }
   
   public Configuration getConfiguration() {
      
      return configuration;
   }
   
}
