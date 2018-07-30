import os
import random
import shutil
from imutils import paths


image_path = r"F:\pro\image\1\1"
label_path = r"G:\image\1\all"

i=0
for img in paths.list_images(label_path):

    print(img)
    file_name = os.path.basename(img)
    name = os.path.splitext(file_name)[0]

    save_path = r"G:\image\img"

    image = os.path.join(image_path, name+".jpg")
    print(image)

    img_save = os.path.join(save_path, "{}.jpg".format(str(i).zfill(6)))
    label_save = os.path.join(save_path, "{}.png".format(str(i).zfill(6)))
    print(img_save)

    shutil.copy(image, img_save)
    shutil.copy(img, label_save)

    i+=1


