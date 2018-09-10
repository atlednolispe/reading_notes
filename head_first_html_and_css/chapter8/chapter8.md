chapter8-增加字体和颜色样式
=========================

```
# 字体
font-family
font-size
color
font-weight
text-decoration

# sans-serif/serif/monospace/cursive/fantasy
# 候选字体列表属于同一字体系列
# 字体含空格"Courier New"
body {
    font-family: Verdana, Geneva, Arial, sans-serif;
}

# web字体,放在CSS的最上方
@font-face {
	font-family: "Emblema One";
	src: url("http://wickedlysmart.com/hfhtmlcss/chapter8/journal/EmblemaOne-Regular.woff"), 
	     url("http://wickedlysmart.com/hfhtmlcss/chapter8/journal/EmblemaOne-Regular.ttf"); 
}

# 字体大小,高度
font-size: 14px;
# 父元素的百分比大小
font-size: 150%;
# 1.2倍
font-size: 1.2em;
# 关键字
xx-small, x-small, small, medium, large, x-large, xx-large
# 关键字设置body,其余用百分比或者倍数设置

# 字体粗细,设置相对于父元素的粗细
# bold, normal, border, lighter, x100
font-weight: bold;

# 斜体,italic/oblique(倾斜)
font-style: italic;

# 颜色
# color控制文本和边框颜色(border-color)
RGB: 
100%, 100%, 100% = WHITE
60%, 60%, 60% = GRAY
# red/rgb(80%, 40%, 0%)/rgb(204, 102, 0)[0-255]/#cc6600(hex)
# #cb0 = #ccbb00
blackground-color: red;
# #xxxxxx 六位相同均是灰色 111111深灰

# 文本和背景使用对比度最大颜色

# 文本装饰
line-through/underline/overline/none
text-decoration: line-through;
# 两种效果叠加
text-decoration: underline overline;

# 删除线
<del>
# 下划线
<ins>
```

[Google Web字体](http://www.google.com/webfonts)
[FontSquirrel Web字体](http://www.fontsquirrel.com/)