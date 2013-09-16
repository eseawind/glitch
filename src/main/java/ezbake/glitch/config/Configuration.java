package ezbake.glitch.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * <p>
 * Represents the exception handler configuration. 
 * </p>
 * <p>
 * The configuration describes the mapping between a {@link ezbake.glitch.CoreException}
 * exception and a {@link ezbake.glitch.CoreExceptionHandler} handler. When
 * an exception is thrown, the exception manager may be used to process the
 * exception in a business specific manner by calling
 * {@link ezbake.glitch.ExceptionManager#handleException(ezbake.glitch.CoreException)}.
 * An exception may be associated to multiple handlers so that several
 * different actions are triggered when that exception is thrown and handled by
 * the exception manager.
 * </p>
 * <p>
 * Default handlers are executed if an exception class is sent for handling by
 * the exception manager but the exception does not have a specific handler
 * associated with it in the configuration.
 * </p>
 * @see {@link ezbake.glitch.CoreException} The exceptions that may be handled
 *       by the framework.
 * @see {@link ezbake.glitch.CoreExceptionHandler} The handlers that perform an
 *       action when an exception is thrown and sent for handling.
 * @see ezbake.glitch.ExceptionManager The manager that directs the handling of
 *       a thrown exception.
 */
public class Configuration {

   private Set<String> defaultHandlers;
   private Multimap<String, String> handlerMap; 
   
   public Configuration() {
      
      defaultHandlers = new TreeSet<String>();
      handlerMap = HashMultimap.create();
   }
   
   /**
    * <p>
    * Returns the set of fully qualified class names that represent the default
    * handlers for this configuration. The class names should be a 
    * {@link ezbake.glitch.CoreExceptionHandler} type.
    * </p>
    * 
    * @return The set of fully qualified class names that are the default
    *       handlers used by this configuration.
    */
   public Set<String> getDefaultHandlers() {
      return defaultHandlers;
   }

   /**
    * <p>
    * Adds a fully qualified class name to the default handler set. The class
    * must be a {@link ezbake.glitch.CoreExceptionHandler} type.
    * </p>
    * 
    * @param defaultHandlerFqcn A fully qualified class name to add to the set
    *       of default handlers used by this configuration.
    * @return true if this default handler set changed as a result of the add
    *       and false if it did not.
    */
   public boolean addDefaultHandler(String defaultHandlerFqcn) {
      
      if (defaultHandlerFqcn == null) {
         return false;
      }
      return this.defaultHandlers.add(defaultHandlerFqcn);
   }
   
   /**
    * <p>
    * Adds the collection of fully qualified class names to the default handler
    * set. The classes must be a {@link ezbake.glitch.CoreExceptionHandler}
    * type.
    * </p>
    * 
    * @param defaultHandlerFqcns A collection of fully qualified class names to
    *       add to the set of default handlers used by this configuration.
    * @return true if this default handler set changed as a result of the add
    *       and false if it did not.
    */
   public boolean addDefaultHandlers(Collection<String> defaultHandlerFqcns) {
      
      if (defaultHandlers == null) {
         return false;
      }
      return this.defaultHandlers.addAll(defaultHandlers);
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
    * Returns a collection of fully qualified class names of the handlers
    * associated with the given {@link ezbake.glitch.CoreException} exception.
    * If no explicit handler mappings are found then the default handlers are
    * returned.
    * </p>
    * 
    * @param exceptionFqcn The fully qualified class name of the
    * {@link ezbake.glitch.CoreException} exception class.
    * @return A collection of handlers that are mapped to the given exception.
    * @see {@link #getExceptionHandlers(String, boolean)} To set whether or not
    *       the default handlers are returned if no explicit handler mappings
    *       exist for the exception.
    */
   public Collection<String> getExceptionHandlers(String exceptionFqcn) {
      
      return getExceptionHandlers(exceptionFqcn, true);
   }
   
   /**
    * <p>
    * Returns a collection of fully qualified class names of the handlers
    * associated with the given {@link ezbake.glitch.CoreException} exception.
    * If the useDefault parameter is true then the default handlers are
    * returned if no explicit handler mappings are found for the exception.
    * </p>
    * 
    * @param exceptionFqcn The fully qualified class name of the
    *       {@link ezbake.glitch.CoreException} exception class.
    * @param useDefault if true then return this configuration's default
    *       handlers if the exception does not have any explicit handler
    *       mappings. if false then return only the exception's explicit
    *       handler mappings even if there are none. 
    * @return A collection of handlers that are mapped to the given exception.
    * @see {@link #getExceptionHandlers(String, boolean)} To set whether or not
    *       the default handlers are returned if no explicit handler mappings
    *       exist for the exception.
    */
   public Collection<String> getExceptionHandlers(String exceptionFqcn, boolean useDefault) {
      
      Collection<String> handlers = new HashSet<String>();
      handlers.addAll(handlerMap.get(exceptionFqcn));
      if (useDefault && handlers.isEmpty()) {
         handlers.addAll(this.getDefaultHandlers());
      }
      return handlers;
   }
   
   /**
    * <p>
    * Returns a set of fully qualified class names of the handlers that are
    * mapped to exceptions and the handlers that are specified as defaults.
    * </p>
    * 
    * @return A set of all the fully qualified handler class names, including
    *       the default handlers. 
    */
   public Set<String> getAllHandlers() {
      
      HashSet<String> handlers = new HashSet<String>();
      handlers.addAll(handlerMap.values());
      handlers.addAll(this.defaultHandlers);
      return handlers;
   }
   
   public boolean removeHandlerReferences(Collection<String> handlerFqcns) {
      
      if (handlerFqcns == null) {
         return false;
      }
      
      boolean result = defaultHandlers.removeAll(handlerFqcns);
      // TODO: How to remove map entries that have a specific value???
      return result;
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
   
   // This may develop into a bi-map for better manipulation and removal.
   static class ExceptionHandlerMap {
      
      private ArrayList<String> exceptionFqcns;
      private ArrayList<String> handlerFqcns;
      
      ExceptionHandlerMap() {
         exceptionFqcns = new ArrayList<String>();
         handlerFqcns = new ArrayList<String>();
      }
      
      boolean add(String exceptionFqcn, String handlerFqcn) {
         // TODO: add "nothing" check.
         exceptionFqcns.add(exceptionFqcn);
         handlerFqcns.add(handlerFqcn);
         return true;
      }
   }
}
