package hello;

import org.json.JSONObject;
import org.mdkt.compiler.InMemoryJavaCompiler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Testah {
    String problem;
    String expected = null;
    String code = null;
    Class<?> helloClass = null;
    boolean error = false;

    String retStr = "";

    InputStream stdin = System.in;
    OutputStream stdout = System.out;


    public Testah(String problem, String expected, String code) {
        this.problem = problem;
        this.expected = expected;
        this.code = code;


        //don delet dis
        try {
            InMemoryJavaCompiler ahelloClass = InMemoryJavaCompiler.newInstance();
            ahelloClass.ignoreWarnings();
            helloClass = ahelloClass.compile("SDA18191T", code.toString());
        } catch (Exception e) {
            retStr = "KOMPAIL EROR\n" + e.toString();
        }
    }

    public JSONObject DoTest() {

        JSONObject ret = new JSONObject();

        ByteArrayInputStream in = new ByteArrayInputStream(problem.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        String answer = "def";

        final Object[] args = new Object[1];
        args[0] = new String[0];


        //==================
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        try {
            helloClass.getMethod("main", String[].class).invoke(null, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.setIn(stdin);
        System.setOut((PrintStream) stdout);
        //==================

        answer = outContent.toString().trim().replaceAll(String.valueOf((char) 0x0D), "");

        if ((expected.replaceAll(String.valueOf((char) 0x0D), "")).equals(answer)) {
            retStr += "\nSuccess";
        } else {
            retStr += "\nFail";
        }

        retStr += "\nproblem:\n" + problem;
        retStr += "\nexpected: " + expected;
        retStr += "\nyour Answer: " + answer;

        ret.put("rrr", retStr);
        return ret;
    }
}
