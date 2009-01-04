package Types;

public class NAME extends Type {
	public Symbol.Symbol name;
	private Type binding;

	public NAME(Symbol.Symbol n) {
		name = n;
	}

	public boolean isLoop() {
		Type b = binding;
		boolean any;
		binding = null;
		if (b == null)
			any = true;
		else if (b instanceof NAME)
			any = ((NAME) b).isLoop();
		else
			any = false;
		binding = b;
		return any;
	}

	public Type actual() {
		Type type = binding.actual();
		return type;
	}

	public boolean coerceTo(Type t) {
		if (t instanceof NAME)
			return this.actual() == t.actual();
		else
			return this.actual().coerceTo(t);
	}
	
	public boolean matches(Type t){
		if (t instanceof NAME)
			return this.actual() == t.actual();
		else
			return this.actual().matches(t);
	}

	public void bind(Type t) {
		binding = t;
	}

	public String toString() {
		return "NAME";
	}
}
