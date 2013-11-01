package com.example;

import java.util.Scanner;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String... args) {

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
		
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		while (ok) {
			System.out.println("dictionary > ");
			String command = s.nextLine();
		}
		s.close();
		
		ctx.close();
	}
	
}
