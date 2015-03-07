package com.example.dictionary;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Controller {

	public void run() {
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		while (ok) {
			System.out.print("dictionary > ");
			String command = s.nextLine();
			
			if ("exit".equals(command)) {
				ok = false;
			}
		}
		s.close();
	}
	
}
