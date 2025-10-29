package org.galaxy.uniflow.api;

public enum Opcode {
    /**
     * Unary operators, of type Unary.
     */
    POS,                             // +
    NEG,                             // -
    NOT,                             // !
    COMPL,                           // ~
    PREINC,                          // ++ _
    PREDEC,                          // -- _
    POSTINC,                         // _ ++
    POSTDEC,                         // _ --

    /**
     * Binary operators, of type Binary.
     */
    OR,                              // ||
    AND,                             // &&
    BITOR,                           // |
    BITXOR,                          // ^
    BITAND,                          // &
    EQ,                              // ==
    NE,                              // !=
    LT,                              // <
    GT,                              // >
    LE,                              // <=
    GE,                              // >=
    SL,                              // <<
    SR,                              // >>
    USR,                             // >>>
    PLUS,                            // +
    MINUS,                           // -
    MUL,                             // *
    DIV,                             // /
    MOD,                             // %

    /**
     * Assignment operators, of type Assignop.
     */
    BITOR_ASG,                // |=
    BITXOR_ASG,              // ^=
    BITAND_ASG,              // &=

    SL_ASG,                      // <<=
    SR_ASG,                      // >>=
    USR_ASG,                    // >>>=
    PLUS_ASG,                  // +=
    MINUS_ASG,                // -=
    MUL_ASG,                    // *=
    DIV_ASG,                    // /=
    MOD_ASG,                    // %=

}
