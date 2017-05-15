package mqy.api.druid;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
/**
 * Created by maoqiyun on 2017/5/8.
 * 整个规则的拼装，比如：
 * hcm_dept_id = ['001','002','003'] and
 * hcm_level_id = ['P2-1','P2-2','P2-3'] or
 * hcm_job_index = 0
 *
 */
public class DataAuthRuleSet {

    private List<DataAuthRelation> dataAuthRelationList = new ArrayList<>(); // 实际上这应该是一个链表，最后一个节点DataAuthRelation的relation应该是null

    public List<DataAuthRelation> getDataAuthRelationList() {
        return dataAuthRelationList;
    }

    public void setDataAuthRelationList(List<DataAuthRelation> dataAuthRelationList) {
        this.dataAuthRelationList = dataAuthRelationList;
    }

    public void addDataAuthRelation(DataAuthRelation dataAuthRelation) {
        this.dataAuthRelationList.add(dataAuthRelation);
    }

    static StringBuilder dataAuthParser(List<DataAuthRelation> dataAuthRelationList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(dataAuthRelationList)) {
            Iterator iterator = dataAuthRelationList.iterator();
            for (DataAuthRelation dataAuthRelation : dataAuthRelationList) {
                if (dataAuthRelation.getRelation() != null) { // 最后一个节点
                    stringBuilder.append(dataAuthRelation.getAtomRule().getColumn()).append(" ");
                    stringBuilder.append(dataAuthRelation.getAtomRule().getOperator()).append(" ");
                    stringBuilder.append(dataAuthRelation.getAtomRule().getValue()).append(" ");
                    if (iterator.hasNext()) {
                        stringBuilder.append(",");
                    }
                }
            }
        }
        return stringBuilder;
    }


}
