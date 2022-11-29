// 5/25/22 

import tester.Tester;

class Monomial {
  int degree;
  int coefficient;

  //constructor 
  Monomial(int degree, int coefficient) {
    this.degree = new Utils().checkDegrees(degree, "Invalid Degree: " + Integer.toString(degree));
    this.coefficient = coefficient;
  }

  // determines if coefficient is zero 
  boolean isZeroCoefficient() {
    return coefficient == 0;
  }

  // determines if this monomial and the given monomial are the same 
  boolean sameMonomial(Monomial other) {
    return (coefficient == other.coefficient && degree == other.degree);
  }
}

interface ILoMonomial {
  //checks if valid list of monomials - not valid if any monomials have the same degree or if a 
  // degree is negative 
  boolean isValidPolynomial();

  //checks if given monomial has the same degree as any other monomial in list 
  boolean isValidPolynomialHelper(Monomial given);

  // creates new list of monomials removing all zero coefficient monomials 
  // 3x^2 + 0x + 5 --> 3x^2 + 5
  ILoMonomial removeZeroCoefficient();

  // produces new list of monomials with other monomial inserted in order of degrees
  ILoMonomial insert(Monomial other);

  // sort list of monomials based on order of degrees 
  ILoMonomial sort();

  // determines if list of monomials are the same
  boolean sameILoMonomial(ILoMonomial other);

  // check if the first monomial of list is the same as the given monomial 
  boolean sameILoMonomialHelper(Monomial other);

  // get the rest of the list 
  ILoMonomial getRest();

  // checks if the list is empty 
  public boolean isEmpty();
}

class MtLoMonomial implements ILoMonomial {
  MtLoMonomial() {
  }

  public boolean isValidPolynomial() {
    return true;
  }

  public boolean isValidPolynomialHelper(Monomial given) {
    return true;
  }

  public ILoMonomial removeZeroCoefficient() {
    return new MtLoMonomial();
  }

  public ILoMonomial insert(Monomial other) {
    return new ConsLoMonomial(other, new MtLoMonomial());
  }

  public ILoMonomial sort() {
    return new MtLoMonomial();
  }

  public boolean isEmpty() {
    return true;
  }

  public boolean sameILoMonomial(ILoMonomial other) {
    return other.isEmpty();
  }

  public boolean sameILoMonomialHelper(Monomial other) {
    return false;
  }

  public ILoMonomial getRest() {
    return new MtLoMonomial();
  }
}

class ConsLoMonomial implements ILoMonomial {
  Monomial first;
  ILoMonomial rest;

  ConsLoMonomial(Monomial first, ILoMonomial rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean isValidPolynomial() {
    if (rest.isValidPolynomialHelper(first)) {
      return this.rest.isValidPolynomial();
    }
    else {
      return false;
    }

  }

  public boolean isValidPolynomialHelper(Monomial given) {
    if (this.first.degree == given.degree) {
      return false;
    }
    else {
      return this.rest.isValidPolynomialHelper(given);
    }
  }

  public ILoMonomial removeZeroCoefficient() {
    if (!this.first.isZeroCoefficient()) {
      return new ConsLoMonomial(this.first, this.rest.removeZeroCoefficient());
    }
    else {
      return rest.removeZeroCoefficient();
    }
  }

  public ILoMonomial insert(Monomial other) {
    if (this.first.degree > other.degree) {
      return new ConsLoMonomial(this.first, this.rest.insert(other));
    }
    else {
      return new ConsLoMonomial(other, this);
    }
  }

  public ILoMonomial sort() {
    return (this.rest.sort()).insert(this.first);
  }

  //is this list empty 
  public boolean isEmpty() {
    return false;
  }

  public ILoMonomial getRest() {
    return this.rest;
  }

  public boolean sameILoMonomial(ILoMonomial other) {
    if (other.sameILoMonomialHelper(first)) {
      return rest.sameILoMonomial(other.getRest());
    }
    else {
      return false;
    }
  }

  public boolean sameILoMonomialHelper(Monomial other) {
    return (this.first.sameMonomial(other));
  }
}

class Polynomial {
  ILoMonomial monomials;

  Polynomial(ILoMonomial monomials) {
    this.monomials = new Utils().checkRepeatDegs(monomials, "Invalid polynomial - repeat degrees");
  }

  boolean samePolynomial(Polynomial other) {
    return (this.monomials.sort().removeZeroCoefficient())
        .sameILoMonomial(other.monomials.sort().removeZeroCoefficient());
  }
}

class Utils {
  int checkDegrees(int degrees, String msg) {
    if (degrees < 0) {
      throw new IllegalArgumentException(msg);
    }
    else {
      return degrees;
    }
  }

