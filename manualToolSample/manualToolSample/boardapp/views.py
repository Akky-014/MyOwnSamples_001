from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.shortcuts import render, redirect
from django.contrib.auth.models import User
from django.views.generic import CreateView, ListView
from django.urls import reverse_lazy

from .models import BoardModel

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

@login_required
def listfunc(request):
    object_list = BoardModel.objects.all().order_by('-id')  # 作成された順（新しい順）に並べ替え
    return render(request, 'list.html', {'object_list': object_list})

def logoutfunc(request):
    logout(request)
    return redirect('login')

def detailfunc(request, pk):
    object= BoardModel.objects.get(pk=pk)
    return render(request, 'detail.html', {'object': object})

def goodfunc(request, pk):
    post = BoardModel.objects.get(pk=pk)
    post.good = post.good + 1
    post.save()
    return redirect('list')

# 既存の関数ベースビューも残す（必要であれば削除）
def readfunc(request, pk):
    post = BoardModel.objects.get(pk=pk)
    post2 = request.user.get_username()
    if post2 in post.readtext:
        return redirect('list')
    else:
        post.read += 1
        post.readtext = post.readtext + ' ' + post2
        post.save()
        return redirect('list')


class ReadListView(ListView):
    model = BoardModel  # 表示したいモデルを指定
    template_name = 'list.html'  # 使用するテンプレートファイル
    context_object_name = 'object_list'  # テンプレートで使うコンテキスト変数名

    def get(self, request, *args, **kwargs):
        # pkを取得して、特定の投稿の既読処理を行う
        pk = self.kwargs.get('pk')
        post = BoardModel.objects.get(pk=pk)
        username = request.user.get_username()

        # 読んだかどうかの確認と処理
        if username not in post.readtext:
            post.read += 1
            post.readtext += f' {username}'
            post.save()

        # 既存のListViewの処理（投稿リストを表示）
        return super().get(request, *args, **kwargs)

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['message'] = "特定の投稿が既読として登録されました。"  # 必要なら追加の情報をコンテキストに渡す
        return context

def homefunc(request):
    return render(request, 'home.html')

# クラスベースビュー
# Djangoにおいてビューの機能をクラスとして定義する方法。所謂、少ない記述でCRUDの機能を作れる汎用クラス
class BoardCreate(CreateView):
    template_name = 'create.html'
    model = BoardModel
    fields = ('title', 'content', 'author', 'images')
    success_url = reverse_lazy('list')