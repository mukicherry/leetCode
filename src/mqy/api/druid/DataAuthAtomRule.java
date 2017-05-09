package mqy.api.druid;

/**
 * Created by maoqiyun on 2017/5/8.
 */
public class DataAuthAtomRule {

    private String table;

    private String column;

    private String operator;

    private String value;

    public DataAuthAtomRule(String table, String column, String operator, String value) {
        this.table = table;
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataAuthAtomRule)) return false;

        DataAuthAtomRule that = (DataAuthAtomRule) o;

        if (!table.equals(that.table)) return false;
        if (!column.equals(that.column)) return false;
        if (!operator.equals(that.operator)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + column.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
