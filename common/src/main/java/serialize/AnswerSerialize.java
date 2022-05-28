package serialize;

import java.io.Serializable;

public class AnswerSerialize implements Serializable {
   private String message;
   
   public AnswerSerialize(String message){
      this.message = message;
   }

   public String getMessage() {
      return message;
   }
}
