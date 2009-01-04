package Semant;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import Absyn.ArrayExp;
import Absyn.ArrayTy;
import Absyn.AssignExp;
import Absyn.BreakExp;
import Absyn.CallExp;
import Absyn.Dec;
import Absyn.DecList;
import Absyn.ErrorExp;
import Absyn.Exp;
import Absyn.ExpList;
import Absyn.FieldExpList;
import Absyn.FieldList;
import Absyn.FieldVar;
import Absyn.ForExp;
import Absyn.FunctionDec;
import Absyn.IfExp;
import Absyn.IntExp;
import Absyn.LetExp;
import Absyn.NameTy;
import Absyn.NilExp;
import Absyn.OpExp;
import Absyn.Print;
import Absyn.RecordExp;
import Absyn.RecordTy;
import Absyn.SeqExp;
import Absyn.SimpleVar;
import Absyn.StringExp;
import Absyn.SubscriptVar;
import Absyn.Ty;
import Absyn.TypeDec;
import Absyn.Var;
import Absyn.VarDec;
import Absyn.VarExp;
import Absyn.WhileExp;
import ErrorMsg.ErrorMsg;
import ErrorMsg.ErrorType;
import Symbol.Symbol;
import Types.ARRAY;
import Types.INT;
import Types.NAME;
import Types.NIL;
import Types.RECORD;
import Types.STRING;
import Types.Type;
import Types.VOID;

public class Semant {
	Env env;
	int loop = 0;
	Set<String> forVar = new HashSet<String>();

	public Semant(ErrorMsg err) {
		env = new Env(err);
	}

	Type INT = new INT();
	Type STRING = new STRING();
	Type NIL = new NIL();
	Type ARRAY = new ARRAY(INT);
	Type RECORD = new RECORD(null, null, null);
	Type VOID = new VOID();

	void checkInt(ExpTy expTy, int pos) {
		if (!(expTy.type.coerceTo(INT))) {
			env.errorMsg.error(pos, "", ErrorType.E_INT);
		}
	}

	ExpTy transExp(OpExp exp) {
		//system.out.println("OpExp");
		ExpTy expLeft, expRight;
		expLeft = transExp(exp.left);
		expRight = transExp(exp.right);
		switch (exp.oper) {
		case OpExp.PLUS:
		case OpExp.MINUS:
		case OpExp.MUL:
		case OpExp.DIV:
			checkInt(expLeft, exp.left.pos);
			checkInt(expRight, exp.right.pos);
			return new ExpTy(null, INT);
		case OpExp.GE:
		case OpExp.GT:
		case OpExp.LE:
		case OpExp.LT:
			if ((expLeft.type.coerceTo(INT)) && (expRight.type.coerceTo(INT)))
				return new ExpTy(null, INT);
			if ((expLeft.type.coerceTo(STRING))
					&& (expRight.type.coerceTo(STRING)))
				return new ExpTy(null, INT);
			env.errorMsg.error(exp.pos, "", ErrorType.E_OPEXPINTSTRING);
			return new ExpTy(null, INT);
		case OpExp.EQ:
		case OpExp.NE:
			if (expLeft.type.matches(expRight.type)){
				if ((expLeft.type.coerceTo(NIL)) || (expLeft.type.coerceTo(VOID)))
					env.errorMsg.error(exp.pos, "", ErrorType.E_OPEQVALUE);
				return new ExpTy(null, INT);
			}
			env.errorMsg.error(exp.pos, "", ErrorType.E_OPEXPUNMATCH);
			return new ExpTy(null, INT);
		case OpExp.AND:
		case OpExp.OR:
			checkInt(expLeft, exp.left.pos);
			checkInt(expRight, exp.right.pos);
			return new ExpTy(null, INT);
		default:
			env.errorMsg.error(exp.pos, "", ErrorType.E_OPTYPEUNMATCH);
			return new ExpTy(null, INT);
		}
	}

