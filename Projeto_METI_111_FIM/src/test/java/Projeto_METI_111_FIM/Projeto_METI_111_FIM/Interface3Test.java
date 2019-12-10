package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Interface3Test {
	
	Interface3 int3;
	Regra rteste;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	void testInterface3() {
		int3 = new Interface3("int3", new ArrayList<Record>());
		assertDoesNotThrow(()->int3);
	}

	@Test
	void testAddFrameContent() {
		
	}

}
