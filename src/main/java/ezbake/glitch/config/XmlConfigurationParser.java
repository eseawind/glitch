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
 * The default configuration file name is <b>glitch-config.xml</b>. Set the
 * resource name attribute to override the default.
 * </p>
 * <p>
 * TODO: This will be redone because the current implementation, while working,
 * is not very readable. Instead of using the DOM parser, the XPATH parser will
 * be used.
 * </p>
 */
public final class XmlConfigurationParser implements ConfigurationParser {
   
   private Logger logger = LoggerFactory.getLogger(XmlConfigurationParser.class);
   
   private static final String DEFAULT_CONFIG_RESOURCE_NAME = "glitch-config.xml";
   
   private Configuration configuration;
   private Document document;
   private String resourceName;
   
   /**
    * <p>
    * Creates in instance of the XML configuration file parser. It is expected
    * that the configuration file is using the default name.
    * </p>
    */
   public XmlConfigurationParser() {
      
      this(DEFAULT_CONFIG_RESOURCE_NAME);
   }
   
   /**
    * <p>
    * Creates an instance of this XML configuration parser. The configuration
    * file parse is the resource name given. It is expected that the resource
    * name is on the classpath. 
    * </p>
    * 
    * @param configResourceName The name of the xml configuration file that
    *       this instance will parse. If null or empty then the default
    *       resource name is used.
    */
   public XmlConfigurationParser(String configResourceName) {
      
      this.configuration = new Configuration();
      this.setResourceName(configResourceName);
   }
   
   /**
    * <p>
    * Sets the resource name of the XML configuration file that this class
    * instance will parse.
    * </p>
    * 
    * @param configResourceName The name of the xml configuration file that
    *       this instance will parse. If null or empty then the default
    *       resource name is used.
    */
   private void setResourceName(String configResourceName) {
      
      if (configResourceName == null || configResourceName.trim().length() == 0) {
         this.resourceName = DEFAULT_CONFIG_RESOURCE_NAME;
      } else {
         this.resourceName = configResourceName;
      }
   }

   public static void main(String[] args) {
      
      try {
         XmlConfigurationParser parser = new XmlConfigurationParser();
         parser.getConfiguration();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * 
    */
   public Configuration getConfiguration() {
      
      loadConfigurationFile();
      configureDefaultHandlers();
      configureExceptionHandlers();
      return this.configuration;
   }
   
   /**
    * 
    */
   public Configuration getConfiguration(String resouceName) {

      setResourceName(resourceName);
      return getConfiguration();
   }
   
   private void loadConfigurationFile() {
      
      ClassLoader loader = this.getClass().getClassLoader();
      InputStream configInputStream = loader.getResourceAsStream(DEFAULT_CONFIG_RESOURCE_NAME);
      
      if (configInputStream == null) {
         logger.warn("The exception handling configuration file," + DEFAULT_CONFIG_RESOURCE_NAME + ", was not found.");
      } else {
         try {
            
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setIgnoringComments(true);
            document = builderFactory.newDocumentBuilder().parse(configInputStream);
         } catch (Exception e) {
            logger.error("An error occurred while parsing the " + DEFAULT_CONFIG_RESOURCE_NAME + " configuration file.", e);
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
}
