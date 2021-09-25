import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
This is a Lexical Analyzer for Tiny Java
 * 
 */

public class Lex {

    FileInputStream fis = null;    // Input File Stream
    char nextch;                   // nextcharacter
    int lineno;                    // lineno;

public TokenInfo gettoken()
{

   TokenInfo t=null;
   while(true)
        {
         switch(nextch) { 
           case '\0' : t = new TokenInfo();
                       t.tok = Token.EOF;
                       t.line = lineno;
                       return(t);
           case '\n'  : nextch = getchar();
                        lineno++;
                        break;
           case '\t'  : nextch = getchar();
                        break;
           case ' '  : nextch = getchar();
                        break;
           case ';'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.SEMICOLON;
                       t.line = lineno;
                       return(t);
           case '('  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.LPAR;
                       t.line = lineno;
                       return(t);
           case ')'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.RPAR;
                       t.line = lineno;
                       return(t);
           case '+'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.ADDOP;
                       t.line = lineno;
                       return(t);
           case '*'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.MULOP;
                       t.line = lineno;
                       return(t);
           case '='  : nextch = getchar();
                       if (nextch == '=')
                         { nextch = getchar();
                           t = new  TokenInfo();
                           t.tok = Token.EQSYM;
                           t.line = lineno;
                           return(t);
                         }
                       else
                         { t = new  TokenInfo();
                           t.tok = Token.ASSIGNOP;
                           t.line = lineno;
                           return(t);
                         }
           default   : nextch = getchar();
                       System.out.println("Error Unknown Character on line no "+lineno);
        }

      }
}

private char getchar()
{
    try {
        if (fis.available() != 0) 
           return ((char) fis.read());
        }
     catch (IOException e) {
         e.printStackTrace();
        }
    return('\0');
}

public Lex(String fname) {

         File file = new File(fname);

         try {
               fis = new FileInputStream(file);
             }
         catch (FileNotFoundException e) {
              System.out.println("File Not Found");
              e.printStackTrace();
              System.exit(0);
             } 


         nextch = getchar();
         lineno = 1;

  }
}

