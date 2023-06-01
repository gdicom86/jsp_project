package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.MemberVO;
import domain.PagingVO;
import handler.FileHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private RequestDispatcher rdp; 
    private String destPage; 
    private int isOk; 
    private BoardService bsv; 
    
   
    private String savePath;
    private final String UTF8 = "utf-8"; 
	
    public BoardController() {
        bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI();
		log.info(">>> uri" + uri);
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(">>> path" + path);
		
		switch(path) {
		case "register":
			destPage = "/board/register.jsp";
			break;
		
		case "insert":
			try {
				
				savePath = getServletContext().getRealPath("/_fileUpload");
				log.info(">>>> 파일 저장 경로:" + savePath);
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				
				fileItemFactory.setRepository(fileDir); 
				
				fileItemFactory.setSizeThreshold(2*1024*1024); 
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "title":
						bvo.setTitle(item.getString(UTF8)); 
						break;
					case "writer":
						bvo.setWriter(item.getString(UTF8)); 
						break;
					case "content":
						bvo.setContent(item.getString(UTF8)); 
						break;
					case "image_file":
						
						if(item.getSize() > 0) { 
							
							String fileName = item.getName().substring(item.getName().lastIndexOf("/")+1); 
							fileName = System.currentTimeMillis()+"_"+fileName;
							log.info(">>> fileName: " + fileName);
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							log.info("실제 파일경로: " + uploadFilePath);
							
							
							try {
								item.write(uploadFilePath); 
								bvo.setImage_file(fileName);
								
								
								Thumbnails.of(uploadFilePath).size(75, 75)
								.toFile(new File(fileDir+File.separator+"th_" + fileName));
								
								
							} catch (Exception e) {
								log.info(">>> file writer on disk fail");
								e.printStackTrace();
							}
						}
						break;
					}
				}
				isOk = bsv.insert(bvo);
				log.info(">>> Insert > " + (isOk > 0 ? "성공" : "실패"));

			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "page";
			break;
			
			
			
		case "list":
			try {
				List<BoardVO> list = bsv.list();
				request.setAttribute("list", list);
				
				log.info(">>> list 출력 > " + (isOk > 0 ? "성공" : "실패"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/board/list.jsp";
			break;	
			
		case "page":
			try {
				
				int pageNo = 1;
				int qty = 10;
				String type = "";
				String keyword = "";
				if(request.getParameter("type") != null) {
					type = request.getParameter("type");
					keyword = request.getParameter("keyword");
					log.info(">>>> type > " + type + ">>>> keyword >" +keyword);
				}
				if(request.getParameter("pageNo") != null) {
					pageNo = Integer.parseInt(request.getParameter("pageNo"));
					qty = Integer.parseInt(request.getParameter("qty"));
				}
				PagingVO pgvo = new PagingVO(pageNo, qty);
				pgvo.setType(type);
				pgvo.setKeyword(keyword);			
				
				
				int totCount = bsv.getTotal(pgvo); 
				log.info("전페 페이지 개수: " + totCount);
				
				List<BoardVO> list = bsv.getPageList(pgvo);
				log.info(">>> list: " + list.size());
				PagingHandler ph = new PagingHandler(pgvo, totCount);
				request.setAttribute("pgh", ph);
				request.setAttribute("list", list);
				log.info("pageList 성공");
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "detail":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				BoardVO b = new BoardVO();			
				b = bsv.detail(bno);
				request.setAttribute("bvo", b);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/board/detail.jsp";
			break;	
			
		case "modify":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				BoardVO b = new BoardVO();
				b = bsv.modify(bno);
				request.setAttribute("bvo", b);
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/board/modify.jsp";
			break;	
			
		case "edit":
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(2*1024*1024);
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				log.info(">>> update 준비");
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "bno":
						bvo.setBno(Integer.parseInt(item.getString(UTF8)));
						break;
					case "title":
						bvo.setTitle(item.getString(UTF8));
						break;
					case "content":
						bvo.setContent(item.getString(UTF8));
						break;
					case "image_file":
						old_file = item.getString(UTF8);
						break;
					case "new_file":
						if(item.getSize() > 0) { 
							if(old_file != null) {		
								
								FileHandler fileHandler = new FileHandler();
								isOk = fileHandler.deleteFile(old_file, savePath);
							}
						
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							log.info(">>> new_fileName: " + fileName);
							
							fileName = System.currentTimeMillis()+"_"+fileName;
							File uploadFilePath = new File(fileDir + File.separator+fileName);
							try {
								item.write(uploadFilePath);
								bvo.setImage_file(fileName);
								log.info(">>> bvo.image_file > " + bvo.getImage_file());
								
								Thumbnails.of(uploadFilePath).size(75, 75)
								.toFile(new File(fileDir+File.separator+"th_"+fileName));
							} catch (Exception e2) {
								log.info(">>> file update on disk fail");
								e2.printStackTrace();
							}
						} else { 
							bvo.setImage_file(old_file);
						}
						break;
					}
				}
				
				isOk = bsv.edit(bvo);
				log.info(">>> edit > " + (isOk > 0 ? "성공" : "실패"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/brd/page";
			break;	
			
		case "remove":
			try {
				int bno = Integer.parseInt(request.getParameter("bno")) ;
				savePath = getServletContext().getRealPath("/_fileUpload");
				String imageFileName = request.getParameter("img");
				FileHandler fh = new FileHandler();
				isOk = fh.deleteFile(imageFileName, savePath);
				log.info(">>> file Delete > " + (isOk > 0 ? "성공" : "실패"));
				
				isOk = bsv.delete(bno);
				log.info(">>> delete > " + (isOk > 0 ? "성공" : "실패"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			destPage = "/brd/page";
			break;	
		
		}	
		
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
