package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import service.CommentService;
import service.CommentServiceImpl;


@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger log = LoggerFactory.getLogger(CommentController.class);
   //목적지 주소로 보내지 않기때문에 destPage 없음
   //비동기 통신으로 요청이 온 곳으로 다시 돌려보냄
   
   private int isOk;
   private CommentService csv;
   
    public CommentController() {
        csv = new CommentServiceImpl();
    }


   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      response.setCharacterEncoding("utf-8"); 
      //response.setContentType("text/html; charset=utf-8"); 
      //contentType은 사용하지 않을 예정!
      
      
      String uri = request.getRequestURI();
      log.info(uri);
      //cmt/post/1 모양으로 가져올 예정 (예: cmt/modify/1)
      //= REST API 방식 (비동기식방식)
      
      String pathUri = uri.substring("/cmt/".length());  
      String path = ""; //post (앞의 기능)
      String pathVar = ""; //1 (숫자)
      
      if(pathUri.contains("/")) { // "/"이 있는 경우 (맨뒤에 숫자가 오는경우)
         path = pathUri.substring(0, pathUri.lastIndexOf("/"));//modify
         pathVar = pathUri.substring(pathUri.lastIndexOf("/")+1); // 1
      }else { // "/"가 없는 경우 (맨뒤에 숫자가 없는 경우)
         path = pathUri;
      }
      
      log.info(">>pathUri: " +pathUri);
      log.info(">>path: " +path);
      log.info(">>>pathVar: "+pathVar);
      
      
      switch(path) {
      case "post":
         try {
            //js에서 보낸 데이터를 StringBuffer로 읽어들이는 작업
            StringBuffer sb = new StringBuffer();
            String line = "";
            BufferedReader br = request.getReader(); //보낸 댓글 객체를 읽어와
            while((line = br.readLine()) != null) {
               //읽을것이 없을때까지 (값이 없을때까지)
               sb.append(line); //버퍼에 추가해   
            }
            log.info(">>>sb: " + sb.toString());
            
            //객체로 생성
            //JSON <-> text 변경
            JSONParser parser = new JSONParser(); //key,value로 만들기 위해
            JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());
            
            int bno = Integer.parseInt(jsonObj.get("bno").toString()); //get("key")으로 가져온 value를 toString으로 바꿔준다. 
            String writer = jsonObj.get("writer").toString();
            String content = jsonObj.get("content").toString();
            CommentVO cvo = new CommentVO(bno,writer,content);
            
            isOk = csv.post(cvo);
            log.info(">>>post: "+(isOk>0 ? "성공" : "실패"));
            
            //결과 전송
            PrintWriter out = response.getWriter();
            out.print(isOk);
            
         } catch (Exception e) {
            log.info("controller error");
            e.printStackTrace();
         }
         break;
      case "list":
    	  try {
    		  int bno = Integer.parseInt(pathVar);
    		  List<CommentVO> list = csv.getList(bno);
    		  log.info(">>>>> Comment List > DB ok");
    		  
    		  //json 형태로 변환해서 printWriter
    		  JSONObject[] jsonObjArr = new JSONObject[list.size()];
    		  JSONArray jsonObjList = new JSONArray();
    		  for(int i = 0; i < list.size(); i++) {
    			  jsonObjArr[i]  = new JSONObject(); //key: value
    			  jsonObjArr[i].put("cno", list.get(i).getCno());
    			  jsonObjArr[i].put("bno", list.get(i).getBno());
    			  jsonObjArr[i].put("writer", list.get(i).getWriter());
    			  jsonObjArr[i].put("content", list.get(i).getContent());
    			  jsonObjArr[i].put("reg_date", list.get(i).getReg_date());
    			  
    			  jsonObjList.add(jsonObjArr[i]);
    		  }
    		  log.info(">>> jsonObjList" + jsonObjList);
    		  
    		  String jsonData = jsonObjList.toJSONString();
    		  PrintWriter out = response.getWriter();
    		  out.print(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	break;
    	
      case "remove":	
    	  try {	
    		  int cno = Integer.parseInt(request.getParameter("cnoVal")); //쿼리스트링방식
    		  log.info(">>> cno > " + cno);
    		  
    		  isOk = csv.remove(cno);
    		  log.info(" >>> remove > " + ((isOk > 0)? "OK" : "Fail"));
    		  PrintWriter out = response.getWriter();
    		  out.print(isOk);
		} catch (Exception e) {
			log.info("remove error");
			e.printStackTrace();
		} 	  
    	break;
    	  
      case "modify":
    	  try {
    		  StringBuffer sb = new StringBuffer();
              String line = "";
              BufferedReader br = request.getReader(); //보낸 댓글 객체를 읽어와
              while((line = br.readLine()) != null) {
                 //읽을것이 없을때까지 (값이 없을때까지)
                 sb.append(line); //버퍼에 추가해   
              }
              log.info(">>>sb: " + sb.toString());
              
              //객체로 생성
              //JSON <-> text 변경
              JSONParser parser = new JSONParser(); //key,value로 만들기 위해
              JSONObject jsonObj = (JSONObject) parser.parse(sb.toString());
              
              int cno = Integer.parseInt(jsonObj.get("cno").toString()); //get("key")으로 가져온 value를 toString으로 바꿔준다. 
              String writer = jsonObj.get("writer").toString();
              String content = jsonObj.get("content").toString();
              CommentVO cvo = new CommentVO(writer, cno, content);
              log.info(">>>>>cvo"+cvo);
              isOk = csv.modify(cvo);
              log.info(">>>modify: "+(isOk>0 ? "성공" : "실패"));
              
              //결과 전송
              PrintWriter out = response.getWriter();
              out.print(isOk);
		} catch (Exception e) {
			log.info("modify error");
			e.printStackTrace();
		}
    	  break;
      }
   }


   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      service(request, response);
   }


   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      service(request, response);
   }

}