package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenfileTest {

	static Interface1 int1;
	static Openfile opf;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		int1 = new Interface1();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testOpenfile() {
		opf = new Openfile(int1);
	}

	@Test
	void testBrowser() throws Exception {
		assertThrows(Exception.class, () -> opf.Browser());
	}

	@Test
	void testGetRecords() {
		assertNotNull(opf.getRecords());
	}

}
