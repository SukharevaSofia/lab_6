package tools;

import exceptions.*;
import serialize.CommandSerialize;

import java.util.*;

import static tools.ConsoleUI.greeting;

public class CommandFinder {

  private DataInput dataInput;
  static boolean scriptMode = false;

  public CommandFinder(DataInput dataInput) {
    this.dataInput =  dataInput;
    greeting();
  }

  public CommandSerialize commandSearcher() {

    while (true) {
      try {

        String[] userCommand = ConsoleUI.reader.nextLine().split(" ");
        List<String> commandsLen1 = List.of("help", "info", "show", "clear",
                "remove_first", "history", "print_field_descending_front_man", "exit");
        List<String> commandsLen2 = List.of("remove_by_id", "execute_script",
                "remove_greater", "remove_all_by_description", "count_less_than_number_of_participants");

        if (userCommand.length > 2)
          throw new CommandException();

        switch(userCommand.length){

          case(1):{
            if(commandsLen1.contains(userCommand[0])){
              return new CommandSerialize(userCommand[0]);
            }
            else if(userCommand[0].equals( "insert")){
              return new CommandSerialize(userCommand[0], userCommand[1]);
            }
          }

          case (2):
            if(commandsLen2.contains(userCommand[0])){
              return new CommandSerialize(userCommand[0], userCommand[1]);
            } else if(userCommand[0].equals("update")){
              return new CommandSerialize(userCommand[0], userCommand[1], dataInput.askMusicBand());
            }

          default:
            throw new IllegalArgumentException();


        }



      } catch (CommandException | IllegalArgumentException e) {
        ConsoleUI.output("Команда не найдена");
      }
    }
  }







}
