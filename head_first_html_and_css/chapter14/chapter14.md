chapter14-HTML表单
==================

```
# form
<form action="http://wickedlysmart.com/hfhtmlcss/contest.php" method="POST">
    <p>
        Just type in your name (and click Submit) to enter the contest:<br>

        First name: <input type="text" name="firstname" value=""><br>
        Last name:  <input type="text" name="lastname" value=""><br>
        <input type="submit">
    </p>
</form>

# input,属于内联元素

<input type="text" name="fullname" placeholder="plz input your name" required>
<input type="submit">
# 单选
<input type="radio" name="hotornot" value="hot">  /* 同组要name相同, bool属性checked默认选中 */
<input type="radio" name="hotornot" value="not">
# 复选
<input type="checkbox" name="spice" value="Salt">
<input type="checkbox" name="spice" value="Pepper">

<textarea name="comments" rows="10" cols="48">hello</textarea>

<select name="characters">
    <option value="Buckaroo">Buckaroo Banzai</option>
    <option value="Tommy">Perfect Tommy</option>
</select>

<input type="number" min="0" max="20">
<input type="range" min="0" max="20">
<input type="color">
<input type="data">
<input type="email">  /* email tel url 都是text*/

<input type="password" name="passwd">

<input type="file" name="doc">

<select name="chars" multiple></select>

# 每个表单输入控件要有唯一的name属性

# label
<label for="hot">hot</label>

# <fieldset>包围一组input,<legend>为组提供标签
```