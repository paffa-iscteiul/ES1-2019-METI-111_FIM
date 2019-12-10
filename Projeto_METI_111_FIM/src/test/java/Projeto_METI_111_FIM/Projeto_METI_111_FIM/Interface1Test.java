package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Interface1Test {

	Interface1 int1;

	@Test
	void testMain() {
		int1.main(null);
	}

	@Test
	void testClickFinish() {
		int1 = new Interface1();
		Openfile opf = new Openfile(int1);
		opf.Excelfile = new File("files/Long-Method.xlsx");
		int1.browser.doClick();
		int1.finish.doClick();
		int1.browser.doClick();
		int1.finish.doClick();
		int1.browser.doClick();
		int1.finish.doClick();
	}

}
