public class NBody {
	public static double readRadius(String file) {
		In in = new In(file);
		int planets = in.readInt();
		double radius = in.readDouble();

		return radius;
	}

	public static Body[] readBodies(String file) {
		In in = new In(file);
		int n = in.readInt();
		double radius = in.readDouble();

		Body[] arr = new Body[n];

		for(int i = 0; i < arr.length; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();

			Body b = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			arr[i] = b;
		}
		return arr;
	}

	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		double radius = readRadius(fileName);
		Body[] bodies = readBodies(fileName);

		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");

		for(Body b : bodies) {
			b.draw();
		}

		StdDraw.enableDoubleBuffering();

		for(int time = 0; time <= T; time += dt) {
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];

			for(int i = 0; i < bodies.length; i++) {
				double x = bodies[i].calcNetForceExertedByX(bodies);
				double y = bodies[i].calcNetForceExertedByY(bodies);
				xForces[i] = x;
				yForces[i] = y;
			}

			for(int i = 0; i < bodies.length; i++) {
				bodies[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.picture(0, 0, "images/starfield.jpg");
			
			for(Body b: bodies) {
				b.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}