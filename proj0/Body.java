public class Body {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b2) {
		double dxSq = Math.pow(this.xxPos - b2.xxPos, 2);
		double dySq = Math.pow(this.yyPos - b2.yyPos, 2);
		double r = Math.pow(dxSq + dySq, .5);
		return r;
	}

	public double calcForceExertedBy(Body b2) {
		double g = 6.67e-11;
		double f = 0;
		double rSq = Math.pow(calcDistance(b2), 2);
		if (this.equals(b2)) {
			return 0;
		} else {
			f = (g * this.mass * b2.mass) / rSq;
		}
		return f;
	}

	public double calcForceExertedByX(Body b2) {
		double f = calcForceExertedBy(b2);
		double dx = b2.xxPos - this.xxPos;
		double r = calcDistance(b2);

		return f * dx / r;
	}

	public double calcForceExertedByY(Body b2) {
		double f = calcForceExertedBy(b2);
		double dy = b2.yyPos - this.yyPos;
		double r = calcDistance(b2);

		return f * dy / r;
	}

	public double calcNetForceExertedByX(Body[] arr) {
		double total = 0;
		for (Body b : arr) {
			if (!b.equals(this)) {
				total += calcForceExertedByX(b);
			}
		}
		return total;
	}

	public double calcNetForceExertedByY(Body[] arr) {
		double total = 0;
		for (Body b : arr) {
			if (!b.equals(this)) {
				total += calcForceExertedByY(b);
			}
		}
		return total;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX/this.mass;
		double aY = fY/this.mass;

		this.xxVel += dt * aX;
		this.yyVel += dt * aY;

		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}