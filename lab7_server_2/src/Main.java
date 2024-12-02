import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;
import java.util.EventObject;
import java.util.concurrent.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {




        int port = 4000;
        int maxConnections = 200;
        int threadPoolSize = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port, maxConnections);
        } catch (IOException e) {
            System.out.println("Не удалось запустить сервер на порту" + port);
            throw new RuntimeException(e);
        }

        while(true){
            Socket client;
            try {
                client = serverSocket.accept();
                executorService.execute(new ConnectionHandlerRunnable(client));
            } catch (IOException e) {
                System.out.println("Не удалось получить сокет");
                continue;
            }


        }


    }
}