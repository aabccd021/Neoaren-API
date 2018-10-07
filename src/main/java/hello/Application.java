package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@SpringBootApplication
public class Application {


    @RequestMapping("/")
    @ResponseBody
    String home() {
        System.out.println("hello");
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(path = "/omae", method = RequestMethod.POST)
    @ResponseBody
    public String testGetRequest(@RequestBody Map<String, Object> request) {
//        for (Object req : request.values()) {
//            System.out.println("REQUEST : " + req.toString());
//        }
//        System.out.println(request.get("code"));
        String code = (String) request.get("code");
        String response = null;
//        try {
            response = Tesuto.nani(code);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return response;
//        return "yeah";
    }
}

