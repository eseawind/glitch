package ezbake.glitch.config;

import java.util.Collection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * <p>
 * Represents the exception handler configuration. 
 * </p>
 */
public class Configuration {

   private String defaultHandlerClassname;
   private Multimap<String, String> handlerMap; 
   
   public Configuration() {
      handlerMap = HashMultimap.create();
   }
   
   /**
    * <p>
    * Returns the default handler used for each exception that does not have
    * a specific handler associated with it.
    * </p>
    * 
    * @return The default handler for exceptions not declared in the
    *       configuration.
    */
   public String getDefaultHandlerClassname() {
      return defaultHandlerClassname;
   }
   
   /**
    * <p>
    * Sets the class name of the default handler. The default handler is used
    * for each exception that does not have a specific handler associated with
    * it. 
    * </p>
    * 
    * @param defaultHandlerFqcn The fully qualified class name of the handler
    *       that is used by default for those exceptions without an associated
    *       handler.
    */
   public void setDefaultHandlerClassname(String defaultHandlerFqcn) {
      this.defaultHandlerClassname = defaultHandlerFqcn;
   }
   
   /**
    * <p>
    * Associates an exception class with a handler class. The handler will
    * process the exception in a manner specific to the handler. No
    * exception-handler association is added if a) the handler is null or
    * empty or b) the exception is null or empty. 
    * </p>
    * <p>
    * An exception may have multiple handlers associated to it; each will
    * provide some action whenever that exception is thrown and processed.
    * </p>
    * 
    * @param exceptionFqcn The fully qualified class name of the exception to
    *       that is handled by handlerFqcn. If the value is null or empty then
    *       the association is not added.
    * @param handlerFqcn The fully qualified class name of the handler. If the
    *       value is null or empty then the association is not added.
    * @return true if the entry was added and false if not.
    */
   public boolean addExceptionHandler(String exceptionFqcn, String handlerFqcn) {
      
      if (isNothing(handlerFqcn) || 
            isNothing(exceptionFqcn)) {
         return false;
      }
      return handlerMap.put(exceptionFqcn, handlerFqcn);
   }
   
   /**
    * <p>
    * Returns the fully-qualified class names of the handlers associated with
    * the {@link ezbake.glitch.CoreException} exception.
    * </p>
    * 
    * @param exceptionFqcn The fully-qualified class name of the
    * {@link ezbake.glitch.CoreException} exception class.
    */
   Collection<String> getHandlers(String exceptionFqcn) {
      
      return handlerMap.get(exceptionFqcn);
   }

   /**
    * <p>
    * Answers true if the string is null or empty and false if not.
    * </p>
    * 
    * @param value A string that is checked for nothingness.
    * @return true if the string is null or empty and false if not.
    */
   private boolean isNothing(String value) {
      return value == null || value.trim().isEmpty();
   }
   
}
