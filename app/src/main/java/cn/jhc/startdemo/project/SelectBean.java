package cn.jhc.startdemo.project;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class SelectBean {

    private  String selectType;
    private List<String> chooses;

    public SelectBean(String selectType, List<String> chooses) {
        this.selectType = selectType;
        this.chooses = chooses;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public List<String> getChooses() {
        return chooses;
    }

    public void setChooses(List<String> chooses) {
        this.chooses = chooses;
    }
}
