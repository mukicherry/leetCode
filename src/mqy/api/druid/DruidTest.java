package mqy.api.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import mqy.api.druid.tableRel.TableColumnRelation;
import mqy.api.druid.tableRel.TableInfo;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.*;

/**
 * Created by maoqiyun on 2017/5/4.
 */
public class DruidTest {

    static String sql = "SELECT COUNT(1)\n" +
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
            "                AND c.hcm_level_id IN ( ? ) ) ";

    public static void main(String[] args) {



        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        System.out.println("getColumns"+":"+visitor.getColumns()+"\n");
        System.out.println("getTables"+":"+visitor.getTables().keySet()+"\n");
        System.out.println("getAliasMap"+":"+visitor.getAliasMap()+"\n");
        System.out.println("getRelationships"+":"+visitor.getRelationships()+"\n");
        System.out.println("getVariants()"+":"+visitor.getVariants()+"\n");

    }

    /**
     *
     * @param rawSql 已经检查过词法和语法的原生sql
     * @param associateRelationList 业务系统的表关联关系，全局唯一
     * @param dataAuthRule 数据权限
     */

    public static String sqlParseAndTransform(String rawSql, List<TableColumnRelation> associateRelationList, DataAuthRule dataAuthRule) {
        if (CollectionUtils.isEmpty(dataAuthRule.getDataAuthRelationList())) { // 没有限制条件，相当于不过interceptor
            return rawSql;
        }
        // 整个DB涉及到限制关系的表
        Set<TableInfo> tb_distinctTableSet = TableColumnRelation.getDistinctTableSet(associateRelationList);

        // 完整的数据权限限制
        DataAuthRule completeDataAuthRule = getRealDataAuthRuleList(associateRelationList,dataAuthRule);

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

        visitor.getVariants();

        // 获得sql中涉及的表
        Set<TableStat.Name> tableSet = rawsql_tableInfoMap.keySet();

        if (CollectionUtils.isNotEmpty(tableSet)) { // 表都解析不到，那就不能怪我咯，返回原生sql
            for (TableStat.Name table : tableSet) {

            }
        }
        else {
            return rawSql;
        }


        return rawSql;
    }

    /**
     * 获取真实的限制
     * @param associateRelationList
     * @param dataAuthRule
     * @return
     */
    static DataAuthRule getRealDataAuthRuleList(List<TableColumnRelation> associateRelationList, DataAuthRule dataAuthRule) {
        DataAuthRule returnRule = new DataAuthRule();
        Set<DataAuthRelation> returnRuleSet = new HashSet<>();
        for (DataAuthRelation relation : dataAuthRule.getDataAuthRelationList()) {
            returnRuleSet.add(relation);
            String ruleTable = relation.getAtomRule().getTable(); // 数据权限表名
            for (TableColumnRelation tableColumnRelation : associateRelationList) {
                String subject1Table = tableColumnRelation.getSubject1Table().getName();
                if (subject1Table.equals(ruleTable)) { // 说明其关联关系表subject2对应的列也应该加上这样的数据权限
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
                if (subject2Table.equals(ruleTable)) {
                    returnRuleSet.add(assembelRelateRule(relation,tableColumnRelation.getSubject2(),tableColumnRelation.getSubject1()));
                }
            }
        }
        returnRule.setDataAuthRelationList((ArrayList)returnRuleSet);
        return returnRule;
    }

    static DataAuthRelation assembelRelateRule(DataAuthRelation relation, TableInfo.ColumnInfo subject, TableInfo.ColumnInfo object) {
        DataAuthRelation newRelation = new DataAuthRelation();
        newRelation.setRelation(relation.getRelation());
        DataAuthAtomRule newAtomRule = new DataAuthAtomRule(object.getTable().getName(),
                object.getColumnName(),
                relation.getAtomRule().getOperator(),
                relation.getAtomRule().getValue());
        newRelation.setAtomRule(newAtomRule);

        return newRelation;
    }

}
