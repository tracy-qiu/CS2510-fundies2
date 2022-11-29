import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import tester.Tester;;

class Utils {
	//return a list with the items that pass the predicate
	<T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
		ArrayList<T> result = new ArrayList<T>();

		for (T t: arr) {
			if (pred.test(t)) {
				result.add(t);
			}
		}

		return result;
	}

	//return a list with the items that do not pass the predicate
	<T> ArrayList<T> filterNot(ArrayList<T> arr, Predicate<T> pred) {
		ArrayList<T> result = new ArrayList<T>();

		for (T t: arr) {
			if (!pred.test(t)) {
				result.add(t);
			}
		}

		return result;
	}

	//filters the list of either items that don't pass the predicate or items that do
	<T> ArrayList<T> customFilter(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {
		ArrayList<T> result = new ArrayList<T>();

		if (!keepPassing) {
			pred = new FlipPredicate<T>(pred);
		}

		for (T t: arr) {
			if (pred.test(t)) {
				result.add(t);
			}
		}

		return result;
	}

	//return a list with the items that pass the predicate
	<T> ArrayList<T> filter2(ArrayList<T> arr, Predicate<T> pred) {
		return this.customFilter(arr, pred, true);
	}

	//return a list with the items that pass the predicate
	<T> ArrayList<T> filterNot2(ArrayList<T> arr, Predicate<T> pred) {
		return this.customFilter(arr, pred, false);
	}

	//EFFECT: removes items from the given list if they don't pass the predicate
	<T> void removeFailing(ArrayList<T> arr, Predicate<T> pred) {
		for (int i = 0; i < arr.size(); i++) {
			if (!pred.test(arr.get(i))) {
				arr.remove(i);
				i--;
			}
		}
	}

	//EFFECT: removes items from the given list if they do pass the predicate
	<T> void removePassing(ArrayList<T> arr, Predicate<T> pred) {
		for (int i = 0; i < arr.size(); i++) {
			if (pred.test(arr.get(i))) {
				arr.remove(i);
				i--;
			}
		}
	}

	//EFFECT: removes items from the given list if they don't pass the predicate
	<T> void customRemove(ArrayList<T> arr, Predicate<T> pred, boolean keepPassing) {

		if (keepPassing) {
			pred = new FlipPredicate<T>(pred);
		}

		for (int i = 0; i < arr.size(); i++) {
			if (pred.test(arr.get(i))) {
				arr.remove(i);
				i--;	
			}
		}

	}


	//interleave the two lists in order, so the first element of the output 
	//list comes from arr1, the second from arr2, the third from arr1, etc
	<T> ArrayList<T> interweave(ArrayList<T> arr1, ArrayList<T> arr2) {
		ArrayList<T> result = new ArrayList<T>();
		int sizeOfResult = arr1.size() + arr2.size();
		int i = 0;

		for (i=0; i < sizeOfResult; i++) {
			if (i < arr1.size() ) {
				result.add(arr1.get(i));
			}
			if (i < arr2.size()) {
				result.add(arr2.get(i));
			}

		}

		return result;
	}

	//get (up to) getFrom1 elements from arr1, then (up to) getFrom2 elements from arr2, 
	//then (up to) getFrom1 elements from arr1, etc.
	<T> ArrayList<T> customInterweave(ArrayList<T> arr1, ArrayList<T> arr2, int getFrom1, int getFrom2) {
		ArrayList<T> result = new ArrayList<T>();
		int currPos1 = 0;
		int currPos2 = 0;

		for (int i=0; i < arr1.size() + arr2.size(); i++) {

			for (int j = currPos1; j < currPos1 + getFrom1; j++)
				if (j < arr1.size() ) {
					result.add(arr1.get(j));
				}
			currPos1 = currPos1 + getFrom1;

			for (int k = currPos2; k < currPos2 + getFrom2; k++) {
				if (k < arr2.size()) {
					result.add(arr2.get(k));
				}
			}
			currPos2 = currPos2 + getFrom2;
		}

		return result;
	}

	//interleave the two lists in order, so the first element of the output 
	//list comes from arr1, the second from arr2, the third from arr1, etc
	<T> ArrayList<T> interweave2(ArrayList<T> arr1, ArrayList<T> arr2) {
		return this.customInterweave(arr1, arr2, 1, 1);
	}

}

class FlipPredicate<T> implements Predicate<T> {
	Predicate<T> pred;

	FlipPredicate(Predicate<T> pred) {
		this.pred = pred;
	}

	@Override
	public boolean test(T t) {
		return !this.pred.test(t);
	}

}


