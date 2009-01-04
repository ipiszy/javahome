package Parse;

import ErrorMsg.ErrorMsg;
import Utility.*;

public class Main {

	public static void main(String argv[]) throws java.io.IOException {
		String fileName = "D:\\Java\\J\\complier\\input.txt";
		if(argv.length>0)
			fileName=argv[0];
		ErrorMsg errorMsg = new ErrorMsg(fileName);
		java.io.InputStream inp = new java.io.FileInputStream(fileName);
		
		Parse parser = new Parse(fileName);
		Absyn.Exp e = parser.parse(fileName);

		Semant.Semant se = new Semant.Semant(parser.errorMsg);
		se.transProg(e);

		inp.close();
	}

	
}
