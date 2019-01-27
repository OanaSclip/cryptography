import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        millerRabin(781);
        System.out.println(compute(2,1751,2357));
    }

    private static void millerRabin(Integer n) {
        List<Integer> b = new ArrayList<>();
        Integer s = 0, n1 = n - 1, t = 1;

        while (n1 % 2 == 0) {
            n1 = n1 / 2;
            s++;
        }
        t = n1;
        System.out.print("s = ");
        System.out.println(s);
        System.out.print("t = ");
        System.out.println(t);

        for (Integer j = 1; j <n ; j++) {
            Long at = compute(j, t, n);
            List<Long> sequence = new ArrayList<>();
            sequence.add(at);
            Long next;
            for (int i = 1; i <= s; i++) {
                next = (at * at) % n;
                sequence.add(next);
                at = next;
            }

            if (sequence.get(0) == 1) {
                b.add(j);
            } else {
                for (int i = 0; i < sequence.size() - 1; i++) {
                    if (sequence.get(i) == n - 1) {
                        b.add(j);
                    }
                }
            }
            if(j==46){
                System.out.println(sequence);
            }
        }
        System.out.println("all bases b with respect to which a composite odd " +
                "number n is strong pseudoprime");
        System.out.println(b);

    }

    private static Long compute(Integer a, Integer t, Integer n) {
        Boolean ok=false;
        List<MyPair<Integer, Integer>> squaring_modular_values = new ArrayList<>();
        squaring_modular_values.add(new MyPair<>(1, a % n));
        if(a==46){
            ok=true;
        }
        Integer j = 0;
        for (int i = 2; i < t; i = i * 2) {
            j++;
            squaring_modular_values.add(new MyPair<>(i, (a * a) % n));
            a = squaring_modular_values.get(j).getValue();
        }
//        System.out.println(squaring_modular_values);

        Long result = Long.valueOf(1);

        for (int i = squaring_modular_values.size() - 1; i >= 0; i--) {
            if (squaring_modular_values.get(i).getKey() <= t) {
                t = t - squaring_modular_values.get(i).getKey();
                result *= squaring_modular_values.get(i).getValue();
            }
        }
        if(ok){
            System.out.println(squaring_modular_values);
        }


        return result % n;
    }
}
