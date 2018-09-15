chapter13-表格与更多列表
======================

```
# 表格 table -> tr -> th/td
# 单元格空需要用<td></td>代替
<table>
    <caption>
        The cities I visited on my
        Segway'n USA travels
    </caption>
    <tr>
        <th>City</th>
        <th>Date</th>
        <th>Temperature</th>
        <th>Altitude</th>
        <th>Population</th>
        <th>Diner Rating</th>
    </tr>
    <tr>
        <td>Walla Walla, WA</td>
        <td>June 15th</td>
        <td>75</td>
        <td>1,204 ft</td>
        <td>29,686</td>
        <td>4/5</td>
    </tr>
</table>

# 标题位置
# caption-side: bottom;

# border-spacing = 单元格外边距

# 设置table属性而不是单元格属性
# border-spacing: 10px 30px;(水平 垂直)

# 折叠边框,忽略表格边框距离
# border-collapse: collapse;

# 伪类选择奇偶段落even, odd, an+b
p:nth-child(even) {
    background-color: red;
}

# 单元格跨行 rowspan/colspan
<tr>
    <td rowspan="2">Truth or Consequences, NM</td>
    <td class="center">August 9th</td>
    <td class="center">93</td>
    <td rowspan="2" class="right">4,242 ft</td>
    <td rowspan="2" class="right">7,289</td>
    <td class="center">5/5</td>
</tr>
<tr>
    /* 跨多行的单元格跳过不用填 */
    <td class="center">August 27th</td>
    <td class="center">98</td>
    <td class="center">
</tr>

# 列表标记类型
list-style-type: disc/circle/square/none;
# 定制列表标记
list-sytle-image: url(images/backpack.gif)
# 标记在文本内部还是外部
list-style-postion: inside/outside;
```