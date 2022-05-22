import tester.Tester;

// to represent a place
class Place {
  String name;
  ILoFeature features;
  int age;

  // constructor
  Place(String name, ILoFeature features) {
    this.name = name;
    this.features = features;
  }

  // computes the average rating of all the restaurants' ratings, for all the
  // restaurants reachable from the current place
  double foodinessRating() {
    if (features.totalRest() == 0) {
      return 0.0;
    }
    else {
      return (features.totalRating() / features.totalRest());
    }
  }

  // produces one String that has in it all names of restaurants reachable from a
  // place, their food types in parentheses
  String restaurantInfo() {
    return (features.loRestInfo());
  }

  /*
   * The foodinessRating and restaurantInfo methods double count ratings and
   * restaurants because of the recursive nature of the method. A rating or
   * restaurant is duplicated when it is first counted in a Place but can be
   * listed again if a shuttle bus also goes to that destination as well. All the
   * LoFeatures methods can lead to double counting: totalRest, totalRating, and
   * loRestInfo all can double count when adding the counters/sums when the place
   * includes a shuttlebus as a feature, it may double count a restaurant that was
   * already counted. Double count also occurs in the implementation of all
   * shuttle methods because it calls methods on the destination which is a
   * LoFeatures.
   */
}

// to represent a list of feature
interface IFeature {
  // count of restaurant 1 if of type restaurant and 0 if not
  int restCount();

  // rating of restaurant
  double rating();

  // represents the information of one restaurant
  String oneRestInfo();
}

// to represent a restaurant
class Restaurant implements IFeature {
  String name;
  String type;
  double averageRating;

  // constructor
  Restaurant(String name, String type, double averageRating) {
    this.name = name;
    this.type = type;
    this.averageRating = averageRating;
  }

  public int restCount() {
    return 1;
  }

  public double rating() {
    return this.averageRating;
  }

  public String oneRestInfo() {
    return name + " (" + type + ")";
  }
}

// to represent a venue
class Venue implements IFeature {
  String name;
  String type;
  int capacity;

  // constructor
  Venue(String name, String type, int capacity) {
    this.name = name;
    this.type = type;
    this.capacity = capacity;
  }

  public int restCount() {
    return 0;
  }

  public double rating() {
    return 0.0;
  }

  public String oneRestInfo() {
    return "";
  }
}

//to represent a shuttle bus
class ShuttleBus implements IFeature {
  String name;
  Place destination;

  // constructor
  ShuttleBus(String name, Place destination) {
    this.name = name;
    this.destination = destination;
  }

  public int restCount() {
    return destination.features.totalRest();
  }

  public double rating() {
    return destination.features.totalRating();
  }

  public String oneRestInfo() {
    return destination.features.loRestInfo();
  }
}

// represents a list of features 
interface ILoFeature {
  // represents the total number of restaurants reachable from feature
  int totalRest();

  // represents the total rating of the restaurants reachable from feature
  double totalRating();

  // represents the restaurant information of the list of features
  String loRestInfo();
}

class MtLoFeature implements ILoFeature {
  MtLoFeature() {
  }

  public int totalRest() {
    return 0;
  }

  public double totalRating() {
    return 0.0;
  }

  public String loRestInfo() {
    return "";
  }
}

class ConsLoFeature implements ILoFeature {
  IFeature first;
  ILoFeature rest;

  // constructor
  ConsLoFeature(IFeature first, ILoFeature rest) {
    this.first = first;
    this.rest = rest;
  }

  public int totalRest() {
    return first.restCount() + rest.totalRest();
  }

  public double totalRating() {
    return first.rating() + rest.totalRating();
  }

  public String loRestInfo() {
    if (!first.oneRestInfo().equals("") && !rest.loRestInfo().equals("")) {
      return first.oneRestInfo() + ", " + rest.loRestInfo();
    }
    else {
      return first.oneRestInfo() + rest.loRestInfo();
    }

  }
}

/*
 * A place named "South End" with the following features: - A "Wedding" venue
 * named "Floral Place" with 2000 person capacity
 * 
 * A place named "Downtown" with the following features: - An "Italian"
 * restaurant named "Giacomo's", with rating of 4.3 stars - The "Red" shuttle
 * bus to "Boston Common" - The "Orange" shuttle to South End
 * 
 * A place named "Back Bay" with the following features: - A "Mexican"
 * restaurant named "El Jefe's" with rating of 4.2 stars - The "Green" shuttle
 * bus to "Downtown" - The "Blue" shuttle bus to "South End"
 */

//to represent examples and tests for people and their pets 
class ExamplesPlaces {

  // First map
  Place noWhere = new Place("No Where", new MtLoFeature());

  IFeature floral = new Venue("Floral Place", "Wedding", 2000);
  ILoFeature fList1 = new ConsLoFeature(floral, new MtLoFeature());
  Place southEnd = new Place("South End", fList1);

  IFeature r1 = new Restaurant("Giacomo's", "Italian", 4.3);
  IFeature red = new ShuttleBus("Red", this.southEnd);
  IFeature orange = new ShuttleBus("Orange", southEnd);
  ILoFeature fList2 = new ConsLoFeature(r1,
      new ConsLoFeature(red, new ConsLoFeature(orange, new MtLoFeature())));
  Place downtown = new Place("downtown", fList2);

