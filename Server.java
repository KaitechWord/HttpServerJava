import java.net.InetSocketAddress;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 9090;
        Map<String, Map<String, Integer>> storageItems = new HashMap<>();
        storageItems = readFiles();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/", new RootHandler(storageItems));
        server.setExecutor(null);
        server.start();
    }

    public static Map<String, Map<String, Integer>> readFiles() {
        Map<String, Map<String, Integer>> storageItems = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            File storage = new File(i + ".txt");
            if (storage.exists()) {
                try {
                    Map<String, Integer> itemQuantity = new HashMap<>();
                    Files.lines(storage.toPath())
                        .map(line -> line.split(":"))
                        .filter(parts -> parts.length == 2)
                        .forEach(parts -> {
                            String itemName = parts[0].trim();
                            int quantity = Integer.parseInt(parts[1].trim());
                            itemQuantity.put(itemName, quantity);
                        });
                    storageItems.put(String.valueOf(i), itemQuantity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storageItems;
    }  
}
