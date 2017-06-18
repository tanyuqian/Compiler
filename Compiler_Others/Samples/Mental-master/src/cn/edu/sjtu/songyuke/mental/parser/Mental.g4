grammar Mental;
COMMENT
	: '//' ~[\r\n]* '\r'? ('\n'|EOF) -> skip
	;

WS
	: [ \t\n\r]+ -> skip
	;

INT
	: DIGIT+
	;

Identifier
	: Identifiernondigit ( Identifiernondigit| DIGIT )*
	;
fragment
Hexquad
	: HEX HEX HEX HEX
	;
fragment
Universalcharactername
	: '\\u' Hexquad
	| '\\U' Hexquad Hexquad
	;
fragment
Identifiernondigit
	: NONDIGIT
	| Universalcharactername
	;

fragment
NONDIGIT
	: [a-zA-Z_]
	;

fragment DIGIT
	: [0-9]
	;

STRING
	: '"' (ESC | .)*? '"'
	;
fragment ESC
	: '\\' (["\\/bfnrt] | UNICODE)
	;
fragment UNICODE
	: 'u' HEX HEX HEX HEX
	;
fragment HEX
	: [0-9a-fA-F]
	;

className
    : Identifier
    ;

typeName
	: 'int'
	| 'string'
	| 'bool'
	| className
	;

array
    : '[' ']'
    ;

cn.edu.sjtu.songyuke.mental.type
	: typeName array*
	;

parameter
    : cn.edu.sjtu.songyuke.mental.type Identifier
    ;

parametersList
	: parameter (',' parameter)*
	;

program
	: (declaration | definition | emptyStatement)*
	;

declaration
	: classDeclaration
	| functionDeclaration
	;

classDeclaration
	: 'class' name=className? '{' (variableDefinition | functionDefinition)* '}'
	;

functionDeclaration
	: (cn.edu.sjtu.songyuke.mental.type | 'void') Identifier '(' parametersList? ')' ';'
	;

definition
	: variableDefinition
	| functionDefinition
	;

singleVariable
    : Identifier ('=' expression)?
    ;

variableDefinition
	: cn.edu.sjtu.songyuke.mental.type singleVariable (',' singleVariable)* ';'
	;

functionDefinition
	: (cn.edu.sjtu.songyuke.mental.type | 'void') functionName=Identifier '(' parametersList? ')' compoundStatement
	;

compoundStatement
	: '{' statement* '}'
	;

statement
	: compoundStatement
	| ifStatement
	| ifElseStatement
	| forStatement
	| whileStatement
	| expressionStatement
	| jumpStatement
	| emptyStatement
	| variableDefinition
//	| callPrint
//	| callPrintln
	;
/*
callPrint
	: 'print' '(' expression ')' ';'
	;

callPrintln
	: 'println' '(' expression ')' ';'
	;

callGetString
	: 'getString' '(' ')'
	;

callGetInt
	: 'getInt' '(' ')'
	;

callToString
	: 'toString' '(' expression ')'
	;

callSubString
    : 'substring' '(' expression ',' expression ')'
    ;
callLength
    : 'length' '(' ')'
    ;
callParseInt
    : 'parseInt' '(' ')'
    ;
callOrd
    : 'ord' '(' expression ')'
    ;
callSize
    : 'size' '(' ')'
    ;
*/
emptyStatement
	: ';'
	;

ifStatement
	: 'if' '(' expression ')' thenStatement=statement
	;

ifElseStatement
	: 'if' '(' expression ')' thenStatement=statement 'else' elseStatment=statement
	;

forStatement
	: 'for' '(' start=expression? ';' cond=expression? ';' loop=expression? ')' statement
	;

whileStatement
	: 'while' '(' cond=expression ')' statement
	;

jumpStatement
	: 'return' expression? ';'
	| 'continue' ';'
	| 'break' ';'
	;

expressionStatement
	: expression ';'
	;

expression
	: '(' expression ')'
    #SUBGROUP_EXPRESSION
	|'new' typeName ('[' expression ']')* array*
	#CREATION_EXPRESSION
	| expression '[' expression ']'
	#ARRAY_SUBSCRIPTING_EXPRESSION
	| expression op='.' (Identifier | functionCall)
	#MEMBER_ACCESS_EXPRESSION
	| expression op=('++' | '--')
	#SUFFIX_INC_DEC_EXPRESSION
	| op=('++'|'--') expression
	#PREFIX_INC_DEC_EXPRESSION
	| op=('+'|'-') expression
	#UNRAY_PLUS_MINUS_EXPRESSION
	| op='~' expression
	#BIT_NOT_EXPRESSION
	| op='!' expression
	#LOGICAL_NOT_EXPRESSION
	| expression op=('*'|'/'|'%') expression
	#MULTIPLY_DIVIDE_EXPRESSION
	| expression op=('+'|'-')  expression
	#ADDITIVE_EXPRESSION
	| expression op=('<<'|'>>') expression
	#BIT_SHIFT_EXPRESSION
	| expression op=('<='|'>='|'<'|'>') expression
	#RELATION_EXPRESSION
	| expression op=('=='|'!=') expression
	#EQUALITY_EXPRESSION
	| expression op='&' expression
	#BIT_AND_EXPRESSION
	| expression op='^' expression
	#BIT_XOR_EXPRESSION
	| expression op='|' expression
	#BIT_OR_EXPRESSION
	| expression op='&&' expression
	#LOGICAL_AND_EXPRESSION
	| expression op='||' expression
	#LOGICAL_OR_EXPRESSION
	| <assoc=right> expression op='=' expression
	#ASSIGN_EXPRESSION
	| functionCall
	#FUNCTION_CALL
	| Identifier
	#IDENTIFIER
	| INT
	#INT_LITERAL
	| STRING
	#STRING_LITERAL
	| 'true'
	#TRUE
	| 'false'
	#FALSE
	| 'null'
	#NULL
	;
functionCall
    : functionName=Identifier '(' expressionList? ')'
    ;
expressionList
	: expression (',' expression)*
	;

PLUS
	: '+' 
	;
MINUS
	: '-' 
	;
MUL
	: '*' 
	;
DIV
	: '/' 
	;
MOD
	: '%'
	;
INC
	: '++' 
	;
DEC
	: '--' 
	;
BIT_NOT
	: '~' 
	;
LOGICAL_NOT
	: '!' 
	;
BIT_AND
	: '&' 
	;
BIT_XOR
	: '^' 
	;
BIT_OR
	: '|'
	;
LOGICAL_AND
	: '&&'
	;
LOGICAL_OR
	: '||'
	;
ASSIGN
	: '='
	;
EQUAL
	: '=='
	;
INEQUAL
	: '!='
	;
LESS
	: '<'
	;
GREATER
	: '>'
	;
LESS_EQUAL
	: '<='
	;
GREATER_EQUAL
	: '>='
	;
LEFT_SHIFT
	: '<<'
	;
RIGHT_SHIFT
	: '>>'
	;
PERIOD
	: '.'
	;
COMMA
	: ','
	;
LBRACKET
    : '['
    ;
RBRACKET
    : ']'
    ;
