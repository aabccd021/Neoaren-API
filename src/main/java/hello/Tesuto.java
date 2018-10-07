package hello;

import java.io.IOException;

public class Tesuto {
    public static void main(String[] args) {

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append("public class HelloClass {\n");
        sourceCode.append("public static int a = 1;\n");
        sourceCode.append("   public static void main(String[] args) { System.out.println(a);a++;}");
        sourceCode.append("}");

//        try {
//            nani(sourceCode.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static String nani(String code){
        String hasil = null;

        String[][] cas = new String[][]{
                {"6 1 3\n4\nB", "nani"},
                {"5 3 1\n1 2 3\nA", "2"},
                {"6 1 3\n4\nB", "NA"},
                {"12 3 1\n1 3 4\nB", "3\n1 4 4"},
                {"6 1 3\n4\nB", "NA"},
                {"6 4 2\n1 2 3 4\nB", "1\n4"},
                {"6 1 3\n4\nB", "NA"},
                {"6 1 3\n4\nA", "0"},
                {"6 1 3\n4\nB", "NA"},
        };

        for (String ca[] : cas) {
            Testah testah = new Testah(ca[0], ca[1], code);
            hasil = testah.DoTest();
        }
        return hasil;
    }
}
