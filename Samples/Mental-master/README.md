# Mental
This is a repository of my course "Principles of Compilers". It is a compiler of *Mx\** language.
## Introduction of Language
Mx* language is a Java-like programming language. But the class in Mx* language is similar to the struct in C.
### Built in Functions
#### ```void print(string str)```
print *str* to **stdout**.
#### ```void println(string str)```
print *str* and a newline(```'\n'```) to **stdout**
#### ```string getString()```
get a line of characters from **stdin** and return a string object contained the characters.
#### ```int getInt()```
get a line of characters from **stdin** and return a first decimal integer.
#### ```string toString(int)```
convert a integer to a string object.
#### ```string.length()```
return a integer which is the length of a string.
#### ```string.substring(int left, int right)```
return a new string object which contain the characters subscripting from $left$ to $right$.
#### ```string.ord(int pos)```
return the ASCII value of the character with subscripting $pos$. (0-base)
#### ```string.parseInt()```
return the first integer in the beginning of the string. The prefix of the string should be a integer.
#### ```array.size()```
return the size of the array.
## Introduction to Mental
### Input
A source file of Mx* language. The source file should be redirect to **stdin**.
### Output
SPIM assemble code in **stdout**.
## Mental
### cn.edu.sjtu.songyuke.mental.ast
Abstract Syntax Tree for Mx* language.
### cn.edu.sjtu.songyuke.mental.core
The main class of the whole project.
### cn.edu.sjtu.songyuke.mental.ir
Intermedia Representation for Mx* language.
### cn.edu.sjtu.songyuke.mental.translator
A cn.edu.sjtu.songyuke.mental.translator from cn.edu.sjtu.songyuke.mental.ir to SPIM assemble code.


