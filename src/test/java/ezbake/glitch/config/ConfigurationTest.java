package ezbake.glitch.config;

import java.util.Collection;

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
    * getExceptionHandlers
    * 
    * <p>
    * Validates that a handler associated with the exception is established.
    * Only one handler is added.
    * </p>
    */
   @Test
   public void addSingleExceptionHandler() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      
      Collection<String> handlers = config.getExceptionHandlers(exceptionFqcn);
      Assert.assertTrue("The handler should have added successfully and, thus, returned true.", result);
      Assert.assertEquals("The size of handler collection is incorrect.", 1, handlers.size());
      Assert.assertTrue("Expected " + handlerFqcn + " in the results.", handlers.contains(handlerFqcn));
   }
   
   /**
    * addExceptionHandler
    * getExceptionHandlers
    * 
    * <p>
    * Validates that multiple handlers may be associated to an exception.
    * </p>
    */
   @Test
   public void addMultipleDifferentHandlersToException() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handler1Fqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      String handler2Fqcn = "ezbake.glitch.handler.CounterExceptionHandler";
      String handler3Fqcn = "ezbake.glitch.handler.MessageListExceptionHandler";
      
      Configuration config = new Configuration();
      config.addExceptionHandler(exceptionFqcn, handler1Fqcn);
      config.addExceptionHandler(exceptionFqcn, handler2Fqcn);
      config.addExceptionHandler(exceptionFqcn, handler3Fqcn);
      Collection<String> handlers = config.getExceptionHandlers(exceptionFqcn);
      
      Assert.assertEquals("The size of handler collection is incorrect.", 3, handlers.size());
      Assert.assertTrue("Expected " + handler1Fqcn + " in the results.", handlers.contains(handler1Fqcn));
      Assert.assertTrue("Expected " + handler2Fqcn + " in the results.", handlers.contains(handler2Fqcn));
      Assert.assertTrue("Expected " + handler3Fqcn + " in the results.", handlers.contains(handler3Fqcn));
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
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertTrue("The handler should have added successfully and, thus, returned true.", result);
      result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertFalse("A duplicate exception-handler mapping was added; thus, mapping set shouldn't be updated and false should be returned..", result);
      
      Collection<String> handlers = config.getExceptionHandlers(exceptionFqcn);
      Assert.assertEquals("The size of handler collection is incorrect; duplicate entries should be ignored.", 1, handlers.size());
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
   
   /**
    * addDefaultHandler
    * <p>
    * Validates that adding a null class name value is not added to the
    * collection.
    * </p>
    */
   @Test
   public void addNullDefaultHandler() {
      
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the default handler collection size should be zero.", 0, config.getDefaultHandlers().size());
      
      boolean result = config.addDefaultHandler(null);
      Assert.assertEquals("A null default handler was added; the default handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("The null value was not added to the defualt handler collection; result should indicate that with a false.", result);
   }
   
   /**
    * addDefaultHandler
    * <p>
    * Validates that adding an empty string class name value is not added to
    * the collection.
    * </p>
    */
   @Test
   public void addEmptyDefaultHandler() {
      
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the default handler collection size should be zero.", 0, config.getDefaultHandlers().size());
      
      boolean result = config.addDefaultHandler("    ");
      Assert.assertEquals("An empty default handler was added; the default handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("The empty value was not added to the defualt handler collection; result should indicate that with a false.", result);
   }
   
   /**
    * addDefaultHandler
    * <p>
    * Validates that adding a default handler is functional.
    * </p>
    */
   @Test
   public void addSingleDefaultHandler() {
   
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      boolean result = config.addDefaultHandler(handlerFqcn);
      Assert.assertTrue("The default handler should have added successfully and, thus, returned true.", result);
      
      Collection<String> defaultHandlers = config.getDefaultHandlers();
      Assert.assertEquals("The size of the default handler collection is incorrect.", 1, defaultHandlers.size());
      Assert.assertTrue("The default handler collection is missing " + handlerFqcn + ".", defaultHandlers.contains(handlerFqcn));
   }
   
   /**
    * addDefaultHandler
    * <p>
    * Validates that adding multiple default handlers is functional.
    * </p>
    */
   @Test
   public void addMultipleDefaultHandlers() {
      
      String handler1Fqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      String handler2Fqcn = "ezbake.glitch.handler.CounterExceptionHandler";
      String handler3Fqcn = "ezbake.glitch.handler.MessageListExceptionHandler";
      
      Configuration config = new Configuration();
      boolean result = config.addDefaultHandler(handler1Fqcn);
      Assert.assertTrue("The default handler " + handler1Fqcn + " should have added successfully and, thus, returned true.", result);

      config.addDefaultHandler(handler2Fqcn);
      Assert.assertTrue("The default handler " + handler2Fqcn + " should have added successfully and, thus, returned true.", result);
      
      config.addDefaultHandler(handler3Fqcn);
      Assert.assertTrue("The default handler " + handler3Fqcn + " should have added successfully and, thus, returned true.", result);

      Collection<String> handlers = config.getDefaultHandlers();
      Assert.assertEquals("The size of handler collection is incorrect.", 3, handlers.size());
      Assert.assertTrue("Expected " + handler1Fqcn + " in the results.", handlers.contains(handler1Fqcn));
      Assert.assertTrue("Expected " + handler2Fqcn + " in the results.", handlers.contains(handler2Fqcn));
      Assert.assertTrue("Expected " + handler3Fqcn + " in the results.", handlers.contains(handler3Fqcn));
   }
   
   /**
    * addDefaultHandler
    * <p>
    * Validates that adding duplicate default handlers will not result in
    * duplicate values in the default handler collection. Duplicate values
    * should be ignored.
    * </p>
    */
   @Test
   public void addDuplicateDefaultHandlers() {
      
      String handlerFqcn = "ezbake.glitch.handler.CounterExceptionHandler";
      
      Configuration config = new Configuration();
      boolean result = config.addDefaultHandler(handlerFqcn);
      Assert.assertTrue("The default handler " + handlerFqcn + " should have added successfully and, thus, returned true.", result);

      result = config.addDefaultHandler(handlerFqcn);
      Assert.assertFalse("The default handler " + handlerFqcn + " is a duplicate and, thus, should return false.", result);

      Collection<String> handlers = config.getDefaultHandlers();
      Assert.assertEquals("The size of handler collection is incorrect.", 1, handlers.size());
      Assert.assertTrue("Expected " + handlerFqcn + " in the results.", handlers.contains(handlerFqcn));
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that adding a null handler class name value to the exception
    * handler collection is ignored because of null value.
    * </p>
    */
   @Test
   public void addExceptionHandlerWithNullException() {
      
      String exceptionFqcn = null;
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the exception handler collection size should be zero.", 0, config.getExceptionHandlers(exceptionFqcn).size());
      
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertEquals("With null exception the exception handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("With null exception no entry added to the exception handler collection; result should indicate that with a false.", result);
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that adding a null exception class name value to exception
    * handler collection is ignored because of null value.
    * </p>
    */
   @Test
   public void addExceptionHandlerWithNullHandler() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = null;
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the exception handler collection size should be zero.", 0, config.getExceptionHandlers(exceptionFqcn).size());
      
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertEquals("With null handler value the exception handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("With null handler no entry added to the exception handler collection; result should indicate that with a false.", result);
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that adding an empty string handler class name value to the
    * exception handler collection is ignored because of the empty value.
    * </p>
    */
   @Test
   public void addExceptionHandlerWithEmptyException() {
      
      String exceptionFqcn = "";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the exception handler collection size should be zero.", 0, config.getExceptionHandlers(exceptionFqcn).size());
      
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertEquals("With empty exception the exception handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("With empty exception no entry added to the exception handler collection; result should indicate that with a false.", result);
   }
   
   /**
    * addExceptionHandler
    * <p>
    * Validates that adding an empty string exception class name value to
    * exception handler collection is ignored because of the empty value.
    * </p>
    */
   @Test
   public void addExceptionHandlerWithEmptyHandler() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "";
      Configuration config = new Configuration();
      Assert.assertEquals("The config was just instantiated; the exception handler collection size should be zero.", 0, config.getExceptionHandlers(exceptionFqcn).size());
      
      boolean result = config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      Assert.assertEquals("With null handler value the exception handler collection size should still be zero.", 0, config.getDefaultHandlers().size());
      Assert.assertFalse("With null handler no entry added to the exception handler collection; result should indicate that with a false.", result);
   }

   /**
    * getExceptionHandlers
    * <p>
    * 
    * </p>
    */
   @Test
   public void getExceptionHandlersExplicitMapping() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handler1Fqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      String handler2Fqcn = "ezbake.glitch.handler.CounterExceptionHandler";
      
      Configuration config = new Configuration();
      config.addDefaultHandler(handler1Fqcn);
      config.addExceptionHandler(exceptionFqcn, handler2Fqcn);
      
      Collection<String> handlers = config.getExceptionHandlers(exceptionFqcn);
      Assert.assertEquals("The size of handler collection is incorrect.", 1, handlers.size());
      Assert.assertTrue("Expected " + handler2Fqcn + " in the results.", handlers.contains(handler2Fqcn));
   }
   
   /**
    * getExceptionHandlers
    * <p>
    * Validates that when getExceptionHandlers when the exception has no explicit
    * handlers that the default handler is returned when useDefault=true and that
    * the default is not returned when useDefault=false.
    * </p>
    */
   @Test
   public void getExceptionHandlerWithNoMappingButWithDefault() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      config.addDefaultHandler(handlerFqcn);
      
      Collection<String> handlers = config.getExceptionHandlers(exceptionFqcn);
      Assert.assertEquals("The size of handler collection is incorrect.", 1, handlers.size());
      Assert.assertTrue("Expected " + handlerFqcn + " in the results.", handlers.contains(handlerFqcn));
      
      handlers = config.getExceptionHandlers(exceptionFqcn, true);
      Assert.assertEquals("The size of handler collection is incorrect (calling with useDefault=true).", 1, handlers.size());
      Assert.assertTrue("Expected " + handlerFqcn + " in the results.", handlers.contains(handlerFqcn));
     
      handlers = config.getExceptionHandlers(exceptionFqcn, false);
      Assert.assertEquals("The size of handler collection is incorrect (calling with useDefault=false).", 0, handlers.size());
   }
   
   /**
    * getAllHandlers
    * <p>
    * Validates that a configuration with multiple default and exception handlers
    * are all accounted for when getAllHandlers is called.
    * </p>
    */
   public void getHandlersWithMultipleDefaultAndExceptionHandlers() {
      
      String exception1Fqcn = "ezbake.glitch.exception.HelloException";
      String handler1Fqcn = "ezbake.glitch.handler.HelloExceptionHandler";
      String exception2Fqcn = "ezbake.glitch.exception.SomeException";
      String handler2Fqcn = "ezbake.glitch.handler.SomeExceptionHandler";
      String defaultHandler1Fqcn = "ezbake.glitch.handler.DefAExceptionHandler";
      String defaultHandler2Fqcn = "ezbake.glitch.handler.DefBExceptionHandler";
     
      Configuration config = new Configuration();
      config.addDefaultHandler(defaultHandler1Fqcn);
      config.addDefaultHandler(defaultHandler2Fqcn);
      config.addExceptionHandler(exception1Fqcn, handler1Fqcn);
      config.addExceptionHandler(exception2Fqcn, handler2Fqcn);
      
      Collection<String> handlers = config.getAllHandlers();
      Assert.assertEquals("The size of handler collection is incorrect.", 4, handlers.size());
      Assert.assertTrue("Expected " + handler1Fqcn + " in the results.", handlers.contains(handler1Fqcn));
      Assert.assertTrue("Expected " + handler2Fqcn + " in the results.", handlers.contains(handler2Fqcn));
      Assert.assertTrue("Expected " + defaultHandler1Fqcn + " in the results.", handlers.contains(defaultHandler1Fqcn));
      Assert.assertTrue("Expected " + defaultHandler2Fqcn + " in the results.", handlers.contains(defaultHandler2Fqcn));
   }
   
   /**
    * getAllHandlers
    * <p>
    * Validates that when the default and exception handlers are the same that
    * the results from getAllHandlers does not return duplicates.
    * </p>
    */
   public void getHandlersWithSameHandlerForDefaultAndException() {
      
      String exceptionFqcn = "ezbake.glitch.exception.DefaultException";
      String handlerFqcn = "ezbake.glitch.handler.DefaultExceptionHandler";
      
      Configuration config = new Configuration();
      config.addDefaultHandler(handlerFqcn);
      config.addExceptionHandler(exceptionFqcn, handlerFqcn);
      
      Collection<String> handlers = config.getAllHandlers();
      Assert.assertEquals("The size of handler collection is incorrect.", 1, handlers.size());
      Assert.assertTrue("Expected " + handlerFqcn + " in the results.", handlers.contains(handlerFqcn));
   }
   
}
