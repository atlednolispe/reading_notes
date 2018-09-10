chapter7-CSS入门
================

```
# CSS
p {
    background-color: red;
    border: 1px solid gray;
}

# 多元素共用一个规则
h1, h2 {
    font-family: sans-serif;
    color: gray;
}

# CSS会在原来的基础上添加样式

# 两种下划线
# 整个页面
border-bottom: 1px solid black;
# 仅文字部分
text-decoration: underline;

# 只能对body部分增加样式

# rel: link的文件与HTML之间的关系
<link type="text/css" rel="sytlesheet" href="lounge.css">

# 子元素会继承和覆盖父元素样式

# CSS注释 /* */   

# 通过为元素添加class属性归属类
p.greentea {
    color: green;
}

# 选取类中的多种元素
blockquote.greentea, p.greentea {
    color: green;
}

# 属于类的所有元素
.greentea {
    color: green;
}

# 元素属于多个类,class中书写顺序对样式没有决定权
<p class="greentea raspberry blueberry">

# 样式顺序
选择器 -> 父类 -> 默认值
# 多选择器共同选择
谁最特殊谁优先,级别相同CSS文件中靠后的优先

# CSS若错误他以下的都会失效

# attr
color: 文本颜色
font-weight: 文本粗细
left: 元素左边所在位置
line-height: 文本元素中的行间距
border: 元素边框
padding: 元素边缘和内容的空间(内边距)
font-size: 文本大小
text-align: 文本对齐
letter-spacing: 字母间距
font-style: 斜体
list-style: 列表项外观
background-image: 背景图
```

[CSS验证](http://jigsaw.w3.org/css-validator)