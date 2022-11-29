//import java.awt.Color;
//
//import javalib.funworld.World;
//import javalib.funworld.WorldScene;
//import javalib.worldimages.CircleImage;
//import javalib.worldimages.EmptyImage;
//import javalib.worldimages.OutlineMode;
//import javalib.worldimages.RectangleImage;
//import javalib.worldimages.WorldImage;
//
//interface ICartPt {
//  int thisX();
//
//  int thisY();
//
//  boolean equalCartPt(ICartPt given);
//
//  WorldImage drawPt();
//
//  ICartPt move();
//
//  ICartPt changeDir();
//
//}
//
//// to represent a 2-d Cartesian Point
//abstract class ACartPt implements ICartPt {
//  int x;
//  int y;
//
//  /*
//   * direction 
//   * true - moves right or moves up (from bottom to top)
//   * false - moves left or moves down (from top to bottom)
//   */
//
//  ACartPt(int x, int y) {
//    this.x = x;
//    this.y = y;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.x ...                        -- int
//  ... this.y ...                        -- int
//  ... this.direction ...                -- boolean
//  
//  Methods:
//  ... this.equalCartPt() ...            -- boolean 
//  */
//
//  public int thisX() {
//    return this.x;
//  }
//
//  public int thisY() {
//    return this.y;
//  }
//
//  public boolean equalCartPt(ICartPt given) {
//    return this.x == given.thisX() && this.y == given.thisY();
//  }
//
//  public abstract WorldImage drawPt();
//
//  public abstract ICartPt move();
//
//  public ICartPt changeDir() {
//    return this;
//  }
//
//}
//
//class ShipPt extends ACartPt {
//  boolean direction;
//
//  ShipPt(int x, int y, boolean direction) {
//    super(x, y);
//    this.direction = direction;
//    //    this.radius = radius; 
//    //    this.color = color; 
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.radius ...                -- int
//  ... this.color ...                 -- Color 
//  ... this.x ...                     -- int 
//  ... this.y ...                     -- int 
//  Methods:
//  ... this.drawBullet()               -- WorldImage
//  */
//
//  public WorldImage drawPt() {
//    return new EmptyImage();
//  }
//
//  //create a new dot that is like this Dot but is shifted on the x-axis
//  public ICartPt move() {
//    if (x < 28 && !this.direction) {
//      return this;
//    }
//    else if (x > 472 && this.direction) {
//      return this;
//    }
//    else {
//      if (this.direction) {
//        return new ShipPt(this.x + 2, this.y, this.direction);
//      }
//      else {
//        return new ShipPt(this.x - 2, this.y, this.direction);
//      }
//    }
//  }
//
//  @Override
//  public ICartPt changeDir() {
//    return new ShipPt(this.x, this.y, !this.direction);
//  }
//}
//
////represents a player bullet
//class PlayerBullet extends ACartPt {
//  int radius;
//  Color color;
//
//  PlayerBullet(int radius, int x, int y) {
//    super(x, y);
//    this.radius = radius;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.radius ...                -- int
//  ... this.color ...                 -- Color 
//  ... this.x ...                     -- int 
//  ... this.y ...                     -- int 
//  ... this.direction ...             -- boolean 
//  
//  */
//
//  public WorldImage drawPt() {
//    return new CircleImage(1, OutlineMode.SOLID, Color.black);
//  }
//
//  //create a new dot that is like this Dot but is shifted on the x-axis
//  public ICartPt move() {
//    return new PlayerBullet(this.radius, this.x, this.y + 3);
//  }
//
//}
//
//// represents an alien bullet
//class AlienBullet extends ACartPt {
//  int radius;
//  Color color;
//
//  AlienBullet(int radius, int x, int y) {
//    super(x, y);
//    this.radius = radius;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.radius ...                -- int
//  ... this.color ...                 -- Color 
//  ... this.x ...                     -- int 
//  ... this.y ...                     -- int 
//  ... this.direction ...             -- boolean 
//  
//  */
//
//  public WorldImage drawPt() {
//    return new CircleImage(1, OutlineMode.SOLID, Color.red);
//  }
//
//  //create a new dot that is like this Dot but is shifted on the x-axis
//  public ICartPt move() {
//    return new AlienBullet(this.radius, this.x, this.y - 3);
//  }
//
//}
//
//// to represent a SpaceShip in SpaceInvaders
//interface ISpaceShip {
//
//  // draws SpaceShip
//  WorldImage drawSpaceShip();
//
//  // place SpaceShip in WorldScene
//  WorldScene placeSpaceShip(WorldScene space);
//
//  // draws Spaceship after being moved 
//  ISpaceShip moveSpaceShip();
//
//  // draws Spaceship after changing direction 
//  ISpaceShip changeDirSpaceShip(boolean direction);
//}
//
//// abstract SpaceShip classes
//abstract class ASpaceShip implements ISpaceShip {
//  ICartPt coordinate;
//  Color color;
//
//  ASpaceShip(ICartPt coordinate, Color color) {
//    this.coordinate = coordinate;
//    this.color = color;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.coordinate ...                   -- CartPt
//  ... this.color ...                        -- Color
//  Methods:
//  ... this.drawSpaceShip() ...               -- WorldImage 
//  ... this.placeSpaceShip(WorldScene) ...    -- WorldScene
//  Methods for fields:
//  ... this.coordinate.equalCartPt(CartPt) ...    -- boolean
//  */
//
//  // draws the Spaceship
//  public abstract WorldImage drawSpaceShip();
//
//  // places the SpaceShip on the WorldScene
//  public WorldScene placeSpaceShip(WorldScene space) {
//    return space.placeImageXY(this.drawSpaceShip(), this.coordinate.thisX(),
//        this.coordinate.thisY());
//  }
//
//  public abstract ISpaceShip moveSpaceShip();
//
//  //draws Spaceship after changing direction 
//  public abstract ISpaceShip changeDirSpaceShip(boolean directiom);
//}
//
//// represents a PlayerShip
//class PlayerShip extends ASpaceShip {
//  int width;
//  int height;
//
//  PlayerShip(ICartPt coordinate, Color color, int width, int height) {
//    super(coordinate, color);
//    this.width = width;
//    this.height = height;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.coordinate ...              -- CartPt
//  ... this.color ...                   -- Color
//  ... this.width ...                   -- int
//  ... this.height ...                  -- int 
//  Methods:
//  ... this.drawSpaceShip() ...           -- WorldImage 
//  Methods for fields:
//  ... this.coordinate.equalCartPt(CartPt) ...    -- boolean
//  */
//
//  // draw PlayerShip as Rectangle w/ size + color
//  public WorldImage drawSpaceShip() {
//    return new RectangleImage(this.width, this.height, OutlineMode.SOLID, Color.black);
//  }
//
//  public ISpaceShip moveSpaceShip() {
//    return new PlayerShip(this.coordinate.move(), this.color, this.width, this.height);
//  }
//
//  public ISpaceShip changeDirSpaceShip(boolean direction) {
//    return new PlayerShip(this.coordinate.changeDir(), this.color, this.width, this.height);
//  }
//}
//
//// represents an AlienShip
//class AlienShip extends ASpaceShip {
//  int side;
//
//  AlienShip(ICartPt coordinate, Color color, int side) {
//    super(coordinate, color);
//    this.side = side;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.coordinate ...              -- CartPt
//  ... this.color ...                   -- Color
//  ... this.side ...                    -- int
//  Methods:
//  ... this.drawSpaceShip() ...           -- WorldImage 
//  Methods for fields:
//  ... this.coordinate.equalCartPt(CartPt) ...    -- boolean
//  */
//
//  // draw the AlienShip as Rectangle w/ size + color
//  public WorldImage drawSpaceShip() {
//    return new RectangleImage(this.side, this.side, OutlineMode.SOLID, Color.red);
//  }
//
//  public ISpaceShip moveSpaceShip() {
//    return this;
//  }
//
//  public ISpaceShip changeDirSpaceShip(boolean direction) {
//    return this;
//  }
//}
//
//// represents the World
//class SpaceInvaders extends World {
//  ISpaceShip spaceship;
//  IList<ISpaceShip> invader;
//
//  SpaceInvaders(ISpaceShip spaceship, IList<ISpaceShip> invader) {
//    this.spaceship = spaceship;
//    this.invader = invader;
//
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.radius ...                -- int
//  ... this.color ...                 -- Color 
//  ... this.x ...                     -- int 
//  ... this.y ...                     -- int 
//  ... this.direction ...             -- boolean 
//  Methods: 
//  ... this.makeScene() ...           -- WorldScene 
//  */
//
//  // makes WorldScene
//  public WorldScene makeScene() {
//    WorldScene blank = new WorldScene(500, 500);
//    WorldScene spaceshipScene = this.spaceship.placeSpaceShip(blank);
//    return this.invader.foldr(new createAllShips(), spaceshipScene);
//  }
//
//  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
//  public SpaceInvaders onTick() {
//    return new SpaceInvaders(spaceship.moveSpaceShip(), invader);
//  }
//
//  //  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
//  //  public World onTick(Random random) {
//  //    ILoDot add = new ConsLoDot(new Dot(10, Color.magenta, random), this.dots);
//  //    return new DotsWorld(add.move());
//  //  }
//
//  //  add a key event to move the player spaceship when the "left" or "right" key is pressed
//  public SpaceInvaders onKeyEvent(String key) {
//    if (key.equals("left")) {
//      return new SpaceInvaders(spaceship.changeDirSpaceShip(false), invader);
//    }
//    else if (key.equals("right")) {
//      return new SpaceInvaders(spaceship.changeDirSpaceShip(true), invader);
//    }
//    else {
//      return this;
//    }
//  }
//}
