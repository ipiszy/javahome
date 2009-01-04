package Absyn;
import Symbol.Symbol;
public class FieldExpList extends Absyn {
   public Symbol name;
   public Exp init;
   public FieldExpList tail;
      
   private FieldExpList(int p, Symbol n, Exp i) {
	   this.pos = p;
	   this.name = n;
	   this.init = i;
	   this.tail = null;
   }

   public FieldExpList(int p, Symbol n, Exp i, FieldExpList x) {
		if (x == null) {
			this.pos = p;
			this.name = n;
			this.init = i;
			this.tail = null;
		} else {
			this.pos = x.pos;
			this.name = x.name;
			this.init = x.init;

			if (x.tail == null)
				this.tail = new FieldExpList(p,n,i);
			else {
				tail = x.tail;
				while (x.tail != null)
					x = x.tail;
				x.tail = new FieldExpList(p,n,i);
			}
		}
	}
}
