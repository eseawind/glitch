package ezbake.glitch;

/**
 * <p>
 * Provides logic to handle exception messaging and notification in a specific
 * manner. Thus, all {@link CoreException} classes that are associated to the
 * handler are handled in the manner provided in the {@link #handle(CoreException)}
 * method. 
 * </p>
 * <p>
 * The {@link Configuration} establishes which {@link CoreException} classes are
 * associated with which {@link CoreExceptionHandler} handlers.
 * </p>
 * 
 * @see  Configuration for establishing the association between a CoreException
 *       and a handler.
 */
public interface CoreExceptionHandler {
   
   /**
    * <p>
    * The method that initiates the handler's processing of the 
    * {@link CoreException} instance.
    * </p>
    * 
    * @param e The exception instance that is to be processed.
    */
   void handle(CoreException e);
}
