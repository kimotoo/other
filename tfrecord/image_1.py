# 多文件夹下内容移动到同一文件夹

import os
import glob
import shutil
from xml_helper import xml_helper
path = r"G:\image\flower\flower_od\img"
save_path = r"G:\image\flower\flower_od\image\train"

def image_1(path, save_path):

    i=0

    for cla in os.listdir(path):

        data_file_path = os.path.join(path, cla)
        print(data_file_path)

        for file in glob.glob(data_file_path + "/*.xml"):

            print(file)

            file_name = os.path.basename(file)

            name = os.path.splitext(file_name)[0]

            image_path = os.path.join(data_file_path, name+'.jpg')
            #print(image_path)

            img_save = os.path.join(save_path, "{}.jpg".format(str(i).zfill(6)))
            xml_save = os.path.join(save_path, "{}.xml".format(str(i).zfill(6)))

            #print(img_save)
            #print(xml_save)
            shutil.copy(image_path, img_save)
            shutil.move(file, xml_save)

            i+=1

if __name__ == '__main__':

    image_1(path, save_path)
