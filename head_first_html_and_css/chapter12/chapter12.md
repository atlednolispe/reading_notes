chapter12-HTML5标记
===================

```
# header footer aside section article

# time
# time内容如果没有采用官方Internet格式,就必须有datetime属性
# 2012/2012-02/2012-02-18/2012-02-18 09:00/05:00/2012-02-18 09:00Z
<time datetime="2012-02-18">2/18/2012</time>

# 导航栏
nav ul {
	margin: 0px;
	list-style-type: none;
	padding: 5px 0px 5px 0px;
}
nav ul li {
	display: inline;
	padding: 5px 10px 5px 10px;
}
nav ul li a:link, nav ul li a:visited {
	color: #954b4b;
	border-bottom: none;
	font-weight: bold;

/*
	text-shadow: 1px 1px 3px #e2c2c2; 
	text-transform: uppercase;
*/
}
nav ul li.selected {
	background-color: #c8b99c;
}

# 选择器中优先级 , > 空格

# video
# 还有一个preload控制视频加载
<video controls
       autoplay
       loop
       width="512" height="288"
       src="video/tweetsip.mp4"
       poster="images/poster.png"
       id="video">
</video>

# 浏览器容器->视频编码+音频编码

# src使用的是具体文件的容器
# type指定容器格式,''
# codecs指定编码器: "视频编码器, 音频编码器",可省略
<video controls autoplay>
    <source src="video/tweetsip.ogv" type='video/ogg; codecs="theora, vorbis"'>
    <source src="video/tweetsip.mp4" type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
    <source src="video/tweetsip.webm" type='video/webm; codecs="vp8, vorbis"'>
    <object>flash...</object>
    <p>Sorry, your browser doesn't support the video element</p>
</video>
```