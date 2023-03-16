package src;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {

    public static void main(String[] agrs) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the port");
        int port = scanner.nextInt();

        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        while (true){
            Socket socket = serverSocket.accept();

            CacheHandling cacheHandling = new CacheHandling(socket);
            executorService.execute(cacheHandling);
        }

    }

}
