import engine.BooleanSearchEngine;
import engine.Saving;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 8989;
    private File pdfsDir = new File("pdfs");
    private String word;

    private BooleanSearchEngine engine = new BooleanSearchEngine(pdfsDir);
    private Saving saving = new Saving();


    public Server() throws IOException {
        start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер работает");
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    while (true) {
                        word = in.readLine();
                        saving.Save(engine.search(word));
                        out.println(engine.search(word));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
