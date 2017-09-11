package indi.sword.util.jdk8.lambda;

public class _01_ilterEmployeeForSalary implements _01_MyPredicate<_01_Employee> {

	@Override
	public boolean test(_01_Employee t) {
		return t.getSalary() >= 5000;
	}

}
