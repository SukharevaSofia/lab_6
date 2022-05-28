import commands.*;
import tools.*;
import utils.CollectionManager;
import utils.CommandManager;

public class ServerApp {
    public static void main(String[] args) {

        DataInput dataInput = new DataInput();
        String readEnv = "READENV";
        String writeEnv = "WRITEENV";
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