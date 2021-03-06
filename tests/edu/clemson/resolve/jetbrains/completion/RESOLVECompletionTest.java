package edu.clemson.resolve.jetbrains.completion;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class RESOLVECompletionTest extends RESOLVECompletionTestBase {

    @NotNull @Override protected String getBasePath() {
        return "completion";
    }

    public void testSimpleUsesRef() throws IOException {
        myFixture.getTempDirFixture().createFile("A.resolve", "Precis A; Definition xss : V; end A;");
        myFixture.getTempDirFixture().createFile("foo/C.resolve", "");
        myFixture.configureByText("C.resolve", "Precis C; uses A; Definition foo : <caret>");
        myFixture.completeBasic();
        List<String> lookupElementStrings = myFixture.getLookupElementStrings();
        assertNotNull(lookupElementStrings);
        assertContainsElements(lookupElementStrings, "xss");
    }

    /*public void testImplicitImport() throws IOException {
        myFixture.getTempDirFixture().createFile("A.resolve", "Precis A; Definition xss : V; end A;");
        myFixture.getTempDirFixture().createFile("foo/Ext.resolve", "");
        myFixture.configureByText("Ext.resolve", "Precis Extension Ext for A; Definition foo : <caret>");
        myFixture.completeBasic();
        List<String> lookupElementStrings = myFixture.getLookupElementStrings();
        assertNotNull(lookupElementStrings);
        assertContainsElements(lookupElementStrings, "xss");
    }*/

    public void testSimpleLocalMathRef() {
        doTestInclude("Precis Foo; Definition T1(xs, ys : Z) : B is <caret>",
                "xs", "ys", "T1");
    }

    public void testSimpleMathSetRef() {
        doTestInclude("Precis Foo; Definition meh : V1 " +
                        "Definition T1 : B is {xs : T | <caret> is_in V}",
                "xs", "meh", "T1");
    }

    public void testQuantifierBoundVarRef() {
        doTestInclude("Precis Foo; Theorem T: " +
                "Forall xs, ys : Z, Exists pz : B, <caret>; " +
                "end Foo;", "xs", "ys", "pz");
    }

    public void testSimpleProgSelectorAccessExp1() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "color", "year");
    }

    public void testSimpleProgSelectorAccessExp2() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "year", "color", "damaged", "used", "prev_owner");
    }

    public void testNestedProgSelectorAccessExp() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "smoker", "has_insurance");
    }

    public void testMathSelectorAccessExp2() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "x", "y", "z", "Curr_Place", "Curr_Pos");
    }

    //Too much in flux currently with this particular test.
    /*public void testExclusivelyHardCodedMathRefs() {
        doTestEquals("Precis Foo; Corollary C1: <caret>; " +
                        "end Foo",
                "Powerset", "Cls", "SSet", "or", "true", "false", "B",
                "implies", "not", "Entity", "El", "Exists", "Forall", "lambda");
    }*/

    //TODO: For some reason it looks like the addition of the console (for our
    //compiler caused this test to bomb with a "CompositeException (2
    public void testTopLevelModuleKeywords() {
        doTestInclude("//test comment \n\n <caret>", "Precis", "PrecisExt",
                "Facility", "Concept", "ConceptExt");
        /*myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "Precis", "PrecisExt", "Facility", "Concept", "ConceptExt",
                "Implementation");*/
    }

    public void testTopLevelPrecisModuleKeywords() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "uses", "Corollary", "Categorical", "Inductive", "Definition",
                "Implicit", "Theorem");
    }

    public void testTopLevelFacilityModuleKeywords() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "uses", "Definition", "Implicit", "OperationProcedure",
                "TypeRepresentation", "FacilityDeclaration");
    }

    //uses already declared, should be no completion for it
    public void testSimpleModuleUsesKeyword() {
        myFixture.testCompletionVariants(
                "./testData/completion/" + getTestName(false) + ".resolve",
                "Definition", "Implicit", "OperationProcedure",
                "TypeRepresentation", "FacilityDeclaration");
    }

    public void testComplicatedHeaderUsesKeyword() {
        doTestInclude("Concept T(type Entry; evaluates k : Int); <caret> end T;", "uses");
    }

    public void testTopLevelConceptModuleKeywords() {
        myFixture.testCompletionVariants("./testData/completion/" +
                getTestName(false) + ".resolve", "TypeFamily", "Implicit",
                "Definition", "constraints", "OperationDeclaration");
    }

    public void testOpParameterModeKeywords() {
        myFixture.testCompletionVariants("./testData/completion/" +
                        getTestName(false) + ".resolve", "updates",
                "evaluates", "restores", "preserves", "clears", "alters", "replaces");
    }

    public void testSpecModuleParameterKeywords() {
        myFixture.testCompletionVariants("./testData/completion/" +
                        getTestName(false) + ".resolve", "updates",
                "evaluates", "restores", "preserves", "clears", "alters",
                "replaces", "type", "Definition");
    }

    public void testTypeReprKeywordCombos() {
        doTestInclude("Implementation T for U; Type X = Record x : I end; <caret> end T;",
                "correspondence", "conventions", "initialization_repr");
        doTestInclude("Implementation T for U; Type X = Record x : I end; conventions x; <caret> end T;",
                "correspondence", "initialization_repr");
        doTestInclude("Implementation T for U; Type X = Record x : I end; correspondence x; <caret> end T;",
                "initialization_repr");
    }

    //TODO: will include "OperationDecl" & parameter modes (no 'type')
    public void testImplModuleParameterKeywords() {

    }
}