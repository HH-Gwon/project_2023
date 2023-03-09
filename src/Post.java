
class Post {
	private int num;
	private String userName;
	private String head;
	private String body;
	private String date;

	public Post(int num, String userName, String head, String body, String date) {
		this.num = num;
		this.userName = userName;
		this.head = head;
		this.body = body;
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public String toString() {
		return "[ " + num + " ]\t<" + head + ">" + "\n작성자: " + userName + "\t\t등록일: " + date;
	}

	public void readBody() {
		System.out.println("\n----------------------------------------");
		System.out.println("제   목: " + head);
		System.out.println("작성자: " + userName + "\t\t등록일: " + date);
		System.out.println("------------------내용-------------------");
		System.out.println(body);
		System.out.println("========================================\n");

		// return "[ " + id + " ]\t<" + head + ">" + '\n' + body + "\n작성자:" + userName +
		// "\t" + date;
	}
}