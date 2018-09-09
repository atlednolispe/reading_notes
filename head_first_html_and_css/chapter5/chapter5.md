chapter5-为你的页面增加图像
=========================

```
# 图片格式
JPEG: 照片,有损,不支持透明和动画,文件比较小
PNG: 单色图像,LOGO,剪切画,无损可透明
GIF: 单色,LOGO,无损,最多256色,支持一种颜色透明,支持动画

# <img>: 内联元素
# src,不能用href属性
<img src="images/drinks.gif" alt="desc of the image" width="48" height="100">
宽高度单位是像素,但最好不要有这两个属性设置,因为调整图像大小前仍然要获取整个大图像
建议图片最大宽度800px
css像素: 1英寸96像素

psd: 若需要透明显示图片,需要反锯齿处理transparency-matte选择边沿光晕
```