package ${package}.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * baseService基础类
 * @author lim
 * @param <V,T,P>
 */
public interface BaseService<V,T,P> {
	/**
	 * 创建po
	 * @param vo
	 * @return 
	 * @author lim
	 */
	Serializable create(V vo);
	
	/**
	 * 批量添加
	 * @param vo
	 */
	int batchCreate(List<V> vos);
	
	int batchUpdate(List<V> vos);
	
	int batchDelete(List<V> vos);
	/**
	 * 删除po
	 * @param vo
	 * @param soft
	 * @return 
	 * @author lim
	 */
	 Integer delete(V vo,boolean soft);
	 
	 /**
	  * 删除po
	  * @param params
	  * @return
	  */
	 int delete(Map<String,Object> params);
	/**
	 * 更新po
	 * @param vo 
	 * @author lim
	 */
	 Integer update(V vo);
	/**
	 * 查询分页
	 * @param params
	 * @param currPage
	 * @param pageSize
	 * @return 
	 * @author lim
	 */
	 List<T> queryByPage(Map<String,Object> params,int currPage,int pageSize);
	 /**
	  * 调用指定sql查询分页
	  * @param params
	  * @param sqlId
	  * @param currPage
	  * @param pageSize
	  * @return
	  */
	 List<T> queryByPage(Map<String,Object> params,String sqlId,int currPage,int pageSize);
	/**
	 * 查询总数
	 * @param params
	 * @return 
	 * @author lim
	 */
	 Integer queryByCount(Map<String,Object> params);
	 
	 /**
	  * 调用指定sql查询总数
	  * @param params
	  * @param sqlId
	  * @return
	  */
	 Integer queryByCount(Map<String,Object> params,String sqlId);
	/**
	 * 根据id查询对象
	 * @param id
	 * @return 
	 * @author lim
	 */
	 T findById(Serializable id);
	 /**
	  * 恢复删除的
	  * @param id
	  * @return
	  */
	 int restoreDel(V vo);
	 
	 T queryByPO(Map<String,Object> params);
	 
	 String queryByDualForString();
	 
	 Object queryByObject(String sqlId);

}
