package ezbake.glitch.handler;

import ezbake.glitch.CoreException;
import ezbake.glitch.CoreExceptionHandler;


/**
 * <p>
 * An exception handler used specifically for unit testing.
 * </p>
 */
public class CounterHandler implements CoreExceptionHandler {

   private int counter = 0;
   
   public void handle(CoreException e) {
      
      counter++;
   }
   
   public int getCount() {
      
      return counter;
   }
}
