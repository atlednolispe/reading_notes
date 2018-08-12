Chapter6-Model Best Practices
=============================

对于model的设计务必慎重!!!

```
# package for models
django-model-utils: TimeStampedModel
django-extensions: auto loads the model classes for all installed apps, but includes a lot of other functionality which breaks the rules of small, focused app.
```

## 6.1 基础

### 6.1.1 分解含有太多models的app

如果在单个app中含有20+models,尝试分解它。在实践中我们建议单个app不超过5个models。

### 6.1.2 小心model inheritance

model inheritance是一个tricky subject, Django提供三种方式实现模版继承: abstract base classes, multi-table inheritance, and proxy models

Django Abstract Base Classes != Python Abstract Base Classes

强烈不建议使用multi-table inheritance

一些经验之谈:
1. 如果models之间仅有1、2个明显相同的字段,可能不需要model inheritance,直接将字段添加到models即可
2. 如果有足够多的重复在models中并且维护重复字段造成一些疏漏,大多数情况下code应该被分解,相同的字段应该放到一个abstract base class
3. proxy models是一个偶尔有用的特性,但和另外两个model inheritance非常不同
4. 无论如何,所有人应该避免multi-table inheritance(因为他增加了混淆以及实质的额外开销),在models间使用明确的OneToOneFields & ForeignKeys让你在joins遍历时也可以控制

### 6.1.3 模版继承实践: The TimeStampedModel

Django项目中使用created,modified timestamp字段是非常普遍的,手动添加这些字段到每个model中增加了发生人为错误的风险,更好的解决方案是通过TimeStampedModel来完成这个工作

```python3
from django.db import models

class TimeStampedModel(models.Model):
    """
    An abstract base class model that provides self-
    updating ``created`` and ``modified`` fields.
    """
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

    class Meta:  # 将class变为abstract base class
        abstract = True
```

abstract base class在做migrate操作时不会生成实际的表,下面的定义只创建了一个flavors_flavor表,但如果使用multi-table inheritance,会多创建一个core_timestampedmodel表并且Flavor的子类都会缺少这两个字段而含有一个隐含的外键,所有对Flavor的引用读写TimeStampedModel会影响两个表。

```
# flavors/models.py
from django.db import models

from core.models import TimeStampedModel

class Flavor(TimeStampedModel):
    title = models.CharField(max_length=200)
```

要记住,明确的继承有产生性能瓶颈的可能,尤其在生成多个子类的时候。

