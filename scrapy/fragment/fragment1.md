# scrapy/__init__.py

scrapy中出现了import搭配del的用法

```
from . import _monkeypatches
del _monkeypatches
```

import只有第一次才会执行整个文件,后面的import相当于在模块中增加一个包的引用。

import package后del package,是为了对package对象进行一些操作,由于package在sys.modules这个字典中会有所有导入过的对象,因此del不会彻底清除对象,感觉是为了防止通过当前包名的属性对import的包做意外的修改。

__all__ = [...]: import ×时会引入的变量,即使是__开头也会导入

__file__: module的绝对路径

__path__:  any module that contains a __path__ attribute is considered a package, [packages](https://docs.python.org/3/reference/import.html)

__package__: 'package', module以python -m package.module才有这个变量

__name__: 'package.module'

