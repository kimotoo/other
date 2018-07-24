from cx_Freeze import setup, Executable
import os
import warnings
warnings.simplefilter('ignore')
os.environ['TCL_LIBRARY'] = 'C:\\Program Files\python\\Python36\\tcl\\tcl8.6'
os.environ['TK_LIBRARY'] = 'C:\\Program Files\python\\Python36\\tcl\\tk8.6'



executables = [Executable("Main.py")]


packages = []
include_files=['user.txt', 'captcha_model.hdf5', 'model_labels.dat']
options = {
    'build_exe': {
        'packages':['numpy', 'matplotlib', 'tkinter'],
        'include_files': ['C:\\Program Files\\python\\Python36\DLLs\\tcl86t.dll',],
        'includes': ['numpy.core._methods',]
    },

}

setup(
    name = "Prog",
    options = options,
    version = "1.0",
    description = 'desc of program',
    executables = executables
)