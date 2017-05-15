package mqy.api.druid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import mqy.api.druid.tableRel.ColumnInfo;
import mqy.api.druid.tableRel.TableColumnRelation;
import mqy.api.druid.tableRel.TableInfo;
import mqy.api.druid.tableRel.TableInitUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by maoqiyun on 2017/5/4.
 */
public class DruidTest {
//    static String sql = "select ac_role_user.role_id,ac_role_user.tenant_user_id,ac_role_user.role_code from ac_role_user,ac_role,empl_mv \n" +
    static String sql = "select * from ac_role_user,ac_role,empl_mv\n" +
            "where ac_role_user.role_id = ac_role.id\n" +
            "and ac_role_user.tenant_user_id = empl_mv.oprid";
    static String sql1 = "SELECT COUNT(1)\n" +
            "FROM hcm_emp_dt_action t\n" +
            "WHERE t.hcm_tenant_id = ?\n" +
            "        AND t.hcm_emp_action_id IN ( ? , ? , ? , ? , ? , ? , ? , ? , ? )\n" +
            "        AND t.hcm_job_date >= ?\n" +
            "        AND t.hcm_job_date <= ?\n" +
            "        AND t.hcm_bu_id IN ( ? )\n" +
            "        AND EXISTS \n" +
            "    (SELECT 1\n" +
            "    FROM hcm_emp_dept_trace c\n" +
            "    WHERE c.hcm_tenant_id = t.hcm_tenant_id\n" +
            "            AND c.hcm_emp_code = t.hcm_emp_code\n" +
            "            AND c.hcm_job_index = t.hcm_job_index\n" +
            "            AND CONCAT(DATE_FORMAT(c.hcm_job_date, '%Y%m%d'), c.hcm_job_seq) = \n" +
            "        (SELECT MAX(CONCAT(DATE_FORMAT(c2.hcm_job_date,\n" +
            "         '%Y%m%d'), c2.hcm_job_seq)) AS dtseq\n" +
            "        FROM hcm_emp_dept_trace c2\n" +
            "        WHERE c2.hcm_tenant_id = t.hcm_tenant_id\n" +
            "                AND c2.hcm_emp_code = t.hcm_emp_code\n" +
            "                AND c2.hcm_job_index = t.hcm_job_index\n" +
            "                AND c2.hcm_job_date <= t.hcm_job_date)\n" +
            "                AND c.hcm_dept_id IN ( ? , ? , ? , ? , ? , ? ) )\n" +
            "            AND EXISTS \n" +
            "    (SELECT 1\n" +
            "    FROM hcm_emp_level_trace c\n" +
            "    WHERE c.hcm_tenant_id = t.hcm_tenant_id\n" +
            "            AND c.hcm_emp_code = t.hcm_emp_code\n" +
            "            AND c.hcm_job_index = t.hcm_job_index\n" +
            "            AND CONCAT(DATE_FORMAT(c.hcm_job_date, '%Y%m%d'), c.hcm_job_seq) = \n" +
            "        (SELECT MAX(CONCAT(DATE_FORMAT(c2.hcm_job_date,\n" +
            "         '%Y%m%d'), c2.hcm_job_seq)) AS dtseq\n" +
            "        FROM hcm_emp_level_trace c2\n" +
            "        WHERE c2.hcm_tenant_id = t.hcm_tenant_id\n" +
            "                AND c2.hcm_emp_code = t.hcm_emp_code\n" +
            "                AND c2.hcm_job_index = t.hcm_job_index\n" +
            "                AND c2.hcm_job_date <= t.hcm_job_date)\n" +
            "                AND c.hcm_level_id IN ( ? ) ) ;" +
            "select * from dept_info where column_a = 'a' ";

