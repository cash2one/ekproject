package esfw.core.framework.business;


import java.io.Serializable;

import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.purview.PurviewType;

/**
 * 
 * 描述：业务逻辑父类
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 * @param <PK>  主键类型 
 *
 */
public abstract class BaseBusiness<PK extends Serializable> implements java.io.Serializable{
	
	protected abstract void onAdd() throws BusinessException;//外层实现的添加
	protected abstract void onModify() throws BusinessException;//外层实现的修改
	protected abstract void onDelete(PK ids[]) throws BusinessException;//外层实现的删除
	
	public abstract String getBusinessName();//外层实现获取业务逻辑名称
	public abstract String getFunctionFlag();//外层实现获取业务逻辑功能标识 对应数据表的功能标识
	public abstract String getModel();//外层实现获取模块名称
	
	protected abstract void checkAndFilter(ActionType type) throws BusinessException;//增删改验证器
	
	private String daoAbb;       //查询对应的地区分表
	
	private int totalRecordNum; //查询对应的总记录数
	
	
	public String getDaoAbb() {
		return daoAbb;
	}
	
	public void setDaoAbb(String daoAbb) {
		this.daoAbb = daoAbb;
	}
	
	//增加
	public void add() throws BusinessException{
		if(!this.havePurview(PurviewType.add)){
			return ;
		}
		this.checkAndFilter(ActionType.add);
		onAdd();//回调
	}
	
	//修改
	public void modify() throws BusinessException{
		if(!this.havePurview(PurviewType.mdf)){
			return ;
		}
		this.checkAndFilter(ActionType.add);
		onModify();//回调
	}
	
	//删除
	public void delete(PK ids[]) throws BusinessException{
		if(!this.havePurview(PurviewType.del)){
			return ;
		}
		this.checkAndFilter(ActionType.del);
		onDelete(ids);//回调
	}

	
	
	//判断是否有权限
	public  boolean havePurview(PurviewType pur){
		return true;
	}
	
	/**
	 * 
	 * 设置记录总数
	 * @param totalRecordNum
	 */
	protected void setQeuryRecordTotalNum(int totalRecordNum){
		 this.totalRecordNum = totalRecordNum;
	}
	
	/**
	 * @return 返回查询总记录
	 */
	public int getQeuryRecordTotalNum(){
		return this.totalRecordNum;
	}
 
}
