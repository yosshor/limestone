package org.tglanz.limestone.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalProject;
import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.ProjectLimeRel;

public class LogicalProjectRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalProjectRule();

    private LogicalProjectRule() {
        super(LogicalProject.class, Convention.NONE, LimeRel.Convention, "LogicalProjectConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalProject source = (LogicalProject)rel;

        final RelNode input = convert(
            source.getInput(),
            source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new ProjectLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                input,
                source.getProjects(),
                source.getRowType());
    }
}
