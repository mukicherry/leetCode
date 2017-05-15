package mqy.api.druid.tableRel;
import com.alibaba.druid.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
/**
 * Created by maoqiyun on 2017/5/8.
 */
public class TableInfo {

    private String name;

    private List<ColumnInfo> columnList = new ArrayList<>();

    public TableInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnInfo> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnInfo> columnList) {
        this.columnList = columnList;
    }

    public void addColumn(ColumnInfo column) {
        this.columnList.add(column);
    }

    /**
     * 获取表的列名列表
     * @return
     */
    public List<String> getColumnNameListWithTableName() {
        List<String> columnNameList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.getColumnList())) {
            for (ColumnInfo columnInfo : this.getColumnList()) {
                columnNameList.add(this.getName() +"."+columnInfo.getColumnName());
            }
        }
        return columnNameList;
    }

    public List<String> getColumnNameList() {
        List<String> columnNameList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.getColumnList())) {
            for (ColumnInfo columnInfo : this.getColumnList()) {
                columnNameList.add(columnInfo.getColumnName());
            }
        }
        return columnNameList;
    }

    public List<String> getColumnNameListWithAliasTable(String aliasTableName) {
        List<String> columnNameList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.getColumnList())) {
            for (ColumnInfo columnInfo : this.getColumnList()) {
                columnNameList.add(aliasTableName +"."+columnInfo.getColumnName());
            }
        }
        return columnNameList;
    }

    /**
     *
     * @param tableName
     * @return
     */
    public StringBuilder getColumnName(String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> columnNameList = getColumnNameList();
        if (StringUtils.isEmpty(tableName)) {
            for (String column : columnNameList) {
                if (!columnNameList.get(columnNameList.size()-1).equals(column)) {
                    stringBuilder.append(column).append(",");
                }
                else {
                    stringBuilder.append(column);
                }
            }
        }
        else {
            for (String column : columnNameList) {
                if (!columnNameList.get(columnNameList.size()-1).equals(column)) {
                    stringBuilder.append(tableName).append(".").append(column).append(",");
                }
                else {
                    stringBuilder.append(tableName).append(".").append(column);
                }
            }
        }

        return stringBuilder;
    }

    public StringBuilder getColumnName() {
        return this.getColumnName(null);
    }

}
