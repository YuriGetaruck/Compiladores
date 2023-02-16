//Classe GYHLexico, gera a saida do analizador lexico 
package gyhLex;

public class GYHLexico {
	
    LeitorDeArquivosTexto ldat; //leitor de arquivos
    
    public GYHLexico(String arquivo) {
        ldat = new LeitorDeArquivosTexto(arquivo);
    }
    
    
    public Token proximoToken() {//chama todas as funcoes ate o fim do arquivo para efetuar a leitura dos tokens
        Token proximo = null;
        espacosEComentarios();
        ldat.confirmar();
        proximo = fim();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = palavrasChave();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = variavel();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = numInt();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorAritmetico();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadorRelacional();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = delimAtrib();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = parenteses();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = cadeia();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        System.err.println("Erro léxico!");
        System.err.println(ldat.toString());
        return null;
    }
    
    private Token operadorAritmetico() { //define os operadores aritimeticos
        int caractereLido = ldat.LerProxCaractere();
        char c = (char) caractereLido;
        if (c == '*') {
            return new Token(TipoToken.OpAritMult, ldat.getLexema());
        } else if (c == '/') {
            return new Token(TipoToken.OpAritDiv, ldat.getLexema());
        } else if (c == '+') {
            return new Token(TipoToken.OpAritSoma, ldat.getLexema());
        } else if (c == '-') {
            return new Token(TipoToken.OpAritSub, ldat.getLexema());
        } else {
            return null;
        }
    }
    
    
    private Token parenteses() { // define a utilização dos parenteses
        int caractereLido = ldat.LerProxCaractere();
        char c = (char) caractereLido;
        if (c == '(') {
            return new Token(TipoToken.AbrePar, ldat.getLexema());
        } else if (c == ')') {
            return new Token(TipoToken.FechaPar, ldat.getLexema());
        } else {
            return null;
        }
    }
    
    private Token operadorRelacional(){ //define os aporadores relacionais
		int caractereLido = ldat.LerProxCaractere();
		char c = (char) caractereLido;
		if(c == '<') {
			c =  (char)ldat.LerProxCaractere();
			if(c == '='){
				return new Token(TipoToken.OpRelMenorIgual, "<=");
			}else {
				ldat.retroceder();
				return new Token(TipoToken.OpRelMenor, "<");
			}
		}else if(c == '>'){
			c =  (char) ldat.LerProxCaractere();
			if(c == '='){
				return new Token(TipoToken.OpRelMaiorIgual, ">=");
			}else{
				ldat.retroceder();
				return new Token(TipoToken.OpRelMaior, ">");
			}
		}else if(c == '=') {
			return new Token(TipoToken.OpRelIgual, "=");
		}else if(c == '!') {
			c =  (char) ldat.LerProxCaractere();
			if(c == '='){
				return new Token(TipoToken.OpRelDif, "!=");
			}
		}
		return null;
	}
    
    private Token numInt() { //define os numeros inteiros
		int estado = 1;
		while(true) {
			char c = (char) ldat.LerProxCaractere();
			if(estado == 1) {
				if(Character.isDigit(c)) {
					estado = 2;
				}else {
					return null;
				}
			}else if(!Character.isDigit(c)) {
				ldat.retroceder();
				return new Token(TipoToken.NumInt, ldat.getLexema());
			}
		}
	}
    
    private Token variavel() { //define as variaiveis 
        int estado = 1;
        while (true) {
            char c = (char) ldat.LerProxCaractere();
            if (estado == 1) {
                if (Character.isLetter(c)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (!Character.isLetterOrDigit(c)) {
                    ldat.retroceder();
                    return new Token(TipoToken.Var, ldat.getLexema());
                }
            }
        }
    }
    
    private Token cadeia() { // define as cadeia de caracteres
        int estado = 1;
        while (true) {
            char c = (char) ldat.LerProxCaractere();
            if (estado == 1) {
                if (c == '\'') {
                    estado = 2;
                } else {
                    return null;
                }
            } else if (estado == 2) {
                if (c == '\n') {
                    return null;
                }
                if (c == '\'') {
                    return new Token(TipoToken.Cadeia, ldat.getLexema());
                } else if (c == '\\') {
                    estado = 3;
                }
            } else if (estado == 3) {
                if (c == '\n') {
                    return null;
                } else {
                    estado = 2;
                }
            }
        }
    }
    
    private void espacosEComentarios() { // define os espacos para comentarios 
        int estado = 1;
        while (true) {
            char c = (char) ldat.LerProxCaractere();
            if (estado == 1) {
                if (Character.isWhitespace(c) || c == ' ') {
                    estado = 2;
                } else if (c == '#') {
                    estado = 3;
                } else {
                    ldat.retroceder();
                    return;
                }
            } else if (estado == 2) {
                if (c == '#') {
                    estado = 3;
                } else if (!(Character.isWhitespace(c) || c == ' ')) {
                    ldat.retroceder();
                    return;
                }
            } else if (estado == 3) {
                if (c == '\n') {
                    return;
                }
            }
        }
    }
    
    private Token delimAtrib() { // define os delimitadores e tambem as atribuicoes devido a semelhança entre as funcoes
		int caractereLido = ldat.LerProxCaractere();
		char c = (char) caractereLido;
		if(c == ':'){
			c = (char) ldat.LerProxCaractere();
			if(c == '=') {
				return new Token(TipoToken.Atrib, ":=");
			}else {
				ldat.retroceder();
				return new Token(TipoToken.Delim, ":");
			}
		}else {
			return null;
		}
	}
    
    private Token palavrasChave() { // define as palavras chaves como tokens 
        while (true) {
            char c = (char) ldat.LerProxCaractere();
            if (!Character.isLetter(c)) {
                ldat.retroceder();
                String lexema = ldat.getLexema();
                if (lexema.equals("DEC")) {
                    return new Token(TipoToken.PCDec, lexema);
                } else if (lexema.equals("INT")) {
                    return new Token(TipoToken.PCInt, lexema);
                } else if (lexema.equals("ATRIBUIR")) {
                    return new Token(TipoToken.Atrib, lexema);
                } else if (lexema.equals("LER")) {
                    return new Token(TipoToken.PCLer, lexema);
                } else if (lexema.equals("IMPRIMIR")) {
                    return new Token(TipoToken.PCImprimir, lexema);
                } else if (lexema.equals("SE")) {
                    return new Token(TipoToken.PCSe, lexema);
                } else if (lexema.equals("ENTAO")) {
                    return new Token(TipoToken.PCEntao, lexema);
                } else if (lexema.equals("ENQTO")) {
                    return new Token(TipoToken.PCEnqto, lexema);
                } else if(lexema.equals("PROG")) {
					return new Token(TipoToken.PCProg, lexema);
				}else if (lexema.equals("INI")) {
                    return new Token(TipoToken.PCini, lexema);
                } else if (lexema.equals("FIM")) {
                    return new Token(TipoToken.PCfim, lexema);
                } else if (lexema.equals("E")) {
                    return new Token(TipoToken.OpBoolE, lexema);
                } else if (lexema.equals("OU")) {
                    return new Token(TipoToken.OpBoolOu, lexema);
                } else {
                    return null;
                }
            }
        }
    }
    
    private Token fim(){ // define o fim do loop
		int caracterelido = ldat.LerProxCaractere();
		if(caracterelido == -1) {
			return new Token(TipoToken.Fim, "Fim");
		}else{
			return null;
		}
	}
}
    