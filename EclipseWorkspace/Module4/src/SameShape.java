import tester.Tester;

interface IShape {
  boolean sameShape(IShape other);

  boolean sameCircle(Circle other);

  boolean sameRect(Rect other);

  boolean sameSquare(Square other);

  // is this shape a Circle?
  boolean isCircle();

  // is this shape a Rect?
  boolean isRect();

  // is this shape a Square?
  boolean isSquare();
}

class Circle implements IShape {
  int x, y;
  int radius;

  Circle(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }

  public boolean sameCircle(Circle other) {
    /* Template:
     * Fields:
     * this.x, this.y, this.radius
     *
     * Fields of parameters:
     * other.x, other.y, other.radius
     */
    return this.x == other.x && this.y == other.y && this.radius == other.radius;
  }

  public boolean sameRect(Rect otherRect) {
    return false;
  }

  public boolean sameSquare(Square otherSquare) {
    return false;
  }

  public boolean sameShape(IShape other) {
    return other.sameCircle(this);
    //    if (this.isCircle()) {
    //      // other is-a Circle -- we can safely cast!
    //      return this.sameCircle((Circle) other);
    //    }
    //    else {
    //      // other is not a Circle
    //      return false;
    //    }
  }

  public boolean isCircle() {
    return true;
  }

  public boolean isRect() {
    return false;
  }

  public boolean isSquare() {
    return false;
  }
}

class Rect implements IShape {
  int x, y;
  int w, h;

  Rect(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public boolean sameRect(Rect other) {
    /* Template:
     * Fields:
     * this.x, this.y, this.w, this.h
     *
     * Fields of parameters:
     * other.x, other.y, other.w, other.h
     */
    return this.x == other.x && this.y == other.y && this.w == other.w && this.h == other.h;
  }

  public boolean sameCircle(Circle otherCircle) {
    return false;
  }

  public boolean sameSquare(Square otherSquare) {
    return false;
  }

  public boolean sameShape(IShape other) {
    if (other.isRect()) {
      // other is-a Rectangle -- we can safely cast!
      return this.sameRect((Rect) other);
    }
    else {
      // other is not a Rectangle
      return false;
    }
  }

  public boolean isCircle() {
    return false;
  }

  public boolean isRect() {
    return true;
  }

  public boolean isSquare() {
    return false;
  }
}

class Square extends Rect {
  Square(int x, int y, int s) {
    super(x, y, s, s);
  }

  public boolean sameShape(IShape other) {
    if (other.isSquare()) {
      return this.sameSquare((Square) other);
    }
    else {
      return false;
    }
  }

  public boolean sameSquare(Square other) {
    return this.x == other.x && this.y == other.y && this.w == other.w; // No need to check the h field, too...
  }

  public boolean isCircle() {
    return false;
  }

  public boolean isRect() {
    return false;
  }

  public boolean isSquare() {
    return true;
  }
}

//In test method in an Examples class
class ExamplesSameShapes {
  ExamplesSameShapes() {
  }

  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);
  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);
  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 6);
  Square s3 = new Square(3, 4, 5);

  boolean testSameCircle(Tester t) {
    return t.checkExpect(c1.sameCircle(c2), false) && t.checkExpect(c2.sameCircle(c1), false)
        && t.checkExpect(c1.sameCircle(c3), true) && t.checkExpect(c3.sameCircle(c1), true);
  }

  boolean testSameRect(Tester t) {
    return t.checkExpect(r1.sameRect(r2), false) && t.checkExpect(r2.sameRect(r1), false)
        && t.checkExpect(r1.sameRect(r3), true) && t.checkExpect(r3.sameRect(r1), true);
  }

  boolean testSameSquare(Tester t) {
    return t.checkExpect(s1.sameShape(s2), false) && t.checkExpect(s2.sameShape(s1), false)
        && t.checkExpect(s1.sameShape(s3), true) && t.checkExpect(s3.sameShape(s1), true)
        // Comparing a Square with a Rect of a different size
        && t.checkExpect(s1.sameShape(r2), false) // Good
        && t.checkExpect(r2.sameShape(s1), false) // Good
        // Comparing a Square with a Rect of the same size
        && t.checkExpect(s1.sameShape(r1), false) // Good
        && t.checkExpect(r1.sameShape(s1), false); // Not so good
  }
}