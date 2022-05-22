// to represent an ice cream
interface IIceCream{ }
 
// to represent an empty serving 
class EmptyServing implements IIceCream {
  Boolean cone; 
  
  // empty serving constructor
  EmptyServing(Boolean cone) {
    this.cone = cone;
  }
}
 
// to represent a scooped ice cream 
class Scooped implements IIceCream {
  IIceCream more; 
  String flavor; 
 
  // scooped constructor
  Scooped(IIceCream more, String flavor) {
    this.more = more; 
    this.flavor = flavor; 
  }
}

// to represent examples and tests for ice cream 
class ExamplesIceCream {
  ExamplesIceCream() {}
  
  IIceCream order1_cone = new EmptyServing(false);
  IIceCream order1_scoop1 = new Scooped(this.order1_cone, "mint chip");
  IIceCream order1_scoop2 = new Scooped(this.order1_scoop1, "coffee");
  IIceCream order1_scoop3 = new Scooped(this.order1_scoop2, "black raspberry");
  IIceCream order1 = new Scooped(this.order1_scoop3, "caramel swirl");

  IIceCream order2_cone = new EmptyServing(true);
  IIceCream order2_scoop1 = new Scooped(this.order2_cone, "chocolate");
  IIceCream order2_scoop2 = new Scooped(this.order2_scoop1, "vanilla");
  IIceCream order2 = new Scooped(this.order2_scoop2, "strawberry");
}
