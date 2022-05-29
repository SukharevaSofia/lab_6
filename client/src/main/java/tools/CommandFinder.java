package tools;

import exceptions.*;
import serialize.CommandSerialize;

import java.util.*;
import java.util.stream.Collectors;

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
        String userCommandCheck = Arrays.stream(userCommand)
            .collect(Collectors.joining(""));
        List<String> commandsLen1 = List.of("help", "info", "show", "clear",
            "print_field_descending_number_of_participants", "exit", "insert");
        List<String> commandsLen2 = List.of("remove_key", "remove_greater", "replace_if_lower",
            "remove_greater_key", "count_greater_than_description", "filter_less_than_genre");
        if (userCommandCheck.equals(""))
          continue;
        if (userCommand.length > 2)
          throw new CommandException();

        switch (userCommand.length) {

          case (1): {
            if (commandsLen1.contains(userCommand[0])) {
              return new CommandSerialize(userCommand[0]);
            } else if (userCommand[0].equals("insert")) {
              return new CommandSerialize(userCommand[0], "", dataInput.askMusicBand());
            }
          }

          case (2):
            if (userCommand.length == 2) {
              if (commandsLen2.contains(userCommand[0])) {
                return new CommandSerialize(userCommand[0], userCommand[1]);
              } else if (userCommand[0].equals("update")) {
                return new CommandSerialize(userCommand[0], "band", dataInput.askMusicBand());
              } else if (userCommand[0].equals("insert")) {
                return new CommandSerialize(userCommand[0], "band", dataInput.askMusicBand());
              }
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
