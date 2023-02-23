import engine.BooleanSearchEngine;
import engine.Saving;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 8989;
        File pdfsDir = new File("pdfs");
        String word;
        BooleanSearchEngine engine = new BooleanSearchEngine(pdfsDir);
        Saving saving = new Saving();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер работает");
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    System.out.println("Новое подключение: " + socket.getPort());
                    out.println("----------< Введите искомое слово или команду 'end' >----------");
                    word = in.readLine();
                        saving.save(engine.search(word), word);
                        out.println(engine.search(word));
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
/*
  ДЛЯ ПРИМЕРА В КОНОСЛЬ ВВОДИЛ
  Бизнес-кейс проекта — анализ целесообразности проекта
 */