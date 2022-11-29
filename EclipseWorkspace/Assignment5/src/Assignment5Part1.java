//import java.awt.Color;
//
//import javalib.funworld.WorldScene;
//import tester.Tester;
//
//// represents Predicates
//interface IPredicate<T> {
//  // determine if function on type T is true 
//  boolean apply(T t);
//}
//
//// SameCartPt Predicate
//class SameCartPt implements IPredicate<ICartPt> {
//  ICartPt point;
//
//  SameCartPt(ICartPt point) {
//    this.point = point;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.point ...                -- CartPt
//  Methods: 
//  ... this.apply(CartPt) ...        -- boolean 
//  Methods for fields: 
//  ... this.point.equalCartPt(CartPt) ...       -- boolean
//  */
//
//  // determine if given point is equal to this point 
//  public boolean apply(ICartPt given) {
//    return point.equalCartPt(given);
//  }
//}
//
//// CartContainedIn Predicate
//class CartPtContainedIn implements IPredicate<ICartPt> {
//  IList<ICartPt> points;
//
//  CartPtContainedIn(IList<ICartPt> points) {
//    this.points = points;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.points ...                -- IList<CartPt>
//  Methods: 
//  ... this.apply(CartPt) ...        -- boolean 
//  Methods for fields: 
//  ... this.point.equalCartPt(CartPt) ...       -- boolean
//  */
//
//  // determine if the given point is contained in this list of points 
//  public boolean apply(ICartPt t) {
//    return points.ormap(new SameCartPt(t));
//  }
//}
//
//////CartContainedIn Predicate
////class MoveICartPt implements IFunction<ICartPt, ICartPt> {
////  boolean direction;
////
////  MoveICartPt(boolean direction) {
////    this.direction = direction;
////  }
////
////  /*  TEMPLATE 
////  Fields:
////  ... this.points ...                -- IList<CartPt>
////  Methods: 
////  ... this.apply(CartPt) ...        -- boolean 
////  Methods for fields: 
////  ... this.point.equalCartPt(CartPt) ...       -- boolean
////  */
////
////  // determine if the given point is contained in this list of points 
////  public ICartPt apply(ICartPt point) {
////    return point.move(direction);
////  }
////}
//
////---------------------------------------------------------
//
//// interface for MAP
//interface IFunction<A, R> {
//  R apply(A t);
//}
//
//// interface for FOLDR
//interface BiFunction<A1, A2, R> {
//  R apply(A1 a1, A2 a2);
//}
//
//// creates WorldScene with all the SpaceShips
//class createAllShips implements BiFunction<ISpaceShip, WorldScene, WorldScene> {
//
//  /*  TEMPLATE  
//  Methods: 
//    ... this.apply(ISpaceShip, WorldScene) ...        -- WorldScene 
//  */
//  public WorldScene apply(ISpaceShip ship, WorldScene space) {
//    return ship.placeSpaceShip(space);
//  }
//}
//
//// represents a generic list
//interface IList<T> {
//
//  // MAP FUNCTION
//  <R> IList<R> map(IFunction<T, R> function);
//
//  // FOLDR FUNCTION
//  <R> R foldr(BiFunction<T, R, R> function, R base);
//
//  // ORMAP FUNCTION
//  boolean ormap(IPredicate<T> predicate);
//
//  // ANDMAP FUNCTION
//  boolean andmap(IPredicate<T> predicate);
//
//  // FILTER FUNCTION 
//  IList<T> filter(IPredicate<T> predicate);
//}
//
//// generic empty list
//class MtList<T> implements IList<T> {
//
//  /*  TEMPLATE 
//  Methods: 
//  ... this.map(IFunction<T, R>) ...                  -- IList<R> 
//  ... this.foldr(BiFunction<T, R, R>, R) ...         -- IList<R> 
//  ... this.ormap(IPredicate<T>) ...                  -- boolean
//  ... this.andmap(IPredicate<T, R>) ...              -- boolean
//  ... this.filter(IPredicate<T>) ...                 -- IList<T> 
//  */
//
//  // MAP FUNCTION: Empty-List
//  public <R> IList<R> map(IFunction<T, R> function) {
//    return new MtList<R>();
//  }
//
//  // FOLDR FUNCTION: Empty-List
//  public <R> R foldr(BiFunction<T, R, R> function, R base) {
//    return base;
//  }
//
//  // ORMAP: Empty-List
//  public boolean ormap(IPredicate<T> predicate) {
//    return false;
//  }
//
//  // ANDMAP: Empty-List 
//  public boolean andmap(IPredicate<T> predicate) {
//    return true;
//  }
//
//  // FILTER
//  public IList<T> filter(IPredicate<T> predicate) {
//    return new MtList<T>();
//  }
//
//}
//
//// generic non-empty list
//class ConsList<T> implements IList<T> {
//  T first;
//  IList<T> rest;
//
//  ConsList(T first, IList<T> rest) {
//    this.first = first;
//    this.rest = rest;
//  }
//
//  /*  TEMPLATE 
//  Fields:
//  ... this.first ...                -- T
//  ... this.rest ...                 -- IList<T> 
//  Methods: 
//  ... this.map(IFunction<T, R>) ...                  -- IList<R> 
//  ... this.foldr(BiFunction<T, R, R>, R) ...         -- IList<R> 
//  ... this.ormap(IPredicate<T>) ...                  -- boolean
//  ... this.andmap(IPredicate<T, R>) ...              -- boolean
//  ... this.filter(IPredicate<T>) ...                 -- IList<T> 
//  Methods for fields:
//  ... this.rest.map(IFunction<T, R>) ...                  -- IList<R> 
//  ... this.rest.foldr(BiFunction<T, R, R>, R) ...         -- IList<R> 
//  ... this.rest.ormap(IPredicate<T>) ...                  -- boolean
//  ... this.rest.andmap(IPredicate<T, R>) ...              -- boolean
//  ... this.rest.filter(IPredicate<T>) ...                 -- IList<T> 
//  */
//
//  // MAP Function: ConsList
//  public <R> IList<R> map(IFunction<T, R> function) {
//    return new ConsList<R>(function.apply(this.first), this.rest.map(function));
//  }
//
//  // FOLDR FUNCTION: ConsList
//  public <R> R foldr(BiFunction<T, R, R> function, R base) {
//    return function.apply(this.first, this.rest.foldr(function, base));
//  }
//
//  // ORMAP FUNCTION: Conslist
//  public boolean ormap(IPredicate<T> predicate) {
//    return predicate.apply(this.first) || this.rest.ormap(predicate);
//  }
//
//  // ANDMAP FUNCTION: Conslist
//  public boolean andmap(IPredicate<T> predicate) {
//    return predicate.apply(this.first) && this.rest.ormap(predicate);
//  }
//
//  // FILTER FUNCTION: ConsList
//  public IList<T> filter(IPredicate<T> predicate) {
//    if (predicate.apply(this.first)) {
//      return new ConsList<T>(this.first, this.rest.filter(predicate));
//    }
//    else {
//      return this.rest.filter(predicate);
//    }
//  }
//}
//
////represents examples and tests for game
//class IListExamples {
//  IListExamples() {
//  }
//
//  WorldScene blank = new WorldScene(500, 500);
//
//  ICartPt p0 = new ShipPt(250, 450, true);
//  ISpaceShip player = new PlayerShip(p0, Color.black, 50, 25);
//
//  ICartPt p1 = new ShipPt(50, 10, true);
//  ICartPt p2 = new ShipPt(100, 10, true);
//  ICartPt p3 = new ShipPt(150, 10, true);
//  ICartPt p4 = new ShipPt(200, 10, true);
//  ICartPt p5 = new ShipPt(250, 10, true);
//  ICartPt p6 = new ShipPt(300, 10, true);
//  ICartPt p7 = new ShipPt(350, 10, true);
//  ICartPt p8 = new ShipPt(400, 10, true);
//  ICartPt p9 = new ShipPt(450, 10, true);
//
//  ICartPt p10 = new ShipPt(50, 40, true);
//  ICartPt p11 = new ShipPt(100, 40, true);
//  ICartPt p12 = new ShipPt(150, 40, true);
//  ICartPt p13 = new ShipPt(200, 40, true);
//  ICartPt p14 = new ShipPt(250, 40, true);
//  ICartPt p15 = new ShipPt(300, 40, true);
//  ICartPt p16 = new ShipPt(350, 40, true);
//  ICartPt p17 = new ShipPt(400, 40, true);
//  ICartPt p18 = new ShipPt(450, 40, true);
//
//  ICartPt p19 = new ShipPt(50, 70, true);
//  ICartPt p20 = new ShipPt(100, 70, true);
//  ICartPt p21 = new ShipPt(150, 70, true);
//  ICartPt p22 = new ShipPt(200, 70, true);
//  ICartPt p23 = new ShipPt(250, 70, true);
//  ICartPt p24 = new ShipPt(300, 70, true);
//  ICartPt p25 = new ShipPt(350, 70, true);
//  ICartPt p26 = new ShipPt(400, 70, true);
//  ICartPt p27 = new ShipPt(450, 70, true);
//
//  ICartPt p28 = new ShipPt(50, 100, true);
//  ICartPt p29 = new ShipPt(100, 100, true);
//  ICartPt p30 = new ShipPt(150, 100, true);
//  ICartPt p31 = new ShipPt(200, 100, true);
//  ICartPt p32 = new ShipPt(250, 100, true);
//  ICartPt p33 = new ShipPt(300, 100, true);
//  ICartPt p34 = new ShipPt(350, 100, true);
//  ICartPt p35 = new ShipPt(400, 100, true);
//  ICartPt p36 = new ShipPt(450, 100, true);
//
//  ISpaceShip as1 = new AlienShip(p1, Color.red, 15);
//  ISpaceShip as2 = new AlienShip(p2, Color.red, 15);
//  ISpaceShip as3 = new AlienShip(p3, Color.red, 15);
//  ISpaceShip as4 = new AlienShip(p4, Color.red, 15);
//  ISpaceShip as5 = new AlienShip(p5, Color.red, 15);
//  ISpaceShip as6 = new AlienShip(p6, Color.red, 15);
//  ISpaceShip as7 = new AlienShip(p7, Color.red, 15);
//  ISpaceShip as8 = new AlienShip(p8, Color.red, 15);
//  ISpaceShip as9 = new AlienShip(p9, Color.red, 15);
//
//  ISpaceShip as10 = new AlienShip(p10, Color.red, 15);
//  ISpaceShip as11 = new AlienShip(p11, Color.red, 15);
//  ISpaceShip as12 = new AlienShip(p12, Color.red, 15);
//  ISpaceShip as13 = new AlienShip(p13, Color.red, 15);
//  ISpaceShip as14 = new AlienShip(p14, Color.red, 15);
//  ISpaceShip as15 = new AlienShip(p15, Color.red, 15);
//  ISpaceShip as16 = new AlienShip(p16, Color.red, 15);
//  ISpaceShip as17 = new AlienShip(p17, Color.red, 15);
//  ISpaceShip as18 = new AlienShip(p18, Color.red, 15);
//
//  ISpaceShip as19 = new AlienShip(p19, Color.red, 15);
//  ISpaceShip as20 = new AlienShip(p20, Color.red, 15);
//  ISpaceShip as21 = new AlienShip(p21, Color.red, 15);
//  ISpaceShip as22 = new AlienShip(p22, Color.red, 15);
//  ISpaceShip as23 = new AlienShip(p23, Color.red, 15);
//  ISpaceShip as24 = new AlienShip(p24, Color.red, 15);
//  ISpaceShip as25 = new AlienShip(p25, Color.red, 15);
//  ISpaceShip as26 = new AlienShip(p26, Color.red, 15);
//  ISpaceShip as27 = new AlienShip(p27, Color.red, 15);
//
//  ISpaceShip as28 = new AlienShip(p28, Color.red, 15);
//  ISpaceShip as29 = new AlienShip(p29, Color.red, 15);
//  ISpaceShip as30 = new AlienShip(p30, Color.red, 15);
//  ISpaceShip as31 = new AlienShip(p31, Color.red, 15);
//  ISpaceShip as32 = new AlienShip(p32, Color.red, 15);
//  ISpaceShip as33 = new AlienShip(p33, Color.red, 15);
//  ISpaceShip as34 = new AlienShip(p34, Color.red, 15);
//  ISpaceShip as35 = new AlienShip(p35, Color.red, 15);
//  ISpaceShip as36 = new AlienShip(p36, Color.red, 15);
//
//  IList<ICartPt> points = new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
//      p1,
//      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
//                              p1,
//                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                                      new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                                          new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                                              new ConsList<ICartPt>(p1, new ConsList<ICartPt>(p1,
//                                                  new ConsList<ICartPt>(p1, new ConsList<ICartPt>(
//                                                      p1,
//                                                      new ConsList<ICartPt>(p1,
//                                                          new ConsList<ICartPt>(p1,
//                                                              new ConsList<ICartPt>(p1,
//                                                                  new MtList<ICartPt>()))))))))))))))))))))))))))))))))))));
//
//  IList<ISpaceShip> alienList = new ConsList<ISpaceShip>(as1, new ConsList<ISpaceShip>(as2,
//      new ConsList<ISpaceShip>(as3, new ConsList<ISpaceShip>(as4, new ConsList<ISpaceShip>(as5,
//          new ConsList<ISpaceShip>(as6, new ConsList<ISpaceShip>(as7, new ConsList<ISpaceShip>(as8,
//              new ConsList<ISpaceShip>(as9, new ConsList<ISpaceShip>(as10, new ConsList<ISpaceShip>(
//                  as11,
//                  new ConsList<ISpaceShip>(as12, new ConsList<ISpaceShip>(as13,
//                      new ConsList<ISpaceShip>(as14, new ConsList<ISpaceShip>(as15,
//                          new ConsList<ISpaceShip>(as16, new ConsList<ISpaceShip>(as17,
//                              new ConsList<ISpaceShip>(as18, new ConsList<ISpaceShip>(as19,
//                                  new ConsList<ISpaceShip>(as20, new ConsList<ISpaceShip>(as21,
//                                      new ConsList<ISpaceShip>(as22, new ConsList<ISpaceShip>(as23,
//                                          new ConsList<ISpaceShip>(as24, new ConsList<ISpaceShip>(
//                                              as25,
//                                              new ConsList<ISpaceShip>(as26,
//                                                  new ConsList<ISpaceShip>(as27,
//                                                      new ConsList<ISpaceShip>(as28,
//                                                          new ConsList<ISpaceShip>(as29,
//                                                              new ConsList<ISpaceShip>(as30,
//                                                                  new ConsList<ISpaceShip>(as31,
//                                                                      new ConsList<ISpaceShip>(as32,
//                                                                          new ConsList<ISpaceShip>(
//                                                                              as33,
//                                                                              new ConsList<ISpaceShip>(
//                                                                                  as34,
//                                                                                  new ConsList<ISpaceShip>(
//                                                                                      as35,
//                                                                                      new ConsList<ISpaceShip>(
//                                                                                          as36,
//                                                                                          new MtList<ISpaceShip>()))))))))))))))))))))))))))))))))))));
//
//  IList<ISpaceShip> smallAList = new ConsList<ISpaceShip>(as1,
//      new ConsList<ISpaceShip>(as2, new ConsList<ISpaceShip>(as3, new MtList<ISpaceShip>())));
//
//  SpaceInvaders world = new SpaceInvaders(this.player, this.alienList);
//  SpaceInvaders smallWorld = new SpaceInvaders(this.player, this.smallAList);
//
//  boolean testBigBang(Tester t) {
//    SpaceInvaders world = new SpaceInvaders(this.player, this.alienList);
//    int worldWidth = 500;
//    int worldHeight = 500;
//    double tickRate = 0.005;
//    return world.bigBang(worldWidth, worldHeight, tickRate);
//  }
//
//  ICartPt pt1 = new ShipPt(1, 1, true);
//  ICartPt pt2 = new ShipPt(1, 2, true);
//  ICartPt pt3 = new ShipPt(1, 3, true);
//  ICartPt pt4 = new ShipPt(1, 3, true);
//
//  //  IBullet pb1 = new PlayerBullet(5, Color.black, 10, 10, false);
//  //  IBullet ab1 = new AlienBullet(5, Color.red, 10, 10, true);
//
//  IList<ICartPt> pointList = new ConsList<ICartPt>(pt1,
//      new ConsList<ICartPt>(pt2, new MtList<ICartPt>()));
//
//  // test equalCartPt
//  boolean testEqualCartPt(Tester t) {
//    return t.checkExpect(this.pt1.equalCartPt(pt2), false)
//        && t.checkExpect(this.pt2.equalCartPt(pt1), false)
//        && t.checkExpect(this.pt3.equalCartPt(pt4), true)
//        && t.checkExpect(this.pt4.equalCartPt(pt3), true);
//  }
//
//  //    // test drawSpaceShip on the PlayerShip class
//  //    boolean testDrawSpaceShipPlayer(Tester t) {
//  //      return t.checkExpect(this.player.drawSpaceShip(),
//  //          new RectangleImage(50, 25, OutlineMode.SOLID, Color.black));
//  //    }
//  //  
//  //    // test drawSpaceShip on the AlienShip class
//  //    boolean testDrawSpaceShipAlien(Tester t) {
//  //      return t.checkExpect(this.as1.drawSpaceShip(),
//  //          new RectangleImage(15, 15, OutlineMode.SOLID, Color.red))
//  //          && t.checkExpect(this.as2.drawSpaceShip(),
//  //              new RectangleImage(15, 15, OutlineMode.SOLID, Color.red));
//  //    }
//  //  
//  //    // test placeSpaceShip on the PlayerShip and AlienShip class
//  //    boolean testPlaceSpaceShip(Tester t) {
//  //      return t.checkExpect(this.player.placeSpaceShip(blank),
//  //          blank.placeImageXY(new RectangleImage(50, 25, OutlineMode.SOLID, Color.black), 250, 450))
//  //          && t.checkExpect(this.as1.placeSpaceShip(blank),
//  //              blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10));
//  //    }
//  //  
//  //    // test PlayerBulletConstructorException
//  //    boolean testPBulletException(Tester t) {
//  //      return t.checkConstructorException(
//  //          new IllegalArgumentException(
//  //              "Invalid Bullet Direction - spaceship bullets must move from bottom to top"),
//  //          "PlayerBullet", 5, Color.black, 10, 10, false);
//  //  
//  //    }
//  //  
//  //    //test AlienBulletConstructorException
//  //    boolean testABulletException(Tester t) {
//  //      return t.checkConstructorException(
//  //          new IllegalArgumentException(
//  //              "Invalid Bullet Direction - invader bullets must move from top to bottom"),
//  //          "AlienBullet", 5, Color.red, 10, 10, true);
//  //  
//  //    }
//  //  
//  //    // test makeScene
//  //    boolean testMakeScene(Tester t) {
//  //      return t.checkExpect(this.smallWorld.makeScene(),
//  //          blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
//  //              .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
//  //              .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10));
//  //    }
//  //  
//  //    // test IPredicate<CartPt>
//  //    boolean testSameCartPtPredicate(Tester t) {
//  //      return t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt3)), false)
//  //          && t.checkExpect(this.pointList.ormap(new SameCartPt(this.pt2)), true);
//  //    }
//  //  
//  //    // test IPredicate<CartPt> that takes in list
//  //    boolean testCartContainedInPredicate(Tester t) {
//  //      return t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt1), true)
//  //          && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt2), true)
//  //          && t.checkExpect(new CartPtContainedIn(this.pointList).apply(pt3), false);
//  //    }
//  //  
//  //    // test createAllShips
//  //    boolean testCreateAllShips(Tester t) {
//  //      return t.checkExpect(this.smallAList.foldr(new createAllShips(), blank),
//  //          blank.placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 150, 10)
//  //              .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 100, 10)
//  //              .placeImageXY(new RectangleImage(15, 15, OutlineMode.SOLID, Color.red), 50, 10));
//  //    }
//
//}
