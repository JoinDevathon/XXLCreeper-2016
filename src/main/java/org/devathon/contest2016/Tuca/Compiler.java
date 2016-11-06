package org.devathon.contest2016.Tuca;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Compiler {

    private ItemStack stack;
    private String name;
    private Player p;
    private ArrayList<Error> errors = new ArrayList<>();

    public Compiler(ItemStack stack, String name, Player p){
        this.stack = stack;
        this.name = name;
        this.p = p;
    }

//-----------------------------------------------

    public void compile(){
        errors.clear();
        int IF = 0;
        int ELSE = 0;
        int WHILE = 0;
        BookMeta meta = (BookMeta) stack.getItemMeta();

        ArrayList<String> lines = new ArrayList<>();
        int page = 0;
        for(String pageStr : meta.getPages()){
            String[] lineArr = pageStr.replace("§0", "").split(Pattern.quote("\n"));
            if(lineArr.length == 0) continue;

            for(int line = 0; line < lineArr.length; line++){
                if(lineArr[line].trim().length() == 0) continue;
                String[] arr = lineArr[line].trim().split(Pattern.quote(" "));

                Instructions i0 = Instructions.fromString(arr[0]);
                if(i0.getType() == Instructions.InstructionType.ACTION || i0.getType() == Instructions.InstructionType.LOGIC){
                    if(i0 == Instructions.TURN){
                        if(arr.length > 1){
                            if(Instructions.fromString(arr[1]).getType() != Instructions.InstructionType.DIRECTION){
                                errors.add(new Error(page, line, "Argument 1 is not a direction: " + arr[1]));
                            } else {
                                if(arr.length > 2){
                                    errors.add(new Error(page, line, "Too many arguments: " + lineArr[line]));
                                }
                            }
                        } else {
                            errors.add(new Error(page, line, "Missing argument(s): " + lineArr[line]));
                        }
                    } else if (i0 == Instructions.MOVE) {
                        if(arr.length > 1){
                            if(isNumber(arr[1])){
                                if(arr.length > 2){
                                    Instructions i2 = Instructions.fromString(arr[2]);
                                    if(i2.getType() != Instructions.InstructionType.DIRECTION) {
                                        errors.add(new Error(page, line, "Argument 2 is not a direction: " + arr[2]));
                                    } else {
                                        if(arr.length > 3){
                                            errors.add(new Error(page, line, "Too many arguments: " + lineArr[line]));
                                        }
                                    }
                                }
                            } else {
                                errors.add(new Error(page, line, "Argument 1 is not a number: " + arr[1]));
                            }
                        } else {
                            errors.add(new Error(page, line, "Missing argument(s): " + lineArr[line]));
                        }
                    } else if(i0 == Instructions.MINE){
                        if(arr.length > 1){
                            if(Instructions.fromString(arr[1]).getType() != Instructions.InstructionType.DIRECTION){
                                errors.add(new Error(page, line, "Argument 1 is not a direction: " + arr[1]));
                            } else {
                                if(arr.length > 2){
                                    errors.add(new Error(page, line, "Too many arguments: " + lineArr[line]));
                                }
                            }
                        }
                    } else {
                        //Check for logic structure
                        if(i0 == Instructions.IF || i0 == Instructions.IFNOT){
                            IF++;
                        } else if(i0 == Instructions.FI){
                            IF--;
                            continue;
                        } else if(i0 == Instructions.ELSE){
                            IF--;
                            ELSE++;
                            continue;
                        } else if(i0 == Instructions.ESLE) {
                            ELSE--;
                            continue;
                        } else if(i0 == Instructions.WHILE || i0 == Instructions.WHILENOT) {
                            WHILE++;
                        } else if(i0 == Instructions.ELIHW) {
                            WHILE--;
                            continue;
                        }

                        if(arr.length > 1){
                            Instructions i1 = Instructions.fromString(arr[1]);
                            if(i1 == Instructions.IS_B){
                                if(arr.length > 2){
                                    if(!isNumber(arr[2])){
                                        if(Instructions.fromString(arr[2]).getType() != Instructions.InstructionType.DIRECTION){
                                            errors.add(new Error(page, line, "Argument 2 is neither a number, nor a direction: " + arr[1]));
                                        } else {
                                            if(arr.length > 3){
                                                errors.add(new Error(page, line, "Too many arguments: " + lineArr[line]));
                                            }
                                        }
                                    }
                                }
                            } else if (i1 != Instructions.IS_SF){
                                errors.add(new Error(page, line, "Invalid argument: " + arr[1]));
                                if(arr.length > 2){
                                    errors.add(new Error(page, line, "Too many arguments: " + lineArr[line]));
                                }
                            }
                        } else {
                            errors.add(new Error(page, line, "Missing argument(s): " + lineArr[line]));
                        }
                    }
                }
            }
            page++;
        }

        //System.out.println("i:" + IF + " e:" + ELSE + " w:" + WHILE);

        if(IF != 0) errors.add(new Error(-1, -1, "Please take a look at your IF/ELSE Conditions. " + (IF > 0 ? "To many IF" : "non-closed IF block(s)")));
        if(ELSE != 0) errors.add(new Error(-1, -1, "Please take a look at your IF/ELSE Conditions. " + (ELSE > 0 ? "To many ELSE" : "non-closed ELSE block(s)")));
        if(WHILE != 0) errors.add(new Error(-1, -1, "Please take a look at your IF/ELSE Conditions. " + (WHILE > 0 ? "To many ELSE" : "non-closed WHILE block(s)")));
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

//-----------------------------------------------

    public boolean hasErrors(){
        return errors.size() > 0;
    }

    public ArrayList<Error> getErrors(){
        return errors;
    }

    public void save(){
        IOUtils.save(((BookMeta)stack.getItemMeta()).getPages(), name, p);
    }

}
