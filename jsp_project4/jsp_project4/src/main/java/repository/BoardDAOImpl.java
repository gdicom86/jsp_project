package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	private SqlSession sql;
	private String NS = "BoardMapper.";
	
	public BoardDAOImpl() {
		new DatabaseBuilder(); 
		sql = DatabaseBuilder.getFactory().openSession(); 
	}
	
	@Override
	public int insert(BoardVO bvo) {
		int isOk = sql.insert(NS+ "ins", bvo); 
		if(isOk > 0 ) { 
			sql.commit(); 
		} 
		
		return isOk;
	}

	@Override
	public List<BoardVO> selectList() {
		log.info(">>> list DAO 진입");
		return sql.selectList(NS+"list"); 
	}

	@Override
	public BoardVO selectOne(int bno) {
		log.info(">>> select one DAO 진입");
		sql.update(NS +"edit1", bno);
		return sql.selectOne(NS + "detail", bno);
	}

	@Override
	public BoardVO select(int bno) {
		log.info(">>> select DAO 진입");
		return sql.selectOne(NS + "modify", bno);
	}

	@Override
	public int edit(BoardVO bvo) {
		log.info(">>> edit DAO 진입");
		return sql.selectOne(NS + "edit", bvo);
	}

	@Override
	public int delete(int bno) {
		log.info(">>> delete DAO 진입");
		return sql.selectOne(NS + "delete", bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info(">>> getTotal DAO 진입");
		return sql.selectOne(NS + "cnt", pgvo);
	}

	@Override
	public List<BoardVO> pageList(PagingVO pgvo) {
		log.info(">>> pageList DAO 진입");
		return sql.selectList(NS + "selectList", pgvo);
	}

	


}