	ExpTy transExp(LetExp exp) {
		//system.out.println("LetExp");
		int scope = 0;
		DecList preDec = null;
		Set<String> set = null;
		for (DecList decList = exp.decs; decList != null; decList = decList.tail) {
			if (preDec == null) {
				preDec = decList;
				scope++;
				env.tenv.beginScope();
				env.venv.beginScope();
				set = new HashSet<String>();
			}

			if (decList.head instanceof FunctionDec) {
				FunctionDec funDec = (FunctionDec) decList.head;
				if (set.contains(funDec.name.toString())) {
					env.errorMsg
							.error(funDec.pos, "", ErrorType.E_DUPLICATEDEF);
				} else {
					set.add(funDec.name.toString());
					Type result;
					RECORD formals = null;
					if (funDec.result == null) // define return type
						result = new VOID();
					else
						result = transTy(funDec.result);

					if (funDec.params != null)
						formals = transTypeFields(funDec.params);
					env.venv.put(funDec.name, new FunEntry(formals, result));
				}

				if ((decList.tail == null)
						|| (!(decList.tail.head instanceof FunctionDec))) {
					transDecBatch(preDec, decList.head);
					preDec = null;
				}

			} else if (decList.head instanceof TypeDec) {

				TypeDec typeDec = (TypeDec) decList.head;

				if (set.contains(typeDec.name.toString())) {
					env.errorMsg.error(typeDec.pos, "",
							ErrorType.E_DUPLICATEDEF);
				} else {
					set.add(typeDec.name.toString());
					env.tenv.put(typeDec.name, new NAME(typeDec.name));
				}

				if ((decList.tail == null)
						|| (!(decList.tail.head instanceof TypeDec))) {
					transDecBatch(preDec, decList.head);
					preDec = null;
				}
			} else {
				transDec(decList.head);
				preDec = null;
			}
		}

		for (DecList decList = exp.decs; decList != null; decList = decList.tail) {
			if (decList.head instanceof TypeDec) {
				TypeDec typeDec = (TypeDec) decList.head;
				NAME name = (NAME) env.tenv.get(typeDec.name);
				if (name.isLoop())
					env.errorMsg.error(typeDec.pos, "",
							ErrorType.E_TYPERECUNDEF);
			}
		}

		ExpTy expTy = transExp(exp.body);

		while (scope != 0) {
			env.tenv.endScope();
			env.venv.endScope();
			scope--;
		}

		return new ExpTy(null, expTy.type);
	}

	ExpTy transExp(IntExp exp) {
		//system.out.println("IntExp");
		return new ExpTy(null, INT);
	}

	ExpTy transExp(StringExp exp) {
		//system.out.println("StringExp");
		return new ExpTy(null, STRING);
	}

	ExpTy transExp(NilExp exp) {
		//system.out.println("NilExp");
		return new ExpTy(null, new NIL());
	}

	ExpTy transExp(VarExp exp) {
		//system.out.println("VarExp");
		return transVar(exp.var);
	}

	ExpTy transExp(AssignExp exp) {
		//system.out.println("AssignExp");
		if ((exp.var instanceof SimpleVar)
				&& (forVar.contains(((SimpleVar) (exp.var)).name.toString()))) {
			env.errorMsg.error(exp.var.pos, "", ErrorType.E_FORVARASSIGN);
			return new ExpTy(null, new VOID());
		}

		Type tyleft = transVar(exp.var).type;
		Type tyright = transExp(exp.exp).type;
		if (!(tyleft.matches(tyright)))
			env.errorMsg.error(exp.pos, "", ErrorType.E_ASSIGNUNMATCH);
		return new ExpTy(null, new VOID());
	}

	ExpTy transExp(SeqExp exp) {
		//system.out.println("SeqExp");
		ExpList expList = exp.list;
		ExpTy expTy = new ExpTy(null, new VOID());
		while (expList != null) {
			expTy = transExp(expList.head);
			expList = expList.tail;
		}
		return expTy;
	}

	ExpTy transExp(CallExp exp) {
		//system.out.println("CallExp");

		Object entry = env.venv.get(exp.func);
		if (entry instanceof FunEntry) {
			RECORD record = ((FunEntry) entry).formals;
			ExpList expList = exp.args;
			while ((record != null) && (expList != null)) {
				if (!(record.fieldType.coerceTo(transExp(expList.head).type))) {
					env.errorMsg.error(exp.pos, "",
							ErrorType.E_FUNCFORMALSUNMATCH);
					return new ExpTy(null, ((FunEntry) entry).result);
				}
				record = record.tail;
				expList = expList.tail;
			}

			if ((record != null) || (expList != null)) {
				env.errorMsg.error(exp.pos, "", ErrorType.E_FUNCFORMALSUNMATCH);
				return new ExpTy(null, ((FunEntry) entry).result);
			}

			return new ExpTy(null, ((FunEntry) entry).result);

		} else {
			env.errorMsg.error(exp.pos, "", ErrorType.E_UNDEFFUNC);
			return new ExpTy(null, VOID);
		}

	}

