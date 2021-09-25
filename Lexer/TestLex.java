public class TestLex{


  public static void main(String[] args) {


    Lex scan = new Lex("input.txt");
    TokenInfo Info = scan.gettoken();
     
    while (Info.tok != Token.EOF)
       {
         switch(Info.tok) {
            case LPAR: System.out.println("LPAR on line no: "+Info.line);
                       break;
            case RPAR: System.out.println("RPAR on line no: "+Info.line);
                       break;
            case SEMICOLON: System.out.println("SEMICOLON on line no: "+Info.line);
                       break;
            case ASSIGNOP: System.out.println("ASSIGNOP on line no: "+Info.line);
                       break;
            case ADDOP: System.out.println("ADDOP on line no: "+Info.line);
                       break;
            case MULOP: System.out.println("MULOP on line no: "+Info.line);
                       break;
            case EQSYM: System.out.println("EQSYM on line no: "+Info.line);
                       break;
         }
         Info = scan.gettoken(); 
       }





}

}

