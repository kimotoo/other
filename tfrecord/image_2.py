# 随机抓取做训练集

import os
import random
import shutil


# 测试的数据百分比
TEST_PERCENTACE = 10


data_path = r'G:\image\flower\flower_od\img'
save_path = r'G:\image\flower\flower_od\image\test'


def spic_image(data_path, save_path):

    i=0
    for file in os.listdir(data_path):

        print(file)
        file_path = os.path.join(data_path, file)
        num = len(os.listdir(file_path))
        test_size = int(num/2*0.1)
        #print(data)

        j=0
        while(j<test_size):

            datas = os.listdir(file_path)
            num = len(os.listdir(file_path))
            image_index = random.randrange(num-1)

            data = datas[image_index]
            #print(data)
            type = os.path.splitext(data)[1]

            if(type == '.jpg'):

                name = os.path.splitext(data)[0]
                image_path = os.path.join(file_path, data)
                xml_path = os.path.join(file_path, "%s.xml" % name)
                print(xml_path)

                image_save = os.path.join(save_path, "{}.jpg".format(str(i).zfill(6)))
                xml_save = os.path.join(save_path, "{}.xml".format(str(i).zfill(6)))

                shutil.move(image_path, image_save)
                shutil.move(xml_path, xml_save)

                i+=1
                j+=1


if __name__ == '__main__':

    spic_image(data_path, save_path)