package ezbake.glitch;

import java.io.PrintStream;

/**
 * <p>
 * This interface identifies a class as an exception that may be quarantined
 * via a {@link ezbake.glitch.CoreExceptionHandler} class.
 * In many cases a class will extend a {@link java.lang.Throwable} class or
 * subclass while implementing this interface. For example:
 * <br><br>
 * <code>
 * public class SomeProjectException extends Exception implements CoreException { <br>
 * ... <br>
 * } <br>
 * </code>
 * </p>
 * <p>
 * The {@link Configuration} establishes which {@link CoreException} classes are
 * associated with which {@link CoreExceptionHandler} handlers.
 * </p>
 */
public interface CoreException {

   /**
    * <p>
    * Returns the detailed message that describes the error.
    * </p>
    * 
    * @return A detailed message describing the error.
    */
   String getMessage();
   
   /**
    * <p>
    * Returns the actual {@link java.lang.Throwable} exception instance that
    * was thrown. If there is not a Throwable instance then null is returned.
    * </p>
    * 
    * @return The actual {@link java.lang.Throwable} exception instance that 
    *       was thrown or null if this class is not a Throwable.
    */
   Throwable getException();
   
   /**
    * <p>
    * Writes the stack trace associated with this exception to the given output
    * stream.
    * </p>
    * 
    * @param out The output stream to which this stack trace is written.
    */
   void printStackTrace(PrintStream out);
   
}
