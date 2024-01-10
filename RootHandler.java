import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootHandler implements HttpHandler {
        Map<String, Map<String, Integer>> storageItems = new HashMap<>();

        public RootHandler(Map<String, Map<String, Integer>> storageItems) {
            this.storageItems = storageItems;
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            String content = "<h1>KROPLICZANKA STORAGE SYSTEM<br>WELCOME TO</h1>";
            String requestPath = he.getRequestURI().getPath().substring(1);
            List<String> storageIds = Arrays.asList(requestPath.split("_"));
            content += getAllStoragesContent(storageIds);  
            he.sendResponseHeaders(200, content.length());
            OutputStream os = he.getResponseBody();
            os.write(content.getBytes());
            os.close();
        }

        private String getAllStoragesContent(List<String> storageIds) throws IOException {
            String allStoragesContent = "<ul class=\"columns\" data-columns=\"2\">";
            for (String storageId : storageIds) {
                allStoragesContent += getStorageContent(storageId);
            }
            allStoragesContent += "</ul>";
            return allStoragesContent;
        }

        private String getStorageContent(String storageId) throws IOException {
            String storageContent = "";
            Map<String, Integer> itemQuantity = storageItems.get(storageId);
            for (Map.Entry<String, Integer> entry : itemQuantity.entrySet()) {
                storageContent += "<li>" + entry.getKey() + ":</li><li>" + entry.getValue() + "</li>";
            }
            return storageContent;
        }
}