import java.awt.Color;
import java.util.function.Predicate;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import tester.Tester;

// represents Predicates
interface IPredicate<T> {

  // determine if function on type T is true
  boolean apply(T t);
}

// determines if points in list are on screen 
class CartPtOffscreen implements IPredicate<ICartPt> {
  /*
   * TEMPLATE FIELDS:
   * 
   * METHODS: ...this.apply(ICartPt) -- boolean
   * 
   * METHODS ON FIELDS:
   */
  // determine if function on type T is true
  public boolean apply(ICartPt given) {
    /*
     * TEMPLATE FIELDS:
     * 
     * METHODS: ...this.apply(ICartPt) -- boolean
     * 
     * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
     * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
     * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
     * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
     * ... this.CartPtOnScreen()... -- boolean
     */
    return given.cartPtOnScreen();
  }
}

class NotSameCartPt implements IPredicate<ICartPt> {
  ICartPt point;

  NotSameCartPt(ICartPt point) {
    this.point = point;
  }

  public boolean apply(ICartPt given) {
    return point.notEqualCartPt(given);
  }
}

// predicate to determine if two CartPts are the same
class SameCartPt implements IPredicate<ICartPt> {
  ICartPt point;

  SameCartPt(ICartPt point) {
    this.point = point;
  }

  /*
   * TEMPLATE FIELDS: ...this.point... -- ICartPt
   * 
   * METHODS: ...this.apply(ICartPt) -- boolean
   * 
   * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
   * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
   * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
   * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
   * ... this.CartPtOnScreen()... -- boolean
   */

  // determine if given point is equal to this point
  public boolean apply(ICartPt given) {
    /*
     * TEMPLATE FIELDS: ...this.point... -- ICartPt
     * 
     * METHODS: ...this.apply(ICartPt) -- boolean
     * 
     * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
     * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
     * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
     * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
     * ... this.CartPtOnScreen()... -- boolean
     */
    return point.equalCartPt(given);
  }
}

// determines if a CartPt is contained in list
class CartPtContainedIn implements IPredicate<ICartPt> {
  IList<ICartPt> points;

  CartPtContainedIn(IList<ICartPt> points) {
    this.points = points;
  }

  /*
   * TEMPLATE FIELDS: ...this.point... -- ICartPt
   * 
   * METHODS: ...this.apply(ICartPt) -- boolean
   * 
   * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
   * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
   * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
   * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
   * ... this.CartPtOnScreen()... -- boolean
   */

  // determine if the given point is contained in this list of points
  public boolean apply(ICartPt t) {
    /*
     * TEMPLATE FIELDS: ...this.point... -- ICartPt
     * 
     * METHODS: ...this.apply(ICartPt) -- boolean
     * 
     * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
     * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
     * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
     * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
     * ... this.CartPtOnScreen()... -- boolean
     */
    return points.ormap(new SameCartPt(t));
  }
}

class AlienShipHit implements IPredicate<ICartPt>{
  IList<ICartPt> invaders;
  
  AlienShipHit(IList<ICartPt> invaders){
    this.invaders = invaders;
  }
  
  public boolean apply(ICartPt t) {
    return invaders.ormap(new SameArea(t));
  }
}

class SameArea implements IPredicate<ICartPt>{
  ICartPt point;
  
  SameArea(ICartPt point){
    this.point = point;
  }
  
  public boolean apply(ICartPt given) {
    return given.thisX() < (this.point.thisX() + 10) && 
        given.thisX() > (this.point.thisX() - 10) &&
        given.thisY() < (this.point.thisY() + 10) && 
        given.thisY() > (this.point.thisY() - 10);
  }
}


////// USE THIS PREDICATE,, pass in on a list of spaceship
class SpaceShipHit implements Predicate<ISpaceShip>{
  IList<ICartPt> playerBullets;
  
  SpaceShipHit(IList<ICartPt> playerBullets){
    this.playerBullets = playerBullets;
  }
  

  public boolean test(ISpaceShip t) {
    return playerBullets.ormap(new SameArea(t.getCoordinate()));
  }
}








// function that moves all CartPt in a list
class MoveICartPt implements IFunction<ICartPt, ICartPt> {

  /*
   * TEMPLATE FIELDS: ...this.point... -- ICartPt
   * 
   * METHODS: ...this.apply(ICartPt) -- boolean
   * 
   * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
   * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
   * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
   * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
   * ... this.CartPtOnScreen()... -- boolean
   */

  // determine if the given point is contained in this list of points
  public ICartPt apply(ICartPt point) {
    /*
     * TEMPLATE FIELDS: ...this.point... -- ICartPt
     * 
     * METHODS: ...this.apply(ICartPt) -- boolean
     * 
     * METHODS ON PARAMETERS: ... this.thisX()... -- int ... this.thisY()... -- int
     * ... this.thisDirection()... -- boolean ... this.equalCartPt() ... -- boolean
     * ... this.drawPt()... -- WorldImage ... this.placePt(WorldScene)... --
     * WorldScene ... this.move()... -- ICartPt ... this.changeDir()... --ICartPt
     * ... this.CartPtOnScreen()... -- boolean
     */
    return point.move();
  }
}

class GetCoordinateSpaceShip implements IFunction<ISpaceShip, ICartPt> {
  public ICartPt apply(ISpaceShip ship) {
    return ship.getCoordinate();
  }

}

