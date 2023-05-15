//------------------paquetes importaciones
package src;
import java_cup.runtime.*;

%%

%public
%class Analizador
%implements sym
%unicode
%line
%column
%cup
%cupdebug

%{

  /**
  *symbol
  *E:: type: nombre que tomara la expresión como terminal en el parser
        value: valor de dicha terminal
  *S:: nuevo simbolo que contiene la información del token encontrado
  *R:: el simbolo debe estar definido en la lista de lexemas
  *O:: definir las terminales que podrá analizar la gramatica
  */
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  /**
  *reportarError (modoPanico)
  *E:: ninguna
  *S:: mensaje en consola que indica que ha ocurrido un error
  *R:: ninguna
  *O:: indicar que se ha encontrado un lexema no valido y continuar con el analizis
  */
  private void reportarError(){
    System.out.println("El lexema \""+yytext()+"\" at line "+yyline+", column "+yycolumn+" no está permitido.");
    System.out.println("Este será ignorado y se continuará con el analisis");
  }
%}


//----------Expresiones Regulares
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

Comment = {EndOfLineComment}  
EndOfLineComment = "@" {InputCharacter}* {LineTerminator}? 

numero = 0 | [1-9][0-9]*
identificador = [a-zA-Z_] [a-zA-Z0-9_]*
float    = [0-9]+ \. [0-9]*
simbolo = "!" | "@" | "#"  | "%" | "^" | "&" | "*" 
    | "(" | ")" | "-" | "_" | "+" | "=" | "[" | "]" | "{" 
    | "}" | ";" | ":" | "\'" | '\"' | "," | "." | "<" | ">" 
    | "?" | "/" | "|" | "\\"


string = \"(\\.|[^\"])*\"
char = \'[a-zA-Z]\' |\'[0-9]\'|\'{simbolo}\'

//------estados
%state COMMENTB
%%

//definición de las terminales
//existen terminales comentadas ya que no forman parte de la gramatica pero podrian ser incluidas
<YYINITIAL>{
    "!"             {return symbol(REXC); }
    //"@"             {return symbol(ARROBA); }
    "#"             {return symbol(OR); }
    "$"             {return symbol(DOLLAR); }
    //"%"             {return symbol(PORCIENTO); }
    "^"             {return symbol(AND); }
    //"&"             {return symbol(AMPER); }
    "*"             {return symbol(MULT, yytext()); }
    "("             {return symbol(LPAREN); }
    ")"             {return symbol(RPAREN); }
    "-"             {return symbol(MENOS, yytext()); }
    //"_"             {return symbol(GUIONBAJO); }
    "+"             {return symbol(MAS, yytext()); }
    "="             {return symbol(ASIG); }
    "["             {return symbol(LBRACKET); }
    "]"             {return symbol(RBRACKET); }
    "{"             {return symbol(LBRACE); }
    "}"             {return symbol(RBRACE); }
    //";"             {return symbol(SEMICOLON); }
    //":"             {return symbol(DOSPUNTOS); }
    //"\'"            {return symbol(COMILLASIM); }
    //"\""            {return symbol(COMILLADOB); }
    ","             {return symbol(COMA); }
    //"."             {return symbol(PUNTO); }
    "<"             {return symbol(MENOR); }
    ">"             {return symbol(MAYOR); }
    //"?"             {return symbol(RINTERRO); }
    "/"             {return symbol(DIV, yytext()); }
    //"|"             {return symbol(PIPE); }
    //"\\"            {return symbol(SLASH); }
    "<="            {return symbol(MENORIGUAL); }
    ">="            {return symbol(MAYORIGUAL); }
    "=="            {return symbol(EQUAL); }
    "!="            {return symbol(NOTEQUAL); }
    "**"            {return symbol(POTENCIA, yytext()); }
    "~"             {return symbol(MODULO, yytext()); }
    "++"            {return symbol(INCREMENTO, yytext()); }
    "--"            {return symbol(DECREMENTO, yytext()); }
    "not"           {return symbol(NOT); }
    "int"           {return symbol(INT,yytext()); }
    "float"         {return symbol(FLOAT,yytext()); }
    "string"        {return symbol(STRING,yytext()); }
    "char"          {return symbol(CHAR,yytext()); }
    "array"         {return symbol(ARRAY,yytext()); }
    "bool"          {return symbol(BOOL,yytext()); }
    "main"          {return symbol(MAIN,yytext()); }
    "true"          {return symbol(LITERAL_BOOL ,yytext()); }
    "false"         {return symbol(LITERAL_BOOL,yytext()); }
    "if"            {return symbol(IF);  }
    "elif"          {return symbol(ELIF); }
    "else"          {return symbol(ELSE); }
    "while"         {return symbol(WHILE); }
    "do"            {return symbol(DO); }
    "for"           {return symbol(FOR); }
    "return"        {return symbol(RETURN); }
    "break"         {return symbol(BREAK); }
    "leer"          {return symbol(LEER,yytext()); }
    "escribir"      {return symbol(ESCRIBIR,yytext()); }

    "/_"            { yybegin(COMMENTB); } //indica que la siguiente expresión será un comentario en bloque

    //terminal de literales
    {numero}        {System.out.println("found numero");
      return symbol(LITERAL_INT, yytext()); }
    {float}         {return symbol(LITERAL_FLOAT, new Float(yytext().substring(0,yylength()-1)));  }
    {identificador} { return symbol(IDENTIFIER, yytext()); }
    {string}        {return symbol(LITERAL_STRING, yytext()); }
    {char}          {return symbol(LITERAL_CHAR, yytext()); }
    {Comment}           { /* ignore */ }
    {WhiteSpace}        { /* ignore */ }
}

//acciones que serán tomadas dentro de los comentarios de bloque
<COMMENTB>{
  [^_]*      { }
  "_"+[^_/]* { }
  "_"+"/"    { yybegin(YYINITIAL); }
  .          { }
}


//[^]                              { /*que hacer en caso de error*/}
[^]                              { reportarError(); }
<<EOF>>                          { return symbol(EOF); }