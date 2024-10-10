from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.shortcuts import render, redirect
from django.contrib.auth.models import User
from django.utils.decorators import method_decorator
from django.views.generic import CreateView, ListView
from django.urls import reverse_lazy

from .models import EventModel


# Create your views here.
def signupfunc(request):

    if request.method == 'POST':
        username2 = request.POST['username']
        password2 = request.POST['password']
        try:
            User.objects.get(username=username2)
            return render(request, 'signup.html', {'error': 'このユーザーは登録されています'})
        except:
            user = User.objects.create_user(username2, '', password2)
            return render(request, 'signup.html', {'some': 100})
    return render(request, 'signup.html', {'some':100})

def loginfunc(request):

    if request.method == 'POST':
        username2 = request.POST['username']
        password2 = request.POST['password']
        user = authenticate(username=username2, password=password2)
        if user is not None:
            login(request, user)
            # return redirect('list')
            return redirect('home')
        else:
            return redirect('login')
    return render(request, 'login.html')

def logoutfunc(request):
    logout(request)
    return redirect('login')

def detailfunc(request, pk):
    # object= BoardModel.objects.get(pk=pk)
    object = EventModel.objects.get(pk=pk)
    return render(request, 'detail.html', {'object': object})

# ログインが必要な場合は、メソッドデコレータを使用
@method_decorator(login_required, name='dispatch')
class ReadListView(ListView):
    # model = BoardModel  # 表示したいモデルを指定
    model = EventModel  # 表示したいモデルを指定
    template_name = 'list.html'  # 使用するテンプレートファイル
    context_object_name = 'object_list'  # テンプレートで使うコンテキスト変数名

    # a hrefはget送信だから、get_querysetメソッドがオーバーライドしておくと呼ばれる
    def get_queryset(self):
        return EventModel.objects.all().order_by('-id')  # ここでデータを新しい順に並べる

def homefunc(request):
    return render(request, 'home.html')

def searchfunc(request):
    return render(request, 'search.html')

# クラスベースビュー
# Djangoにおいてビューの機能をクラスとして定義する方法。所謂、少ない記述でCRUDの機能を作れる汎用クラス
class BoardCreate(CreateView):
    template_name = 'create.html'
    model = EventModel
    fields = ('eventName', 'packageName', 'componentName', 'nodeViewId', 'nodeAddress', 'detail')
    success_url = reverse_lazy('read_list')

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)  # 親クラスの初期化を呼び出す
        # ここでカスタム初期化処理を追加できる
        print("BoardCreate instance is being initialized")