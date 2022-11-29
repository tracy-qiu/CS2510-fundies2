import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

//a class to represent a dot with a radius, color and location
class Dot {
  int radius;
  Color c;
  int x;
  int y;
  Random random;

  Dot(int radius, Color c) {
    Random rand = new Random();
    this.radius = radius;
    this.c = c;
    this.x = rand.nextInt(600);
    this.y = rand.nextInt(400);
  }

  Dot(int radius, Color c, int x, int y) {
    this.radius = radius;
    this.c = c;
    this.x = x;
    this.y = y;
  }

  Dot(int radius, Color c, Random random) {
    this.radius = radius;
    this.c = c;
    this.random = random;
    this.x = random.nextInt(600);
    this.y = random.nextInt(400);
  }

  //draw this Dot as CircleImage with its size and color
  WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.c);
  }

  //create a new dot that is like this Dot but is shifted on the x-axis
  Dot move() {
    return new Dot(this.radius, this.c, this.x + 5, this.y + 3);
  }

  Dot changeColor() {
    return new Dot(this.radius, Color.green, this.x, this.y);
  }

}

//represents a list of Dots
interface ILoDot {
  //draws Dots from this list onto the given scene
  WorldScene draw(WorldScene acc);

  //moves this list of Dots
  ILoDot move();

  ILoDot changeColors();
}

//represents an empty list of Dots
class MtLoDot implements ILoDot {

  //draws Dots from this empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return acc;
  }

  //move the Dots in this empty list
  public ILoDot move() {
    return this;
  }

  public ILoDot changeColors() {
    return this;
  }
}

//represents a non-empty list of Dots
class ConsLoDot implements ILoDot {
  Dot first;
  ILoDot rest;

  ConsLoDot(Dot first, ILoDot rest) {
    this.first = first;
    this.rest = rest;
  }

  //draws dots from this non-empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return this.rest.draw(acc.placeImageXY(this.first.draw(), this.first.x, this.first.y));
  }

  //move the dots in this non-empty list
  public ILoDot move() {
    return new ConsLoDot(this.first.move(), this.rest.move());
  }

  public ILoDot changeColors() {
    return new ConsLoDot(this.first.changeColor(), this.rest.changeColors());
  }
}

//represents a world class to animate a list of Dots on a scene
class DotsWorld extends World {
  ILoDot dots;

  DotsWorld(ILoDot dots) {
    this.dots = dots;
  }

  DotsWorld(ILoDot dots, Random random) {
    this.dots = dots;
  }

  //draws the dots onto the background
  public WorldScene makeScene() {
    return this.dots.draw(new WorldScene(600, 400));
  }

  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
  public World onTick() {
    ILoDot add = new ConsLoDot(new Dot(10, Color.magenta), this.dots);
    return new DotsWorld(add.move());
  }

  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
  public World onTick(Random random) {
    ILoDot add = new ConsLoDot(new Dot(10, Color.magenta, random), this.dots);
    return new DotsWorld(add.move());
  }

  //add a key event to change the colors of all of the existing Dots in this World to green when 
  // the "g" key is pressed
  public DotsWorld onKeyEvent(String key) {
    if (key.equals("g")) {
      return new DotsWorld(dots.changeColors());
    }
    else {
      return this;
    }
  }
}

class ExamplesAnimation {
  Random r1 = new Random(1);
  Random r2 = new Random(2);
  Random r3 = new Random(3);

  Dot d1 = new Dot(10, Color.magenta, r1);
  Dot d2 = new Dot(10, Color.magenta, r2);
  Dot d3 = new Dot(10, Color.magenta, r3);

  Dot d4 = new Dot(10, Color.magenta, 1, 1);
  Dot d5 = new Dot(10, Color.magenta, 2, 2);
  Dot d6 = new Dot(10, Color.magenta, 3, 3);

  // test dots 
  Dot d7 = new Dot(10, Color.magenta, 585, 188);
  Dot d8 = new Dot(10, Color.magenta, 508, 172);
  Dot d9 = new Dot(10, Color.magenta, 134, 60);

  ILoDot mt = new MtLoDot();
  ILoDot lod1 = new ConsLoDot(this.d1, this.mt);
  ILoDot lod2 = new ConsLoDot(this.d2, this.lod1);
  ILoDot lod3 = new ConsLoDot(this.d3, this.lod2);
  ILoDot lod1onTick = new ConsLoDot(new Dot(10, Color.magenta, 215, 184),
      new ConsLoDot(new Dot(10, Color.magenta, 590, 191), this.mt));
  ILoDot lod2onTick = new ConsLoDot(new Dot(10, Color.magenta, 533, 5),
      new ConsLoDot(new Dot(10, Color.magenta, 513, 175),
          new ConsLoDot(new Dot(10, Color.magenta, 590, 191), this.mt)));
  ILoDot lod1onKey = new ConsLoDot(new Dot(10, Color.green, 585, 188), this.mt);
  ILoDot lod2onKey = new ConsLoDot(new Dot(10, Color.green, 508, 172),
      new ConsLoDot(new Dot(10, Color.green, 585, 188), this.mt));

