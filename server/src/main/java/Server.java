import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serialize.AnswerSerialize;
import serialize.CommandSerialize;
import utils.CommandManager;
import tools.ConsoleUI;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


class Server implements Runnable {
    private SocketChannel clientSocket; //сокет для общения
    private CommandManager commandManager;
    private int PORT;
    private ServerSocketChannel server; // серверный сокет
    Logger logger = LogManager.getLogger(Server.class);
    private ObjectInputStream in;// поток чтения из сокета
    private ObjectOutputStream out;// поток записи в сокет


    public Server(CommandManager commandManager, int PORT) {
        this.commandManager = commandManager;
        this.PORT = PORT;
    }

    @Override
    public void run() {

        openServer();
        connectToClient();
        while (true) {


            try {

                CommandSerialize command = (CommandSerialize) in.readObject();
                logger.info("Команда принята");
                AnswerSerialize answer = commandManager.launchCommand(command);
                out.writeObject(answer);
                out.flush();

            } catch (EOFException e) {

            } catch (SocketException e) {
                logger.error("Потеряно соединение");
                ConsoleUI.message("Соединение потеряно");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }


    private void connectToClient(){
        try {
            logger.info("Прослушивание порта '$PORT'...");
            clientSocket = server.accept();
            out = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            in = new ObjectInputStream(clientSocket.socket().getInputStream());
            logger.info("Соединение с клиентом успешно установлено.");
            ConsoleUI.message("Соединение с клиентом успешно установлено.");

        } catch (SocketTimeoutException e) {
            logger.warn("Превышено время ожидания подключения!");
            ConsoleUI.message("Превышено время ожидания подключения!");
        } catch (IOException e){
            logger.error("Произошла ошибка при соединении с клиентом!");
            ConsoleUI.message("Произошла ошибка при соединении с клиентом!");
        }catch (NullPointerException e){
            ConsoleUI.message("Соединение с клиентом не установлено");
        }
    }

//    private void serverStop() {
//        try {
//            logger.info("Завершение работы сервера...");
//            ConsoleUI.message("Завершение работы сервера...");
//            if (server == null) throw CloseSocketException();
//            server !!.close()
//            println("Работа сервера успешно завершена.")
//            logger.info("Работа сервера успешно завершена.")
//        } catch (e :CloseSocketException){
//            println("Невозможно завершить работу сервера : сервер изначально был закрыт!")
//            logger.error("Невозможно завершить работу сервера : сервер изначально был закрыт!")
//        } catch(e :IOException){
//            println("Произошла ошибка при завершении работы сервера!")
//            logger.error("Произошла ошибка при завершении работы сервера!")
//        }
//    }

    private void openServer() {
        try {
            logger.info("Запуск сервера...");
            ConsoleUI.message("Запуск сервера...");
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(PORT));
            logger.info("Сервер успешно запущен.");
           ConsoleUI.message("Сервер успешно запущен.");
        } catch (IOException e){
            logger.fatal("Произошла ошибка при попытке использовать порт '$PORT'!");
            ConsoleUI.message("Произошла ошибка при попытке использовать порт '$PORT'!");
        }
    }

}

