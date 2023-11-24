package indi.sword.util.basic.dataStructure.basic._04_doubleList;

public class TestFirstLastLinkList {
	public static void main(String[] args) {
		
		FirstLastLinkList fl = new FirstLastLinkList();
//		fl.insertFirst(34);
//		fl.insertFirst(56);
//		fl.insertFirst(67);
//		fl.display();
//		
//		fl.deleteFirst();
//		fl.deleteFirst();
//		fl.display();
		
		fl.insertLast(56);
		fl.insertLast(56);
		fl.insertLast(56);
		fl.insertLast(90);
		fl.insertLast(12);

		fl.display();
		fl.deleteByValue(56);
		fl.display();
//		fl.deleteFirst();
	}
}
