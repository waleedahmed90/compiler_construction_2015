public enum Token{
            LPAR, //(
            RPAR, //)
            SEMICOLON, //;
            ASSIGNOP, //=
            ADDOP, //+
            MULOP, //*
            EQSYM, //==
            EOF, // '\0'
            SUBOP, // -
            DIVOP, // /
            DOTOP,	//.
            LBRACE, //{
            RBRACE, //}
            COMMA, //,
            NOTOP,//!
            NOTEQOP, //!=
            LESS, //<
            LESSEQ, //<=
            GREAT, //>
            GREATEQ, //>=
            KWPUBLIC,//public
            ID, //identifier
            KWCLASS, //class
            KWIF, //if
            KWELSE, //else
            KWWHILE, //while
            KWRETURN, //return
            KWSTATIC, //static
            KWVOID, //void
            KWMAIN, //main
            KWSYSTEM, //system
            KWPRINT, //print
            KWPRINTLN, //println
            KWOUT, //out
            KWINT, //int
            KWFLOAT,//float 
            INTEGERCONSTANT, 
            FLOATCONSTANT, 
            STRINGCONSTANT
}