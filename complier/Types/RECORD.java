package Types;

public class RECORD extends Type {
	public Symbol.Symbol fieldName;
	public Type fieldType;
	public RECORD tail;

	public RECORD(Symbol.Symbol n, Type t, RECORD x) {
		fieldName = n;
		fieldType = t;
		tail = x;
	}

	public boolean coerceTo(Type t) {
		return (t.actual() instanceof RECORD);
	}

	public boolean matches(Type t) {
		NIL NIL = new NIL();
		RECORD RECORD = new RECORD(null,null,null);
		return ((t.actual().coerceTo(NIL)) || (t.actual().coerceTo(RECORD)));
	}

	public String toString() {
		return "RECORD";
	}
}
