import java.awt.Color;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
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
  
  // determine if the CartPts are equal
  boolean notEqualCartPt(ICartPt given);

}

// to represent a 2-D Cartesian Point
abstract class ACartPt implements ICartPt {
  int x;
  int y;

  ACartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /*
   * TEMPLATE 
   * FIELDS: 
   * ... this.x ... -- int 
   * ... this.y ... -- int
   * 
   * METHODS: 
   * ... this.thisX()... -- int 
   * ... this.thisY()... -- int 
   * ...this.thisDirection()... -- boolean 
   * ... this.equalCartPt() ... -- boolean ...
   * ...this.drawPt()... -- WorldImage 
   * ... this.placePt(WorldScene)... -- WorldScene
   * ... this.move()... -- ICartPt 
   * ... this.changeDir()... --ICartPt ...
   * ...this.CartPtOnScreen()... -- boolean
   * 
   * METHODS ON FIELDS:
   */

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
    /*
     * TEMPLATE FIELDS: 
     * ... this.x ... -- int 
     * ... this.y ... -- int
     * 
     * METHODS: 
     * ... this.thisX()... -- int 
     * ... this.thisY()... -- int 
     * ...this.thisDirection()... -- boolean 
     * ... this.equalCartPt() ... -- boolean ...
     * ...this.drawPt()... -- WorldImage 
     * ... this.placePt(WorldScene)... -- WorldScene
     * ... this.move()... -- ICartPt 
     * ... this.changeDir()... --ICartPt ...
     * ...this.CartPtOnScreen()... -- boolean
     * 
     * METHODS ON PARAMETERS: 
     * ... given.thisX()... -- int 
     * ... given.thisY()... -- int
     * ... given.thisDirection()... -- boolean 
     * ... given.equalCartPt() ... -- boolean
     * ... given.drawPt()... -- WorldImage 
     * ... given.placePt(WorldScene)... -- WorldScene 
     * ... given.move()... -- ICartPt 
     * ... given.changeDir()... --ICartPt 
     * ... given.CartPtOnScreen()... -- boolean
     */
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
  
  // determine if two points are not equal
  public boolean notEqualCartPt(ICartPt given) {
    return this.x != given.thisX() || this.y != given.thisY();
  }
  
  
}

// represents a point for the PlayerShip
class ShipPt extends ACartPt {
  boolean direction;

  ShipPt(int x, int y, boolean direction) {
    super(x, y);
    this.direction = direction;

  }
  /*
   * TEMPLATE FIELDS: 
   * ... this.x ... -- int 
   * ... this.y ... -- int
   * 
   * METHODS: 
   * ... this.thisX()... -- int 
   * ... this.thisY()... -- int 
   * ...this.thisDirection()... -- boolean 
   * ... this.equalCartPt() ... -- boolean ...
   * ...this.drawPt()... -- WorldImage 
   * ... this.placePt(WorldScene)... -- WorldScene
   * ... this.move()... -- ICartPt 
   * ... this.changeDir()... --ICartPt ...
   * ...this.CartPtOnScreen()... -- boolean
   * 
   * METHODS ON PARAMETERS: 
   * ... given.thisX()... -- int 
   * ... given.thisY()... -- int
   * ... given.thisDirection()... -- boolean 
   * ... given.equalCartPt() ... -- boolean
   * ... given.drawPt()... -- WorldImage 
   * ... given.placePt(WorldScene)... -- WorldScene 
   * ... given.move()... -- ICartPt 
   * ... given.changeDir()... --ICartPt 
   * ... given.CartPtOnScreen()... -- boolean
   */

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

}

//represents a player bullet
class PlayerBullet extends ACartPt {
  int radius;
  Color color;

  PlayerBullet(int radius, int x, int y) {
    super(x, y);
    this.radius = radius;
  }

  /*
   * TEMPLATE FIELDS: 
   * ... this.x ... -- int 
   * ... this.y ... -- int
   * 
   * METHODS: 
   * ... this.thisX()... -- int 
   * ... this.thisY()... -- int 
   * ...this.thisDirection()... -- boolean 
   * ... this.equalCartPt() ... -- boolean ...
   * ...this.drawPt()... -- WorldImage 
   * ... this.placePt(WorldScene)... -- WorldScene
   * ... this.move()... -- ICartPt 
   * ... this.changeDir()... --ICartPt ...
   * ...this.CartPtOnScreen()... -- boolean
   * 
   * METHODS ON PARAMETERS: 
   * ... given.thisX()... -- int 
   * ... given.thisY()... -- int
   * ... given.thisDirection()... -- boolean 
   * ... given.equalCartPt() ... -- boolean
   * ... given.drawPt()... -- WorldImage 
   * ... given.placePt(WorldScene)... -- WorldScene 
   * ... given.move()... -- ICartPt 
   * ... given.changeDir()... --ICartPt 
   * ... given.CartPtOnScreen()... -- boolean
   */

