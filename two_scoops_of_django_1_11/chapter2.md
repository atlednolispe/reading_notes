Chapter2-The Optimal Django Environment Setup
=============================================

## 2.1 在开发和生产环境使用相同的数据库

### 2.1.1 无法保证完全相同的数据备份进行检测

### 2.1.2 不同数据库存在不同的字段类型和限制

Django推荐使用PostgreSQL,生产环境不要使用SQLite3

## 2.2 使用pip和virtualenv

### 2.2.1 virtualenvwrapper

现在应该是更推荐pipenv

## 2.3 通过pip安装Django和其他依赖

## 2.4 使用版本控制系统

## 2.5 配置相同的环境

### 2.5.1 Docker