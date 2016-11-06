package org.devathon.contest2016.Tuca;

import org.bukkit.entity.Player;
import org.devathon.contest2016.MiningTurtle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class Interpreter {

    private ArrayList<String> lines;
    private MiningTurtle turtle;

    public Interpreter(Player p, String prog, MiningTurtle turtle){
        lines = IOUtils.readProg(prog, p);
        this.turtle = turtle;
    }

//-----------------------------------------------

    public void execute() throws Exception{
        turtle.codeRunning = true;
        int IF = 0;
        int ELSE = 0;
        int WHILE = 0;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                if(i >= lines.size()) this.cancel();
                else {
                    String line = lines.get(i);
                    String[] arr = line.trim().split(Pattern.quote(" "));
                    if(arr.length > 0) {
                        Instructions i0 = Instructions.fromString(arr[0]);
                        if(i0.getType() == Instructions.InstructionType.ACTION){
                            if(i0 == Instructions.MOVE){
                                if(isNumber(arr[1])){
                                    if(arr.length > 2){
                                        Instructions i1 = Instructions.fromString(arr[2]);
                                        //turtle.rotate(i1);
                                        for(int i = 0; i < Integer.parseInt(arr[1]); i++){
                                            turtle.move(i1);
                                        }
                                    } else {
                                        for(int i = 0; i < Integer.parseInt(arr[1]); i++){
                                            turtle.move(Instructions.FORWARD);
                                        }
                                    }
                                } else {
                                }
                            } else if(i0 == Instructions.TURN){
                                turtle.rotate(Instructions.fromString(arr[1]));
                            } else if(i0 == Instructions.MINE){
                                turtle.mine(Instructions.fromString(arr[1]));
                            }
                        } else if(i0.getType() == Instructions.InstructionType.LOGIC){

                        }
                    }
                    i++;
                }
            }
        }, 500, 500);

/*        for(String line : lines){
            String[] arr = line.trim().split(Pattern.quote(" "));
            if(arr.length == 0) continue;

            Instructions i0 = Instructions.fromString(arr[0]);
            if(i0.getType() == Instructions.InstructionType.ACTION){
                if(i0 == Instructions.MOVE){
                    if(isNumber(arr[1])){
                        for(int i = 0; i < Integer.parseInt(arr[1]); i++){
                            turtle.move(Instructions.FORWARD);
                        }
                    } else {
                        Instructions i1 = Instructions.fromString(arr[1]);
                        turtle.rotate(i1);
                        for(int i = 0; i < Integer.parseInt(arr[2]); i++){
                            turtle.move(Instructions.FORWARD);
                        }
                    }
                } else if(i0 == Instructions.TURN){
                    turtle.rotate(Instructions.fromString(arr[1]));
                } else if(i0 == Instructions.MINE){
                    turtle.mine(Instructions.fromString(arr[1]));
                }
            } else if(i0.getType() == Instructions.InstructionType.LOGIC){

            }

        }*/

        turtle.codeRunning = false;
    }

//-----------------------------------------------

    private boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
