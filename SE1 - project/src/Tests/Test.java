package Tests;

public class Test {
	public static void main(String[] args) {
		String minStreng="Martin 3";
		String[] strings=minStreng.split(" ");
		System.out.println(strings[0]);
		System.out.println(strings[1]);
		System.out.println(Integer.parseInt(strings[1]));
	}
}
