package edu.clemson.resolve.plugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import edu.clemson.resolve.plugin.ResTypes;
import java.util.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static edu.clemson.resolve.plugin.RESOLVEParserDefinition.*;

%%

%{
  public _ResLexer() {
    this((java.io.Reader)null);
  }
%}

%unicode
%class _ResLexer
%implements FlexLexer, ResTypes
%function advance
%type IElementType

%eof{
  return;
%eof}

NL = [\r\n] | \r\n      // NewLine
WS = [ \t\f]            // Whitespaces

LINE_COMMENT = "//" [^\r\n]*

LETTER = [:letter:] | "_"
DIGIT =  [:digit:]

INT_DIGIT = [0-9]
//TODO: Octal & hex..

NUM_INT = "0" | ([1-9] {INT_DIGIT}*)

IDENT = {LETTER} ({LETTER} | {DIGIT} )*
STR =      "\""
ESCAPES = [abfnrtv]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
"/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")? { return MULTILINE_COMMENT; }

// Punctuation

"@"                                     { return AT; }
"..."                                   { return TRIPLE_DOT; }
"."                                     { return DOT; }

"'" [^\\] "'"                           { return CHAR; }
"'" \n "'"                              { return CHAR; }
"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }
"'\\'"                                  { return BAD_CHARACTER; }


"`" [^`]* "`"?                          { return RAW_STRING; }
{STR} ( [^\"\\\n\r] | "\\" ("\\" | {STR} | {ESCAPES} | [0-8xuU] ) )* {STR}?
                                        { return STRING; }

"{{"                                    { return DBL_LBRACE; }
"{"                                     { return LBRACE; }
"}"                                     { return RBRACE; }
"}}"                                    { return DBL_RBRACE; }

"["                                     { return LBRACK; }
"]"                                     { return RBRACK; }

"("                                     { return LPAREN; }
")"                                     { return RPAREN; }

":"                                     { return COLON; }
"::"                                    { return COLONCOLON; }
";"                                     { return SEMICOLON; }
","                                     { return COMMA; }

// Operators

"="                                     { return EQUALS; }
"/="                                    { return NEQUALS; }

"and"                                   { return AND; }
"or"                                    { return OR; }
"not"                                   { return NOT; }
"o"                                     { return CAT; }

"<="                                    { return LESS_OR_EQUAL; }
"<"                                     { return LESS; }
">="                                    { return GREATER_OR_EQUAL; }
">"                                     { return GREATER; }

"%"                                     { return MOD; }
"*"                                     { return MUL; }
"/"                                     { return QUOTIENT; }
"++"                                    { return PLUS_PLUS; }
"+"                                     { return PLUS; }
"--"                                    { return MINUS_MINUS; }
"-"                                     { return MINUS; }

":="                                    { return COLON_EQUALS; }
":=:"                                   { return COLON_EQUALS_COLON; }
"->"                                    { return RARROW; }
"~"			                            { return TILDE; }
"union"                                 { return UNION; }
"intersect"                             { return INTERSECT; }
"is_in"                                 { return IS_IN; }
"is_not_in"                             { return IS_NOT_IN; }
"|"                                     { return BAR; }
"||"                                    { return DBL_BAR; }

// Keywords

"by"                                    { return BY; }
"Concept"                               { return CONCEPT;  }
"constraints"                           { return CONSTRAINT; }
"do"                                    { return DO; }
"end"                                   { return END;  }
"ensures"                               { return ENSURES; }
"exemplar"                              { return EXEMPLAR; }
"externally"                            { return EXTERNALLY; }
"Facility"                              { return FACILITY;  }
"Family"                                { return FAMILY; }
"is"                                    { return IS; }
"implemented"                           { return IMPLEMENTED; }
"initialization"                        { return INITIALIZATION; }
"lambda"                                { return LAMBDA; }
"modeled"                               { return MODELED; }
"Operation"                             { return OPERATION; }
"Procedure"                             { return PROCEDURE; }
"Recursive"                             { return RECURSIVE; }
"Record"                                { return RECORD; }
"requires"                              { return REQUIRES; }
"Type"                                  { return FAMILY_TYPE; }
"type"                                  { return PARAM_TYPE; }
"uses"                                  { return USES; }
"Var"                                   { return VAR; }
"While"                                 { return WHILE; }
"which_entails"                         { return WHICH_ENTAILS; }

// Parameter modes

"alters"                                { return ALTERS; }
"updates"                               { return UPDATES; }
"clears"                                { return CLEARS; }
"restores"                              { return RESTORES; }
"preserves"                             { return PRESERVES; }
"replaces"                              { return REPLACES; }
"evaluates"                             { return EVALUATES; }

{IDENT}                                 { return IDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}