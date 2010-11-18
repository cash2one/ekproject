import com.qtone.datasync.bean.*
import com.qtone.datasync.util.*

//def bean = new UserInfoBean(["msgType":"SyncOrderRelationReq", "transactionID":"1", "phoneFee":"13800138000", "phoneUse":"13800138000", "action":1, "actionReason":1, "spId":"Qtone", "spServiceId":"528400"])
//print bean.msgType

//xmlStr = ''' 
//
//<?xml version="1.0" encoding="GBK"?>
//<SyncOrderRelationResp>
//  <MsgType>SyncOrderRelationResp</MsgType>
//  <TransactionID>1123</TransactionID>
//  <Version>1.5.0</Version>
//  <hRet>4004</hRet>
//</SyncOrderRelationResp>
//
//'''
//
//xmlStr = xmlStr.replace('<?xml version="1.0" encoding="GBK"?>','')
//root = new XmlParser().parseText(xmlStr)
//
//msgType = root.MsgType[0]
//print msgType.name()+" "+msgType.text()
//
//		def tranId = root.TransactionID[0]
//		println '--------------------------------------------------------'
//		println tranId.name()+' '+tranId.text()
//		println '--------------------------------------------------------'
//
//println new Date(1242622884895L)	

println GUtil.getSyncOrderTemplate()