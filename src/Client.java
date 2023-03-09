import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 7777);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                //System.out.print("> ");
                System.out.print("|1.목록|	|2.등록|	|3.내용|	|4.삭제|	|0.종료|" + '\n' + ">>");
                String line = sc.nextLine();
                
                if (line.equalsIgnoreCase("0")) {
                    break;
                }

                out.println(line);

                String response = in.readLine();

                while (response != null) {
                    System.out.println(response);
                    response = in.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
