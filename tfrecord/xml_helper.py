# xml 文件的修改

import os
import glob
import pandas as pd
import xml.etree.ElementTree as et

path = r"G:\CS\tensorflow\models\research\object_detection\images\train"

def xml_helper(xml,file_name):

    tree = et.parse(xml)
    root = tree.getroot()

    for filename in root.iter("filename"):

        name = os.path.splitext(file_name)[0]
        filename.text = str(name+'.jpg')

    tree.write(os.path.join(path, file_name))



if __name__ == '__main__':

    for xml in glob.glob(path+'\\*.xml'):

        file_name = os.path.basename(xml)
        print(file_name)

        xml_helper(xml, file_name)