  ILoMonomial checkRepeatDegs(ILoMonomial monomials, String msg) {
    if (!monomials.isValidPolynomial()) {
      throw new IllegalArgumentException(msg);
    }
    else {
      return monomials;
    }
  }
}

class ExamplesPolynomials {
  Monomial m0 = new Monomial(0, 0);
  Monomial m1 = new Monomial(1, 16);
  Monomial m2 = new Monomial(2, 7);
  Monomial m3 = new Monomial(3, 9);
  Monomial m3dupl = new Monomial(3, 2);

  ILoMonomial loMt = new MtLoMonomial();
  ILoMonomial loM1 = new ConsLoMonomial(m1, new MtLoMonomial());
  ILoMonomial loM2 = new ConsLoMonomial(m2, new ConsLoMonomial(m1, new MtLoMonomial()));
  ILoMonomial loM3 = new ConsLoMonomial(m3,
      new ConsLoMonomial(m2, new ConsLoMonomial(m1, new MtLoMonomial())));
  //  ILoMonomial loM3invalid = new ConsLoMonomial(m3,
  //      new ConsLoMonomial(m3dupl, new ConsLoMonomial(m1, new MtLoMonomial())));
  ILoMonomial loM3notOrdered1 = new ConsLoMonomial(m2,
      new ConsLoMonomial(m1, new ConsLoMonomial(m3, new MtLoMonomial())));
  ILoMonomial loM3notOrdered2 = new ConsLoMonomial(m1,
      new ConsLoMonomial(m2, new ConsLoMonomial(m3, new MtLoMonomial())));
  ILoMonomial zeroCoefficients = new ConsLoMonomial(m2,
      new ConsLoMonomial(m0, new ConsLoMonomial(m1, new MtLoMonomial())));

  Polynomial p0 = new Polynomial(loMt);
  Polynomial p1 = new Polynomial(loM1);
  Polynomial p2 = new Polynomial(loM2);
  Polynomial p3 = new Polynomial(loM3);
  //  Polynomial Invalid = new Polynomial(loM3invalid);

  Polynomial p3test = new Polynomial(loM3notOrdered1);
  Polynomial p3test2 = new Polynomial(loM3notOrdered2);
  Polynomial p2test = new Polynomial(zeroCoefficients);

  // test exceptions 
  boolean testCheckConstructorException(Tester t) {
    return t.checkConstructorException(new IllegalArgumentException("Invalid Degree: -5"),
        "Monomial", -5, 3)
        && t.checkConstructorException(new IllegalArgumentException("Invalid Degree: -1"),
            "Monomial", -1, 2)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid polynomial - repeat degrees"), "Polynomial",
            new ConsLoMonomial(m3, new ConsLoMonomial(m3dupl, new MtLoMonomial())));
  }

  boolean testIsZeroDegree(Tester t) {
    return t.checkExpect(this.m0.isZeroCoefficient(), true)
        && t.checkExpect(this.m1.isZeroCoefficient(), false);
  }

  boolean testIsValidPolynomial(Tester t) {
    return t.checkExpect(this.loMt.isValidPolynomial(), true)
        && t.checkExpect(this.loM1.isValidPolynomial(), true)
        && t.checkExpect(this.loM2.isValidPolynomial(), true)
        && t.checkExpect(this.loM3.isValidPolynomial(), true);
    //        && t.checkExpect(this.loM3invalid.isValidPolynomial(), false);
  }

  boolean testRemoveZeroCoefficient(Tester t) {
    return t.checkExpect(this.zeroCoefficients.removeZeroCoefficient(), loM2)
        && t.checkExpect(this.loM3.removeZeroCoefficient(), loM3);
  }

  boolean testInsert(Tester t) {
    return t.checkExpect(this.loM1.insert(m2), loM2) && t.checkExpect(this.loM2.insert(m3), loM3)
        && t.checkExpect(this.loMt.insert(m2), new ConsLoMonomial(m2, new MtLoMonomial()));
  }

  boolean testSort(Tester t) {
    return t.checkExpect(this.loM3notOrdered1.sort(), loM3)
        && t.checkExpect(this.loM3notOrdered2.sort(), loM3)
        && t.checkExpect(this.loM3.sort(), loM3);
  }

  boolean testSameILoMonomials(Tester t) {
    return t.checkExpect(this.loM2.sameILoMonomial(loM1), false)
        && t.checkExpect(this.loM3.sameILoMonomial(loM2), false)
        && t.checkExpect(this.loMt.sameILoMonomial(loMt), true)
        && t.checkExpect(this.loM3.sameILoMonomial(loM3), true);
  }

  boolean testSamePolynomial(Tester t) {
    return t.checkExpect(this.p1.samePolynomial(p1), true)
        && t.checkExpect(this.p2.samePolynomial(p2), true)
        && t.checkExpect(this.p3test.samePolynomial(p3), true)
        && t.checkExpect(this.p3test2.samePolynomial(p3), true)
        && t.checkExpect(this.p2test.samePolynomial(p2), true);
  }
}
