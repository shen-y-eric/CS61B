import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        List<Integer> N = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        AList a1 = new AList(); // 1000
        int n1 = 1000;

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < n1; i++) {
            a1.addLast(i);
        }
        double a1Time = sw1.elapsedTime();

        N.add(n1);
        opCounts.add(n1);
        times.add(a1Time);


        AList a2 = new AList(); // 2000
        int n2 = 2000;

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < n2; i++) {
            a2.addLast(i);
        }
        double a2Time = sw2.elapsedTime();

        N.add(n2);
        opCounts.add(n2);
        times.add(a2Time);


        AList a3 = new AList(); //4000
        int n3 = 4000;

        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < n3; i++) {
            a3.addLast(i);
        }
        double a3Time = sw3.elapsedTime();

        N.add(n3);
        opCounts.add(n3);
        times.add(a3Time);


        AList a4 = new AList(); //8000
        int n4 = 8000;

        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < n4; i++) {
            a4.addLast(i);
        }
        double a4Time = sw4.elapsedTime();

        N.add(n4);
        opCounts.add(n4);
        times.add(a4Time);


        AList a5 = new AList(); //16000
        int n5 = 16000;

        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < n5; i++) {
            a5.addLast(i);
        }
        double a5Time = sw5.elapsedTime();

        N.add(n5);
        opCounts.add(n5);
        times.add(a5Time);


        AList a6 = new AList(); //32000
        int n6 = 32000;

        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < n6; i++) {
            a6.addLast(i);
        }
        double a6Time = sw6.elapsedTime();

        N.add(n6);
        opCounts.add(n6);
        times.add(a6Time);


        AList a7 = new AList();
        int n7 = 64000;

        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < n7; i++) {
            a7.addLast(i);
        }
        double a7Time = sw7.elapsedTime();

        N.add(n7);
        opCounts.add(n7);
        times.add(a7Time);


        AList a8 = new AList(); //128000
        int n8 = 128000;

        Stopwatch sw8 = new Stopwatch();
        for (int i = 0; i < n8; i++) {
            a8.addLast(i);
        }
        double a8Time = sw8.elapsedTime();

        N.add(n8);
        opCounts.add(n8);
        times.add(a8Time);

        printTimingTable(N, times, opCounts);
    }


}