//class PointToShip implements IFunction<ICartPt, ISpaceShip>{
//  public ICartPt apply(ICartPt point) {
//    return (ICartPt) new ISpaceShip(point, 15);
//  }
//}






//---------------------------------------------------------

// interface for MAP
interface IFunction<A, R> {
  R apply(A t);
}

// interface for FOLDR
interface BiFunction<A1, A2, R> {
  R apply(A1 a1, A2 a2);
}

// creates WorldScene with all the SpaceShips
class CreateAllShips implements BiFunction<ISpaceShip, WorldScene, WorldScene> {

  /*
   * TEMPLATE METHODS: ... this.apply(ISpaceShip, WorldScene) ... -- WorldScene
   */
  // places all CartPts on WorldScene
  public WorldScene apply(ISpaceShip ship, WorldScene space) {
    return ship.placeSpaceShip(space);
  }
}

//adds bullets to WorldScene
class AddPlayerBullets implements BiFunction<ICartPt, WorldScene, WorldScene> {
  /*
   * TEMPLATE METHODS: ... this.apply(ISpaceShip, WorldScene) ... -- WorldScene
   */

  // places bullets on WorldScene
  public WorldScene apply(ICartPt bullet, WorldScene space) {
    return bullet.placePt(space);
  }
}

// represents a generic list
// functions from lecture/lab (omitted from tests unless used in code)
interface IList<T> {

  // MAP FUNCTION
  <R> IList<R> map(IFunction<T, R> function);

  boolean BulletHitInvader(IList<ISpaceShip> invaders);

  // FOLDR FUNCTION
  <R> R foldr(BiFunction<T, R, R> function, R base);

  // ORMAP FUNCTION
  boolean ormap(IPredicate<T> predicate);

  // ANDMAP FUNCTION
  boolean andmap(IPredicate<T> predicate);

  // FILTER FUNCTION
  IList<T> filter(IPredicate<T> predicate);

  // add function for lists
  IList<T> add(T item);

  // count function for lists
  int count(int count);

}

// generic empty list
class MtList<T> implements IList<T> {

  /*
   * TEMPLATE Methods: ... this.map(IFunction<T, R>) ... -- IList<R>
   * ...this.foldr(BiFunction<T, R, R>, R) ... -- IList<R> ...
   * ...this.ormap(IPredicate<T>) ... -- boolean ... this.andmap(IPredicate<T,
   * R>)... -- boolean ... this.filter(IPredicate<T>) ... -- IList<T> ...
   * this.add()... -- IList<T> ... this.count()... -- int
   */

  // MAP FUNCTION: Empty-List
  public <R> IList<R> map(IFunction<T, R> function) {
    return new MtList<R>();
  }

  // FOLDR FUNCTION: Empty-List
  public <R> R foldr(BiFunction<T, R, R> function, R base) {
    return base;
  }

  // ORMAP: Empty-List
  public boolean ormap(IPredicate<T> predicate) {
    return false;
  }

  // ANDMAP: Empty-List
  public boolean andmap(IPredicate<T> predicate) {
    return true;
  }

  // FILTER: Empty-List
  public IList<T> filter(IPredicate<T> predicate) {
    return new MtList<T>();
  }

  // add item to the list
  public IList<T> add(T item) {
    return new ConsList<T>(item, new MtList<T>());
  }

  // count number of items in the list
  public int count(int count) {
    return count;
  }

}

// generic non-empty list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE Fields: ... this.first ... -- T ... this.rest ... -- IList<T>
   * 
   * Methods: ... this.map(IFunction<T, R>) ... -- IList<R> ... ...
   * this.foldr(BiFunction<T, R, R>, R) ... -- IList<R> ... ...
   * this.ormap(IPredicate<T>) ... -- boolean ... this.andmap(IPredicate<T, R>) --
   * boolean ... this.filter(IPredicate<T>) ... -- IList<T> ... ... this.add()...
   * -- IList<T> ... this.count()... -- int
   * 
   * Methods for fields: ... ...this.rest.map(IFunction<T, R>) ... -- IList<R> ...
   * ...this.rest.foldr(BiFunction<T, R, R>, R) ... -- IList<R> ...
   * ...this.rest.ormap(IPredicate<T>) ... -- boolean ...
   * ...this.rest.andmap(IPredicate<T, R>) ... -- boolean ...
   * ...this.rest.filter(IPredicate<T>) ... -- IList<T> ... this.rest.add()... --
   * IList<T> ... this.rest.count()... -- int
   */

  // MAP Function: ConsList
  public <R> IList<R> map(IFunction<T, R> function) {
    return new ConsList<R>(function.apply(this.first), this.rest.map(function));
  }

  // FOLDR FUNCTION: ConsList
  public <R> R foldr(BiFunction<T, R, R> function, R base) {
    return function.apply(this.first, this.rest.foldr(function, base));
  }

  // ORMAP FUNCTION: Conslist
  public boolean ormap(IPredicate<T> predicate) {
    return predicate.apply(this.first) || this.rest.ormap(predicate);
  }

  // ANDMAP FUNCTION: Conslist
  public boolean andmap(IPredicate<T> predicate) {
    return predicate.apply(this.first) && this.rest.andmap(predicate);
  }

  // FILTER FUNCTION: ConsList
  public IList<T> filter(IPredicate<T> predicate) {
    if (predicate.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(predicate));
    }
    else {
      return this.rest.filter(predicate);
    }
  }

  // adds an item to the list
  public IList<T> add(T item) {
    return new ConsList<T>(item, this);
  }

  // counts number of items in the list
  public int count(int count) {
    return this.rest.count(count + 1);
  }

