__author__ = 'Ethanlam'

from string import Template
import os

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

def saveFile(filePath, buf):
        if not os.path.exists(filePath):
             temp = os.path.dirname(filePath)
             if not os.path.exists(temp):
                os.makedirs(temp)
        else:
             os.remove(filePath)
        f = open(filePath,'w')
        f.write(buf)
        f.close()

entityContent =  EntityTemplate.safe_substitute({"ClassName":"Test","Author":"Ethan.Lam","Datetime":"2012-01"})
saveFile("e://test.java",entityContent)