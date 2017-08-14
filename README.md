# socialLibrary
社交分享相关代码封装

[![wxsocial](https://jitpack.io/v/daohen/socialLibrary.svg)](https://jitpack.io/#daohen/socialLibrary)

使用方法：

（1）在build.gradle文件中配置如下：
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.daohen:personalToolboxLibrary:xx'
    compile 'com.github.daohen:thirdPartyLibrary:xx'
    compile 'com.github.daohen:WxLibrary:xx'
}
```
上面依赖的其它两个libray详情可参照
[personalToolboxLibrary][1]
[thirdPartyLibrary][2]

（2）在包名的根目录下新建wxapi，并把一个extends WxCallbackActivity的Activity放在它下面,以上，就可以调用封装的方法，具体用法都在WxProvider中。
使用前最好在Application中先调用以下方法实现注册app到微信
```java
WxProvider.get().registerWx()
```
然后，就可以调用下面各种方法来做分享操作
```java
WxProvider.get().shareText()
WxProvider.get().shareBitmap()
WxProvider.get().shareMusic()
...
```

如果做登录操作的话，直接调用
```java
WxProvider.get().login()
```

代码混淆
```
-keep class com.tencent.mm.opensdk.** {*;}
```

[1]:https://github.com/daohen/personalToolboxLibrary
[2]:https://github.com/daohen/thirdPartyLibrary