//  // compare two lists
//  public boolean compareLists(IList<ICartPt> given) {
//    return given.compareListsHelper(this.first) && this.rest.compareLists(given);
//  }
//  
//  // compare two lists helper
//  public boolean compareListsHelper(ICartPt given) {
//    return this.ormap(new NotSameCartPt(given));   
//        

  // }

}

//represents examples and tests for game
class IListExamples {
  IListExamples() {
  }

  WorldScene blank = new WorldScene(500, 500);

  IList<ICartPt> mtct = new MtList<ICartPt>();
  IList<ISpaceShip> mtss = new MtList<ISpaceShip>();

  ICartPt pt5 = new ShipPt(50, 3, false);
  ICartPt pt10 = new ShipPt(50, 3, false);

  ICartPt p0 = new ShipPt(250, 450, true);
  ISpaceShip player = new PlayerShip(p0, Color.black, 50, 25);
  ISpaceShip player2 = new PlayerShip(pt5, Color.black, 100, 100);

  ICartPt p1 = new ShipPt(50, 10, true);
  ICartPt p2 = new ShipPt(100, 10, true);
  ICartPt p3 = new ShipPt(150, 10, true);
  ICartPt p4 = new ShipPt(200, 10, true);
  ICartPt p5 = new ShipPt(250, 10, true);
  ICartPt p6 = new ShipPt(300, 10, true);
  ICartPt p7 = new ShipPt(350, 10, true);
  ICartPt p8 = new ShipPt(400, 10, true);
  ICartPt p9 = new ShipPt(450, 10, true);

  ICartPt p10 = new ShipPt(50, 40, true);
  ICartPt p11 = new ShipPt(100, 40, true);
  ICartPt p12 = new ShipPt(150, 40, true);
  ICartPt p13 = new ShipPt(200, 40, true);
  ICartPt p14 = new ShipPt(250, 40, true);
  ICartPt p15 = new ShipPt(300, 40, true);
  ICartPt p16 = new ShipPt(350, 40, true);
  ICartPt p17 = new ShipPt(400, 40, true);
  ICartPt p18 = new ShipPt(450, 40, true);

  ICartPt p19 = new ShipPt(50, 70, true);
  ICartPt p20 = new ShipPt(100, 70, true);
  ICartPt p21 = new ShipPt(150, 70, true);
  ICartPt p22 = new ShipPt(200, 70, true);
  ICartPt p23 = new ShipPt(250, 70, true);
  ICartPt p24 = new ShipPt(300, 70, true);
  ICartPt p25 = new ShipPt(350, 70, true);
  ICartPt p26 = new ShipPt(400, 70, true);
  ICartPt p27 = new ShipPt(450, 70, true);

  ICartPt p28 = new ShipPt(50, 100, true);
  ICartPt p29 = new ShipPt(100, 100, true);
  ICartPt p30 = new ShipPt(150, 100, true);
  ICartPt p31 = new ShipPt(200, 100, true);
  ICartPt p32 = new ShipPt(250, 100, true);
  ICartPt p33 = new ShipPt(300, 100, true);
  ICartPt p34 = new ShipPt(350, 100, true);
  ICartPt p35 = new ShipPt(400, 100, true);
  ICartPt p36 = new ShipPt(450, 100, true);
  ICartPt p37 = new ShipPt(450, 505, true);
  ICartPt p38 = new ShipPt(450, -1, true);

  ISpaceShip as1 = new AlienShip(p1, Color.red, 15);
  ISpaceShip as2 = new AlienShip(p2, Color.red, 15);
  ISpaceShip as3 = new AlienShip(p3, Color.red, 15);
  ISpaceShip as4 = new AlienShip(p4, Color.red, 15);
  ISpaceShip as5 = new AlienShip(p5, Color.red, 15);
  ISpaceShip as6 = new AlienShip(p6, Color.red, 15);
  ISpaceShip as7 = new AlienShip(p7, Color.red, 15);
  ISpaceShip as8 = new AlienShip(p8, Color.red, 15);
  ISpaceShip as9 = new AlienShip(p9, Color.red, 15);

  ISpaceShip as10 = new AlienShip(p10, Color.red, 15);
  ISpaceShip as11 = new AlienShip(p11, Color.red, 15);
  ISpaceShip as12 = new AlienShip(p12, Color.red, 15);
  ISpaceShip as13 = new AlienShip(p13, Color.red, 15);
  ISpaceShip as14 = new AlienShip(p14, Color.red, 15);
  ISpaceShip as15 = new AlienShip(p15, Color.red, 15);
  ISpaceShip as16 = new AlienShip(p16, Color.red, 15);
  ISpaceShip as17 = new AlienShip(p17, Color.red, 15);
  ISpaceShip as18 = new AlienShip(p18, Color.red, 15);