  DotsWorld dw1 = new DotsWorld(lod1);
  DotsWorld dw2 = new DotsWorld(lod2);
  DotsWorld dw3 = new DotsWorld(lod1onTick);
  DotsWorld dw4 = new DotsWorld(lod2onTick);
  DotsWorld dw5 = new DotsWorld(lod1onKey);
  DotsWorld dw6 = new DotsWorld(lod2onKey);

  WorldScene ws = new WorldScene(600, 400);

  WorldScene ws1 = ws.placeImageXY(d7.draw(), 585, 188);
  WorldScene ws2 = ws.placeImageXY(d8.draw(), 508, 172).placeImageXY(d7.draw(), 585, 188);
  WorldScene ws3 = ws.placeImageXY(d9.draw(), 134, 60).placeImageXY(d8.draw(), 508, 172)
      .placeImageXY(d7.draw(), 585, 188);

  //add test for all methods above and any that you add

  boolean testBigBang(Tester t) {
    DotsWorld world = new DotsWorld(this.mt);
    int worldWidth = 600;
    int worldHeight = 400;
    double tickRate = .1;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

  // test dot draw
  boolean testDraw(Tester t) {
    return t.checkExpect(this.d1.draw(), new CircleImage(10, "solid", Color.magenta))
        && t.checkExpect(this.d2.draw(), new CircleImage(10, "solid", Color.magenta));
  }

  // test dot move 
  boolean testMove(Tester t) {
    return t.checkExpect(this.d4.move(), new Dot(10, Color.magenta, 6, 4))
        && t.checkExpect(this.d5.move(), new Dot(10, Color.magenta, 7, 5))
        && t.checkExpect(this.d6.move(), new Dot(10, Color.magenta, 8, 6));
  }

  // test dot change color 
  boolean testChangeColor(Tester t) {
    return t.checkExpect(this.d4.changeColor(), new Dot(10, Color.green, 1, 1))
        && t.checkExpect(this.d5.changeColor(), new Dot(10, Color.green, 2, 2))
        && t.checkExpect(this.d6.changeColor(), new Dot(10, Color.green, 3, 3));

  }

  // test draw list of dots 
  boolean testLoDraw(Tester t) {
    return t.checkExpect(this.lod1.draw(ws), ws1) && t.checkExpect(this.lod2.draw(ws), ws2)
        && t.checkExpect(this.lod3.draw(ws), ws3);
  }

  // test move list of dots
  boolean testLoMove(Tester t) {
    return t.checkExpect(this.lod1.move(),
        new ConsLoDot(new Dot(10, Color.magenta, 590, 191), new MtLoDot()))
        && t.checkExpect(this.lod2.move(), new ConsLoDot(new Dot(10, Color.magenta, 513, 175),
            new ConsLoDot(new Dot(10, Color.magenta, 590, 191), new MtLoDot())));
  }

  // test change colors list of dots 
  boolean testLoChangeColors(Tester t) {
    return t.checkExpect(this.lod1.changeColors(),
        new ConsLoDot(new Dot(10, Color.green, 585, 188), new MtLoDot()))
        && t.checkExpect(this.lod2.changeColors(), new ConsLoDot(new Dot(10, Color.green, 508, 172),
            new ConsLoDot(new Dot(10, Color.green, 585, 188), new MtLoDot())));
  }

  // test make scene for dots world
  boolean testMakeScene(Tester t) {
    return t.checkExpect(this.dw1.makeScene(), ws1) && t.checkExpect(this.dw2.makeScene(), ws2);
  }

  // test on tick for dots world
  boolean testOnTick(Tester t) {
    return t.checkExpect(this.dw1.onTick(r3), dw3) && t.checkExpect(this.dw2.onTick(r3), dw4);
  }

  // test on key event for dots world 
  boolean testonKeyEvent(Tester t) {
    return t.checkExpect(this.dw1.onKeyEvent("g"), dw5)
        && t.checkExpect(this.dw2.onKeyEvent("g"), dw6)
        && t.checkExpect(this.dw2.onKeyEvent("z"), dw2);
  }
}
