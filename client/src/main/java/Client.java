import serialize.AnswerSerialize;
import serialize.CommandSerialize;
import tools.CommandFinder;
import tools.ConsoleUI;

import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class Client implements Runnable {
    private int PORT;
    private String HOST;
    private SocketChannel socketChannel;
    private DatagramChannel datagramChannel;
    private CommandFinder commandFinder;
    private ObjectOutputStream out; 
    private ObjectInputStream in;

    public Client(int PORT, String HOST, CommandFinder commandFinder){
        this.PORT = PORT;
        this.HOST = HOST;
        this.commandFinder = commandFinder;
    }


    @Override
    public void run(){
        openClient();
        while(true){
            request();
        }



    }



    private void openClient() {

        while(true) {

            try {

                try {

                    socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
                    in = new ObjectInputStream(socketChannel.socket().getInputStream());
                    out = new ObjectOutputStream(socketChannel.socket().getOutputStream());
                    break;

                } catch (ConnectException e) {
                    System.out.println("Cервер не отвечает");
                    Thread.sleep(5000L);
                } catch (IOException e) {

                    // FIXME: 5/28/22
                }
            }
            catch (InterruptedException ignored){

            }
        }

    }

    private void request(){
        try{
            CommandSerialize command = commandFinder.commandSearcher();
            out.writeObject(command);
            out.flush();
            AnswerSerialize serverWords = (AnswerSerialize) in.readObject();
            ConsoleUI.output(serverWords.getMessage());

        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (ClassCastException e){

        }


    }





}
