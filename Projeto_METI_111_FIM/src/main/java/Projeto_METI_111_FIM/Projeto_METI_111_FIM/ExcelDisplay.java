package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
* Esta classe permite a leitura e visualização do ficheiro excel
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class ExcelDisplay {

	private JTable table;
	private JScrollPane sp;
	private String [] [] data; 
	private static ExcelDisplay INSTANCE = null;
	
	/**
	 * Inicializa todos os parametros do Display
	 * @param excel
	 */
	public ExcelDisplay(ReadExcel excel) {		
		//Column Names
		String [] columnNames = excel.getColumnNames();
		
		//Excel Data
		data = excel.getData();
		
		//Initializing the JTable
		table = new JTable(data, columnNames);
		table.setBounds(30, 40, 200, 300);
		
		//Adding it to JScrollPane
		sp = new JScrollPane(table);
	}
	

	/**
	 * startInstance(ReadExcel readExcel)
	 * getInstance()
	 * Foi criada uma instância de display para que o display 
	 * seja de fácil acesso e facilmente actualizavel
	 * @param readExcel
	 * @return Instância do Display
	 */
	public static ExcelDisplay startInstance(ReadExcel readExcel) {
		if(INSTANCE == null) {
	         INSTANCE = new ExcelDisplay(readExcel);
	      }
	      return INSTANCE;
	}
	

	/**
	 * getInstance() Devolve a instância de ExcelDisplay
	 * @return INSTANCE
	 */
	public static ExcelDisplay getInstance() {
		return INSTANCE;
	}

	/**
	 * getJ() Devolve a tabela
	 * @return table tabela excel
	 */
	public JTable getJ() {
		return table;
	}

	/**
	 * setJ() Coloca tabela j no atributo table
	 * @param j tabela a atributir ao atributo table
	 */
	public void setJ(JTable j) {
		this.table = j;
	}

	/**
	 * getSp() Devolve scrollpane
	 * @return sp scrollpane
	 */
	public JScrollPane getSp() {
		return sp;
	}
	
	/**
	 * setSp() Coloca scrollpane sp no atributo sp
	 * @param sp scrollpane
	 */	
	public void setSp(JScrollPane sp) {
		this.sp = sp;
	}
	
	/**
	 * getData() Devolve a matriz de strings que representa a tabela
	 * @return data matriz de strings que representa a tabela
	 */
	public String[][] getData() {
		return data;
	}
}