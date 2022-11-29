package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ConnectClient extends Thread{
    Socket s;
    ObjectInputStream in;
	ObjectOutputStream out;
	Server server;

    public ConnectClient(Socket s, Server server) {
        this.s = s;
        this.server = server;
    }
    
    public void setup(){
        try {
        	in = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void getRequest() {
		try {
			Object received = in.readObject();
			if (received instanceof Integer[]) {
				System.out.println(received);

			} else {
				System.out.println("cheguei no get");
				System.out.println(received);
				sendDataReq();
				
			}
		}catch (Exception e) {
			e.printStackTrace();

		}
	}

	
	public void sendDataReq() {
		try {
			out.writeObject(server.arrays.get(server.controller.get()));
			server.controller.set(server.controller.get() + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void run() {
    	System.out.println("cheguei na run thread");
        while (true) {            
            getRequest();
        }
        
    }
    
    
}
