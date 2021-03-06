package utils;

import java.util.List;
import java.util.stream.Collectors;

import commands.*;
import serialize.AnswerSerialize;
import serialize.CommandSerialize;

public class CommandManager {
    private InsertCommand addCommand;
    private ClearCommand clearCommand;
    private ExecuteScriptCommand executeScriptCommand;
    private HelpCommand helpCommand;
    private InfoCommand infoCommand;
    private RemoveKeyCommand removeKey;
    private SaveCommand saveCommand;
    private ShowCommand showCommand;
    private UpdateIdCommand updateIdCommand;
    private FilterGenreCommand exitCommand;
    private RemoveGreaterCommand removeLowerCommand;
    private ReplaceIfLowerCommand replaceIfLowerCommand;
    private CountDescriptionCommand countDescriptionCommand;
    private FilterGenreCommand filterGenreCommand;
    private PrintParticipantsCommand printParticipantsCommand;

    private CollectionManager collectionManager;

    private final Integer HISTORY_SIZE = 6;
    private List<Command> commands;
    public static boolean scriptMode = false;

    public CommandManager(InsertCommand addCommand, ClearCommand clearCommand,
                          ExecuteScriptCommand executeScriptCommand, HelpCommand helpCommand,
                          InfoCommand infoCommand, RemoveKeyCommand removeKey,
                          SaveCommand saveCommand, ShowCommand showCommand, UpdateIdCommand updateIdCommand,
                          FilterGenreCommand exitCommand,RemoveGreaterCommand removeLowerCommand,
                          ReplaceIfLowerCommand replaceIfLowerCommand, CountDescriptionCommand countDescriptionCommand,
                          FilterGenreCommand filterGenreCommand, PrintParticipantsCommand printParticipantsCommand, CollectionManager collectionManager) {
        this.addCommand = addCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.removeKey = removeKey;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.updateIdCommand = updateIdCommand;
        this.exitCommand = exitCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.replaceIfLowerCommand = replaceIfLowerCommand;
        this.countDescriptionCommand = countDescriptionCommand;
        this.filterGenreCommand = filterGenreCommand;
        this.printParticipantsCommand = printParticipantsCommand;
        this.collectionManager = collectionManager;
        commands = List.of(addCommand, clearCommand, executeScriptCommand, helpCommand,
                infoCommand, removeKey, saveCommand, showCommand, updateIdCommand, exitCommand, removeLowerCommand,
                replaceIfLowerCommand, countDescriptionCommand, filterGenreCommand, printParticipantsCommand);

    }


    public AnswerSerialize help() {
        return new AnswerSerialize(commands.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining("\n")));
    }


    public String commandsString() {
        return commands.stream()
                .map(x -> x.getName())
                .collect(Collectors.joining("\n"));
    }
    public AnswerSerialize launchCommand(CommandSerialize command) {

        return switch (command.getCommand()) {
            case ("help") -> help();
            case ("info") -> collectionManager.info();
            case ("show") -> collectionManager.show();
            case ("insert") -> collectionManager.insert(command.getMusicBand());
            case ("update") -> collectionManager.updateById(command.getArgument());
            case ("remove_key") -> collectionManager.removeById(command.getArgument());
            case ("clear") -> collectionManager.clear();
            case ("exit") -> collectionManager.save();

//            case ("execute_script"):
//                    return executeScript(command.getArgument());
            case ("remove_greater") -> collectionManager.removeGreater(command.getArgument());
            case ("replace_if_lower") -> collectionManager.replaceIfLower(command.getArgument());
            case ("remove_greater_key") -> collectionManager.removeGreaterKey(command.getArgument());
            case ("filter_less_than_genre") -> collectionManager.filterLessThanGenre(command.getArgument());
            case ("count_greater_than_description") ->
                    collectionManager.countGreaterThanDescription(command.getArgument());
            case ("print_field_descending_number_of_participants") -> collectionManager.printDescendingParticipants();
            default -> new AnswerSerialize("?????????????? ???? ??????????????");
        };
    }

}
