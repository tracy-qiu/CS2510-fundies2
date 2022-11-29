import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;

// represents a 2-D Cartesian Point
interface ICartPt {

  // get the x-value of the CartPt
  int thisX();

  // get the y-value of the CartPt
  int thisY();

  // get the direction of the CartPt
  boolean thisDirection();

  // determine if the CartPts are equal
  boolean equalCartPt(ICartPt given);

  // draw the CartPt as a WorldImage
  WorldImage drawPt();

  // place the CartPt on a WorldScene
  WorldScene placePt(WorldScene space);

  // move the CartPt
  ICartPt move();

  // change the direction of the CartPt
  ICartPt changeDir();

  // determine if the CartPt is still on screen
  boolean cartPtOnScreen();

  // create new ICartPt where the hitSpaceship field is true
  ICartPt updateBulletHit();

  // determines if hitSpaceship is true
  boolean hitSpaceship();

  // determines if the given point touches this point
  boolean sameArea(ICartPt given, boolean isPlayer);

}

// to represent a 2-D Cartesian Point
abstract class ACartPt implements ICartPt {
  int x;
  int y;

  ACartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // get the x-coordinate of CartPt
  public int thisX() {
    return this.x;
  }

  // get the y-coordinate of CartPt
  public int thisY() {
    return this.y;
  }

  // return the direction
  public boolean thisDirection() {
    return false;
  }

  // determine if two CartPts are equal
  public boolean equalCartPt(ICartPt given) {
    return this.x == given.thisX() && this.y == given.thisY();
  }

  // draw CartPt as a WorldImage
  public abstract WorldImage drawPt();

  // place the CartPt Image on WorldScene
  public WorldScene placePt(WorldScene space) {
    return space.placeImageXY(this.drawPt(), this.x, this.y);
  }

  // move the CartPt
  public abstract ICartPt move();

  // changes the direction of the CartPt
  public ICartPt changeDir() {
    return this;
  }

  // determines if the CartPt is on screen
  public boolean cartPtOnScreen() {
    return this.y < 500 && this.y > 0;
  }

  // create new ICartPt where the hitSpaceship field is true
  public abstract ICartPt updateBulletHit();

  // determines if hitSpaceship is true
  public boolean hitSpaceship() {
    return false;
  }

  // determines if the given point touches this point
  public boolean sameArea(ICartPt given, boolean isPlayer) {
    // dimensions are halved because adds that distance to both sides of the ICartPt
    // ex (0, 25) (50, 25)
    // (25, 12.5)
    // (0, 0) (50, 0)
    int width = 7;
    int height = 7;
    if (isPlayer) {
      width = 25;
      height = 12;
    }
    return (given.thisX() <= (this.x + width) && given.thisX() >= (this.x - width)
        && given.thisY() <= (this.y + height) && given.thisY() >= (this.y - height));
  }
}

// represents a point for the PlayerShip
class ShipPt extends ACartPt {
  boolean direction;

  ShipPt(int x, int y, boolean direction) {
    super(x, y);
    this.direction = direction;

  }

  // draw the CartPt
  public WorldImage drawPt() {
    return new EmptyImage();
  }

  // move the CartPt along the x-axis
  public ICartPt move() {
    if (x < 28 && !this.direction) {
      return this;
    }
    else if (x > 472 && this.direction) {
      return this;
    }
    else {
      if (this.direction) {
        return new ShipPt(this.x + 2, this.y, this.direction);
      }
      else {
        return new ShipPt(this.x - 2, this.y, this.direction);
      }
    }
  }

  @Override
  // change the direction of the CartPt
  public ICartPt changeDir() {
    return new ShipPt(this.x, this.y, !this.direction);
  }

  // return the direction of the CartPt
  public boolean thisDirection() {
    return this.direction;
  }

  @Override
  // object was hit, dont change anything because not a bullet
  public ICartPt updateBulletHit() {
    return this;
  }

}

//represents a player bullet
class PlayerBullet extends ACartPt {
  boolean hitSpaceship;

  PlayerBullet(int x, int y, boolean hitSpaceship) {
    super(x, y);
    this.hitSpaceship = hitSpaceship;
  }

  // draw the CartPt
  public WorldImage drawPt() {
    return new CircleImage(5, OutlineMode.SOLID, Color.black);
  }

