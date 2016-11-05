package org.devathon.contest2016.Tuca;

public enum InstructionSet {

    /*
    TURN + Direction
    MOVE + Direction + Amount
    MINE + Direction ???


    GOTO
    ...
     */

    MOVE(),
    TURN(),
    MINE,

    LEFT,
    RIGHT,
    FORWARD,
    BACKWARD,
    UP,
    DOWN,

    IF,
    FI,
    NOT,
    ELSE,
    ESLE,
    IS_BLOCK,
    IS_STORAGE_FULL;

}
