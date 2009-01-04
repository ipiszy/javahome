package Semant;

import Types.*;

class VarEntry extends Entry {	
	Type ty;
	VarEntry(Type ty){
		this.ty = ty;
	}
	
	public String toString(){
		   return ty.toString();
	   }

}
