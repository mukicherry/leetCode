package mqy.api.druid;

/**
 * Created by maoqiyun on 2017/5/8.
 * 连接各个限制条件的关系类
 */
public class DataAuthRelation {

    private String relation; // and,or

    private DataAuthAtomRule atomRule;

    public DataAuthRelation(String relation, DataAuthAtomRule atomRule) {
        this.relation = relation;
        this.atomRule = atomRule;
    }

    public DataAuthRelation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataAuthRelation)) return false;

        DataAuthRelation that = (DataAuthRelation) o;

        if (!relation.equals(that.relation)) return false;
        return atomRule.equals(that.atomRule);
    }

    @Override
    public int hashCode() {
        int result = relation.hashCode();
        result = 31 * result + atomRule.hashCode();
        return result;
    }

    public DataAuthAtomRule getAtomRule() {
        return atomRule;
    }

    public void setAtomRule(DataAuthAtomRule atomRule) {
        this.atomRule = atomRule;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
