package com.phonemate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.CommonUtils;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 关于我们
 * User: RanCQ
 * Date: 2015/9/28
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @ViewInject(id = R.id.layout_page,click = true)
    private LinearLayout layout_page;

    @ViewInject(id = R.id.layout_qq,click = true)
    private LinearLayout layout_qq;

    @ViewInject(id = R.id.tv_verson)
    private TextView tv_verson;

    @ViewInject(id = R.id.tv_url)
    private TextView tv_url;

    @ViewInject(id = R.id.tv_qq)
    private TextView tv_qq;

    private final String URL="http://phonemate.love";
    private final String QQ="171400283";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));

        mTvCentre.setText(R.string.title_about_us);
        mTvLeft.setText(R.string._return);
        mTvRight.setText("");
        mTvRight.setVisibility(View.INVISIBLE);

        tv_verson.setText("V "+ CommonUtils.getVersonName(mActivity));
        tv_qq.setText(QQ);
        tv_url.setText(URL);
    }



    @Override
    public void onClick(View v) {
        if (v == mTvLeft) {
            mActivity.finish();
        }else if(v==layout_page){//官网

        }else if(v==layout_qq){//QQ
            joinQQGroup("N2i-rb8gL3x4YNNiyEGh7hkFkGCjz2tt");
        }
    }

    /****************
     *
     * 发起添加群流程。群号：PhoneMate(手机伴侣)(171400283) 的 key 为： N2i-rb8gL3x4YNNiyEGh7hkFkGCjz2tt
     * 调用 joinQQGroup(N2i-rb8gL3x4YNNiyEGh7hkFkGCjz2tt) 即可发起手Q客户端申请加群 PhoneMate(手机伴侣)(171400283)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }


}
