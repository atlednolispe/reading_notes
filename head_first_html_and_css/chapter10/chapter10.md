chapter10-div与span
===================

```
# logical section: div
div不存在任何默认样式,只是一个块元素,上下方会有默认换行

# width只指定内容区宽度,块元素默认宽度auto:延伸占满整个浏览器

# text-align: 对块元素中所有内联内容对齐,且只能在块元素设置,内联元素设置无效(例如<img>),块元素会继承父div的这个属性

# 子孙选择器
# 所有子孙
#elixirs h2 {
    color: black;
}

# 直接子孙
#elixirs>h2

# line-height: 1;
line-height直接设置数字使得子元素行高是子元素字体大小的指定倍数,设置%/em是父元素字体的倍数

# 指定边距
# 上右下左
padding: 0px 20px 30px 10px;

# 上下左右(顺时针)
padding: 20px;

# (上下) (左右)
padding: 0px 20px;

# border/background可以用任意顺序
border: thin solid #007e7e;
background: white url(images/cocktail.gif) repeat-x;

# 字体
font: [font-style font-variant font-weight] font-size[/line-height] font-family1, [font-family2]

# span: 类似于div,内联元素

# 内联元素四周加外边距只有左右有效果,内联元素上下内边距不会影响包围他的其他内联元素边距,会与其他内联元素重叠

# 根据状态指定样式,有先后顺序,link visited hover focus
# link visited hover focus(tab) active(第一次单击一个链接)
a:link {
    color: green;
}

# 伪类
# :link :visited xx的第x个xx

# attr: ... !important 读者覆盖作者样式

# 层叠
1. 收集样式表
2. 找到所有匹配声明
3. 对规则排序: 作者 > 读者 > browser
4. 根据选择器特定性对声明排序: id, class/pseudo-class, element-name(二进制大小排序)
5. 冲突的规则按样式表出现顺序排序

# float属性
```