/**
 * Основной класс, осуществляет работу приложения
 * @author Сухарева Софья P3112
 */

import commands.*;
import tools.*;

public class Main {
    public static void main(String[] args) {

        DataInput dataInput = new DataInput();
        CommandFinder commandFinder = new CommandFinder(dataInput);
        Client client = new Client(7002, "localhost", commandFinder);
        client.run();
    }
}