  // draw the CartPt
  public WorldImage drawPt() {
    return new CircleImage(5, OutlineMode.SOLID, Color.black);
  }

  // move the CartPt
  public ICartPt move() {
    return new PlayerBullet(this.radius, this.x, this.y - 3);
  }
  
//  public boolean BulletHitInvader(IList<ISpaceShip> invaders) {
//    return invaders.map(new GetCoordinateSpaceShip()).ormap(new SameArea(this));
//  }
  
}

// represents an alien bullet
class AlienBullet extends ACartPt {
  int radius;
  Color color;

  AlienBullet(int radius, int x, int y) {
    super(x, y);
    this.radius = radius;
  }

  /*
   * TEMPLATE FIELDS: 
   * ... this.x ... -- int 
   * ... this.y ... -- int
   * 
   * METHODS: 
   * ... this.thisX()... -- int 
   * ... this.thisY()... -- int 
   * ...this.thisDirection()... -- boolean 
   * ... this.equalCartPt() ... -- boolean ...
   * ...this.drawPt()... -- WorldImage 
   * ... this.placePt(WorldScene)... -- WorldScene
   * ... this.move()... -- ICartPt 
   * ... this.changeDir()... --ICartPt ...
   * ...this.CartPtOnScreen()... -- boolean
   * 
   * METHODS ON PARAMETERS: 
   * ... given.thisX()... -- int 
   * ... given.thisY()... -- int
   * ... given.thisDirection()... -- boolean 
   * ... given.equalCartPt() ... -- boolean
   * ... given.drawPt()... -- WorldImage 
   * ... given.placePt(WorldScene)... -- WorldScene 
   * ... given.move()... -- ICartPt 
   * ... given.changeDir()... --ICartPt 
   * ... given.CartPtOnScreen()... -- boolean
   */

  // draw the CartPt
  public WorldImage drawPt() {
    return new CircleImage(5, OutlineMode.SOLID, Color.red);
  }

  // move the CartPt
  public ICartPt move() {
    return new AlienBullet(this.radius, this.x, this.y + 3);
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
  Color color;

  ASpaceShip(ICartPt coordinate, Color color) {
    this.coordinate = coordinate;
    this.color = color;
  }

  /*
   * TEMPLATE FIELDS: 
   * ...this.coordinate.. --ICartPt 
   * ...this.color... -- color
   * 
   * METHODS: 
   * ...this.drawSpaceShip()... -- WorldImage
   * ...this.placeSpaceShip(WorldScene)... -- WorldScene
   * ...this.moveSpaceShip()... -- ISpaceShip
   * ...this.changeDirectionSpaceShip(boolean)... ISpaceShip 
   * ...this.getX()... -- int
   * ...this.getY()... -- int 
   * ...this.Direction()... -- boolean
   * 
   * METHODS ON FIELDS: 
   * ... coordinate.thisX()... -- int 
   * ... coordinate.thisY()... -- int 
   * ... coordinate.thisDirection()... -- boolean 
   * ...coordinate.equalCartPt() ... -- boolean 
   * ... coordinate.drawPt()... -- WorldImage 
   * ... coordinate.placePt(WorldScene)... -- WorldScene ...
   * ...coordinate.move()... -- ICartPt 
   * ... coordinate.changeDir()... --ICartPt ...
   *...coordinate.CartPtOnScreen()... -- boolean
   */

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
  int width;
  int height;

  PlayerShip(ICartPt coordinate, Color color, int width, int height) {
    super(coordinate, color);
    this.width = width;
    this.height = height;
  }

  /*
   * TEMPLATE FIELDS: 
   * ...this.coordinate.. --ICartPt 
   * ...this.color... -- color
   * 
   * METHODS: 
   * ...this.drawSpaceShip()... -- WorldImage
   * ...this.placeSpaceShip(WorldScene)... -- WorldScene
   * ...this.moveSpaceShip()... -- ISpaceShip
   * ...this.changeDirectionSpaceShip(boolean)... ISpaceShip 
   * ...this.getX()... -- int
   * ...this.getY()... -- int 
   * ...this.Direction()... -- boolean
   * 
   * METHODS ON FIELDS: 
   * ... coordinate.thisX()... -- int 
   * ... coordinate.thisY()... -- int 
   * ... coordinate.thisDirection()... -- boolean 
   * ...coordinate.equalCartPt() ... -- boolean 
   * ... coordinate.drawPt()... -- WorldImage 
   * ... coordinate.placePt(WorldScene)... -- WorldScene ...
   * ...coordinate.move()... -- ICartPt 
   * ... coordinate.changeDir()... --ICartPt ...
   *...coordinate.CartPtOnScreen()... -- boolean
   */

  // draw PlayerShip as Rectangle w/ size + color
  public WorldImage drawSpaceShip() {
    return new RectangleImage(this.width, this.height, OutlineMode.SOLID, Color.black);
  }

  // moves the SpaceShip
  public ISpaceShip moveSpaceShip() {
    return new PlayerShip(this.coordinate.move(), this.color, this.width, this.height);
  }

  // changes the direction of the SpaceShip
  public ISpaceShip changeDirSpaceShip() {
    return new PlayerShip(this.coordinate.changeDir(), this.color, this.width, this.height);
  }

  // gets the direction of the SpaceShip
  public boolean thisDirection() {
    return this.coordinate.thisDirection();
  }
}

// represents an AlienShip
class AlienShip extends ASpaceShip {
  int side;

