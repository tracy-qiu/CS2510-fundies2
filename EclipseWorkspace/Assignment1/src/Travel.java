// to represent housing
interface IHousing {
}

// to represent a hut 
class Hut implements IHousing {
  int capacity;
  int population;

  // hut constructor
  Hut(int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
  }
}

// to represent an inn 
class Inn implements IHousing {
  String name;
  int capacity;
  int population;
  int stalls;

  // inn constructor
  Inn(String name, int capacity, int population, int stalls) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
  }
}

//to represent a Castle  
class Castle implements IHousing {
  String name;
  String familyName;
  int population;
  int carriageHouse;

  // castle constructor
  Castle(String name, String familyName, int population, int carriageHouse) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}

// to represent transportation 
interface ITransportation {
}

// to represent a horse 
class Horse implements ITransportation {
  IHousing from;
  IHousing to;
  String name;
  String color;

  // horse constructor
  Horse(IHousing from, IHousing to, String name, String color) {
    this.from = from;
    this.to = to;
    this.name = name;
    this.color = color;
  }
}

// to represent a carriage 
class Carriage implements ITransportation {
  IHousing from;
  IHousing to;
  int tonnage;

  // carriage constructor
  Carriage(IHousing from, IHousing to, int tonnage) {
    this.from = from;
    this.to = to;
    this.tonnage = tonnage;
  }
}

// to represent examples and tests for traveling
class ExamplesTravel {
  ExamplesTravel() {
  }

  IHousing hovel = new Hut(5, 1);
  IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);
  IHousing nest = new Hut(9, 4);
  IHousing narnia = new Castle("Narnia", "Lion", 200, 4);
  IHousing breakfast = new Inn("Bed and Breakfast Inn", 50, 29, 8);

  ITransportation horse1 = new Horse(winterfell, hovel, "Ash", "brown");
  ITransportation horse2 = new Horse(crossroads, nest, "Amber", "black");
  ITransportation carriage1 = new Carriage(crossroads, winterfell, 10);
  ITransportation carriage2 = new Carriage(nest, narnia, 8);
}
