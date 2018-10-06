package hello;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mdkt.compiler.InMemoryJavaCompiler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;


@DisplayName("a")
@RunWith(value = Parameterized.class)
public class MyIOUnitTest {

    private String soal;
    private String jawaban;
    private MyIOUnitTest entityManager;

    final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public MyIOUnitTest(String soal, String jawaban) {
        this.soal = soal;
        this.jawaban = jawaban;
    }

    @Parameterized.Parameters(name = "{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"6 1 3\n4\nB", "nani"},
                {"5 3 1\n1 2 3\nA", "2"},
                {"6 1 3\n4\nB", "NA"},
                {"12 3 1\n1 3 4\nB", "3\n1 4 4"},
                {"6 1 3\n4\nB", "NA"},
                {"6 4 2\n1 2 3 4\nB", "1\n4"},
                {"6 1 3\n4\nB", "NA"},
                {"6 1 3\n4\nA", "0"},
                {"6 1 3\n4\nB", "NA"},
        });
    }

    InputStream stdin = System.in;
    OutputStream stdout = System.out;


    @Test
    @DisplayName("runs correctly given 1 cat to transport")
    public void testSingleInput() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append("public class HelloClass {\n");
        sourceCode.append("   public static void main(String[] args) { System.out.println(\"hello\");}");
        sourceCode.append("}");

        Class<?> helloClass = null;

        try {
            helloClass = InMemoryJavaCompiler.newInstance().compile("HelloClass", sourceCode.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        ByteArrayInputStream in = new ByteArrayInputStream(soal.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        String answer = "def";
        Field[] fields = null;
        Object[] fielda = null;

        try {
            System.setIn(in);
            System.setOut(new PrintStream(outContent));
//            fields = SDA18191T.class.getDeclaredFields();
            fields = helloClass.getDeclaredFields();
            fielda = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fielda[i] = fields[i].get(fields[i]);
            }

//            SDA18191T.main(new String[0]);
//            Object ins = helloClass.newInstance();
            final Object[] args = new Object[1];
            args[0] = new String[0];
            helloClass.getMethod("main", String[].class).invoke(null, args);


//            Object instance = helloClass.newInstance();
//            helloClass.getMethod("hello").invoke(instance);
//            InMemoryCompiler compiler = new InMemoryCompiler();
//CompilationPackage pkg = compiler.singleCompile("HelloWorld", source);

// **** LOAD
//CompilationPackageLoader loader = new CompilationPackageLoader();
//Map<String, Class<?>> classes = loader.loadAsMap(pkg);
//            SDA18191T aab = new SDA18191T();
//            aab.main(new String[0]);
//            Runtime.exec();
//            String path = "C:\\Users\\aabcc\\Projects\\aren\\gs-rest-service\\complete\\src\\main\\java\\hello\\SDA18191T.java";
//            Runtime.getRuntime().exec("javac " + path);
//            Runtime.getRuntime().exec("java " + path);
//            Process process = new ProcessBuilder("javac" + path).start();
//            Process process2 = new ProcessBuilder("java" + path).start();

            answer = outContent.toString().trim().replaceAll(String.valueOf((char) 0x0D), "");

            assertEquals(jawaban.replaceAll(String.valueOf((char) 0x0D), ""), answer);

        } catch (Exception e) {
            System.out.println("ada error gan");
            throw e;
        } finally {
            System.setIn(stdin);
            System.setOut((PrintStream) stdout);
            System.out.println(answer);
//            for(Field f : fields){
//                System.out.println(f);
//            }
            System.out.println("nanu");
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    Object a = fielda[i];
                    Object s = fields[i];
                    System.out.println(a);
                    System.out.println(((Field) s).get(s));
                    fields[i].set(null, fielda[i]);
//                    System.out.println(((Field) s).get(s));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("nana");
        }
    }

    private String readCode(String sourcePath) throws FileNotFoundException {
        InputStream stream = new FileInputStream(sourcePath);
        String separator = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().collect(Collectors.joining(separator));
    }

    private Path saveSource(String source) throws IOException {
        String tmpProperty = System.getProperty("java.io.tmpdir");
        Path sourcePath = Paths.get(tmpProperty, "SDA18191T.java");
        Files.write(sourcePath, source.getBytes(UTF_8));
        return sourcePath;
    }

    private Path compileSource(Path javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.toFile().getAbsolutePath());
        return javaFile.getParent().resolve("SDA18191T.class");
    }

    private void runClass(Path javaClass)
            throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName("SDA18191T", true, classLoader);
//        clazz.newInstance();
//        clazz.main(new String[0]);
    }

    public void doEvil(String sourcePath) throws Exception {
        String source = readCode(sourcePath);
        Path javaFile = saveSource(source);
        Path classFile = compileSource(javaFile);
        runClass(classFile);
    }
}
