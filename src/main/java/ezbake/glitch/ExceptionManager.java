package ezbake.glitch;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>
 * Directs the {@link CoreException} 
 * </p>
 *
 */
public class ExceptionManager {

   private static ExceptionManager instance;
   
   private Logger logger = Logger.getLogger(ExceptionManager.class.getName());
   private Class<CoreExceptionHandler> defaultHandler;
   private Map<Class<CoreException>, Class<CoreExceptionHandler>> handlerMap;
   
   /**
    * <p>
    * Constructs a new instance of this class. This class acts as a singleton;
    * thus, {@link #getInstance()} must be used to obtain a class instance.
    * </p>
    * 
    * @see  #getInstance() to obtain an ExceptionManager instance.
    */
   private ExceptionManager() {
   }
   
   /**
    * <p>
    * Returns an instance of the ExceptionManager class.
    * </p>
    * 
    * @return The singleton instance of ExceptionManager class.
    */
   public static ExceptionManager getInstance() {
      
      if (instance == null) {
         synchronized (ExceptionManager.class) {
            instance = new ExceptionManager();
            instance.readConfigurationSettings();
         }
      }
      return instance;
   }
   
   /**
    * 
    * @param exception
    */
   public static void handleException(CoreException exception) {
      
      getInstance().handle(exception);
   }
   
   /**
    * <p>
    * </p>
    *  
    * @param exception
    */
   public void handle(CoreException exception) {
      
      Class<CoreExceptionHandler> handlerClass = handlerMap.get(exception.getClass());
      if (handlerClass == null) {
         handlerClass = defaultHandler;
      }
      
      try {
         CoreExceptionHandler handler = handlerClass.newInstance();
         handler.handle(exception);
      } catch (Exception e) {
         logger.log(Level.SEVERE, "The handler class '" + handlerClass.getName() + "' could not be instantiated.", e);
      }
   }
   
   /**
    * 
    */
   private void readConfigurationSettings() {
      
   }
}