  // move the CartPt
  public ICartPt move() {
    return new PlayerBullet(this.x, this.y - 3, this.hitSpaceship);
  }

  // public boolean BulletHitInvader(IList<ISpaceShip> invaders) {
  // return invaders.map(new GetCoordinateSpaceShip()).ormap(new SameArea(this));
  // }

  @Override
  // object was hit, change hitSpaceship field to true
  public ICartPt updateBulletHit() {
    return new PlayerBullet(this.x, this.y, true);
  }

  // determines if hitSpaceship is true
  public boolean hitSpaceship() {
    return this.hitSpaceship;
  }
}

// represents an alien bullet
class AlienBullet extends ACartPt {
  boolean hitSpaceship;

  AlienBullet(int x, int y, boolean hitSpaceship) {
    super(x, y);
    this.hitSpaceship = hitSpaceship;
  }

  // draw the CartPt
  public WorldImage drawPt() {
    return new CircleImage(5, OutlineMode.SOLID, Color.red);
  }

  // move the CartPt
  public ICartPt move() {
    return new AlienBullet(this.x, this.y + 3, this.hitSpaceship);
  }

  @Override
  // object was hit, change hitSpaceship field to true
  public ICartPt updateBulletHit() {
    return new AlienBullet(this.x, this.y, true);
  }

  // determines if hitSpaceship is true
  public boolean hitSpaceship() {
    return this.hitSpaceship;
  }
}

// to represent a SpaceShip in SpaceInvaders
interface ISpaceShip {

  // draws SpaceShip
  WorldImage drawSpaceShip();

  // place SpaceShip in WorldScene
  WorldScene placeSpaceShip(WorldScene space);

  // draws Spaceship after being moved
  ISpaceShip moveSpaceShip();

  // draws Spaceship after changing direction
  ISpaceShip changeDirSpaceShip();

  // get the x-coordinate
  int getX();

  // get the y-coordinate
  int getY();

  // get the direction
  boolean thisDirection();

  // get coordinate
  ICartPt getCoordinate();
}

// abstract SpaceShip class
abstract class ASpaceShip implements ISpaceShip {
  ICartPt coordinate;

  ASpaceShip(ICartPt coordinate) {
    this.coordinate = coordinate;
  }

  // draws the Spaceship
  public abstract WorldImage drawSpaceShip();

  // places the SpaceShip on the WorldScene
  public WorldScene placeSpaceShip(WorldScene space) {
    return space.placeImageXY(this.drawSpaceShip(), this.coordinate.thisX(),
        this.coordinate.thisY());
  }

  // moves the SpaceShip
  public abstract ISpaceShip moveSpaceShip();

  // draws Spaceship after changing direction
  public abstract ISpaceShip changeDirSpaceShip();

  // get the x-coordinate of SpaceShip
  public int getX() {
    return this.coordinate.thisX();
  }

  // get the y-coordinate of the SpaceShip
  public int getY() {
    return this.coordinate.thisY();
  }

  // get the direction of the SpaceShip
  public boolean thisDirection() {
    return false;
  }

  // get coordinate of spaceship
  public ICartPt getCoordinate() {
    return this.coordinate;
  }
}

// represents a PlayerShip
class PlayerShip extends ASpaceShip {
  Color color;
  int width;
  int height;

  PlayerShip(ICartPt coordinate) {
    super(coordinate);
  }

  // draw PlayerShip as Rectangle w/ size + color
  public WorldImage drawSpaceShip() {
    return new RectangleImage(50, 25, OutlineMode.SOLID, Color.black);
  }

  // moves the SpaceShip
  public ISpaceShip moveSpaceShip() {
    return new PlayerShip(this.coordinate.move());
  }

  // changes the direction of the SpaceShip
  public ISpaceShip changeDirSpaceShip() {
    return new PlayerShip(this.coordinate.changeDir());
  }

  // gets the direction of the SpaceShip
  public boolean thisDirection() {
    return this.coordinate.thisDirection();
  }
}

// represents an AlienShip
class AlienShip extends ASpaceShip {

  AlienShip(ICartPt coordinate) {
    super(coordinate);
  }

  // draw the AlienShip as Rectangle w/ size + color
  public WorldImage drawSpaceShip() {
    return new RectangleImage(15, 15, OutlineMode.SOLID, Color.red);
  }

