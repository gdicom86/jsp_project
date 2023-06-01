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


@WebServlet("/mem/*") 
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp;
	private MemberService msv;
	private int isOk; //확인
	private String destPage; //목적지주소
	
	
  
    public MemberController() {
        msv = new MemberServiceImp(); //멤버의 서비스를 구현한 객체
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI(); 
		System.out.println(">>> uri  " + uri);
		
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
				
				MemberVO mvo = new MemberVO(id,password, email,age);
			
				isOk = msv.register(mvo);
				log.info(">>> JOIN > " + (isOk > 0 ? "성공" : "실패"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/"; 
			break;
			
		case "login":
			log.info(">>> LogIn page 이동");
			destPage = "/member/login.jsp";
			break;
			
		case "login_auth": 
			try {		
				String id2 = request.getParameter("id");
				String password2 = request.getParameter("password");
				MemberVO mvo2 = new MemberVO(id2, password2);
			
				log.info(">>> login 요청"+mvo2);
				MemberVO loginMvo = msv.login(mvo2);
				
				if(loginMvo != null) {		
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					
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
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				String id2 = mvo2.getId();
				log.info(">>> login id: " + id2);
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
				
				MemberVO mvo2 = (MemberVO)ses.getAttribute("ses");
				String id2 = mvo2.getId();
				ses.invalidate(); 
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
