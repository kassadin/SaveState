## [处理运行时变更](http://developer.android.com/intl/zh-cn/guide/topics/resources/runtime-changes.html#RetainingAnObject)

1. 在配置变更期间保留对象

	1. onSaveInstanceState() 保存数据、onRestoreInstanceState() 恢复数据
	2. 使用 Fragment.setRetainInstance(boolean) 
	
2. 自行处理配置变更

	android:configChanges="orientation|keyboardHidden|screenSize"


## [保存 View 状态](http://www.codeceo.com/article/android-save-view-state.html)

系统 View 只需要有 id 就能自动保存状态，内部已实现

自定义 View 保存状态

1. 设置 idd
2. 要调用 setSaveEnabled(true)
3. 实现 View.BaseSavedState， 在 onSaveInstanceState、onRestoreInstanceState 中保存恢复状态

id 要唯一，SparseArray container 是共享， id 重复会覆盖

## onSave/onRestore 调用时间

正常 back 销毁 activity 不会调用 onSaveInstanceState

按 home，或新 activity 覆盖当前 activity 时会调用 onSaveInstanceState

onPause -> onSaveInstanceState -> onStop

当 Activity 重新可见时，如果已被销毁，则会调用，onRestoreInstanceState，否则不调用

onCreate -> onStart -> onRestoreInstanceState -> onResume

## Else

内存不足时释放应用后，系统会保存现有的栈。 从 launcher 或 recent 启动应用会先初始化 Application，然后直接启动栈顶 activity。

如果使用了全局静态变量或依赖之前页面的数据，就可能出现问题。不过系统同时保存了启动该 Activity 的 Intent ，还会调用 onRestoreInstanceState 帮助恢复页面。

解决方法

1. 减少页面间依赖，少使用全局变量，注意保存数据，使每个页面都能正常恢复
2. 判断如果是恢复状态，则再判断一下中间页流程是否正常，不正常就直接启动第一个页面，清除现有堆栈

	```
	Intent starter = new Intent(context, AActivity.class);
	starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	```
	
activity 的 `android:alwaysRetainTaskState` 默认 false，30分钟后不保存任务栈，没找到测试方法。

## 模拟杀应用

1. Android Studio/ DDMS Terminate Application
2. adb shell am kill [com.xxx.app]

没找到更好的方法


