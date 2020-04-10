package tools;

import java.util.Scanner;

public class ConsoleManager {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void Clear() {
		
		for(int i=0;i<5;i++) {
			
			System.out.println("\n\n\n\n\n\n\n\n\n");
		}
	}
		
	public static String ReadLine() {
		
		return scanner.nextLine();
	}
	
	public static void WriteLine() {
		
		System.out.println();
	}
	
	public static void WriteLine(String value) {
		
		System.out.println(value);
	}
	
	public static void WriteLine(int value) {
		
		System.out.println(value);
	}
	
	public static void WriteLine(Boolean value) {
		
		System.out.println(value);
	}
	
	public static void WriteLine(Double value) {
		
		System.out.println(value);
	}
	
	public static void Write(String value) {
		
		System.out.print(value);
	}
	
	public static void Write(int value) {
		
		System.out.print(value);
	}
	
	public static void Write(Boolean value) {
		
		System.out.print(value);
	}
	
	public static void Write(Double value) {
		
		System.out.print(value);
	}
}
