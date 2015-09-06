/**  
 * @Title: BaseDao.java 
 * @author Administrator  
 * @date 2013-7-30 下午01:30:32 
 * @version V1.0   
 */
package ${package}.core.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ${package}.core.ibatis.BaseDaoiBatis;
import ${package}.core.utils.PropertyUtil;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;

/**
 * 基础DAO抽象支持类(用户不直接使用)
 * @version $Id$
 * @since 2.0
 * @date 2013-6-25 下午04:59:42
 * @updateInfo
 */
public abstract class BaseDaoSupport<T> extends BaseDaoiBatis  {
	
	
	/** 泛型类 */
	private final Class<T> clazz;
	
	private SqlExecutor sqlExecutor;

	/** 构造函数 */
	public BaseDaoSupport(Class<T> clazz) {
		this.clazz = clazz;
	}

	/** 查找PO */
	@SuppressWarnings("unchecked")
	public T findPO(Serializable id) throws Exception {
		T o = (T) super.sqlMapClient.queryForObject(this.clazz.getSimpleName() + ".query_id", id);
		return o;
	}

	/** 创建PO */
	public Serializable createPO(T t) throws Exception {
		return this.createPO(t, "add");
	}
	
	/** 返回主键 */
	public Serializable createPO(T t,String sqlId) throws Exception{
		return (Serializable) sqlMapClient.insert(this.clazz.getSimpleName() + "." + sqlId, t);
	}

	/** 删除PO */
	public int deletePO(T t) throws Exception {
		return this.deletePO(t, "delete");
	}
	
	public int deletePO(T t,String sqlId) throws Exception{
		return sqlMapClient.delete(t.getClass().getSimpleName() + "." + sqlId, t);
	}
	
	public int deletePO(Map<String, Object> params) throws Exception {
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		return sqlMapClient.delete(this.clazz.getSimpleName() + ".delete_map", params);
	}

	/** 更新PO */
	public int updatePO(T t) throws Exception {
		return this.updatePO(t, "update");
	}
	
	public int updatePO(T t ,String sqlId) throws Exception{
		return sqlMapClient.update(t.getClass().getSimpleName() + "." +sqlId, t);
	}

	/** 查询所有 */
	@SuppressWarnings("unchecked")
	public List<T> queryAll() throws Exception {
		return sqlMapClient.queryForList(this.clazz.getSimpleName()
				+ ".query_all");
	}

	/** 分页 */
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(Map<String, Object> params,Integer currPage,Integer pageSize) throws Exception {
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		if(currPage == -1 && pageSize == -1){
			return sqlMapClient.queryForList(this.clazz.getSimpleName()+".query",params);
		}
		return sqlMapClient.queryForList(this.clazz.getSimpleName() + ".query", params, currPage, pageSize);
	}
	
