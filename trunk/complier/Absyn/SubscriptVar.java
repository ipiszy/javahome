package Absyn;
import Symbol.Symbol;
public class SubscriptVar extends Var {
   public Var var;
   public Exp index;
   public SubscriptVar(int p, Var v, Exp i) {pos=p; var=v; index=i;}
   public String toString(){
	   return var.toString()+"["+index+"]";
   }
}
