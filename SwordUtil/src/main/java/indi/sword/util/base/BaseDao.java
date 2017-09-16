package indi.sword.util.base;


import indi.sword.util.Exception.BaseException;
import indi.sword.util.core.GenericsUtils;
import indi.sword.util.core.QueryResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description DAO基础类
 * @Author:rd_jianbin_lin
 * @Date: 10:54 2017/9/14
 */

@Repository
public class BaseDao<T> {

    /**************** 注入sessionFactory，对数据库Session统一管理  **************************/
    @Autowired
    private SessionFactory sessionFactory;

    private static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    /**
     * @Description OpenSessionInView方式维护session
     * @Author:rd_jianbin_lin
     * @Date: 11:16 2017/9/14
     */
    public Session getCurrentSession() {

        return sessionFactory.getCurrentSession();

    }

    /**
     * @Description 以手动加载的方式获得session
     * @Author:rd_jianbin_lin
     * @Date: 11:16 2017/9/14
     */
    public Session openSession() {
        return sessionFactory.openSession();
    }

    /******************************** 解决泛型精确匹配，提高通用性  ************************************/
    /**
     * 获取范型实体类型
     */
    @SuppressWarnings("unchecked")
    protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
    /**
     * 获取当前实体类名称
     */
    public String entityName = getEntityName(this.entityClass);

    /**
     * 获取实体类名称
     */
    protected static <E> String getEntityName(Class<E> clazz) {
        String entityname = clazz.getSimpleName();
        return entityname;
    }

    /******************************* 对于泛型实体操作的具体实现 *******************************/

