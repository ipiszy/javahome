package Parse;
import java.io.*;

import ErrorMsg.ErrorMsg;
import ErrorMsg.ErrorType;
import Utility.*;


public class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
 
	StringBuffer strBuffer;
	int yycharBegin; 
	int yylineBegin;
	private ErrorMsg errorMsg;
	private int comment_count = 0;
	private boolean stringState = false;
	private void newline() {
		errorMsg.newline(yychar + 1);
	}
	private void err(int pos, String errorText, int errorType) {
		errorMsg.error(pos,errorText, errorType);
		if (errorType == ErrorType.E_ENDCOMMENT)
			comment_count = 0;
		if (errorType == ErrorType.E_UNCLOSEDSTR)
			stringState = false;
	}
	private java_cup.runtime.Symbol tok(int kind, Object value) {
    	return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
	}
	Yylex(java.io.InputStream s, ErrorMsg e) {
		this(s);
  		errorMsg=e;
	}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYSTRING = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int YYSTRINGSLASH = 4;
	private final int YYSTRINGCONTROL = 3;
	private final int yy_state_dtrans[] = {
		0,
		72,
		53,
		79,
		81
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"44:8,41:2,43,44:2,42,44:18,1,49,45,49:3,19,49,5,6,14,12,2,13,11,15,46:10,3," +
"4,17,16,18,49,54,50:26,7,52,8,53,48,51,24,27,35,30,28,33,47,37,22,47,29,23," +
"47,21,31,38,47,25,32,36,34,39,40,47,26,47,9,20,10,51,44,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,120,
"0,1,2,1,3,1:11,4,1,5,6,1:2,7,2,1:2,8,1:5,9:5,1,9:12,1:3,10,1:12,11,12,13,1," +
"14:2,15,16,17,18,19,20,21,22,23,24,25,26,9,14,27,28,29,30,31,32,33,34,35,36" +
",37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,9,55,56,57,58,59")[0];

	private int yy_nxt[][] = unpackFromString(60,55,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,67,110,113,114:2,1" +
"15,116,114,73,78,114,117,114:2,80,114:2,118,119,23,66,-1,24,25,26,114,24:2," +
"114,24:4,-1:56,23,-1:39,23,-1:29,27,-1:52,28,-1:56,29,-1,30,-1:52,31,-1:59," +
"114,82,114:18,-1:5,84,114,84,-1,114,-1:50,26,-1:29,114:20,-1:5,84,114,84,-1" +
",114,-1:4,1,69:7,75:3,69:9,75:21,54,77,-1,54,55,69,75:2,69:2,75,56,75,69,-1" +
":43,37,-1:32,32,114:11,33,114:7,-1:5,84,114,84,-1,114,-1:19,51,-1:40,85,-1:" +
"39,85:3,-1:8,64,-1:2,1,50:13,68,74,50:26,66,-1,50:11,-1:21,114:10,34,114:9," +
"-1:5,84,114,84,-1,114,-1:18,52,-1:48,75:3,-1:9,75:21,-1:6,75:2,-1:2,75,-1,7" +
"5,-1:47,87,-1:51,57,-1:32,114:12,35,114:7,-1:5,84,114,84,-1,114,-1:4,1,54:6" +
",58:2,54:33,77,-1,54:4,58,54,58,54,58:3,-1:21,114:5,94,114:4,36,114:5,111,1" +
"14:3,-1:5,84,114,84,-1,114,-1:4,1,70,54:19,59,54:14,60,54:4,70,83,85,54,61," +
"76,54:5,62,63,54,-1:21,114:2,38,114:17,-1:5,84,114,84,-1,114,-1:5,85,-1:39," +
"85:2,71,-1:8,64,-1:23,114:15,39,114:4,-1:5,84,114,84,-1,114,-1:50,65,-1:29," +
"114:4,97,114:15,-1:5,84,114,84,-1,114,-1:25,114:7,112,114:12,-1:5,84,114,84" +
",-1,114,-1:25,114:9,40,114:10,-1:5,84,114,84,-1,114,-1:25,114:11,98,114:8,-" +
"1:5,84,114,84,-1,114,-1:25,114:4,41,114:15,-1:5,84,114,84,-1,114,-1:25,99,1" +
"14:19,-1:5,84,114,84,-1,114,-1:25,114:17,100,114:2,-1:5,84,114,84,-1,114,-1" +
":25,114:4,42,114:15,-1:5,84,114,84,-1,114,-1:25,114,102,114:18,-1:5,84,114," +
"84,-1,114,-1:25,114:3,103,114:16,-1:5,84,114,84,-1,114,-1:25,114:7,43,114:1" +
"2,-1:5,84,114,84,-1,114,-1:25,114:14,105,114:5,-1:5,84,114,84,-1,114,-1:25," +
"114:7,44,114:12,-1:5,84,114,84,-1,114,-1:25,45,114:19,-1:5,84,114,84,-1,114" +
",-1:25,114:2,106,114:17,-1:5,84,114,84,-1,114,-1:25,114:5,46,114:14,-1:5,84" +
",114,84,-1,114,-1:25,114:8,47,114:11,-1:5,84,114,84,-1,114,-1:25,114:15,107" +
",114:4,-1:5,84,114,84,-1,114,-1:25,114:7,48,114:12,-1:5,84,114,84,-1,114,-1" +
":25,114,108,114:18,-1:5,84,114,84,-1,114,-1:25,114:10,109,114:9,-1:5,84,114" +
",84,-1,114,-1:25,49,114:19,-1:5,84,114,84,-1,114,-1:25,114:7,86,114:12,-1:5" +
",84,114,84,-1,114,-1:25,114:7,101,114:12,-1:5,84,114,84,-1,114,-1:25,114:3," +
"104,114:16,-1:5,84,114,84,-1,114,-1:25,114:4,88,114:15,-1:5,84,114,84,-1,11" +
"4,-1:25,114:4,89,114:15,-1:5,84,114,84,-1,114,-1:25,90,114,91,114:17,-1:5,8" +
"4,114,84,-1,114,-1:25,114:10,92,114:2,93,114:6,-1:5,84,114,84,-1,114,-1:25," +
"114:3,95,114:16,-1:5,84,114,84,-1,114,-1:25,114:16,96,114:3,-1:5,84,114,84," +
"-1,114,-1:4");

	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	{
	 	if (stringState == true)
	 		err(yychar,strBuffer.toString(),ErrorType.E_UNCLOSEDSTR);
	 	if (comment_count != 0)
	 		err(yychar,yytext(), ErrorType.E_ENDCOMMENT);
	 	return tok(sym.EOF, null);
    }
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{ return tok(sym.COMMA, null);}
					case -4:
						break;
					case 4:
						{ return tok(sym.COLON, null); }
					case -5:
						break;
					case 5:
						{ return tok(sym.SEMICOLON, null); }
					case -6:
						break;
					case 6:
						{ return tok(sym.LPAREN, null); }
					case -7:
						break;
					case 7:
						{ return tok(sym.RPAREN, null); }
					case -8:
						break;
					case 8:
						{ return tok(sym.LBRACK, null); }
					case -9:
						break;
					case 9:
						{ return tok(sym.RBRACK, null); }
					case -10:
						break;
					case 10:
						{ return tok(sym.LBRACE, null); }
					case -11:
						break;
					case 11:
						{ return tok(sym.RBRACE, null); }
					case -12:
						break;
					case 12:
						{ return tok(sym.DOT, null); }
					case -13:
						break;
					case 13:
						{ return tok(sym.PLUS, null); }
					case -14:
						break;
					case 14:
						{ return tok(sym.MINUS, null); }
					case -15:
						break;
					case 15:
						{ return tok(sym.TIMES, null); }
					case -16:
						break;
					case 16:
						{ return tok(sym.DIVIDE, null); }
					case -17:
						break;
					case 17:
						{ return tok(sym.EQ, null); }
					case -18:
						break;
					case 18:
						{ return tok(sym.LT, null); }
					case -19:
						break;
					case 19:
						{ return tok(sym.GT, null); }
					case -20:
						break;
					case 20:
						{ return tok(sym.AND, null); }
					case -21:
						break;
					case 21:
						{ return tok(sym.OR, null); }
					case -22:
						break;
					case 22:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -23:
						break;
					case 23:
						{ }
					case -24:
						break;
					case 24:
						{
	err(yychar,yytext(),ErrorType.E_ILLEGALCHAR);
}
					case -25:
						break;
					case 25:
						{
	yybegin(YYSTRING);
	stringState = true; 
	strBuffer = new StringBuffer(); 
	yycharBegin = yychar+1;
	yylineBegin = yyline; 
}
					case -26:
						break;
					case 26:
						{ 
	return (new java_cup.runtime.Symbol(sym.INT,yychar,yychar + yytext().length(),yytext()));
}
					case -27:
						break;
					case 27:
						{ return tok(sym.ASSIGN, null); }
					case -28:
						break;
					case 28:
						{ yybegin(COMMENT); comment_count = comment_count + 1; }
					case -29:
						break;
					case 29:
						{ return tok(sym.LE, null); }
					case -30:
						break;
					case 30:
						{ return tok(sym.NEQ, null); }
					case -31:
						break;
					case 31:
						{ return tok(sym.GE, null); }
					case -32:
						break;
					case 32:
						{ return tok(sym.IN, null); }
					case -33:
						break;
					case 33:
						{ return tok(sym.IF, null); }
					case -34:
						break;
					case 34:
						{ return tok(sym.DO, null); }
					case -35:
						break;
					case 35:
						{ return tok(sym.OF, null); }
					case -36:
						break;
					case 36:
						{ return tok(sym.TO, null); }
					case -37:
						break;
					case 37:
						{ newline(); }
					case -38:
						break;
					case 38:
						{ return tok(sym.NIL, null); }
					case -39:
						break;
					case 39:
						{ return tok(sym.LET, null); }
					case -40:
						break;
					case 40:
						{ return tok(sym.END, null); }
					case -41:
						break;
					case 41:
						{ return tok(sym.FOR, null); }
					case -42:
						break;
					case 42:
						{ return tok(sym.VAR, null); }
					case -43:
						break;
					case 43:
						{ return tok(sym.ELSE, null); }
					case -44:
						break;
					case 44:
						{ return tok(sym.TYPE, null); }
					case -45:
						break;
					case 45:
						{ return tok(sym.THEN, null); }
					case -46:
						break;
					case 46:
						{ return tok(sym.ARRAY, null); }
					case -47:
						break;
					case 47:
						{ return tok(sym.BREAK, null); }
					case -48:
						break;
					case 48:
						{ return tok(sym.WHILE, null); }
					case -49:
						break;
					case 49:
						{ return tok(sym.FUNCTION, null); }
					case -50:
						break;
					case 50:
						{ }
					case -51:
						break;
					case 51:
						{ 
	comment_count = comment_count - 1;
	if (comment_count == 0) {
    		yybegin(YYINITIAL);
	}
}
					case -52:
						break;
					case 52:
						{ comment_count = comment_count + 1; }
					case -53:
						break;
					case 53:
						{strBuffer.append(yytext());}
					case -54:
						break;
					case 54:
						{
	yybegin(YYSTRING);
	err(yychar,yytext(),ErrorType.E_ILLEGALESCAPECHAR);
}
					case -55:
						break;
					case 55:
						{
	yybegin(YYINITIAL);
	stringState = false; 
	return (new java_cup.runtime.Symbol(sym.STRING, yycharBegin, yycharBegin+strBuffer.length(), strBuffer.toString()));
}
					case -56:
						break;
					case 56:
						{yybegin(YYSTRINGSLASH); }
					case -57:
						break;
					case 57:
						{
	yybegin(YYINITIAL);
	err(yychar,strBuffer.toString(),ErrorType.E_UNCLOSEDSTR);
	errorMsg.newline(yychar + 1);
}
					case -58:
						break;
					case 58:
						{
	yybegin(YYSTRING);
	char charTemp = yytext().charAt(0);
    int intTemp = charTemp - 64;
	strBuffer.append((char)intTemp);
}
					case -59:
						break;
					case 59:
						{strBuffer.append("\n"); yybegin(YYSTRING);}
					case -60:
						break;
					case 60:
						{strBuffer.append("\t"); yybegin(YYSTRING);}
					case -61:
						break;
					case 61:
						{strBuffer.append("\""); yybegin(YYSTRING);}
					case -62:
						break;
					case 62:
						{strBuffer.append("\\"); yybegin(YYSTRING);}
					case -63:
						break;
					case 63:
						{
    yybegin(YYSTRINGCONTROL);
}
					case -64:
						break;
					case 64:
						{
	yybegin(YYSTRING);
	String str = yytext();
	int count = yychar - 1;
	int i = 0;
	System.out.println ("WHITE");
	for (;i<str.length();i++){
		count++;
		if ((str.charAt(i) == '\r') && (str.charAt(i+1) == '\n'))
			errorMsg.newline(count + 1);
	}
}
					case -65:
						break;
					case 65:
						{
	yybegin(YYSTRING);
	String str = yytext();
	int intTemp = Integer.parseInt(str);
	if ((intTemp < 0 ) || (intTemp >127))
		err(yychar,yytext(),ErrorType.E_ILLEGALESCAPECHAR);
	else{ 
		char charTemp = (char)intTemp; 
		strBuffer.append(charTemp);
	}
}
					case -66:
						break;
					case 67:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -67:
						break;
					case 68:
						{ }
					case -68:
						break;
					case 69:
						{strBuffer.append(yytext());}
					case -69:
						break;
					case 70:
						{
	yybegin(YYSTRING);
	err(yychar,yytext(),ErrorType.E_ILLEGALESCAPECHAR);
}
					case -70:
						break;
					case 71:
						{
	yybegin(YYINITIAL);
	err(yychar,strBuffer.toString(),ErrorType.E_UNCLOSEDSTR);
	errorMsg.newline(yychar + 1);
}
					case -71:
						break;
					case 73:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -72:
						break;
					case 74:
						{ }
					case -73:
						break;
					case 75:
						{strBuffer.append(yytext());}
					case -74:
						break;
					case 76:
						{
	yybegin(YYSTRING);
	err(yychar,yytext(),ErrorType.E_ILLEGALESCAPECHAR);
}
					case -75:
						break;
					case 78:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -76:
						break;
					case 80:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -77:
						break;
					case 82:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -78:
						break;
					case 84:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -79:
						break;
					case 86:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -80:
						break;
					case 88:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -81:
						break;
					case 89:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -82:
						break;
					case 90:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -83:
						break;
					case 91:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -84:
						break;
					case 92:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -85:
						break;
					case 93:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -86:
						break;
					case 94:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -87:
						break;
					case 95:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -88:
						break;
					case 96:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -89:
						break;
					case 97:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -90:
						break;
					case 98:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -91:
						break;
					case 99:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -92:
						break;
					case 100:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -93:
						break;
					case 101:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -94:
						break;
					case 102:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -95:
						break;
					case 103:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -96:
						break;
					case 104:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -97:
						break;
					case 105:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -98:
						break;
					case 106:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -99:
						break;
					case 107:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -100:
						break;
					case 108:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -101:
						break;
					case 109:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -102:
						break;
					case 110:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -103:
						break;
					case 111:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -104:
						break;
					case 112:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -105:
						break;
					case 113:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -106:
						break;
					case 114:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -107:
						break;
					case 115:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -108:
						break;
					case 116:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -109:
						break;
					case 117:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -110:
						break;
					case 118:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -111:
						break;
					case 119:
						{
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}
					case -112:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
