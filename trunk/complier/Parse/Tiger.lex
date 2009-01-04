package Parse;
import java.io.*;
import Utility.*;

%% 

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%line
%char
%notunix
%public

%{ 
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
	
%}

%eofval{
	{
	 	if (stringState == true)
	 		err(yychar,strBuffer.toString(),ErrorType.E_UNCLOSEDSTR);
	 	if (comment_count != 0)
	 		err(yychar,yytext(), ErrorType.E_ENDCOMMENT);
	 		
	 	return tok(sym.EOF, null);
    }
%eofval}       

%state COMMENT, YYSTRING, YYSTRINGCONTROL, YYSTRINGSLASH

ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b]
WHITE_SPACE_CHAR=[\n\ \t\b\r]
PRINTCHAR=[\040-\041]|[\043-\133]|[\135-\176]
NEWLINE_CHAR=\r\n
COMMENT_START=/\*
COMMENT_END=\*/
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
CONTROL_C=[\100-\137]

%%
<YYINITIAL> " "	{}
<YYINITIAL> ","	{ return tok(sym.COMMA, null);}
<YYINITIAL> ":" { return tok(sym.COLON, null); }
<YYINITIAL> ";" { return tok(sym.SEMICOLON, null); }
<YYINITIAL> "(" { return tok(sym.LPAREN, null); }
<YYINITIAL> ")" { return tok(sym.RPAREN, null); }
<YYINITIAL> "[" { return tok(sym.LBRACK, null); }
<YYINITIAL> "]" { return tok(sym.RBRACK, null); }
<YYINITIAL> "{" { return tok(sym.LBRACE, null); }
<YYINITIAL> "}" { return tok(sym.RBRACE, null); }
<YYINITIAL> "." { return tok(sym.DOT, null); }
<YYINITIAL> "+" { return tok(sym.PLUS, null); }
<YYINITIAL> "-" { return tok(sym.MINUS, null); }
<YYINITIAL> "*" { return tok(sym.TIMES, null); }
<YYINITIAL> "/" { return tok(sym.DIVIDE, null); }
<YYINITIAL> "=" { return tok(sym.EQ, null); }
<YYINITIAL> "<>" { return tok(sym.NEQ, null); }
<YYINITIAL> "<"  { return tok(sym.LT, null); }
<YYINITIAL> "<=" { return tok(sym.LE, null); }
<YYINITIAL> ">"  { return tok(sym.GT, null); }
<YYINITIAL> ">=" { return tok(sym.GE, null); }
<YYINITIAL> "&"  { return tok(sym.AND, null); }
<YYINITIAL> "|"  { return tok(sym.OR, null); }
<YYINITIAL> ":=" { return tok(sym.ASSIGN, null); }
<YYINITIAL> "nil" { return tok(sym.NIL, null); }
<YYINITIAL> "array" { return tok(sym.ARRAY, null); }
<YYINITIAL> "break" { return tok(sym.BREAK, null); }
<YYINITIAL> "do" { return tok(sym.DO, null); }
<YYINITIAL> "else" { return tok(sym.ELSE, null); }
<YYINITIAL> "end" { return tok(sym.END, null); }
<YYINITIAL> "for" { return tok(sym.FOR, null); }
<YYINITIAL> "function" { return tok(sym.FUNCTION, null); }
<YYINITIAL> "if" { return tok(sym.IF, null); }
<YYINITIAL> "in" { return tok(sym.IN, null); }
<YYINITIAL> "let" { return tok(sym.LET, null); }
<YYINITIAL> "of" { return tok(sym.OF, null); }
<YYINITIAL> "then" { return tok(sym.THEN, null); }
<YYINITIAL> "to" { return tok(sym.TO, null); }
<YYINITIAL> "type" { return tok(sym.TYPE, null); }
<YYINITIAL> "var" { return tok(sym.VAR, null); }
<YYINITIAL> "while" { return tok(sym.WHILE, null); }

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

<YYINITIAL,COMMENT> \r\n { newline(); }

<YYINITIAL> "/*" { yybegin(COMMENT); comment_count = comment_count + 1; }

<COMMENT> "/*" { comment_count = comment_count + 1; }
<COMMENT> "*/" { 
	comment_count = comment_count - 1;
	if (comment_count == 0) {
    		yybegin(YYINITIAL);
	}
}
<COMMENT> . { }

<YYINITIAL> \" {
	yybegin(YYSTRING);
	stringState = true; 
	strBuffer = new StringBuffer(); 
	yycharBegin = yychar+1;
	yylineBegin = yyline; 
} 
<YYINITIAL> {DIGIT}+ { 
	return (new java_cup.runtime.Symbol(sym.INT,yychar,yychar + yytext().length(),yytext()));
}	
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|_)* {
	return (new java_cup.runtime.Symbol(sym.ID,yychar,yychar + yytext().length(),yytext()));
}	
<YYINITIAL> . {
	err(yychar,yytext(),ErrorType.E_ILLEGALCHAR);
}

<YYSTRING> {PRINTCHAR}* {strBuffer.append(yytext());}
<YYSTRING> " " {strBuffer.append(yytext());}
<YYSTRING> \\ {yybegin(YYSTRINGSLASH); }
<YYSTRING> \" {
	yybegin(YYINITIAL);
	stringState = false; 
	return (new java_cup.runtime.Symbol(sym.STRING, yycharBegin, yycharBegin+strBuffer.length(), strBuffer.toString()));
}


<YYSTRINGSLASH> {WHITE_SPACE_CHAR}+\\ {
	yybegin(YYSTRING);
	String str = yytext();
	int count = yychar - 1;
	int i = 0;
	for (;i<str.length();i++){
		count++;
		if ((str.charAt(i) == '\r') && (str.charAt(i+1) == '\n'))
			errorMsg.newline(count + 1);
	}
}

<YYSTRINGSLASH> "n" {strBuffer.append("\n"); yybegin(YYSTRING);}
<YYSTRINGSLASH> "t" {strBuffer.append("\t"); yybegin(YYSTRING);}
<YYSTRINGSLASH> "\"" {strBuffer.append("\""); yybegin(YYSTRING);}
<YYSTRINGSLASH> \\ {strBuffer.append("\\"); yybegin(YYSTRING);}
<YYSTRINGSLASH> "^" {
    yybegin(YYSTRINGCONTROL);
}

<YYSTRINGSLASH> {DIGIT}{DIGIT}{DIGIT} {
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

<YYSTRINGCONTROL> {CONTROL_C} {
	yybegin(YYSTRING);
	char charTemp = yytext().charAt(0);
    int intTemp = charTemp - 64;
	strBuffer.append((char)intTemp);
}

<YYSTRING, YYSTRINGCONTROL, YYSTRINGSLASH> . {
	yybegin(YYSTRING);
	err(yychar,yytext(),ErrorType.E_ILLEGALESCAPECHAR);
}

<YYSTRING, YYSTRINGSLASH, YYSTRINGCONTROL> {NEWLINE_CHAR} {
	yybegin(YYINITIAL);
	err(yychar,strBuffer.toString(),ErrorType.E_UNCLOSEDSTR);
	errorMsg.newline(yychar + 1);
}

