package com.phonemate.global;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/17 22:26
 * 修改人：colorful
 * 修改时间：15/11/17 22:26
 * 修改备注：
 */
public interface GlobalUtils {
    String BROADCAST_RECEIVER_UPDATE_FLOAT_VIEW = "com.phonemate.update.floatview";
    String BROADCAST_RECEIVER_UPDATE_PANEL_VIEW = "com.phonemate.update.panel";
    String BROADCAST_RECEIVER_APP_LOCK_CHANGE = "com.phonemate.app.lock.change";
    //=====start抢红包设置=====
    int AUTO_ANSWER_TYPE_MONEY = 0;
    int AUTO_ANSWER_TYPE_PERSON = 1;
    int AUTO_ANSWER_TYPE_DIY = 2;
    String AUTO_ANSWER_TYPE_DIY_CONTENT = "谢谢老板,恭喜发财";
    //======end =========
}