	ExpTy transExp(ArrayExp exp) {
		//system.out.println("ArrayExp");
		Type type = transTy(exp.typ, exp.pos);
		if ((!(type instanceof NAME)) || (!(type.coerceTo(ARRAY)))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_UNDEFARR);
			return new ExpTy(null, type);
		}

		if (!(transExp(exp.size).type.actual().coerceTo(INT))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_ARRAYSIZEUNMATCH);
			return new ExpTy(null, (NAME) type);
		}

		if (!(transExp(exp.init).type.actual()
				.coerceTo(((ARRAY) type.actual()).element))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_ARRAYTYPEUNMATCH);
			return new ExpTy(null, (NAME) type);
		}
		return new ExpTy(null, (NAME) type);
	}

	ExpTy transExp(RecordExp exp) {
		//system.out.println("RecordExp");
		Type type = transTy(exp.typ, exp.pos);
		if ((!(type instanceof NAME)) || (!(type.coerceTo(RECORD)))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_UNDEFREC);
			return new ExpTy(null, type);
		}

		if (exp.fields == null)
			return new ExpTy(null, (NAME) type);

		RECORD rec = (RECORD) type.actual();
		FieldExpList fieldExp = exp.fields;
		while ((rec != null) && (fieldExp != null)) {
			if (!(rec.fieldType.matches(transExp(fieldExp.init).type))) {
				env.errorMsg.error(exp.pos, "", ErrorType.E_RECORDFIELDUNMATCH);
				return new ExpTy(null, type);
			}
			if (!(rec.fieldName.toString().equals(fieldExp.name.toString()))) {
				env.errorMsg.error(exp.pos, "", ErrorType.E_RECORDFIELDUNMATCH);
				return new ExpTy(null, type);
			}
			rec = rec.tail;
			fieldExp = fieldExp.tail;
		}

		if ((rec != null) || (fieldExp != null)) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_RECORDFIELDUNMATCH);
			return new ExpTy(null, type);
		}
		return new ExpTy(null, (NAME) type);
	}

	ExpTy transExp(IfExp exp) {
		//system.out.println("IfExp");
		if (!(transExp(exp.test).type.coerceTo(INT))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_TESTEXPTYPEUNMATCH);
			return new ExpTy(null, VOID);
		}

		Type thenTy = transExp(exp.thenclause).type;
		if (exp.elseclause != null) {
			Type elseTy = transExp(exp.elseclause).type;
			if (!(thenTy.matches(elseTy))) {
				{
					env.errorMsg.error(exp.thenclause.pos, "",
							ErrorType.E_THENELSETYPEUNMATCH);
				}
				return new ExpTy(null, VOID);
			}
			return new ExpTy(null, thenTy);
		} else {
			if (!(thenTy.coerceTo(VOID)))
				env.errorMsg.error(exp.thenclause.pos, "",
						ErrorType.E_THENNORETURN);
			return new ExpTy(null, VOID);
		}
	}

	ExpTy transExp(WhileExp exp) {
		//system.out.println("WhileExp");
		loop++;
		if (!(transExp(exp.test).type.coerceTo(INT))) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_WHILEEXPTYPEUNMATCH);
		}

		Type doTy = transExp(exp.body).type;
		if (!(doTy.coerceTo(VOID)))
			env.errorMsg.error(exp.body.pos, "", ErrorType.E_DONORETURN);
		loop--;
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(ForExp exp) {
		//system.out.println("ForExp");
		if (forVar.contains(exp.var.name.toString())) {
			env.errorMsg.error(exp.var.pos, "", ErrorType.E_FORVARASSIGN);
			return new ExpTy(null, VOID);
		}

		loop++;
		env.venv.beginScope();

		env.venv.put(exp.var.name, new VarEntry(INT));
		forVar.add(exp.var.name.toString());
		if (!transExp(exp.var.init).type.coerceTo(INT)) {
			env.errorMsg.error(exp.var.init.pos, "",
					ErrorType.E_FORINITTYPEUNMATCH);
		}
		if (!transExp(exp.hi).type.coerceTo(INT)) {
			env.errorMsg.error(exp.hi.pos, "", ErrorType.E_FORBOUNDTYPEUNMATCH);
		}

		transExp(exp.body);

		forVar.remove(exp.var.name.toString());
		env.venv.endScope();
		loop--;
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(BreakExp exp) {
		//system.out.println("BreakExp");
		if (loop <= 0) {
			env.errorMsg.error(exp.pos, "", ErrorType.E_BREAKLOCALFALSE);
		}
		loop--;
		return new ExpTy(null, VOID);
	}

	ExpTy transExp(Exp exp) {
		if (exp instanceof OpExp)
			return transExp((OpExp) exp);
		else if (exp instanceof LetExp)
			return transExp((LetExp) exp);
		else if (exp instanceof IntExp)
			return transExp((IntExp) exp);
		else if (exp instanceof NilExp)
			return transExp((NilExp) exp);
		else if (exp instanceof VarExp)
			return transExp((VarExp) exp);
		else if (exp instanceof AssignExp)
			return transExp((AssignExp) exp);
		else if (exp instanceof SeqExp)
			return transExp((SeqExp) exp);
		else if (exp instanceof ArrayExp)
			return transExp((ArrayExp) exp);
		else if (exp instanceof RecordExp)
			return (transExp((RecordExp) exp));
		else if (exp instanceof StringExp)
			return (transExp((StringExp) exp));
		else if (exp instanceof CallExp)
			return (transExp((CallExp) exp));
		else if (exp instanceof IfExp)
			return (transExp((IfExp) exp));
		else if (exp instanceof ForExp)
			return (transExp((ForExp) exp));
		else if (exp instanceof WhileExp)
			return (transExp((WhileExp) exp));
		else if (exp instanceof BreakExp)
			return (transExp((BreakExp) exp));
		else {
			new Print(System.out).prExp(exp, 5);
			env.errorMsg.error(exp.pos, "", ErrorType.E_EXP);
			return new ExpTy(null, INT);
		}
	}

	ExpTy transVar(SimpleVar var) {

		//system.out.println("SimpleVar");
		Object entry = env.venv.get(var.name);
		if (entry instanceof VarEntry) {
			return new ExpTy(null, ((VarEntry) entry).ty);
		} else {
			env.errorMsg.error(var.pos, "", ErrorType.E_UNDEFVAR);
			return new ExpTy(null, INT);
		}
	}

	ExpTy transVar(SubscriptVar var) {
		//system.out.println("SubscriptVar");
		ExpTy expTy = transVar(var.var);

		if (expTy.type.coerceTo(ARRAY)) {
			ARRAY arr = (ARRAY) expTy.type.actual();
			return new ExpTy(null, arr.element);
		} else {
			env.errorMsg.error(var.pos, "", ErrorType.E_UNDEFARR);
			return new ExpTy(null, INT);
		}
	}

	ExpTy transVar(FieldVar var) {
		//system.out.println("FieldVar");
		ExpTy expTy = transVar(var.var);

		if (expTy.type.coerceTo(RECORD)) {
			RECORD rec = (RECORD) expTy.type.actual();
			while (rec != null) {
				if (var.field.toString().equals(rec.fieldName.toString()))
					return new ExpTy(null, rec.fieldType);
				rec = rec.tail;
			}
			env.errorMsg.error(var.pos, "", ErrorType.E_UNDEFREC);
			return new ExpTy(null, INT);

		} else {
			env.errorMsg.error(var.pos, "", ErrorType.E_UNDEFREC);
			return new ExpTy(null, INT);
		}
	}

	ExpTy transVar(Var var) {
		if (var instanceof SimpleVar)
			return transVar((SimpleVar) var);
		else if (var instanceof SubscriptVar)
			return transVar((SubscriptVar) var);
		else if (var instanceof FieldVar)
			return transVar((FieldVar) var);
		else {
			env.errorMsg.error(var.pos, "", ErrorType.E_LVALUE);
			return new ExpTy(null, INT);
		}
	}

	Exp transDec(Dec dec) {
		//system.out.println("Dec");
		if (dec instanceof FunctionDec) {
			transDec((FunctionDec) dec);
		} else if (dec instanceof TypeDec) {
			transDec((TypeDec) dec);
		} else if (dec instanceof VarDec) {
			transDec((VarDec) dec);
		} else
			env.errorMsg.error(dec.pos, "", ErrorType.E_DECLAR);
		return null;
	}

	void transDecBatch(DecList preDec, Dec lastDec) {
		for (DecList decList = preDec; decList.head != lastDec; decList = decList.tail)
			transDec(decList.head);
		transDec(lastDec);
	}

	Exp transDec(VarDec varDec) {
		//system.out.println("varDec");
		Type ty = transExp(varDec.init).type;

		// complex form: expression must be matched
		if (varDec.typ != null) {
			Type type = transTy(varDec.typ);

			if ((type.coerceTo(ty))
					|| (type.coerceTo(RECORD) && ty.coerceTo(NIL)))
				env.venv.put(varDec.name, new VarEntry(type));
			else
				env.errorMsg.error(varDec.pos, "", ErrorType.E_VARTYPEUNMATCH);
		}

		// simple form: expression cannot be NIL
		else {
			if (ty.coerceTo(NIL))
				env.errorMsg.error(varDec.pos, "", ErrorType.E_TYPEMISSED);
			env.venv.put(varDec.name, new VarEntry(ty));
		}
		return null;
	}

	Exp transDec(TypeDec typeDec) {
		//system.out.println("typeDec");
		((NAME) env.tenv.get(typeDec.name)).bind(transTy(typeDec.ty));
		return null;
	}

	Exp transDec(FunctionDec funDec) {

		//system.out.println("funDec");
		Type result;
		if (funDec.result == null) // define return type
			result = new VOID();
		else
			result = transTy(funDec.result);

		// ((FunEntry) env.venv.get(funDec.name)).setResult(result);

		if (funDec.params != null) {
			// RECORD formals = transTypeFields(funDec.params);
			// ((FunEntry) env.venv.get(funDec.name)).setFormals(formals);
			env.venv.beginScope();
			for (FieldList fieldList = funDec.params; fieldList != null; fieldList = fieldList.tail)
				env.venv.put(fieldList.name, new VarEntry((Type) env.tenv
						.get(fieldList.typ)));
		}

		ExpTy expTy = transExp(funDec.body);

		if (!expTy.type.coerceTo(result)) // check return type
			env.errorMsg.error(funDec.pos, "", ErrorType.E_FUNTYPEUNMATCH);

		if (funDec.params != null)
			env.venv.endScope();
		return null;
	}

	Type transTy(Ty ty) {
		//system.out.println("ty");
		if (ty instanceof NameTy) {
			return transTy((NameTy) ty);
		} else if (ty instanceof ArrayTy) {
			return transTy((ArrayTy) ty);
		} else if (ty instanceof RecordTy) {
			return transTy((RecordTy) ty);
		} else {
			env.errorMsg.error(ty.pos, "", ErrorType.E_TYPEDECLAR);
			return null;
		}
	}

	Type transTy(Symbol tyName, int pos) {
		//system.out.println("transTy:Symbol");
		if (tyName.toString().equals("int"))
			return new INT();
		else if (tyName.toString().equals("string"))
			return new STRING();
		else if (env.tenv.get(tyName) == null) {
			env.errorMsg.error(pos, "", ErrorType.E_UNDEFTYPE); // check type_id
			// existence
			return (new INT());
		} else {
			NAME result = new NAME(tyName);
			result.bind((Type) env.tenv.get(tyName));
			return result;
		}
	}

	Type transTy(NameTy ty) {
		//system.out.println("NameTy");
		return transTy(ty.name, ty.pos);
	}

	Type transTy(ArrayTy ty) {
		//system.out.println("ArrayTy");
		return new ARRAY(transTy(ty.typ, ty.pos));
	}

	Type transTy(RecordTy ty) {
		//system.out.println("RecordTy");
		return transTypeFields(ty.fields);
	}

	RECORD transTypeFields(FieldList fieldList) {
		//system.out.println("fieldList");
		if (fieldList == null) // deal with "fieldList == null"
			return new RECORD(null, null, null);

		RECORD rec = new RECORD(fieldList.name, transTy(fieldList.typ,
				fieldList.pos), null);
		RECORD recPointer = rec;
		for (FieldList fPointer = fieldList.tail; fPointer != null; fPointer = fPointer.tail) {
			recPointer.tail = new RECORD(fPointer.name, transTy(fPointer.typ,
					fieldList.pos), null);
			recPointer = recPointer.tail;
		}
		return rec;
	}

	public void transProg(Exp exp) {
		if (exp!=null)
			transExp(exp);
	}
}
