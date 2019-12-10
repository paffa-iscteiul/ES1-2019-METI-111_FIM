package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import java.io.IOException;

/**
* Esta classe permite definir uma estrutura de dados para os registos do ficheiro Excel
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class Record {
	String methodID;
	String pack;
	String classe;
	String method;
	String loc;
	String cyclo;
	String atfd;
	String laa;
	String islongmethod;
	String iplasma;
	String pmd;
	String isfeatureenvy;
	
	/**
	 * Construtor da classe
	 * Recebe os vários valores para os atributos, ou seja, os vários valores para cada coluna do ficheiro Excel
	 * @param methodID id do método
	 * @param pack package do método
	 * @param classe classe do método
	 * @param method método
	 * @param loc linhas de código do método
	 * @param cyclo complexidade ciclomátca do método
	 * @param atfd acessos do método a métodos de outras classes
	 * @param laa acessos do método a atributos da própria classe
	 * @param islongmethod se o método é longo
	 * @param iplasma classificação da ferramenta de deteçao de defeitos iplasma
	 * @param pmd classificação da ferramenta de deteçao de defeitos pmd
	 * @param isfeatureenvy se o método é featureenvy
	 * @throws IOException importante
	 */
	public Record(String methodID, String pack, String classe, String method, String loc, String cyclo,
			String atfd, String laa, String islongmethod, String iplasma, String pmd, String isfeatureenvy) throws IOException {
		this.methodID=methodID;
		this.pack=pack;
		this.classe=classe;
		this.method=method;
		this.loc=loc;
		this.cyclo=cyclo;
		this.atfd=atfd;
		this.laa=laa;
		this.islongmethod=islongmethod;
		this.iplasma=iplasma;
		this.pmd=pmd;
		this.isfeatureenvy=isfeatureenvy;
	}
	
	/**
	 * Construtor da classe
	 * Serve para criar uma instância, cujos atributos se encontram vazios
	 * @throws IOException importante
	 */
	public Record() throws IOException {}
	
	/**
	 * getMethodID() Irá retornar o ID do método do registo em causa (linha do excel)
	 * @return methodID, ID do método
	 */
	public String getMethodID() {
		return methodID;
	}
	
	/**
	 * getPack() Irá retornar o Package do método do registo em causa (linha do excel)
	 * @return pack, Package do método
	 */
	public String getPack() {
		return pack;
	}
	
	/**
	 * getClasse() Irá retornar a classe do método do registo em causa (linha do excel)
	 * @return classe, classe do método
	 */
	public String getClasse() {
		return classe;
	}
	
	/**
	 * getMethod() Irá retornar o método do registo em causa (linha do excel)
	 * @return method, método
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * getLoc() Irá retornar linhas de código do método do registo em causa (linha do excel)
	 * @return loc, linhas de código do método
	 */
	public String getLoc() {
		return loc;
	}
	
	/**
	 * getCyclo() Irá retornar a complexidade ciclomática do método do registo em causa (linha do excel)
	 * @return cyclo, complexidade ciclomática do método
	 */
	public String getCyclo() {
		return cyclo;
	}
	
	/**
	 * getAtfd() Irá retornar o número de acessos do método do registo em causa 
	 * (linha do excel) a método exteriores
	 * @return atfd, atfd do método
	 */
	public String getAtfd() {
		return atfd;
	}
	
	/**
	 * getLaa() Irá retornar o número de acessos do método do registo em causa 
	 * (linha do excel) a atributos da própria classe
	 * @return laa, laa do método
	 */
	public String getLaa() {
		return laa;
	}
	
	/**
	 * getIsLongMethod() Irá retornar se o método do registo em causa (linha do excel) é longo método
	 * @return islongmethod, se o método é longo (True or False)
	 */
	public String getIsLongMethod() {
		return islongmethod;
	}
	
	/**
	 * getIplasma() Irá retornar a classificação da ferramente iPlasma sobre 
	 * o método do registo em causa (linha do excel)
	 * @return iplasma, classificação iplasma sobre método
	 */
	public String getIPlasma() {
		return iplasma;
	}
	
	/**
	 * getPmd() Irá retornar a classificação da ferramente PMD sobre 
	 * o método do registo em causa (linha do excel)
	 * @return pmd, classificação PMD sobre método
	 */
	public String getPmd() {
		return pmd;
	}
	
	/**
	 * getIsFeatureEnvy() Irá retornar se o método do registo em causa (linha do excel) é feature_envy
	 * @return isfeatureenvy, se o método é feature_envy (True or False)
	 */
	public String getIsFeatureEnvy() {
		return isfeatureenvy;
	}
	
	/**
	 * add() É uma função que armazena a informação s, segundo determinado indice
	 * @param i indice
	 * @param s valor de atributo
	 */
	public void add(int i, String s) {
		if(i==1) methodID=s;
		if(i==2) pack=s;
		if(i==3) classe=s;
		if(i==4) method=s;
		if(i==5) loc=s;
		if(i==6) cyclo=s;
		if(i==7) atfd=s;
		if(i==8) laa=s;
		if(i==9) islongmethod=s;
		if(i==10) iplasma=s;
		if(i==11) pmd=s;
		if(i==12) isfeatureenvy=s;
	}
	
	/**
	 * getRegisto() Irá retornar o registo do método em causa (linha do excel)
	 * @return (methodID+" "+pack+" "+classe+" "+method+" "+loc+" "+cyclo+" "+atfd+" 
	 * "+laa+" "+islongmethod+" "+iplasma+" "+pmd+" "+isfeatureenvy), registo do método (linha do excel)
	 */
	public String getRegisto() {
		return (methodID+" "+pack+" "+classe+" "+method+" "+loc+" "+cyclo+" "+atfd+" "+laa+" "+islongmethod
				+" "+iplasma+" "+pmd+" "+isfeatureenvy);
	}
			
}
