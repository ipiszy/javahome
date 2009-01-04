package Absyn;
import Symbol.Symbol;
public class FieldList extends Absyn {
   public Symbol name;
   public Symbol typ;
   public FieldList tail;
   public boolean escape = true;

   private FieldList(int p, Symbol n, Symbol t) {
	   this.pos = p;
	   this.name = n;
	   this.typ = t;
	   this.tail = null;
   }

   public FieldList(int p, Symbol n, Symbol t, FieldList x) {
		if (x == null) {
			this.pos = p;
			this.name = n;
			this.typ = t;
			this.tail = null;
		} else {
			this.pos = x.pos;
			this.name = x.name;
			this.typ = x.typ;

			if (x.tail == null)
				this.tail = new FieldList(p,n,t);
			else {
				tail = x.tail;
				while (x.tail != null)
					x = x.tail;
				x.tail = new FieldList(p,n,t);
			}
		}
	}
  
}

