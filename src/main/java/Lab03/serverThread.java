package Lab03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class serverThread extends Thread{

    public Map<String, Method> services;
    public Socket clientSocket;

    public serverThread(Map<String, Method> services, Socket clienSocket){
        this.services = services;
        this.clientSocket = clienSocket;
    }

    @Override
    public void run(){
        try {
            System.out.println("New thread");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String[] dataPage = readInput(in).split(" ");
            String types = dataPage[0];
            String name = dataPage[1];
            String outputLine = createOutput(types, name, clientSocket.getOutputStream());
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        
    }

    public static void sendImage(String outputLine, OutputStream output, File file) throws IOException {
        output.write(outputLine.getBytes());
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }

    public String createOutput(String dataPage, String name, OutputStream output) throws IOException, IllegalAccessException, InvocationTargetException {
        Method method = services.get(name);
        String nameFile = "";
        if (method != null) {
            nameFile = (String) services.get(name).invoke("null", "parametro");
        }
        else{
            nameFile = name.split("\\.")[0];
        }
            String outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + dataPage + "\r\n"
                    + "\r\n";
            String extentions = dataPage.split("/")[1];
            if(extentions.equals("javascript")){
                extentions = "js";
            }
            File file = new File("target/classes/Lab03/files/" + nameFile + "." + extentions);
            if (dataPage.contains("image")) {
                sendImage(outputLine, output, file);
                return null;
            } else {
                outputLine += readHtmlFile("target/classes/Lab03/files/" + nameFile + "." + extentions);
                return outputLine;
            }
    }

    public static String readHtmlFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
            String line;
            BufferedReader br;
            try {
                br = new BufferedReader(new java.io.FileReader(filePath));
                while ((line = br.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                System.out.println("");
            }
        return contentBuilder.toString();
    }

    public static String readInput(BufferedReader in) throws IOException {
        String inputLine;
        String type = null;
        String name = null;
        boolean isFirstLine = true;
        while ((inputLine = in.readLine()) != null) {
            if (isFirstLine) {
                String[] data = inputLine.split(" ");
                isFirstLine = false;
                name = data[1];
                if (name.split("\\.").length > 1) {
                    type = getType(name.split("\\.")[1]);
                }
            }
            if (inputLine.isEmpty()) {
                break;
            }
        }
        if (type == null)
            type = "text/html";
        return type + " " + name;
    }

    public static String getType(String type) {
        HashMap<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put("png", "image/png");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("app", "application/json");
        mimeTypes.put("icon", "icon");
        return mimeTypes.get(type);
    }

}