[Django Model Inheritance](docs.djangoproject.com/en/1.11/topics/db/models/#model-inheritance)

## 6.2 数据库迁移(Database Migrations)

### 6.2.1 Migrations的tips

1. 一旦新app/model被创建, python manage.py makemigrations
2. sqlmigrate检查将要被执行的SQL命令
3. MIGRATION_MODULES为没有自己django.migrations-style的三方编写migrations
4. migrations数量变得很大可以使用squashmigrations
5. 执行migration前记得备份数据

### 6.2.2 增加python函数和定制的SQL到migrations中

django.db.migrations无法预测数据上的复杂变化,特别对于生产项目,会有RunPython or RunSQL classed使用的场景

[runpython](docs.djangoproject.com/en/1.11/ref/migration-operations/#runpython)
[runsql](docs.djangoproject.com/en/1.11/ref/migration-operations/#runsql)

建议优先使用RunPython,但更建议坚持你所擅长的。

## 6.3 克服使用RunPython的普遍障碍

编写RunPython函数时会遭遇一些痛点,多数但不是全部可以被解决

### 6.3.1 获得Custom Model Manager的方法

有时想使用定制的模型管理器方法来filter, exclude, create, modify记录,然而默认的migrations中没有这些。但可以通过增加use`_in_migrations= True`覆盖这个默认行为。

[model-managers](docs.djangoproject.com/en/1.11/topics/migrations/#historical-models)

注意: 如果覆盖了model的save或delete方法,他们不会通过RunPython被调用,这是一个很严重的问题

### 6.3.3 使用RunPython.noop什么都不做

为了migrations的回退,RunPython必须提供一个可调用的reverse_code。但我们的一些code是幂等(idempotent,f(f(x))=f(x))的,像组合存在的数据到一个新增加的字段,对于这样等操作reverse_code就没有意义,当遇到这样的情况时使用RunPython.noop作为reverse_code

例子:
create a new model "Cone", 所有存在的scoops需要自己的cone,使用add_cones函数增加cones到数据库,做erverse migration时,编写移除cone的代码时无意义的,migrations.CreateModel.database_backwards会删除cone.cone表和他的所有记录,所以在reverse_code中使用RunPython.noop

```python3
from django.db import migrations, models

def add_cones(apps, schema_editor):  # schema_editor是什么?
    Scoop = apps.get_model('scoop', 'Scoop')
    Cone = apps.get_model('cone', 'Cone')

    for scoop in Scoop.objects.all():
        Cone.objects.create(
            scoop=scoop,
            style='sugar'
        )

class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('scoop', '0051_auto_20670724'),
    ]

    operations = [
        migrations.CreateModel(
            name='Cone',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True,
                    serialize=False, verbose_name='ID')),
                ('style', models.CharField(max_length=10),
                    choices=[('sugar', 'Sugar'), ('waffle', 'Waffle')]),
                ('scoop', models.OneToOneField(null=True, to='scoop.Scoop'
                    on_delete=django.db.models.deletion.SET_NULL, )),
            ],
        ),
        # RunPython.noop does nothing but allows reverse migrations to occur
        migrations.RunPython(add_cones, migrations.RunPython.noop)
    ]
```

### 6.3.4 部署和管理Migrations

1. 永远在run migration前备份你的数据!!!
2. 部署前确保你可以回滚你的migrations,我们不能保证一直顺利,但不能回滚影响bug追踪
3. 如果项目包含百万行级别的表,migration前在阶段性服务器上做广泛的相同级别测试再在生产环境执行,真是环境可能需要much,much,much more time than anticipated
4. 如果你使用MySQL:
(1) 你必须积极的备份数据,因为MySQL在schema changes上缺少事务支持,因此回滚是不可能的
(2) 如果可以,执行变化前将项目放到一个只读模式
(3) schema changes在heavily populated表中会花上很久的时间

migration和settings文件都应该被包含在VCS中。

## 6.4 Django Model设计

### 6.4.1 Start Normalized

我们应该熟悉数据库范式(database normalization),为了有效地使用Django Model需要这方面的知识。开始设计Django Models时就应该开始规范化,应该花时间确保没有model含有已经存储在别的model中的数据。自由使用关系字段,不要不成熟地denormalize。

### 6.4.2 Cache Before Denormalizing

设置缓存可以减少反范式遭遇的问题

### 6.4.3 只有在确实需要反范式时才使用

在反范式前请先研究缓存,当项目到达研究缓存来处理瓶颈时,反范式差不多应该开始了。

### 6.4.4 何时使用null和blank

field默认null和blank是False,对应书的P73-74,null针对数据库字段而言,blank针对表单

### 6.4.5 何时使用BinaryField

BinaryField用于存储原生二进制数据,不能执行filters,excludes之类的SQL字段操作,但有下列的一些存储场景:
1. MessagePack-formatted content
2. 原生传感器数据
3. 压缩数据

应用的场景很多,但记住二进制数据可以是很大的块数据,可能降低数据库速度,如果因此导致了瓶颈,解决方案之一是将数据存储在文件中并通过FileField引用

在数据库字段中存储文件从不应该发生!!!

PostgreSQL专家Frank Wiles的建议:
1. 数据库r/w大多数情况下比文件系统要慢
2. 存储文件到数据库导致备份变得很大并且需要花费更多的时间
3. 读取文件现在需要经过app层和DB层

### 6.4.6 尝试避免使用generic relation

避免使用generic relations和models.field.GenericForeignKey,generic relations = binding one table to another by way of an unconstrained foreign key(GenericForeignKey),由于models间缺少index影响查询速度,另外党查询引用到一个不存在的记录时会有些问题

generic relations是的建立有许多models交互的app很容易,但这不是很好,并且我们建议ones are focused on a single task

favorites,ratings,voting,messages,and tagging apps可以用ForeignKey和MangyToMany字段来建立,应该避免使用GenericForeignKey

[avoid django genericforeignkey](lukeplant.me.uk/blog/posts/avoid-django-genericforeignkey)

### 6.4.7 Make Choices and Sub-Choices Model Constants

加入choices作为model的属性是一个好的模式,因为这些绑定到model的常量便于读取并且便于开发,这种模式在Python code和template中都适用

[add choices as properties](https://docs.djangoproject.com/en/1.11/ref/models/fields/#choices)

```python3
# orders/models.py
from django import models

class IceCreamOrder(models.Model):
    FLAVOR_CHOCOLATE = 'ch'
    FLAVOR_VANILLA = 'vn'
    FLAVOR_STRAWBERRY = 'st'
    FLAVOR_CHUNKY_MUNKY = 'cm'

    FLAVOR_CHOICES = (
        (FLAVOR_CHOCOLATE, 'Chocolate'),
        (FLAVOR_VANILLA, 'Vanilla'),
        (FLAVOR_STRAWBERRY, 'Strawberry'),
        (FLAVOR_CHUNKY_MUNKY, 'Chunky Munky')
    )

    flavor = models.CharField(
        max_length=2,
        choices=FLAVOR_CHOICES
    )

>>> from orders.models import IceCreamOrder
>>> IceCreamOrder.objects.filter(flavor=IceCreamOrder.FLAVOR_CHOCOLATE)
[<icecreamorder: 35>, <icecreamorder: 42>, <icecreamorder: 49>]
```

### 6.4.8 更好的Model Choice Constants使用Enum

需要Python3.4+/Python2.7+

```python3
from django import models
from enum import Enum

class IceCreamOrder(models.Model):
    class FLAVORS(Enum):
        chocolate = ('ch', 'Chocolate')
        vanilla = ('vn', 'Vanilla')
        strawberry = ('st', 'Strawberry')
        chunky_munky = ('cm', 'Chunky Munky')

        @classmethod
        def get_value(cls, member):
            return cls[member].value[0]

    flavor = models.CharField(
        max_length=2,
        choices=[x.value for x in FLAVORS]
    )

>>> from orders.models import IceCreamOrder
>>> chocolate = IceCreamOrder.FLAVORS.get_value('chocolate')
>>> IceCreamOrder.objects.filter(flavor=chocolate)
[<icecreamorder: 35>, <icecreamorder: 42>, <icecreamorder: 49>]
```

使用Enum有些问题,特别是在涉及细节的filter,但这种方式可以获得iterate的好处,并且便于增加新值

### 6.4.9 针对PostgreSQL的字段: 何时使用null和blank

## 6.5 Model的_meta API

_meta是public,documented API,不像其他_开头的是代表将要弃用的,多数情况不需要使用_meta,有下面几种情况需要使用:
1. 获取model的fields列表
2. 获取特定字段的类或者继承链以及涉及这些的派生信息
3. 确保如何获得这些信息在未来的Django版本保持不变

* 构建Django model introspection pool
* 构建自我定制的Django格式库
* 建立admin-like和model交互的工具
* 编写可视化或者分析库

[_meta](docs.djangoproject.com/en/1.11/ref/models/meta/)

## 6.6 Model Managers

我们每次使用Django ORM对model进行查询时都使用的是叫做model manager的接口,Django提供默认的Model Managers,我们也可以定义自己的

```python3
from django.db import models
from django.utils import timezone

class PublishedManager(models.Manager):

    use_for_related_fields = True

    def published(self, **kwargs):
        return self.filter(pub_date__lte=timezone.now(), **kwargs)

class FlavorReview(models.Model):
    review = models.CharField(max_length=255)
    pub_date = models.DateTimeField()

    # add our custom model manager
    objects = PublishedManager()

>>> from reviews.models import FlavorReview
>>> FlavorReview.objects.count()
35
>>> FlavorReview.objects.published().count()
31
```

替换默认model manager需要注意:
1. 使用model inheritance,abstract base classes得到父类的model manager,具体的base class的子类没有
2. 第一个被应用到model class的manager被Django作为默认的,打破了普遍的Python模式,在QuerySets中可能造成难以预测的结果

因此,在每个model class中,objects = models.Manager()应该被手动定义

[managers](docs.djangoproject.com/en/1.11/topics/db/managers/)

## 6.7 理解Fat Models

Fat Models的概念是不要将数据相关的操作放到views和templates,应该被封装到model methods,classmethods, properties, even manager methods,这样views或者task可以使用相同的逻辑。Fat Models便于重用代码,但也可能导致models的代码过多,用面向对象的观点,大问题应该被分解为多个小问题。Methods,classmethods,properties应该被保留,但其中的逻辑应该被移动到Model Behaviors或者Stateless Helper Functions。

### 6.7.1 Model Behaviors a.k.a Mixins

Models inherit logic from abstract models

[composition to reduce replication of code](blog.kevinastone.com/django-model-behaviors.html)
[using DateTimeField for logical deletes](medium.com/eshares-blog/supercharging-django-productivity-8dbf9042825e)
[Section 10.2: Using Mixins With CBVs]

### 6.7.2 Stateless Helper Functions

将逻辑移动带models外部变成使用函数,这种做法更加隔离,但缺点是函数是无状态的,因此所有参数都需要被传递

## 6.8 Summary

涉及Models需要深思熟虑。

只有在尝试了所有可能的方案后再使用反范式,可以通过raw SQL和缓存去优化相当慢和复杂的查询。

不要忘记使用索引,使用索引往往会有很好的效果。

继承abstract base classes比继承具体的models更好。

null和blank的问题可以查看书中的表。

Fat models是封装逻辑到models中的一种方法,但容易导致god objects。