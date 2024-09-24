from django.contrib.auth import get_user_model
from django.db.utils import IntegrityError

User = get_user_model()

try:
    User.objects.create_superuser(
        username='admin',
        email='admin@example.com',
        password='admin'
    )
    print("Superuser created.")
except IntegrityError:
    print("Superuser already exists.")