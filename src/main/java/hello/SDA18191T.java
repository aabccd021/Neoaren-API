package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SDA18191T {
    static int[] V;
    static int U;
    static HashMap hm;
    static int minim;
    static ArrayList<Integer> ans;


    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            String par[] = br.readLine().split(" ");
            int S = Integer.parseInt(par[0]);
            int N = Integer.parseInt(par[1]);
            U = Integer.parseInt(par[2]);
            V = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            String P = br.readLine();


            if (P.equals("A")) {
                hm = new HashMap();
                hm.put(0, 1);
                System.out.println(recc(S));
//                System.out.println(hm);
            }

            if (P.equals("B")) {
                minim = S;
                reccc(S, new ArrayList<Integer>());
//                System.out.println("===");
                if (ans == null) {
                    System.out.println("NA");
                } else {
                    System.out.println(minim);
                    for (int el : ans) {
                        System.out.print(el + " ");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int recc(int parent) {

        if (hm.containsKey(parent)) {
            return (int) hm.get(parent);
        }
        int counter = 0;
        for (int v : V) {
            int ans = (parent - U) - v;
            if (ans >= 0) {
                int res = recc(ans);
                counter += res;
                counter = counter % 1000000007;
            }
        }

        hm.put(parent, counter);
        return counter;
    }

    public static void reccc(int parent, ArrayList<Integer> arr) {
//        System.out.println(parent);
//        System.out.println(arr);

        int nuxt = parent - U;
        for (int v : V) {
            int next = nuxt - v;

            // System.out.println("==="+parent+"==="+v+"==="+arr);

            if (arr.size() + 1 < minim) {
                ArrayList<Integer> arru = new ArrayList<Integer>(arr);
                arru.add(v);

                // System.out.println("===");

                if (next == 0) {
                    ans = arru;
                    minim = arru.size();

                    // System.out.println("minim changed to "+minim);
                }
                if (next > 0) {
//                    System.out.println("next"+next);

                    reccc(next, arru);
                }
            }
        }
    }

}
