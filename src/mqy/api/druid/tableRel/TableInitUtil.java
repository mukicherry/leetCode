package mqy.api.druid.tableRel;

import mqy.api.druid.DataAuthAtomRule;
import mqy.api.druid.DataAuthRelation;
import mqy.api.druid.DataAuthRuleRelEnum;
import mqy.api.druid.DataAuthRuleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoqiyun on 2017/5/11.
 * for sql:
 * select * from ac_role_user,ac_role,empl_mv
 " +
 "where ac_role_user.role_id = ac_role.id\n" +
 "and ac_role_user.tenant_user_id = empl_mv.oprid
 */
public class TableInitUtil {

    public static TableInfo initMainTableInfo() {
        TableInfo roleUserTable = new TableInfo("ac_role_user");
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"id"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"role_id"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"role_code"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"tenant_code"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"tenant_user_id"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"source"));
        roleUserTable.addColumn(new ColumnInfo(roleUserTable,"status"));

        return roleUserTable;
    }

    public static TableInfo initTb2() {
        TableInfo table_2 = new TableInfo("ac_role");
        table_2.addColumn(new ColumnInfo(table_2,"id"));
        table_2.addColumn(new ColumnInfo(table_2,"tenant_code"));
        table_2.addColumn(new ColumnInfo(table_2,"business_sys_code"));
        table_2.addColumn(new ColumnInfo(table_2,"business_sys_id"));
        table_2.addColumn(new ColumnInfo(table_2,"role_name"));
        table_2.addColumn(new ColumnInfo(table_2,"role_code"));
        table_2.addColumn(new ColumnInfo(table_2,"role_type"));
        table_2.addColumn(new ColumnInfo(table_2,"role_desc"));
        return table_2;
    }

    public static TableInfo initTb3() {
        TableInfo table_3 = new TableInfo("empl_mv");
        table_3.addColumn(new ColumnInfo(table_3,"OPRID"));
        table_3.addColumn(new ColumnInfo(table_3,"NAME"));
        table_3.addColumn(new ColumnInfo(table_3,"TENANT_CODE"));
        return table_3;
    }

    public static List<TableColumnRelation> constructCertainTBRelationSet() {
        List<TableColumnRelation> associateRelationList = new ArrayList<>();

//         表
        TableInfo table_1 = initMainTableInfo();

        TableInfo table_2 = initTb2();

        TableInfo table_3 = initTb3();

        TableColumnRelation relation_role_id = new TableColumnRelation(new ColumnInfo(table_1,"role_id"),new ColumnInfo(table_2,"id"));
        TableColumnRelation relation_user_id = new TableColumnRelation(new ColumnInfo(table_1,"tenant_user_id"),new ColumnInfo(table_3,"OPRID"));
        TableColumnRelation relation_tenant_code = new TableColumnRelation(new ColumnInfo(table_2,"tenant_code"),new ColumnInfo(table_3,"TENANT_CODE"));

        associateRelationList.add(relation_role_id);
        associateRelationList.add(relation_user_id);
        associateRelationList.add(relation_tenant_code);

        return associateRelationList;
    }

    public static DataAuthRuleSet constructDataAuthRuleSet() {
        DataAuthRuleSet dataAuthRuleSet = new DataAuthRuleSet();
        DataAuthAtomRule authAtomRule = new DataAuthAtomRule("ac_role_user","tenant_user_id",
                "="," 'maoqiyun' ");
        DataAuthRelation relation_1 = new DataAuthRelation(DataAuthRuleRelEnum.REL_AND,authAtomRule);
        dataAuthRuleSet.addDataAuthRelation(relation_1);
        return dataAuthRuleSet;
    }

}
