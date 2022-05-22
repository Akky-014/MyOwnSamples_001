import openpyxl
import wordOperatorFundoshi

wb = openpyxl.load_workbook(r'C:\techSamples\ふんどし\大会ふんどし名簿.xlsx')
sheet = wb['ふんどし用']

# 肩書1、肩書２、氏名、敬称をリストで取得
list_Katagaki_1 = list(sheet.columns)[0]
list_katagaki_2 = list(sheet.columns)[1]
list_shimei = list(sheet.columns)[2]
list_keisho = list(sheet.columns)[3]

# 印刷する総数を取得(名前の数と同じ)
total = len(list_shimei)
print(total - 1)

# 名前の数だけふんどし_wordを作る繰り返し文
for counter in range(total):
    # 通常
    if counter != 0 and list_Katagaki_1[counter].value is not None:
        wordOperatorFundoshi.nomalTypeCreater(counter,
                                              list_Katagaki_1[counter].value,
                                              list_katagaki_2[counter].value,
                                              list_shimei[counter].value,
                                              list_keisho[counter].value)
    # 肩書1がなしの場合
    elif list_Katagaki_1[counter].value is None:
        wordOperatorFundoshi.nonKatagaki1TypeCreater(counter,
                                                     list_katagaki_2[counter].value,
                                                     list_shimei[counter].value,
                                                     list_keisho[counter].value)
