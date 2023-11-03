import types.Program;
public class Main {
    public static void main(String[] args) {
        String program = """
            class Point {
                def constructor(x, y) {
                  this.x = x;
                  this.y = y;
                }
          
                def calc() {
                  return this.x + this.y;
                }
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
