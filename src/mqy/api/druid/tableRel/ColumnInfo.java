package mqy.api.druid.tableRel;

/**
 * Created by maoqiyun on 2017/5/11.
 */
public class ColumnInfo {

    private TableInfo table ;

    private String columnName;

    public ColumnInfo() {
    }

    public ColumnInfo(TableInfo table, String columnName) {
        this.table = table;
        this.columnName = columnName;
    }

    public TableInfo getTable() {
        return table;
    }

    public void setTable(TableInfo table) {
        this.table = table;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
