import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class NetworkBoard {
	private ArrayList<Post> posts;
	private int lastId;

	public void BulletinBoardServer() {
		posts = new ArrayList<Post>();
		lastId = 0;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(7777)) {
			System.out.println("Server started.");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

				Thread thread = new Thread(new ClientHandler(clientSocket));
				thread.start();
			}
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public synchronized void listPosts(PrintWriter out) {

		if (posts.size() == 0) {
			out.println("등록된 글이 없습니다.");
			return;
		}
		
		out.println("\n----------------------------------------");
		out.println("[번 호]	<제 목>");
		for (Post post : posts) {
			out.println(post.toString());
		}
		out.println("========================================\n");
	}

	public synchronized void addPost(String userName, String head, String body, String date) {
		posts.add(new Post(++lastId, userName, head, body, date));

		System.out.println("글이 등록되었습니다.");
	}

	public synchronized void readPost(int num) {
		Post post = getPostByNum(num);

		if (post == null) {
			System.out.println("읽으려는 번호의 글이 없습니다.");
			return;
		}
		post.readBody();
	}

	public synchronized void deletePost(int num) {
		Post post = getPostByNum(num);

		if (post == null) {
			System.out.println("삭제하려는 글이 없습니다.");
			return;
		}

		posts.remove(post);
		System.out.println("해당 글을 삭제하였습니다.");
	}

	private Post getPostByNum(int num) {
		for (Post post : posts) {
			if (post.getNum() == num) {
				return post;
			}
		}
		return null;
	}

	public static void main(String[] args) {

		NetworkBoard server = new NetworkBoard();
		server.start();
	}

	private class ClientHandler implements Runnable {
		private Socket clientSocket;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
				Scanner sc = new Scanner(System.in);
				while (true) {
					String line = in.readLine();

					if (line == null) {
						break;
					}

					String[] parts = line.split("\\|");
//					int[] parts = choice.split("\\|");

//		boardData board = new boardData();
//					Scanner sc = new Scanner(System.in);
//
//		while (true) {
//			try {
//				System.out.print("|1.목록|	|2.등록|	|3.내용|	|4.삭제|	|0.종료|" + '\n' + ">>");
//					int choice = sc.nextInt();
//					sc.nextLine();

					switch (parts[0]) {
					case "1":
						listPosts(out);
						break;
					case "2":
//						System.out.print("제목>");
//						String head = sc.nextLine();
//						System.out.print("내용>");
//						String body = sc.nextLine();
//						System.out.print("작성자>");
//						String userName = sc.nextLine();

						SimpleDateFormat dt = new SimpleDateFormat("yy/MM/dd [HH:mm:ss]");
						String date = dt.format(new Date());
						addPost(parts[1], parts[2], parts[3], date);

//						addPost(userName, head, body, date);
						break;
					case "3":
						System.out.println("\n----------------------------------------");
						System.out.println("[번 호]	<제 목>");
						listPosts(out);
						System.out.println("========================================\n");

						System.out.print("읽을 글의 번호를 입력해주세요." + '\n' + ">>");

						int readNum = sc.nextInt();
						sc.nextLine();
						readPost(readNum);
						break;
					case "4":
						System.out.println("\n----------------------------------------");
						System.out.println("[번 호]	<제 목>");
						listPosts(out);
						System.out.println("========================================\n");

						System.out.print("삭제하려는 글의 번호를 입력해주세요." + '\n' + ">>");
						int deleteNum = sc.nextInt();
						sc.nextLine();
						deletePost(deleteNum);
						break;
					case "0":
						System.out.println("Good bye!");
						return;

					default:
						System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("에러: " + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					System.out.println("에러: " + e.getMessage());
				}
			}
		}
	}
}
