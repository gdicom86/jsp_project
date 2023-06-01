package domain;

public class PagingVO {
	private int pageNo; //현재 화면에 출력되는 페이지네이션 번호
	private int qty; // 한페이지에 보여줄 게시글 수 => 10개
	
	//검색 관련 멤버변수 추가
	private String type;
	private String keyword;
	

	public PagingVO() { //페이지네이션을 클릭하기전 기본 값으로 지정
		this(1, 10);
	}
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	/* 
	 * 
	 	limit 번지, 개수;
		번지는 0부터 시작
	 	1page
		select * from board limit 0, 10;
	
		2page
		select * from board limit 10, 10; // 10~19 10개
	
		3page 20 4page 30    (page - 1) * 10
		
	 */
	public int getPageStart() {
		return (this.pageNo-1)*10; //시작 limit 번지 구하기
	}
	
	//타입을 배열처리
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {}: this.type.split("");
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	} 
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "PagingVO [pageNo=" + pageNo + ", qty=" + qty + ", type=" + type + ", keyword=" + keyword + "]";
	}
	
}
