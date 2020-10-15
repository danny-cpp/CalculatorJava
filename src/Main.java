public class Main {

    public static void main(String[] args) {
	    String test = "a b        s";
	    String[] a = test.split("\\s+");

        for (String s : a) {
            System.out.print("a is:");
            System.out.println(s);
        }

        
    }
}
