package edu.clemson.resolve.plugin;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class RESOLVESyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @NotNull @Override public SyntaxHighlighter getSyntaxHighlighter(
            Project project, VirtualFile virtualFile) {
        return new RESOLVESyntaxHighlighter();
    }
}