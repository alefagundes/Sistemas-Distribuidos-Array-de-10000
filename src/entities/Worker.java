package entities;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class Worker implements Runnable{

    Integer[] values;
    ObjectInputStream in;
    Socket socket = null;
    ObjectOutputStream out;
    
    void start() {
    	Thread t = new Thread(this);
    	t.start();
    }

    void connect() {
        try {
            InetAddress i = InetAddress.getLocalHost();
            socket = new Socket(i.getHostName(), 8282);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Conectado ao " + socket.getLocalAddress());
    }

    void getReq() {
        try {
            System.out.println("Esperando request!");
            values = (Integer[]) in.readObject();
            System.out.println(values);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    void sort() {
        Arrays.sort(values);
        sendReq();
    }

    void sendReq() {
        try {
            System.out.println("Enviando dado!");
            out.writeObject(values);
            values = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void notifyStandBy() {
        try {
            System.out.println("Executei e estou de boas!");
            String notification = new String("I'm free!");
            out.writeObject(notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Worker w = new Worker();
        w.start();
        System.out.println("Chamei um worker!");
        
    }

	@Override
	public void run() {
		connect();
		notify();
		while (true) {
         getReq();
        }
		
	}

}
