package ezbake.glitch.config;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;


public class XmlConfigurationParserTest {

   /**
    * <p>
    * Verifies that the no-arg constructor uses a default configuration
    * resource name.
    * </p>
    */
   @Test
   public void noargConstructorUsesDefaultResourceName() {
      
      XmlConfigurationParser parser = new XmlConfigurationParser();
      Assert.assertEquals("Assert 001", XmlConfigurationParser.DEFAULT_CONFIG_RESOURCE_NAME, parser.getResourceName());
   }
   
   /**
    * <p>
    * Verifies that the configuration resource name set in the constructor is
    * used instead of the default.
    * </p>
    */
   @Test
   public void setResourceNameInConstructor() {

      String resourceName = "some-name.xml";
      XmlConfigurationParser parser = new XmlConfigurationParser(resourceName);
      Assert.assertEquals("Assert 001", resourceName, parser.getResourceName());
   }
   
   /**
    * <p>
    * Verifies that a null resource name set in the constructor uses the
    * default resource name and not null.
    * </p>
    */
   @Test
   public void nullConstructorUsesDefaultResourceName() {

      String resourceName = null;
      XmlConfigurationParser parser = new XmlConfigurationParser(resourceName);
      Assert.assertEquals("Assert 001", XmlConfigurationParser.DEFAULT_CONFIG_RESOURCE_NAME, parser.getResourceName());
   }

   /**
    * <p>
    * Verfies that an empty resource name set in the constructor uses the
    * default resource name and not the empty value.
    * </p>
    */
   @Test
   public void emptyConstructorUsesDefaultResourceName() {

      String resourceName = "   ";
      XmlConfigurationParser parser = new XmlConfigurationParser(resourceName);
      Assert.assertEquals("Assert 001", XmlConfigurationParser.DEFAULT_CONFIG_RESOURCE_NAME, parser.getResourceName());
   }
   
   /**
    * <p>
    * Verifies that the configuration resource name set in the getConfiguration
    * method is used instead of the default.
    * </p>
    */
   @Test
   public void setResourceNameInGetConfig() {

      String resourceName = "glitch-config-empty.xml";
      XmlConfigurationParser parser = new XmlConfigurationParser();
      parser.getConfiguration(resourceName);
      Assert.assertEquals("Assert 001", resourceName, parser.getResourceName());
   }
   
   /**
    * <p>
    * Verifies that a non-existent configuration resource name returns a
    * configuration object that does not have handler mappings but does not
    * throw an error.
    * </p>
    */
   @Test
   public void useNonExistingResourceName() {

      XmlConfigurationParser parser = new XmlConfigurationParser("some-bogus-name.xml");
      Configuration config = parser.getConfiguration();
      Assert.assertEquals("Assert 001", 0, config.getAllHandlers().size());
   }
   
   /**
    * <p>
    * Verifies that a null resource name set in the getConfiguration method uses
    * the default resource name and not null.
    * </p>
    */
   @Test
   public void nullGetConfigUsesDefaultResourceName() {

      String resourceName = null;
      XmlConfigurationParser parser = new XmlConfigurationParser(resourceName);
      parser.getConfiguration(resourceName);
      Assert.assertEquals("Assert 001", XmlConfigurationParser.DEFAULT_CONFIG_RESOURCE_NAME, parser.getResourceName());
   }

   /**
    * <p>
    * Verfies that an empty resource name set in the getConfiguration method
    * uses the default resource name and not the empty value.
    * </p>
    */
   @Test
   public void emptyGetConfigUsesDefaultResourceName() {

      String resourceName = "   ";
      XmlConfigurationParser parser = new XmlConfigurationParser();
      parser.getConfiguration(resourceName);
      Assert.assertEquals("Assert 001", XmlConfigurationParser.DEFAULT_CONFIG_RESOURCE_NAME, parser.getResourceName());
   }
   
   /**
    * <p>
    * Verifies that an empty (no <defaultHandlers> or <handlers> tag) config
    * file doesn't blow up.
    * </p>
    */
   @Test
   public void emptyConfig() {
      
      XmlConfigurationParser parser = new XmlConfigurationParser("glitch-config-empty.xml");
      Configuration config = parser.getConfiguration();
      
      Collection<String> handlers = config.getAllHandlers();
      Assert.assertTrue(handlers.isEmpty());
   }
   
   /**
    * <p>
    * Multiple entries defined under <defaultHandlers>.
    * Multiple entries defined under <handlers>.
    * </p>
    */
   @Test
   public void  baseCaseConfig() {
      
      XmlConfigurationParser parser = new XmlConfigurationParser("glitch-config-basic.xml");
      Configuration config = parser.getConfiguration();
      
      Collection<String> handlers = config.getDefaultHandlers();
      Assert.assertEquals("Assert 100", 2, handlers.size());
      Assert.assertTrue("Assert 101", handlers.contains("Test01ExceptionHandler"));
      Assert.assertTrue("Assert 102", handlers.contains("Test02ExceptionHandler"));
      
      handlers = config.getExceptionHandlers("TestAException", false);
      Assert.assertEquals("Assert 200", 2, handlers.size());
      Assert.assertTrue("Assert 201", handlers.contains("Test03ExceptionHandler"));
      Assert.assertTrue("Assert 202", handlers.contains("Test04ExceptionHandler"));
      
      handlers = config.getExceptionHandlers("TestBException", false);
      Assert.assertEquals("Assert 300", 1, handlers.size());
      Assert.assertTrue("Assert 301", handlers.contains("Test04ExceptionHandler"));
      
      handlers = config.getExceptionHandlers("TestCException", false);
      Assert.assertEquals("Assert 400", 1, handlers.size());
      Assert.assertTrue("Assert 401", handlers.contains("Test04ExceptionHandler"));
   }
   
}
