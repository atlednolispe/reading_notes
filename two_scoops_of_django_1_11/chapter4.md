Chapter4-Fundamentals of Django App Design
==========================================

## 4.1 Django App的设计法制

Write programs that do one thing and do it well.

An app can be explained in a single sentence, or to be broken up.

## 4.2 如何命名Django Apps

app名字通常是model的复数形式,但也有例外(flavors, animals, blog, ...)

app名和URL相对应更为重要,清晰地表示app对应网站的哪一部分,甚至连下划线也应该尽量避免

## 4.3 产生疑问时保持app尽量小

不要对于完美的app设置心生焦虑,必要时拆解并重写,多个小app优于巨大的app

## 4.4 App布局

```bash
# Common modules
scoops/
├── __init__.py
├── admin.py
├── forms.py
├── management/
├── migrations/
├── models.py
├── templatetags/
├── tests/
├── urls.py
├── views.py

# uncommon modules
scoops/
├── api/
├── behaviors.py
├── constants.py
├── context_processors.py
├── decorators.py
├── db/
├── exceptions.py
├── fields.py
├── factories.py
├── helpers.py
├── managers.py
├── middleware.py
├── signals.py
├── utils.py
├── viewmixins.py
```

## 4.5 Summary

Each app should be tightly focused on its own task.