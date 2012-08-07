__author__ = 'Ethanlam'

from string import Template

class BusinessTemplate(Template):
    def __int__(self):


EntityTemplate  = Template("""
/***************************************************
  ${ClassName}.java
  Comment:
  Current Version: V1.0
  Author: ${Author}
  V1.0    Created by CodeBuilder@${Datetime}
#******************************************************************************/

import java.util.*



""")