	/** 调用sql */
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(Map<String,Object> params,String sqlName,Integer currPage,Integer pageSize) throws Exception{
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		if(currPage == -1 && pageSize == -1){
			return sqlMapClient.queryForList(this.clazz.getSimpleName()+"."+sqlName,params);
		}
		return sqlMapClient.queryForList(this.clazz.getSimpleName()+"."+sqlName,params,currPage,pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> queryByPage(List<Object> params , Integer currPage,Integer pageSize) throws Exception{
		if (params == null) {
			params = new ArrayList<Object>();
		}
		if(currPage == -1 && pageSize == -1){
			return sqlMapClient.queryForList(this.clazz.getSimpleName()+".queryByList",params);
		}
		return sqlMapClient.queryForList(this.clazz.getSimpleName() + ".queryByList", params, currPage, pageSize);
	}

	/** 查询总数 */
	public Integer queryByCount(Map<String,Object> params) throws Exception {
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		return (Integer) sqlMapClient.queryForObject(this.clazz.getSimpleName()+ ".count",params);
	}
	
	public Integer queryByCount(Map<String, Object> params, String sqlName) throws Exception {
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		return (Integer) sqlMapClient.queryForObject(this.clazz.getSimpleName() + "." + sqlName, params);
	}
	
	/** 查询序列 */
	public Integer queryByDual() throws Exception{
		return (Integer) sqlMapClient.queryForObject(this.clazz.getSimpleName()+".dual");
	}
	
	public String queryByDualForString() throws Exception{
		return (String) sqlMapClient.queryForObject(this.clazz.getSimpleName()+".dual");
	}
	/** 返回object */
	public Object queryByObject(String sqlId) throws Exception{
		return (Object) sqlMapClient.queryForObject(this.clazz.getSimpleName()+"."+sqlId);
	}
	
	@SuppressWarnings("unchecked")
	public T queryByPO(Map<String,Object> params) throws SQLException{
		if (params == null) {
			params = new HashMap<String,Object>();
		}
		List<T> ts = this.sqlMapClient.queryForList(this.clazz.getSimpleName() +".query", params);
		if(ts != null && ts.size() > 0){
			return ts.get(0);
		}
		return null;
	}
	
	
	/** 批量创建po */
	public int batchCreatePO(List<T> list) throws Exception{
		int result = 1;
		try {
			//将事务设置不提交
//			sqlMapClient.getDataSource().getConnection().setAutoCommit(false);
			//开启事务
			sqlMapClient.startTransaction();   
			//开始批处理
			sqlMapClient.startBatch();        
			for(T t : list){
				if (PropertyUtil.containsField(t.getClass(), "createTime")) {
					PropertyUtil.setProperty(t, "createTime", new Date());
				}
				if (PropertyUtil.containsField(t.getClass(), "modifyTime")) {
					PropertyUtil.setProperty(t, "modifyTime", new Date());
				}
				sqlMapClient.insert(this.clazz.getSimpleName() + ".add", t);
			}
			//结束批处理
			sqlMapClient.executeBatch();
			//提交事务
			sqlMapClient.commitTransaction();
		} catch (Exception e) {
			result=0;
			e.printStackTrace();
		} finally {
			try {
				// 结束事务
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return result;
	}
	
	/** 批量更新po */
	public int batchUpdatePO(List<T> list) throws Exception{
		int result = 1;
		try {
			//将事务设置不提交
//			sqlMapClient.getDataSource().getConnection().setAutoCommit(false);
			//开启事务
			sqlMapClient.startTransaction();   
			//开始批处理
			sqlMapClient.startBatch();        
			for(T t : list){
				if (PropertyUtil.containsField(t.getClass(), "modifyTime")) {
					PropertyUtil.setProperty(t, "modifyTime", new Date());
				}
				sqlMapClient.update(this.clazz.getSimpleName() + ".update", t);
			}
			//结束批处理
			sqlMapClient.executeBatch();
			//提交事务
			sqlMapClient.commitTransaction();
		} catch (Exception e) {
			result=-1;
			e.printStackTrace();
		} finally {
			try {
				// 结束事务
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return result;
	}
	
	/** 批量删除po */
	public int batchDeletePO(List<T> list) throws Exception{
		int result = 1;
		try {
			//将事务设置不提交
//			sqlMapClient.getDataSource().getConnection().setAutoCommit(false);
			//开启事务
			sqlMapClient.startTransaction();   
			//开始批处理
			sqlMapClient.startBatch();        
			for(T t : list){
				sqlMapClient.delete(this.clazz.getSimpleName() + ".delete", t);
			}
			//结束批处理
			sqlMapClient.executeBatch();
			//提交事务
			sqlMapClient.commitTransaction();
		} catch (Exception e) {
			result=-1;
			e.printStackTrace();
		} finally {
			try {
				// 结束事务
				sqlMapClient.endTransaction();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return result;
	}

	/** 
	 * @return sqlExecutor 
	 */
	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	/**
	 * @param sqlExecutor the sqlExecutor to set
	 */
	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	
	

}
