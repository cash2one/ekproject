__author__ = 'Ethanlam'

from string import Template

EntityTemplate  = Template("""
/***************************************************
  ${ClassName}.java
  Comment: Entity
  Current Version: V1.0
  Author: ${Author}
  V1.0    Created by CodeBuilder ${Datetime}
#******************************************************************************/
import java.util.*

${fieldSet}
${GetAndSet}

""")

FieldSet = Template("""
private ${fieldType} ${fieldName};
""")


GetAndSetMethod = Template("""
public ${fieldType} get${FieldName}( ){
    return this.${fieldName};
}

public set${FieldName}(${fieldType} ${fieldName}){
     this.${fieldName} = ${fieldName};
}
""")




