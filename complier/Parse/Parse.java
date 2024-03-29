package Parse;

public class Parse {

	public ErrorMsg.ErrorMsg errorMsg;

	public Parse(String filename) {
		errorMsg = new ErrorMsg.ErrorMsg(filename);
	}

	public Absyn.Exp parse(String filename) {
		java.io.InputStream inp;
		try {
			inp = new java.io.FileInputStream(filename);
		} catch (java.io.FileNotFoundException e) {
			throw new Error("File not found: " + filename);
		}

		Grm parser = new Grm(new Yylex(inp, errorMsg), errorMsg);
		try {

			java_cup.runtime.Symbol sym = parser./* debug_ */parse();
			return (Absyn.Exp) sym.value;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Error(e.toString());
		} finally {
			try {
				inp.close();
			} catch (java.io.IOException e) {
			}
		}

	}
}
