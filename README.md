## [处理运行时变更](http://developer.android.com/intl/zh-cn/guide/topics/resources/runtime-changes.html#RetainingAnObject)

1. 在配置变更期间保留对象

	1. onSaveInstanceState() 保存数据、onRestoreInstanceState() 恢复数据
	2. 使用 Fragment.setRetainInstance(boolean) 
	
2. 自行处理配置变更

	android:configChanges="orientation|keyboardHidden|screenSize"


## [保存 View 状态](http://www.codeceo.com/article/android-save-view-state.html)

系统 View 只需要有 id 就能自动保存状态，内部已实现

自定义 View 保存状态

1. 设置 id
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

## Application 里 getApplicationContext 就是自己

```
 Context applicationContext = getApplicationContext();
Log.d("app", String.valueOf(applicationContext == this)); // true
```

## 退出应用

退出应用后 activity 应当全部释放，应用状态应当是 ` AppContexts: 1   Activities: 0`，可以检查下是否存在内存泄露

## Fragment 重叠问题

[Android fragment 重叠问题的解决方法](http://blog.csdn.net/weizongwei5/article/details/44036543)
[关于Fragment的那些事、那些坑](http://www.jianshu.com/p/e658ffc02211)
[知乎相关回答](https://www.zhihu.com/question/39662488/answer/82469372)

当 Activity 销毁时系统是会保存一部分状态的，这其中就包括了 Fragment，在恢复的时候先恢复 Fragment，这也就导致了 Fragment 的重叠问题，解决办法：

1. onSaveInstanceState 时找回 fragment

	```
	oncreate{
	mFragmentManager = getSupportFragmentManager();
	if (savedInstanceState != null) { // 恢复数据
		mFragment0 = (MainFragment) mFragmentManager.findFragmentByTag("f0");
		}
	}
	```

2. 在可能销毁时，重写onSaveInstanceState，但不保存 Fragment 状态
	1. 注释掉 super，这个处理太粗糙了
	2. 移除掉 super 保存的 "android:support:fragments"

	```
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
		  outState.putParcelable("android:support:fragments", null);
	}
	```

## ViewPager 内 Fragment 恢复问题

之前 adapter的写法有问题，导致应用被杀恢复后 ViewPager 的 Fragment 会重复创建，一般情况下对应用没什么影响，就是多了个 Fragment 实例，不会被调用，也会随应用的退出而销毁，但是如果 Fragment 内的相关变量的话，会导致 ViewPager 在恢复 Fragment 的时候出问题。

这个是在外部给 Fragment 赋 Presenter 的时候发现的。

```java
 @Override
public Object instantiateItem(ViewGroup container, int position) {
	if (mCurTransaction == null) {
		mCurTransaction = mFragmentManager.beginTransaction();
	}

	final long itemId = getItemId(position);

	// Do we already have this fragment?
	String name = makeFragmentName(container.getId(), itemId);
	Fragment fragment = mFragmentManager.findFragmentByTag(name);
	if (fragment != null) {
		if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
		mCurTransaction.attach(fragment);
	} else {
		fragment = getItem(position);
		if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
		mCurTransaction.add(container.getId(), fragment,
				makeFragmentName(container.getId(), itemId));
	}
	if (fragment != mCurrentPrimaryItem) {
		fragment.setMenuVisibility(false);
		fragment.setUserVisibleHint(false);
	}

	return fragment;
}
```

Fragment 被找了回来，如果在创建的时候有相关的额外操作，这种恢复方式就会出问题。
所以 Fragment 需要的东西还是在内部自给自足吧。

另外就是不要自己再用List<Fragment> fragments 这样的形式提前创建 Fragment了，PagerAdapter内有自己的处理，在没找到缓存的 Fragment时才会调用 getItem， 提前创建会导致在恢复时重复创建。

```java
 private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
```
这是 FragmentPagerAdapter 内部给Fragment创Tag的规则，私有的。艹。还是别用了。

所以Fragment的问题就是不缓存、手动创建的自己恢复、ViewPager创建ViewPager恢复。

