package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.devathon.contest2016.Json.TellRaw;
import org.devathon.contest2016.Tuca.IOUtils;
import org.devathon.contest2016.Tuca.Instructions;
import org.devathon.contest2016.Tuca.Interpreter;

import java.util.*;

public class CommandExecutor implements org.bukkit.command.CommandExecutor{

    private final String indent = "  ";
    public static HashMap<UUID, MiningTurtle> player_turtle = new HashMap<>();
    private DevathonPlugin pl;

    public CommandExecutor(DevathonPlugin pl){
        this.pl = pl;
    }


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
                        ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        ((Player)sender).getInventory().addItem(stack);

                        sendTurtleMessage(sender, "§dDue to issues with the current version of spigot, I'm note able to provide you with fancy books! §7(Info: /turtle bookBug)");
                        sendTurtleMessage(sender, "§aHave fun programming!");
                        sendCommand(sender, new String[]{"i", "instructions"}, "Shows you the syntax of Tuca, the turtle language", "");
                    } else if(args[1].equalsIgnoreCase("edit")){
                        if(args.length > 2){
                            if(IOUtils.getProgramExists((Player)sender, args[2])){
                                sendTurtleMessage(sender, "§aRemoving Program from store for you to edit §7(recompile to use it again)");
                                ((Player)sender).getInventory().addItem(IOUtils.read(args[2], (Player)sender));
                                IOUtils.delete(args[2], (Player)sender);
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
                                sendTurtleMessage(sender, "§cProgram §3'" + args[2] + "' §c successfully deleted!");
                                IOUtils.delete(args[2], (Player)sender);
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
                                sendTurtleMessage(sender, "§aHere's your cloned version. Feel free to share it with friends.");
                                ((Player)sender).getInventory().addItem(IOUtils.read(args[2], (Player)sender));
                            } else {
                                sendTurtleMessage(sender, "§cProgram does not exist!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                        }
                    } else if(args[1].equalsIgnoreCase("compile")){
                        if(args.length > 2){
                            if(!IOUtils.getProgramExists((Player)sender, args[2])){
                                ItemStack stack = ((Player)sender).getInventory().getItemInMainHand();
                                if(stack != null){
                                    if(stack.getType() == Material.BOOK_AND_QUILL || stack.getType() == Material.WRITTEN_BOOK){
                                        org.devathon.contest2016.Tuca.Compiler compiler = new org.devathon.contest2016.Tuca.Compiler(stack, args[2], (Player)sender);
                                        compiler.compile();

                                        if(compiler.hasErrors()){
                                            sendTurtleMessage(sender, "§cThere are Errors in your code:");
                                            for(org.devathon.contest2016.Tuca.Error error : compiler.getErrors()) sender.sendMessage(error.toString());
                                        } else {
                                            compiler.save();
                                            sendTurtleMessage(sender, "§aYour Program §3" + args[2] + " §ais saved!");
                                            ((Player)sender).getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                        }
                                    } else {
                                        sendTurtleMessage(sender, "§cYou must have a book and quill or signed book in your main hand!");
                                    }
                                } else {
                                    sendTurtleMessage(sender, "§cYou must have a book and quill or signed book in your main hand!");
                                }
                            } else {
                                sendTurtleMessage(sender, "§cProgram does already exist!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cName missing!");
                            sendCommand(sender, new String[]{"p", "program"}, "Compiles the Code in book in your hand", "compile", "name");
                        }
                    } else {
                        sendTurtleMessage(sender, "§cInvalid argument specified!");
                        sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
                        sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new");
                        sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                        sendCommand(sender, new String[]{"p", "program"}, "Compiles the Code in book in your hand", "compile", "name");
                    }
                } else {
                    sendTurtleMessage(sender, "§cNo argument specified!");
                    sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
                    sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new");
                    sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
                    sendCommand(sender, new String[]{"p", "program"}, "Compiles the Code in book in your hand", "compile", "name");
                }
            } else if (args[0].equalsIgnoreCase("i") || args[0].equalsIgnoreCase("instructions")) {
                sendInstructions(sender);
            } else if (args[0].equalsIgnoreCase("bookBug")) {
                sender.sendMessage("§l§6----------[ Mining Turtle §5BookBug§6 ]----------");
                sendTurtleMessage(sender, "§7You can rename books in Minecraft or via Plugin without any issues, but as soon as you open it, you aer unable to write. " +
                        "For this you have to switch to page 2 and then back to 1 and it's possible again");
                sendTurtleMessage(sender, "§7As soon as you close the book (in any case), the server removes the book from your Inventory, reads the text you've written and writes the text " +
                        "in a completely new book which it gives back to you then");
                sendTurtleMessage(sender, "§7The new book has all changes removed like DisplayName or the Lore");
            } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("spawn")) {
                if(args.length > 1){
                    if(player_turtle.containsKey(((Player)sender).getUniqueId())){
                        player_turtle.get(((Player)sender).getUniqueId()).despawn();
                    }
                    player_turtle.put(((Player)sender).getUniqueId(), new MiningTurtle(((Player)sender).getLocation(), args[1]));
                } else {
                    sendTurtleMessage(sender, "§cName missing");
                    sendCommand(sender, new String[]{"s", "spawn"}, "Spawns a new MiningTurtle", "name");
                }
            } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("run")) {
                if(args.length > 1){
                    if(player_turtle.containsKey(((Player)sender).getUniqueId())){
                        if(!player_turtle.get(((Player)sender).getUniqueId()).codeRunning){
                            try {
                                if(IOUtils.getProgramExists((Player)sender, args[1])){
                                    new Interpreter((Player)sender, args[1], player_turtle.get(((Player)sender).getUniqueId()), pl).execute();
                                } else {
                                    sendTurtleMessage(sender, "§cProgram does not exist!");
                                }
                            } catch (Exception e) {
                                sendTurtleMessage(sender, "§cAn Error occurred in your Program!");
                            }
                        } else {
                            sendTurtleMessage(sender, "§cOn this Turtle is already a Program running");
                        }
                    } else {
                        sendTurtleMessage(sender, "§cYou don't have a MiningTurtle!");
                        sendCommand(sender, new String[]{"s", "spawn"}, "Spawns a new MiningTurtle", "name");
                    }
                } else {
                    sendTurtleMessage(sender, "§cNo Program provided");
                    sendCommand(sender, new String[]{"r", "run"}, "Runs on of your programs on your turtle", "name");
                }
            } else if (args[0].equalsIgnoreCase("sample")) {
                try {
                    pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
                        int i = 0;
                        Player p = (Player)sender;
                        @Override
                        public void run() {
                            if(i == 0){
                                IOUtils.delete("sample", p);
                                p.sendMessage("§61) §7First we'll get a book and write some code into it with §8/turtle p new");
                                p.getInventory().setItemInMainHand(new ItemStack(Material.BOOK_AND_QUILL));
                                String[] errorCode = new String[]{"MOVE 1", "TURN LEFT", "MOVE UP"};
                                BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
                                meta.setPages(Arrays.asList(errorCode));
                                p.getInventory().getItemInMainHand().setItemMeta(meta);
                            } else if(i == 1) {
                                p.sendMessage("§62) §7Once we're finished, we have to compile the program to check it for errors §8/turtle p compile <name>");
                            } else if(i == 2) {
                                p.performCommand("turtle p compile sample");
                                p.sendMessage("§63) §7Oh, we got some errors. Time to correct them");
                                String[] correctCode = new String[]{"MOVE 1", "TURN LEFT", "MOVE 1", "TURN LEFT", "MOVE 1", "TURN LEFT", "MOVE 2 UP", "MOVE 1", "TURN LEFT", "MOVE 2", "TURN LEFT", "MOVE 1 DOWN", "MOVE 2", "TURN LEFT", "MOVE 2", "TURN LEFT", "MOVE 2", "MOVE 1 DOWN", "TURN LEFT"};
                                BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
                                meta.setPages(Arrays.asList(correctCode));
                                p.getInventory().getItemInMainHand().setItemMeta(meta);
                            } else if(i == 3) {
                                p.performCommand("turtle p compile sample");
                                p.sendMessage("§64) §7Now we need a Turtle where we can run the code on §8/turtle spawn <name>");
                            } else if(i == 4) {
                                p.performCommand("turtle s SampleTurtle");
                                p.sendMessage("§65) §7This is your personal Turtle now, Let's feed it with our program §8/turtle run <program>");
                                p.performCommand("turtle run sample");
                            } else if(i == 5) {
                                p.sendMessage("§66) §7You should have got the basics now, have fun experimenting with it! (by §aXXLCreeper§7)");
                            } else {  }
                            i++;
                        }
                    }, 20, 120);
                } catch(Exception e){  }
            } else {
                sendHelp(sender);
            }
        } else sendHelp(sender);
    }

