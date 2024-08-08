package chapter_seven;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5050;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    System.out.println("Enter your message: ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();

        System.out.println("Connected to chat server. Start typing your messages.");
        System.out.println("Enter your messages:");
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }
}
