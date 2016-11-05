package org.devathon.contest2016.Tuca;

public class Error {

    private int page;
    private int line;
    private String error;

    public Error(int page, int line, String error){
        this.page = page;
        this.line = line;
        this.error = error;
    }

//-----------------------------------------------

    @Override
    public String toString(){
        return "§7p:§3" + page + "§7, l:§2" + line + ", e:§4 " + error;
    }

}
