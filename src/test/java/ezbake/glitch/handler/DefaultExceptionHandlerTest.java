package ezbake.glitch.handler;

import org.junit.Assert;
import org.junit.Test;

import ezbake.glitch.exception.DefaultException;

/**
 * <p>
 * Tests to validate the {@link ezbake.glitch.exception.DefaultException}
 * class.
 * </p>
 */
public class DefaultExceptionHandlerTest {

   /**
    * handle
    * <p>
    * Validates that the handle method prints the stack trace to System.out.
    * </p>
    * <p>
    * Note: This doesn't validate anything because it goes to System.out.
    * </p>
    */
   @Test
   public void getExceptionReturnsThis() {
      
      DefaultException exception = new DefaultException("Message Goes Here");
      Assert.assertEquals(exception, exception.getException());
      
      try {
         throwDefaultException("Message Goes Here");
      } catch (DefaultException e) {
         new DefaultExceptionHandler().handle(e);
      }
   }
   
   /**
    * 
    * @param message
    * @throws DefaultException
    */
   private void throwDefaultException(String message)
   throws DefaultException {
      
      throw new DefaultException(message);
   }
}
