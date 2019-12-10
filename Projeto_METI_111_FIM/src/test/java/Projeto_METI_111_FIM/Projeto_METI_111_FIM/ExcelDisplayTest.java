package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.junit.jupiter.api.Test;

public class ExcelDisplayTest {

	static ReadExcel readExcel;
	static ExcelDisplay excelDisplay;

	@Test
	public void testExcelDisplayWorks() throws IOException {
		File excelfile = new File("files/Long-Method.xlsx");
		ReadExcel readExcel = new ReadExcel(excelfile);
		ExcelDisplay.startInstance(readExcel);
	}

	@Test
	public void testExcelDisplayFileDontExists() throws IOException {
		File excelfile = new File("files/Long-Method2.xlsx");
		assertThrows(IOException.class, () -> readExcel = new ReadExcel(excelfile));
	}

	@Test
	public void testExcelDisplayFileInvalid() throws IOException {
		File excelfile = new File("files/teste.txt");
		assertThrows(Exception.class, () -> readExcel = new ReadExcel(excelfile));
		// ExcelDisplay.startInstance(readExcel);
	}

	@Test
	public void testStartInstance() throws IOException {
		File excelfile = new File("files/Long-Method.xlsx");
		ReadExcel readExcel = new ReadExcel(excelfile);
		ExcelDisplay object1 = ExcelDisplay.startInstance(readExcel);
		ExcelDisplay object2 = ExcelDisplay.startInstance(readExcel);
		assertEquals(object1, object2);
	}

	@Test
	void testGetRecord() throws IOException {
		File excelfile = new File("files/Long-Method.xlsx");
		readExcel = new ReadExcel(excelfile);
		assertNotNull(readExcel.getRecord());
	}

	@Test
	void testGetInstance() {
		excelDisplay = new ExcelDisplay(readExcel);
		assertNotNull(excelDisplay);
	}

	@Test
	void testGetJ() {
		assertNotNull(ExcelDisplay.getInstance().getJ());
	}

	@Test
	void testSetJ() {
		JTable table = new JTable();
		assertDoesNotThrow(() -> ExcelDisplay.getInstance().setJ(table));
	}

	@Test
	void testGetSp() {
		assertNotNull(ExcelDisplay.getInstance().getSp());
	}

	@Test
	void testSetSp() {
		JScrollPane panel = new JScrollPane();
		assertDoesNotThrow(() -> ExcelDisplay.getInstance().setSp(panel));
	}

	@Test
	void testGetData() {
		assertNotNull(ExcelDisplay.getInstance().getData());
	}

}
