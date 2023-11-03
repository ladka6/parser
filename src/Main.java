import types.Program;
public class Main {
    public static void main(String[] args) {
        String program = """
               let s = "Hello world!";
               let i = 0;

               while(i < s.length) {
                s[i];
                i += 1;
               }
                """;

        Parser parser = new Parser();
        try {
            Program program2 = parser.parse(program);
            String str = program2.toString();
            System.out.println(str);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
