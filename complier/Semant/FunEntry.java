package Semant;

import Types.*;

class FunEntry extends Entry {
	RECORD formals;
	Type result;

	public FunEntry(RECORD formals, Type result) {
		this.formals = formals;
		this.result = result;
	}

	public String toString() {
		return formals.toString() + " " + result.toString();
	}

	public void setFormals(RECORD formals) {
		this.formals = formals;
	}

	public void setResult(Type result) {
		this.result = result;
	}
	
}
