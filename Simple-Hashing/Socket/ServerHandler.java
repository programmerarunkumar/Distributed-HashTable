import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {

    public static void main(String[] agrs) throws Exception {

        ServerSocket serverSocket = new ServerSocket(1111);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        while (true){
            Socket socket = serverSocket.accept();

            CacheHandling cacheHandling = new CacheHandling(socket);
            executorService.execute(cacheHandling);
        }

    }
}
