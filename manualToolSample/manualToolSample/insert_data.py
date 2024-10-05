# insert_data.py
import os
import django

# Django設定を読み込むために環境変数をセット
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'manualToolSample.settings')

# Djangoをセットアップ
django.setup()

from boardapp.models import EventModel

# 挿入するデータ
events = [
    {'eventName': 'Event1', 'packageName': 'com.example.package1', 'componentName': 'Component1', 'nodeViewId': 'ViewId1', 'nodeAddress': 'Address1', 'images': 'example_image1.jpg', 'detail': 'This is a detail for Event 1'},
    {'eventName': 'Event2', 'packageName': 'com.example.package2', 'componentName': 'Component2', 'nodeViewId': 'ViewId2', 'nodeAddress': 'Address2', 'images': 'example_image2.jpg', 'detail': 'This is a detail for Event 2'},
    {'eventName': 'Event3', 'packageName': 'com.example.package3', 'componentName': 'Component3', 'nodeViewId': 'ViewId3', 'nodeAddress': 'Address3', 'images': 'example_image3.jpg', 'detail': 'This is a detail for Event 3'},
    {'eventName': 'Event4', 'packageName': 'com.example.package4', 'componentName': 'Component4', 'nodeViewId': 'ViewId4', 'nodeAddress': 'Address4', 'images': 'example_image4.jpg', 'detail': 'This is a detail for Event 3'},
]

# データの挿入
for event in events:
    EventModel.objects.create(**event)

print("Data inserted successfully.")