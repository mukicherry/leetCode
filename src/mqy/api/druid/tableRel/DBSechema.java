package mqy.api.druid.tableRel;
import java.util.*;
/**
 * Created by maoqiyun on 2017/5/11.
 */
public class DBSechema {
    private String name;

    private List<TableInfo> tableList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableInfo> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableInfo> tableList) {
        this.tableList = tableList;
    }
}
