package org.devathon.contest2016.Tuca;

public enum InstructionSet {

    /*
    TURN + Direction
    MOVE + Direction + Amount
    MINE + Direction ???


    GOTO
    ...
     */

    MOVE("Args: §3§oDirection §r§2Steps"),
    TURN("§5TURN §3§oDirection"),
    MINE(""),

    LEFT("§7"),
    RIGHT(""),
    FORWARD(""),
    BACKWARD(""),
    UP(""),
    DOWN(""),

    IF(""),
    FI(""),
    NOT(""),
    ELSE(""),
    ESLE(""),
    IS_BLOCK(""), //asks for blocktype, requires blockId
    IS_STORAGE_FULL("");

    private String help;
    InstructionSet(String help){
        this.help = help;
    }
}
