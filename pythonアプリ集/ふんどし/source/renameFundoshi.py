import os
import shutil

# 変更前ファイル_通常版
path1 = 'C:\\techSamples\\ふんどし\\ふんどし_130\\counter.docx'

# 変更後ファイル_肩書1なし版
path2 = 'C:\\techSamples\\ふんどし\\ふんどし_130\\counter.docx'

# ファイル名変更_通常版
def reName_path(counter, shimei, keisho):
    # ファイル名の変更
    os.rename(path1, path2)
    file_oldname = os.path.join("C:\\techSamples\\ふんどし\\ふんどし_130", "counter.docx")
    file_newname_newfile = os.path.join("C:\\techSamples\\ふんどし\\ふんどし_130", str(counter) + '_' + shimei + keisho + ".docx")

    newFileName = shutil.move(file_oldname, file_newname_newfile)

    print("The renamed file has the name:", newFileName)


