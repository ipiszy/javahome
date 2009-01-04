package Absyn;

import Symbol.Symbol;

public class DecList {
	public Dec head;
	public DecList tail;

	private DecList(Dec t) {
		this.head = t;
		this.tail = null;
	}

	public DecList(Dec t, DecList h) {
		if (h == null) {
			this.head = t;
			this.tail = null;
		} else {
			head = h.head;
			if (h.tail == null)
				tail = new DecList(t);
			else {
				tail = h.tail;
				while (h.tail != null)
					h = h.tail;
				h.tail = new DecList(t);
			}
		}
	}
}
