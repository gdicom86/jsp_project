package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductService;
import service.Service;
import domain.productVO;

public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//해야하는것 : 상품등록,상품리스트,상품상세,상품수정,상품삭제
	
	//연결순서 : 컨트롤러 -> 서비스 -> dao -> DBconnection
	//컨트롤러 -> 서비스를 호출해서 값을 처리해야함
	//서비스 -> dao에서 서비스를 호출해서 처리
	//dao -> DBconnection을 연결해서 처리
	
	private Service svc;

    public ProductController() {
        // 생성자
    	svc = new ProductService();
    	
    }

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 주 작업 영역 =  서비스 영역
		
		//service(request,response);로 get,서비스로 오는 처리는 모두 서비스로 답변할 예정!
		//이때, post방식으로 데이터 처리를 할 떄 한글이 깨지는 것을 방지
		req.setCharacterEncoding("utf-8"); //받을떄
		res.setCharacterEncoding("utf-8"); //보낼때
		res.setContentType("text/html; charset=utf-8"); //보내는 값의 컨텐츠의 타입을 정해줌 (contentType="text/html; charset=UTF-8")
		
		//요청에 대한 모든것을 담고 있는 객체 uri 생성
		String uri = req.getRequestURI(); //전체 요청경로 (원하는 서비스를 담아옴)
		System.out.println(">>> uri  " + uri);
		//보통은 컨트롤러의 주소/요청서비스로 주는데, 컨트롤러의 주소가 이건하나니까 주소없이 요청서비스만 받아오게 생성!
		
		String context = req.getContextPath(); //프로젝트 명
		System.out.println("프로젝트명: " + context);
		
		//객체를 여기에 거쳤다가 보내야하는 목적지의 주소
		String destPage = ""; //목적지주소
		
		switch(uri) { //if문 사용도 가능은하는데 복잡해서 비추천
		case "/register.pd": 
			destPage = "/register.jsp"; // '/'를 넣어서 폴더변경해서 이동해주기 (/폴더명/jsp파일명.jsp)
			break;
			
		case "/insert.pd": 
			//DB연결, 등록처리
			//1. jsp에서 가져온 객체(이름, 가격, 세부정보)를 가져왔음!
			//2. service에서 가져온 객체를 객체화해 db에 저장해달라고 요청해야함!
			
			//해야하는 것 : 1. 객체 생성
			// 1) 생성자가 있는 경우!
			String pname = req.getParameter("pname"); //파라미터의 pname
			int price = Integer.parseInt(req.getParameter("price")); //파라미터의 price (단, 파라미터는 모두 string이라 형변환필요)
			String madeby = req.getParameter("madeby"); //파라미터의 madeby
			productVO pvo = new productVO(pname,price,madeby);
			
			// 2) 생성자가 없는 경우!
//			productVO pvo = new productVO();
//			pvo.setPname(pname);
//			pvo.setPrice(price);
//			pvo.setMadeby(madeby);
			
			//해야하는 것 : 2. service에게 객체를 주고 요청하기
			int isOk =  svc.register(pvo); //리턴값은  1 = 1개의 행이 등록되었습니다
			System.out.println(">>> 상품등록 >" + (isOk > 0 ? "등록성공" : "등록실패"));
			
			destPage = "/index.jsp";
			break;
			
		case "/list.pd":
			//서비스에게 요청만 하면 됨
			List<productVO> list = new ArrayList<productVO>();
			
			list = svc.list(); //svc에서 list가져와서
			req.setAttribute("list", list); //넣기
			
//			req.setAttribute("list", svc.list); //바로 넣는 방법
			
			System.out.println(">>> 상품리스트 가져오기 성공!");
			destPage = "/list.jsp";
			break;
			
		case "/detail.pd":
			//1. 쿼리스트링으로 가져온 pno 때서 가져오기
			int pno = Integer.parseInt(req.getParameter("pno")); 
			
			//2. 해당 pno의 데이터를 가져오기
			productVO p = new productVO();
			p = svc.detail(pno);
			req.setAttribute("pvo", p);
			System.out.println(">>> 상품상세 성공!");
			destPage = "detail.jsp";
			break;
			
		case "/remove.pd":
			int pno2 = Integer.parseInt(req.getParameter("pno")); 
			int isOk2 =  svc.remove(pno2); 
			System.out.println(">>> 상품삭제 > " + (isOk2 > 0 ? "수정 성공" : "수정 실패"));
			destPage = "/list.pd"; 
			
			break;
		
		case "/modify.pd":
			pno = Integer.parseInt(req.getParameter("pno")); 
			req.setAttribute("pvo", svc.detail(pno));
			destPage = "/modify.jsp";
			break;
		
		case "/edit.pd":
			int pno1 = Integer.parseInt(req.getParameter("pno")); 
			String pname1 = req.getParameter("pname"); 
			int price1 = Integer.parseInt(req.getParameter("price")); 
			String regdate1 = req.getParameter("regdate");
			String madeby1 = req.getParameter("madeby"); 
			productVO pvo1 = new productVO(pno1,pname1,price1,regdate1,madeby1);
			
			int isOk1 =  svc.modify(pvo1); //리턴값은  1 = 1개의 행이 등록되었습니다
			System.out.println(">>> 상품수정 > " + (isOk1 > 0 ? "수정 성공" : "수정 실패"));
			destPage = "/list.pd"; //pd로 가야 데이터를 가져와서 출력해줌.
			
			break;
			
		}
		
		
		
		
		//웹의 목적지 주소에 따라 보내는(연결해주는) 객체
		//destpage로 이동시 응답객체를 싣고 가야함!
		RequestDispatcher rdp = req.getRequestDispatcher(destPage);
		rdp.forward(req, res);
		
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get방식으로 오는 값을 확인(체킹)
		
		//response.getWriter().append("Served at: ").append(request.getContextPath()); //get으로 보내는 구문
		//서비스로 처리해 보낼예정이라 get으로보내는 구문은 필요가 없음!
		
		//서비스로 보내는 구문
		service(req,res);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// post방식으로 오는 값을 확인(체킹)
		
		//doGet(request, response); //get으로 보내는 구문
		//서비스로 처리해 보낼예정이라 get으로보내는 구문은 필요가 없음!
		
		//서비스로 보내는 구문
		service(req,res);
	}

}
