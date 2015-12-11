package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.adapter.PanelMenuAdapter;
import com.phonemate.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：1.0版本不开放
 * 创建人：colorful
 * 创建时间：15/11/20 16:45
 * 修改人：colorful
 * 修改时间：15/11/20 16:45
 * 修改备注：
 */
public class DirectorEntity extends PanelMenuEntity{

    public static final String NAME="文件夹";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.DirectorEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_director;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };
    private PanelMenuAdapter mAdapter;
    private List<PanelMenuEntity> mList;
    public DirectorEntity(Context context) {
        super(context);
        mList=new ArrayList<>();
        mAdapter=new PanelMenuAdapter(mList,context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public PanelMenuAdapter getDirectorAdapter() {
        return  mAdapter;
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog= false;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}
