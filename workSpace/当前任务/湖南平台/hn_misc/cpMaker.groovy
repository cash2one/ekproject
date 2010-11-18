def tip='''groovy cpMaker.groovy /path/to/lib [":"|";"]
1.the first arg is the path of library
2.the second arg is the path separator,default is ":"
'''

def len = args.length
if(len <= 0){
    println tip
}

def libPath = args[0]
pathSep = ':'
def result = ''

if(len == 2){
    pathSep = args[1]
    println 'Set path seprator to ' + pathSep
}

def libDir = new File(libPath)
libDir.eachFile(){file->
        result += getFilePath(file)
}

def getFilePath(file){
    def tmp = ''

    if(file.isFile()){
        if(!file.getName().endsWith('jar'))
            return '';

        tmp += file.getCanonicalPath()
    }else if(file.isDirectory()){
        file.eachFile(){
            def fileName = getFilePath(it)
            if(tmp.length() == 0)
                tmp += fileName
            else if(tmp.length() > 0){
                tmp += pathSep;
                tmp += fileName
            }
        }
    }
    if(tmp.length() > 0 )
        tmp += pathSep
    /*if(tmp.endsWith(pathSep)){
        tmp = tmp.substring(0,tmp.lastIndexOf(pathSep)-1)   
    }*/
    return tmp;
}

println result