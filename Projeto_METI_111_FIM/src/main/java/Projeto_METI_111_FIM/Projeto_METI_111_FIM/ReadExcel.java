package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* Esta classe permite a leitura do ficheiro excel
*
* @author  Pedro Fonseca, Fábio Cardoso, Filipa Gomes, Inês Gomes, Sofia Pérsio, Marco Silva
*/
public class ReadExcel {
	private ArrayList<Record> records=new ArrayList<Record>();
	private Openfile of;
	
	private FileInputStream fis;
	private int rowCount;
	private int columnCount;
	private ArrayList<String> columnNames;
	private ArrayList<String> data;
	private XSSFWorkbook workbook;
	private static ReadExcel INSTANCE = null;
	
	
	/**
	 * Construtor da classe
	 * Recebe o Ficheiro Excel e a partir deste conta o nº de linhas e colunas de modo a
	 * criar a matrix de informação que posteriormente irá ser introduzida no ExcelDisplay.java
	 * @param excelF
	 * @throws IOException
	 */
	public ReadExcel(File excelF) throws IOException{
		fis = new FileInputStream(excelF);
		columnNames = new ArrayList<String>();
		data = new ArrayList<String>();
		workbook = new XSSFWorkbook(fis);
		XSSFSheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Row nextRow = iterator.next();
		this.rowCount = ((XSSFSheet) firstSheet).getLastRowNum();
		this.columnCount = nextRow.getLastCellNum();
		ReadFile();
  }
	/**
	 * ReadFile() É uma função que armazena toda a informação do ficheiro Excel
	 * nas listas de informação
	 * @throws IOException 
	 */
	public void ReadFile() throws IOException {
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIt = sheet.iterator();
		Boolean Titles = true;
		while(rowIt.hasNext()) {
			Row row = rowIt.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			if(Titles == true) {
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					columnNames.add(cell.toString());
				}
			}else {
				Record rec = new Record();
				int i=0;
				while (cellIterator.hasNext()) {
					i++;
					Cell cell = cellIterator.next();
					rec.add(i, cell.toString());
					data.add(cell.toString());
				}
				records.add(rec);
			}
			Titles = false;
		}
	}
	
	/**
	 * getColumnNames() Irá buscar toda a informação sobre os Titulos e 
	 * converter de ArrayList para Vector
	 * @return columnVector, são os títulos do excel, 1º Linha
	 */
	public String [] getColumnNames() {
		String [] columnVector = new String [columnCount];
		for(int i = 0; i < columnCount; i++) {
			columnVector [i] = columnNames.get(i);
		}
	return columnVector;
	}
	
	/**
	 * getData() Irá buscar toda a informação, sem contar com os titulos.
	 * Para além de converter Arraylist em Matriz
	 * @return Matriz de Data do Excel
	 */
	public String [] [] getData(){
		String [] [] dataMatrix = new String [rowCount] [columnCount];
		int index = 0;
		for(int i = 0; i < (rowCount); i++) {
			for(int j = 0; j < columnCount; j++) {
				dataMatrix [i] [j] = this.data.get(index);
				index++;
			}
		}
		return dataMatrix;
	}
	
	/**
	 * getRecord() Devolve lista de registos do excel
	 * @return records lista de registos do excel
	 */
	public ArrayList<Record> getRecord() {
		return records;
	}
	
	
}