    public static void main(String[] args) {

        String dbType = JdbcConstants.MYSQL;

        String result = SQLUtils.format(sql, dbType);
//        System.out.println(result); // 缺省大写格式

        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor statVisitor = new MySqlSchemaStatVisitor();
        statement.accept(statVisitor);

        System.out.println("getColumns"+":"+statVisitor.getColumns()+"\n");
        System.out.println("getTables"+":"+statVisitor.getTables().keySet()+"\n");
        System.out.println("getAliasMap"+":"+statVisitor.getAliasMap()+"\n");
        System.out.println("getRelationships"+":"+statVisitor.getRelationships()+"\n");
        System.out.println("getVariants()"+":"+statVisitor.getVariants()+"\n");

//        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
//        System.out.println("size is:" + stmtList.size());
//        for (int i = 0; i < stmtList.size(); i++) {
//
//            SQLStatement stmt = stmtList.get(i);
//            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//            stmt.accept(visitor);
//
//            //获取表名称
//            System.out.println("Tables : " + visitor.getCurrentTable());
//            //获取操作方法名称,依赖于表名称
//            System.out.println("Manipulation : " + visitor.getTables());
//            //获取字段名称
//            System.out.println("fields : " + visitor.getColumns());
//
//        }
//        List<TableColumnRelation> associateRelationList = new ArrayList<>();

        String process_sql = sqlParseAndTransform(sql,TableInitUtil.constructCertainTBRelationSet(),TableInitUtil.constructDataAuthRuleSet());
        System.out.println("看这里：处理后的sql"+":"+process_sql+"\n");
    }

    /**
     *
     * @param rawSql 已经检查过词法和语法的原生sql
     * @param associateRelationList 业务系统的表关联关系，全局唯一
     * @param dataAuthRuleSet 数据权限
     */

