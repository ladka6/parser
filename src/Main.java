import types.Program;
public class Main {
    public static void main(String[] args) {
        String program = """
                x > 0 || y < 1;
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
