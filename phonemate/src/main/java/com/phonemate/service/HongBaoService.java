//package com.phonemate.service;
//
//import android.accessibilityservice.AccessibilityService;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.PendingIntent.CanceledException;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.util.Log;
//import android.view.accessibility.AccessibilityEvent;
//import android.view.accessibility.AccessibilityNodeInfo;
//import android.widget.Toast;
//import java.util.Iterator;
//import java.util.List;
//
//public class HongBaoService extends AccessibilityService
//{
//  static final String QQ_HONGBAO_TEXT_KEY = "[QQ红包]";
//  static final String QQ_PACKAGENAME = "com.tencent.mobileqq";
//  static final String TAG = "HongBao";
//  static final String WECHAT_HONGBAO_TEXT_KEY = "[微信红包]";
//  static final String WECHAT_PACKAGENAME = "com.tencent.mm";
//  public static boolean isWork = false;
//  Handler handler = new Handler();
//  boolean isFromProgram = false;
//
//  private void backAppMain()
//  {
//  }
//
//  @TargetApi(16)
//  private void clickQQHongbao()
//  {
//    AccessibilityNodeInfo localAccessibilityNodeInfo1 = getRootInActiveWindow();
//    if (localAccessibilityNodeInfo1 == null);
//    label142:
//    while (true)
//    {
//      return;
//      List localList1 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("赶紧点击拆开吧");
//      int j;
//      List localList2;
//      if (!localList1.isEmpty())
//      {
//        j = -1 + localList1.size();
//        if (j >= 0);
//      }
//      else
//      {
//        localList2 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("点击拆开");
//        if (localList2.isEmpty())
//          continue;
//      }
//      for (int i = -1 + localList2.size(); ; i--)
//      {
//        if (i < 0)
//          break label142;
//        AccessibilityNodeInfo localAccessibilityNodeInfo2 = ((AccessibilityNodeInfo)localList2.get(i)).getParent();
//        if (localAccessibilityNodeInfo2 != null)
//        {
//          localAccessibilityNodeInfo2.performAction(16);
//          return;
//          AccessibilityNodeInfo localAccessibilityNodeInfo3 = ((AccessibilityNodeInfo)localList1.get(j)).getParent();
//          if (localAccessibilityNodeInfo3 != null)
//          {
//            localAccessibilityNodeInfo3.performAction(16);
//            return;
//          }
//          j--;
//          break;
//        }
//      }
//    }
//  }
//
//  @TargetApi(16)
//  private void clickWXHongBao()
//  {
//    AccessibilityNodeInfo localAccessibilityNodeInfo1 = getRootInActiveWindow();
//    if (localAccessibilityNodeInfo1 == null)
//      Log.w("HongBao", "rootWindow为空");
//    while (true)
//    {
//      return;
//      List localList = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("领取红包");
//      if (localList.isEmpty())
//      {
//        Iterator localIterator = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("[微信红包]").iterator();
//        if (localIterator.hasNext())
//        {
//          AccessibilityNodeInfo localAccessibilityNodeInfo3 = (AccessibilityNodeInfo)localIterator.next();
//          Log.i("HongBao", "-->微信红包:" + localAccessibilityNodeInfo3);
//          localAccessibilityNodeInfo3.performAction(16);
//        }
//      }
//      else
//      {
//        for (int i = -1 + localList.size(); i >= 0; i--)
//        {
//          AccessibilityNodeInfo localAccessibilityNodeInfo2 = ((AccessibilityNodeInfo)localList.get(i)).getParent();
//          Log.i("HongBao", "-->领取红包:" + localAccessibilityNodeInfo2);
//          if (localAccessibilityNodeInfo2 != null)
//          {
//            localAccessibilityNodeInfo2.performAction(16);
//            ((AccessibilityNodeInfo)localList.get(i)).performAction(16);
//            return;
//          }
//        }
//      }
//    }
//  }
//
//  @TargetApi(16)
//  private void getWXHongBao()
//  {
//    AccessibilityNodeInfo localAccessibilityNodeInfo1 = getRootInActiveWindow();
//    if (localAccessibilityNodeInfo1 == null);
//    while (true)
//    {
//      return;
//      List localList = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("拆红包");
//      if (!localList.isEmpty())
//        for (int i = -1 + localList.size(); i >= 0; i--)
//        {
//          AccessibilityNodeInfo localAccessibilityNodeInfo2 = (AccessibilityNodeInfo)localList.get(i);
//          if (localAccessibilityNodeInfo2 != null)
//          {
//            localAccessibilityNodeInfo2.performAction(16);
//            return;
//          }
//        }
//    }
//  }
//
//  private void openHongBao(AccessibilityEvent paramAccessibilityEvent)
//  {
//    String str = paramAccessibilityEvent.getClassName().toString();
//    if ("com.tencent.mobileqq.activity.SplashActivity".equals(str))
//      clickQQHongbao();
//    do
//    {
//      return;
//      if ("cooperation.qwallet.plugin.QWalletPluginProxyActivity".equals(str))
//      {
//        this.isFromProgram = false;
//        SystemClock.sleep(1000L);
//        backAppMain();
//        return;
//      }
//      if ("com.tencent.mm.ui.LauncherUI".equals(str))
//      {
//        clickWXHongBao();
//        return;
//      }
//      if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(str))
//      {
//        getWXHongBao();
//        return;
//      }
//    }
//    while (!"com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(str));
//    this.isFromProgram = false;
//    SystemClock.sleep(1000L);
//    backAppMain();
//  }
//
//  @TargetApi(16)
//  private void openHongBaoAll(AccessibilityEvent paramAccessibilityEvent)
//  {
//    AccessibilityNodeInfo localAccessibilityNodeInfo1 = getRootInActiveWindow();
//    if (localAccessibilityNodeInfo1 == null);
//    label70: label75: String str;
//    label182:
//    do
//    {
//      return;
//      List localList1 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("点击拆开");
//      int k;
//      List localList2;
//      int j;
//      List localList3;
//      if (!localList1.isEmpty())
//      {
//        k = -1 + localList1.size();
//        if (k >= 0);
//      }
//      else
//      {
//        localList2 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("领取红包");
//        if (!localList2.isEmpty())
//        {
//          j = -1 + localList2.size();
//          if (j >= 0)
//            break label182;
//        }
//        localList3 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByText("拆红包");
//        if (localList3.isEmpty());
//      }
//      for (int i = -1 + localList3.size(); ; i--)
//      {
//        if (i < 0);
//        while (true)
//        {
//          str = paramAccessibilityEvent.getClassName().toString();
//          if (!"cooperation.qwallet.plugin.QWalletPluginProxyActivity".equals(str))
//            break label280;
//          this.isFromProgram = false;
//          SystemClock.sleep(1000L);
//          backAppMain();
//          return;
//          AccessibilityNodeInfo localAccessibilityNodeInfo4 = ((AccessibilityNodeInfo)localList1.get(k)).getParent();
//          if (localAccessibilityNodeInfo4 != null)
//          {
//            localAccessibilityNodeInfo4.performAction(16);
//            return;
//          }
//          k--;
//          break;
//          AccessibilityNodeInfo localAccessibilityNodeInfo3 = ((AccessibilityNodeInfo)localList2.get(j)).getParent();
//          Log.i("HongBao", "-->领取红包:" + localAccessibilityNodeInfo3);
//          if (localAccessibilityNodeInfo3 != null)
//          {
//            localAccessibilityNodeInfo3.performAction(16);
//            break label75;
//          }
//          j--;
//          break label70;
//          AccessibilityNodeInfo localAccessibilityNodeInfo2 = (AccessibilityNodeInfo)localList3.get(i);
//          if (localAccessibilityNodeInfo2 == null)
//            break label274;
//          localAccessibilityNodeInfo2.performAction(16);
//        }
//      }
//    }
//    while (!"com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(str));
//    label274: label280: this.isFromProgram = false;
//    SystemClock.sleep(1000L);
//    backAppMain();
//  }
//
//  private void openNotify(AccessibilityEvent paramAccessibilityEvent)
//  {
//    if ((paramAccessibilityEvent.getParcelableData() == null) || (!(paramAccessibilityEvent.getParcelableData() instanceof Notification)))
//      return;
//    PendingIntent localPendingIntent = ((Notification)paramAccessibilityEvent.getParcelableData()).contentIntent;
//    try
//    {
//      this.isFromProgram = true;
//      localPendingIntent.send();
//      return;
//    }
//    catch (CanceledException localCanceledException)
//    {
//      localCanceledException.printStackTrace();
//    }
//  }
//
//  public void onAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
//  {
//    int i = paramAccessibilityEvent.getEventType();
//    Iterator localIterator;
//    if (i == 64)
//    {
//      List localList = paramAccessibilityEvent.getText();
//      if (!localList.isEmpty())
//      {
//        localIterator = localList.iterator();
//        if (localIterator.hasNext())
//          break label44;
//      }
//    }
//    label44: label109:
//    do
//    {
//      do
//      {
//        do
//        {
//          return;
//          String str = String.valueOf((CharSequence)localIterator.next());
//          if ((!str.contains("[QQ红包]")) && (!str.contains("[微信红包]")))
//            break;
//          this.isFromProgram = true;
//          openNotify(paramAccessibilityEvent);
//          return;
//          if (i != 32)
//            break label109;
//        }
//        while (!this.isFromProgram);
//        openHongBaoAll(paramAccessibilityEvent);
//        return;
//        if (i != 2048)
//          break label129;
//      }
//      while (!this.isFromProgram);
//      openHongBaoAll(paramAccessibilityEvent);
//      return;
//    }
//    while ((i != 1) || (!this.isFromProgram));
//    label129: openHongBaoAll(paramAccessibilityEvent);
//  }
//
//  public void onInterrupt()
//  {
//    isWork = false;
//    ((Activity)getApplicationContext()).finish();
//    backAppMain();
//    Toast.makeText(this, "抢红包服务中断，不能抢红包", 0).show();
//  }
//
//  protected void onServiceConnected()
//  {
//    super.onServiceConnected();
//    isWork = true;
//    Toast.makeText(this, "成功连接抢红包服", 1000).show();
//  }
//
//  public boolean onUnbind(Intent paramIntent)
//  {
//    isWork = false;
//    return super.onUnbind(paramIntent);
//  }
//}
//
