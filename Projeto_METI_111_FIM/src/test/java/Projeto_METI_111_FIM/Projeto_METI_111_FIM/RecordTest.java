package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordTest {
	
	static Record r ;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		r = new Record("a","b","c","d","e","f","g","h","i","j","k","l");
	}

	@Test
	void testRecordStringStringStringStringStringStringStringStringStringStringStringString() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetMethodID() throws IOException {
		assertEquals("a", r.getMethodID());
	}

	@Test
	void testGetPack() {
		assertEquals("b", r.getPack());
	}

	@Test
	void testGetClasse() {
		assertEquals("c", r.getClasse());
	}

	@Test
	void testGetMethod() {
		assertEquals("d", r.getMethod());
	}

	@Test
	void testGetLoc() {
		assertEquals("e", r.getLoc());
	}

	@Test
	void testGetCyclo() {
		assertEquals("f", r.getCyclo());
	}

	@Test
	void testGetAtfd() {
		assertEquals("g", r.getAtfd());
	}

	@Test
	void testGetLaa() {
		assertEquals("h", r.getLaa());
	}

	@Test
	void testGetIsLongMethod() {
		assertEquals("i", r.getIsLongMethod());
	}

	@Test
	void testGetIPlasma() {
		assertEquals("j", r.getIPlasma());
	}

	@Test
	void testGetPmd() {
		assertEquals("k", r.getPmd());
	}

	@Test
	void testGetIsFeatureEnvy() {
		assertEquals("l", r.getIsFeatureEnvy());
	}


	@Test
	void testGetRegisto() {
		assertEquals("a b c d e f g h i j k l", r.getRegisto());;
	}

}
