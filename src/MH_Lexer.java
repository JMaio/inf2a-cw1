// s1621503
// File:   MH_Lexer.java
// Date:   October 2013, subsequently modified each year.

// Java template file for lexer component of Informatics 2A Assignment 1.
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').

import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM {

	static class VarAcceptor extends Acceptor implements DFA {
	    public String lexClass() {return "VAR" ;} ;
	    public int numberOfStates() {return 3 ;} ;
	
	    int next (int state, char c) {
		switch (state) {
			case 0: if (CharTypes.isSmall(c)) return 1 ; else return dead() ;	// small
			case 1: if (CharTypes.isSmall(c) || 								//(small +
						CharTypes.isLarge(c) || 								// large +
						CharTypes.isDigit(c) || 								// digit +
						c == '\'') return 1 ; else return dead() ;				// apostrophe)
		        default: return dead() ; // garbage state, declared "dead" below
			}
	    }
	
	    boolean accepting (int state) {return (state == 1) ;}
	    int dead () {return 2 ;}
	}
	
	static class NumAcceptor extends Acceptor implements DFA {
		public String lexClass() {return "NUM" ;} ;
	    public int numberOfStates() {return 4 ;} ;
	
	    int next (int state, char c) {
			switch (state) {
				case 0: if (c == '0') return 2 ; else if (CharTypes.isDigit(c)) return 1 ;	else return dead() ;	// 0
				case 1: if (CharTypes.isDigit(c)) return 1; else return dead() ;	// nonZeroDigit
			        default: return dead() ; // garbage state, declared "dead" below
				}
	    }
	
	    boolean accepting (int state) {return (state == 1 || state == 2) ;}
	    int dead () {return 3 ;}
	}
	
	static class BooleanAcceptor extends Acceptor implements DFA {
		public String lexClass() {return "BOOLEAN" ;} ;
	    public int numberOfStates() {return 9 ;} ;
	
	    int next (int state, char c) {
			switch (state) {
				case 0: if (c == 'T') return 1 ; else if (c == 'F') return 3 ; else return dead() ;		// T or F
				case 1: if (c == 'r') return 2 ; else return dead() ;	// r
				case 2: if (c == 'u') return 6 ; else return dead() ;	// u
		
				case 3: if (c == 'a') return 4 ; else return dead() ;	// a
				case 4: if (c == 'l') return 5 ; else return dead() ;	// l
				case 5: if (c == 's') return 6 ; else return dead() ;	// s
				
				case 6: if (c == 'e') return 7 ; else return dead() ;	// e
			        default: return dead() ; // garbage state, declared "dead" below
				}
	    }
	
	    boolean accepting (int state) {return (state == 7) ;}
	    int dead () {return 8 ;}
	}
	
	static class SymAcceptor extends Acceptor implements DFA {
		public String lexClass() {return "SYM" ;} ;
	    public int numberOfStates() {return 3 ;} ;
	
	    int next (int state, char c) {
			switch (state) {
				case 0: if (CharTypes.isSymbolic(c)) return 1 ; else return dead() ;	// symbolic
				case 1: if (CharTypes.isSymbolic(c)) return 1 ; else return dead() ;	// symbolic
		        	default: return dead() ; // garbage state, declared "dead" below
				}
	    }
	
	    boolean accepting (int state) {return (state == 1) ;}
	    int dead () {return 2 ;}
	}
	
	static class WhitespaceAcceptor extends Acceptor implements DFA {
		public String lexClass() {return "" ;} ;
	    public int numberOfStates() {return 3 ;} ;
	
	    int next (int state, char c) {
			switch (state) {
				case 0: if (CharTypes.isWhitespace(c)) return 1 ; else return dead() ;	// WHTSPC
				case 1: if (CharTypes.isWhitespace(c)) return 1 ; else return dead() ;	// WHTSPC
		        	default: return dead() ; // garbage state, declared "dead" below
				}
	    }
	
	    boolean accepting (int state) {return (state == 1) ;}
	    int dead () {return 2 ;}
	}
	
	static class CommentAcceptor extends Acceptor implements DFA {
		public String lexClass() {return "" ;} ;
	    public int numberOfStates() {return 6 ;} ;
	
	    int next (int state, char c) {
			switch (state) {
				case 0: if (c == '-') return 1 ; else return dead() ;			// -
				case 1: if (c == '-') return 2 ; else return dead() ;			// -
				case 2: if (c == '-') return 2 ; else return 3 ;				// -*
				case 3: if (!CharTypes.isSymbolic(c) &&
							!CharTypes.isNewline(c)) return 4 ; else return dead() ;	// nonSymbolNewline / epsilon
				case 4: if (!CharTypes.isNewline(c)) return 4 ; else return dead() ;	// nonSymbol
		        	default: return dead() ; 									// garbage state, declared "dead" below
				}
	    }
	
	    boolean accepting (int state) {return (state == 2 || state == 3 || state == 4 ) ;}
	    int dead () {return 5 ;}
	}
	
	static class TokAcceptor extends Acceptor implements DFA {
	
	    String tok ;
	    int tokLen ;
	    TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}
	    
	    public String lexClass() {return tok ;} ;
	    public int numberOfStates() {return (tokLen+2) ;} ;
	    
	    
	    int next (int state, char c) {	    	
	    	if (state < tokLen && c == tok.charAt(state)) {
	    		return (state+1) ;
	    	} else {
	    		return dead() ;
	    	}
	    }
	
	    boolean accepting (int state) {return (state == (tokLen)) ;}
	    int dead () {return (tokLen+1) ;}
	}
	static DFA commentAcc 		= new CommentAcceptor() ;
    static DFA whitespaceAcc 	= new WhitespaceAcceptor() ;
    
	static DFA lbracketAcc 		= new TokAcceptor("(") ;
	static DFA rbracketAcc 		= new TokAcceptor(")") ;
    
    static DFA semicolAcc 		= new TokAcceptor(";") ;
    
	static DFA symAcc 			= new SymAcceptor() ;
    static DFA booleanAcc 		= new BooleanAcceptor() ;
    static DFA varAcc 			= new VarAcceptor() ;
    static DFA numAcc 			= new NumAcceptor() ;
    
    static DFA integerAcc 		= new TokAcceptor("Integer") ;
    static DFA boolAcc 			= new TokAcceptor("Bool") ;
    static DFA ifAcc 			= new TokAcceptor("if") ;
    static DFA thenAcc 			= new TokAcceptor("then") ;
    static DFA elseAcc 			= new TokAcceptor("else") ;
    
    
    static DFA[] MH_acceptors = 
	new DFA[] {
			whitespaceAcc,
			commentAcc,

			lbracketAcc,
			rbracketAcc,

			semicolAcc,

			integerAcc,
			boolAcc,
			ifAcc,
			thenAcc,
			elseAcc,

			numAcc,
			varAcc,
			symAcc,
			booleanAcc,
			   } ;

    MH_Lexer (Reader reader) {
    	super(reader,MH_acceptors) ;
    }

}
