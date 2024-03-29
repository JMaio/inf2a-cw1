// s1621503
// File:   MH_Parser.java
// Date:   October 2013, subsequently modified each year

// Java template file for parser component of Informatics 2A Assignment 2 (2013).
// Students should add a method body for the LL(1) parse table for Micro-Haskell.


import java.io.* ;
 
class MH_Parser extends GenParser implements PARSER {

    String startSymbol() {return "#Prog" ;}

    // Right hand sides of all productions in grammar:

    static String[] epsilon              = new String[] { } ;
    static String[] Decl_Prog            = new String[] {"#Decl", "#Prog"} ;
    static String[] TypeDecl_TermDecl    = new String[] {"#TypeDecl", "#TermDecl"} ;
    static String[] VAR_has_Type         = new String[] {"VAR", "::", "#Type", ";"} ;
    static String[] Type0_TypeRest       = new String[] {"#Type0", "#TypeRest"} ;
    static String[] arrow_Type           = new String[] {"->", "#Type"} ;
    static String[] Integer              = new String[] {"Integer"} ;
    static String[] Bool                 = new String[] {"Bool"} ;
    static String[] lbr_Type_rbr         = new String[] {"(", "#Type", ")"} ;
    static String[] VAR_Args_eq_Exp      = new String[] {"VAR", "#Args", "=", "#Exp", ";"} ;
    static String[] VAR_Args             = new String[] {"VAR", "#Args"} ;
    static String[] Exp0                 = new String[] {"#Exp0"} ;
    static String[] if_then_else         = new String[] {"if", "#Exp", "then", "#Exp", "else", "#Exp"} ;
    static String[] Exp1_Rest0           = new String[] {"#Exp1", "#Rest0"} ;
    static String[] eqeq_Exp1            = new String[] {"==", "#Exp1"} ;
    static String[] lteq_Exp1            = new String[] {"<=", "#Exp1"} ;
    static String[] Exp2_Rest1           = new String[] {"#Exp2", "#Rest1"} ;
    static String[] plus_Exp2_Rest1      = new String[] {"+", "#Exp2", "#Rest1"} ;
    static String[] minus_Exp2_Rest1     = new String[] {"-", "#Exp2", "#Rest1"} ;
    static String[] Exp3_Rest2           = new String[] {"#Exp3", "#Rest2"} ;
    static String[] VAR                  = new String[] {"VAR"} ;
    static String[] NUM                  = new String[] {"NUM"} ;
    static String[] BOOLEAN              = new String[] {"BOOLEAN"} ;
    static String[] lbr_Exp_rbr          = new String[] {"(", "#Exp", ")"} ;

