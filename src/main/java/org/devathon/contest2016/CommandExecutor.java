package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.Tuca.InstructionSet;

public class CommandExecutor implements org.bukkit.command.CommandExecutor{

    private final String indent = "  ";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase(DevathonPlugin.CMD_TURTLE)){
            if(sender instanceof Player){
                handleArgs(sender, args);
            } else {
                sender.sendMessage("Only Players can use this Command");
            }
        }
        return false;
    }

//-----------------------------------------------

    private void handleArgs(CommandSender sender, String[] args){
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("program")){

            } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("compile")) {

            } else if (args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("instructions")) {
                sendInstructions(sender);
            } else {
                sendHelp(sender);
            }
        } else sendHelp(sender);
    }

//-----------------------------------------------

    private void sendHelp(CommandSender sender){
        sender.sendMessage("§l§6----------[ Mining Turtle ]----------");
        sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "ProgramName");
        sendCommand(sender, new String[]{"n", "new"}, "Gives you a book where you can write your code into", "");
        sendCommand(sender, new String[]{"c", "compile"}, "Compiles the Code in book in your hand", "");
        sendCommand(sender, new String[]{"i", "instructions"}, "Shows you the syntax you can program the turtle programs", "");
    }
    private void sendCommand(CommandSender sender, String[] cmdArr, String description, String args){
        String cmd;
        if(cmdArr.length == 1) cmd = "§2" + cmdArr[0];
        else {
            cmd = "§2<";
            for(int i = 0; i < cmdArr.length; i++){
                cmd += "§2" + cmdArr[i];
                if(i < cmdArr.length-1) cmd += " §7| ";
                else cmd += "§a>";
            }
        }

        sender.sendMessage("§6/" + DevathonPlugin.CMD_TURTLE + " " + cmd + " §3" + args);
        if(description.length() > 0) sender.sendMessage(indent + ChatColor.GRAY + description);
    }

    private void sendInstructions(CommandSender sender){
        sender.sendMessage("§l§6----------[ Mining Turtle §5Instructions§6 ]----------");
        sender.sendMessage("§3§oCyan§r§7§o arguments are optional!");
        for(InstructionSet instruction : InstructionSet.values()){
            switch (instruction){
                case MOVE:
                    sendInstructionHelp(sender, instruction, "Steps, .Direction", "Move x steps into current/selected direction");
                    break;
                case TURN:
                    sendInstructionHelp(sender, instruction, "Direction", "Turn into given direction");
                    break;
                case LEFT:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case RIGHT:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case FORWARD:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case BACKWARD:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case UP:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case DOWN:
                    sendInstructionHelp(sender, instruction, "", "Type: Direction");
                    break;
                case MINE:
                    sendInstructionHelp(sender, instruction, ".Direction", "Mine block at given direction or in fron of the turtle");
                    break;
                case IF:
                    sendInstructionHelp(sender, instruction, "Condition", "Executes Code inside if the condition is true");
                    break;
                case FI:
                    sendInstructionHelp(sender, instruction, "", "Terminates IF block");
                    break;
                case NOT:
                    sendInstructionHelp(sender, instruction, "", "Tells if to negotiate the result");
                case ELSE:
                    sendInstructionHelp(sender, instruction, "", "Executes block if the IF condition returns false");
                    break;
                case ESLE:
                    sendInstructionHelp(sender, instruction, "", "Terminates ELSE block");
                    break;
                case IS_BLOCK:
                    sendInstructionHelp(sender, instruction, ".BlockId | .Direction", "Checks if there is a block (with specified Id) in front (at given Direction) of the turtle");
                    break;
                case IS_STORAGE_FULL:
                    sendInstructionHelp(sender, instruction, "", "Checks if the turtle storage is full");
                    break;
            }
        }
    }
    private void sendInstructionHelp(CommandSender sender, InstructionSet set, String args, String info){
        sender.sendMessage("§5" + set.name() + (args.length() > 0 ? indent + "§8Args: §2" + args.replace(".", "§3") : ""));
        if(info.length() > 0) sender.sendMessage(indent + "§7" + info);
    }

}
