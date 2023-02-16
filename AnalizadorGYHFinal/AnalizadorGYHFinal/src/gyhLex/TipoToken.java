//define os tipos de tokens que podem existir no programa recebido

package gyhLex;

public enum TipoToken {
	
	PCDec, PCProg, PCInt, PCLer, PCSe, PCEntao, PCEnqto, PCini, PCfim,
	
	OpAritMult, OpAritDiv, OpAritSoma, OpAritSub,
	
	OpRelMenor, OpRelMenorIgual, OpRelMaior, OpRealMaiorIgual, OpRelIgual, OpRelDif,
	
	OpBoolE, OpBoolOu,
	
	Delim, 
	
	Atrib,
	
	AbrePar, FechaPar,
	
	Var, NumInt, Cadeia, OpRelMaiorIgual, PCImprimir, Fim
	
}




