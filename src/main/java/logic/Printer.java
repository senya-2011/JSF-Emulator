package logic;

public class Printer {
    public static void info(String message){
        System.out.println(message);
    }

    public static void globalInfo(String message){
        System.out.println(String.format("\n====== %s ======\n", message));
    }
    public static void requestInfo(String message){
        String s = "=".repeat(50);
        System.out.println(String.format("\n\n\n%s\n %s \n", s, message));
    }
}
