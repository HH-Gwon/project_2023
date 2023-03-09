import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsoleBoard {

	public static void main(String[] args) {
		BoardData board = new BoardData();
		Scanner sc = new Scanner(System.in);
	
		while (true) {
			try {
				System.out.print("|1.목록|	|2.등록|	|3.내용|	|4.삭제|	|0.종료|" + '\n' + ">>");
				int choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
					System.out.println("\n----------------------------------------");
					System.out.println("[번 호]	<제 목>");
					board.listPosts();
					System.out.println("========================================\n");
					break;
				case 2:
					System.out.print("제목>");
					String head = sc.nextLine();
					System.out.print("내용>");
					String body = sc.nextLine();
					System.out.print("작성자>");
					String userName = sc.nextLine();

					SimpleDateFormat dt = new SimpleDateFormat("yy/MM/dd [HH:mm:ss]");
					String date = dt.format(new Date());

					board.addPost(userName, head, body, date);
					break;
				case 3:

					System.out.print("읽을 글의 번호를 입력해주세요." + '\n' + ">>");
					int readNum = sc.nextInt();
					sc.nextLine();
					board.readPost(readNum);
					break;
				case 4:
					
					System.out.print("삭제하려는 글의 번호를 입력해주세요." + '\n' + ">>");
					int deleteNum = sc.nextInt();
					sc.nextLine();
					board.deletePost(deleteNum);
					break;
				case 0:
					System.out.println("Good bye!");
					return;

				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요. 처음으로 돌아갑니다.");
				sc.nextLine();
			}
		}
	}
}
