import java.util.List;
import java.util.Stack;
import java.util.Arrays;

public class ASDI implements Parser{

    private int i = 0;
    private boolean hayErrores = false;
    private final List<Token> tokens;
    String [][] TablaAS =
    { {"No terminal",     "select",             "from",      "distinct",      "*",       ",",        "id",        ".",       "$" },
      {     "Q",        "select D from T",       "",             "",           "",       "",          "",          "",       ""  },             
      {     "D",             "",                 "",         "distinct P",     "P",      "",          "P",         "",       ""  },
      {     "P",             "",                 "",             "",           "*",      "",          "A",         "",       ""  },  
      {     "A",             "",                 "",             "",           "",       "",        "A2 A1",       "",       ""  }, 
      {     "A1",            "",                 "e",            "",           "",      ", A",         "",         "",       ""  },
      {     "A2",            "",                 "",             "",           "",       "",        "id A3",       "",       ""  },
      {     "A3",            "",                 "e",            "",           "",       "e",          "",       ". id",     ""  },
      {     "T",             "",                 "",             "",           "",       "",        "T2 T1",       "",       ""  },
      {     "T1",            "",                 "",             "",           "",      ", T",         "",         "",       "e" },
      {     "T2",            "",                 "",             "",           "",       "",        "id T3",       "",       ""  },                                                        
      {     "T3",            "",                 "",             "",           "",       "e",        "id",         "",       "e" }
    };

    public ASDI(List<Token> tokens){
        this.tokens = tokens;
    }

    @Override
    public boolean parse() {

        String entrada = "";
        int j,k = 0;
        int fila = 0, columna = 0;
        Stack <String> pila = new Stack <String>();
        pila.push("$");
        pila.push("T");
        pila.push("from");
        pila.push("D");
        pila.push("select");

        while( !pila.empty() ){
              

            if(tokens.get(i).tipo == TipoToken.IDENTIFICADOR )
                entrada = "id";
            else 
                entrada = tokens.get(i).lexema;
            
            for( k=1;i<tablaAS.length;;i++){
                if(tablaAS[i][0].equals( pila.peek()) )
                    fila = k;
                else 
                    fila = -1;
            }

            for( k=1;i<l;i++){
                if(tablaAS[0][i].equals(entrada) )
                    columna = k;
                else 
                    columna = -1;
            }

            if (pila.peek().equals(entrada)){
                pila.pop();
                i++;
            }
            else if( pila.peek().equals("id") || (pila.peek().equals("select") || (pila.peek().equals("from") || (pila.peek().equals("distinct") || (pila.peek().equals(",") || (pila.peek().equals(".") || (pila.peek().equals("*") ){
                hayErrores = true;
                break;
            }
            else if( !(pila.peek().equals("id") || (pila.peek().equals("select") || (pila.peek().equals("from") || (pila.peek().equals("distinct") || (pila.peek().equals(",") || (pila.peek().equals(".") || (pila.peek().equals("*") ) && 
                    TablaAS[fila][columna].equals("") ){
                hayErrores = true;
                break;
            }else{
                String[] producciones = TablaAS[fila][columna].split(" "); 
                j = producciones.length;
                pila.pop();
                while(j>0){
                    if(!producciones[j-1].equals("e"))
                        pila.push(producciones[j-1]);
                    j--;
                }
            }
        }

        if( pila.empty() && tokens.get(i-1).tipo == TipoToken.EOF && !hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Consulta incorrecta");
        }
        return false;
    }

}

