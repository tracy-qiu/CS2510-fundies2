import java.awt.Color;
import java.util.Random;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldEnd;
import tester.Tester;

// represents Predicates
interface IPredicate<T> {

  // determine if function on type T is true
  boolean apply(T t);
}

// determines if points in list are on screen 
class CartPtOffscreen implements IPredicate<ICartPt> {

  // determine if function on type T is true
  public boolean apply(ICartPt given) {
    return given.cartPtOnScreen();
  }
}

// predicate to determine if two CartPts are the same
class SameCartPt implements IPredicate<ICartPt> {
  ICartPt point;

  SameCartPt(ICartPt point) {
    this.point = point;
  }

  // determine if given point is equal to this point
  public boolean apply(ICartPt given) {
    return point.equalCartPt(given);
  }
}

// determines if a CartPt is contained in list
class CartPtContainedIn implements IPredicate<ICartPt> {
  IList<ICartPt> points;

  CartPtContainedIn(IList<ICartPt> points) {
    this.points = points;
  }

  // determine if the given point is contained in this list of points
  public boolean apply(ICartPt t) {
    return points.ormap(new SameCartPt(t));
  }
}

// function that determines if point is in within a specified area 
class SameArea implements IPredicate<ICartPt> {
  ICartPt point;
  boolean isPlayer;

  SameArea(ICartPt point) {
    this.point = point;
    this.isPlayer = false;
  }

  SameArea(ISpaceShip spaceship) {
    this.point = spaceship.getCoordinate();
    this.isPlayer = true;
  }

  // determines if point is in specified area
  public boolean apply(ICartPt given) {
    return point.sameArea(given, isPlayer);
  }
}

// update hitSpaceship field of bullet if bullet hit any spaceship
class UpdateHitObject implements IFunction<ICartPt, ICartPt> {
  IList<ISpaceShip> spaceships;

  UpdateHitObject(IList<ISpaceShip> spaceships) {
    this.spaceships = spaceships;
  }

  // updates bullet infomation (field) to indicate that its hit a ship
  public ICartPt apply(ICartPt t) {
    if (spaceships.map(new GetCoordinateSpaceShip()).ormap(new SameArea(t))) {
      return t.updateBulletHit();
    }
    return t;
  }
}

// determines if bullet hit the spaceship
class BulletHit implements IPredicate<ICartPt> {

  // determines if bullet hit the spaceship
  public boolean apply(ICartPt t) {
    return !t.hitSpaceship();
  }
}

// determines if spaceship is hit by given list of player bullets 
class SpaceShipHit implements IPredicate<ISpaceShip> {
  IList<ICartPt> playerBullets;

  SpaceShipHit(IList<ICartPt> playerBullets) {
    this.playerBullets = playerBullets;
  }

  //determines if spaceship is hit by given list of player bullets 
  public boolean apply(ISpaceShip t) {
    return !playerBullets.ormap(new SameArea(t.getCoordinate()));
  }
}

// function that moves all CartPt in a list
class MoveICartPt implements IFunction<ICartPt, ICartPt> {

  // determine if the given point is contained in this list of points
  public ICartPt apply(ICartPt point) {
    return point.move();
  }
}

// function that gets coordinate of spaceship 
class GetCoordinateSpaceShip implements IFunction<ISpaceShip, ICartPt> {

  // return coordinates of ship
  public ICartPt apply(ISpaceShip ship) {
    return ship.getCoordinate();
  }

}

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

  // places all CartPts on WorldScene
  public WorldScene apply(ISpaceShip ship, WorldScene space) {
    return ship.placeSpaceShip(space);
  }
}

//adds bullets to WorldScene
class AddBullets implements BiFunction<ICartPt, WorldScene, WorldScene> {

  // places bullets on WorldScene
  public WorldScene apply(ICartPt bullet, WorldScene space) {
    return bullet.placePt(space);
  }
}

// represents a generic list
interface IList<T> {

  // applies a function to all items in a list
  <R> IList<R> map(IFunction<T, R> function);

  // boolean BulletHitInvader(IList<ISpaceShip> invaders);

  // collapses all items in a list into a single variable
  <R> R foldr(BiFunction<T, R, R> function, R base);

  // determines if at least one item in the list passes a test
  boolean ormap(IPredicate<T> predicate);

  // determines if all items in a list passes a test
  boolean andmap(IPredicate<T> predicate);

  // produces list of items that passes a test
  IList<T> filter(IPredicate<T> predicate);

  // add function for lists
  IList<T> add(T item);

  // count function for lists
  int count(int count);

  // returns object at the given index
  T produceBullet(int index, int sofar);

  // determines if list is empty
  public boolean isEmpty();
}

// generic empty list
class MtList<T> implements IList<T> {

  // applies a function to all items in a list
  public <R> IList<R> map(IFunction<T, R> function) {
    return new MtList<R>();
  }

