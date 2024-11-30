import matrix.Matrix;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        int port = 4001;
        int maxConnections = 200;

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket( port, maxConnections);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true){
            Socket client;
            try {
                client = serverSocket.accept();

                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                Matrix matr = (Matrix) ois.readObject();

                System.out.println(client.getInetAddress() + " --> " + matr.getSumOfOddElements() );
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeDouble(matr.getSumOfOddElements());
                client.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}