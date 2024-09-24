from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('boardapp.urls')), # include() を使って boardapp.urls を指定する
]
