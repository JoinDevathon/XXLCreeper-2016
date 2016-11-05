package org.devathon.contest2016.Tuca;

import java.util.ArrayList;

public enum Instructions {

    /*
    TURN + Direction
    MOVE + Direction + Amount
    MINE + Direction ???


    for an if clause in interpreter: look for FI or ELSE!


    GOTO
    ...
     */

    MOVE        (InstructionType.ACTION),
    TURN        (InstructionType.ACTION),
    MINE        (InstructionType.ACTION),

    LEFT        (InstructionType.DIRECTION),
    RIGHT       (InstructionType.DIRECTION),
    FORWARD     (InstructionType.DIRECTION),
    BACKWARD    (InstructionType.DIRECTION),
    UP          (InstructionType.DIRECTION),
    DOWN        (InstructionType.DIRECTION),

    IF          (InstructionType.LOGIC),
    FI          (InstructionType.LOGIC),
    IFNOT       (InstructionType.LOGIC),
    ELSE        (InstructionType.LOGIC),
    ESLE        (InstructionType.LOGIC),
    WHILE       (InstructionType.LOGIC),
    WHILENOT    (InstructionType.LOGIC),
    ELIHW       (InstructionType.LOGIC),

    IS_B(InstructionType.BOOL),
    IS_SF(InstructionType.BOOL),

    NULL(InstructionType.NULL);

//-----------------------------------------------

    private InstructionType type;
    Instructions(InstructionType typ){
        this.type = type;
    }

    public InstructionType getType(){
        return this.type;
    }

//-----------------------------------------------

    public static Instructions fromString(String str){
        for(Instructions instruction : Instructions.values()){
            if(instruction.name().equalsIgnoreCase(str)) return instruction;
        }
        return Instructions.NULL;
    }

//-----------------------------------------------

    public enum InstructionType{
        ACTION,
        DIRECTION,
        LOGIC,
        BOOL,
        NULL,
        NUMBER
    }

}
