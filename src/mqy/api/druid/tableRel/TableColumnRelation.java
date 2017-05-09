package mqy.api.druid.tableRel;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
/**
 * Created by maoqiyun on 2017/5/8.
 * 表示业务系统表关联关系
 */
public class TableColumnRelation {

    // TB_a.Column_a1 <-> TB_b.Column_b1

    private TableInfo.ColumnInfo subject1;  // TB_a.Column_a1

    private TableInfo.ColumnInfo subject2;  // TB_b.Column_b1

    public TableColumnRelation(TableInfo.ColumnInfo subject1, TableInfo.ColumnInfo subject2) {
        this.subject1 = subject1;
        this.subject2 = subject2;
    }

    public TableInfo.ColumnInfo getSubject1() {
        return subject1;
    }

    public void setSubject1(TableInfo.ColumnInfo subject1) {
        this.subject1 = subject1;
    }

    public TableInfo.ColumnInfo getSubject2() {
        return subject2;
    }

    public void setSubject2(TableInfo.ColumnInfo subject2) {
        this.subject2 = subject2;
    }

    public TableInfo getSubject1Table() {
        return subject1.getTable();
    }

    public TableInfo getSubject2Table() {
        return subject2.getTable();
    }

    /**
     * 获取有关联关系的表
     * @param associateRelationList 业务系统
     * @return
     */
    public static Set<TableInfo> getDistinctTableSet(List<TableColumnRelation> associateRelationList) {
        Set<TableInfo> distinctTableSet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(associateRelationList)) {
            for (TableColumnRelation relation : associateRelationList) {
                distinctTableSet.add(relation.getSubject1Table());
                distinctTableSet.add(relation.getSubject2Table());
            }
        }

        return distinctTableSet;
    }
}
