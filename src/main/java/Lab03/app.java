package Lab03;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class app extends MicroServer {
    
    public static void main(String[] args){
        try {
            startServer(35000);
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    
}
