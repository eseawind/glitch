package ezbake.glitch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ezbake.glitch.config.Configuration;
import ezbake.glitch.config.XmlConfigurationParser;


/**
 * <p>
 * The ExceptionManager is responsible for executing handlers that are mapped
 * to exceptions. Mapping occurs between a {@link ezbake.glitch.CoreException}
 * type, referred to as the exception, and a {@link ezbake.glitch.CoreExceptionHandler}
 * type, referred to as the handler.<br>
 * The mapping between an exception and a handler is defined in the
 * configuration which is either read from a file or constructed programmatically.
 * In most operating situations the  configuration file is used over the
 * programmatic construction.
 * </p>
 * <p>
 * To trigger exception handling, get the ExceptionManager instance via
 * {@link #getInstance()} and call the {@link #handleException(CoreException)}.
 * The code snippet example below will read in exception-handler mappings from
 * the configuration file automatically.
 * </p>
 * <p>
 * <code>
 * try {   <br>
 *    ...  <br>
 * } catch (SomeCoreException e) { <br>
 *    ExceptionManager.getInstance().handleException(e);  <br>
 * }  <br>
 * </code>
 * </p>
 * <p>
 */
public final class ExceptionManager {

   private static ExceptionManager instance;
   
   private Logger logger = LoggerFactory.getLogger(ExceptionManager.class);
   private Configuration configuration; 
   private Map<String, CoreExceptionHandler> handlers; 
   
   /**
    * <p>
    * Constructs a new instance of this class. This class acts as a singleton;
    * thus, {@link #getInstance()} must be used to obtain a class instance.
    * </p>
    * 
    * @see  #getInstance() to obtain an ExceptionManager instance.
    */
   private ExceptionManager() {
      
      this.readConfiguration();
      this.instantiateHandlers();
   }
   
   /**
    * <p>
    * Constructs a new instance of this class using the given configuration.
    * This class acts as a singleton; thus, {@link #getInstance()} must be
    * used to obtain a class instance.
    * </p>
    * 
    * @see  #initialize(Configuration) to explicitly set the configuration.
    * @see  #getInstance() to obtain an ExceptionManager instance.
    */
   private ExceptionManager(Configuration configuration) {
      
      this.configuration = configuration;
      this.instantiateHandlers();
   }
   
   /**
    * <p>
    * Returns the exception handler manager instance. This instance is used to
    * request the handling of a thrown {@link ezbake.glitch.CoreException}
    * exception.
    * </p>
    * 
    * @return The ExceptionManager instance responsible for executing exception
    *       handler processing.
    */
   public static ExceptionManager getInstance() {
      
      if (instance == null) {
         synchronized (ExceptionManager.class) {
            instance = new ExceptionManager();
         }
      }
      return instance;
   }
   
   /**
    * <p>
    * Initializes the exception handler manager with the configuration provided.
    * Any existing exception handling manager instance is reset with the given
    * configuration.
    * </p>
    * <p>
    * A null configuration is permissible. The result will be that no mappings
    * are specified. Thus, there will be an absence of exception handling.
    * </p>
    * 
    * @param configuration The configuration with which the exception handling
    *       manager uses to determine the exception-handling mapping.
    * @return The ExceptionManager instance responsible for executing exception
    *       handler processing.
    */
   public synchronized static ExceptionManager initialize(Configuration configuration) {
      
      instance = new ExceptionManager(configuration == null ? 
            new Configuration() : configuration);
      return instance;
   }
   
   /**
    * <p>
    * Executes the handlers associated with the given exception. If no
    * associated handlers exist for the exception then the default handlers
    * are executed.
    * </p>
    * 
    * @param exception The exception instance that is to be handled. Handling
    *       is ignored if no exception is provided.
    */
   public static void handleException(CoreException exception) {
      
      getInstance().handle(exception);
   }
   
   /**
    * <p>
    * Executes the handlers associated with the given exception. If no
    * associated handlers exist for the exception then the default handlers
    * are executed.
    * </p>
    * 
    * @param exception The exception instance that is to be handled. Handling
    *       is ignored if no exception is provided.
    */
   public void handle(CoreException exception) {
      
      if (exception == null) return;
      
      String exceptionFqcn = exception.getClass().getName();
      Collection<String> handlerFqcns = configuration.getExceptionHandlers(exceptionFqcn);

      if (handlerFqcns.isEmpty()) {
         handlerFqcns = configuration.getDefaultHandlers();
      }
      
      for (String handlerFqcn : handlerFqcns) {
         
         try {
            CoreExceptionHandler handler = handlers.get(handlerFqcn);
            if (handler == null) {
               logger.info("Handler '" + handlerFqcn + "' was not found; ignoring this handler's processing for exception type '" + exceptionFqcn + "'. Verify that the handler's namespace/package is correct and that it is in the runtime classpath.");
            }
            handler.handle(exception);
         } catch (Exception e) {
            logger.error("Handler '" + handlerFqcn + "' encountered errors when executing handler processing for exception type '" + exceptionFqcn + "'.", e);
         }
      }
   }
   
   /**
    * <p>
    * Obtains the configuration settings from a user-configured file.
    * </p>
    * 
    * @return  The configuration settings as defined in the configuration file.
    */
   private Configuration readConfiguration() {
      
      return XmlConfigurationParser.load();
   }
   
   /**
    * <p>
    * Obtains all the handlers defined in the configuration and instantiates
    * each one. A handler that cannot be instantiated will have all its
    * mappings removed.
    * </p>
    */
   private void instantiateHandlers() {

      ArrayList<String> badHandlers = new ArrayList<String>();
      Set<String> handlerFqcnSet = configuration.getAllHandlers();
      
      for (Iterator<String> handlerIterator = handlerFqcnSet.iterator(); handlerIterator.hasNext();) {
         
         String handlerFqcn = handlerIterator.next();
         CoreExceptionHandler handler = instantiateHandler(handlerFqcn);
         if (handler == null) {
            badHandlers.add(handlerFqcn);
         } else {
            handlers.put(handlerFqcn, handler);
         }
      }
      
      configuration.removeHandlerReferences(badHandlers);
   }
   
   /**
    * <p>
    * Given the fully qualified class name of a CoreExceptionHandler, returns
    * an instance of the class.
    * </p>
    * <p>
    * If the class name is null, is empty, cannot be found, cannot be
    * initialized or is not otherwise accessible then null is returned.
    * </p>
    * 
    * @param fqcn A fully qualified class name of a CoreExceptionHandler.
    * @return An instance of class identified by the given fully qualified
    *       class name parameter. Null is returned if the class name is not
    *       valid.
    */
   @SuppressWarnings("unchecked")
   private CoreExceptionHandler instantiateHandler(String fqcn) {
      
      CoreExceptionHandler handler = null;
      
      if (fqcn != null && !fqcn.trim().isEmpty()) {
         
         try {
            Class<CoreExceptionHandler> handlerClass = (Class<CoreExceptionHandler>) Class.forName(fqcn);
            handler = handlerClass.newInstance();
         } catch (Exception e) {
            logger.error(
                  "The handler '" + fqcn + "' could be found or could not be instantiated; ingoring all references and associations to this handler. Adjust the configuration to use a valid, locatable handler.", 
                  e);
         }
      }
      
      return handler;
   }
   
}