  ISpaceShip as19 = new AlienShip(p19, Color.red, 15);
  ISpaceShip as20 = new AlienShip(p20, Color.red, 15);
  ISpaceShip as21 = new AlienShip(p21, Color.red, 15);
  ISpaceShip as22 = new AlienShip(p22, Color.red, 15);
  ISpaceShip as23 = new AlienShip(p23, Color.red, 15);
  ISpaceShip as24 = new AlienShip(p24, Color.red, 15);
  ISpaceShip as25 = new AlienShip(p25, Color.red, 15);
  ISpaceShip as26 = new AlienShip(p26, Color.red, 15);
  ISpaceShip as27 = new AlienShip(p27, Color.red, 15);

  ISpaceShip as28 = new AlienShip(p28, Color.red, 15);
  ISpaceShip as29 = new AlienShip(p29, Color.red, 15);
  ISpaceShip as30 = new AlienShip(p30, Color.red, 15);
  ISpaceShip as31 = new AlienShip(p31, Color.red, 15);
  ISpaceShip as32 = new AlienShip(p32, Color.red, 15);
  ISpaceShip as33 = new AlienShip(p33, Color.red, 15);
  ISpaceShip as34 = new AlienShip(p34, Color.red, 15);
  ISpaceShip as35 = new AlienShip(p35, Color.red, 15);
  ISpaceShip as36 = new AlienShip(p36, Color.red, 15);

