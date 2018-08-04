Chapter1-Coding Style
=====================

## 1.1 代码可读性很重要

1. 避免变量名缩写
2. 带上函数参数名
3. 注释类和方法
4. 注释代码块
5. 重构重复的代码成可重用的函数和方法
6. 保持函数和方法简短: 建议一页可以看完整个函数或者方法

## 1.2 PEP8

[pep8](https://www.python.org/dev/peps/pep-0008/)

## 1.3 imports

1. Standard Lib
*. Core Django
2. Third-party
3. Local

## 1.4 使用显式的相对导入

```python3
# cones/views.py
from cones.models import xxx
```

在包名需要改名或者在另外的应用中想重用这个包但发生命名冲突时,implicit relative imports会导致问题，修改为下

```python3
# cones/views.py
from .models import xxx
```

## 1.5 避免使用import *

主要是为了避免命名冲突

```python3
# alias to avoid name collision
from django.db.models import CharField as ModelCharField
from django.forms import CharField as FormCharField
```

## 1.6 Django编码习惯

[Django Coding Style](https://docs.djangoproject.com/en/1.11/internals/contributing/writing-code/coding-style/)

[Django Internals](https://docs.djangoproject.com/en/1.11/internals/)

### 1.6.2 url names

```python3
patterns = [
    url(regex='^add/$',  # '^add-topping/$' is ok
        view=views.add_topping,
        name='add_topping'),  # add-topping is bad
]
```

## 1.7 选择JS, HTML, CSS代码风格

## 1.8 Never Code to the IDE(Or Text Editor)

## 1.9 Summary

**务必保持一致的代码风格!**