package ${package}.core.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

/** 
 * Ibatis SqlExecutor 执行sql的类，实现物理分页，重写executeQuery
 * 组装sql
 * @author lim
 * @ClassName: LimitSqlExecutor 
 * @version 1.0 2012-8-9 上午10:00:27
 * @lastUpdateTime 2012-8-9 上午10:00:27 
 */
public class LimitSqlExecutor extends SqlExecutor {
	
	private static final Logger logger = Logger.getLogger(LimitSqlExecutor.class);

	private Dialect dialect;

	private boolean enableLimit = true;

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}
	
	/**
	 * 重写ibatis执行sql方法
	 * @param statementScope
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param skipResults
	 * @param maxResults
	 * @param callback
	 * @throws SQLException 
	 * @see com.ibatis.sqlmap.engine.execution.SqlExecutor#executeQuery(com.ibatis.sqlmap.engine.scope.StatementScope, java.sql.Connection, java.lang.String, java.lang.Object[], int, int, com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback)
	 */
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS) && supportsLimit()) {
			sql = dialect.getLimitString(sql, skipResults, maxResults);
			if (logger.isDebugEnabled()) {
				logger.debug(sql);
			}
			/** 设置skipResults为SqlExecutor不分页   设置maxResults为SqlExecutor不分页 */
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
//		System.out.println(sql);
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,maxResults, callback);
	}
	/**
	 * @return 
	 * @author lim
	 */
	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return dialect.supportsLimit();
		}
		return false;
	}
}
