# 将xml文件文件夹内的xml文件内的信息统一记录到.csv表格

path = r"G:\CS\tensorflow\models\research\object_detection\images\test"
save_path = r'G:\CS\tensorflow\models\research\object_detection\data'

import os
import glob
import pandas as pd
import xml.etree.ElementTree as et

def xml_to_csv(path):

    xml_list = []

    for xml_file in glob.glob(path+'\\*.xml'):

        print(xml_file)
        # G:\image\flower\flower_od\img\baihe\000000.xml

        tree = et.parse(xml_file)
        root = tree.getroot()

        for menber in root.findall('object'):

            # print(menber)

            value = (root.find('filename').text,
                     int(root.find('size')[0].text),
                     int(root.find('size')[1].text),
                     menber[0].text,
                     int(menber[4][0].text),
                     int(menber[4][1].text),
                     int(menber[4][2].text),
                     int(menber[4][3].text),
                     )

            print(value)

            xml_list.append(value)

        column_name = ['filename', 'width', 'height', 'class', 'xmin', 'ymin', 'xmax', 'ymax']
        xml_df = pd.DataFrame(xml_list, columns=column_name)

    return xml_df





if __name__ == '__main__':

    xml_df = xml_to_csv(path)
    xml_df.to_csv(save_path+'/test_labels.csv', index=None)
    print('Successfully converted xml to csv.')