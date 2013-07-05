package fiuba.mda.ui.figures;

import fiuba.mda.model.BehaviorRelation;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.model.Representation.Position;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import java.util.List;

/**
 * Figure which displays a state in a behavior diagram
 */
public class BehaviorRelationFigure extends PolylineConnection  {
	private Representation<BehaviorRelation> relation;

	public BehaviorRelationFigure(final Representation<BehaviorRelation> relation, List<BehaviorStateFigure> behaviorStateFigures) {
		this.relation = relation;

        BehaviorState initialState = relation.getEntity().getInitialState();
        if (initialState != null){
            for (BehaviorStateFigure behaviorStateFigure : behaviorStateFigures){
                if (behaviorStateFigure.getState().getEntity().equals(initialState)){
                    ChopboxAnchor sourceAnchor = new ChopboxAnchor(behaviorStateFigure);
                    this.setTargetAnchor(sourceAnchor);
                }
            }

        }

        BehaviorState finalState = relation.getEntity().getFinalState();
        if (finalState != null){
            for (BehaviorStateFigure behaviorStateFigure : behaviorStateFigures){
                if (behaviorStateFigure.getState().getEntity().equals(finalState)){
                    ChopboxAnchor targetAnchor = new ChopboxAnchor(behaviorStateFigure);
                    this.setSourceAnchor(targetAnchor);
                    PolygonDecoration decoration = new PolygonDecoration();
                    PointList decorationPointList = new PointList();
                    decorationPointList.addPoint(0,0);
                    decorationPointList.addPoint(-2,2);
                    decorationPointList.addPoint(-2,0);
                    decorationPointList.addPoint(-2,-2);
                    decoration.setTemplate(decorationPointList);
                    this.setSourceDecoration(decoration);

                }
            }

        }


	}


}
