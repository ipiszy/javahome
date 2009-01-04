package ErrorMsg;

public class ErrorMsg {
	private LineList linePos = new LineList(-1, null);
	private int lineNum = 1;
	private String filename;
	public boolean anyErrors;

	public ErrorMsg(String f) {
		filename = f;
	}

	public void newline(int pos) {
		// System.out.println ("lineNum = " + lineNum + "\tpos = " + pos);
		lineNum++;
		linePos = new LineList(pos, linePos);
	}

	public void error(int pos, String errorText) {
		int n = lineNum;
		LineList p = linePos;
		String sayPos = "0.0";

		anyErrors = true;

		while (p != null) {
			if (p.head < pos) {
				sayPos = ":" + String.valueOf(n) + "."
						+ String.valueOf(pos - p.head);
				break;
			}
			p = p.tail;
			n--;
		}
	}

	public void error(int pos, String errorText, int msg) {
		int n = lineNum;
		LineList p = linePos;
		String sayPos = "0.0";

		anyErrors = true;

		while (p != null) {
			if (p.head < pos) {
				sayPos = ":" + String.valueOf(n) + "."
						+ String.valueOf(pos - p.head);
				break;
			}
			p = p.tail;
			n--;
		}

		// System.out.println("p.head = " + p.head + "\tpos = " + pos);
		System.err.println(filename + ":" + sayPos + ": " + errorText + " "
				+ ErrorType.errorMsgArray[msg]);
	}
}

class LineList {
	int head;
	LineList tail;

	LineList(int h, LineList t) {
		head = h;
		tail = t;
	}
}
