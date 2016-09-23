package br.ita.automatos;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	static boolean toGraphviz = false;
	static String inputString;
	
	public static void main(String... args) {
		toGraphviz = Stream.of(args)
				.filter(arg -> arg.equals("graphviz")).count() > 0;
				
		int idx = hasRegex(args);
		if (args.length >= 1 && idx >= 0) {
			inputString = (args.length > 1 && idx != 0 ? args[0] : null);
			processRegex(args[idx].split("\\=")[1].trim());
		} else {
			inputString = (args.length > 0 ? args[0] : null);
			String input = readInput();
			processAutomata(input);
		}
	}
	
	private static int hasRegex(String... args) {
		for (int i = 0; i < args.length; i++) {
			if(args[i].matches("regex\\=.+")) {
				return i;
			}
		}
		return -1;
	}

	private static void processAutomata(String input) {
		System.out.println("Parsing the automata...");
		Automata automata = new Automata(input);
		
		printAutomata(automata);
		
		System.out.println("Removing epsilon transitions...");
		automata.removeEpsilonTransitions();
		printAutomata(automata);
		
		System.out.println("To Regex...");
		System.out.println(new Automata(input).toRegex());
		
		if (inputString != null) {
			String[] inputStringArr = inputString.split(",");
			System.out.print("\n\n");
			for (String inputStr : inputStringArr) {
				System.out.printf("Verifying input string '%s': ", inputStr);
				System.out.println(new Automata(input).accept(inputStr) ? "ACCEPT" : "NOT ACCEPT");
			}
		}
	}

	private static void printAutomata(Automata automata) {
		System.out.println("Automata obtained:");
		System.out.println("-----------------------------------");
		
		if (toGraphviz) {
			System.out.println(automata.toGraphViz());
		} else {
			System.out.println(automata);
		}
		
		System.out.println("-----------------------------------");
	}

	private static void processRegex(String input) {
		System.out.println("Building the automata...");
		Automata automata = Automata.fromRegex(input);
		processAutomata(automata.toString());		
		
	}
	
	public static String readInput() {
		Scanner scanner = new Scanner(System.in);
		StringBuilder stringBuilder = new StringBuilder();
		
		while(scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
			stringBuilder.append(System.lineSeparator());
		}
		scanner.close();
		return stringBuilder.toString().trim();
	}
}