    public static String sqlParseAndTransform(String rawSql, List<TableColumnRelation> associateRelationList, DataAuthRuleSet dataAuthRuleSet) {
        StringBuilder stringBuilder = new StringBuilder();

        if (CollectionUtils.isEmpty(dataAuthRuleSet.getDataAuthRelationList())) { // 没有限制条件，相当于不过interceptor
            return stringBuilder.toString();
        }

        // 1.完整的数据权限限制大集合
        DataAuthRuleSet completeDataAuthRuleSet = getCompleteDataAuthRuleList(associateRelationList, dataAuthRuleSet);
        // 2.整个DB涉及到限制关系的表
        Set<TableInfo> tb_distinctTableSet = TableColumnRelation.getDistinctTableSet(associateRelationList);

        /***** rawSql 处理 *****/

        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(rawSql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        Map<TableStat.Name, TableStat> rawsql_tableInfoMap =  visitor.getTables(); // sql中牵涉的表

        Set<TableStat.Column> rawsql_columnSet = visitor.getColumns(); // sql中牵涉的列

        Set<TableStat.Relationship> rawsql_relationshipSet = visitor.getRelationships(); // sql中牵涉的关联关系

        // 获得sql中涉及的表
        Set<TableStat.Name> rawsql_tableSet = rawsql_tableInfoMap.keySet();
        // todo 找主表
        if (CollectionUtils.isNotEmpty(rawsql_tableSet) && rawsql_tableSet.size() > 1) { // 无法解析表 或者 单表时 返回原生sql
            TableInfo mainTable  = TableInitUtil.initMainTableInfo();
            stringBuilder.append(" select ").append(mainTable.getColumnName(SqlAppendEnum.MAINTABLE)); // 拼接sql：select <rawsql.columns List>
            List<String> columnList = mainTable.getColumnNameList();
            stringBuilder.append(" from (").append(rawSql).append(") ").append(SqlAppendEnum.MAINTABLE).append(" "); // 拼接sql：from <left_table:rawsql>
            stringBuilder.append(" inner join ("); // 拼接sql：<join_type>
            stringBuilder.append(" select ").append(mainTable.getColumnName(mainTable.getName())); // 拼接sql：<right_table:[关联条件]和[数据权限]限制>
            stringBuilder.append(" from ").append(mainTable.getName()).append(" ");

            for (TableStat.Name table : rawsql_tableSet) { // 拼接 [关联条件]
                String tableName = table.getName();
                String left_table = mainTable.getName();
                String right_table = tableName;
                if (!tableName.equals(mainTable.getName())) { // 非主表才作处理
                    stringBuilder.append(" inner join ");
                    stringBuilder.append(tableName).append(" on ");
                    for (TableColumnRelation relation : associateRelationList) {
                        if (left_table.equals(relation.getSubject1Table().getName()) && right_table.equals(relation.getSubject2Table().getName())
                                || left_table.equals(relation.getSubject2Table().getName()) && right_table.equals(relation.getSubject1Table().getName())) {
                            stringBuilder.append(relation.getSubject1Table().getName()).append(".").append(relation.getSubject1().getColumnName()).append(" = ")
                                    .append(relation.getSubject2Table().getName()).append(".").append(relation.getSubject2().getColumnName()).append("\n");
                        }
                    }
                }

            }
            for (DataAuthRelation dataAuthRelation : completeDataAuthRuleSet.getDataAuthRelationList()) { // 拼接 [数据权限]
                stringBuilder.append(dataAuthRelation.getRelation()).append(" ").
                        append(dataAuthRelation.getAtomRule().getTable()).append(".").
                        append(dataAuthRelation.getAtomRule().getColumn()).append(" ").
                        append(dataAuthRelation.getAtomRule().getOperator()).append(" ").
                        append(dataAuthRelation.getAtomRule().getValue()).append("\n");
            }
            stringBuilder.append(") ").append(SqlAppendEnum.AUTHRULE);
            stringBuilder.append(" on "); // 拼接sql：<join_condition>
            int i = 0;
            for (TableStat.Relationship rawsql_relation : rawsql_relationshipSet) {
                StringBuilder relationStrBuilder = new StringBuilder();
                relationStrBuilder.append(SqlAppendEnum.MAINTABLE).append(".").append(rawsql_relation.getLeft().getName()).append(" ").append(rawsql_relation.getOperator()).append(" ")
                        .append(SqlAppendEnum.AUTHRULE).append(".").append(rawsql_relation.getLeft().getName()).append("\n");
                if (i != 0) {
                    stringBuilder.append(" AND ").append(relationStrBuilder);
                }
                else {
                    stringBuilder.append(relationStrBuilder);
                }
                i ++ ;
            }
        }
        else {
            return stringBuilder.toString();
        }


        return stringBuilder.toString();
    }

    /**
     * 获取完整的限制
     * @param associateRelationList
     * @param dataAuthRuleSet
     * @return
     */
    static DataAuthRuleSet getCompleteDataAuthRuleList(List<TableColumnRelation> associateRelationList, DataAuthRuleSet dataAuthRuleSet) {
        DataAuthRuleSet returnRule = new DataAuthRuleSet();
        Set<DataAuthRelation> returnRuleSet = new HashSet<>();
        for (DataAuthRelation relation : dataAuthRuleSet.getDataAuthRelationList()) {
            returnRuleSet.add(relation);
            String ruleTable = relation.getAtomRule().getTable(); // 数据权限表名
            for (TableColumnRelation tableColumnRelation : associateRelationList) {
                String subject1Table = tableColumnRelation.getSubject1Table().getName();
                if (subject1Table.equals(ruleTable) && tableColumnRelation.getSubject1().getColumnName().equals(relation.getAtomRule().getColumn())) { // 说明其关联关系表subject2对应的列也应该加上这样的数据权限
                    DataAuthRelation newRelation = new DataAuthRelation();
                    newRelation.setRelation(relation.getRelation());
                    DataAuthAtomRule newAtomRule = new DataAuthAtomRule(tableColumnRelation.getSubject2Table().getName(),
                            tableColumnRelation.getSubject2().getColumnName(),
                            relation.getAtomRule().getOperator(),
                            relation.getAtomRule().getValue());

                    newRelation.setAtomRule(newAtomRule);
                    returnRuleSet.add(newRelation);
                }
                String subject2Table = tableColumnRelation.getSubject2Table().getName();
                if (subject2Table.equals(ruleTable) && tableColumnRelation.getSubject2().getColumnName().equals(relation.getAtomRule().getColumn())) {
                    returnRuleSet.add(assembelRelateRule(relation,tableColumnRelation.getSubject2(),tableColumnRelation.getSubject1()));
                }
            }
        }
        returnRule.setDataAuthRelationList(new ArrayList(returnRuleSet));
        return returnRule;
    }

    static DataAuthRelation assembelRelateRule(DataAuthRelation relation, ColumnInfo subject, ColumnInfo object) {
        DataAuthRelation newRelation = new DataAuthRelation();
        newRelation.setRelation(relation.getRelation());
        DataAuthAtomRule newAtomRule = new DataAuthAtomRule(object.getTable().getName(),
                object.getColumnName(),
                relation.getAtomRule().getOperator(),
                relation.getAtomRule().getValue());
        newRelation.setAtomRule(newAtomRule);

        return newRelation;
    }

    /**
     * 构建和初始化库表的表关联关系，算法成熟后需要放到表里
     * @return
     */
    static List<TableColumnRelation> constructCertainTBRelationSet() {
        List<TableColumnRelation> associateRelationList = new ArrayList<>();

//         表
        TableInfo table_1 = new TableInfo("hcm_emp_dt_action");
        table_1.addColumn(new ColumnInfo(table_1,"hcm_job_index"));
        table_1.addColumn(new ColumnInfo(table_1,"hcm_emp_code"));
        table_1.addColumn(new ColumnInfo(table_1,"hcm_job_date"));
        table_1.addColumn(new ColumnInfo(table_1,"hcm_tenant_id"));

        TableInfo table_2 = new TableInfo("hcm_emp_dept_trace");
        table_1.addColumn(new ColumnInfo(table_2,"hcm_job_index"));
        table_1.addColumn(new ColumnInfo(table_2,"hcm_emp_code"));
        table_1.addColumn(new ColumnInfo(table_2,"hcm_job_date"));
        table_1.addColumn(new ColumnInfo(table_2,"hcm_tenant_id"));
        table_1.addColumn(new ColumnInfo(table_2,"hcm_dept_id"));
        table_1.addColumn(new ColumnInfo(table_2,"hcm_level_id"));

        TableColumnRelation relation_1 = new TableColumnRelation(new ColumnInfo(table_1,"hcm_job_index"),new ColumnInfo(table_2,"hcm_job_index"));
        TableColumnRelation relation_2 = new TableColumnRelation(new ColumnInfo(table_1,"hcm_emp_code"),new ColumnInfo(table_2,"hcm_emp_code"));
        TableColumnRelation relation_3 = new TableColumnRelation(new ColumnInfo(table_1,"hcm_job_date"),new ColumnInfo(table_2,"hcm_job_date"));
        TableColumnRelation relation_4 = new TableColumnRelation(new ColumnInfo(table_1,"hcm_tenant_id"),new ColumnInfo(table_2,"hcm_tenant_id"));

        associateRelationList.add(relation_1);
        associateRelationList.add(relation_2);
        associateRelationList.add(relation_3);
        associateRelationList.add(relation_4);


        return associateRelationList;
    }

    /**
     * 构建数据权限限制集合
     * @return
     */
    static DataAuthRuleSet constructDataAuthRuleSet() {
        DataAuthRuleSet dataAuthRuleSet = new DataAuthRuleSet();
        DataAuthAtomRule authAtomRule = new DataAuthAtomRule("hcm_emp_dept_trace","hcm_dept_id",
                "in"," '2000001','2000002','2000003','2000004','2000005','2000006','2000007' ");
        DataAuthRelation relation_1 = new DataAuthRelation(DataAuthRuleRelEnum.REL_AND,authAtomRule);
        dataAuthRuleSet.addDataAuthRelation(relation_1);
        return dataAuthRuleSet;
    }

    /**
     * 获取主表的列名列表，可以从DB中获取，但是最好的办法是从DB调接口获取，用DB的方式容易有坑
     * @param mainTable
     * @return
     */
    static List<String> getMainTableColumns(TableInfo mainTable) {
        return mainTable.getColumnNameList();
    }




}
