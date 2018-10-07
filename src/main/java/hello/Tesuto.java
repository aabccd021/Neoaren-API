package hello;

import org.json.JSONObject;
import org.mdkt.compiler.InMemoryJavaCompiler;

import java.util.ArrayList;

public class Tesuto {
    public static void main(String[] args) {

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append("public class SDA18191T {\n");
        sourceCode.append("public static int a = 1;\n");
        sourceCode.append("   public static void main(String[] args) { System.out.println(a);a++;}");
        sourceCode.append("}");

        nani(sourceCode.toString());
    }

    public static JSONObject nani(String code) {

        JSONObject ret = new JSONObject();
        Boolean isCompileError = false;
        ret.put("isCompileError", isCompileError);
        ArrayList<JSONObject> cases = new ArrayList<JSONObject>();
        ret.put("cases", cases);

        try {
            InMemoryJavaCompiler ahelloClass = InMemoryJavaCompiler.newInstance();
            ahelloClass.ignoreWarnings();
            Class<?> helloClass = ahelloClass.compile("SDA18191T", code.toString());
        } catch (Exception e) {
            isCompileError = true;
            ret.put("isCompileError", isCompileError);
            ret.put("compileErrorMessage", e);
            return ret;
        }

        String[][] cas = new String[][]{
                {"5 3 1\n1 2 3\nA", "2"},
                {"12 3 1\n1 3 4\nB", "3\n1 4 4"},
                {"6 4 2\n1 2 3 4\nB", "1\n4"},
                {"6 1 3\n4\nA", "0"},
                {"6 1 3\n4\nB", "NA"},
        };

        int index = 0;
        for (String ca[] : cas) {
            Testah testah = new Testah(ca[0], ca[1], code);
            cases.add(testah.DoTest());
            index++;
        }
        ret.put("cases", cases);
        return ret;
    }
}