//-----------------------------------------------

    private void sendHelp(CommandSender sender){
        sender.sendMessage("§l§6----------[ Mining Turtle ]----------");
        sendCommand(sender, new String[]{"p", "program"}, "Shows you a list of all your programs", "list");
        sendCommand(sender, new String[]{"p", "program"}, "Gives you a book for a new program", "new");
        sendCommand(sender, new String[]{"p", "program"}, "Allows you to edit an existing program", "edit", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Deletes an existing program", "delete", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Gives you a copy of you program to share with friends", "clone", "name");
        sendCommand(sender, new String[]{"p", "program"}, "Compiles the Code in book in your hand", "compile", "name");
        sendCommand(sender, new String[]{"i", "instructions"}, "Shows you the syntax of Tuca, the turtle language", "");
        sendCommand(sender, new String[]{"s", "spawn"}, "Spawns a new MiningTurtle", "name");
        sendCommand(sender, new String[]{"r", "run"}, "Runs on of your programs on your turtle", "name");
        //sendCommand(sender, new String[]{"bookBug"}, "Explanation of current Spigot bugs with books", "");
    }
    private void sendCommand(CommandSender sender, String[] cmdArr, String description, String... args){
        String arg = "", argNC = "";
        for(int i = 0; i < args.length; i++) {
            arg += " §" + (i == 0 ? "3" : (i == 1 ? "d" : "9")) + args[i];
            argNC += " " + args[i];
        }

        new TellRaw("").addClickAndHover("§6/" + DevathonPlugin.CMD_TURTLE + " " + modify(cmdArr, "§2") + "§3" + arg,
                ChatColor.GRAY + description,
                "/" + DevathonPlugin.CMD_TURTLE + " " + cmdArr[0] + argNC,
                TellRaw.ClickEvent.SuggestCommand).send((Player)sender);
        //sender.sendMessage("§6/" + DevathonPlugin.CMD_TURTLE + " " + modify(cmdArr, "§2") + "§3" + arg);
        //if(description.length() > 0) sender.sendMessage(indent + ChatColor.GRAY + description);
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
        sender.sendMessage("§b§mStriked§r§7§o explanation means that the statement does not work yet!");
        for(Instructions instruction : Instructions.values()){
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
                    sendInstructionHelp(sender, instruction, "Condition", "§mExecutes Code inside if the condition is true");
                    break;
                case FI:
                    sendInstructionHelp(sender, instruction, "", "§mTerminates IF block");
                    break;
                case IFNOT:
                    sendInstructionHelp(sender, instruction, "", "§mTells if to negotiate the result");
                case ELSE:
                    sendInstructionHelp(sender, instruction, "", "§mExecutes block if the IF condition returns false");
                    break;
                case ESLE:
                    sendInstructionHelp(sender, instruction, "", "§mTerminates ELSE block");
                    break;
                case WHILE:
                    sendInstructionHelp(sender, instruction, "", "§mWHILE loop will be executed until the condition becomes false");
                    break;
                case WHILENOT:
                    sendInstructionHelp(sender, instruction, "", "§mWHILENOT loop will be executed until the condition becomes true");
                    break;
                case ELIHW:
                    sendInstructionHelp(sender, instruction, "", "§mTerminates WHILE block");
                    break;
                case IS_B:
                    sendInstructionHelp(sender, instruction, ".BlockId | .Direction", "§mChecks if there is a block (with specified Id) in front (at given Direction) of the turtle");
                    break;
                case IS_SF:
                    sendInstructionHelp(sender, instruction, "", "§mChecks if the turtle storage is full");
                    break;
            }
        }
    }
    private void sendInstructionHelp(CommandSender sender, Instructions set, String args, String info){
        sender.sendMessage("§5" + set.name() + (args.length() > 0 ? indent + "§8Args: §2" + args.replace(".", "§3") : ""));
        if(info.length() > 0) sender.sendMessage(indent + "§7" + info);
    }

    private void sendProgramList(Player sender){
        sender.sendMessage("§l§6----------[ Mining Turtle §5Programs§6 ]----------");
        String[] arr = IOUtils.getProgramList(sender);
        if(arr.length == 0) sender.sendMessage("§7You don't have any programs");
        else
            for(int i = 0; i < arr.length; i++) new TellRaw("").addClickAndHover("§7(§8" + (i+1) + "§7) §a" + arr[i], "§7Click to run program", "/" + DevathonPlugin.CMD_TURTLE + " run " + arr[i], TellRaw.ClickEvent.RunCommand).send(sender); //sender.sendMessage("§7(§8" + (i+1) + "§7) §a" + arr[i]);
    }

    private void sendTurtleMessage(CommandSender sender, String message){
        sender.sendMessage("§5[Turtle] §r" + message);
    }
}
