# CalendarView
日历控件   
### GIF示例

![gif示例](https://github.com/ssccbb/CalendarView/blob/master/calendar.gif)

### 如何使用
1. java文件迁移至项目（adapter包下 / CalendarUtils / CalendarView）
2. 资源文件迁移（layout以及drawable）
3. 项目依赖 *com.android.support:recyclerview*
4. xml中使用 *<你的包名.view.CalendarView/>*

### 一些说明
该项目实现的主要功能如下
- 可自定月份数量的日历的展示/切换
- 日历签到
- 区别显示当前月份日期
- ContentProvider存储日期数据

默认当前月份在中间页，一共7页前三后三，需要更改前后显示的月份跨度请更改CalendarView内*DEFAULT_MONTH_NUM*的默认值。
*drawable*四个文件分别为选中的四个状态（根据需求替换）
- 当前选中左右选中
- 当前选中右选中
- 当前选中左选中
- 当前选中左右为选中

各类颜色使用android的基本色，xml内可自行更换

当前项目系列文章讲解移步blog：

[基于ViewPager+Recyclerview实现的CalendarView视图(一)](http://zhouqipa.com/2017/07/21/%E5%9F%BA%E4%BA%8EViewPager+Recyclerview%E5%AE%9E%E7%8E%B0%E7%9A%84CalendarView%E8%A7%86%E5%9B%BE(%E4%B8%80).html)

[基于ViewPager+Recyclerview实现的CalendarView视图(二)](http://zhouqipa.com/2018/03/08/%E5%9F%BA%E4%BA%8EViewPager+Recyclerview%E5%AE%9E%E7%8E%B0%E7%9A%84CalendarView%E8%A7%86%E5%9B%BE(%E4%BA%8C).html)
