import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardData {
	private ArrayList<Post> posts;
	private int lastId;

	public BoardData() {
		posts = new ArrayList<Post>();
		lastId = 0;
	}

	public void addPost(String userName, String head, String body, String date) {
		posts.add(new Post(++lastId, userName, head, body, date));

		System.out.println("글이 등록되었습니다.");
	}

	public void listPosts() {
		if (posts.size() == 0) {
			System.out.println("등록된 글이 없습니다.");
			return;
		}

		for (int i = posts.size() - 1; i >= 0; i--) {
			System.out.println(posts.get(i));
		}
	}

	public void readPost(int num) {
		Post post = getPostByNum(num);

		if (post == null) {
			System.out.println("읽으려는 번호의 글이 없습니다.");
			return;
		}
		post.readBody();
	}

	public void deletePost(int num) {
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
}