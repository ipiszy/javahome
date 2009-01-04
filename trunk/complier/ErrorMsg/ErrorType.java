package ErrorMsg;

public class ErrorType {
	public static final int E_ENDCOMMENT = 0;
	public static final int E_STARTCOMMENT = 1;
	public static final int E_UNCLOSEDSTR = 2;
	public static final int E_ILLEGALCHAR = 3;
	public static final int E_ASSERTIONFAIL = 4;
	public static final int E_BADINPUTINI = 5;
	public static final int E_FATAL = 6;
	public static final int E_UNMATCHED = 7;
	public static final int E_ILLEGALESCAPECHAR = 8;
	public static final int E_INT = 9;
	public static final int E_UNDEFVAR = 10;
	public static final int E_VARTYPEUNMATCH = 11;
	public static final int E_TYPEDECLAR = 12;
	public static final int E_DECLAR = 13;
	public static final int E_TYPEMISSED = 14;
	public static final int E_UNDEFTYPE = 15;
	public static final int E_FUNTYPEUNMATCH = 16;
	public static final int E_EXP = 17;
	public static final int E_LVALUE = 18;
	public static final int E_UNDEFREC = 19;
	public static final int E_UNDEFARR = 20;
	public static final int E_ASSIGNUNMATCH = 21;
	public static final int E_ARRAYSIZEUNMATCH = 22;
	public static final int E_ARRAYTYPEUNMATCH = 23;
	public static final int E_RECORDFIELDUNMATCH = 24;
	public static final int E_UNDEFFUNC = 25;
	public static final int E_FUNCFORMALSUNMATCH = 26;
	public static final int E_TESTEXPTYPEUNMATCH = 27;
	public static final int E_THENELSETYPEUNMATCH = 28;
	public static final int E_THENNORETURN = 29;
	public static final int E_WHILEEXPTYPEUNMATCH = 30;
	public static final int E_DONORETURN = 31;
	public static final int E_FORINITTYPEUNMATCH = 32;
	public static final int E_FORBOUNDTYPEUNMATCH = 33;
	public static final int E_BREAKLOCALFALSE = 34;
	public static final int E_OPEXPINTSTRING = 35;
	public static final int E_OPEXPUNMATCH = 36;
	public static final int E_OPTYPEUNMATCH = 37;
	public static final int E_TYPERECUNDEF = 38;
	public static final int E_DUPLICATEDEF = 39;
	public static final int E_SYNAXERROR = 40;
	public static final int E_FORVARASSIGN = 41;
	public static final int E_OPEQVALUE = 42;
	
	public static final String errorMsgArray[] = {
	"Error: Unmatched end-of-comment punctuation.",
	"Error: Unmatched start-of-comment punctuation.",
	"Error: Unclosed string.",
	"Error: Illegal character.",
	"Error: Assertion failed.",
	"Error: Bad input stream initializer.",
	"Fatal Error.",
	"Lexical Error: Unmatched Input.",
	"Error: Illegal escape character.",
	"Error: Integer required.",
	"Error: Undefined variable.",
	"Error: Variable declaration type unmatched.",
	"Error: Type declaration.",
	"Error: Declaration.",
	"Error: Type missed.",
	"Error: Undefined TYPE.",
	"Error: Function return type unmatched.",
	"Error: Expression.",
	"Error: L-value.",
	"Error: Undefined RECORD.",
	"Error: Undefined ARRAY.",
	"Error: Assign unmatched.",
	"Error: Array size type unmatched.",
	"Error: Array type unmatched.",
	"Error: Record fields unmatched.",
	"Error: Undefined FUNCTION.",
	"Error: Function formals unmatched.",
	"Error: TEST expression return not INT.",
	"Error: THEN and ELSE clauses return type unmatched.",
	"Error: THEN clause should return no value.",
	"Error: Whle expression return not INT.",
	"Error: DO clause should return no value.",
	"Error: For init expression return not INT.",
	"Error: For bound expression return not INT.",
	"Error: Break expression must appear in a loop.",
	"Error: Operator Expression needs both INT or STRING,",
	"Error: Operator Expression needs type matched.",
	"Error: Operator type unmatched.",
	"Error: Type recursive or undefined.",
	"Error: Duplicate defined type or function.",
	"Error: Synatx Error.",
	"Error: For variable cannot be assigned to.",
	"Error: Oprands should not be valueless."
	};
}