    // may add auxiliary methods here if desired

    
    String[] tableEntry (String nonterm, String tokClass) {
//    	Prog
    	if (nonterm.equals("#Prog")) {
    	    if (tokClass == null) return epsilon ;
    	    else if (tokClass.equals("VAR")) return Decl_Prog ;
    	    else return null ;
    	}
    	
//    	Decl
    	else if (nonterm.equals("#Decl")) {
    		if (tokClass == null) return epsilon;
    		if (tokClass.equals("VAR")) return TypeDecl_TermDecl ;
    		else return null ;
    	}
    	
//    	TypeDecl
    	else if (nonterm.equals("#TypeDecl")) {
    		if (tokClass.equals("VAR")) return VAR_has_Type ;
//    		if (tokClass.equals(";")) return epsilon ;
    		else return null ;
    	}
    	
//    	Type
    	else if (nonterm.equals("#Type")) { 
    		if (tokClass.equals("Integer")) return Type0_TypeRest ;
    		if (tokClass.equals("Bool")) return Type0_TypeRest ;
    		if (tokClass.equals("(")) return Type0_TypeRest ;
    		else return null ;
    	}
    	
//    	TypeRest
    	else if (nonterm.equals("#TypeRest")) {
    		if (tokClass.equals(")")) return epsilon ;
    		if (tokClass.equals(";")) return epsilon ;
    		if (tokClass.equals("->")) return arrow_Type;
    		else return null ;
    	}
    	
//    	Type0
    	else if (nonterm.equals("#Type0")) {
    		if (tokClass.equals("Integer")) return Integer ;
    		if (tokClass.equals("Bool")) return Bool ;
    		if (tokClass.equals("(")) return lbr_Type_rbr ;
    		else return null ;
    	}
    	
//    	TermDecl
    	else if (nonterm.equals("#TermDecl")) {
    		if (tokClass.equals("VAR")) return VAR_Args_eq_Exp ;
    		else return null ;
    	}
    	
//    	Args
    	else if (nonterm.equals("#Args")) {
    		if (tokClass.equals("VAR")) return VAR_Args ;
    		if (tokClass.equals("=")) return epsilon ;
    		else return null ;
    	}
    	
//    	Exp
    	else if (nonterm.equals("#Exp")) {
    		if (tokClass.equals("VAR")) return Exp0 ;
    		if (tokClass.equals("NUM")) return Exp0 ;
    		if (tokClass.equals("BOOLEAN")) return Exp0 ;
    		if (tokClass.equals("if")) return if_then_else ;
    		if (tokClass.equals("(")) return Exp0 ;
    		else return null ;
    	}
    	
//    	Exp0
    	else if (nonterm.equals("#Exp0")) {
    		if (tokClass.equals("VAR")) return Exp1_Rest0 ;
    		if (tokClass.equals("NUM")) return Exp1_Rest0 ;
    		if (tokClass.equals("BOOLEAN")) return Exp1_Rest0 ;
    		if (tokClass.equals("(")) return Exp1_Rest0 ;
    		else return null ;
    	}
    	
//    	Rest0
    	else if (nonterm.equals("#Rest0")) {
    		if (tokClass.equals("then")) return epsilon ;
    		if (tokClass.equals("else")) return epsilon ;
    		if (tokClass.equals(")")) return epsilon ;
    		if (tokClass.equals(";")) return epsilon ;
    		if (tokClass.equals("==")) return eqeq_Exp1 ;
    		if (tokClass.equals("<=")) return lteq_Exp1 ;
    		else return null ;
    	}
    	
//    	Exp1
    	else if (nonterm.equals("#Exp1")) {
    		if (tokClass.equals("VAR")) return Exp2_Rest1;
    		if (tokClass.equals("NUM")) return Exp2_Rest1;
    		if (tokClass.equals("BOOLEAN")) return Exp2_Rest1;
    		if (tokClass.equals("(")) return Exp2_Rest1;
    		else return null ;
    	}
    	
//    	Rest1
    	else if (nonterm.equals("#Rest1")) {
    		if (tokClass.equals("then")) return epsilon ;
    		if (tokClass.equals("else")) return epsilon ;
    		if (tokClass.equals(")")) return epsilon ;
    		if (tokClass.equals(";")) return epsilon ;
    		if (tokClass.equals("==")) return epsilon ;
    		if (tokClass.equals("<=")) return epsilon ;
    		if (tokClass.equals("+")) return plus_Exp2_Rest1 ;
    		if (tokClass.equals("-")) return minus_Exp2_Rest1 ;
    		else return null ;
    	}
    	
//    	Exp2
    	else if (nonterm.equals("#Exp2")) {
    		if (tokClass.equals("VAR")) return Exp3_Rest2 ;
    		if (tokClass.equals("NUM")) return Exp3_Rest2 ;
    		if (tokClass.equals("BOOLEAN")) return Exp3_Rest2 ;
    		if (tokClass.equals("(")) return Exp3_Rest2 ;
    		else return null ;
    	}
    	
//    	Rest2
    	else if (nonterm.equals("#Rest2")) {
    		if (tokClass.equals("VAR")) return Exp3_Rest2 ;
    		if (tokClass.equals("NUM")) return Exp3_Rest2 ;
    		if (tokClass.equals("BOOLEAN")) return Exp3_Rest2 ;
    		if (tokClass.equals("then")) return epsilon ;
    		if (tokClass.equals("else")) return epsilon ;
    		if (tokClass.equals("(")) return Exp3_Rest2 ;
    		if (tokClass.equals(")")) return epsilon ;
    		if (tokClass.equals(";")) return epsilon ;
    		if (tokClass.equals("==")) return epsilon ;
    		if (tokClass.equals("<=")) return epsilon ;
    		if (tokClass.equals("+")) return epsilon ;
    		if (tokClass.equals("-")) return epsilon ;
    		else return null ;
    	}

//    	Exp3
    	else if (nonterm.equals("#Exp3")) {
    		if (tokClass.equals("VAR")) return VAR ;
    		if (tokClass.equals("NUM")) return NUM ;
    		if (tokClass.equals("NUM")) return BOOLEAN ;
    		if (tokClass.equals("(")) return lbr_Exp_rbr ;
    		else return null ;
    	}
    	else {
    		return null ;
    	}
    }
}


//Prog -> e | Decl Prog
//Decl -> TypeDecl TermDecl
//TypeDecl -> v :: Type;
//Type -> Type0 TypeRest
//TypeRest -> e | Type
//Type0 -> i | b | (Type)
//TermDecl -> v Args = Exp;
//Args -> e | v Args
//Exp -> Exp0 | if Exp then Exp else Exp
//Exp0 -> Exp1 Rest0
//Rest0 -> e | == Exp1 | <= Exp 1
//Exp1 -> Exp2 Rest1
//Rest1 -> e | + Exp2 Rest1 | - Exp2 Rest1
//Exp2 -> Exp3 Rest2
//Rest2 -> e | Exp3 Rest2
//Exp3 -> v | n | b | (Exp)

// For testing

class MH_ParserDemo {

    static PARSER MH_Parser = new MH_Parser() ;

    public static void main (String[] args) throws Exception {
	Reader reader = new BufferedReader (new FileReader (args[0])) ;
	LEX_TOKEN_STREAM MH_Lexer = 
	    new CheckedSymbolLexer (new MH_Lexer (reader)) ;
	TREE theTree = MH_Parser.parseTokenStream (MH_Lexer) ;
    }
}
