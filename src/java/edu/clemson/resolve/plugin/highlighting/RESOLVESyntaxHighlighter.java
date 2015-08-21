package edu.clemson.resolve.plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.RESOLVELanguage;
import edu.clemson.resolve.plugin.RESOLVETokenTypes;
import edu.clemson.resolve.plugin.adaptors.RESOLVELexerAdaptor;
import edu.clemson.resolve.plugin.parser.Resolve;
import edu.clemson.resolve.plugin.parser.ResolveLexer;
import org.antlr.intellij.adaptor.lexer.TokenElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RESOLVESyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey PARAMETER_MODE =
            createTextAttributesKey("PARAMETER_MODE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATOR =
            createTextAttributesKey("BUILTIN_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey JAVADOC_COMMENT =
            createTextAttributesKey("JAVADOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{HighlighterColors.BAD_CHARACTER};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT, JAVADOC_COMMENT, BLOCK_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull @Override public Lexer getHighlightingLexer() {
        ResolveLexer lexer = new ResolveLexer(null);
        return new RESOLVELexerAdaptor(RESOLVELanguage.INSTANCE, lexer);
    }

    @NotNull @Override public TextAttributesKey[] getTokenHighlights(
            IElementType tokenType) {
        if ( RESOLVETokenTypes.KEYWORDS.contains(tokenType) ){
            /*TokenElementType ThenType = RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.THEN);
            if (ThenType.equals(tokenType)) {
                int i;
                i=0;
            }*/
            return new TextAttributesKey[]{KEYWORD};
        }
        else if ( RESOLVETokenTypes.PARAMETER_MODES.contains(tokenType) ) {
            return new TextAttributesKey[]{PARAMETER_MODE};
        }
        else if (RESOLVETokenTypes.BUILTIN_OPERATORS.contains(tokenType)) {
            return new TextAttributesKey[]{OPERATOR};
        }

        if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.STRING)) {
            return STRING_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.INT)) {
            return NUMBER_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.BLOCK_COMMENT)) {
            return COMMENT_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.DOC_COMMENT)) {
            return COMMENT_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.TOKEN_ELEMENT_TYPES.get(ResolveLexer.LINE_COMMENT)) {
            return COMMENT_KEYS;
        }
        else if (tokenType == RESOLVETokenTypes.BAD_TOKEN_TYPE) {
            return BAD_CHAR_KEYS;
        }
        else {
            return EMPTY_KEYS;
        }
    }
}
