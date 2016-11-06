package org.devathon.contest2016.Json;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TellRaw {

    private String tellraw;
    public TellRaw(String text){
        tellraw = BASIC_CLOSED.replace(FIRST, text);
    }

    private String BASIC_CLOSED = "{\"text\":\"\",\"extra\":[{\"text\":\"%firstMSG%\"}]}";
    private String EXTRA_TEXT = ",{\"text\":\"%et%\"}";
    private String EXTRA_HOVER = ",{\"text\":\"%ht%\",\"hoverEvent\":{\"action\":\"%ha%\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"%hm%\"}]}}}";
    private String EXTRA_CLICK = ",{\"text\":\"%ct%\",\"clickEvent\":{\"action\":\"%ca%\",\"value\":\"%cc%\"}}";
    private String EXTRA_COMB = ",{\"text\":\"%ht%\",\"clickEvent\":{\"action\":\"%ca%\",\"value\":\"%cc%\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"%hm%\"}]}}}";
    private String FIRST = "%firstMSG%", ET = "%et%", HT = "%ht%", HA = "%ha%", HM = "%hm%", CT = "%ct%", CA = "%ca%", CC = "%cc%";

    public TellRaw addText(String text) {
        String[] list = getSubStrings();
        tellraw = list[0] + EXTRA_TEXT.replace(ET, text) + list[1];
        return this;
    }

    public TellRaw addHoverText(String text, String hoverText) {
        String[] list = getSubStrings();
        tellraw = list[0] + EXTRA_HOVER.replace(HT, text).replace(HA, "show_text").replace(HM, hoverText) + list[1];
        return this;
    }

    public TellRaw addClickAndHover(String text, String hoverText, String clickToDO, ClickEvent clickAction) {
        String[] list = getSubStrings();
        tellraw = list[0] + EXTRA_COMB.replace(HT, text).replace(CA, clickAction.a()).replace(CC, clickToDO).replace(HM, hoverText) + list[1];
        return this;
    }

    public TellRaw addClickEvent(String text, String clickAction, ClickEvent e) {
        String[] list = getSubStrings();
        tellraw = list[0] + EXTRA_CLICK.replace(CT, text).replace(CA, e.a()).replace(CC, clickAction) + list[1];
        return this;
    }

//-----------------------------------------------


    public void send(Player p){
        IChatBaseComponent a = IChatBaseComponent.ChatSerializer.a(tellraw);
        PacketPlayOutChat b = new PacketPlayOutChat(a);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(b);
    }

//-----------------------------------------------

    private String[] getSubStrings(){

        String temp = null;
        int in = -1, f = 0;

        for(int i = 0; i < tellraw.length(); i++){
            temp = tellraw.substring(i, i+1);
            if(temp.equalsIgnoreCase("{")){
                in++;
            } else if(temp.equalsIgnoreCase("}")){
                in--;
            } else if(temp.equalsIgnoreCase("]") && in == 0){
                f = i;
            }
        }

        String a = tellraw.substring(0, f), b = tellraw.substring(f, tellraw.length());
        String[] c = {a, b};

        return c;
    }


    public String toString(){
        return tellraw;
    }

//-----------------------------------------------

    public enum ClickEvent{

        RunCommand("run_command"),
        SuggestCommand("suggest_command"),
        OpenUrl("open_url");

        private final String a;
        ClickEvent(final String a){
            this.a = a;
        }

        protected String a(){
            return this.a;
        }
    }

}
