import django
import pytest
from django.contrib.auth.models import User
from django.urls import reverse

import os

# DJANGO_SETTINGS_MODULE環境変数を設定
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'manualToolSample.settings')

# Djangoの設定をセットアップ
django.setup()

@pytest.mark.django_db
def test_signup_view(client):
    # 新規ユーザーが作成されるかテスト
    response = client.post(reverse('signup'), {'username': 'testuser', 'password': 'testpass'})
    assert response.status_code == 200
    assert User.objects.filter(username='testuser').exists()

    # 同じユーザー名での再登録はエラーが発生するか
    response = client.post(reverse('signup'), {'username': 'testuser', 'password': 'testpass'})
    assert response.status_code == 200
    assert 'このユーザーは登録されています' in response.content.decode()

# Create your tests here.
