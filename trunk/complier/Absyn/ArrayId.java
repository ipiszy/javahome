package Absyn;

public class ArrayId {
	public String arrayId;
	public Exp exp;
	public int pos;
	
	public ArrayId(int p, Exp e, String id){
		pos = p;
		exp = e;
		arrayId = id;
	}

}
