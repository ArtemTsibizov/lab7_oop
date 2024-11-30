import matrix.Matrix;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConnectionHandlerRunnable implements Runnable {

    private Socket client;
    public ConnectionHandlerRunnable(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try{
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            Matrix matr = (Matrix) ois.readObject();

            System.out.println(client.getInetAddress() + " --> " +  matr.getSumOfOddElements() );
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeDouble(matr.getSumOfOddElements());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
