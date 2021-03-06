package edu.clemson.resolve.jetbrains.parser;

import com.intellij.execution.testframework.actions.TestTreeExpander;
import com.intellij.execution.testframework.sm.runner.GeneralToSMTRunnerEventsConvertor;
import com.intellij.execution.testframework.sm.runner.states.TestPassedState;
import com.intellij.testFramework.ParsingTestCase;
import edu.clemson.resolve.jetbrains.RESOLVEParserDefinition;

public class RESOLVEParserTest extends ParsingTestCase {

    public RESOLVEParserTest() {
        super("parser", "resolve", new RESOLVEParserDefinition());
    }

    @Override protected String getTestDataPath() {
        return "testData";
    }

    @Override protected boolean skipSpaces() {
        return true;
    }

    protected void doTest(boolean checkErrors) {
        super.doTest(true);
        if (checkErrors) {
            assertFalse(
                    "PsiFile contains error elements",
                    toParseTreeText(myFile, skipSpaces(), includeRanges())
                            .contains("PsiErrorElement")
            );
        }
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        //TODO: register any matching extensions you might provide in the
        //future here -- for instance, codeblock insertionhandler
    }

    public void testEmpty_File() { doTest(true); }
    public void testEmpty_File_Comments() {
        TestTreeExpander x;
        doTest(true);
    }
    public void testEmpty_Module() { doTest(true); }
    public void testDefs_and_Theorems() { doTest(true); }

    //public void testModule_Block_Error_Recover() { doTest(false); }
    //public void testModule_Block_Error_Recover2() { doTest(false); }
    public void testModule_Block_Error_Recover3() { doTest(false); }
    //public void testOp_Error_Recover() { doTest(false); }

}
