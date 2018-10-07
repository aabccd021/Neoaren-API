package hello;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    String testGetRequest(@RequestBody Map<String, Object> request) {
        String code = (String) request.get("code");
        JSONObject response = Tesuto.nani(code);
        return response.toString();
    }
}

