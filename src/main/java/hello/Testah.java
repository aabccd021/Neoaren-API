package hello;

import org.json.JSONObject;
import org.mdkt.compiler.InMemoryJavaCompiler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class Testah {
    String problem;
    String expected;
    String code;
    String answer;
    Class<?> helloClass = null;
    //    boolean error = false;
    boolean pass = false;
    boolean timeout = false;
    long duration = 0;

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
            System.out.println("KOMPAIL ERROR");
        }
    }

    public JSONObject DoTest() {

        JSONObject ret = new JSONObject();

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(problem.getBytes());
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();

            final Object[] args = new Object[1];
            args[0] = new String[0];


            //==================
            System.setIn(in);
            System.setOut(new PrintStream(outContent));
            long startTime = 0;
            long endTime = 0;

            ExecutorService executor = Executors.newCachedThreadPool();
            Callable<Object> task = new Callable<Object>() {
                public Object call() {
                    try {
                        return helloClass.getMethod("main", String[].class).invoke(null, args);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            Future<Object> future = executor.submit(task);
            try {
                startTime = System.nanoTime();
                Object result = future.get(5, TimeUnit.SECONDS);
            } catch (TimeoutException ex) {
                timeout = true;
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            } finally {
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                future.cancel(true); // may or may not desire this
            }

            System.setIn(stdin);
            System.setOut((PrintStream) stdout);
            //==================

            answer = outContent.toString().trim().replaceAll(String.valueOf((char) 0x0D), "");

            if ((expected.replaceAll(String.valueOf((char) 0x0D), "")).equals(answer)) {
                pass = true;
            }

            ret.put("problem", problem);
            ret.put("expected", expected);
            ret.put("answer", answer);
            ret.put("timeout", timeout);
            ret.put("pass", pass);
            ret.put("duration", duration);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
