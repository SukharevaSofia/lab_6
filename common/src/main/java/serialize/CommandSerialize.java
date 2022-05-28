package serialize;


import data.MusicBand;

import java.io.Serializable;

public class CommandSerialize implements Serializable {
    private String command;
    private String argument;

    private MusicBand musicBand;


    public CommandSerialize(String command){
        this.command = command;

    }

    public CommandSerialize(String command, MusicBand musicBand){
        this.musicBand = musicBand;
    }

    public CommandSerialize(String command, String argument, MusicBand musicBand){
        this.command = command;
        this.argument = argument;
        this.musicBand = musicBand;
    }

    public CommandSerialize(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public String getCommand(){
        return command;
    }

    public String getArgument(){
        return argument;
    }

    public MusicBand getMusicBand() { return musicBand;}
}
