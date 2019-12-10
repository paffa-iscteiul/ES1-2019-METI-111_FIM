package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegraTest {

	static Regra rule;
	static Regra rule1; 

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rule = new Regra("CYCLO < 3 AND ATFD > 1", "is_feature_envy = TRUE", "is_feature_envy = FALSE");
		rule1 = new Regra("CYCLO < 3 OR ATFD > 1", "is_feature_envy = TRUE", "is_feature_envy = FALSE");
	}

	@Test
	void testGetVetor_metricas() {
		assertNotNull(rule.getVetor_metricas());
	}

	@Test
	void testGetMetricas() {
		assertNotNull(rule.getMetricas());
	}

	@Test
	void testGetEntao() {
		assertNotNull(rule.getEntao());
	}

	@Test
	void testGetSenao() {
		assertNotNull(rule.getSenao());
	}

	@Test
	void testGetOp() {
		assertNotNull(rule.getOp());
	}

}
