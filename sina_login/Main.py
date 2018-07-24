import cv2
import captcha_model
import os
import pickle
from keras.models import load_model
from imutils import paths
import id_js



MODEL_FILENAME = "captcha_model.hdf5"
MODEL_LABELS_FILENAME = "model_labels.dat"


if __name__ == '__main__':

    url = input("输入url")

    # 启动captcha模型
    with open(MODEL_FILENAME, "rb") as f:
        lb = pickle.load(f)

    model = load_model(MODEL_FILENAME)

    # 加载账号

    jsa = id_js.read()

    for i in range(3):














