package orm;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseBuilder {
	
	//log 표현하도록 설정
	private static Logger log = LoggerFactory.getLogger(DatabaseBuilder.class);
	
	private static SqlSessionFactory factory;
	
	//경로 넣기 (경로는 일반적으로 final)
	//패키지는 / , 폴더는 .
	private static final String config = "orm/MybatisConfig.xml";
	
	static {
		try {
			factory = new SqlSessionFactoryBuilder().build(
					Resources.getResourceAsReader(config));
					
		} catch (IOException e) {
			
			log.info("mybatis 설정 오류"); //log.info == sysout
			e.printStackTrace();
			
		}
	}
	
	public static SqlSessionFactory getFactory() {
		return factory;
	}

	
	
}
