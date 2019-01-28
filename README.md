新手引导浮层
=====

本引导浮层框架支持如下功能：<br>
（1）添加高亮的区域，该区域可以为矩形、椭圆和圆形，并且可以为该区域增加padding。<br>
（2）在浮层上添加组件，该组件可以相对于当前已显示的某个组件的位置添加，也可以自定义显示的位置。<br>
（3）浮层的显示与消失。<br>
（4）清除高亮的区域和添加的组件，更新浮层的视图（通常为多步骤引导使用）。<br>
（5）其它功能：设置浮层是否点击可消失、点击的事件和返回键是否可取消等等。<br>
<br>

引用方式
-------
maven：
```xml
<dependency>
	<groupId>com.jimmysun</groupId>
	<artifactId>guide</artifactId>
	<version>2.0.0</version>
	<type>pom</type>
</dependency>
```
gradle：
```
compile 'com.jimmysun:guide:2.0.0'
```

示例
----
代码参见[https://github.com/jimmysuncpt/Guide/tree/master/app](https://github.com/jimmysuncpt/Guide/tree/master/app)<br>
截图：<br>
<img src="/image/device-2019-01-22-193943.png" width="30%" height="30%"/> <img src="/image/device-2019-01-22-194114.png" width="30%" height="30%"/> <img src="/image/device-2019-01-22-194201.png" width="30%" height="30%"/> <img src="/image/device-2019-01-22-194235.png" width="30%" height="30%"/> <img src="/image/device-2019-01-22-194311.png" width="30%" height="30%"/><br>

使用方式
-------
```java
GuideManager guideManager = new GuideManager(context)
        .highlightViewInRect(view) // 以矩形的方式高亮显示视图
        .highlightViewInRect(view, paddingLeft, paddingTop, paddingRight, paddingBottom) // 以矩形的方式高亮显示视图，带间距
        .highlightViewInOval(view) // 以椭圆形的方式高亮显示视图
        .highlightViewInOval(view, paddingLeft, paddingTop, paddingRight, paddingBottom) // 以椭圆形的方式高亮显示视图，带间距
        .highlightViewInCircle(view) // 以圆形的方式高亮显示视图
        .highlightViewInCircle(view, paddingRadius) // 以圆形的方式高亮显示视图，带间距
        .addViewRelativeTo(view, relativeTo, alignType, xMargin, yMargin) // 相对于某个视图添加一个视图，见注意事项（3）
        .addView(view, params) // 以指定的布局参数添加一个视图
        .updateViewLayout(view, params) // 更新视图的布局参数
        .removeView(view) // 删除一个视图
        .clear() // 清除所有高亮的区域和添加的视图
        .setCancelable(true) // 设置是否可以按返回键消失
        .setCanceledOnTouch(true) // 设置是否可以点击消失
        .setOnClickListener(onClickListener) // 设置点击事件监听器
        .setAlpha(0.7f) // 设置透明度，0~1，默认0.7
        .requestLayout(); // 更新布局
guideManager.isShowing(); // 是否正在显示
guideManager.show(); // 显示
guideManager.dismiss(); // 消失
```
### 注意事项
（1）如果要获取某个视图的具体显示位置，通常在这个视图的addOnGlobalLayoutListener()方法中使用GuideManager，代码如下：
```java
view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        // show guide
    }
});

```
（2）如果想做多步骤引导，可以嵌套使用，代码如下：
```java
// 第一步...
guideManager.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        guideManager.clear();
        // 第二步...
        guideManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideManager.clear();
                // 第三步...
            }
        });
    }
});
guideManager.show();
```
（3）addViewRelativeTo(view, relativeTo, alignType, xMargin, yMargin)方法参数说明<br>
- <B>view</B>：要添加的视图。<br>
- <B>relativeTo</B>：相对的视图。<br>
- <B>alignType</B>：对齐方式，可使用GuideManager的LEFT_OF、RIGHT_OF、ABOVE、BELOW、ALIGN_LEFT、ALIGN_TOP、ALIGN_RIGHT和ALIGN_BOTTOM，如果要使用多种对齐方式，可以采用“或”的方式，例如“GuideManager.RIGHT_OF | GuideManager.BELOW”。<br>
- <B>xMargin</B>：横向的距离，单位px。<br>
- <B>yMargin</B>：纵向的距离，单位px。<br>
对齐方式及相对距离如图所示：<br>
<img src="/image/align_type.png"/>
