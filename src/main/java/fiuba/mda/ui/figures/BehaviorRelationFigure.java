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
        BehaviorRelation behaviorRelation = relation.getEntity();

        BehaviorState initialState = behaviorRelation.getInitialState();
        if (initialState != null){
            for (BehaviorStateFigure behaviorStateFigure : behaviorStateFigures){
                if (behaviorStateFigure.getState().getEntity().equals(initialState)){
                    ChopboxAnchor sourceAnchor = new ChopboxAnchor(behaviorStateFigure);
                    this.setSourceAnchor(sourceAnchor);

                }
            }

        }

        BehaviorState finalState = behaviorRelation.getFinalState();
        if (finalState != null){
            for (BehaviorStateFigure behaviorStateFigure : behaviorStateFigures){
                if (behaviorStateFigure.getState().getEntity().equals(finalState)){
                    ChopboxAnchor targetAnchor = new ChopboxAnchor(behaviorStateFigure);
                    this.setTargetAnchor(targetAnchor);
                    PolygonDecoration decoration = new PolygonDecoration();
                    /*PointList decorationPointList = new PointList();
                    decorationPointList.addPoint(0,0);
                    decorationPointList.addPoint(-2,2);
                    decorationPointList.addPoint(-2,0);
                    decorationPointList.addPoint(-2,-2);
                    decoration.setTemplate(decorationPointList);*/
                    this.setTargetDecoration(decoration);

                }
            }

        }

        /* Adding labels to the connection */
        /*ConnectionEndpointLocator targetEndpointLocator =
                new ConnectionEndpointLocator(this, true);
        targetEndpointLocator.setVDistance(5);
        targetEndpointLocator.setUDistance(100);
        Label targetMultiplicityLabel = new Label();
        this.add(targetMultiplicityLabel, targetEndpointLocator);*/

        Label label = new Label(behaviorRelation.getName());
        label.setOpaque(true);
        label.setBackgroundColor(ColorConstants.buttonLightest);
        label.setBorder(new LineBorder());
        this.add(label, new MidpointLocator(this, 0));
        //conn.setSourceDecoration(new ConnectionLabel());


	}


}
