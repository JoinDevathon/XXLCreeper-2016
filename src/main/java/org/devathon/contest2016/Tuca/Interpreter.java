package org.devathon.contest2016.Tuca;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.MiningTurtle;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Interpreter {

    private ArrayList<String> lines;
    private MiningTurtle turtle;
    private DevathonPlugin pl;
    private BukkitTask bt;

    public Interpreter(Player p, String prog, MiningTurtle turtle, DevathonPlugin pl){
        lines = IOUtils.readProg(prog, p);
        this.turtle = turtle;
        this.pl = pl;
    }

//-----------------------------------------------

    public void execute() throws Exception{
        turtle.codeRunning = true;
        int IF = 0;
        int ELSE = 0;
        int WHILE = 0;

        bt = pl.getServer().getScheduler().runTaskTimer(pl, new Runnable() {
            int i = 0;
            @Override
            public void run() {
                if(i >= lines.size()) bt.cancel();
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
        }, 0, 10);

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
