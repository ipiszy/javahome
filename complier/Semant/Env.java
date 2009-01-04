package Semant;

import Symbol.Symbol;
import Symbol.Table;
import Types.INT;
import Types.RECORD;
import Types.STRING;
import Types.Type;
import Types.VOID;

class Env {
	Table venv; // value environment;
	Table tenv; // type environment;
	ErrorMsg.ErrorMsg errorMsg;

	Env(ErrorMsg.ErrorMsg err) {
		venv = new Table();
		tenv = new Table();
		this.errorMsg = err;
		STRING STRING = new STRING();
		VOID VOID = new VOID();
		INT INT = new INT();

		tenv.beginScope();
		venv.beginScope();
		tenv.put(Symbol.symbol("int"), INT);
		tenv.put(Symbol.symbol("string"), STRING);
		
		transFunc(Symbol.symbol("print"), new RECORD(Symbol.symbol("s"),
				STRING, null), VOID);
		transFunc(Symbol.symbol("printi"), new RECORD(Symbol.symbol("i"), INT,
				null), VOID);
		transFunc(Symbol.symbol("flush"), null, VOID);
		transFunc(Symbol.symbol("getchar"), null, STRING);
		transFunc(Symbol.symbol("ord"), new RECORD(Symbol.symbol("s"), STRING,
				null), INT);
		transFunc(Symbol.symbol("chr"), new RECORD(Symbol.symbol("i"), INT,
				null), STRING);
		transFunc(Symbol.symbol("size"), new RECORD(Symbol.symbol("s"), STRING,
				null), INT);
		transFunc(Symbol.symbol("substring"), new RECORD(Symbol.symbol("s"),
				STRING, new RECORD(Symbol.symbol("i"), INT, new RECORD(Symbol
						.symbol("n"), INT, null))), STRING);
		transFunc(Symbol.symbol("concat"), new RECORD(Symbol.symbol("s1"),
				STRING, new RECORD(Symbol.symbol("s2"), STRING, null)), STRING);
		transFunc(Symbol.symbol("not"), new RECORD(Symbol.symbol("i"), INT,
				null), INT);
		transFunc(Symbol.symbol("exit"), new RECORD(Symbol.symbol("i"), INT,
				null), VOID);
	}

	void transFunc(Symbol name, RECORD formals, Type result) {
		venv.put(name, new FunEntry(formals, result));
	}
}
