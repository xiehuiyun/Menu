package cn.jhc.startdemo.project;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jhc.startdemo.R;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class PopRightListAdapter extends RecyclerView.Adapter<PopRightListAdapter.MyHolder> {

    private List<SelectBean> mList;
    private Context context;
    private List<String> selectedType;
    private List<MyHolder> myHolders;
    private boolean isReset = false;

    public PopRightListAdapter(Context context, List<SelectBean> list) {
        this.context = context;
        mList = list;
        selectedType = new ArrayList<>();
        myHolders = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.pop_right_recycler_item, parent, false);
        MyHolder myHolder = new MyHolder(root);
        myHolders.add(myHolder);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final SelectBean bean = mList.get(position);
        if (isReset) {
            holder.selectTypeItem.onChanged();
        } else {
            holder.selectTypeName.setText(bean.getSelectType());
            holder.all.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //pop展示所有的选项
                    Log.d("message", "all 展示所有");
                    if (holder.selectTypeItem.getMaxNumber()==-1){
                        holder.selectTypeItem.setMaxNumber(6);
                    }else {
                        holder.selectTypeItem.setMaxNumber(-1);
                    }
                   holder.selectTypeItem.onChanged();
                }
            });

            holder.selectTypeItem.setAdapter(new TagAdapter(bean.getChooses()) {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.tag_item_layout, parent, false);
                    TextView text = view.findViewById(R.id.tag_txt);
                    text.setText(bean.getChooses().get(position));
                    return view;
                }
            });

            holder.selectTypeItem.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    for (Integer pos : selectPosSet) {
                        selectedType.add(bean.getChooses().get(pos));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

//************************************************************************************************************************************

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView selectTypeName;
        TagFlowLayout selectTypeItem;
        EditText all;

        public MyHolder(View itemView) {
            super(itemView);
            selectTypeItem = itemView.findViewById(R.id.select_type_item);
            selectTypeName = itemView.findViewById(R.id.select_type_name);
            all = itemView.findViewById(R.id.select_type_all);
        }
    }

    //******************************************************************************************************************************************
    public List<String> getSelectedType() {
        selectedType.clear();
        TagAdapter tag;
        for (MyHolder myHolder :myHolders){
            tag = myHolder.selectTypeItem.getAdapter();
            HashSet<Integer> list =  tag.getPreCheckedList();
            for (int i : list){
                Log.d("Select_Type", (String) tag.getItem(i));
                selectedType.add((String) tag.getItem(i));
            }
        }
        return selectedType;
    }

    public void setReset() {
        for (MyHolder holder :myHolders){
            holder.selectTypeItem.getAdapter().setSelectedList(new HashSet<Integer>());
        }
    }
}
