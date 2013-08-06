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
import org.eclipse.swt.graphics.Color;

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
                    this.setTargetDecoration(decoration);

                }
            }

        }




        String labelString = "Id: " + behaviorRelation.getName();
        labelString = labelString + "\n" + "FA: Indicar FA";
        //TODO - Agregar el nombre de la función que la activa


        Label label = new Label(labelString);
        label.setOpaque(true);
        label.setBackgroundColor(ColorConstants.buttonLightest);
        label.setForegroundColor(ColorConstants.black);
        label.setBorder(new LineBorder());

        EndPointFigureLocator conecLocator = new EndPointFigureLocator(this,ConnectionLocator.TARGET,label);

        this.add(label,conecLocator);

        if (behaviorRelation.hasBillateralRelation()){

            BehaviorRelation bilateralRelation = behaviorRelation.getBilateralRelation();

            String labelString2 = "Id: " + bilateralRelation.getName();
            labelString2 = labelString2 + "\n" + "FA: Indicar FA";
            //TODO - Agregar el nombre de la función que la activa

            Label label2 = new Label(labelString2);
            label2.setOpaque(true);
            label2.setBackgroundColor(ColorConstants.buttonLightest);
            label2.setForegroundColor(ColorConstants.black);
            label2.setBorder(new LineBorder());

            EndPointFigureLocator conecLocator2 = new EndPointFigureLocator(this,ConnectionLocator.SOURCE,label);

            this.add(label2,conecLocator2);

        }

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

    private static class EndPointFigureLocator extends ConnectionLocator {

        private enum POSITION {
            NORTH, EAST, SOUTH, WEST;
        }

        private IFigure figure;

        /**
         * Constructor
         *
         * @param connection The {@link Connection} this locator should be used for.
         * @param align The alignment of the locator. Only if
         *          {@link ConnectionLocator#SOURCE} and
         *          {@link ConnectionLocator#TARGET} will change the behavior. If
         *          {@link ConnectionLocator#MIDDLE} is passed, the default behavior
         *          of {@link ConnectionLocator} is used.
         * @param figure The {@link IFigure} that should be placed at the Endpoint of
         *          the connection.
         */
        public EndPointFigureLocator(Connection connection, int align, IFigure figure) {
            super(connection, align);
            this.figure = figure;
        }

        @Override
        protected Point getLocation(PointList points) {
            // size of figure
            Dimension prefsLabel = figure.getPreferredSize();
            int lHeight = prefsLabel.height;
            int lWidth = prefsLabel.width;

            Connection con = getConnection();

            switch (getAlignment()) {
                case SOURCE:
                    ConnectionAnchor sAnchor = con.getSourceAnchor();
                    IFigure fig = sAnchor.getOwner();
                    if (fig == null) {
                        return super.getLocation(points);
                    }
                    Rectangle bounds = fig.getBounds();

                    Point point = points.getPoint(Point.SINGLETON, 0);
                    switch (computePosition(bounds, point)) {
                        case EAST:
                            point = new Point(point.x + lWidth / 2 - 4 + 18, point.y);
                            break;
                        case NORTH:
                            point = new Point(point.x, point.y - lHeight / 2 - 12);
                            break;
                        case SOUTH:
                            point = new Point(point.x, point.y + 2 + lHeight / 2 + 10);
                            break;
                        case WEST:
                            point = new Point(point.x - lWidth / 2 + 2 - 16, point.y);
                            break;
                    }
                    return point;
                case TARGET:
                    ConnectionAnchor tAnchor = con.getTargetAnchor();
                    fig = tAnchor.getOwner();
                    if (fig == null) {
                        return super.getLocation(points);
                    }
                    bounds = fig.getBounds();

                    point = points.getPoint(Point.SINGLETON, points.size() - 1);
                    switch (computePosition(bounds, point)) {
                        case EAST:
                            point = new Point(point.x + lWidth / 2 - 4 + 18, point.y);
                            break;
                        case NORTH:
                            point = new Point(point.x, point.y - lHeight / 2 - 12);
                            break;
                        case SOUTH:
                            point = new Point(point.x, point.y + 2 + lHeight /2 + 10);
                            break;
                        case WEST:
                            point = new Point(point.x - lWidth / 2 + 2 - 16, point.y);
                            break;
                    }
                    return point;
                case MIDDLE:
                    if (points.size() % 2 == 0) {
                        int i = points.size() / 2;
                        int j = i - 1;
                        Point p1 = points.getPoint(j);
                        Point p2 = points.getPoint(i);
                        Dimension d = p2.getDifference(p1);
                        return Point.SINGLETON.setLocation(p1.x + d.width / 2, p1.y + d.height / 2);
                    }
                    int i = (points.size() - 1) / 2;
                    return points.getPoint(Point.SINGLETON, i);
                default:
                    return new Point();
            }
        }

        private POSITION computePosition(Rectangle figBounds, Point location) {
            int figX = figBounds.x;
            int figY = figBounds.y;
            int figH = figBounds.height;
            int figW = figBounds.width;

            int locX = location.x;
            int locY = location.y;

            if (locX >= figX && locY <= figY) {
                return POSITION.NORTH;
            }
            if (locX >= figX && locY >= figY + figH) {
                return POSITION.SOUTH;
            }
            if (locX <= figX && locY >= figY) {
                return POSITION.WEST;
            }
            if (locX >= figX + figW && locY >= figY) {
                return POSITION.EAST;
            }

            // special case, this is the top left corner
            if (locX < figX && locY < figY) {
                return POSITION.NORTH;
            }

            return null;
        }
    }
}
