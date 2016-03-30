package com.phonemate.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.rxx.fast.utils.LUtils;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：辅助功能
 * 创建人：colorful
 * 创建时间：15/11/20 14:37
 * 修改人：colorful
 * 修改时间：15/11/20 14:37
 * 修改备注：
 */
public class PMAccessibilityService extends AccessibilityService {


    /**
     * Tag for logging.
     */
    private static final String TAG = "HBAccessibilityService";

    /**
     * 微信的包名
     */
    private final String WECHAT_WX_PACKAGENAME = "com.tencent.mm";

    /**
     * QQ包名
     */
    private final String WECHAT_QQ_PACKAGENAME = "com.tencent.mobileqq";
    /**
     * 微信红包消息的关键字
     */
    private final String WX_HONGBAO_TEXT_KEY = "[微信红包]";

    /**
     * 红包消息的关键字
     */
    private final String QQ_HONGBAO_TEXT_KEY = "[QQ红包]";

    private AccessibilityNodeInfo lastQQHBInfo = null;

    private AccessibilityNodeInfo lastHbNodeInfo;
    private AccessibilityNodeInfo nowNodeInfo;
    private AccessibilityNodeInfo editNodeInfo;
    private AccessibilityNodeInfo sendNodeInfo;
    private boolean isAutoAnswer = false;
    private int lastNum = 0;
    private int nowNum;

    private MessageReceiver mMessageReceiver;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LUtils.e(TAG, event.getClassName().toString() + ">>" + event.getEventType() + ">>getPackageName:" + event.getPackageName());
        //com.tencent.mm.ui.LauncherUI 微信聊天列表界面 或者微信主界面

        //qq 聊天界面 com.tencent.mobileqq.activity.SplashActivity
        //qq 列表界面 红包显示内容 [QQ红包] 普通红包
        //消息通知 红包显示 [QQ红包]  普通红包  [QQ红包]口令
        //拆开红包后界面 cooperation.qwallet.plugin.QWalletPluginProxyActivity
        //通知栏事件非当前聊天界面有红包关键字就会触发

