package Lab03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MicroServer {
    private static final String TARGET_CLASSES_PATH = "target/classes/";

    public static Map<String, Method> services = new HashMap();

    protected static void startServer(int port) throws IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        loadComponents();
        boolean running = true;
        ServerSocket serverSocket = new ServerSocket(port);
        while (running) {
            Socket clientSocket = serverSocket.accept();
            new serverThread(services, clientSocket).start();
        }
        serverSocket.close();
    }

    private static void loadComponents() throws ClassNotFoundException, IOException {
        Set<Class<?>> classes = getClasses(new File(TARGET_CLASSES_PATH), "");
        for (Class<?> c : classes) {
            if (c.isAnnotationPresent(RestController.class)) {
                for (Method method : c.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping a = method.getAnnotation(GetMapping.class);
                        services.put(a.value(), method);
                    }
                }
            }
        }
    }

    private static Set<Class<?>> getClasses(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(getClasses(file, packageName + file.getName() + "."));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

}
// java -cp target/classes Lab03.MicroServer