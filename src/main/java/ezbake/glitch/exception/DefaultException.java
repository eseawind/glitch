package ezbake.glitch.exception;

import ezbake.glitch.CoreException;

/**
 * <p>
 * A default, generic exception of the framework that implements
 * CoreException.
 * </p>
 */
public class DefaultException extends Exception implements CoreException {

   private static final long serialVersionUID = 1L;

   /**
    * <p>
    * Constructs an instance of this exception.
    * </p>
    */
   public DefaultException() {
      
      super();
   }
   
   /**
    * <p>
    * Constructs an instance of this exception using the given message
    * parameter as the exception's error message.
    * </p>
    * 
    * @param message A message that describes the problem.
    */
   public DefaultException(String message) {
      
      super(message);
   }
   
   /**
    * <p>
    * Returns the actual exception instance that was thrown.
    * </p>
    * <p>
    * For this class, this instance is the exception thrown.
    * </p>
    * 
    * @return The actual exception instance that was thrown.
    */
   public Exception getException() {
      
      return this;
   }
   
}
