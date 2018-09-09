Chapter3-构建模块
=================

```
# <q>: 引用,文字两侧加",内联元素,行内出现

# <blockquote>: 多段文字引用,自带缩进,本身就是一个新段落,块元素(前后相当于有\n)

# <blockquote>中有多段文字
<blockquote><p></p></blockquote>

# <em>: 强调

# <br>: 换行符,void元素,没有实际内容,只需要一个开始标记

# 正常元素必须有start tag + end tag

# 列表, <li> <ol> <ul>都是块元素,列表之间可以嵌套
# <li> + <ol> / <ul>
# <ol>/<ul>只能包含<li>

# 定义列表 <dl> = <ol>, <dt> + <dd> = <li>: 定义加描述
    <dl>
      <dt>Burma Shave SIgns</dt>
        <dd>Road Signs common in the U.S.</dd>
      <dt>Route 66</dt>
        <dd>Most famous</dd>
    </dl>

# character entity
>: &gt;
<: &lt;
&: &amp;
&#(\d+);

<time>
<code>
<strong>
<pre>: 以输入原样显示
```

[character entity](http://www.unicode.org/charts/)