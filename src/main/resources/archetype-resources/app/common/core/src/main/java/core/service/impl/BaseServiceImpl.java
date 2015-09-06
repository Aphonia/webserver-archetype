package ${package}.core.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import ${package}.core.dao.BaseDao;
import ${package}.core.service.BaseService;
import ${package}.core.utils.DozerBeanUtil;
import ${package}.core.utils.PropertyUtil;


/**
 * baseService实现
 * @author lim
 */
public class BaseServiceImpl<V,T,P> implements BaseService<V,T,P>{
	
	private Class<P> pojoClazz;
	private Class<T> dtoClazz;
	private BaseDao<P> baseDao;
	private DozerBeanUtil dozerBeanUtil;
	
	/** log4j */
	private final static Logger LOGGER = Logger.getLogger(BaseServiceImpl.class);
	
	public BaseServiceImpl(Class<P> pojoClazz,Class<T> dtoClazz) {
		this.pojoClazz = pojoClazz;
		this.dtoClazz = dtoClazz;
	}

	public Serializable create(V vo) {
		try {
			P pojo = this.dozerBeanUtil.convert(vo, pojoClazz);
			Serializable s = this.baseDao.createPO(pojo);
			return s;
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return null;
	}

	public Integer delete(V vo,boolean soft) {
		try {
			P pojo = this.dozerBeanUtil.convert(vo, pojoClazz);
			if(soft){
				//判断是否存在软删除字段
				if (PropertyUtil.containsField(pojo.getClass(), "isDel")) {
					PropertyUtil.setProperty(pojo, "isDel", 1);
				}
				return this.baseDao.updatePO(pojo);
			}
			return this.baseDao.deletePO(pojo);
		} catch (Exception e) {
			LOGGER.error("删除数据失败！",e);
		}
		return -1;
	}

	public T findById(Serializable id) {
		try {
			T dto = this.dozerBeanUtil.convert(this.baseDao.findPO(id),dtoClazz);
			return dto;
		} catch (Exception e) {
			LOGGER.error("查询单条数据失败！",e);
		}
		return null;
	}

	public Integer queryByCount(Map<String, Object> params) {
		try {
			return this.baseDao.queryByCount(params);
		} catch (Exception e) {
			LOGGER.error("查询总数失败！",e);
		}
		return null;
	}

	public List<T> queryByPage(Map<String, Object> params, int currPage,int pageSize) {
		try {
			List<P> pojos = this.baseDao.queryByPage(params, currPage, pageSize);
			if(CollectionUtils.isNotEmpty(pojos)){
				List<T> dtos = this.dozerBeanUtil.convertList(pojos,dtoClazz);
				return dtos;
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("查询分页失败！",e);
		}
		return null;
	}

	public Integer update(V vo) {
		P pojo = this.dozerBeanUtil.convert(vo, pojoClazz);
		try {
			return this.baseDao.updatePO(pojo);
		} catch (Exception e) {
			LOGGER.error("更新数据失败！",e);
		}
		return -1;
	}
	
	public int restoreDel(V vo){
		P pojo = this.dozerBeanUtil.convert(vo, pojoClazz);
		// 判断是否存在软删除字段
		try {
			if (PropertyUtil.containsField(pojo.getClass(), "isDel")) {
				PropertyUtil.setProperty(pojo, "isDel", 0);
				return this.baseDao.updatePO(pojo);
			}
			return -1;
		} catch (IllegalAccessException e) {
			LOGGER.error("恢复删除数据失败！",e);
		} catch (InvocationTargetException e) {
			LOGGER.error("恢复删除数据失败！",e);
		} catch (NoSuchMethodException e) {
			LOGGER.error("恢复删除数据失败！",e);
		} catch (Exception e) {
			LOGGER.error("恢复删除数据失败！",e);
		}
		return -1;
	}

	public void setBaseDao(BaseDao<P> baseDao) {
		this.baseDao = baseDao;
	}
	
	public void setDozerBeanUtil(DozerBeanUtil dozerBeanUtil) {
		this.dozerBeanUtil = dozerBeanUtil;
	}

	public int batchCreate(List<V> vos) {
		List<P> pojos = this.dozerBeanUtil.convertList(vos, pojoClazz);
		try {
			return this.baseDao.batchCreatePO(pojos);
		} catch (Exception e) {
			LOGGER.error("批量添加数据失败！",e);
		}
		return -1;
	}

	/**
	 * @param params
	 * @return
	 * @see ${package}.core.service.BaseService#delete(java.util.Map)
	 */
	public int delete(Map<String, Object> params) {
		try {
			return this.baseDao.deletePO(params);
		} catch (Exception e) {
			LOGGER.error("删除数据失败！",e);
		}
		return -1;
	}

	/**
	 * 调用指定sql查询分页
	 * @param params
	 * @param sqlId
	 * @param currPage
	 * @param pageSize
	 * @return
	 * @see ${package}.core.service.BaseService#queryByPage(java.util.Map, java.lang.String, int, int)
	 */
	public List<T> queryByPage(Map<String, Object> params, String sqlId,int currPage, int pageSize) {
		try {
			List<P> pojos = this.baseDao.queryByPage(params,sqlId, currPage, pageSize);
			if(CollectionUtils.isNotEmpty(pojos)){
				List<T> dtos = this.dozerBeanUtil.convertList(pojos,dtoClazz);
				return dtos;
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("查询分页失败！",e);
		}
		return null;
	}

	/**
	 * 调用指定sql查询总数
	 * @param params
	 * @param sqlId
	 * @return
	 * @see ${package}.core.service.BaseService#queryByCount(java.util.Map, java.lang.String)
	 */
	public Integer queryByCount(Map<String, Object> params, String sqlId) {
		try {
			return this.baseDao.queryByCount(params, sqlId);
		} catch (Exception e) {
			LOGGER.error("查询总数失败！",e);
		}
		return -1;
	}

	/**
	 * @param vos
	 * @return
	 * @see ${package}.core.service.BaseService#batchUpdate(java.util.List)
	 */
	public int batchUpdate(List<V> vos) {
		List<P> pojos = this.dozerBeanUtil.convertList(vos, pojoClazz);
		try {
			return this.baseDao.batchUpdatePO(pojos);
		} catch (Exception e) {
			LOGGER.error("批量修改数据失败！",e);
		}
		return -1;
	}

	/** 
	 * @see ${package}.core.service.BaseService#queryByPO(java.util.Map)
	 */
	@Override
	public T queryByPO(Map<String, Object> params) {
		try {
			T dto = this.dozerBeanUtil.convert(this.baseDao.queryByPO(params),dtoClazz);
			return dto;
		} catch (Exception e) {
			LOGGER.error("查询单条数据失败！",e);
		}
		return null;
	}

	/** 
	 * @see ${package}.core.service.BaseService#batchDelete(java.util.List)
	 */
	@Override
	public int batchDelete(List<V> vos) {
		List<P> pojos = this.dozerBeanUtil.convertList(vos, pojoClazz);
		try {
			return this.baseDao.batchDeletePO(pojos);
		} catch (Exception e) {
			LOGGER.error("批量删除数据失败！",e);
		}
		return -1;
	}

	/** 
	 * @see ${package}.core.service.BaseService#queryByDualForString()
	 */
	@Override
	public String queryByDualForString() {
		try {
			return this.baseDao.queryByDualForString();
		} catch (Exception e) {
			LOGGER.error("查询序列失败", e);
		}
		return null;
	}

	/** 
	 * @see ${package}.core.service.BaseService#queryByObject(java.lang.String)
	 */
	@Override
	public Object queryByObject(String sqlId) {
		try {
			return this.baseDao.queryByObject(sqlId);
		} catch (Exception e) {
			LOGGER.error("queryByObject", e);
		}
		return null;
	}
	
}
