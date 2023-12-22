A compiler capable of analizing a written file and compiling from ac to dc -> https://linux.die.net/man/1/dc

In AC we have:
2 types of data: integers and floating point numbers. 
- An integer literal is a sequence of digits that, if it starts with 0, should not be followed by any other digits.
- A floating point number is a sequence of digits (non-empty) followed by a "." (dot) followed by at most 5 digits.

Variables that are strings containing only the 26 lowercase English alphabet characters.
A variable must be declared before it can be used (in an expression).

Declarations: float or int followed by a variable and optionally initialized.

Expressions that can be literals (integers or floating point numbers), variables, or expression op expression, where op (binary operator) can be +, -, *, and /.

The operands of a binary expression must have the same type.
An int expression can be automatically converted to a float (if necessary), and no other conversions are possible.

Assignment and print statements, with the following syntax:
- assignment: variable = expression, or variable op= expression
- print: print variable
