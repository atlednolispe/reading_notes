Chapter5-Settings and Requirements Files
========================================

1. 所有settings需要被版本控制
2. 继承base settings
3. 保护秘钥

## 5.2 使用多个设置文件

```bash
settings/
├── __init__.py
├── base.py
├── local.py
├── staging.py
├── test.py
├── production.py
```

```bash
# 或者设置DJANGO_SETTINGS_MODULE
$ python manage.py shell --settings=twoscoops.settings.local
$ python manage.py runserver --settings=twoscoops.settings.local
```

```python3
# settings模版

# settings/local.py
from .base import *

DEBUG = True

EMAIL_BACKEND = 'django.core.mail.backends.console.EmailBackend'

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql_psycopg2',
        'NAME': 'twoscoops',
        'HOST': 'localhost',
    }
}

INSTALLED_APPS += ['debug_toolbar', ]

# settings/local_pydanny.py
from .local import *

# Set short cache timeout
CACHE_TIMEOUT = 30
```

## 5.3 代码外的单独配置

像密钥之类的配置通过**环境变量**来进行配置

### 5.3.1 将环境变量用于秘钥时需要谨慎

1. 对隐私设置需要有个地方保存
2. Apache的环境变量设置对于环境变量不适用

### 5.3.2 本地的环境变量设置

.bashrc/.bash_profile/.profile/ bin/postactivate

### 5.3.3 撤销环境变量

unset

### 5.3.4 生产环境中设置环境变量

不同平台有不同的设置方式

```python3
# settings文件中设置环境变量
# settings/production.py
import os
SOME_SECRET_KEY = os.environ['SOME_SECRET_KEY']
```

### 5.3.5 处理缺少密钥的异常

```python3
# settings/base.py
import os

# Normally you should not import ANYTHING from Django directly
# into your settings, but ImproperlyConfigured is an exception.
from django.core.exceptions import ImproperlyConfigured

def get_env_variable(var_name):
    """Get the environment variable or return exception."""
    try:
        return os.environ[var_name]
    except KeyError:
        error_msg = 'Set the {} environment variable'.format(var_name)
        raise ImproperlyConfigured(error_msg)
```

不要在settings中导入Django中的内容,多个配置文件时Django文档推荐django-admin替代manage,但使用manage也是没问题的

## 5.4 不能使用环境变量时怎么办

增加私密文件,格式可以问JSON,.env,Config,YAML,XML,并且添加到.gitignore

```python3
# settings/base.py
import json

# Normally you should not import ANYTHING from Django directly
# into your settings, but ImproperlyConfigured is an exception.
from django.core.exceptions import ImproperlyConfigured

# JSON-based secrets module
with open('secrets.json') as f:
    secrets = json.loads(f.read())

def get_secret(setting, secrets=secrets):
    '''Get the secret variable or return explicit exception.'''
    try:
        return secrets[setting]
    except KeyError:
        error_msg = 'Set the {0} environment variable'.format(setting)
        raise ImproperlyConfigured(error_msg)

SECRET_KEY = get_secret('SECRET_KEY')
```

同时要注意python代码注入攻击

## 5.5 使用多个requirements

settings应该和requirements对应,package的版本应该被指明

```python3
# requirements/base.txt
Django==1.11.0
psycopg2==2.6.2
djangorestframework==3.4.0

# requirements/local.txt
-r base.txt # includes the base.txt requirements file

coverage==4.2
django-debug-toolbar==1.5
```

## 5.6 settings中设置文件路径

**拒绝hardcode**

```python3
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent.parent
MEDIA_ROOT = BASE_DIR / 'media'
STATIC_ROOT = BASE_DIR / 'static_root'
STATICFILES_DIRS = [BASE_DIR / 'static']
TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [BASE_DIR / 'templates']
    },
]
```