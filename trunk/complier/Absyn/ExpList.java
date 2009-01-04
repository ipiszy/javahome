package Absyn;
import Symbol.Symbol;
public class ExpList {
   public Exp head;
   public ExpList tail;
      
   private ExpList(Exp t) {
		this.head = t;
		this.tail = null;
	}

	public ExpList(Exp t, ExpList h) {
		if (h == null) {
			this.head = t;
			this.tail = null;
		} else {
			head = h.head;
			if (h.tail == null)
				tail = new ExpList(t);
			else {
				tail = h.tail;
				while (h.tail != null)
					h = h.tail;
				h.tail = new ExpList(t);
			}
		}
	}
   
}