  IFeature elJefes = new Restaurant("El Jefe's", "Mexican", 4.2);
  IFeature green = new ShuttleBus("Green", downtown);
  IFeature blue = new ShuttleBus("Blue", southEnd);
  ILoFeature fList3 = new ConsLoFeature(elJefes,
      new ConsLoFeature(green, new ConsLoFeature(blue, new MtLoFeature())));
  Place backBay = new Place("Back Bay", fList3);

  // Second map
  IFeature tdGarden = new Venue("TD Garden", "stadium", 19580);
  IFeature dailyCatch = new Restaurant("The Daily Catch", "Sicilian", 4.4);
  ILoFeature features3 = new ConsLoFeature(tdGarden,
      new ConsLoFeature(dailyCatch, new MtLoFeature()));
  Place northEnd = new Place("North End", features3);

  IFeature freshman15 = new ShuttleBus("Freshmen-15", northEnd);
  IFeature borderCafe = new Restaurant("Border Cafe", "Tex-Mex", 4.5);
  IFeature harvardStadium = new Venue("Harvard Stadium", "football", 30323);
  ILoFeature features4 = new ConsLoFeature(freshman15,
      new ConsLoFeature(borderCafe, new ConsLoFeature(harvardStadium, new MtLoFeature())));
  Place harvard = new Place("Harvard", features4);

  IFeature littleItaly = new ShuttleBus("Little Italy Express", this.northEnd);
  IFeature reginas = new Restaurant("Regina's Pizza", "pizza", 4.0);
  IFeature crimson = new ShuttleBus("Crimson Cruiser", this.harvard);
  IFeature bostonCommon = new Venue("Boston Common", "public", 150000);
  ILoFeature features2 = new ConsLoFeature(littleItaly, new ConsLoFeature(reginas,
      new ConsLoFeature(crimson, new ConsLoFeature(bostonCommon, new MtLoFeature()))));
  Place southStation = new Place("South Station", features2);

  IFeature teriyaki = new Restaurant("Sarku Japan", "teriyaki", 3.9);
  IFeature coffee = new Restaurant("Starbucks", "coffee", 4.1);
  IFeature bridge = new ShuttleBus("bridge shuttle", this.southStation);
  ILoFeature features1 = new ConsLoFeature(teriyaki,
      new ConsLoFeature(coffee, new ConsLoFeature(bridge, new MtLoFeature())));
  Place cambridgeSide = new Place("CambridgeSide Galleria", features1);

  // Tests for IFeatures
  // test for number of restaurants reachable from feature
  boolean testRestCount(Tester t) {
    return t.checkExpect(this.teriyaki.restCount(), 1) && t.checkExpect(this.bridge.restCount(), 4);
  }

  // rating of restaurant
  boolean testRating(Tester t) {
    return t.checkExpect(this.coffee.rating(), 4.1) && t.checkExpect(this.teriyaki.rating(), 3.9);
  }

  // represents the information of one restaurant
  boolean oneRestInfo(Tester t) {
    return t.checkExpect(this.freshman15.oneRestInfo(), "The Daily Catch (Sicilian)")
        && t.checkExpect(this.crimson.oneRestInfo(),
            "The Daily Catch (Sicilian), Border Cafe (Tex-Mex)");
  }

  // Tests for ILoFeature
  // test restaurant count method
  boolean testTotalRest(Tester t) {
    return t.checkExpect(this.northEnd.features.totalRest(), 1)
        && t.checkExpect(this.harvard.features.totalRest(), 2)
        && t.checkExpect(this.southStation.features.totalRest(), 4)
        && t.checkExpect(this.cambridgeSide.features.totalRest(), 6);
  }

  // test restaurant total rating method
  boolean testTotalRating(Tester t) {
    return t.checkInexact(this.northEnd.features.totalRating(), 4.4, 0.001)
        && t.checkInexact(this.harvard.features.totalRating(), 8.9, 0.001)
        && t.checkInexact(this.southStation.features.totalRating(), 17.3, 0.001)
        && t.checkInexact(this.cambridgeSide.features.totalRating(), 25.3, 0.1);
  }

  // test list of restaurant info method
  boolean testLoRestInfo(Tester t) {
    return t.checkExpect(this.northEnd.features.loRestInfo(), "The Daily Catch (Sicilian)")
        && t.checkExpect(this.harvard.features.loRestInfo(),
            "The Daily Catch (Sicilian), Border Cafe (Tex-Mex)");
  }

  // tests for Places
  // test restaurant foodiness rating method
  boolean testFoodinessRating(Tester t) {
    return t.checkInexact(this.northEnd.foodinessRating(), 4.4, 0.01)
        && t.checkInexact(this.harvard.foodinessRating(), 4.45, 0.1)
        && t.checkInexact(this.southStation.foodinessRating(), 4.325, 0.1)
        && t.checkInexact(this.cambridgeSide.foodinessRating(), 4.21667, 0.1)
        && t.checkInexact(this.noWhere.foodinessRating(), 0.0, 0.0001);
  }

  // test restaurant info method
  boolean testRestaurantInfo(Tester t) {
    return t.checkExpect(this.cambridgeSide.restaurantInfo(),
        "Sarku Japan (teriyaki), Starbucks (coffee), The Daily Catch (Sicilian), Regina's Pizza "
            + "(pizza), The Daily Catch (Sicilian), Border Cafe (Tex-Mex)");
  }

}
