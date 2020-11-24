import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        List<Integer> N = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            opCounts.add(10000);
        }

        SLList a1 = new SLList(); // 1000
        int n1 = 1000;

        for (int i = 0; i < n1; i++) {
            a1.addLast(i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a1.getLast();
        }
        double a1Time = sw1.elapsedTime();

        N.add(n1);
        times.add(a1Time);


        SLList a2 = new SLList(); // 2000
        int n2 = 2000;

        for (int i = 0; i < n2; i++) {
            a2.addLast(i);
        }
        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a2.getLast();
        }
        double a2Time = sw2.elapsedTime();

        N.add(n2);
        times.add(a2Time);


        SLList a3 = new SLList(); //4000
        int n3 = 4000;

        for (int i = 0; i < n3; i++) {
            a3.addLast(i);
        }
        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a3.getLast();
        }
        double a3Time = sw3.elapsedTime();

        N.add(n3);
        times.add(a3Time);


        SLList a4 = new SLList(); //8000
        int n4 = 8000;

        for (int i = 0; i < n4; i++) {
            a4.addLast(i);
        }
        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a4.getLast();
        }
        double a4Time = sw4.elapsedTime();

        N.add(n4);
        times.add(a4Time);


        SLList a5 = new SLList(); //16000
        int n5 = 16000;

        for (int i = 0; i < n5; i++) {
            a5.addLast(i);
        }
        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a5.getLast();
        }
        double a5Time = sw5.elapsedTime();

        N.add(n5);
        times.add(a5Time);


        SLList a6 = new SLList(); //32000
        int n6 = 32000;

        for (int i = 0; i < n6; i++) {
            a6.addLast(i);
        }
        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a6.getLast();
        }
        double a6Time = sw6.elapsedTime();

        N.add(n6);
        times.add(a6Time);


        SLList a7 = new SLList(); //64000
        int n7 = 64000;

        for (int i = 0; i < n7; i++) {
            a7.addLast(i);
        }
        Stopwatch sw7 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a7.getLast();
        }
        double a7Time = sw7.elapsedTime();

        N.add(n7);
        times.add(a7Time);


        SLList a8 = new SLList(); //128000
        int n8 = 128000;

        for (int i = 0; i < n8; i++) {
            a8.addLast(i);
        }
        Stopwatch sw8 = new Stopwatch();
        for (int i = 0; i < 10000; i++) {
            a8.getLast();
        }
        double a8Time = sw8.elapsedTime();

        N.add(n8);
        times.add(a8Time);

        printTimingTable(N, times, opCounts);
    }

}
