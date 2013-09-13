package ezbake.glitch.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * Tests to validate the {@link ezbake.glitch.config.Configuration} class.
 * </p>
 */
public class ConfigurationTest {

   /**
    * addExceptionHandler
    * <p>
    * Validates that a handler associated with the exception is established.
    * Only one handler is added.
    * </p>
    */
   @Test
   public void addSingExceptionHandler() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      
      List<String> handlers = asList(config.getHandlers(exceptionFqcn));
      Assert.assertEquals(1, handlers.size());
      Assert.assertEquals(handlerFqcn, handlers.get(0));
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that multiple handlers may be associated to an exception.
    * </p>
    */
   @Test
   public void addMultipleDifferentHandlersToException() {
      
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that if the same handler is added for an exception multiple
    * times then it will only save the association one time; there will be no
    * duplicates.
    * </p>
    */
   @Test
   public void addMultipleSameHandlerToException() {
      
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that a handler can be used for different exceptions.
    * </p>
    */
   @Test
   public void addSameHandlerToDifferentExceptions() {
      
   }
  
   private List<String> asList(Collection<String> collection) {
      
      ArrayList<String> list = new ArrayList<String>();
      list.addAll(collection);
      return list;
   }

}