    /**
     * 手动 开启事务
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 14, 2017 5:01:17 PM
     */
    public void beginTransaction() throws Exception{
        Session session = this.getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            try {
                tx.begin();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException("事务开启失败...");
            }
        }
    }

    /**
     * 手动 关闭事务
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 14, 2017 5:01:25 PM
     */
    public void closeTransaction(){
        Session session = this.getCurrentSession();
        Transaction tx = session.getTransaction();
        if(tx.isActive()){
            try {
                tx.commit();
                session.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * flag 是否开事务，
     * 如果是，那么就不独立flush到DB，
     * 如果否，那么就直接flush到数据库。
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 14, 2017 12:38:00 AM
     */
    public void saveOrUpdate(T entity,boolean flag) throws Exception{
        if(StringUtils.isEmpty(entity)){
            throw new BaseException("entity不存在...");
        }

        Session session = this.getCurrentSession();
        session.saveOrUpdate(entity);
        if(!flag){
            session.flush();
            session.clear();
        }
    }


    /**
     * flag 是否开事务，
     * 如果是，那么就不独立flush到DB，
     * 如果否，那么就直接flush到数据库。
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 14, 2017 12:38:00 AM
     */
    public void saveOrUpdate(List<T> list,boolean flag) throws Exception{
        if(StringUtils.isEmpty(list)){
            throw new BaseException("存储List为空...");
        }
        for(int i = 0,size = list.size(); i < size;i++){
            saveOrUpdate(list.get(i),flag);
        }
    }

    /**
     * flag 是否开事务，
     * 如果是，那么就不独立flush到DB，
     * 如果否，那么就直接flush到数据库。
     * 删除单个对象
     */
    public void delete(T entity,boolean flag) throws Exception {
        if(StringUtils.isEmpty(entity)){
            throw new BaseException("entity不存在...");
        }
        Session session = this.getCurrentSession();
        session.delete(entity);
        if(!flag){
            session.flush();
            session.clear();
        }
    }

    /**
     * 根据id删除实体
     */
    public void delete(Serializable entityId,boolean flag) throws Exception {
        delete(findById(entityId),flag);
    }

    /**
     * 删除实体集
     */
    public void deleteEntities(List<T> entites,boolean flag) throws Exception{

        for (T entity : entites) {
            delete(entity,flag);
        }
    }

    /****************** 查询相关 ********************/
    /**
     * 根据自定义HQL查询数据
     */
    @SuppressWarnings("unchecked")
    public List<T> findByHQL(String hql) {
        return this.getCurrentSession().createQuery(hql).list();
    }

    /**
     * 根据Id查询数据
     */
    @SuppressWarnings("unchecked")
    public T findById(Serializable entityId) {
        return (T) this.getCurrentSession().get(entityClass, entityId);
    }

    /**
     * 根据属性查询数据
     */
    @SuppressWarnings("unchecked")
    public List<T> findByProperty(String propertyName, Object value) {
        String hql = "from " + entityName + " o where o." + propertyName + "= ?";
        return this.getCurrentSession().createQuery(hql).setParameter(0, value).list();
    }

    /**
     * 获取所有实体对象集合，并对结果集进行排序
     *
     * @param orderBy
     *            排序子句，key为排序属性，value为升序（asc）或降序（desc），支持多排序，用法为：
     *            LinkedHashMap<String, String> orderBy = new
     *            LinkedHashMap<String, String>(); orderBy.put("属性名1","asc");
     *            orderBy.put("属性名2","desc");....
     * @return 所有实体对象集合
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll(final LinkedHashMap<String, String> orderBy) {
        Session session = this.getCurrentSession();
        List<T> list = session.createQuery("from " + entityName + " o " + buildOrderby(orderBy)).list();
        return list;
    }

    /**
     * 获取所有实体对象集合
     */
    public List<T> findAll() {
        return findAll(null);
    }

    /**
     * 多条件查询，排序、分页
     *
     * @param currentPageNo
     *            当前页号，若其值和maxresult值同时为-1，表示获取所有记录
     * @param pageSize
     *            每页记录数（页面大小）
     * @param where
     *            查询子句，不包括where关键字，格式为：o.属性名1=? and o.属性名2 like?等，支持多条件查询
     * @param queryParams
     *            条件查询子句中出现的位置参数值
     * @param orderBy
     *            排序子句，key为排序属性，value为升序（asc）或降序（desc），支持多排序，用法为：
     *            LinkedHashMap<String, String> orderBy = new
     *            LinkedHashMap<String, String>(); orderBy.put("属性名1","asc");
     *            orderBy.put("属性名2","desc");....
     * @return 符合条件的查询结果
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public QueryResult<T> getQueryResult(final int currentPageNo, final int pageSize, final String where,
                                         final List<Object> queryParams, final LinkedHashMap<String, String> orderBy) {
        final QueryResult<T> queryResult = new QueryResult<T>();
        String hql = "from " + entityName + " o " + (where == null || "".equals(where.trim()) ? "" : " where " + where);
        // 是否排序
        if (orderBy != null) {
            System.out.println(orderBy.size());
            hql += buildOrderby(orderBy);
        }
        Query query = this.getCurrentSession().createQuery(hql);
        setQueryParams(query, queryParams);// where
        queryResult.setTotalRecords(query.list().size());// first get size
        // 是否分页
        if (currentPageNo != -1 && pageSize != -1) {
            // last
            query.setFirstResult((currentPageNo - 1) * pageSize).setMaxResults(pageSize);
        }
        // page
        queryResult.setResultList(query.list());
        return queryResult;
    }


    /**
     * 设置查询参数
     */
    protected static void setQueryParams(Query query, List<Object> queryParams) {
        if (queryParams != null && queryParams.size() > 0) {
            for (int i = 0; i < queryParams.size(); i++) {
                query.setParameter(i, queryParams.get(i));// 从0开始
            }
        }
    }

    /**
     * 组装order by子句
     */
    protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
        StringBuilder orderbyql = new StringBuilder("");
        if (orderby != null && orderby.size() > 0) {
            orderbyql.append(" order by ");
            for (String key : orderby.keySet()) {
                orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
            }
            orderbyql.deleteCharAt(orderbyql.length() - 1);
        }
        return orderbyql.toString();
    }

    /**
     * 多条件查询，不排序、不分页
     */
    public QueryResult<T> getQueryResult(final String where, final List<Object> queryParams) {
        return this.getQueryResult(-1, -1, where, queryParams, null);
    }

    /**
     * 多条件查询，排序、不分页
     */
    public QueryResult<T> getQueryResult(final String where, final List<Object> queryParams,
                                         final LinkedHashMap<String, String> orderBy) {
        return this.getQueryResult(-1, -1, where, queryParams, orderBy);
    }

    /**
     * 多条件查询，不排序、分页
     */
    public QueryResult<T> getQueryResult(final int currentPageNo, final int pageSize, final String where,
                                         final List<Object> queryParams) {
        return this.getQueryResult(-1, -1, where, queryParams, null);
    }

    /**
     * 要分页、无条件、要排序
     */
    public QueryResult<T> getQueryResult(final int currentPageNo, final int pageSize,
                                         final LinkedHashMap<String, String> orderBy) {
        return this.getQueryResult(currentPageNo, pageSize, null, null, orderBy);
    }
}
