package Semant;

import Absyn.*;
import Types.*;

class ExpTy {
	Exp exp;
	Type type;
	
	ExpTy(Exp exp, Type type){
		this.exp = exp;
		this.type = type;
	}

}
