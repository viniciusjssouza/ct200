package br.ita.automatos;

public class TestData {

	public static Automata automata1() {
		return new Automata(
	        	"0, 4, &\n "
	        	+ "4, 4, a\n "
	        	+ "4, 4, b\n "
	        	+ "4, 2, &\n "
	        	+ "2, 3, b\n"
	        	+ "3, 5, b\n"
	        	+ "5, 6, &\n"
	        	+ "6, 6, a\n"
	        	+ "6, 6, b\n"
	        	+ "6, 1, &\n"        		
	        );        
	}
	
	public static Automata automata2() {
		return new Automata(
				"0, 2, &\n "
    	        	+ "2, 1, &\n "
    	        	+ "2, 3, a\n"
    	        	+ "3, 2, b\n"
    	        	+ "3, 2, c\n"        		
    	     );              
	}
	
	public static Automata automata3() {
		return new Automata(
				"0, 4, &\n"
	        	+ "0, 5, &\n"
	        	+ "4, 4, a\n"
	        	+ "5, 5, b\n"
	        	+ "4, 2, &\n"
	        	+ "5, 3, &\n"
	        	+ "2, 1, b\n"
	        	+ "3, 1, a\n"        		
	        );              
	}
	
	
	public static Automata automata4() {
		return  new Automata(
			"0, 5, &\n"
        	+ "5, 5, a\n"
        	+ "5, 2, &\n"
        	+ "2, 4, &\n"
        	+ "4, 4, b\n"
        	+ "4, 3, &\n"
        	+ "3, 6, &\n"
        	+ "6, 6, c\n"        		
        	+ "6, 1, &\n"
        );               
	}
}
