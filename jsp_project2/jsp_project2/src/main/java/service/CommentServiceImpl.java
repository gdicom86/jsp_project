package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import repository.CommentDAO;
import repository.CommentDAOImpl;

public class CommentServiceImpl implements CommentService {
	
	private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    
    private CommentDAO cdao;
    
    
    public CommentServiceImpl() {
       cdao = new CommentDAOImpl();
    }
	@Override
	public int post(CommentVO cvo) {
		log.info(">>> Commend Post service 진입");
		return cdao.post(cvo);	
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info(">>> Get List service 진입");
		return cdao.list(bno);	
	}
	@Override
	public int remove(int cno) {
		log.info(">>> remove service 진입");
		return cdao.remove(cno);	
	}
	@Override
	public int modify(CommentVO cvo) {
		log.info(">>> modify service 진입");
		return cdao.modify(cvo);	
	}}
