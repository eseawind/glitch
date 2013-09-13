package ezbake.glitch.handler;

import ezbake.glitch.CoreException;
import ezbake.glitch.CoreExceptionHandler;

/**
 * <p>
 * The default class for exception handling. 
 * </p>
 * <p>
 * This handler will send the stack trace to System.out. 
 * </p>
 */
public class DefaultExceptionHandler implements CoreExceptionHandler {

   /**
    * <p>
    * The method that initiates the handler's processing of the 
    * {@link CoreException} instance.
    * </p>
    * 
    * @param e The exception instance that is to be processed.
    */
   public void handle(CoreException e) {

      e.printStackTrace(System.out);
   }

}
