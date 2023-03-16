import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class CacheHandling implements Runnable{

    private  Socket socket;

    CacheHandling(Socket socket){
        this.socket = socket;
    }

    private static ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<>();

    public void run() {

        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String inputStr = dataInputStream.readUTF();
            JSONObject inputJSON = new JSONObject(inputStr);

            String method = inputJSON.getString("METHOD");
            int key = inputJSON.getInt("KEY");

            System.out.println("Method : " + method + " Key : " + key);

            switch (method){
                case "GET":
                    String output = get(key);
                    dataOutputStream.writeUTF(output);
                    dataOutputStream.flush();
                    break;

                case "PUT":
                    String value = inputJSON.getString("VALUE");
                    boolean added = put(key, value);
                    dataOutputStream.writeUTF(String.valueOf(added));
                    break;
            }
        }catch (Exception e){
            System.out.println("Exception while handling the cache. Exception : " + e);
        }

    }

    public static String get(int key){
        String value = (String) concurrentHashMap.get(key);

        System.out.println("Key : " + key + " Value : " + value);
        return value;
    }

    public static boolean put(int key, String value){
        System.out.println("Key : " + key + " Value : " + value);

        String data = (String) concurrentHashMap.put(key, value);
        return data != null;
    }

}
