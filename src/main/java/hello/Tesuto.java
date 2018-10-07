package hello;

import org.mdkt.compiler.InMemoryJavaCompiler;

public class Tesuto {
    public static void main(String[] args) {

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append("public class SDA18191T {\n");
        sourceCode.append("public static int a = 1;\n");
        sourceCode.append("   public static void main(String[] args) { System.out.println(a);a++;}");
        sourceCode.append("}");

        nani(sourceCode.toString());
    }

    public static String nani(String code) {

        try {
            InMemoryJavaCompiler ahelloClass = InMemoryJavaCompiler.newInstance();
            ahelloClass.ignoreWarnings();
            Class<?> helloClass = ahelloClass.compile("SDA18191T", code.toString());
        } catch (Exception e) {
            String retStr = "KOMPAIL EROR\n" + e.toString();
            return retStr;
        }

        String hasil = "";

        String[][] cas = new String[][]{
                {"5 3 1\n1 2 3\nA", "2"},
                {"12 3 1\n1 3 4\nB", "3\n1 4 4"},
                {"6 4 2\n1 2 3 4\nB", "1\n4"},
                {"6 1 3\n4\nA", "0"},
                {"6 1 3\n4\nB", "NA"},
        };

        for (String ca[] : cas) {
//            System.out.println(ca[0]);
            Testah testah = new Testah(ca[0], ca[1], code);
            hasil += testah.DoTest();
//            System.out.println(hasil);
        }
        return hasil;
    }
}