        //消息列表界面 点击拆开(普通红包) 口令红包(口令红包)


        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {//打开通知栏
            List<CharSequence> texts = event.getText();
            if (!texts.isEmpty()) {
                for (CharSequence t : texts) {
                    String text = String.valueOf(t);
                    LUtils.i(TAG, "消息:" + text);
                    if (text.contains(WX_HONGBAO_TEXT_KEY) || text.contains(QQ_HONGBAO_TEXT_KEY)) {
                        openNotify(event);
                        break;
                    }
                }
            }
        } else if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (WECHAT_WX_PACKAGENAME.equals(event.getPackageName().toString())) {//微信
                openWXHongBao(event);
            } else if (WECHAT_QQ_PACKAGENAME.equals(event.getPackageName().toString())) {//qq
                openQQHongBao(event);
            }
        } else if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            if (WECHAT_WX_PACKAGENAME.equals(event.getPackageName().toString())) {//微信
                AccessibilityNodeInfo nodeInfo = event.getSource();
                if (nodeInfo != null) {
                    nowNum = nodeInfo.getChildCount();
                    nowNodeInfo = nodeInfo.getChild(nodeInfo.getChildCount() - 1);
                }
                openWXHongBao(event);
            } else if (WECHAT_QQ_PACKAGENAME.equals(event.getPackageName().toString())) {//qq
                openQQHongBao(event);
            }

        } else if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            if (WECHAT_QQ_PACKAGENAME.equals(event.getPackageName().toString())) {//qq
                openQQHongBao(event);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 打开通知栏消息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void openNotify(AccessibilityEvent event) {
        if (event.getParcelableData() == null || !(event.getParcelableData() instanceof Notification)) {
            return;
        }
        Notification notification = (Notification) event.getParcelableData();
        lastHbNodeInfo = null;
        PendingIntent pendingIntent = notification.contentIntent;
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private synchronized void openWXHongBao(AccessibilityEvent event) {
        LUtils.i("event className:" + event.getClassName());
        if (event == null) {
            return;
        }
        if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(event.getClassName())) { //红包界面,
            OpenWXHb();
        } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(event.getClassName())) {//拆完红包界面
//            performGlobalAction(GLOBAL_ACTION_BACK);
            isAutoAnswer = true;
        } else if ("com.tencent.mm.ui.LauncherUI".equals(event.getClassName())) { //聊天界面
            OpenWXHbFromHistory(event);
        } else {
            OpenWXHbFromHistory(event);
        }
    }

    /**
     * QQ红包控制中心
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private synchronized void openQQHongBao(AccessibilityEvent event) {
        if (event == null) {
            return;
        }
        if ("cooperation.qwallet.plugin.QWalletPluginProxyActivity".equals(event.getClassName())) { //红包界面,
//            performGlobalAction(GLOBAL_ACTION_BACK);
        } else {
            findQQHBNodeInfo(event);
        }
    }

    /**
     * 遍历节点
     */
    public void findNodeInfo(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getChildCount() == 0) {
            LUtils.i(TAG, "findNodeInfo" + nodeInfo.getText() + ">>" + nodeInfo.getText());
        } else {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                findNodeInfo(nodeInfo.getChild(i));
            }
        }
    }


    //微信拆开红包
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void OpenWXHb() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        findWXOpenNodeInfo(nodeInfo).performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    /**
     * 查找微信红包开按钮
     */
    public AccessibilityNodeInfo findWXOpenNodeInfo(AccessibilityNodeInfo nodeInfo) {
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            if ("android.widget.Button".equals(nodeInfo.getChild(i).getClassName())) {
                return nodeInfo.getChild(i);
            }
        }
        return null;
    }


    public void clickSend(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getChildCount() > 0) {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                LUtils.i(TAG, "nodeInfo:" + nodeInfo.getChild(i).getClassName() + ">" + nodeInfo.getChild(i).getText());
                if (!TextUtils.isEmpty(nodeInfo.getChild(i).getText())) {
                    if ("发送".equals(nodeInfo.getChild(i).getText().toString())) {
                        nodeInfo.getChild(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
                clickSend(nodeInfo.getChild(i));
            }
        }
    }

    public void clickKouLing(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getChildCount() > 0) {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                LUtils.i(TAG, "nodeInfo:" + nodeInfo.getChild(i).getClassName() + ">" + nodeInfo.getChild(i).getText());
                if (!TextUtils.isEmpty(nodeInfo.getChild(i).getText())) {
                    if ("点击输入口令".equals(nodeInfo.getChild(i).getText().toString())) {
                        nodeInfo.getChild(i).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
                clickKouLing(nodeInfo.getChild(i));
            }
        }
    }

    /**
     * 查找qq红包入口
     */
    public void findQQHBNodeInfo(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("点击拆开");//一般红包
        if (list != null && list.size() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                AccessibilityNodeInfo parent = list.get(i).getParent();
                if (parent != null) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
            }
        }

        if (list == null || list.isEmpty()) {
            list = nodeInfo.findAccessibilityNodeInfosByText("口令红包");//口令红包
            if (list != null && list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    AccessibilityNodeInfo parent = list.get(i).getParent();
                    if (parent != null) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        clickKouLing(event.getSource());
                        clickSend(event.getSource());
                        break;
                    }
                }
            }
        }

        //个性红包
        if (list == null || list.isEmpty()) {
            getQQDIYHongBao(getRootInActiveWindow());
        }

        if (list == null || list.isEmpty()) {//聊天 列表界面
            findQQKey(getRootInActiveWindow());
        }
    }


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        registerMessageReceiver();
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION").equals(intent.getAction())) {
                if ("back".equals(intent.getStringExtra("action"))) {
                    LUtils.i(TAG,"GLOBAL_ACTION_BACK");
                    boolean isDone = performGlobalAction(GLOBAL_ACTION_BACK);
                }
            }
        }
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION");
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMessageReceiver != null) {
            unregisterReceiver(mMessageReceiver);
        }

    }



    /**
     * 查找自定义红包
     */
    public void getQQDIYHongBao(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getChildCount() > 0) {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                if (!TextUtils.isEmpty(nodeInfo.getChild(i).getText())) {
                    if ("QQ红包个性版".equals(nodeInfo.getChild(i).getText().toString())) {
                        if (!nodeInfo.getChild(i).getParent().equals(lastQQHBInfo)) {
                            nodeInfo.getChild(i).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            lastQQHBInfo = nodeInfo.getChild(i).getParent();
                        }
                    }
                }
                getQQDIYHongBao(nodeInfo.getChild(i));
            }
        }
    }

    public void findQQKey(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.getChildCount() == 0) {
            if (!TextUtils.isEmpty(nodeInfo.getText())) {
                if (nodeInfo.getText().toString().contains(QQ_HONGBAO_TEXT_KEY)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        } else {
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                findQQKey(nodeInfo.getChild(i));
            }
        }
    }


    //微信点击聊天记录的红包
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void OpenWXHbFromHistory(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("领取红包");//别人发出的红包
        if (list == null || list.isEmpty()) {
            list = nodeInfo.findAccessibilityNodeInfosByText("查看红包");//我发出的红包
        }
        if (list == null || list.isEmpty()) {//好友列表界面
            //好友列表
            list = nodeInfo.findAccessibilityNodeInfosByText(WX_HONGBAO_TEXT_KEY);
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        } else {
            if (lastHbNodeInfo != null && !lastHbNodeInfo.equals(nowNodeInfo)) {
                return;
            }
            if (nowNum == lastNum && (lastHbNodeInfo != null && lastHbNodeInfo.equals(nowNodeInfo))) {
                return;
            }
            lastNum = nowNum;
            for (int i = list.size() - 1; i >= 0; i--) {
                AccessibilityNodeInfo parent = list.get(i).getParent();
                lastHbNodeInfo = parent.getParent();
                if (parent != null) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
            }
        }
    }


}
