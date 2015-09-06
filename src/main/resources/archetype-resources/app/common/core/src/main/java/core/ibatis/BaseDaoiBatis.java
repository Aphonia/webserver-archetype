/**  
 * @Title: BaseDaoiBatis.java 
 * @author Administrator  
 * @date 2013-8-8 下午09:20:56 
 * @version V1.0   
 */
package ${package}.core.ibatis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import ${package}.core.utils.ReflectUtil;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

/** 
 * ibatis基类
 * @author lim
 * @ClassName: BaseDaoiBatis 
 * @version 1.0 2013-8-9 上午10:04:49
 * @lastUpdateTime 2013-8-9 上午10:04:49 
 */
@SuppressWarnings({ "deprecation"})
public abstract class BaseDaoiBatis {
	
	/** ibatis执行sql类 */
	@Resource
	private SqlExecutor sqlExecutor;
	
	/**  */
	@Resource
	protected SqlMapClient sqlMapClient;

	
	/**
	 * 把dialect注入到ibatis的sqlExecutor
	 * @throws Exception 
	 * @author lim
	 */
	@PostConstruct
	public void initialize() throws Exception {
		if (sqlExecutor != null) {
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
						.getDelegate(), "sqlExecutor", SqlExecutor.class,
						sqlExecutor);
			}
		}
	}
	

	public void setEnableLimit(boolean enableLimit) {
		if (sqlExecutor instanceof LimitSqlExecutor) {
			((LimitSqlExecutor) sqlExecutor).setEnableLimit(enableLimit);
		}
	}

}
