package hello;

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


        try {
            InMemoryJavaCompiler ahelloClass = InMemoryJavaCompiler.newInstance();
            ahelloClass.ignoreWarnings();
//                    helloClass = InMemoryJavaCompiler.newInstance().compile("SDA18191T", code.toString());
            helloClass = ahelloClass.compile("SDA18191T", code.toString());
            System.out.println("HHHHH");
        } catch (Exception e) {
            error = true;
            retStr = "KOMPAIL EROR\n" + e.toString();
        }
    }

    public String DoTest() {
        System.out.println("LLLL");

        ByteArrayInputStream in = new ByteArrayInputStream(problem.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(outContent));
        String answer = "def";

        final Object[] args = new Object[1];
        args[0] = new String[0];
        try {
            helloClass.getMethod("main", String[].class).invoke(null, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        answer = outContent.toString().trim().replaceAll(String.valueOf((char) 0x0D), "");

        if ((answer.replaceAll(String.valueOf((char) 0x0D), "")).equals(expected)) {
            retStr += "\nSuccess";
        } else {
            retStr += "\nFail";
        }

        retStr += "\nproblem:\n" + problem;
        retStr += "\nexpected: " + expected;
        retStr += "\nyour Answer: " + answer;
        System.setIn(stdin);
        System.setOut((PrintStream) stdout);

        return retStr;
    }
}
