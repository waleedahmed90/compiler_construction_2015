import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Lex {

    FileInputStream fis = null;    
    BufferedReader br = null;
    FileReader fr;
    char nextch;                   
    int lineno;                    
	int i;
	String dot = ".";
	
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
		   case '\r'  : nextch = getchar();
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
		   case ','  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.COMMA;
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
		   case '-'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.SUBOP;
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
           case '!'  : nextch = getchar();
			           if (nextch == '=')
			             { nextch = getchar();
			               t = new  TokenInfo();
			               t.tok = Token.NOTEQOP;
			               t.line = lineno;
			               return(t);
			             }
			           else
			             { t = new  TokenInfo();
			               t.tok = Token.NOTOP;
			               t.line = lineno;
			               return(t);
			             }
			           
           case '<'  : nextch = getchar();
			           if (nextch == '=')
			             { nextch = getchar();
			               t = new  TokenInfo();
			               t.tok = Token.LESSEQ;
			               t.line = lineno;
			               return(t);
			             }
			           else
			             { t = new  TokenInfo();
			               t.tok = Token.LESS;
			               t.line = lineno;
			               return(t);
			             }
           case '>'  : nextch = getchar();
			           if (nextch == '=')
			             { nextch = getchar();
			               t = new  TokenInfo();
			               t.tok = Token.GREATEQ;
			               t.line = lineno;
			               return(t);
			             }
			           else
			             { t = new  TokenInfo();
			               t.tok = Token.GREAT;
			               t.line = lineno;
			               return(t);
			             }
			
		   case '/'  : nextch = getchar();
                       t = new  TokenInfo();
					   if(nextch != '/' && nextch != '*') {
						   t.tok = Token.DIVOP;
						   t.line = lineno;
						   return(t);
					    }
					   else if(nextch == '*') {
							nextch = getchar();
							boolean done = false;
							while(!done) {
								if(nextch!= '*' && nextch!= '\n' && nextch!= '\0') {
									nextch = getchar();
								}
								else if(nextch == '\n') {
									lineno++;
									nextch = getchar();
								}
								else if(nextch == '*') {
									nextch = getchar();
									if(nextch == '/') {
										nextch = getchar();
										done = true;
									}
									else if(nextch == '\n') {
										lineno++;
										nextch = getchar();
									}
									else if(nextch == '\0') {
										t.tok = Token.EOF;
										return (t);
									}
								}
							}
						}
					   else if(nextch == '\0') {
									t.tok = Token.EOF;
									return(t);
								}
								
					   else if(nextch == '/') {
							nextch = getchar();
							boolean done = false;
							while(nextch != '\n') {
								nextch = getchar();		
								}
							}
					     break;
					  
					  
			case '.'  : nextch = getchar();
					   t = new TokenInfo();
					   t.tok = Token.DOTOP;
					   t.line = lineno++;
					   return(t); 
		    case '{'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.LBRACE;
                       t.line = lineno;
                       return(t); 
			case '}'  : nextch = getchar();
                       t = new  TokenInfo();
                       t.tok = Token.RBRACE;
                       t.line = lineno;
                       return(t);
			default   : if(Character.isLetter(nextch) || nextch == '_') {
                    	   String nextStr = "";
                    	   nextStr = nextStr + nextch;
                    	   
                    	   if(Character.isLetter(nextch)){
	                    	   nextch = getchar();
	                    	   while(Character.isLetter(nextch) || nextch == '_' || Character.isDigit(nextch)) {
	                    		   nextStr = nextStr + nextch;
	                    		   nextch = getchar();
	                    	   }
	                    	   if(nextStr.equals("public")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWPUBLIC;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("class")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWCLASS;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("main")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWMAIN;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("void")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWVOID;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("int")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWINT;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("float")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWFLOAT;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   
	                    	   else if(nextStr.equals("static")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWSTATIC;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("System")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWSYSTEM;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("out")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWOUT;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("print")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWPRINT;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("println")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWPRINTLN;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("return")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWRETURN;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("if")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWIF;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("else")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWELSE;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	   else if(nextStr.equals("while")) {
	                    		   t = new TokenInfo();
	        					   t.tok = Token.KWWHILE;
	        					   t.line = lineno;
	        					   return(t); 
	                    	   }
	                    	 
	                    	   else {
	                    		   t = new TokenInfo();
		    					   t.tok = Token.ID;
		    					   t.line = lineno;
		    					   t.strval = nextStr;
		    					   return(t); 
	                    	   }
							}
						}
		
					  else if(nextch == '"') { 
						  boolean done = false;
						  String str = "";
						  nextch = getchar();
						  while(nextch != '\n' && nextch != '\0' && !done)  {
						  if(nextch == '"'){
							  break;
						  	}
						  else {
							  str = str + nextch;
							  nextch = getchar();
							  }
						  }
						  if (nextch == '\n') {
							  System.out.println("Error: Unterminated String on line "+lineno);
						  }
						  if (nextch == '\0') {
							  System.out.println("Error: String never ended on line "+lineno);
						  }
						  nextch = getchar();
						  t = new TokenInfo();
	   					  t.tok = Token.STRINGCONSTANT;
	   					  t.line = lineno;
	   					  t.strval = str;
	   					  return(t); 
					  	}
					  else if(Character.isDigit(nextch)) {
                    	  if(nextch == '0') {
                    		  String ss = "";
                    		  ss = ss + nextch;
                    		  nextch= getchar();
                    		  if(Character.isDigit(nextch)) {
                    			  System.out.println("invalid numeric literal at line no " + lineno);
                    			  while(nextch != '\n') {
                    				  nextch = getchar();
                    			  }
                    		  }
                    		  else if(nextch == '.') {
                    			  String sss = "0";                    			 
                    			  sss = sss + nextch;
                    			  nextch = getchar();
                    			  while(Character.isDigit(nextch)) {
                    				  sss = sss + nextch;
                    				  nextch = getchar();
                    			  	  }
                    			  	  sss += nextch;
                    			  	  if(nextch == 'f' || nextch == 'F') {
                    			  		  nextch = getchar();
                    			  		if(nextch == 'e') {
                    			  			sss = sss + nextch;
                                 		   nextch = getchar();
                                 		   if(nextch == '+' || nextch == '-' || Character.isDigit(nextch)) {
                                 			   sss = sss + nextch;
                                 			   nextch = getchar();
                                 			   while (Character.isDigit(nextch)) {
                                 			   sss = sss + nextch;
                                     		   nextch = getchar();
                                     		   } 
                                     		  
                                 		   }
                    			  		}
		                    			  t = new TokenInfo();
			   	    					  t.tok = Token.FLOATCONSTANT;
			   	    					  t.line = lineno;
			   	    					  t.strval = sss;
			   	    					  try {
		   	    						  t.floatval = Float.parseFloat(sss);
			   	    					  }
			   	    					  catch(Exception ex) {
			   	    						  ex.getMessage();
			   	    					  }
			   	    					  return(t);
	                    			  	  }
                    			  	  else
                    			  		  System.out.println("Error: F || f is missing in float value on line " + lineno);
                    		  			}
                    	  			}
                    		  		else {
                    		  			String s = "";
                    			  while(Character.isDigit(nextch) || nextch == '.') {
                    				  s = s + nextch;
                    				  nextch = getchar();
                    			  }
                    			  s += nextch;
                    			  if(s.contains(dot) && (s.contains("f") || s.contains("F"))) {
                    				  
                    				  nextch = getchar();
                    				  if(nextch == 'e') {
                  			  			s = s + nextch;
                               		   nextch = getchar();
                               		   if(nextch == '+' || nextch == '-' || Character.isDigit(nextch)) {
                               			   s = s + nextch;
                               			   nextch = getchar();
                               			   while (Character.isDigit(nextch)) {
                               			   s = s + nextch;
                                   		   nextch = getchar();
                                   		   } 
                               		   }
                    				  }
                        			  t = new TokenInfo();
    	   	    					  t.tok = Token.FLOATCONSTANT;
    	   	    					  t.line = lineno;
    	   	    					  t.strval = s;
    	   	    					  try {
    	   	    						  t.floatval = Float.parseFloat(s);
    	   	    					  }
    	   	    					  catch(Exception ex) {
    	   	    						  ex.getMessage();
    	   	    					  }
    	   	    					  return(t);
                    			  }
                    			  else if(s.contains(dot) && (!s.contains("f") || !s.contains("F"))) {
                    				  System.out.println("Error: F || f is missing in float value on line " + lineno);
                    			  }
                    			  else if (!s.contains(dot)){
                            		  int l = s.length();
		   	    					  String fv = s.substring(0, l-1);
       			                      t = new TokenInfo();
       			   	    			  t.tok = Token.INTEGERCONSTANT;
       			   	    			  t.line = lineno;
       			   	    			  t.strval = s;
       			   	    			  try {
       			   	    				  t.intval = Integer.parseInt(fv);
       			   	    			  }
       			   	    			  catch(Exception Ex) {
       			   	    				  Ex.getMessage();
       			   	    			  }
       			   	    			  return(t); 
                            		 }
                    			  }
                      		   }
         					}
        				}   			 
        			}
private char getchar() {
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
               fr = new FileReader(file);
               br = new BufferedReader(fr);
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