  // collapses all items in a list into a single variable
  public <R> R foldr(BiFunction<T, R, R> function, R base) {
    return base;
  }

  // determines if at least one item in the list passes a test
  public boolean ormap(IPredicate<T> predicate) {
    return false;
  }

  // determines if all items in a list passes a test
  public boolean andmap(IPredicate<T> predicate) {
    return true;
  }

  // produces list of items that passes a test
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

  // returns object at the given index
  public T produceBullet(int index, int sofar) {
    throw new IllegalArgumentException("Invalid Index - no invader found at given index");
  }

  // determines if list is empty
  public boolean isEmpty() {
    return true;
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

  // applies a function to all items in a list
  public <R> IList<R> map(IFunction<T, R> function) {
    return new ConsList<R>(function.apply(this.first), this.rest.map(function));
  }

  // collapses all items in a list into a single variable
  public <R> R foldr(BiFunction<T, R, R> function, R base) {
    return function.apply(this.first, this.rest.foldr(function, base));
  }

  // determines if at least one item in the list passes a test
  public boolean ormap(IPredicate<T> predicate) {
    return predicate.apply(this.first) || this.rest.ormap(predicate);
  }

  // determines if all items in a list passes a test
  public boolean andmap(IPredicate<T> predicate) {
    return predicate.apply(this.first) && this.rest.andmap(predicate);
  }

  // produces list of items that passes a test
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

  // returns object at the given index
  public T produceBullet(int index, int sofar) {
    if (sofar == index) {
      return this.first;
    }
    else {
      return this.rest.produceBullet(index, sofar + 1);
    }
  }

  // determines if list is empty
  public boolean isEmpty() {
    return false;
  }
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
  ICartPt p00 = new ShipPt(252, 450, true);
  ICartPt p000 = new ShipPt(254, 450, true);
  ISpaceShip player = new PlayerShip(p0);
  ISpaceShip player2 = new PlayerShip(pt5);

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
  ICartPt p39 = new ShipPt(450, 10, false);
  ICartPt p40 = new ShipPt(0, 150, false);

  ISpaceShip as1 = new AlienShip(p1);
  ISpaceShip as2 = new AlienShip(p2);
  ISpaceShip as3 = new AlienShip(p3);
  ISpaceShip as4 = new AlienShip(p4);
  ISpaceShip as5 = new AlienShip(p5);
  ISpaceShip as6 = new AlienShip(p6);
  ISpaceShip as7 = new AlienShip(p7);
  ISpaceShip as8 = new AlienShip(p8);
  ISpaceShip as9 = new AlienShip(p9);

  ISpaceShip as10 = new AlienShip(p10);
  ISpaceShip as11 = new AlienShip(p11);
  ISpaceShip as12 = new AlienShip(p12);
  ISpaceShip as13 = new AlienShip(p13);
  ISpaceShip as14 = new AlienShip(p14);
  ISpaceShip as15 = new AlienShip(p15);
  ISpaceShip as16 = new AlienShip(p16);
  ISpaceShip as17 = new AlienShip(p17);
  ISpaceShip as18 = new AlienShip(p18);

  ISpaceShip as19 = new AlienShip(p19);
  ISpaceShip as20 = new AlienShip(p20);
  ISpaceShip as21 = new AlienShip(p21);
  ISpaceShip as22 = new AlienShip(p22);
  ISpaceShip as23 = new AlienShip(p23);
  ISpaceShip as24 = new AlienShip(p24);
  ISpaceShip as25 = new AlienShip(p25);
  ISpaceShip as26 = new AlienShip(p26);
  ISpaceShip as27 = new AlienShip(p27);

  ISpaceShip as28 = new AlienShip(p28);
  ISpaceShip as29 = new AlienShip(p29);
  ISpaceShip as30 = new AlienShip(p30);
  ISpaceShip as31 = new AlienShip(p31);
  ISpaceShip as32 = new AlienShip(p32);
  ISpaceShip as33 = new AlienShip(p33);
  ISpaceShip as34 = new AlienShip(p34);
  ISpaceShip as35 = new AlienShip(p35);
  ISpaceShip as36 = new AlienShip(p36);

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
                                                                      new MtList<ICartPt>()))))))))
                                              ))))))))))))))))))))))))))));

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
                                                                      new MtList<ICartPt>()))))))))
                                              ))))))))))))))))))))))))))));

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
                                                                          new MtList<ICartPt>()))))
                                                          )))))))))))))))))))))))))))))))));

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
                                                                          new MtList<ICartPt>()))))
                                                          )))))))))))))))))))))))))))))))));

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

  IList<ISpaceShip> smallPlayer = new ConsList<ISpaceShip>(this.player, this.mtss);
  IList<ISpaceShip> smallAList = new ConsList<ISpaceShip>(as1,
      new ConsList<ISpaceShip>(as2, new ConsList<ISpaceShip>(as3, new MtList<ISpaceShip>())));
  IList<ISpaceShip> smallAList2 = new ConsList<ISpaceShip>(as1, new ConsList<ISpaceShip>(as2,
      new ConsList<ISpaceShip>(as3, new ConsList<ISpaceShip>(as4, new MtList<ISpaceShip>()))));
  ICartPt pb1 = new PlayerBullet(250, 450, false);
  ICartPt pb11 = new PlayerBullet(250, 450, true);
  ICartPt pb111 = new PlayerBullet(50, 10, false);
  ICartPt pb1new = new PlayerBullet(250, 447, false);
  ICartPt ib1 = new AlienBullet(200, 10, false);
  ICartPt ib2 = new AlienBullet(200, 10, true);
  ICartPt ib3 = new AlienBullet(250, 450, false);
  IList<ICartPt> startBullets = new MtList<ICartPt>();
  IList<ICartPt> startBullets2 = new ConsList<ICartPt>(new AlienBullet(249, 449, true), this.mtct);
  IList<ICartPt> addib = new ConsList<ICartPt>(ib1, startBullets);
  IList<ICartPt> addib3 = new ConsList<ICartPt>(ib3, startBullets);
  IList<ICartPt> addib4 = new ConsList<ICartPt>(ib3, addib);
  IList<ICartPt> addpb = new ConsList<ICartPt>(pb1, startBullets);
  IList<ICartPt> addpb2 = new ConsList<ICartPt>(pb1new, startBullets);
  IList<ICartPt> addpb3 = new ConsList<ICartPt>(pb1new, new ConsList<ICartPt>(pb1, startBullets));
  IList<ICartPt> addpb33 = new ConsList<ICartPt>(pb1,
      new ConsList<ICartPt>(pb1new, new ConsList<ICartPt>(pb1, startBullets)));

  IList<ICartPt> addpb4 = new ConsList<ICartPt>(pb1new, new ConsList<ICartPt>(pb1, addpb));
  IList<ICartPt> addpb5 = new ConsList<ICartPt>(pb111, addpb4);

  SpaceInvaders world = new SpaceInvaders(this.player, this.alienList, startBullets, startBullets);
  SpaceInvaders smallWorld = new SpaceInvaders(this.player, this.smallAList, startBullets,
      startBullets);
  SpaceInvaders smallWorld11 = new SpaceInvaders(this.player2, this.smallAList, startBullets,
      startBullets);
  SpaceInvaders smallWorld2 = new SpaceInvaders(this.player, this.smallAList2, addpb, startBullets);
  SpaceInvaders smallWorld3 = new SpaceInvaders(this.player, this.mtss, addpb, startBullets);
  SpaceInvaders smallWorld4 = new SpaceInvaders(this.player, this.alienList, addpb, startBullets2);
  SpaceInvaders smallWorld5 = new SpaceInvaders(this.player, this.smallAList2, addpb2,
      startBullets2);
  SpaceInvaders smallWorld6 = new SpaceInvaders(this.player, this.smallAList2, addpb2,
      startBullets2);
  SpaceInvaders smallWorld7 = new SpaceInvaders(this.player, this.smallAList2, addpb3,
      startBullets2);
  SpaceInvaders smallWorld75 = new SpaceInvaders(this.player, this.smallAList2, addpb33,
      startBullets2);
  SpaceInvaders smallWorld8 = new SpaceInvaders(this.player, this.smallAList2, addpb4,
      startBullets2);

  // BigBang
  boolean testBigBang(Tester t) {
    SpaceInvaders world = new SpaceInvaders(this.player, this.alienList, startBullets,
        startBullets);
    int worldWidth = 500;
    int worldHeight = 500;
    double tickRate = 0.02;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

  ICartPt pt1 = new ShipPt(1, 1, true);
  ICartPt pt2 = new ShipPt(1, 2, false);
  ICartPt pt3 = new ShipPt(473, 3, true);
  ICartPt pt4 = new ShipPt(50, 3, false);

  ICartPt pt6 = new ShipPt(50, 501, true);
  ICartPt pt7 = new ShipPt(50, -1, true);

  ICartPt pb2 = new PlayerBullet(250, 501, false);
  ICartPt pb3 = new PlayerBullet(250, -1, false);
  ICartPt pb4 = new PlayerBullet(150, 10, false);
  ICartPt al1 = new AlienBullet(100, 100, false);
  ICartPt al2 = new AlienBullet(100, 501, false);
  ICartPt al3 = new AlienBullet(100, -1, false);
  IList<ICartPt> ablist = new ConsList<ICartPt>(al1,
      new ConsList<ICartPt>(al2, new ConsList<ICartPt>(al3, new MtList<ICartPt>())));
  IList<ICartPt> ablist2 = new ConsList<ICartPt>(p1,
      new ConsList<ICartPt>(p2, new ConsList<ICartPt>(p3, new MtList<ICartPt>())));

  ISpaceShip player3 = new PlayerShip(pt3);
  ISpaceShip player4 = new PlayerShip(pt2);

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
        && t.checkExpect(this.pb1.move(), new PlayerBullet(250, 447, false))
        && t.checkExpect(this.al1.move(), new AlienBullet(100, 103, false));
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

  // test updateBulletHit
  boolean testUpdateBulletHit(Tester t) {
    return t.checkExpect(this.p1.updateBulletHit(), this.p1)
        && t.checkExpect(this.p39.updateBulletHit(), new ShipPt(450, 10, false))
        && t.checkExpect(this.pb1.updateBulletHit(), new PlayerBullet(250, 450, true))
        && t.checkExpect(this.pb11.updateBulletHit(), new PlayerBullet(250, 450, true))
        && t.checkExpect(this.ib1.updateBulletHit(), new AlienBullet(200, 10, true))
        && t.checkExpect(this.ib2.updateBulletHit(), new AlienBullet(200, 10, true));
  }

  // test hitSpaceShip
  boolean testHitSpaceShip(Tester t) {
    return t.checkExpect(this.p1.hitSpaceship(), false)
        && t.checkExpect(this.p30.hitSpaceship(), false)
        && t.checkExpect(this.pb1.hitSpaceship(), false)
        && t.checkExpect(this.pb11.hitSpaceship(), true)
        && t.checkExpect(this.ib1.hitSpaceship(), false)
        && t.checkExpect(this.ib2.hitSpaceship(), true);
  }

  ICartPt test1 = new AlienBullet(225, 438, true);
  ICartPt test2 = new AlienBullet(275, 438, true);
  ICartPt test3 = new AlienBullet(225, 462, true);
  ICartPt test4 = new AlienBullet(275, 462, true);
  ICartPt test5 = new AlienBullet(243, 443, true);
  ICartPt test6 = new AlienBullet(257, 457, true);
  ICartPt test7 = new AlienBullet(243, 457, true);
  ICartPt test8 = new AlienBullet(243, 443, true);
  ICartPt test9 = new AlienBullet(245, 445, true);

  // test sameArea
  boolean testSameArea(Tester t) {
    return t.checkExpect(this.p0.sameArea(test1, true), true)
        && t.checkExpect(this.p0.sameArea(test2, true), true)
        && t.checkExpect(this.p0.sameArea(test3, true), true)
        && t.checkExpect(this.p0.sameArea(test4, true), true)
        && t.checkExpect(this.p0.sameArea(test1, false), false)
        && t.checkExpect(this.p0.sameArea(test2, false), false)
        && t.checkExpect(this.p0.sameArea(test3, false), false)
        && t.checkExpect(this.p0.sameArea(test4, false), false)
        && t.checkExpect(this.p0.sameArea(test5, false), true)
        && t.checkExpect(this.p0.sameArea(test6, false), true)
        && t.checkExpect(this.p0.sameArea(test7, false), true)
        && t.checkExpect(this.p0.sameArea(test8, false), true)
        && t.checkExpect(this.p0.sameArea(test4, false), false)
        && t.checkExpect(this.p0.sameArea(test5, true), true)
        && t.checkExpect(this.p0.sameArea(test6, true), true)
        && t.checkExpect(this.p0.sameArea(test7, true), true)
        && t.checkExpect(this.p0.sameArea(test8, true), true)
        && t.checkExpect(this.p0.sameArea(test4, false), false)
        && t.checkExpect(this.p0.sameArea(p1, false), false)
        && t.checkExpect(this.p0.sameArea(p1, true), false)
        && t.checkExpect(this.p0.sameArea(p0, false), true)
        && t.checkExpect(this.p0.sameArea(p0, true), true);
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
            new RectangleImage(50, 25, OutlineMode.SOLID, Color.black));
  }

  // test moveSpaceShip
  boolean testMoveSpaceShip(Tester t) {
    return t.checkExpect(this.player.moveSpaceShip(), new PlayerShip(new ShipPt(252, 450, true)))
        && t.checkExpect(this.player3.moveSpaceShip(), new PlayerShip(new ShipPt(473, 3, true)))
        && t.checkExpect(this.player4.moveSpaceShip(), new PlayerShip(new ShipPt(1, 2, false)))
        && t.checkExpect(this.player2.moveSpaceShip(), new PlayerShip(new ShipPt(48, 3, false)))
        && t.checkExpect(this.as1.moveSpaceShip(), this.as1)
        && t.checkExpect(this.as2.moveSpaceShip(), this.as2);
  }

  // test placeSpaceShip
  boolean testPlaceSpaceShip(Tester t) {
    return t.checkExpect(this.player.placeSpaceShip(blank),
        blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.player2.placeSpaceShip(blank),
            blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 50, 3))
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
        new PlayerShip(new ShipPt(250, 450, false)))
        && t.checkExpect(this.player2.changeDirSpaceShip(), new PlayerShip(new ShipPt(50, 3, true)))
        && t.checkExpect(this.as1.changeDirSpaceShip(), this.as1);
  }

  // test getX, get Y and getDirection
  boolean testGetXYDirection(Tester t) {
    return t.checkExpect(this.player.getX(), 250) && t.checkExpect(this.player.getY(), 450)
        && t.checkExpect(this.player.thisDirection(), true) && t.checkExpect(this.as1.getX(), 50)
        && t.checkExpect(this.as1.getY(), 10) && t.checkExpect(this.as1.thisDirection(), false);
  }

  // test getCoordinate
  boolean testGetCoordinate(Tester t) {
    return t.checkExpect(this.player.getCoordinate(), this.p0)
        && t.checkExpect(this.as1.getCoordinate(), this.p1)
        && t.checkExpect(this.as2.getCoordinate(), this.p2)
        && t.checkExpect(this.as3.getCoordinate(), this.p3)
        && t.checkExpect(this.player2.getCoordinate(), new ShipPt(50, 3, false));
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
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.smallWorld5.makeScene(),
            this.player.placeSpaceShip(blank)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10)
                .placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 447)
                .placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 249, 449));
  }

  // test makeAFinalScene
  boolean testFinalScene(Tester t) {
    return t.checkExpect(this.world.makeAFinalScene(), new WorldScene(500, 500))
        && t.checkExpect(this.smallWorld.makeAFinalScene(), new WorldScene(500, 500))
        && t.checkExpect(this.smallWorld2.makeAFinalScene(), new WorldScene(500, 500));
  }

  IList<ISpaceShip> alienshiptest0 = mtss;
  IList<ISpaceShip> alienshiptest = new ConsList<ISpaceShip>(as1, mtss);
  IList<ISpaceShip> alienshiptest2 = new ConsList<ISpaceShip>(as2, alienshiptest);
  IList<ISpaceShip> alienshiptest3 = new ConsList<ISpaceShip>(as3, alienshiptest2);
  IList<ICartPt> playerbulletstest0 = this.mtct;
  IList<ICartPt> playerbulletstest = new ConsList<ICartPt>(new PlayerBullet(50, 10, false),
      this.mtct);
  IList<ICartPt> playerbulletstest2 = new ConsList<ICartPt>(new PlayerBullet(400, 250, false),
      new ConsList<ICartPt>(new PlayerBullet(50, 10, false), this.mtct));
  IList<ICartPt> playerbulletstest3 = new ConsList<ICartPt>(new PlayerBullet(100, 10, false),
      new ConsList<ICartPt>(new PlayerBullet(400, 250, false),
          new ConsList<ICartPt>(new PlayerBullet(50, 10, false), this.mtct)));
  SpaceInvaders testWorld = new SpaceInvaders(this.player, this.alienshiptest3, this.startBullets,
      this.startBullets);
  SpaceInvaders testWorldran = new SpaceInvaders(this.player, this.alienshiptest3,
      this.startBullets, this.startBullets, new Random(3));
  SpaceInvaders testWorldranNew = new SpaceInvaders(this.player, this.alienshiptest3,
      this.startBullets, this.alienBulletran, new Random(3));
  IList<ICartPt> alienBulletran = new ConsList<ICartPt>(new AlienBullet(50, 13, false), this.mtct);

  // onTick Helper (line 419)
  boolean testTickHelper419(Tester t) {
    return t
        .checkExpect(this.playerbulletstest.map(new UpdateHitObject(this.alienshiptest))
            .filter(new BulletHit()), this.mtct)
        && t.checkExpect(
            this.playerbulletstest2.map(new UpdateHitObject(this.alienshiptest))
                .filter(new BulletHit()),
            new ConsList<ICartPt>(new PlayerBullet(400, 250, false), this.mtct))
        && t.checkExpect(this.playerbulletstest0.map(new UpdateHitObject(this.alienshiptest0))
            .filter(new BulletHit()), this.mtct)
        && t.checkExpect(
            this.playerbulletstest3.map(new UpdateHitObject(this.alienshiptest2))
                .filter(new BulletHit()),
            new ConsList<ICartPt>(new PlayerBullet(400, 250, false), this.mtct));
  }

  Random randvalue = new Random(this.alienshiptest3.count(0));
  IList<ICartPt> ibrand1 = new ConsList<ICartPt>(new AlienBullet(50, 13, false), this.mtct);
  IList<ICartPt> ibrand2 = new ConsList<ICartPt>(new AlienBullet(50, 13, false),
      new ConsList<ICartPt>(new AlienBullet(50, 16, false), this.mtct));
  SpaceInvaders tickWorld = new SpaceInvaders(new PlayerShip(this.p00), this.alienshiptest3,
      this.mtct, this.ibrand1);
  SpaceInvaders tickWorld2 = new SpaceInvaders(new PlayerShip(this.p000), this.alienshiptest3,
      this.mtct, this.ibrand2);

  IList<ICartPt> alienbulletsran1 = new ConsList<ICartPt>(new AlienBullet(50, 13, false),
      this.mtct);
  IList<ICartPt> alienbulletsran2 = new ConsList<ICartPt>(new AlienBullet(100, 13, false),
      this.mtct);
  IList<ICartPt> alienbulletsran3 = new ConsList<ICartPt>(new AlienBullet(150, 13, false),
      this.mtct);
  IList<ICartPt> alienbulletsran4 = new ConsList<ICartPt>(new AlienBullet(100, 13, false),
      this.mtct);
  SpaceInvaders ranWorld = new SpaceInvaders(new PlayerShip(this.p0), this.alienshiptest3,
      this.startBullets, this.startBullets, new Random(3));
  SpaceInvaders ranWorld2 = new SpaceInvaders(new PlayerShip(this.p00), this.alienshiptest3,
      this.startBullets, this.alienbulletsran1, new Random(3));
  SpaceInvaders ranWorld3 = new SpaceInvaders(new PlayerShip(this.p0), this.alienshiptest3,
      this.startBullets, this.startBullets, new Random(8));
  SpaceInvaders ranWorld4 = new SpaceInvaders(new PlayerShip(this.p00), this.alienshiptest3,
      this.startBullets, this.alienbulletsran2, new Random(8));
  SpaceInvaders ranWorld5 = new SpaceInvaders(new PlayerShip(this.p0), this.alienshiptest3,
      this.startBullets, this.startBullets, new Random(10));
  SpaceInvaders ranWorld6 = new SpaceInvaders(new PlayerShip(this.p00), this.alienshiptest3,
      this.startBullets, this.alienbulletsran3, new Random(10));
  SpaceInvaders ranWorld7 = new SpaceInvaders(new PlayerShip(this.p0), this.alienshiptest3,
      this.startBullets, this.startBullets, new Random(39));
  SpaceInvaders ranWorld8 = new SpaceInvaders(new PlayerShip(this.p00), this.alienshiptest3,
      this.startBullets, this.alienbulletsran4, new Random(39));

  // test onTick
  boolean testOnTickTester(Tester t) {
    return t.checkExpect(this.ranWorld.onTick(), this.ranWorld2)
        && t.checkExpect(this.ranWorld3.onTick(), this.ranWorld4)
        && t.checkExpect(this.ranWorld5.onTick(), this.ranWorld6)
        && t.checkExpect(this.ranWorld7.onTick(), this.ranWorld8);

  }

  // test onKey
  boolean testOnKey(Tester t) {
    return t.checkExpect(this.smallWorld.onKeyEvent("left"),
        new SpaceInvaders(new PlayerShip(new ShipPt(250, 450, false)), this.smallAList,
            this.startBullets, this.startBullets))
        && t.checkExpect(this.smallWorld11.onKeyEvent("left"), this.smallWorld11)
        && t.checkExpect(this.smallWorld.onKeyEvent("right"), this.smallWorld)
        && t.checkExpect(this.smallWorld11.onKeyEvent("right"),
            new SpaceInvaders(new PlayerShip(new ShipPt(50, 3, true)), this.smallAList,
                this.startBullets, this.startBullets))
        && t.checkExpect(this.smallWorld.onKeyEvent(" "),
            new SpaceInvaders(new PlayerShip(new ShipPt(250, 450, true)), this.smallAList,
                this.addpb, this.startBullets))
        && t.checkExpect(this.smallWorld.onKeyEvent(" "),
            new SpaceInvaders(new PlayerShip(new ShipPt(250, 450, true)), this.smallAList,
                this.addpb, this.startBullets))
        && t.checkExpect(this.smallWorld7.onKeyEvent(" "), this.smallWorld75)
        && t.checkExpect(this.smallWorld8.onKeyEvent(" "), this.smallWorld8);
  }

  // test WorldEnd
  boolean testWorldEnd(Tester t) {
    return t.checkExpect(this.smallWorld3.worldEnds(), new WorldEnd(true, new WorldScene(500, 500)))
        && t.checkExpect(this.smallWorld2.worldEnds(),
            new WorldEnd(false, this.player.placeSpaceShip(blank)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
                .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10)))
        && t.checkExpect(this.smallWorld4.worldEnds(),
            new WorldEnd(true, new WorldScene(500, 500)));
  }

  // test IPredicate<ICartPt> SameArea
  boolean testSameAreaPredicate(Tester t) {
    return t.checkExpect(this.points.ormap(new SameArea(this.p1)), true)
        && t.checkExpect(this.points.ormap(new SameArea(this.p40)), false)
        && t.checkExpect(this.points.andmap(new SameArea(this.p1)), false)
        && t.checkExpect(this.points.ormap(new SameArea(this.p15)), true)
        && t.checkExpect(this.points.ormap(new SameArea(this.p22)), true)
        && t.checkExpect(this.points.ormap(new SameArea(this.test1)), false)
        && t.checkExpect(this.points.ormap(new SameArea(this.test2)), false);
  }

  // test andmap (SameArea)
  boolean testSameAreaAndMap(Tester t) {
    return t.checkExpect(this.pointsFil.andmap(new SameArea(this.p1)), true)
        && t.checkExpect(this.points.andmap(new SameArea(this.p40)), false)
        && t.checkExpect(this.points.andmap(new SameArea(this.p1)), false)
        && t.checkExpect(this.pointsFil.andmap(new SameCartPt(this.p1)), true);
  }

  // test IPredicate<ICartPt> UpdateHitObject
  boolean testUpdateHitObject(Tester t) {
    return t.checkExpect(new UpdateHitObject(this.alienList).apply(this.pb4),
        new PlayerBullet(150, 10, true))
        && t.checkExpect(new UpdateHitObject(this.alienList).apply(this.pb3), this.pb3)
        && t.checkExpect(new UpdateHitObject(this.smallPlayer).apply(this.ib3),
            new AlienBullet(250, 450, true))
        && t.checkExpect(new UpdateHitObject(this.smallPlayer).apply(this.ib1), this.ib1)
        && t.checkExpect(new UpdateHitObject(this.smallPlayer).apply(this.p1), this.p1)
        && t.checkExpect(new UpdateHitObject(this.alienList).apply(this.p11), this.p11);
  }

  // test IPredicate<ICartPt> BulletHit
  boolean testBulletHit(Tester t) {
    return t.checkExpect(new BulletHit().apply(p1), true)
        && t.checkExpect(new BulletHit().apply(p2), true)
        && t.checkExpect(new BulletHit().apply(pb1), true)
        && t.checkExpect(new BulletHit().apply(pb11), false)
        && t.checkExpect(new BulletHit().apply(ib1), true)
        && t.checkExpect(new BulletHit().apply(ib2), false);
  }

  // test IPredicate<CartPt> SpaceShipHit
  boolean testSpaceShipHit(Tester t) {
    return t.checkExpect(new SpaceShipHit(this.points).apply(this.player), true)
        && t.checkExpect(new SpaceShipHit(this.points).apply(this.as1), false)
        && t.checkExpect(new SpaceShipHit(this.points).apply(this.as11), false)
        && t.checkExpect(new SpaceShipHit(this.points).apply(this.as25), false)
        && t.checkExpect(new SpaceShipHit(this.pointsFil).apply(this.as1), false)
        && t.checkExpect(new SpaceShipHit(this.pointsFil).apply(this.player), true)
        && t.checkExpect(new SpaceShipHit(this.pointsFilter2).apply(this.player), true)
        && t.checkExpect(new SpaceShipHit(this.addib3).apply(this.player), false);
  }

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
        && t.checkExpect(this.startBullets.add(this.pb1), this.addpb)
        && t.checkExpect(this.mtss.add(this.as1), new ConsList<ISpaceShip>(this.as1, this.mtss));
  }

  // test count
  boolean testCountList(Tester t) {
    return t.checkExpect(this.smallAList.count(0), 3) && t.checkExpect(this.alienList.count(0), 36)
        && t.checkExpect(this.smallAList2.count(0), 4)
        && t.checkExpect(new MtList<ICartPt>().count(0), 0);
  }

  // test CartPtOffscreen IPredicate<CartPt> and other filter use
  boolean testFilter(Tester t) {
    return t.checkExpect(this.points.filter(new CartPtOffscreen()), this.points)
        && t.checkExpect(this.pointsFilter.filter(new CartPtOffscreen()), this.pointsFil)
        && t.checkExpect(this.pointsFilter2.filter(new CartPtOffscreen()), this.pointsFil)
        && t.checkExpect(this.mtct.filter(new CartPtOffscreen()), this.mtct)
        && t.checkExpect(this.smallAList.filter(new SpaceShipHit(this.mtct)), this.smallAList)
        && t.checkExpect(this.smallAList.filter(new SpaceShipHit(this.addpb4)), this.smallAList)
        && t.checkExpect(this.smallAList.filter(new SpaceShipHit(this.addpb5)),
            new ConsList<ISpaceShip>(as2, new ConsList<ISpaceShip>(as3, new MtList<ISpaceShip>())));
  }

  // test IMoveCartPt IFunction<> (MAP
  boolean testMapIMoveCartPt(Tester t) {
    return t.checkExpect(this.mtct.map(new MoveICartPt()), new MtList<ICartPt>())
        && t.checkExpect(this.addpb3.map(new MoveICartPt()),
            new ConsList<ICartPt>(new PlayerBullet(250, 444, false),
                new ConsList<ICartPt>(new PlayerBullet(250, 447, false), startBullets)))
        && t.checkExpect(this.ablist.map(new MoveICartPt()),
            new ConsList<ICartPt>(new AlienBullet(100, 103, false),
                new ConsList<ICartPt>(new AlienBullet(100, 504, false),
                    new ConsList<ICartPt>(new AlienBullet(100, 2, false), this.mtct))));
  }

  // test GetSpaceShipCoordinates IFunction<> Map
  boolean testGetSpaceShipCoordinates(Tester t) {
    return t.checkExpect(this.mtss.map(new GetCoordinateSpaceShip()), this.mtct)
        && t.checkExpect(this.alienList.map(new GetCoordinateSpaceShip()), this.points)
        && t.checkExpect(this.smallAList.map(new GetCoordinateSpaceShip()), this.ablist2);
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

  // test addBullets <foldr>
  boolean testAddPlayerBullets(Tester t) {
    return t.checkExpect(this.startBullets.foldr(new AddBullets(), blank), blank)
        && t.checkExpect(this.addpb.foldr(new AddBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.addpb3.foldr(new AddBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 447)
                .placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.addib.foldr(new AddBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 200, 10))
        && t.checkExpect(this.addib4.foldr(new AddBullets(), blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 200, 10)
                .placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 250, 450));
  }

  IPredicate<ICartPt> cartOffScreen = new CartPtOffscreen();
  IPredicate<ICartPt> sameCartPT = new SameCartPt(p1);
  IPredicate<ICartPt> cartContainedIn = new CartPtContainedIn(this.points);
  IPredicate<ICartPt> sameArea = new SameArea(this.p0);

  IFunction<ICartPt, ICartPt> moveCart = new MoveICartPt();
  BiFunction<ISpaceShip, WorldScene, WorldScene> createShips = new CreateAllShips();
  BiFunction<ICartPt, WorldScene, WorldScene> addPlayerBullets = new AddBullets();

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
        && t.checkExpect(this.moveCart.apply(al1), new AlienBullet(100, 103, false))
        && t.checkExpect(this.moveCart.apply(pb1), new PlayerBullet(250, 447, false))
        && t.checkExpect(this.createShips.apply(this.player, this.blank),
            blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 250,
                450))
        && t.checkExpect(this.createShips.apply(as1, blank),
            blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10))
        && t.checkExpect(this.createShips.apply(player2, blank),
            blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 50, 3))
        && t.checkExpect(this.addPlayerBullets.apply(al1, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.red), 100, 100))
        && t.checkExpect(this.addPlayerBullets.apply(pb1, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 450))
        && t.checkExpect(this.addPlayerBullets.apply(this.pb2, blank),
            blank.placeImageXY(new CircleImage(5, OutlineMode.SOLID, Color.black), 250, 501))
        && t.checkExpect(this.sameArea.apply(p0), true)
        && t.checkExpect(this.sameArea.apply(test9), true)
        && t.checkExpect(this.sameArea.apply(p1), false)
        && t.checkExpect(this.sameArea.apply(p40), false)
        && t.checkExpect((new GetCoordinateSpaceShip().apply(as1)), this.p1)
        && t.checkExpect((new GetCoordinateSpaceShip().apply(this.player)), this.p0)
        && t.checkExpect((new GetCoordinateSpaceShip().apply(as10)), this.p10)
        && t.checkExpect((new GetCoordinateSpaceShip().apply(this.player2)), this.pt5);

  }

  // test drawSpaceShip on the PlayerShip class
  boolean testDrawSpaceShipPlayer(Tester t) {
    return t.checkExpect(this.player.drawSpaceShip(),
        new RectangleImage(50, 25, OutlineMode.SOLID, Color.black));
  }
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

  // test produceBullets
  boolean testProduceBullets(Tester t) {
    return t.checkExpect(this.alienList.produceBullet(5, 0), as6)
        && t.checkExpect(this.alienList.produceBullet(6, 0), as7)
        && t.checkExpect(this.alienList.produceBullet(22, 0), as23)
        && t.checkExpect(this.alienList.produceBullet(14, 0), as15)
        && t.checkExpect(this.alienList.produceBullet(10, 0), as11)
        && t.checkException(
            new IllegalArgumentException("Invalid Index - no invader found at given index"),
            this.mtss, "produceBullet", 5, 0);
  }

  // test isEmpty
  boolean testIsEmpty(Tester t) {
    return t.checkExpect(this.mtct.isEmpty(), true) && t.checkExpect(this.mtss.isEmpty(), true)
        && t.checkExpect(this.points.isEmpty(), false)
        && t.checkExpect(this.alienList.isEmpty(), false);

  }

}