package src;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClientHandler {

    private static void get() throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Get the key");
        int key = scanner.nextInt();

        Long startTime = System.currentTimeMillis();

        Map nodeMap = Configuration.getNodeMap();
        int len = nodeMap.size();

        int hash = key % len;
        System.out.println("Hash : " + hash);
        if(nodeMap.containsKey(hash)){
            ServerNode serverNode = (ServerNode) nodeMap.get(hash);
            Socket socket = new Socket(serverNode.getIp(), serverNode.getPort());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("METHOD","GET");
            jsonObject.put("KEY", key);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String value = dataInputStream.readUTF();
            System.out.println("Key : " + key + " Value : " + value);


            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("GET. TOTALTIME : " + (endTime-startTime));

    }

    private static void put() throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Get the key");
        int key = scanner.nextInt();

        System.out.println("Get the Value");
        String value = scanner.next();

        Long startTime = System.currentTimeMillis();

        Map nodeMap = Configuration.getNodeMap();
        int len = nodeMap.size();

        int hash = key % len;
        if(nodeMap.containsKey(hash)){
            ServerNode serverNode = (ServerNode) nodeMap.get(hash);
            Socket socket = new Socket(serverNode.getIp(), serverNode.getPort());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("METHOD","PUT");
            jsonObject.put("KEY", key);
            jsonObject.put("VALUE", value);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            dataOutputStream.close();

            socket.close();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("PUT. TOTALTIME : " + (endTime-startTime));

    }

    public static void main(String[] args) throws Exception {

        Configuration.initCacheConfiguration();

        int type;
        do{
            System.out.println("1.Read 2.Write 3.Exit");
            Scanner scanner = new Scanner(System.in);
            type = scanner.nextInt();
            switch (type){
                case 1:
                   get();
                   break;

                case  2:
                    put();
                    break;
            }
        }while (type != 3);

    }

}