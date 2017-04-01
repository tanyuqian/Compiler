grammar Meh;

program
    : (classDeclaration | functionDeclaration | variableDeclarationStatement)+
;

classDeclaration
    : 'class' IDENTIFIER '{' (functionDeclaration | variableDeclarationStatement)* '}'
;

functionDeclaration
    : type IDENTIFIER '(' (type IDENTIFIER (',' type IDENTIFIER)* )? ')' blockStatement
;

variableDeclarationStatement
    : type IDENTIFIER ('=' expression)? ';'
;

blockStatement
    : '{' statement* '}'
;

statement
    : variableDeclarationStatement
    | expressionStatement
    | selectionStatement
    | iterationStatement
    | jumpStatement
    | blockStatement
;

expressionStatement
    : expression? ';'
;

selectionStatement
    : 'if' '(' expression ')' statement ('else' statement)?
;

iterationStatement
    : 'while' '(' expression ')' statement                                      #whileStatement
    | 'for' '(' expression? ';' expression? ';' expression ')' statement        #forStatement
;

jumpStatement
    : 'return' expression? ';'  #returnStatement
    | 'break' ';'               #breakStatement
    | 'continue' ';'            #continueStatement
;

expression
    : constant                                                  #constantExpression
    | IDENTIFIER                                                #variableExpression
    | '(' expression ')'                                        #subExpression
    | expression operator=('++'|'--')                           #postfixExpression
    | expression '(' (expression (',' expression)*)? ')'        #functionCallExpression
    | expression '[' expression ']'                             #subscriptExpression
    | expression '.' IDENTIFIER                                 #fieldExpression
    | operator=('++'|'--'|'+'|'-'|'!'|'~') expression           #unaryExpression
    | 'new' type ('[' expression ']')* ('[' ']')*               #newExpression
    | expression operator=('*'|'/'|'%') expression              #multiplicativeExpression
    | expression operator=('+'|'-') expression                  #additiveExpression
    | expression operator=('<<'|'>>') expression                #shiftExpression
    | expression operator=('<'|'<='|'>'|'>=') expression        #relationExpression
    | expression operator=('=='|'!=') expression                #equalityExpression
    | expression '&' expression                                 #andExpression
    | expression '^' expression                                 #exclusiveOrExpression
    | expression '|' expression                                 #inclusiveOrExpression
    | expression '&&' expression                                #logicalAndExpression
    | expression '||' expression                                #logicalOrExpression
    | <assoc=right> expression '=' expression				    #assignmentExpression
;

type
    : 'void'        #voidType
    | 'bool'        #boolType
    | 'int'         #intType
    | 'string'      #stringType
    | IDENTIFIER    #classType
    | type '[' ']'     #arrayType
;

constant
    : ('true' | 'false')    #boolConstant
    | INTEGER               #intConstant
    | STRING                #stringConstant
    | 'null'                #nullConstant
;

IDENTIFIER
    : [a-zA-Z_][a-zA-Z_0-9]*
;

INTEGER
    : [0-9]+
;

STRING
    : '\"' CHAR* '\"'
;

fragment
CHAR
    :   ~["\\\r\n]
    |   '\\' ['"?abfnrtv\\]
;

LINECOMMENT
    : '//' ~[\r\n]*     -> skip
;

WHITESPACE
    : [ \t\r\n]     -> skip
;