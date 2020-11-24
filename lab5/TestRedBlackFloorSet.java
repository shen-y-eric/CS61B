import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
       AListFloorSet a = new AListFloorSet();
       RedBlackFloorSet r = new RedBlackFloorSet();

       for (int i = 0; i < 1000000; i++) {
            double n = StdRandom.uniform(-5000,5000);
            a.add(n);
            r.add(n);
       }

       for (int i = 0; i < 1000000; i++) {
           double n1 = StdRandom.uniform(-5000,5000);
           double aFloor = a.floor(n1);
           double rFloor = r.floor(n1);
           assertEquals(aFloor, rFloor, 0.00001);
        }
    }
}
