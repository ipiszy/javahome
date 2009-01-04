package Types;

public class ARRAY extends Type {
   public Type element;
   public ARRAY(Type e) {element = e;}
   public boolean coerceTo(Type t) {
	   return t.actual() instanceof ARRAY;
   }
   public String toString(){
	   return "ARRAY";
   }
   
   /*public boolean matches(Type t){
	   if (t.actual() instanceof ARRAY)
		   if (this.element.coerceTo(((ARRAY)(t.actual())).element.actual()))
			   return true;
	   return false;
   }*/
}

