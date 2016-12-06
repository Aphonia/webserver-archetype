package ${package}.core.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ${package}.core.dao.BaseDao;
import ${package}.core.service.BaseService;
import ${package}.core.utils.PropertyUtil;


public class BaseServiceImpl<P> implements BaseService<P>{
    
    private BaseDao<P> baseDao;
    
    /** log4j */
    private final static Logger LOGGER = Logger.getLogger(BaseServiceImpl.class);
    
    public Serializable create(P vo) {
        try {
            return this.baseDao.createPO(vo);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    public Integer delete(P vo,boolean soft) {
        try {
            if(soft){
                //判断是否存在软删除字段
                if (PropertyUtil.containsField(vo.getClass(), "isDel")) {
                    PropertyUtil.setProperty(vo, "isDel", 1);
                }
                return this.baseDao.updatePO(vo);
            }
            return this.baseDao.deletePO(vo);
        } catch (Exception e) {
            LOGGER.error("删除数据失败！",e);
        }
        return -1;
    }

    public P findById(Serializable id) {
        try {
            return this.baseDao.findPO(id);
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

    public List<P> queryByPage(Map<String, Object> params, int currPage,int pageSize) {
        try {
            return this.baseDao.queryByPage(params, currPage, pageSize);
        } catch (Exception e) {
            LOGGER.error("查询分页失败！",e);
        }
        return null;
    }

    public Integer update(P vo) {
        try {
            return this.baseDao.updatePO(vo);
        } catch (Exception e) {
            LOGGER.error("更新数据失败！",e);
        }
        return -1;
    }
    
    public int restoreDel(P vo){
//        P pojo = this.dozerBeanUtil.convert(vo, pojoClazz);
        // 判断是否存在软删除字段
        try {
            if (PropertyUtil.containsField(vo.getClass(), "isDel")) {
                PropertyUtil.setProperty(vo, "isDel", 0);
                return this.baseDao.updatePO(vo);
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
    
    public int batchCreate(List<P> vos) {
        try {
            return this.baseDao.batchCreatePO(vos);
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
    public List<P> queryByPage(Map<String, Object> params, String sqlId,int currPage, int pageSize) {
        try {
            return this.baseDao.queryByPage(params,sqlId, currPage, pageSize);
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
    public int batchUpdate(List<P> vos) {
        try {
            return this.baseDao.batchUpdatePO(vos);
        } catch (Exception e) {
            LOGGER.error("批量修改数据失败！",e);
        }
        return -1;
    }

    /** 
     * @see ${package}.core.service.BaseService#queryByPO(java.util.Map)
     */
    @Override
    public P queryByPO(Map<String, Object> params) {
        try {
            return this.baseDao.queryByPO(params);
        } catch (Exception e) {
            LOGGER.error("查询单条数据失败！",e);
        }
        return null;
    }

    /** 
     * @see ${package}.core.service.BaseService#batchDelete(java.util.List)
     */
    @Override
    public int batchDelete(List<P> vos) {
        try {
            return this.baseDao.batchDeletePO(vos);
        } catch (Exception e) {
            LOGGER.error("批量删除数据失败！",e);
        }
        return -1;
    }

    /** 
     * @see ${package}.core.service.BaseService#queryDual()
     */
    @Override
    public String queryDual() {
        try {
            return this.baseDao.queryByDualForString();
        } catch (Exception e) {
            LOGGER.error("查询序列！",e);
        }
        return null;
    }

}