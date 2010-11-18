package com.qtone.datasync.xxt

import com.qtone.datasync.util.ClassUtil
import com.qtone.datasync.dao.*

import groovy.sql.*

public class XxtRequestHelper{
	/**
	 * 清空数据库表，将插入新的数据。新数据以文本格式存在
	 */
	public static void prepareData(){
		//向cs_tranpackage_customer表中写入数据
		def dataSet = getDataSet()
		
		Sql sql = new Sql(new QtoneDataSource())
		sql.execute('truncate table cs_tranpackage_customer')
		dataSet.each{aRec->
			sql.executeInsert("insert into cs_tranpackage_customer(family_id,"+
					"phone,xxt_salemodalid,del,adcdeal_suc) values(?,?,?,?,?)",aRec)
		}
		
		//清空misc_order_relation
		sql.execute('truncate table misc_order_relation')
		
		sql.commit()
	}
	
	private static getDataSet(){
		def dataFile = new File(ClassUtil.getFilePath(XxtRequestHelper.class,'com/qtone/datasync/xxt/sync.txt'))
		
		def dataSet = []
		
		dataFile.eachLine({line->
			def aRec = []
			line.split(' ').each{
				aRec << it
			}
			dataSet << aRec
		})
		
		return dataSet
	}
	
	public static void main(String[] args){
		prepareData();
	}
}