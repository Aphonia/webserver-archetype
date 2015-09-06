package ${package}.core.ibatis;

/**
 * mysql物理分页实现
 * @author lim
 * @ClassName: OracleDialect
 * @version 1.0 2012-8-8 下午03:54:51
 * @lastUpdateTime 2012-8-8 下午03:54:51
 */
public class MysqlDialect implements Dialect {

	protected static final String SQL_END_DELIMITER = ";";

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuffer(sql.length() + 20).append(trim(sql)).append(
				hasOffset ? " limit ?,?" : " limit ?")
				.append(SQL_END_DELIMITER).toString();
	}

	/**
	 * 拦截sql组装分页
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return 
	 * @see cn.com.topinfo.exam.code.ibatis.Dialect#getLimitString(java.lang.String, int, int)
	 */
	public String getLimitString(String sql, int offset, int limit) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" limit ").append(limit * (offset-1)).append(",").append(limit);
		return sb.toString();
	}

	public boolean supportsLimit() {
		return true;
	}
	
	/**
	 * 去掉分号
	 * @param sql
	 * @return 
	 * @author lim
	 */
	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1
					- SQL_END_DELIMITER.length());
		}
		return sql;
	}

}
