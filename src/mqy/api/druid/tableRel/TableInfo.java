package mqy.api.druid.tableRel;
import java.util.*;
/**
 * Created by maoqiyun on 2017/5/8.
 */
public class TableInfo {

    private String name;

    private List<ColumnInfo> columnList = new ArrayList<>();

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

    public class ColumnInfo {

        private TableInfo table;

        private String columnName;

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
}
