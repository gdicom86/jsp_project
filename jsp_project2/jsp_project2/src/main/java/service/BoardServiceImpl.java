package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int insert(BoardVO bvo) {
		log.info(">>> register service 진입");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> list() {
		log.info(">>> list print service 진입");
		return bdao.selectList();
	}

	@Override
	public BoardVO detail(int bno) {
		log.info(">>> detail service 진입");
		return bdao.selectOne(bno);
		
	}

	@Override
	public BoardVO modify(int bno) {
		log.info(">>> detail service 진입");
		return bdao.select(bno);
		
	}

	@Override
	public int edit(BoardVO bvo) {
		log.info(">>> edit service 진입");
		return bdao.edit(bvo);
	}

	@Override
	public int delete(int bno) {
		log.info(">>> delete service 진입");
		return bdao.delete(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info(">>> get total service 진입");
		return bdao.getTotal(pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		log.info(">>> get page list service 진입");
		return bdao.pageList(pgvo);
	}

	



}
