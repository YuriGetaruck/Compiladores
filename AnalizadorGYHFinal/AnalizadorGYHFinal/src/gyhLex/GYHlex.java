//funcao principal do programa, tem o intuito de chamar as demais funcoes parar analizar o programa

package gyhLex;

import java.io.File;

public class GYHlex {//classe principal
	
	public static void main(String[] args) { //main do programa

		GYHLexico lex = new GYHLexico(args[0]);

		Token t = null;
        while((t=lex.proximoToken()).nome != TipoToken.Fim) { // chama as funcoes de leitura dos tokens ate o fim do aquivo
            System.out.print(t);
        }
        
	}
}
