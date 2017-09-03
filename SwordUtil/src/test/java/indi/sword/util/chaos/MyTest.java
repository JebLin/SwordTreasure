package indi.sword.util.chaos;

public class MyTest {
	public static void main(String[] args){
		Integer i = 127;
		Integer j = 127;
		System.out.println(i == j);
		System.out.println(i.equals(j));

		Integer i1 = 128;
		Integer j1 = 128;
		System.out.println(i1 == j1);
		System.out.println(i1.equals(j1));
	}
}