  // move the SpaceShip
  public ISpaceShip moveSpaceShip() {
    return this;
  }

  // change the direction of the SpaceShip
  public ISpaceShip changeDirSpaceShip() {
    return this;
  }
}

// represents the World
class SpaceInvaders extends World {
  ISpaceShip spaceship;
  IList<ISpaceShip> invaders;
  IList<ICartPt> playerBullets;
  IList<ICartPt> invaderBullets;
  Random rand;

  SpaceInvaders(ISpaceShip spaceship, IList<ISpaceShip> invaders, IList<ICartPt> playerBullets,
      IList<ICartPt> invaderBullets) {
    Random random = new Random();
    this.spaceship = spaceship;
    this.invaders = invaders;
    this.playerBullets = playerBullets;
    this.invaderBullets = invaderBullets;
    this.rand = random;

  }

  SpaceInvaders(ISpaceShip spaceship, IList<ISpaceShip> invaders, IList<ICartPt> playerBullets,
      IList<ICartPt> invaderBullets, Random random) {
    this.spaceship = spaceship;
    this.invaders = invaders;
    this.playerBullets = playerBullets;
    this.invaderBullets = invaderBullets;
    this.rand = random;

  }

  // makes WorldScene
  public WorldScene makeScene() {
    WorldScene blank = new WorldScene(500, 500);
    WorldScene withSpaceship = this.spaceship.placeSpaceShip(blank);
    WorldScene withInvaders = this.invaders.foldr(new CreateAllShips(), withSpaceship);
    WorldScene withPlayerBullets = this.playerBullets.foldr(new AddBullets(), withInvaders);
    WorldScene withInvaderBullets = this.invaderBullets.foldr(new AddBullets(), withPlayerBullets);
    return withInvaderBullets;
  }

  // creates the final scene of game
  public WorldScene makeAFinalScene() {
    WorldScene blank = new WorldScene(500, 500);
    return blank;
  }

  // move the Dots on the scene. Adds a new Dot at a random location at every tick
  // of the clock
  public SpaceInvaders onTick() {
    int invaderCount = invaders.count(0);
    ISpaceShip randomInvader;
    IList<ICartPt> updatedInvaderBullets = new MtList<ICartPt>();
    IList<ICartPt> updatedPlayerBullets = new MtList<ICartPt>();
    IList<ISpaceShip> updatedInvaders = new MtList<ISpaceShip>();

    updatedInvaders = invaders.filter(new SpaceShipHit(this.playerBullets));
    updatedInvaderBullets = invaderBullets.filter(new CartPtOffscreen());
    updatedPlayerBullets = playerBullets.map(new UpdateHitObject(invaders)).filter(new BulletHit());
    if (updatedInvaderBullets.count(0) < 10) {
      randomInvader = invaders.produceBullet(rand.nextInt(invaderCount), 0);
      updatedInvaderBullets = invaderBullets
          .add(new AlienBullet(randomInvader.getX(), randomInvader.getY(), false));
    }
    return new SpaceInvaders(spaceship.moveSpaceShip(), updatedInvaders,
        updatedPlayerBullets.map(new MoveICartPt()), updatedInvaderBullets.map(new MoveICartPt()));
  }

  // add a key event to move the player spaceship when the "left" or "right" key
  // is pressed
  public SpaceInvaders onKeyEvent(String key) {
    if (key.equals("left") && spaceship.thisDirection()) {
      return new SpaceInvaders(spaceship.changeDirSpaceShip(), invaders, playerBullets,
          invaderBullets);
    }
    else if (key.equals("right") && !spaceship.thisDirection()) {
      return new SpaceInvaders(spaceship.changeDirSpaceShip(), invaders, playerBullets,
          invaderBullets);
    }
    else if (key.equals(" ") && playerBullets.filter(new CartPtOffscreen()).count(0) < 3) {
      return new SpaceInvaders(spaceship, invaders,
          playerBullets.add(new PlayerBullet(this.spaceship.getX(), this.spaceship.getY(), false)),
          invaderBullets);
    }
    else {
      return this;
    }
  }

  // ends the game when certain conditions are met
  public WorldEnd worldEnds() {
    if ((invaderBullets.ormap(new SameArea(spaceship))) || (invaders.isEmpty())) {
      return new WorldEnd(true, this.makeAFinalScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
}