package mrmcmax.data_structures.linear;

import java.util.LinkedList;
import java.util.ListIterator;

public class TestIterators {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EraserLinkedList<Integer> list = new EraserLinkedList<>();
		EraserLinkedList.Node<Integer> one = list.addAndReturnPointer(1);
		for (int i = 2; i <= 4; i++) {
			list.add(i);
		}
		EraserLinkedList.Node<Integer> five = list.addAndReturnPointer(5);
		list.add(6);
		EraserLinkedList.Node<Integer> seven = list.addAndReturnPointer(7);
		System.out.println(list);
		System.out.println(five.data);
		list.remove(five);
		System.out.println(list);
		list.remove(one);
		System.out.println(list);
		list.remove(seven);
		System.out.println(list);
		System.out.println(list.size());
	}

}
