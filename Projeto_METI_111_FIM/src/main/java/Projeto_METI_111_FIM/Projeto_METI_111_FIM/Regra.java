package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

/**
* Esta classe permite definir uma estrutura de dados para a criação de regras
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class Regra {
	public int numero_de_regras;
	public String[] vetor_metricas;
	public String entao;
	public String senao;
	public String op;
	public String metricas;
	
	
	/**
	 * Construtor da classe
	 * Recebe o corpo de IF, recebe medidas a adotar em caso de regra verificada ou não
	 * @param metricas metricas (corpo de IF)
	 * @param metricas entao (corpo de THEN)
	 * @param metricas senao (corpo de ELSE)
	 */
	public Regra (String metricas, String entao, String senao ) {
		this.entao = entao;
		this.senao=senao;
		this.metricas=metricas;
		
		analisar_parametros(metricas);
		
	}
	
	/**
	 * analisar_parametros() É um procedimento que permite o processamento dos valores recebidos na construção da instância
	 * @param metricas
	 */
	public void analisar_parametros(String metricas) {
		numero_de_regras=count_ocorrences(metricas);
		String metricas1 = metricas;
			if(metricas.contains("OR")) {
				op="OR";
				metricas1 = metricas.replace("OR ", "");
			}
			if(metricas.contains("AND")) {
				op="AND";
				metricas1 = metricas.replace("AND ", "");
			}
			
			String aux[] = metricas1.split(" ");
			String metricas2[] = new String [aux.length / 3];
				for(int i = 0; i<aux.length;i=i+3) {
					metricas2[i/3]= aux[i]+aux[i+1]+aux[i+2];
				}
				vetor_metricas=metricas2 ;
	}
	
	/**
	 * count_ocorrences() É um procedimento que permite o cálculo do número de ocorrências de AND ou OR em m
	 * @param m
	 * @return count
	 */
	private int count_ocorrences(String m) {
		String a1[]=m.split(" ");
		int n= a1.length;
		int count = 1 ;
			for(int i = 0; i <n; i++) {
				if(a1[i].equals("AND")|| a1[i].equals("OR")) {
					count ++ ;
				}
			}
		
		
		return count;
	}

	/**
	 * getVetor_metricas() É uma função que retorna métricas a serem verificadas em forma de lista
	 * @return vetor_metricas lista de métricas a serem verificadas
	 */
	public String[] getVetor_metricas() {
		return vetor_metricas;
	}
	
	/**
	 * getMetricas() É uma função que retorna métrica
	 * @return metricas métrica
	 */
	public String getMetricas() {
		return metricas;
	}
	
	/**
	 * getEntao() É uma função que retorna o interior de THEN
	 * @return entao 
	 */
	public String getEntao() {
		return entao;
	}

	/**
	 * getSenao() É uma função que retorna o interior de ELSE
	 * @return senao 
	 */
	public String getSenao() {
		return senao;
	}
	
	/**
	 * getOp() É uma função que retorna o operador da regra
	 * @return op 
	 */
	public String getOp() {
		return op;
	}
	
}
