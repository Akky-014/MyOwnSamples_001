from django.db import models

# Create your models here.
from django.db import models


# Create your models here.
class EventModel(models.Model):
    eventName = models.CharField(max_length=50)
    packageName = models.CharField(max_length=150, null=True, blank=True, default='-')
    componentName = models.CharField(max_length=150, null=True, blank=True, default='-')
    nodeViewId = models.CharField(max_length=150, null=True, blank=True, default='-')
    nodeAddress = models.CharField(max_length=150, null=True, blank=True, default='-')
    images = models.ImageField(upload_to='', null=True, blank=True)
    detail = models.TextField(null=True, blank=True, default='-')

    class Meta:
        # テーブル名を指定
        db_table = 'event_model_table'

        # デフォルトの並び順を指定（eventNameの昇順でソート）
        ordering = ['eventName']

        # モデルの名前（人間が読むときの名前）
        verbose_name = 'Event'
        verbose_name_plural = 'Events'