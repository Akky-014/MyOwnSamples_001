from django.urls import path

from .views import signupfunc, loginfunc, listfunc, logoutfunc, detailfunc, goodfunc, readfunc, BoardCreate, homefunc, \
    ReadListView

urlpatterns = [
    path('', loginfunc, name='login'),
    path('signup/', signupfunc, name='signup'),  # URLに対応するビュー関数を指定
    path('login/', loginfunc, name='login'),
    path('home/', homefunc, name='home'),
    path('list/', listfunc, name='list'),
    path('logout/', logoutfunc, name='logout'),
    path('detail/<int:pk>', detailfunc, name="detail"),
    path('good/<int:pk>', goodfunc, name="good"),
    path('read/<int:pk>/', readfunc, name="read"),
    path('read_list/<int:pk>/', ReadListView.as_view(), name='read_list'),  # 新しいクラスベースビュー
    path('create/', BoardCreate.as_view(), name="create"),
]