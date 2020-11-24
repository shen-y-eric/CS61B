public class HelloNumbers {
	public static void main (String[] args) {
		int x = 1;
		int total = 0;
		while (x < 11) {
			System.out.println(total);
			total += x;
			x += 1;
		}
	}
}