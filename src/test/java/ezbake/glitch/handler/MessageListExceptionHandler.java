package ezbake.glitch.handler;

import java.util.ArrayList;
import java.util.List;

import ezbake.glitch.CoreException;


/**
 * <p>
 * An exception handler used specifically for unit testing.
 * </p>
 */
public class MessageListExceptionHandler {

   private List<String> messages = new ArrayList<String>();
   
   public void handler(CoreException e) {
      
      messages.add(e.getMessage());
   }
   
   public List<String> getMessages() {
      
      return messages;
   }
}
