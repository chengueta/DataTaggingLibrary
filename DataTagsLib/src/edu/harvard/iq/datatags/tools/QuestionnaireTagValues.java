package edu.harvard.iq.datatags.tools;

import edu.harvard.iq.datatags.model.graphs.DecisionGraph;
import edu.harvard.iq.datatags.model.graphs.nodes.AskNode;
import edu.harvard.iq.datatags.model.graphs.nodes.MultiNode;
import edu.harvard.iq.datatags.model.graphs.nodes.CallNode;
import edu.harvard.iq.datatags.model.graphs.nodes.EndNode;
import edu.harvard.iq.datatags.model.graphs.nodes.Node.VoidVisitor;
import edu.harvard.iq.datatags.model.graphs.nodes.RejectNode;
import edu.harvard.iq.datatags.model.graphs.nodes.SetNode;
import edu.harvard.iq.datatags.model.graphs.nodes.TodoNode;
import edu.harvard.iq.datatags.model.values.AggregateValue;
import edu.harvard.iq.datatags.model.values.AtomicValue;
import edu.harvard.iq.datatags.model.values.CompoundValue;
import edu.harvard.iq.datatags.model.values.TagValue;
import edu.harvard.iq.datatags.model.values.ToDoValue;
import edu.harvard.iq.datatags.runtime.exceptions.DataTagsRuntimeException;
import java.util.HashSet;
import java.util.Set;

/**
 * Traverse the questionnaire and gather all used tag values
 * (used in set nodes).
 *
 * @author Naomi
 */
public class QuestionnaireTagValues extends VoidVisitor {
    private final Set<TagValue> usedTagValues = new HashSet<>();
    
    private final TagValue.VoidVisitor tagValueCollector = new TagValue.VoidVisitor() {
        @Override
        protected void visitTodoValueImpl(ToDoValue v) {
            usedTagValues.add(v);
        }

        @Override
        protected void visitAtomicValueImpl(AtomicValue v) {
            usedTagValues.add(v);
        }

        @Override
        protected void visitAggregateValueImpl(AggregateValue v) {
            usedTagValues.addAll( v.getValues() );
        }

        @Override
        protected void visitCompoundValueImpl(CompoundValue v) {
            v.getTypesWithNonNullValues().forEach( t->v.get(t).accept(this) );
        }
    };
    
    public Set<TagValue> gatherInterviewTagValues( DecisionGraph dg ) {
        dg.nodes().forEach( a -> a.accept(this));
        return usedTagValues;
    }

    @Override
    public void visitImpl(AskNode nd) throws DataTagsRuntimeException {
        // do nothing
    }
    
    @Override
    public void visitImpl(MultiNode nd) throws DataTagsRuntimeException {
        // do nothing
    }

    @Override
    public void visitImpl(SetNode nd) throws DataTagsRuntimeException {
        CompoundValue compound = nd.getTags();
        compound.getTypesWithNonNullValues().forEach( t -> {
            compound.get(t).accept(tagValueCollector);
        });
    }

    @Override
    public void visitImpl(RejectNode nd) throws DataTagsRuntimeException {
        // do nothing
    }

    @Override
    public void visitImpl(CallNode nd) throws DataTagsRuntimeException {
        // do nothing
    }

    @Override
    public void visitImpl(TodoNode nd) throws DataTagsRuntimeException {
        // do nothing
    }

    @Override
    public void visitImpl(EndNode nd) throws DataTagsRuntimeException {
        // do nothing
    }
    
}
