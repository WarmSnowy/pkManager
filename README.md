# pkManager


## Introduce
一款基于Xposed框架开发的手机安全管理软件，目前实现的功能为权限管理。

## Issues 宝贵意见
如果有任何问题，请到github的[issue处][1]写上你不明白的地方，也可以通过下面提供的方式联系我，我会及时给予解决。

If you have any questions, please write to [the issue][1] of making you don't understand of place, also can contact me through here, I will help them in time.

## Features 特性
* 1、基于Xposed动态拦截程序的API访问请求，采用返回空值或指定值的方式来保护隐私，这样减少了直接禁用权限所造成的APP崩溃。
* 2、采用objectbox来存储配置，相比于使用传统SQLite更简便，效率也更高。
* 3、...

## Thanks
 - 主要功能参考了小米的应用管家，不过目前只实现了一个权限管理功能，后续会慢慢补全。这里列出主要参考的内容。
 
 - UI界面：[KuaiChuan][2].用了它的主界面的大部分代码。图标来自[Ionic][3].

 - 参考项目：[XPrivacyLua][4].

 - 使用到的库：[objectbox][4]、[butterknife][5]等等。
 
## End
> 注意：此开源项目仅做学习交流使用，如用到实际项目还需进一步完善，请多多斟酌。如果你觉得不错，对你有帮助，欢迎点个fork，star，follow，也可以帮忙分享给你更多的朋友，这是给我们最大的动力与支持。

## About me
 - **QQ：** 372351310
 - **Email：** tianyue_3176@qq.com
 
## License
[GNU General Public License version 3](https://www.gnu.org/licenses/gpl.txt)

Copyright (c) 2017-2018 Tianyue. All rights reserved

XPrivacyLua is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

XPrivacyLua is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with XPrivacyLua. If not, see [https://www.gnu.org/licenses/](https://www.gnu.org/licenses/).

[1]:https://github.com/WarmSnowy/pkManager/issues
[2]:https://gitee.com/mayubao2015/KuaiChuan
[3]:http://ionicframework.com/
[4]:https://github.com/M66B/XPrivacyLua
[4]:http://objectbox.io/
[5]:http://jakewharton.github.io/butterknife/