import commands.*;
import utils.CommandManager;
import utils.CollectionManager;
import tools.DataInput;

public class Main {
    public static void main(String[] args) {
        DataInput dataInput = new DataInput();
        final String readEnv = "READENV";
        final String writeEnv = "WRITEENV";
        CollectionManager collectionManager = new CollectionManager(readEnv, writeEnv,dataInput);

        CommandManager commandManager = new CommandManager(
                new InsertCommand(),
                new ClearCommand(),
                new ExecuteScriptCommand(),
                new HelpCommand(),
                new InfoCommand(),
                new RemoveKeyCommand(),
                new SaveCommand(),
                new ShowCommand(),
                new UpdateIdCommand(),
                new FilterGenreCommand(),
                new RemoveGreaterCommand(),
                new ReplaceIfLowerCommand(),
                new CountDescriptionCommand(),
                new FilterGenreCommand(),
                new PrintParticipantsCommand(),
               collectionManager
        );
        Server server = new Server(commandManager, 7002);
        server.run();
    }
}