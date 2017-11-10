package cn.jhc.startdemo.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jhc.startdemo.R;

public class Main2Activity extends AutoLayoutActivity implements TextView.OnEditorActionListener {

    @Bind(R.id.show)
    Button show;
    @Bind(R.id.price_min)
    EditText priceMin;
    @Bind(R.id.price_max)
    EditText priceMax;
    @Bind(R.id.select_type)
    RecyclerView selectType;
    @Bind(R.id.main2_activity)
    DrawerLayout main2Activity;
    @Bind(R.id.result)
    TextView result;

    private List<SelectBean> mList;
    private PopRightListAdapter adapter;
    private InputMethodManager imm;
    private String[] list = {"品牌", "类别", "适用人群", "电压", "最高时速", "优惠类型", "续航", "颜色"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        priceMax.setOnEditorActionListener(this);
        mList = new ArrayList<>();
        for (int j = 0; j < list.length; j++) {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                data.add(list[j] + i);
            }
            mList.add(new SelectBean(list[j], data));
        }
        adapter = new PopRightListAdapter(this, mList);
        selectType.setLayoutManager(new LinearLayoutManager(this));
        selectType.setAdapter(adapter);
    }


    @OnClick(R.id.show)
    public void onViewClicked() {
        main2Activity.openDrawer(Gravity.RIGHT);
    }

    @OnClick({R.id.reset, R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset:
                priceMin.setText("");
                priceMax.setText("");
                adapter.setReset();
                break;
            case R.id.finish:
                //获取选择的条件
                main2Activity.closeDrawer(Gravity.RIGHT);
                StringBuffer buffer = new StringBuffer();
                String price1 = priceMax.getText().toString();
                String price2 = priceMin.getText().toString();
                if (!TextUtils.isEmpty(price1)) {
                    buffer.append("price" + price1 + "~");
                }
                if (!TextUtils.isEmpty(price2)) {
                    buffer.append(price2);
                }
                buffer.append("\n");
                for (String s : adapter.getSelectedType()) {
                    buffer.append(s);
                    buffer.append("\n");
                }
                result.setText(buffer.toString());
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }
}
