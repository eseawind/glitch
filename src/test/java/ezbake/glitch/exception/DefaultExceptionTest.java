package ezbake.glitch.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * Tests to validate the {@link ezbake.glitch.exception.DefaultException}
 * class.
 * </p>
 */
public class DefaultExceptionTest  {

   /**
    * getException
    * <p>
    * Validates that "this" instance is returned.
    * </p>
    */
   @Test
   public void getExceptionReturnsThis() {
      
      DefaultException exception = new DefaultException("Message Goes Here");
      Assert.assertEquals(exception, exception.getException());
   }
   
   /**
    * getMessage
    * <p>
    * Validates that the message is returned.
    * </p>
    */
   @Test
   public void getMessage() {
      
      String message = "Message Goes Here";
      DefaultException exception = new DefaultException(message);
      Assert.assertEquals(message, exception.getMessage());
   }
}
