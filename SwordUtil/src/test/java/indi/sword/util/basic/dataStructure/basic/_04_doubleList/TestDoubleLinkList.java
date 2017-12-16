package indi.sword.util.basic.dataStructure.basic._04_doubleList;

public class TestDoubleLinkList {
	public static void main(String[] args) {
		DoubleLinkList dl = new DoubleLinkList();
		dl.insertLast(56);
		dl.insertLast(56);
		dl.insertLast(56);
		dl.insertLast(56);
		dl.insertLast(45);
		dl.insertLast(90);
		dl.display();
		dl.deleteByValue(90);
		dl.display();

//		while(!dl.isEmpty()) {
//			dl.deleteFirst();
//			dl.display();
//		}
	}
}
