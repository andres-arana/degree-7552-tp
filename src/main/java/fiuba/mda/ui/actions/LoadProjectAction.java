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

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * {@link Action} implementation which represents the command of creating a new
 * package in the project tree
 */
@Singleton
public class LoadProjectAction extends Action {
	private final Application model;

	private final SimpleDialogLauncher dialog;


	/**
	 * Creates a new {@link LoadProjectAction} instance
	 * 
	 * @param model
	 *            the model on which this action will create a new package
	 * @param dialog
	 *            the dialog controller used to create the associated dialogs
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public LoadProjectAction(final Application model,
			final SimpleDialogLauncher dialog, final ImageLoader imageLoader) {
		this.model = model;
		this.dialog = dialog;

		setupPresentation(imageLoader);
	}

	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Cargar proyecto");
		setToolTipText("Cargar proyecto desde un archivo en disco previamente guardado");
		setEnabled(true);
		setImageDescriptor(imageLoader.descriptorOf("folder_page"));
	}

	@Override
	public void run() {
		try {
		    FileInputStream fileIn = new FileInputStream("project.proj");
		    ObjectInputStream in = new ObjectInputStream(fileIn);
		    Project existingProject = (Project) in.readObject();
		    existingProject.init();
		    in.close();
		    fileIn.close();

			model.openProject(existingProject);
	    } catch (Exception e) {
	    	System.out.println("Error cargando proyect: " + e.toString());
	    	e.printStackTrace();
	    }
	}

}
