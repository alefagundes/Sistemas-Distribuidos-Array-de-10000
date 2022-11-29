package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
	ObjectInputStream in;
	ServerSocket servSocket;
	ObjectOutputStream out;
	ArrayList<Integer[]> arrays;
	AtomicInteger controller;
	

	void generateValues(Integer number) {
		
		controller = new AtomicInteger(0);
		arrays = new ArrayList<Integer[]>();
		for (int j = 0; j < 10; j++) {
			Integer[] values = new Integer[number];
			for (int i = 0; i < number; i++) {
				values[i] = (int) Math.floor(Math.random() * 10000);
			}
			arrays.add(values);
		}
		System.out.println("Gerou os valores");

	}

	public void setup() {
		try {
			servSocket = new ServerSocket(8282);
                        
		} catch (Exception e) {
			e.printStackTrace();
		}
                System.out.println("Setup definido");
	}

	public void waitClient() {
		try {
			ConnectClient cliente = new ConnectClient(servSocket.accept(), this);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Conectei um cliente");
	}


	public static void main(String[] args) {

		Server ps = new Server();
		ps.generateValues(100);
		ps.setup();
		while (true) {
			ps.waitClient();
			System.out.println("Servidor no AR!");
		}

	}
}
