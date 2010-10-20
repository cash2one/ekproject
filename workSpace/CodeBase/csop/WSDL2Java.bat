set Output_Path=e:\zjxxt\adc\src
set Package=server.SayHello
java -Djava.ext.dirs=e:\zjxxt\adc\axis\WEB-INF\lib org.apache.axis.wsdl.WSDL2Java -o %Output_Path% -p %Package% SayHello.wsdl