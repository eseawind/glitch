package ezbake.glitch;

import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import ezbake.glitch.config.Configuration;
import ezbake.glitch.exception.DefaultException;
import ezbake.glitch.handler.CounterHandler;
import ezbake.glitch.handler.DefaultExceptionHandler;

public class ExceptionManagerTest {

   /**
    * getInstance
    * <p>
    * Validates that the getInstance method will load the configuration file
    * by default. The configuration file only has 1 default handler entry and
    * no exception handler entries.
    * </p>
    * <p>
    * Ignoring because this test case is unreliable. ExceptionManager is a
    * singleton and the order in which the tests result in the singleton
    * being already initialized.
    * </p>
    */
   @Test
   @Ignore
   public void getInstanceBasicCase() {
      
      ExceptionManager manager = ExceptionManager.getInstance();
      Map<String, CoreExceptionHandler> handlerMap = manager.getHandlers();
      Assert.assertEquals("Assert 001", 1, handlerMap.size());
      Assert.assertTrue("Assert 002", handlerMap.containsKey(DefaultExceptionHandler.class.getName()));
   }
   
   /**
    * <p>
    * Verifies that a null config passed into initialize will not blow up. The
    * configuration will just not have any handling.
    * </p>
    */
   @Test
   public void initializeWithNoConfig() {
      
      ExceptionManager manager = ExceptionManager.initialize(null);
      Map<String, CoreExceptionHandler> handlerMap = manager.getHandlers();
      Assert.assertEquals(0, handlerMap.size());
   }
   
   /**
    * <p>
    * Verifies that all the handlers are accounted for when using a programmatic
    * configuration in the initialize method.
    * </p>
    */
   @Test
   public void initializeWithConfig() {

      Configuration config = new Configuration();
      config.addDefaultHandler(DefaultExceptionHandler.class.getName());
      config.addExceptionHandler(DefaultException.class.getName(), CounterHandler.class.getName());
      
      ExceptionManager manager = ExceptionManager.initialize(config);
      Map<String, CoreExceptionHandler> handlerMap = manager.getHandlers();
      Assert.assertEquals("Assert 001",  2, handlerMap.size());
      Assert.assertTrue("Assert 002", handlerMap.containsKey(DefaultExceptionHandler.class.getName()));
      Assert.assertTrue("Assert 003", handlerMap.containsKey(CounterHandler.class.getName()));
   }
   
}
