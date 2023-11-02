import types.Program;
public class Main {
    public static void main(String[] args) {
        String program = """
                for(let i = 0; i < 10; i +=1) {
                    x += 1;
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
