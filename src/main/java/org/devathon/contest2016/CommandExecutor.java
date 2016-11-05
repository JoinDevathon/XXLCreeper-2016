package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.devathon.contest2016.Tuca.IOUtils;
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
            if (args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("program")) {
                if(args.length > 1){
                    if(args[1].equalsIgnoreCase("list")){
                        sendProgramList((Player)sender);
                    } else if(args[1].equalsIgnoreCase("new")){
                        if(args.length > 2){
                            if(!IOUtils.getProgramExists((Player)sender, args[2])){
                                ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
                                BookMeta meta = (BookMeta) stack.getItemMeta();
                                meta.setDisplayName("§5[Turtle] §3" + args[1]);
                                stack.setItemMeta(meta);

                                ((Player)sender).getInventory().addItem(stack);
                                sendTurtleMessage(sender, "§aHave fun programming!");
                                sendCommand(sender, new String[]{"i", "instructions"}, "Shows you the syntax you can program the turtle programs", "");
                            } else {
                                sendTurtleMessage(sender, "§cProgram already exists!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new", "name");
                        }
                    } else if(args[1].equalsIgnoreCase("edit")){
                        if(args.length > 2){
                            if(IOUtils.getProgramExists((Player)sender, args[2])){

                            } else {
                                sendTurtleMessage(sender, "§cProgram does not exist!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
                        }
                    } else if(args[1].equalsIgnoreCase("delete")){
                        if(args.length > 2){
                            if(IOUtils.getProgramExists((Player)sender, args[2])){

                            } else {
                                sendTurtleMessage(sender, "§cProgram does not exist!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
                        }
                    } else if(args[1].equalsIgnoreCase("clone")){
                        if(args.length > 2){
                            if(IOUtils.getProgramExists((Player)sender, args[2])){

                            } else {
                                sendTurtleMessage(sender, "§cProgram does not exist!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                        }
                    } else {
                        sender.sendMessage("§cInvalid argument specified!");
                        sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
                        sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                    }
                } else {
                    sender.sendMessage("§cNo argument specified!");
                    sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
                    sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                }
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
        sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
        sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
        sendCommand(sender, new String[]{"c", "compile"}, "Compiles the Code in book in your hand", "");
        sendCommand(sender, new String[]{"i", "instructions"}, "Shows you the syntax you can program the turtle programs", "");
    }
    private void sendCommand(CommandSender sender, String[] cmdArr, String description, String... args){
        String arg = "";
        for(String str : args) arg += " " + str;

        sender.sendMessage("§6/" + DevathonPlugin.CMD_TURTLE + " " + modify(cmdArr, "§2") + "§3" + arg);
        if(description.length() > 0) sender.sendMessage(indent + ChatColor.GRAY + description);
    }
    private String modify(String[] data, String color){
        String str;
        if(data.length == 1) str = "§2" + data[0];
        else {
            str = color + "<";
            for(int i = 0; i < data.length; i++){
                str += color + data[i];
                if(i < data.length-1) str += " §7| ";
                else str += color + ">";
            }
        }
        return str;
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

    private void sendProgramList(Player sender){
        sender.sendMessage("§l§6----------[ Mining Turtle §5Programs§6 ]----------");
        for(String str : IOUtils.getProgramList(sender)) sender.sendMessage("§2" + str);
    }

    private void sendTurtleMessage(CommandSender sender, String message){
        sender.sendMessage("§5[Turtle] §r" + message);
    }
}