class ExamplesArrayLists {
	ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));
	ArrayList<Integer> intsResult = new ArrayList<Integer>(Arrays.asList(2,4,6));
	ArrayList<Integer> intsResultNot = new ArrayList<Integer>(Arrays.asList(1,3,5));

	ArrayList<Integer> ints2 = new ArrayList<Integer>(Arrays.asList(1,2,5,5,5,6));
	ArrayList<Integer> ints2Result = new ArrayList<Integer>(Arrays.asList(2,6));

	ArrayList<Integer> intsWeaved = new ArrayList<Integer>(Arrays.asList(2,2,4,6,6));
	ArrayList<Integer> intsWeaved2 = new ArrayList<Integer>(Arrays.asList(1,2,2,6,3,4,5,6));
	ArrayList<Integer> intsWeaved3 = new ArrayList<Integer>(Arrays.asList(2,1,6,2,3,4,5,6));

	ArrayList<String> strings1 = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F"));
	ArrayList<String> strings2 = new ArrayList<String>(Arrays.asList("w", "x", "y", "z"));
	ArrayList<String> strings3 = new ArrayList<String>(Arrays.asList("A", "B", "C", "w", "x", "D", "E", "F", "y", "z"));
	ArrayList<String> strings4 = new ArrayList<String>(Arrays.asList("w", "x", "y", "A", "B", "z", "C", "D", "E", "F"));


	Utils u = new Utils();

	void initData() {
		ints = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));
		intsResult = new ArrayList<Integer>(Arrays.asList(2,4,6));
		intsResultNot = new ArrayList<Integer>(Arrays.asList(1,3,5));

		ints2 = new ArrayList<Integer>(Arrays.asList(1,2,5,5,5,6));
		ints2Result = new ArrayList<Integer>(Arrays.asList(2,6));
	}


	void testFilter(Tester t) {
		this.initData();
		t.checkExpect(u.filter(ints, i->i%2==0), intsResult);
		t.checkExpect(u.filter(new ArrayList<Integer>(), i->i%2==0), new ArrayList<Integer>());

		t.checkExpect(u.filterNot(ints, i->i%2==0), intsResultNot);
		t.checkExpect(u.filterNot(new ArrayList<Integer>(), i->i%2==0), new ArrayList<Integer>());

		t.checkExpect(u.customFilter(ints, i->i%2==0, true), intsResult);
		t.checkExpect(u.customFilter(new ArrayList<Integer>(), i->i%2==0, true), new ArrayList<Integer>());

		t.checkExpect(u.customFilter(ints, i->i%2==0, false), intsResultNot);
		t.checkExpect(u.customFilter(new ArrayList<Integer>(), i->i%2==0, false), new ArrayList<Integer>());

		t.checkExpect(u.filter2(ints, i->i%2==0), intsResult);
		t.checkExpect(u.filter2(new ArrayList<Integer>(), i->i%2==0), new ArrayList<Integer>());

		t.checkExpect(u.filterNot2(ints, i->i%2==0), intsResultNot);
		t.checkExpect(u.filterNot2(new ArrayList<Integer>(), i->i%2==0), new ArrayList<Integer>());

		u.removeFailing(ints, i->i%2==0);

		t.checkExpect(ints, intsResult);

		u.removeFailing(ints, i->i%2!=0);

		t.checkExpect(ints, new ArrayList<Integer>());

		u.removeFailing(ints2, i->i%2==0);

		t.checkExpect(ints2, ints2Result);

	}
	
	void testRemove(Tester t) {
		this.initData();
		
		u.customRemove(ints, i->i%2==0, true);

		t.checkExpect(ints, intsResult);
		
		this.initData();

		u.customRemove(ints, i->i%2==0, false);

		t.checkExpect(ints, intsResultNot);
		
		this.initData();
		
		u.customRemove(ints2, i->i%2==0, true);

		t.checkExpect(ints2, ints2Result);
	}

	void testInterweave(Tester t) {
		this.initData();
		t.checkExpect(u.interweave(intsResult, ints2Result), intsWeaved);
		t.checkExpect(u.interweave(ints, ints2Result), intsWeaved2);
		t.checkExpect(u.interweave(ints, new ArrayList<Integer>()), ints);
		t.checkExpect(u.interweave(new ArrayList<Integer>(), ints), ints);
		t.checkExpect(u.interweave(ints2Result,ints), intsWeaved3);

		t.checkExpect(u.customInterweave(strings1, strings2, 3, 2), strings3);
		t.checkExpect(u.customInterweave(strings2, strings1, 3, 2), strings4);

		t.checkExpect(u.customInterweave(strings1, new ArrayList<String>(), 3, 2), strings1);
		t.checkExpect(u.customInterweave(new ArrayList<String>(), strings1, 3, 2), strings1);

		t.checkExpect(u.interweave2(intsResult, ints2Result), intsWeaved);
		t.checkExpect(u.interweave2(ints, ints2Result), intsWeaved2);
		t.checkExpect(u.interweave2(ints, new ArrayList<Integer>()), ints);
		t.checkExpect(u.interweave2(new ArrayList<Integer>(), ints), ints);
		t.checkExpect(u.interweave2(ints2Result,ints), intsWeaved3);
	}
}