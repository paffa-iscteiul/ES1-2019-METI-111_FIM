package Projeto_METI_111_FIM.Projeto_METI_111_FIM;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Projeto_METI_111_FIM.Projeto_METI_111_FIM.ExcelDisplayTest;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.Interface1Test;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.Interface3Test;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.OpenfileTest;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.RecordTest;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.RegraTest;
import Projeto_METI_111_FIM.Projeto_METI_111_FIM.ResultDisplayTest;

@RunWith(JUnitPlatform.class)
@SelectClasses({ExcelDisplayTest.class, RecordTest.class, OpenfileTest.class, RegraTest.class, ResultDisplayTest.class,  Interface1Test.class, Interface3Test.class, ResultDisplayTest.class})
public class AllTests {

}
