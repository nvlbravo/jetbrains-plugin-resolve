package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.Nullable;

public interface ResProgTypeOwner extends ResCompositeElement {

    @Nullable ResProgType getResProgType(@Nullable ResolveState context);
}