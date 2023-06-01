package domain;

public class CommentVO {
/*
create table comment(
cno int not null auto_increment,
bno int default 0,
writer varchar(100) default "unkown",
content varchar(1000),
reg_date datetime default now(),
primary key(cno));
 */
	
	private int cno;
	private int bno;
	private String writer;
	private String content;
	private String reg_date;
	
	public CommentVO() {}
	
	//post(bno, writer, content)
	public CommentVO(int bno, String writer, String content) {
		this.bno = bno;
		this.writer = writer;
		this.content = content;
	}
	
	//(writer, content, cno)
	public CommentVO(String writer, int cno, String content) {
		this.cno = cno;
		this.writer = writer;
		this.content = content;
	}
	
	
	//modify(cno, content)
	public CommentVO(int cno, String content) {
		this.cno = cno;
		this.content = content;
	}
	
	
	//list(cno, bno, writer, content, reg_date)
	public CommentVO(int cno, int bno, String writer, String content, String reg_date) {
		this.cno = cno;
		this.bno = bno;
		this.writer = writer;
		this.content = content;
		this.reg_date = reg_date;
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {
		return "CommentVO [cno=" + cno + ", bno=" + bno + ", writer=" + writer + ", content=" + content + ", reg_date="
				+ reg_date + "]";
	}
	
	
}
