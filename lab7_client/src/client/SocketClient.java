package client;

import matrix.Matrix;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
    public static double getSumOfOddElements(String addr, int port, Matrix matrix) throws IOException{

        double sumOfOddElements;
        Socket socket = new Socket(addr, port);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(matrix);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        sumOfOddElements = dis.readDouble();

        socket.close();

        return sumOfOddElements ;
    }
}
