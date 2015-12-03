package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResAbstractMathSignatureImpl
        extends
            ResNamedElementImpl implements ResMathDefinitionSignature {

    public ResAbstractMathSignatureImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public List<ResMathVarDeclGroup> getParameters() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,
                ResMathVarDeclGroup.class);
    }

    @Nullable @Override public ResMathExp getMathTypeExp() {
        return findChildByClass(ResMathExp.class);
    }

    /**
     * This has to be {@code Nullable} at the moment; think about it: We have
     * infix and outfix signatures, how in the would we create the |..| needed?
     * In other words, its tough doing completion for non-identifiers.
     */
    @Nullable @Override public PsiElement getIdentifier() {
        return findChildByClass(ResMathNameIdentifier.class);
    }
}
