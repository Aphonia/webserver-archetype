package ${package}.core.ibatis;

/** 
 * 使用hibernate方言机制实现各种数据库分页
 * @author lim
 * @ClassName: Dialect 
 * @version 1.0 2012-8-9 上午10:01:48
 * @lastUpdateTime 2012-8-9 上午10:01:48 
 */
public interface Dialect {
	/**
	 * 是否分页
	 * @return 
	 * @author lim
	 */
	public boolean supportsLimit();
	/**
	 * 从什么位置开始
	 * @param sql
	 * @param hasOffset
	 * @return 
	 * @author lim
	 */
	public String getLimitString(String sql, boolean hasOffset);
	/**
	 * 实现物理分页
	 * @param sql
	 * @param offset 开始
	 * @param limit	结束
	 * @return sql
	 * @author lim
	 */
	public String getLimitString(String sql, int offset, int limit);
}
