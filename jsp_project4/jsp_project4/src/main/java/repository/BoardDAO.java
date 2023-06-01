package repository;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList();

	BoardVO selectOne(int bno);

	BoardVO select(int bno);

	int edit(BoardVO bvo);

	int delete(int bno);

	int getTotal(PagingVO pgvo);

	List<BoardVO> pageList(PagingVO pgvo);


}
