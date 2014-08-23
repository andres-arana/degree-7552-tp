package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fiuba.mda.model.Application;
import fiuba.mda.model.Project;
import fiuba.mda.model.ModelPackage;

import fiuba.mda.ui.actions.validators.NameValidatorFactory;
import fiuba.mda.ui.launchers.SimpleDialogLauncher;
import fiuba.mda.ui.utilities.ImageLoader;
import fiuba.mda.utilities.SimpleEvent.Observer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.swt.SWT;
/**
 * {@link Action} implementation which represents the command of creating a new
 * package in the project tree
 */
@Singleton
public class SaveProjectAsAction extends Action {
	private Observer<Application> onProjectOpen = new Observer<Application>() {
		@Override
		public void notify(Application observable) {
			setEnabled(model.hasCurrentProject());
		}
	};

	private final Application model;

	private final SimpleDialogLauncher dialog;

	private final Shell shell;

	private static final String[] extensionProyecto = new String[] { "*.proj" };	


	/**
	 * Creates a new {@link SaveProjectAction} instance
	 * 
	 * @param model
	 *            the model on which this action will create a new package
	 * @param dialog
	 *            the dialog controller used to create the associated dialogs
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public SaveProjectAsAction(final Application model,
			final SimpleDialogLauncher dialog, final ImageLoader imageLoader, final Shell shell) {
		this.model = model;
		this.dialog = dialog;
		this.shell = shell;

		setupPresentation(imageLoader);
		setupEventObservation(model);
	}

	private void setupEventObservation(final Application model) {
		model.projectOpenEvent().observe(this.onProjectOpen);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Guardar proyecto como...");
		setToolTipText("Guardar proyecto a un archivo en disco distinto del actual");
		setEnabled(false);
		setImageDescriptor(imageLoader.descriptorOf("bullet_disk"));
	}

	@Override
	public void run() {
		FileDialog fileDialog = new FileDialog(this.shell, SWT.SAVE);
		Project project;
		String path;

		fileDialog.setFilterExtensions(extensionProyecto);
		path = fileDialog.open();		
		project = model.getCurrentProject();

		try {
			FileOutputStream fileOut = new FileOutputStream(path);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(project);
	        out.close();
	        fileOut.close();

	        model.setCurrentProjectPath(path);
	        shell.setText("MDA IDE - " + path);
	    } catch (Exception e) {
	    	System.out.println("Error exportando proyect: " + e.toString());
	    }
	}

}
