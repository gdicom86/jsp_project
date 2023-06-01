package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;


public class CommentDAOImpl implements CommentDAO {
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
    private SqlSession sql;
    private final String NS = "CommentMapper.";
    private int isOk;
    
    
    public CommentDAOImpl() {
       new DatabaseBuilder();
       sql = DatabaseBuilder.getFactory().openSession();
    }


	@Override
	public int post(CommentVO cvo) {
		isOk = sql.insert(NS + "post", cvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}


	@Override
	public List<CommentVO> list(int bno) {
		return sql.selectList(NS + "list", bno);
	}


	@Override
	public int remove(int cno) {
		return sql.delete(NS + "del", cno);
	}


	@Override
	public int modify(CommentVO cvo) {
		isOk = sql.update(NS + "modify", cvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
}
