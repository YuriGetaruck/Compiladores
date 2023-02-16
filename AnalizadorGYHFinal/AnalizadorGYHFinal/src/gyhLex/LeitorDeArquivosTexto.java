//classe de leitura dos arquivos de texto
package gyhLex;

//imports do programa
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LeitorDeArquivosTexto {// classe leitor de arquivos
	
	InputStream is;
	
	public LeitorDeArquivosTexto(String arquivo) { // cria o arquivo a partir do parametro de entrada
		
		try {
            is = new FileInputStream(new File(arquivo));
            inicializarBuffer();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
	
	private final static int TAMANHO_BUFFER = 20;// buffer utilizado para armazenar os dados analizados
    int[] bufferDeLeitura;
    int ponteiro;
    int bufferAtual;
    int inicioLexema;
    private String lexema;
    
    private void inicializarBuffer() {// inicia o buffer
    	bufferAtual = 2;
    	inicioLexema = 0;
    	lexema = "";
        bufferDeLeitura = new int[TAMANHO_BUFFER * 2];
        ponteiro = 0;
        recarregarBuffer1();
    }
    
    private int lerCaractereDoBuffer() { // le o caractere do buffer
        int ret = bufferDeLeitura[ponteiro];
        incrementarPonteiro();
        return ret;
    }
    
    private void incrementarPonteiro() { // incrementa o ponteiro do buffer
        ponteiro++;
        if (ponteiro == TAMANHO_BUFFER) {
            recarregarBuffer2();
        } else if (ponteiro == TAMANHO_BUFFER * 2) {
            recarregarBuffer1();
            ponteiro = 0;
        }
    }
    
    private void recarregarBuffer1() { // funcao primaria para recarregar o buffer 1
    	if (bufferAtual == 2) {
    		bufferAtual = 1;
    	
	        try {
	            for (int i = 0; i < TAMANHO_BUFFER; i++) {
	                bufferDeLeitura[i] = is.read();
	                if (bufferDeLeitura[i] == -1) {
	                    break;
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace(System.err);
	        }
    	}
    }
    
    private void recarregarBuffer2() { // funcao primaria para recarregar o buffer 2
    	if (bufferAtual == 1) {
    		bufferAtual = 2;
	        try {
	            for (int i = TAMANHO_BUFFER; i < TAMANHO_BUFFER * 2; i++) {
	                bufferDeLeitura[i] = is.read();
	                if (bufferDeLeitura[i] == -1) {
	                    break;
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace(System.err);
	        }
	    }
    }
    
    public int LerProxCaractere() { // le o proximo caractere do buffer
        int c = lerCaractereDoBuffer();
        lexema += (char) c;
        return c;
    }
    
    public void retroceder() { //volta um caractere no buffer
        ponteiro--;
        lexema = lexema.substring(0, lexema.length() - 1);
        if (ponteiro < 0) {
            ponteiro = TAMANHO_BUFFER * 2 - 1;
        }
    }
    
	public void zerar() { // zera o ponteiro do buffer
		ponteiro = inicioLexema;
		lexema = "";
	}
	
	public void confirmar() {
		inicioLexema = ponteiro;
		lexema = "";
	}
	
	public String getLexema() { //retorna o lexema 
		return lexema;
	}
	
	public String toString() { // retorna o erro lexico do programa
		String ret = "Buffer:[";
		for (int i : bufferDeLeitura) {
			char c = (char) i;
			if (Character.isWhitespace(c)) {
				ret += ' ';
			} else {
				ret += (char) i;
			}
		}
		ret += "]\n";
		ret += "        ";
		for (int i = 0; i < TAMANHO_BUFFER * 2; i++) {
			if (i == inicioLexema && i == ponteiro) {
				ret += "%";
			} else if (i == inicioLexema) {
				ret += "^";
			} else if (i == ponteiro) {
				ret += "*";
			} else {
				ret += " ";
			}
		}
	return ret;
	}
	
}
