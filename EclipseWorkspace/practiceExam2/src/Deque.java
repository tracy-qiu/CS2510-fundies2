import java.util.function.Predicate;

import tester.Tester;

// Practice Exam problem 3 
/*
 * Design a void method in the class Deque that appends a given Deque to the end of this Deque. 
 * The method should append all the items of the given Deque onto the current one and remove the items from that given Deque. 
 * Explain in a few sentences if there are any instances where the method you designed fails to work as expected. 
 * You may assume the methods for Deque shown above exist and work. If you need any methods in ANode, Sentinel or Node, 
 * you must implement them. You may not assume there are any existing methods in these classes.
 */

//class Deque {
//  Sentinel header;
//
//  // Returns the number of items in this Deque (excluding the sentinel)
//  int size();
//
//  // EFFECT: inserts the given value at the front of the Deque
//  void addToHead(T value);
//
//  // EFFECT: inserts the given value at the back of the Deque
//  void addToTail(T value);
//
//  // Returns the current first item of the Deque
//  // EFFECT: removes the current first item of the Deque
//  T removeFromHead();
//
//  // Returns the current last item of the Deque
//  // EFFECT: removes the current last item of the Deque
//  T removeFromTail();
//}
//
//class ANode {
//  ANode next;
//  ANode prev;
//}
//
//class Sentinel extends ANode {
//}
//
//class Node extends ANode {
//  T data;
//}

// represents a deque
class Deque<T> {
  Sentinel<T> header;

  // constructors
  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // determines size of deque
  int size() {
    return this.header.next.sizeHelper();
  }

  // insert at start of deque
  void addAtHead(T value) {
    // // Without abstraction
    // Node<T> newNode = new Node<T>(value);
    // this.header.insertHead(newNode, this.header.next);
    addAtCustom(value, true);
  }

  // insert at the end of deque
  void addAtTail(T value) {
    // // Without abstraction
    // Node<T> newNode = new Node<T>(value);
    // this.header.insertTail(newNode, this.header.next, this.header.prev);
    addAtCustom(value, false);
  }

  // insert at the start of end of deque
  void addAtCustom(T value, boolean atHead) {
    Node<T> newNode = new Node<T>(value);
    if (atHead) {
      this.header.insertHead(newNode, this.header.next);
    }
    else {
      this.header.insertTail(newNode, this.header.next, this.header.prev);
    }
  }

  // remove the first thing from the deque
  // field of field of field access
  T removeFromHead() {
    return this.header.next.removeFromHelper();
  }

  // remove the last thing from the deque
  T removeFromTail() {
    return this.header.prev.removeFromHelper();
  }

  // modify the deque by removing the given node
  void removeNode(ANode<T> given) {
    given.removeFromHelper();
  }

  // finds first node that passes the predicate
  ANode<T> find(Predicate<T> predicate) {
    return this.header.next.findHelper(predicate);
  }

  void appendDeque(Deque<T> given) {
    while (given.size() > 0) {
      this.addAtTail(given.removeFromHead());
    }
  }
}

//represents ANode 
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // updates the next
  void updateNext(ANode<T> next) {
    this.next = next;
  }

  // updates the previous
  void updatePrev(ANode<T> prev) {
    this.prev = prev;
  }

  // determines the size of a deque
  abstract int sizeHelper();

  // inserts a node at the beginning of a deque
  void insertHead(Node<T> newNode, ANode<T> oldFirst) {
    this.next = newNode;
    // dont have to update previous
    newNode.next = oldFirst;
    newNode.prev = this;
    oldFirst.prev = newNode;
  }

  // inserts node at the end of a deque
  void insertTail(Node<T> newNode, ANode<T> firstNode, ANode<T> oldLast) {
    if (this.prev == firstNode) {
      this.prev = newNode;
      firstNode.next = newNode;
      newNode.next = this;
      newNode.prev = firstNode;
    }
    else {
      this.prev = newNode;
      oldLast.next = newNode;
      newNode.next = this;
      newNode.prev = oldLast;
    }

  }

  // removes node from deque
  abstract T removeFromHelper();

  // find node that passes the given predicate
  abstract ANode<T> findHelper(Predicate<T> predicate);

}