  IList<ICartPt> points = new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p2, new ConsList<ICartPt>(
      p3,
      new ConsList<ICartPt>(p4, new ConsList<ICartPt>(p5, new ConsList<ICartPt>(p6,
          new ConsList<ICartPt>(p7, new ConsList<ICartPt>(p8, new ConsList<ICartPt>(p9,
              new ConsList<ICartPt>(p10, new ConsList<ICartPt>(p11, new ConsList<ICartPt>(p12,
                  new ConsList<ICartPt>(p13, new ConsList<ICartPt>(p14, new ConsList<ICartPt>(p15,
                      new ConsList<ICartPt>(p16, new ConsList<ICartPt>(p17, new ConsList<ICartPt>(
                          p18,
                          new ConsList<ICartPt>(p19, new ConsList<ICartPt>(p20,
                              new ConsList<ICartPt>(p21, new ConsList<ICartPt>(p22,
                                  new ConsList<ICartPt>(p23, new ConsList<ICartPt>(p24,
                                      new ConsList<ICartPt>(p25, new ConsList<ICartPt>(p26,
                                          new ConsList<ICartPt>(p27, new ConsList<ICartPt>(p28,
                                              new ConsList<ICartPt>(p29, new ConsList<ICartPt>(p30,
                                                  new ConsList<ICartPt>(p31, new ConsList<ICartPt>(
                                                      p32,
                                                      new ConsList<ICartPt>(p33,
                                                          new ConsList<ICartPt>(p34,
                                                              new ConsList<ICartPt>(p35,
                                                                  new ConsList<ICartPt>(p36,
                                                                      new MtList<ICartPt>()))))))))))))))))))))))))))))))))))));

  IList<ICartPt> pointsFil = new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                              p1,
                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                                                      p1,
                                                      new ConsList<ICartPt>(p1,
                                                          new ConsList<ICartPt>(p1,
                                                              new ConsList<ICartPt>(p1,
                                                                  new ConsList<ICartPt>(p1,
                                                                      new MtList<ICartPt>()))))))))))))))))))))))))))))))))))));

  IList<ICartPt> pointsFilter = new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                              p1,
                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                                                      p1,
                                                      new ConsList<ICartPt>(p1,
                                                          new ConsList<ICartPt>(p1,
                                                              new ConsList<ICartPt>(p1,
                                                                  new ConsList<ICartPt>(p1,
                                                                      new ConsList<ICartPt>(p37,
                                                                          new MtList<ICartPt>())))))))))))))))))))))))))))))))))))));

  IList<ICartPt> pointsFilter2 = new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                              p1,
                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
                                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
                                                      p1,
                                                      new ConsList<ICartPt>(p1,
                                                          new ConsList<ICartPt>(p1,
                                                              new ConsList<ICartPt>(p1,
                                                                  new ConsList<ICartPt>(p1,
                                                                      new ConsList<ICartPt>(p38,
                                                                          new MtList<ICartPt>())))))))))))))))))))))))))))))))))))));

  IList<ISpaceShip> alienList2 = new ConsList<ISpaceShip>(as21,
      new ConsList<ISpaceShip>(as22,
          new ConsList<ISpaceShip>(as23,
              new ConsList<ISpaceShip>(as24,
                  new ConsList<ISpaceShip>(as25, new ConsList<ISpaceShip>(as26,
                      new ConsList<ISpaceShip>(as27, new ConsList<ISpaceShip>(as28,
                          new ConsList<ISpaceShip>(as29, new ConsList<ISpaceShip>(as30,
                              new ConsList<ISpaceShip>(as31, new ConsList<ISpaceShip>(as32,
                                  new ConsList<ISpaceShip>(as33, new ConsList<ISpaceShip>(as34,
                                      new ConsList<ISpaceShip>(as35, new ConsList<ISpaceShip>(as36,
                                          new MtList<ISpaceShip>()))))))))))))))));

  IList<ISpaceShip> alienList = new ConsList<ISpaceShip>(as1,
      new ConsList<ISpaceShip>(as2, new ConsList<ISpaceShip>(as3,
          new ConsList<ISpaceShip>(as4, new ConsList<ISpaceShip>(as5, new ConsList<ISpaceShip>(as6,
              new ConsList<ISpaceShip>(as7, new ConsList<ISpaceShip>(as8,
                  new ConsList<ISpaceShip>(as9, new ConsList<ISpaceShip>(as10,
                      new ConsList<ISpaceShip>(as11, new ConsList<ISpaceShip>(as12,
                          new ConsList<ISpaceShip>(as13, new ConsList<ISpaceShip>(as14,
                              new ConsList<ISpaceShip>(as15, new ConsList<ISpaceShip>(as16,
                                  new ConsList<ISpaceShip>(as17, new ConsList<ISpaceShip>(as18,
                                      new ConsList<ISpaceShip>(as19, new ConsList<ISpaceShip>(as20,
                                          this.alienList2))))))))))))))))))));

  IList<ISpaceShip> smallAList = new ConsList<ISpaceShip>(as1,
      new ConsList<ISpaceShip>(as2, new ConsList<ISpaceShip>(as3, new MtList<ISpaceShip>())));
  IList<ISpaceShip> smallAList2 = new ConsList<ISpaceShip>(as1, new ConsList<ISpaceShip>(as2,
      new ConsList<ISpaceShip>(as3, new ConsList<ISpaceShip>(as4, new MtList<ISpaceShip>()))));
  ICartPt pb1 = new PlayerBullet(5, 250, 450);
  ICartPt pb1new = new PlayerBullet(5, 250, 447);
  IList<ICartPt> startpb = new MtList<ICartPt>();
  IList<ICartPt> addpb = new ConsList<ICartPt>(pb1, startpb);
  IList<ICartPt> addpb2 = new ConsList<ICartPt>(pb1new, startpb);
  IList<ICartPt> addpb3 = new ConsList<ICartPt>(pb1new, new ConsList<ICartPt>(pb1, startpb));

  SpaceInvaders world = new SpaceInvaders(this.player, this.alienList, startpb);
  SpaceInvaders smallWorld = new SpaceInvaders(this.player, this.smallAList, startpb);
  SpaceInvaders smallWorld2 = new SpaceInvaders(this.player, this.smallAList2, addpb);

  // BigBang
  boolean testBigBang(Tester t) {
    SpaceInvaders world = new SpaceInvaders(this.player, this.alienList, startpb);
    int worldWidth = 500;
    int worldHeight = 500;
    double tickRate = 0.005;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

  ICartPt pt1 = new ShipPt(1, 1, true);
  ICartPt pt2 = new ShipPt(1, 2, false);
  ICartPt pt3 = new ShipPt(473, 3, true);
  ICartPt pt4 = new ShipPt(50, 3, false);

  ICartPt pt6 = new ShipPt(50, 501, true);
  ICartPt pt7 = new ShipPt(50, -1, true);

  ICartPt pb2 = new PlayerBullet(5, 250, 501);
  ICartPt pb3 = new PlayerBullet(5, 250, -1);
  ICartPt al1 = new AlienBullet(5, 100, 100);
  ICartPt al2 = new AlienBullet(5, 100, 501);
  ICartPt al3 = new AlienBullet(5, 100, -1);
  IList<ICartPt> ablist = new ConsList<ICartPt>(al1,
      new ConsList<ICartPt>(al2, new ConsList<ICartPt>(al3, new MtList<ICartPt>())));

  ISpaceShip player3 = new PlayerShip(pt3, Color.black, 50, 25);
  ISpaceShip player4 = new PlayerShip(pt2, Color.black, 100, 100);

  IList<ICartPt> pointList = new ConsList<ICartPt>(pt1,
      new ConsList<ICartPt>(pt2, new MtList<ICartPt>()));

  // --------------------------------------------------------------------------------------------

  // ICartPt Interface Tests

  // test thisX
  boolean testThisX(Tester t) {
    return t.checkExpect(this.pt1.thisX(), 1) && t.checkExpect(this.pb1.thisX(), 250)
        && t.checkExpect(this.al1.thisX(), 100);
  }

  // test thisY
  boolean testThisY(Tester t) {
    return t.checkExpect(this.pt1.thisY(), 1) && t.checkExpect(this.pb1.thisY(), 450)
        && t.checkExpect(this.al1.thisX(), 100);
  }

  // test getDirection
  boolean testThisDirection(Tester t) {
    return t.checkExpect(this.pt1.thisDirection(), true)
        && t.checkExpect(this.pb1.thisDirection(), false)
        && t.checkExpect(this.al1.thisDirection(), false);
  }

  // test equalCartPt
  boolean testEqualCartPt(Tester t) {
    return t.checkExpect(this.pt1.equalCartPt(pt2), false)
        && t.checkExpect(this.pt2.equalCartPt(pt1), false)
        && t.checkExpect(this.pt5.equalCartPt(pt4), true)
        && t.checkExpect(this.pt4.equalCartPt(pt5), true)
        && t.checkExpect(this.pt5.equalCartPt(pt10), true)
        && t.checkExpect(this.pt10.equalCartPt(pt5), true)
        && t.checkExpect(this.pt10.equalCartPt(pt4), true)
        && t.checkExpect(this.pt4.equalCartPt(pt10), true)
        && t.checkExpect(this.pt6.equalCartPt(pt10), false)
        && t.checkExpect(this.pt10.equalCartPt(pt6), false);
  }

  // test drawPt
  boolean testDrawCartPt(Tester t) {
    return t.checkExpect(this.pt1.drawPt(), new EmptyImage())
        && t.checkExpect(this.pb1.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.black))
        && t.checkExpect(this.al1.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.pb2.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.black))
        && t.checkExpect(this.al2.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.pb3.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.black))
        && t.checkExpect(this.al3.drawPt(), new CircleImage(5, OutlineMode.SOLID, Color.red));
  }

  // test placePt
  boolean testPlacePt(Tester t) {
    return t.checkExpect(this.pt1.placePt(blank), blank.placeImageXY(this.pt1.drawPt(), 1, 1))
        && t.checkExpect(this.pb1.placePt(blank), blank.placeImageXY(this.pb1.drawPt(), 250, 450))
        && t.checkExpect(this.al1.placePt(blank), blank.placeImageXY(this.al1.drawPt(), 100, 100))
        && t.checkExpect(this.pb2.placePt(blank), blank.placeImageXY(this.pb1.drawPt(), 250, 501))
        && t.checkExpect(this.al2.placePt(blank), blank.placeImageXY(this.al1.drawPt(), 100, 501))
        && t.checkExpect(this.pb3.placePt(blank), blank.placeImageXY(this.pb1.drawPt(), 250, -1))
        && t.checkExpect(this.al3.placePt(blank), blank.placeImageXY(this.al1.drawPt(), 100, -1));
  }

  // test move
  boolean testMovePt(Tester t) {
    return t.checkExpect(this.pt1.move(), new ShipPt(3, 1, true))
        && t.checkExpect(this.pt2.move(), this.pt2) && t.checkExpect(this.pt3.move(), this.pt3)
        && t.checkExpect(this.pt4.move(), new ShipPt(48, 3, false))
        && t.checkExpect(this.pb1.move(), new PlayerBullet(5, 250, 447))
        && t.checkExpect(this.al1.move(), new AlienBullet(5, 100, 103));
  }

  // test changeDir
  boolean testChangeDir(Tester t) {
    return t.checkExpect(this.pt1.changeDir(), new ShipPt(1, 1, false))
        && t.checkExpect(this.pt2.changeDir(), new ShipPt(1, 2, true))
        && t.checkExpect(this.pb1.changeDir(), this.pb1)
        && t.checkExpect(this.al1.changeDir(), this.al1);
  }

  // test cartOnScreen
  boolean testCartOnScreen(Tester t) {
    return t.checkExpect(this.pt1.cartPtOnScreen(), true)
        && t.checkExpect(this.pt6.cartPtOnScreen(), false)
        && t.checkExpect(this.pt7.cartPtOnScreen(), false)
        && t.checkExpect(this.pb2.cartPtOnScreen(), false)
        && t.checkExpect(this.pb3.cartPtOnScreen(), false)
        && t.checkExpect(this.al2.cartPtOnScreen(), false)
        && t.checkExpect(this.al3.cartPtOnScreen(), false)
        && t.checkExpect(this.pb1.cartPtOnScreen(), true)
        && t.checkExpect(this.al1.cartPtOnScreen(), true);

  }

  // ISpaceShip Interface Tests

  // test drawSpaceShip
  boolean testDrawSpaceShip(Tester t) {
    return t.checkExpect(this.player.drawSpaceShip(),
        new RectangleImage(50, 25, OutlineMode.SOLID, Color.black))
        && t.checkExpect(this.as1.drawSpaceShip(),
            new RectangleImage(15, 15, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.as2.drawSpaceShip(),
            new RectangleImage(15, 15, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.as3.drawSpaceShip(),
            new RectangleImage(15, 15, OutlineMode.SOLID, Color.red))
        && t.checkExpect(this.player2.drawSpaceShip(),
            new RectangleImage(100, 100, OutlineMode.SOLID, Color.black));
  }

  // test moveSpaceShip
  boolean testMoveSpaceShip(Tester t) {
    return t.checkExpect(this.player.moveSpaceShip(),
        new PlayerShip(new ShipPt(252, 450, true), Color.black, 50, 25))
        && t.checkExpect(this.player3.moveSpaceShip(),
            new PlayerShip(new ShipPt(473, 3, true), Color.black, 50, 25))
        && t.checkExpect(this.player4.moveSpaceShip(),
            new PlayerShip(new ShipPt(1, 2, false), Color.black, 100, 100))
        && t.checkExpect(this.player2.moveSpaceShip(),
            new PlayerShip(new ShipPt(48, 3, false), Color.black, 100, 100))
        && t.checkExpect(this.as1.moveSpaceShip(), this.as1)
        && t.checkExpect(this.as2.moveSpaceShip(), this.as2);
  }

  // test placeSpaceShip
  boolean testPlaceSpaceShip(Tester t) {
    return t.checkExpect(this.player.placeSpaceShip(blank),
        blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.player2.placeSpaceShip(blank),
            blank.placeImageXY(new RectangleImage(100, 100, OutlineMode.SOLID, Color.black), 50, 3))
        && t.checkExpect(this.player3.placeSpaceShip(blank),
            blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 473, 3))
        && t.checkExpect(this.as1.placeSpaceShip(blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.as1.placeSpaceShip(blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10));
  }

  // test changeDirection
  boolean testChangeDirectionSpaceShip(Tester t) {
    return t.checkExpect(this.player.changeDirSpaceShip(),
        new PlayerShip(new ShipPt(250, 450, false), Color.black, 50, 25))
        && t.checkExpect(this.player2.changeDirSpaceShip(),
            new PlayerShip(new ShipPt(50, 3, true), Color.black, 100, 100))
        && t.checkExpect(this.as1.changeDirSpaceShip(), this.as1);
  }

  // test getX, get Y and getDirection
  boolean testGetXYDirection(Tester t) {
    return t.checkExpect(this.player.getX(), 250) && t.checkExpect(this.player.getY(), 450)
        && t.checkExpect(this.player.thisDirection(), true) && t.checkExpect(this.as1.getX(), 50)
        && t.checkExpect(this.as1.getY(), 10) && t.checkExpect(this.as1.thisDirection(), false);
  }

  // test for SpaceInvaders World Class

  // test makeScene

  boolean testMakeScene(Tester t) {
    return t.checkExpect(this.smallWorld.makeScene(),
        this.player.placeSpaceShip(blank)
            .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
            .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
            .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.smallWorld2.makeScene(),
            this.player.placeSpaceShip(blank)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10));
  }

  // test onTick
  boolean testOnTick(Tester t) {
    return t.checkExpect(this.world.onTick(),
        new SpaceInvaders(new PlayerShip(new ShipPt(252, 450, true), Color.black, 50, 25),
            this.alienList, this.startpb))
        && t.checkExpect(this.smallWorld.onTick(),
            new SpaceInvaders(new PlayerShip(new ShipPt(252, 450, true), Color.black, 50, 25),
                this.smallAList, this.startpb))
        && t.checkExpect(this.smallWorld2.onTick(),
            new SpaceInvaders(new PlayerShip(new ShipPt(252, 450, true), Color.black, 50, 25),
                this.smallAList2, this.addpb2));
  }

  // // test onKeyEvent
  // boolean testOnKey(Tester t) {
  // return t.checkExpect(this.world.onKeyEvent("left"), this.world) &&
  // t.checkExpect(this.world.onKeyEvent("right"),
  // new SpaceInvaders(new PlayerShip(new ShipPt(252, 450, true),
  // Color.black, 50, 25), this.alienList, this.startpb));
  //
  // }

  // test IPredicate<CartPt>
  boolean testSameCartPtPredicate(Tester t) {
    return t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt2)), true)
        && t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt3)), false)
        && t.checkExpect(this.points.ormap(new SameCartPt(this.p22)), true)
        && t.checkExpect(this.points.ormap(new SameCartPt(this.p38)), false)
        && t.checkExpect(this.mtct.ormap(new SameCartPt(this.p22)), false);
  }

  // test IPredicate<CartPt> that takes in list
  boolean testCartContainedInPredicate1(Tester t) {
    return t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt1), true)
        && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt2), true)
        && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt3), false)
        && t.checkExpect(new CartPtContainedIn(this.points).apply(p38), false)
        && t.checkExpect(new CartPtContainedIn(this.points).apply(p37), false)
        && t.checkExpect(new CartPtContainedIn(this.points).apply(p22), true)
        && t.checkExpect(new CartPtContainedIn(this.points).apply(p2), true)
        && t.checkExpect(new CartPtContainedIn(this.mtct).apply(p2), false);
  }

  // test add
  boolean testAddList(Tester t) {
    return t.checkExpect(this.smallAList.add(as4),
        new ConsList<ISpaceShip>(as4,
            new ConsList<ISpaceShip>(as1,
                new ConsList<ISpaceShip>(as2,
                    new ConsList<ISpaceShip>(as3, new MtList<ISpaceShip>())))))
        && t.checkExpect(this.alienList.add(player),
            new ConsList<ISpaceShip>(this.player, this.alienList))
        && t.checkExpect(this.startpb.add(this.pb1), this.addpb)
        && t.checkExpect(this.mtss.add(this.as1), new ConsList<ISpaceShip>(this.as1, this.mtss));
  }

  // test count
  boolean testCountList(Tester t) {
    return t.checkExpect(this.smallAList.count(0), 3) && t.checkExpect(this.alienList.count(0), 36)
        && t.checkExpect(this.smallAList2.count(0), 4)
        && t.checkExpect(new MtList<ICartPt>().count(0), 0);
  }

  // test CartPtOffscreen IPredicate<CartPt>
  boolean testFilter(Tester t) {
    return t.checkExpect(this.points.filter(new CartPtOffscreen()), this.points)
        && t.checkExpect(this.pointsFilter.filter(new CartPtOffscreen()), this.pointsFil)
        && t.checkExpect(this.pointsFilter2.filter(new CartPtOffscreen()), this.pointsFil)
        && t.checkExpect(this.mtct.filter(new CartPtOffscreen()), this.mtct);
  }

  // test IMoveCartPt IFunction<> (MAP
  boolean testMapIMoveCartPt(Tester t) {
    return t.checkExpect(this.mtct.map(new MoveICartPt()), new MtList<ICartPt>())
        && t.checkExpect(this.addpb3.map(new MoveICartPt()),
            new ConsList<ICartPt>(new PlayerBullet(5, 250, 444),
                new ConsList<ICartPt>(new PlayerBullet(5, 250, 447), startpb)))
        && t.checkExpect(this.ablist.map(new MoveICartPt()),
            new ConsList<ICartPt>(new AlienBullet(5, 100, 103),
                new ConsList<ICartPt>(new AlienBullet(5, 100, 504),
                    new ConsList<ICartPt>(new AlienBullet(5, 100, 2), this.mtct))));
  }

  // test createAllShips <foldr>
  boolean testCreateAllShips(Tester t) {
    return t.checkExpect(this.smallAList.foldr(new CreateAllShips(), blank),
        blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
            .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
            .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.smallAList2.foldr(new CreateAllShips(), blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.alienList.foldr(new CreateAllShips(), blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 250, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 300, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 350, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 400, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 450, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 250, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 300, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 350, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 400, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 450, 40)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 250, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 300, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 350, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 400, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 450, 70)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 250, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 300, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 350, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 400, 100)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 450, 100));

  }

  // test addPlayerBullets <foldr>
  boolean testAddPlayerBullets(Tester t) {
    return t.checkExpect(this.startpb.foldr(new AddPlayerBullets(), blank), blank)
        && t.checkExpect(this.addpb.foldr(new AddPlayerBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.addpb3.foldr(new AddPlayerBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 447)
                .placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450));
  }

  IPredicate<ICartPt> cartOffScreen = new CartPtOffscreen();
  IPredicate<ICartPt> sameCartPT = new SameCartPt(p1);
  IPredicate<ICartPt> cartContainedIn = new CartPtContainedIn(this.points);

  IFunction<ICartPt, ICartPt> moveCart = new MoveICartPt();
  BiFunction<ISpaceShip, WorldScene, WorldScene> createShips = new CreateAllShips();
  BiFunction<ICartPt, WorldScene, WorldScene> addPlayerBullets = new AddPlayerBullets();

  // test apply methods
  boolean testApplyMethod(Tester t) {
    return t.checkExpect(this.cartOffScreen.apply(p1), true)
        && t.checkExpect(this.cartOffScreen.apply(p37), false)
        && t.checkExpect(this.cartOffScreen.apply(p38), false)
        && t.checkExpect(this.cartOffScreen.apply(p15), true)
        && t.checkExpect(this.sameCartPT.apply(p1), true)
        && t.checkExpect(this.sameCartPT.apply(pt5), false)
        && t.checkExpect(this.sameCartPT.apply(pt10), false)
        && t.checkExpect(this.cartContainedIn.apply(pt1), false)
        && t.checkExpect(this.cartContainedIn.apply(pt2), false)
        && t.checkExpect(this.cartContainedIn.apply(p1), true)
        && t.checkExpect(this.cartContainedIn.apply(p15), true)
        && t.checkExpect(this.moveCart.apply(pt1), new ShipPt(3, 1, true))
        && t.checkExpect(this.moveCart.apply(al1), new AlienBullet(5, 100, 103))
        && t.checkExpect(this.moveCart.apply(pb1), new PlayerBullet(5, 250, 447))
        && t.checkExpect(this.createShips.apply(this.player, this.blank),
            blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 250,
                450))
        && t.checkExpect(this.createShips.apply(as1, blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.createShips.apply(player2, blank),
            blank.placeImageXY(new RectangleImage(100, 100, OutlineMode.SOLID, Color.black), 50, 3))
        && t.checkExpect(this.addPlayerBullets.apply(al1, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 100, 100))
        && t.checkExpect(this.addPlayerBullets.apply(pb1, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.addPlayerBullets.apply(this.pb2, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 501));

  }

  // // test drawSpaceShip on the PlayerShip class
  // boolean testDrawSpaceShipPlayer(Tester t) {
  // return t.checkExpect(this.player.drawSpaceShip(),
  // new RectangleImage(50, 25, OutlineMode.SOLID, Color.black));
  // }
  //
  // // test drawSpaceShip on the AlienShip class
  // boolean testDrawSpaceShipAlien(Tester t) {
  // return t.checkExpect(this.as1.drawSpaceShip(),
  // new RectangleImage(15, 15, OutlineMode.SOLID, Color.red))
  // && t.checkExpect(this.as2.drawSpaceShip(),
  // new RectangleImage(15, 15, OutlineMode.SOLID, Color.red));
  // }
  //
  // // test placeSpaceShip on the PlayerShip and AlienShip class
  // boolean testPlaceSpaceShip(Tester t) {
  // return t.checkExpect(this.player.placeSpaceShip(blank),
  // blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID,
  // Color.black), 250, 450))
  // && t.checkExpect(this.as1.placeSpaceShip(blank),
  // blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red),
  // 50, 10));
  // }
  //
  //
  // // test makeScene
  // boolean testMakeScene(Tester t) {
  // return t.checkExpect(this.smallWorld.makeScene(),
  // blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red),
  // 150, 10)
  // .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100,
  // 10)
  // .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50,
  // 10));
  // }
  //
  // // test IPredicate<CartPt>
  // boolean testSameCartPtPredicate(Tester t) {
  // return t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt3)), false)
  // && t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt2)), true);
  // }
  //
  // // test IPredicate<CartPt> that takes in list
  // boolean testCartContainedInPredicate(Tester t) {
  // return t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt1), true)
  // && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt2), true)
  // && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt3), false);
  // }
  //
  // // test createAllShips
  // boolean testCreateAllShips(Tester t) {
  // return t.checkExpect(this.smallAList.foldr(new createAllShips(), blank),
  // blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red),
  // 150, 10)
  // .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100,
  // 10)
  // .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50,
  // 10));
  // }

}