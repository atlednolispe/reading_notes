chapter9-盒模型
===============

```
# line-height: 行与行之间的间隔
body {
    line-height: 1.6em;  /* 两行的上侧距离是字体的1.6倍 */
}

# 盒模型,所有元素都被当做盒子
margin: 透明
border
padding: 透明,元素背景色/图只延伸到内边距 
content area: 大小 = text/img

#guarantee {
    line-height: 1.9em;
    font-style: italic;
    font-family: Georgia, 'Times New Roman', Times, serif;
    color: #444444;
    /*
     * border 圆角
     * border-radius: 15px;
     * border-top-left: 3em;
     * top bottom left right
     */
    border-color: white;  /* border-top-color */
    border-width: 1px;  /* thin/5px */
    border-style: dashed;  /* solid, double, groove, outset, inset, dotted, dashed, ridge */
    background-color: #a7cece;
    padding: 25px;
    padding-left: 80px;  /* 与padding之间会覆盖 */
    margin: 30px;
    margin-right: 250px;    
    background-image: url(images/background.gif);
    background-repeat: no-repeat;  /* repeat-x, repeat-y, inherit */
    background-position: top left;    
}

# 多个元素定义样式使用class
# 单个元素使用id
# id只能一对一,id中不能出现空鼓或者特殊字符
#footer {
    color: red;
}
p#footer {
    color: red;
}

# class要求字母开头
# id可以字母或者数字开头
# id/class: 字母,数字,_,不能有空格

# 设置样式表针对的设备类型 media
<link type="text/css" rel="stylesheet" href="lounge.css" media="screen and (min-width: 481px)">
<link type="text/css" rel="stylesheet" href="lounge-mobile.css" media="screen and (max-width: 480px)">
<link type="text/css" rel="stylesheet" href="lounge-print.css" media="print">

# 媒体查询
@media screen and (min-device-width: 481px) {
    #guarantee {
        margin-right: 250px;
    }
}

# max-device-width 设备实际屏幕
# max-width 浏览器当前窗口大小
```