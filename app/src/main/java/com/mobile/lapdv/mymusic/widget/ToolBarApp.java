package com.mobile.lapdv.mymusic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

/**
 * Created by lap on 03/05/2018.
 */

public class ToolBarApp extends ConstraintLayout {

    private Context mContext;
    private OnClickItemToolBar mOnClickItemToolBar;
    private View mView;
    private TextView mTextToolBarCenter, mTextToolBarRight, mTextToolBarLeft;
    private ImageView mIconToolBarLeft, mIconToolBarRight;

    public ToolBarApp(Context context) {
        super(context);
        init(context, null);
    }

    public ToolBarApp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToolBarApp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray mTypedArray = null;
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.toolbar_app, this, true);
        initView();
        try {
            mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBarApp);
            initTitle(mTypedArray);
            initListener();
        } finally {
            mTypedArray.recycle();
        }
    }

    private void initView() {
        mTextToolBarCenter = mView.findViewById(R.id.tv_tool_bar_center);
        mTextToolBarRight = mView.findViewById(R.id.tv_tool_bar_right);
        mTextToolBarLeft = mView.findViewById(R.id.tv_title);
        mIconToolBarLeft = mView.findViewById(R.id.iv_toolbar_left);
        mIconToolBarRight = mView.findViewById(R.id.iv_toolbar_right);
    }

    private void initTitle(TypedArray typedArray) {
        String title = typedArray.getString(R.styleable.ToolBarApp_tbaTextTitle);
        boolean isActive = typedArray.getBoolean(R.styleable.ToolBarApp_tbaTitle, false);
        int size = typedArray.getDimensionPixelSize(R.styleable.ToolBarApp_tbaSizeTitle,
                getResources().getDimensionPixelSize(R.dimen.default_tba_title_text_size));
        int color = typedArray.getColor(R.styleable.ToolBarApp_tbaColorTitle,
                getResources().getColor(R.color.color_md_amber_A100));
        int textStyle = typedArray.getInteger(
                R.styleable.ToolBarApp_tbaTextTitleStyle,
                1);
        mTextToolBarCenter.setTypeface(mTextToolBarCenter.getTypeface(),
                textStyle == 1 ? Typeface.BOLD : (textStyle == 0 ?
                        Typeface.NORMAL : Typeface.ITALIC));
        mTextToolBarCenter.setText(title);
        mTextToolBarCenter.setTextColor(color);
        mTextToolBarCenter.setVisibility(isActive ? VISIBLE : GONE);
    }

    private void initListener() {
        mIconToolBarLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemToolBar != null) {
                    mOnClickItemToolBar.onItemLeft();
                }
            }
        });
        mIconToolBarRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemToolBar != null) {
                    mOnClickItemToolBar.onItemRight();
                }
            }
        });
    }

    public void setTitleApp(int idTitle) {
        mTextToolBarCenter.setVisibility(idTitle == 0 ? GONE : VISIBLE);
        if (idTitle != 0) {
            setTitleApp(mContext.getString(idTitle));
        }
    }

    public void setTitleApp(String title) {
        if (EmptyUtils.isNotEmpty(title)) {
            mTextToolBarCenter.setText(title);
            mTextToolBarCenter.setSelected(true);
        }
    }

    public void setIconToolBarLeft(Drawable drawable) {
        if (EmptyUtils.isNotEmpty(drawable)) {
            mIconToolBarLeft.setImageDrawable(drawable);
        }
    }

    public void setIconToolBarRight(Drawable drawable) {
        if (EmptyUtils.isNotEmpty(drawable)) {
            mIconToolBarRight.setImageDrawable(drawable);
        }
    }

    public void setTextCenterToolbar(int idTextCenter) {
        mTextToolBarCenter.setVisibility(idTextCenter == 0 ? GONE : VISIBLE);
        if (idTextCenter != 0) {
            setTextCenterToolbar(mContext.getString(idTextCenter));
        }
    }

    public void setTextCenterToolbar(String textCenter) {
        mTextToolBarCenter.setVisibility(EmptyUtils.isNotEmpty(textCenter) ? VISIBLE : GONE);
        if (EmptyUtils.isNotEmpty(textCenter)) {
            mTextToolBarCenter.setText(textCenter);
            mTextToolBarCenter.setSelected(true);
        }
    }

    public void setTextRightToolbar(int idTextRightToolbar) {
        mTextToolBarRight.setVisibility(idTextRightToolbar == 0 ? GONE : VISIBLE);
        if (idTextRightToolbar != 0) {
            setTextRightToolbar(mContext.getString(idTextRightToolbar));
        }
    }

    public void setTextRightToolbar(String textRightToolbar) {
        mTextToolBarRight.setVisibility(EmptyUtils.isNotEmpty(textRightToolbar) ? VISIBLE : GONE);
        if (EmptyUtils.isNotEmpty(textRightToolbar)) {
            mTextToolBarRight.setText(textRightToolbar);
            mTextToolBarRight.setSelected(true);
        }
    }

    public void setOnClickItemIconToolBar(OnClickItemToolBar mOnClickItemToolBar) {
        this.mOnClickItemToolBar = mOnClickItemToolBar;
    }

    public interface OnClickItemToolBar {
        void onItemRight();

        void onItemLeft();
    }
}
