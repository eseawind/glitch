package ezbake.glitch.exception;

import java.io.PrintStream;

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

   /**
    * <p>
    * Writes the stack trace associated with this exception to the given output
    * stream.
    * </p>
    * 
    * @param out The output stream to which this stack trace is written.
    */
   public void printStackTrace(PrintStream out) {
      
      this.printStackTrace(out);
   }
   
}
