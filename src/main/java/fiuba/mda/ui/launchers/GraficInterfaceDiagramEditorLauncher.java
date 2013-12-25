package fiuba.mda.ui.launchers;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import fiuba.mda.model.BehaviorDiagram;
import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.actions.*;
import fiuba.mda.ui.figures.BehaviorDiagramFigure;
import fiuba.mda.ui.figures.GraficInterfaceDiagramFigure;
import fiuba.mda.ui.main.MainWindow;
import fiuba.mda.ui.main.tree.ComponentImageVisitor;
import fiuba.mda.ui.main.workspace.ControlBuilder;
import fiuba.mda.ui.main.workspace.DiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * {@link fiuba.mda.ui.launchers.Launcher} implementation which allows editing a behavior diagram
 */
@Singleton
public class GraficInterfaceDiagramEditorLauncher extends
        BaseLauncher<GraficInterfaceDiagram> {
    private final MainWindow mainWindow;
    private final ComponentImageVisitor imageVisitor;
    private final Provider<NewTextAction> newTextActionProvider;
    private final Provider<NewButtonAction> newButtonActionProvider;
    private final Provider<NewFieldAction> newFieldActionProvider;
    private final Provider<NewFormAction> newFormActionProvider;

    /**
     * Creates a new @{link BehaviorDiagramLauncher} instance
     *
     * @param mainWindow
     *            the main window on which behavior diagram editors will be
     *            added
     * @param imageVisitor
     *            the image visitor to get the image for the tab
     * @param newTextActionProvider
     * @param newButtonActionProvider
     * @param newFormActionProvider
     */
    @Inject
    public GraficInterfaceDiagramEditorLauncher(
            final MainWindow mainWindow,
            final ComponentImageVisitor imageVisitor,
            final Provider<NewTextAction> newTextActionProvider,
            final Provider<NewButtonAction> newButtonActionProvider,
            final Provider<NewFieldAction> newFieldActionProvider, Provider<NewFormAction> newFormActionProvider) {
        this.mainWindow = mainWindow;
        this.imageVisitor = imageVisitor;
        this.newTextActionProvider = newTextActionProvider;
        this.newButtonActionProvider = newButtonActionProvider;
        this.newFieldActionProvider = newFieldActionProvider;
        this.newFormActionProvider = newFormActionProvider;
    }

    @Override
    protected void doLaunch(final GraficInterfaceDiagram component) {
        final String name = component.getQualifiedName();
        final Optional<Image> optionalImage = imageVisitor.imageFor(component);
        final Image image = optionalImage.isPresent() ? optionalImage.get()
                : null;

        mainWindow.ensureEditor(name, image, new ControlBuilder() {
            @Override
            public Control buildInto(Composite parent) {
                DiagramEditor editor = new DiagramEditor(parent, SWT.NONE);

                editor.addFigure(new GraficInterfaceDiagramFigure(component));

                editor.addAction(newTextActionProvider.get().boundTo(component));
                editor.addAction(newButtonActionProvider.get().boundTo(component));
                editor.addAction(newFieldActionProvider.get().boundTo(component));
                editor.addAction(newFormActionProvider.get().boundTo(component));
                return editor;
            }
        });
    }
}
