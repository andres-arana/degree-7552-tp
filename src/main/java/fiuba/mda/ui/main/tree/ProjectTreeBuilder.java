package fiuba.mda.ui.main.tree;

import com.google.inject.Provider;
import fiuba.mda.model.*;
import fiuba.mda.ui.actions.DeleteBehaviorDiagramAction;import fiuba.mda.ui.actions.DeletePackageAction;
import fiuba.mda.ui.actions.EditPackageAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.ui.main.workspace.ControlBuilder;


/**
 * Builder which creates a new project tree
 */
@Singleton
public class ProjectTreeBuilder implements ControlBuilder {
	private final Application model;

	private final ProjectTreeLabelProvider labelProvider;

	private final ProjectTreeContentProvider contentProvider;

	private final PackageSelectedListener onSelectionChanged;

	private final NodeDobleClickListener onDoubleClick;

    private final Provider<ComponentEditorVisitor> editorProvider;

	/**
	 * Creates a new {@link ProjectTreeBuilder} instance
	 *
     * @param model
     *            the model which will be watched by the project tree
     * @param labelProvider
     *            the provider of labels for the project tree
     * @param contentProvider
*            the provider of content for the project tree
     * @param onSelectionChanged
*            the listener in charge of handling the selection of the
*            current package
     * @param onDoubleClick
     * @param editorProvider
     */
	@Inject
	public ProjectTreeBuilder(final Application model,
                              final ProjectTreeLabelProvider labelProvider,
                              final ProjectTreeContentProvider contentProvider,
                              final PackageSelectedListener onSelectionChanged,
                              final NodeDobleClickListener onDoubleClick, Provider<ComponentEditorVisitor> editorProvider) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
		this.onSelectionChanged = onSelectionChanged;
		this.onDoubleClick = onDoubleClick;
        this.editorProvider = editorProvider;
    }

	/**
	 * Creates a new project tree in the given parent
	 * 
	 * @param parent
	 *            the composite to build the new project tree into
	 * @return The built {@link TreeViewer} instance
	 */
	public Control buildInto(Composite parent) {
		final TreeViewer treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.addSelectionChangedListener(onSelectionChanged);
		treeViewer.addDoubleClickListener(onDoubleClick);
        MenuManager menuMgr = new MenuManager();
        final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                // IWorkbench wb = PlatformUI.getWorkbench();
                // IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
                if (treeViewer.getSelection().isEmpty()) {
                    return;
                }

                if (treeViewer.getSelection() instanceof IStructuredSelection) {
                    IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
                    try{
                    AbstractContainerProjectComponent object = (AbstractContainerProjectComponent)selection.getFirstElement();

                    if (object instanceof ModelPackage) {
                        ModelPackage modelPackage = (ModelPackage) object;
                        manager.add(new EditPackageAction(model, modelPackage,editorProvider));
                        if(!modelPackage.equals(model.getCurrentProject().getRootPackage())){
                            manager.add(new DeletePackageAction(model, modelPackage));
                        }

                    }
                    } catch (Exception ex) {
                        AbstractLeafProjectComponent object = (AbstractLeafProjectComponent) selection.getFirstElement();
                        if (object instanceof BehaviorDiagram) {
                            manager.add(new DeleteBehaviorDiagramAction(model, (BehaviorDiagram) object));
                        }
                    }
                }
            }
        });
        menuMgr.setRemoveAllWhenShown(true);
        treeViewer.getControl().setMenu(menu);
        treeViewer.setInput(model);
		treeViewer.expandAll();
		return treeViewer.getControl();
	}
}
