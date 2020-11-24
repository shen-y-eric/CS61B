package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        a.add("Hi", 1);
        a.add("Hello", 2);
        a.add("Lol", 0);

        assertEquals("Lol", a.getSmallest());

        a.add("Yeet", -1);
        assertEquals("Yeet", a.getSmallest());

        a.removeSmallest();
        assertEquals("Lol", a.getSmallest());

        a.changePriority("Hello", -2);
        assertEquals("Hello", a.getSmallest());
    }

    @Test
    public void timingTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 100000; i++) {
            a.add(Integer.toString(i), i);
        }
        double a1Time = sw1.elapsedTime();

        System.out.println("Min PQ Build Time: " + a1Time);
    }

}
