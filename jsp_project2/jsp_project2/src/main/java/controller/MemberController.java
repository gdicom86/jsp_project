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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import service.MemberService;
import service.MemberServiceImp;


@WebServlet("/mem/*") //'/*'를 붙이는 이유는 하위 경로때문
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //log 설정
	//private static final Logger log = LoggerFactory.getLogger(클래스명.class);
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp;
	private MemberService msv;
	private int isOk; //확인
	private String destPage; //목적지주소
	
	
  
    public MemberController() {
        msv = new MemberServiceImp(); //멤버의 서비스를 구현한 객체
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// charactorEncoding 설정 / contentType / uri경로 확인
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI(); //전체 요청경로
		System.out.println(">>> uri  " + uri);
		// uri = mem/join -> 요청에 대한 path만  남길래!
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>path: " +path);
		
		
		switch(path) {
		case "join":
			destPage = "/member/join.jsp";
			break;
			
		case "register":
			try {
				String id = request.getParameter("id");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age")) ;
				//파라미터로 mvo객체 생성
				MemberVO mvo = new MemberVO(id,password, email,age);
			
				isOk = msv.register(mvo);
				log.info(">>> JOIN > " + (isOk > 0 ? "성공" : "실패"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/"; //이렇게만 적어도 index페이지로 이동
			break;
			
		case "login":
			log.info(">>> LogIn page 이동");
			destPage = "/member/login.jsp";
			break;
			
		case "login_auth": //실제 로그인이 일어나는 케이스
			try {		
				String id2 = request.getParameter("id");
				String password2 = request.getParameter("password");
				MemberVO mvo2 = new MemberVO(id2, password2);
				//해당 ID, password가 db상에 있는지 체크 
				//해당 객체(사용자)를 가져와서 
				//해당 객체(사용자)를 세션에 담기
				log.info(">>> login 요청"+mvo2);
				MemberVO loginMvo = msv.login(mvo2);
				//같은정보가 있으면 객체를 리턴, 없다면 null이 리턴
				//객체가 있다면 세션에 저장
				if(loginMvo != null) {
					//세션을 가져오기 연결된 세션이 없다면 새로 생성
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					//로그인 유지 시간(초단위)
					ses.setMaxInactiveInterval(30*60);
				} else {
					request.setAttribute("msg_login", 0);
				}
				destPage="/";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "logout":
			try {
				HttpSession ses = request.getSession();
				//마지막 로그인 시간 기록
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				String id2 = mvo2.getId();
				log.info(">>> login id: " + id2);
				//로그인정보(id)를 주고 마지막 로그인 시간 기록(update)
				isOk = msv.lastLogin(id2);
				log.info(">>> logout > " + (isOk > 0 ? "성공" : "실패"));
				ses.invalidate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			destPage = "/";
			break;
			
		case "modify":
			try {
				log.info(">>> modify page 이동");	
				HttpSession ses = request.getSession();
				//마지막 로그인 시간 기록
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				ses.setAttribute("ses", mvo2);	
			} catch(Exception e) {
				e.printStackTrace();
			}
			destPage = "/member/modify.jsp";
			break;
			
		case "edit":
			try {	
				String id2 = request.getParameter("id");
				String password2 = request.getParameter("password");
				String email2 = request.getParameter("email");
				int age2 = Integer.parseInt(request.getParameter("age"));
				MemberVO mvo3 = new MemberVO(id2, password2, email2, age2);
				log.info(">>>>> "+mvo3);
				isOk = msv.edit(mvo3);
				log.info(">>> edit > " + (isOk > 0 ? "성공" : "실패"));		
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/";
			break;
		case "delete":
			try {
				HttpSession ses = request.getSession();
				//마지막 로그인 시간 기록
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				String id2 = mvo2.getId();
				ses.invalidate(); //세션 로그아웃
				isOk = msv.delete(id2);
				log.info(">>> 회원탈퇴" + (isOk > 0 ? "성공" : "실패"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/";
			break;
			
		case "list":
			try {
				List<MemberVO> list = msv.list();
				request.setAttribute("list", list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/member/list.jsp";
			break;
		}	
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
