import tester.Tester;

//represents a ternary tree
interface ITree {
  // determines whether the given value appears in this ITree
  boolean contains(String val);

  // puts all of the left values in this ITree in a list of Strings
  ILoString allLeft();

  ILoString allLeftHelper(ILoString sofar);

  String value();
}

//represents a leaf in a ternary tree
class Leaf implements ITree {
  Leaf() {
  }

  // determines whether the given value appears in this ITree
  public boolean contains(String val) {
    return false;
  }

  // puts all of the left values in this ITree in a list of Strings
  public ILoString allLeft() {
    return new MtLoString();
  }

  public ILoString allLeftHelper(ILoString sofar) {
    return sofar;
  }

  public String value() {
    return "";
  }
}

//represents a node in a ternary tree
class Node implements ITree {
  String val;
  ITree left;
  ITree mid;
  ITree right;

  Node(String val, ITree left, ITree mid, ITree right) {
    this.val = val;
    this.left = left;
    this.mid = mid;
    this.right = right;
  }

  // determines whether the given value appears in this ITree
  public boolean contains(String val) {
    if (this.val == val) {
      return true;
    }
    else {
      return this.left.contains(val) || this.mid.contains(val) || this.right.contains(val);
    }
  }

  public String value() {
    return this.val;
  }

  //  public ILoString allLeftHelper(ILoString sofar) {
  //    return new ConsLoString(this.value(), new ConsLoString(this.left.value(),
  //        new ConsLoString(this.mid.value(), new ConsLoString(this.right.value(), sofar))));
  //  }
  //
  //  public ILoString allLeft() {
  //    return this.allLeftHelper(this.allLeft());

  //  public ILoString allLeftHelper(ILoString sofar) {
  //    return new ConsLoString(this.val, this.allLeft());
  //  }

  public ILoString allLeftHelper(ILoString sofar) {
    return new ConsLoString(this.val, sofar);
  }

  // puts all of the left values in this ITree in a list of Strings
  public ILoString allLeft() {
    return new ConsLoString(this.val,
        left.allLeftHelper(mid.allLeftHelper(right.allLeftHelper(new MtLoString()))));
  }
}

class ExamplesTrees {
  //here are some examples you can use
  ITree lf = new Leaf();
  ITree tree1 = new Node("a1", lf, lf, lf);
  ITree tree2 = new Node("b1", lf, lf, lf);
  ITree tree3 = new Node("c1", lf, lf, lf);

  ITree tree4 = new Node("a2", lf, lf, lf);
  ITree tree5 = new Node("b2", lf, lf, lf);
  ITree tree6 = new Node("c2", lf, lf, lf);

  ITree tree7 = new Node("a3", lf, lf, lf);
  ITree tree8 = new Node("b3", lf, lf, lf);
  ITree tree9 = new Node("c3", lf, lf, lf);

  ITree tree10 = new Node("a4", tree1, tree2, tree3);
  ITree tree11 = new Node("b4", tree4, tree5, tree6);
  ITree tree12 = new Node("c4", tree7, tree8, tree9);

  ITree tree13 = new Node("a5", tree10, tree11, tree12);

  // test contains method 
  boolean testContains(Tester t) {
    return t.checkExpect(this.tree13.contains("a3"), true)
        && t.checkExpect(this.tree13.contains("a1"), true)
        && t.checkExpect(this.tree12.contains("a2"), false)
        && t.checkExpect(this.tree3.contains("c1"), true)
        && t.checkExpect(this.tree5.contains("a1"), false);
  }

  // test for allLeft method 
  boolean testAllLeft(Tester t) {
    return t.checkExpect(this.tree10.allLeft(),
        new ConsLoString("a4",
            new ConsLoString("a1",
                new ConsLoString("b1", new ConsLoString("c1", new MtLoString())))))
        && t.checkExpect(this.tree13.allLeft(),
            new ConsLoString("a5",
                new ConsLoString("a4",
                    new ConsLoString("b4", new ConsLoString("c4", new ConsLoString("a1",
                        new ConsLoString("b1", new ConsLoString("c1", new MtLoString()))))))));
  }
}