// represents a sentinel 
class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // determines the size of a deque
  int sizeHelper() {
    return 0;
  }

  // removes the node from the deque
  T removeFromHelper() {
    throw new RuntimeException("Empty Deque!");
  }

  // find node that passes the given predicate
  ANode<T> findHelper(Predicate<T> predicate) {
    return this;
  }
}

// represents a Node
class Node<T> extends ANode<T> {
  T data;

  // node constructor
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    if (next == null) {
      throw new IllegalArgumentException("Node is Null!");
    }
    this.next = next;
    // update the previous
    next.updatePrev(this);

    if (prev == null) {
      throw new IllegalArgumentException("Node is Null!");
    }
    this.prev = prev;
    // update the next
    prev.updateNext(this);

  }

  // determines size of the deque
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  // returns data of node that was removed and patches up the data
  T removeFromHelper() {
    this.next.prev = this.prev;
    this.prev.next = this.next;

    return this.data;
  }

  // returns the first node that passes the predicate
  ANode<T> findHelper(Predicate<T> predicate) {
    if (predicate.test(data)) {
      return this;
    }
    else {
      return this.next.findHelper(predicate);
    }
  }

}

// Predicate that determines if string length equals 3 
class StringLength3 implements Predicate<String> {
  public boolean test(String str) {
    return str.length() == 3;
  }
}

class ExamplesDeque {
  ExamplesDeque() {
  }

  Sentinel<String> mtsent;
  Deque<String> deque1;

  Sentinel<String> sent2;
  Node<String> first;
  Node<String> second;
  Node<String> third;
  Node<String> fourth;
  Deque<String> deque2;

  Sentinel<String> sent3;
  Node<String> first3;
  Node<String> second3;
  Node<String> third3;
  Node<String> fourth3;
  Deque<String> deque3;

  Sentinel<String> sentResult;
  Node<String> r1;
  Node<String> r2;
  Node<String> r3;
  Node<String> r4;
  Node<String> r5;
  Node<String> r6;
  Node<String> r7;
  Deque<String> result;

  void initDeque() {

    mtsent = new Sentinel<String>();
    deque1 = new Deque<String>(this.mtsent);

    sent2 = new Sentinel<String>();
    first = new Node<String>("abc", this.sent2, this.sent2);
    second = new Node<String>("bcd", this.sent2, this.first);
    third = new Node<String>("cde", this.sent2, this.second);
    fourth = new Node<String>("def", this.sent2, this.third);
    deque2 = new Deque<String>(this.sent2);

    sent3 = new Sentinel<String>();
    first3 = new Node<String>("hi", this.sent3, this.sent3);
    second3 = new Node<String>("hello", this.sent3, this.first3);
    third3 = new Node<String>("bye", this.sent3, this.second3);
    deque3 = new Deque<String>(this.sent3);

    sentResult = new Sentinel<String>();
    r1 = new Node<String>("abc", this.sentResult, this.sentResult);
    r2 = new Node<String>("bcd", this.sentResult, this.r1);
    r3 = new Node<String>("cde", this.sentResult, this.r2);
    r4 = new Node<String>("def", this.sentResult, this.r3);
    r5 = new Node<String>("hi", this.sentResult, this.r4);
    r6 = new Node<String>("hello", this.sentResult, this.r5);
    r7 = new Node<String>("bye", this.sentResult, this.r6);
    result = new Deque<String>(this.sentResult);
  }

  void testAppend(Tester t) {
    initDeque();

    this.deque2.appendDeque(this.deque3);
    t.checkExpect(deque2, result);
  }
}