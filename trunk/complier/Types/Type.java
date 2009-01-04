package Types;

public abstract class Type {
   public Type actual() {return this;}
         
   public boolean coerceTo(Type t) {return false;}
   
   public boolean matches(Type t) {return coerceTo(t);} 
   
}

