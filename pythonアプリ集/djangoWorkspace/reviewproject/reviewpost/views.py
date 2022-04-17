from django.shortcuts import render, redirect
from django.contrib.auth.models import User
from django.db import IntegrityError
from django.contrib.auth import authenticate, login
from .models import ReviewModel

# Create your views here.


def signupview (request):
    if request.method == 'POST':
        username_data = request.POST['username_data']
        password_data = request.POST['password_data']
        try:
            User.objects.create_user(username_data, '', password_data)
        except IntegrityError:
            return render(request, 'signup.html', {'error': 'このユーザーは既に登録されています。'})
    else:
        return render(request, 'signup.html', {})
    return render(request, 'signup.html', {})

def loginview(request):
    if request.method == 'POST':
        username_data = request.POST['username_data']
        password_data = request.POST['password_data'] 
        user = authenticate(request, username=username_data,password=password_data)       
        if user is not None:
            login(request, user)
            return redirect('list')
        else:
            return redirect('login')
    return render(request, 'login.html')

def listview(request):
    object_list = ReviewModel.objects.all()
    return render(request, 'list.html',{ 'object_list':object_list })

def detailview(request, pk):
    object = ReviewModel.objects.get(pk=pk)
    return render(request, 'detail.html', {'object':object})