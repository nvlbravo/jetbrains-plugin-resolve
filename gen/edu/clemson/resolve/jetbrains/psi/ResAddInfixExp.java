// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResAddInfixExp extends ResExp {

  @NotNull
  List<ResExp> getExpList();

  @Nullable
  PsiElement getMinus();

  @Nullable
  PsiElement getPlus();

}
