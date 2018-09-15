chapter11-布局与定位
===================

```
# flow
块元素从上往下,\n分隔
内联元素从左上到右下,中间没有空格

# 上下放置两个块元素会选择两者中较大的外边距作为共同的折叠外边距
h1: margin: 10px;
-- margin1 + margin2 -> 20px
h2: margin: 20px;

# 内联元素一般不设置外边距,除了<img>

# 元素嵌套时外边距会折叠,但如果外侧元素存在border则不会折叠

# float: 尽可能远地向左或者向右(根据float的值)浮动一个元素,他下面的元素会绕流这个元素
# 所有浮动元素必须有一个width
# 元素正常流向页面,遇到float元素会尽可能将其放置在(L/R),并从流中删除,好像浮在页面上
# 块元素会占据浮动元素的下方位置,内联元素会考虑浮动元素边界围绕
# 浮动元素外边距不会折叠

# 对a设置边框
a:visited {
    color: #675c47;
    text-decoration: none;
    border-bottom: thin dotted #675c47;
}

# clear 元素(L/R)不允许有浮动内容

# liquid/frozen layouts

# jello layouts
#allcontent {
    margin-left: auto;
    margin-right: auto;
}

# position 默认是static,fixed是相对于浏览器(0, 0),absolute是父元素(0, 0),relative

# absolute positioning
# absolute + 两种方向(top + right) + width
# 从流中删除,浮动在上方
# 流中元素完全不知道绝对定位的元素存在,内联元素也不知道
# z-index决定重叠
# absolute不指定width会导致右侧占满
#sidebar {
    position: absolute;
    top: 100px;
    right: 200px;
    width: 280px;
}

# CSS表格显示
# 每个单元格包含一个块元素,直接子元素要是一个块元素,一般为<div>
1. 表格div
#tableContainer {
  display: table;
  border-spacing: 10px;  /* 相当于外边距,但与margin不会折叠 */
}
2. 行div
div#tableRow {
    display: table-row;
}
3. 单元格
#main {
    display: table-cell;
    ...
}

# vertical-align: top;
# 相对单元格上边对齐

# CSS表格是用来显示一种表格布局,HTML表格面向的是建立表格的数据结构

# 固定定位
fixed

# CSS要求关闭text-decoration使用border来建立链接下划线

# 相对定位
position: relative;
元素仍然属于流中,但按指定量偏移

# 视窗是页面的可见区域
```