  AlienShip(ICartPt coordinate, Color color, int side) {
    super(coordinate, color);
    this.side = side;
  }

  /*
   * TEMPLATE FIELDS: 
   * ...this.coordinate.. --ICartPt 
   * ...this.color... -- color
   * 
   * METHODS: 
   * ...this.drawSpaceShip()... -- WorldImage
   * ...this.placeSpaceShip(WorldScene)... -- WorldScene
   * ...this.moveSpaceShip()... -- ISpaceShip
   * ...this.changeDirectionSpaceShip(boolean)... ISpaceShip 
   * ...this.getX()... -- int
   * ...this.getY()... -- int 
   * ...this.Direction()... -- boolean
   * 
   * METHODS ON FIELDS: 
   * ... coordinate.thisX()... -- int 
   * ... coordinate.thisY()... -- int 
   * ... coordinate.thisDirection()... -- boolean 
   * ...coordinate.equalCartPt() ... -- boolean 
   * ... coordinate.drawPt()... -- WorldImage 
   * ... coordinate.placePt(WorldScene)... -- WorldScene ...
   * ...coordinate.move()... -- ICartPt 
   * ... coordinate.changeDir()... --ICartPt ...
   *...coordinate.CartPtOnScreen()... -- boolean
   */

  // draw the AlienShip as Rectangle w/ size + color
  public WorldImage drawSpaceShip() {
    return new RectangleImage(this.side, this.side, OutlineMode.SOLID, Color.red);
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

  SpaceInvaders(ISpaceShip spaceship, IList<ISpaceShip> invaders, IList<ICartPt> playerBullets) {
    this.spaceship = spaceship;
    this.invaders = invaders;
    this.playerBullets = playerBullets;

  }
  /*
   * TEMPLATE FIELDS: 
   * ...this.spaceship... -- ISpaceShip 
   * ...this.invader... -- IList<ISpaceShip> 
   * ...this.playerBullet... -- IList<CartPt>
   * 
   * METHODS: 
   * ...this.makeScene()... -- WorldScene 
   * ...this.onTick()... -- SpaceInvaders 
   * ...this.onKeyEvent()... -- SpaceInvaders
   * 
   * METHODS ON FIELDS: 
   * ...this.spaceship.placeSpaceShip(blank)...
   * ...this.playerBullets.foldr(new addPlayerBullets())...
   * ...this.invader.foldr(new createAllShips(), spaceshipScene))...
   * ...spaceship.moveSpaceShip()... 
   * ...this.invader.foldr(new createAllShips(), spaceshipScene)... 
   * ...spaceship.thisDirection()...
   * ...spaceship.changeDirSpaceShip(false)... 
   * ...playerBullets.filter(new CartPtOffscreen())... 
   * ...playerBullets.add()... 
   * ...playerBullets.count()...
   */

  // makes WorldScene
  public WorldScene makeScene() {
    WorldScene blank = new WorldScene(500, 500);
    WorldScene spaceshipScene = this.spaceship.placeSpaceShip(blank);
    return this.playerBullets.foldr(new AddPlayerBullets(),
        this.invaders.filter(new SpaceShipHit(this.playerBullets))
        .foldr(new CreateAllShips(), spaceshipScene));
  }

  // .map(new GetCoordinateSpaceShip()).filter(new NotSameCartPt()).
  
  // move the Dots on the scene. Adds a new Dot at a random location at every tick
  // of the clock
  public SpaceInvaders onTick() {
    return new SpaceInvaders(spaceship.moveSpaceShip(), invader,
        playerBullets.map(new MoveICartPt()));
  }

    // add a key event to move the player spaceship when the "left" or "right" key
    // is pressed
    public SpaceInvaders onKeyEvent(String key) {
      if (key.equals("left") && spaceship.thisDirection() == true) {
        return new SpaceInvaders(spaceship.changeDirSpaceShip(), invader, playerBullets);
      }
      else if (key.equals("right") && spaceship.thisDirection() == false) {
        return new SpaceInvaders(spaceship.changeDirSpaceShip(), invader, playerBullets);
      }
      else if (key.equals(" ") && playerBullets.filter(new CartPtOffscreen()).count(0) < 3) {
        return new SpaceInvaders(spaceship, invader,
            playerBullets.add(new PlayerBullet(5, this.spaceship.getX(), this.spaceship.getY())));
      }
      else {
        return this;
      }
    }

}