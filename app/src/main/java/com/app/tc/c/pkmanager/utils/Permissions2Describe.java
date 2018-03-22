package com.app.tc.c.pkmanager.utils;

import java.util.HashMap;

import static android.Manifest.permission.*;
import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.CONTACTS;
import static android.Manifest.permission_group.LOCATION;
import static android.Manifest.permission_group.MICROPHONE;
import static android.Manifest.permission_group.PHONE;
import static android.Manifest.permission_group.SENSORS;
import static android.Manifest.permission_group.SMS;
import static android.Manifest.permission_group.STORAGE;

/**
 * Created by MrRobot on 2018/1/24.
 */

public class Permissions2Describe {
    private static HashMap map = new HashMap();
    private static boolean isCreated = false;

    public static void setup() {
        isCreated = true;
        map.put(CAMERA, "【危险】相机");
        map.put(CONTACTS, "通讯录");
        map.put(LOCATION, "位置");
        map.put(MICROPHONE, "麦克风");
        map.put(PHONE, "通话");
        map.put(SENSORS, "所有传感器");
        map.put(SMS, "【危险】短信");
        map.put(STORAGE, "【危险】存储");
        map.put(ACCESS_CHECKIN_PROPERTIES, "访问修改chenck in数据库的表属性");
        map.put(ACCESS_COARSE_LOCATION, "【危险】基于网络的大致位置");
        map.put(ACCESS_FINE_LOCATION, "【危险】精确定位");
        map.put(ACCESS_LOCATION_EXTRA_COMMANDS, "访问额外的位置提供命令");
        map.put(ACCESS_NETWORK_STATE, "访问有关GSM网络信息");
        map.put(ACCESS_NOTIFICATION_POLICY, "");
        map.put(ACCESS_WIFI_STATE, "允许应用程序访问Wi-Fi网络的信息");
        map.put(ACCOUNT_MANAGER, "允许应用进入帐户认证");
        map.put(ADD_VOICEMAIL, "【危险】添加语音邮件");
        map.put(ANSWER_PHONE_CALLS, "");
        map.put(BATTERY_STATS, "	允许应用程序收集的电池信息");
        map.put(BIND_ACCESSIBILITY_SERVICE, "辅助服务");
        map.put(BIND_APPWIDGET, "那个应用程序可以访问AppWidget的数据AppWidget服务");
        map.put(BIND_AUTOFILL_SERVICE, "");

        map.put(BIND_CARRIER_MESSAGING_SERVICE, "");
        map.put(BIND_CARRIER_SERVICES, "");
        map.put(BIND_CHOOSER_TARGET_SERVICE, "");
        map.put(BIND_CONDITION_PROVIDER_SERVICE, "");
        map.put(BIND_DEVICE_ADMIN, "必须要求的设备管理接收机，以确保只有系统可以与它进行交互");
        map.put(BIND_DREAM_SERVICE, "必须按要求由DreamService，以确保只有系统可以绑定到它");
        map.put(BIND_INCALL_SERVICE, "");
        map.put(BIND_INPUT_METHOD, "必须按要求由InputMethodService，以确保只有系统可以绑定到它");
        map.put(BIND_MIDI_DEVICE_SERVICE, "");
        map.put(BIND_NFC_SERVICE, "必须按要求由一个HostApduService 或OffHostApduService以确保只有系统可以绑定到它");
        map.put(BIND_NOTIFICATION_LISTENER_SERVICE, "必须按要求由NotificationListenerService，以确保只有系统可以绑定到它");
        map.put(BIND_PRINT_SERVICE, "必须要求的一个的PrintService，以确保只有系统可以绑定到它");
        map.put(BIND_QUICK_SETTINGS_TILE, "");
        map.put(BIND_REMOTEVIEWS, "必须按要求由一个RemoteViewsService，以确保只有系统可以绑定到它");
        map.put(BIND_SCREENING_SERVICE, "");
        map.put(BIND_TELECOM_CONNECTION_SERVICE, "");
        map.put(BIND_TEXT_SERVICE, "必须按规定由TextService（如");
        map.put(BIND_TV_INPUT, "必须按要求由一个TvInputService 以确保只有系统可以绑定到它");
        map.put(BIND_VISUAL_VOICEMAIL_SERVICE, "");
        map.put(BIND_VOICE_INTERACTION, "");
        map.put(BIND_VPN_SERVICE, "必须按要求由一个VpnService，以确保只有系统可以绑定到它");
        map.put(BIND_VR_LISTENER_SERVICE, "");
        map.put(BIND_WALLPAPER, "必须按要求由一个WallpaperService，以确保只有系统可以绑定到它");
        map.put(BLUETOOTH, "允许应用程序连接到已配对的蓝牙设备");
        map.put(BLUETOOTH_ADMIN, "允许程序发现和配对蓝牙设备");
        map.put(BLUETOOTH_PRIVILEGED, "允许应用程序配对蓝牙设备，而无需用户交互，以及允许或禁止访问通讯录或消息的访问");
        map.put(BODY_SENSORS, "身体传感器");
        map.put(BROADCAST_PACKAGE_REMOVED, "允许程序广播一个提示消息在一个应用程序包已经移除后");
        map.put(BROADCAST_SMS, "允许应用程序广播短信回执通知");
        map.put(BROADCAST_STICKY, "允许应用程序广播常用意图");
        map.put(BROADCAST_WAP_PUSH, "允许应用程序广播WAP PUSH收到通知");
        map.put(CALL_PHONE, "【危险】拨打电话");
        map.put(CALL_PRIVILEGED, "允许应用程序拨打任何号码，但是要在拨号键盘上来确认");
        map.put(CAMERA, "【危险】允许访问相机");
        map.put(CAPTURE_AUDIO_OUTPUT, "允许应用程序捕获音频输出");
        map.put(CAPTURE_SECURE_VIDEO_OUTPUT, "允许应用程序来捕捉安全视频输出");
        map.put(CAPTURE_VIDEO_OUTPUT, "允许应用程序捕获视频输出");
        map.put(CHANGE_COMPONENT_ENABLED_STATE, "允许应用程序更改应用程序组件（而非它自己）是否被启用");
        map.put(CHANGE_CONFIGURATION, "允许应用程序修改当前设置，如本地化");
        map.put(CHANGE_NETWORK_STATE, "允许应用程序改变网络连接状态");
        map.put(CHANGE_WIFI_MULTICAST_STATE, "允许应用程序进入无线多播模式");
        map.put(CHANGE_WIFI_STATE, "允许应用程序更改Wi-Fi连接状态");
        map.put(CLEAR_APP_CACHE, "允许应用程序清除设备上的所有安装的应用程序缓存");
        map.put(CONTROL_LOCATION_UPDATES, "允许启用/禁用位置更新提示从无线模块");
        map.put(DELETE_CACHE_FILES, "允许应用程序删除缓存文件");
        map.put(DELETE_PACKAGES, "允许应用程序删除包");
        map.put(DIAGNOSTIC, "允许应用程序RW诊断资源");
        map.put(DISABLE_KEYGUARD, "允许应用程序禁用键盘锁");
        map.put(DUMP, "允许应用程序检索系统服务状态转储信息");
        map.put(EXPAND_STATUS_BAR, "允许应用程序展开或折叠状态栏");
        map.put(FACTORY_TEST, "如制造商测试的应用一样用终极权限用户运行 root权限");
        map.put(GET_ACCOUNTS, "【危险】访问账户列表");
        map.put(GET_ACCOUNTS_PRIVILEGED, "");
        map.put(GET_PACKAGE_SIZE, "允许应用查找出任何程序包使用的空间");

        map.put(GET_TASKS, "允许应用找到关于当前或最近运行的任务和在哪些acitivities里运行，而推荐使用API​​ 21级不再执行");
        map.put(GLOBAL_SEARCH, "此权限可以用在内容提供者，以允许全局搜索系统来访问自己的数据");
        map.put(INSTALL_LOCATION_PROVIDER, "允许安装位置信息提供到位置管理器应用程序");
        map.put(INSTALL_PACKAGES, "允许安装的软件包的应用程序");
        map.put(INSTALL_SHORTCUT, "允许安装在发射器的快捷方式的应用程序");
        map.put(INSTANT_APP_FOREGROUND_SERVICE, "");
        map.put(INTERNET, "允许应用程序打开网络套接字");
        map.put(KILL_BACKGROUND_PROCESSES, "");
        map.put(LOCATION_HARDWARE, "允许应用程序使用定位功能在硬件上，如地理围栏API");
        map.put(MANAGE_DOCUMENTS, "允许应用程序来管理存取文件，通常作为文件选择器的一部分");
        map.put(MANAGE_OWN_CALLS, "");
        map.put(MASTER_CLEAR, "不通过第三方应用程序使用");
        map.put(MEDIA_CONTENT_CONTROL, "允许应用程序知道什么内容播放和控制它的播放");
        map.put(MODIFY_AUDIO_SETTINGS, "允许应用程序修改全局音频设置");
        map.put(MODIFY_PHONE_STATE, "允许修改话机状态 - 电源，人机接口等");
        map.put(MOUNT_FORMAT_FILESYSTEMS, "允许格式的文件系统可移动存储");
        map.put(MOUNT_UNMOUNT_FILESYSTEMS, "允许安装和卸载文件系统可移动存储");
        map.put(NFC, "允许应用程序在执行NFC的I / O操作");
        map.put(PACKAGE_USAGE_STATS, "");

        map.put(PERSISTENT_ACTIVITY, "");
        map.put(PROCESS_OUTGOING_CALLS, "【危险】监听并修改呼出电话");
        map.put(READ_CALENDAR, "【危险】允许读取日历数据");
        map.put(READ_CALL_LOG, "【危险】读取通话记录");
        map.put(READ_CONTACTS, "【危险】允许读取用户联系人数据");
        map.put(READ_EXTERNAL_STORAGE, "【危险】读取外部存储");
        map.put(READ_FRAME_BUFFER, "允许应用程序采取截屏和更多常规的访问帧缓冲数据");

        map.put(READ_INPUT_STATE, "");
        map.put(READ_LOGS, "允许应用程序读取低级别的系统日志文件");
        map.put(READ_PHONE_NUMBERS, "");
        map.put(READ_PHONE_STATE, "【危险】允许读取手机通话状态，获取电话号码");
        map.put(READ_SMS, "【危险】读取短信");
        map.put(READ_SYNC_SETTINGS, "允许应用程序读取同步设置");
        map.put(READ_SYNC_STATS, "允许应用程序读取同步状态");
        map.put(READ_VOICEMAIL, "允许应用程序读取语音邮件系统");
        map.put(REBOOT, "允许重新启动设备");
        map.put(RECEIVE_BOOT_COMPLETED, "接收启动完成广播");
        map.put(RECEIVE_MMS, "【危险】监控获取MMS彩信");
        map.put(RECEIVE_SMS, "【危险】监控获取短信息");
        map.put(RECEIVE_WAP_PUSH, "【危险】监控获取WAP PUSH信息");
        map.put(RECORD_AUDIO, "【危险】录制音频");
        map.put(REORDER_TASKS, "");
        map.put(REQUEST_COMPANION_RUN_IN_BACKGROUND, "");
        map.put(REQUEST_COMPANION_USE_DATA_IN_BACKGROUND, "");
        map.put(REQUEST_DELETE_PACKAGES, "");
        map.put(REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, "");
        map.put(REQUEST_INSTALL_PACKAGES, "");

        map.put(RESTART_PACKAGES, "");
        map.put(SEND_RESPOND_VIA_MESSAGE, "允许应用（电话）发送一个请求给其它应用程序，以处理期间来电的响应-经由消息的动作");
        map.put(SEND_SMS, "【危险】允许发送短信");
        map.put(SET_ALARM, "允许应用程序广播一个Intent设置为用户报警");
        map.put(SET_ALWAYS_FINISH, "允许应用程序来控制是否当在后台把活动间接完成");
        map.put(SET_ANIMATION_SCALE, "修改全局动画缩放因子");
        map.put(SET_DEBUG_APP, "配置调试的应用程序");

        map.put(SET_PREFERRED_APPLICATIONS, "");
        map.put(SET_PROCESS_LIMIT, "设置最大进程数");
        map.put(SET_TIME, "允许应用程序设置系统时间");
        map.put(SET_TIME_ZONE, "允许应用程序设置系统时区");
        map.put(SET_WALLPAPER, "允许应用程序设置壁纸");
        map.put(SET_WALLPAPER_HINTS, "允许应用程序设置壁纸提示");
        map.put(SIGNAL_PERSISTENT_PROCESSES, "允许应用程序请求信号发送给所有持续的进程");
        map.put(STATUS_BAR, "允许使用状态栏");
        map.put(SYSTEM_ALERT_WINDOW, "允许弹窗");
        map.put(TRANSMIT_IR, "使用红外发射器");
        map.put(UNINSTALL_SHORTCUT, "删除快捷方式");
        map.put(UPDATE_DEVICE_STATS, "更新设备统计");
        map.put(USE_FINGERPRINT, "指纹");
        map.put(USE_SIP, "【危险】使用SIP服务");
        map.put(VIBRATE, "访问振动器");
        map.put(WAKE_LOCK, "在屏幕关闭时运行后台");
        map.put(WRITE_APN_SETTINGS, "写入API设置");
        map.put(WRITE_CALENDAR, "【危险】写入（但不读取）用户的日历数据");
        map.put(WRITE_CALL_LOG, "【危险】写入（但不读取）用户的联系人数据");
        map.put(WRITE_CONTACTS, "【危险】写入（但不读取）用户的联系人数据");
        map.put(WRITE_EXTERNAL_STORAGE, "【危险】写入到外部存储");
        map.put(WRITE_GSERVICES, "	修改谷歌地图服务");
        map.put(WRITE_SECURE_SETTINGS, "读取或写入安全系统设置");
        map.put(WRITE_SETTINGS, "读取或写入系统设置");
        map.put(WRITE_SYNC_SETTINGS, "写入同步设置");
        map.put(WRITE_VOICEMAIL, "修改和删除语音邮件");
    }

    public static String getDescribe(String permission) {
        if (!isCreated)
            setup();

        String describe;
        try {

            describe = map.get(permission).toString();
        } catch (RuntimeException e) {
            describe = permission;
        }
        return describe;
    }
}
