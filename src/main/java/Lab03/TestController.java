package Lab03;

@RestController
public class TestController {
    
    @GetMapping("/")
    public static String index(@RequestParam(value = "basura", defaultValue = "basura") String basura){
        return "index";
    }

    @GetMapping("/test")
    public static String pag(){
        return "page";
    }

}
