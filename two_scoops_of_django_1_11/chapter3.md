Chapter3-How to Lay Out Django Projects
=======================================

## 3.1 1.11's 默认布局

默认布局对于真实项目开发不是很合理需要修改

## 3.3 推荐的项目布局模版

```
  icecreamratings_project
  ├── config/
  │   ├── settings/
  │   ├── __init__.py
  │   ├── urls.py
  │   └── wsgi.py
  ├── docs/
  ├── icecreamratings/
  │   ├── media/  # Development only!
  │   ├── products/
  │   ├── profiles/
  │   ├── ratings/
  │   ├── static/
  │   └── templates/
  ├── .gitignore
  ├── Makefile
  ├── README.rst
  ├── manage.py
  └── requirements.txt
```

## 3.4 virtualenv在哪

只需要把requirements.txt加入VCS,virtualenv不需要添加进来,并且virtualenv应该与项目分离

## 3.5 超越startproject

使用cookiecutter创建Django模版

```bash
$ pipenv install cookiecutter
$ cookiecutter https://github.com/pydanny/cookiecutter-django
```

之后可以了解一下这个项目,不知道后面有没有介绍如何对接docker

## 3.6 Summary

如何布局应该被清晰地文档说明