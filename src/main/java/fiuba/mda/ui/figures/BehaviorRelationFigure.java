package fiuba.mda.ui.figures;

import com.google.inject.Injector;
import fiuba.mda.injection.InjectorConfiguration;
import fiuba.mda.model.BehaviorRelation;
import fiuba.mda.model.BehaviorState;
import fiuba.mda.model.Representation;
import fiuba.mda.model.Representation.Position;
import fiuba.mda.ui.actions.EditBehaviorDiagramRelationAction;
import fiuba.mda.ui.actions.NewBehaviorDiagramRelationAction;
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
public class BehaviorRelationFigure extends PolylineConnection  implements MouseListener{
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
                    //POner color?
                    this.setTargetDecoration(decoration);

                }
            }

        }


        String labelString = "Id: " + behaviorRelation.getName();
        labelString = labelString + "\n" + "FA: Indicar FA";
        //TODO - Agregar el nombre de la función que la activa

        if (behaviorRelation.hasBillateralRelation()){
            //Completar informacion bilateral para mostrar
        }

        Label label = new Label(labelString);
        label.setOpaque(true);
        label.setBackgroundColor(ColorConstants.buttonLightest);
        label.setBorder(new LineBorder());
        this.add(label, new MidpointLocator(this, 0));
        addMouseListener(this);


	}


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseDoubleClicked(MouseEvent mouseEvent) {
        Injector injector = InjectorConfiguration.bootstrapInjector();
        EditBehaviorDiagramRelationAction action = injector.getInstance(EditBehaviorDiagramRelationAction.class);
        action.boundTo(relation.getEntity());
        action.run();
    }
}
