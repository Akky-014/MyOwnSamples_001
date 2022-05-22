#####################################################
# フォルダ内のaとbのcsvファイルを１つのエクセルにまとめる
#####################################################

# CSV結合
import pandas
import glob
import openpyxl

#■初期設定
# 読み込んだファイルを入れるリストを準備
lists = []

#フォルダ、ファイルの種類を指定
folder='C:\Tool_Wanpaku\_csv\*.csv'

#結合後のExcelファイル名を指定
EXCEL_FILE_NAME ='C:\Tool_Wanpaku\わんぱく事前応募者リスト.xlsx'

#1 フォルダ内のファイル一覧を読み込
file_list=glob.glob(folder)

#2 ファイル一覧をExcelでリストへ追加
for i in file_list:
    lists.append(pandas.read_csv(i ,encoding='utf-8'))

#3 リスト内のExcelをマージ
merge_data = pandas.concat(lists)

#4 マージしたデータをExcelへ書き込み
merge_data.to_excel(EXCEL_FILE_NAME,index=0)

#####################################################
# 1列目をNo.に入れ替え
#####################################################

wb = openpyxl.load_workbook(EXCEL_FILE_NAME)
ws = wb['Sheet1']

#列を削除
ws.delete_cols(1)

#新しい1列目を挿入
ws.insert_cols(1)
cell_01 = ws["A1"]
cell_01.value = "No."

#保存
wb.save(EXCEL_FILE_NAME)