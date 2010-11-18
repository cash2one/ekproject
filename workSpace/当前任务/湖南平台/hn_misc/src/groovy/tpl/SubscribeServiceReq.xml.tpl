<SubscribeServiceReq xmlns="http://www.monternet.com/dsmp/schemas/">
	<Version>1.5.0</Version>
	<MsgType>${bean.beanType}</MsgType>
	<Send_Address>
		<DeviceType>400</DeviceType>
		<DeviceID>913002</DeviceID>
	</Send_Address>
	<Dest_Address>
		<DeviceType>0</DeviceType>
		<DeviceID>0024</DeviceID>
	</Dest_Address>
	<FeeUser_ID>
		<UserIDType>1</UserIDType>
		<MSISDN>${bean.phone}</MSISDN>
		<PseudoCode />
	</FeeUser_ID>
	<DestUser_ID>
		<UserIDType>1</UserIDType>
		<MSISDN>${bean.phone}</MSISDN>
		<PseudoCode />
	</DestUser_ID>
	<Service_ID>
		<ServiceIDType>1</ServiceIDType>
		<SPID>${bean.spId}</SPID>
		<SPServiceID>-TQAAU</SPServiceID>
		<AccessNo />
		<FeatureStr />
	</Service_ID>
	<FeatureStr />
</SubscribeServiceReq>