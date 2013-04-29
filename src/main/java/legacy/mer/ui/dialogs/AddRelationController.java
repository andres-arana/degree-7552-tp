package legacy.mer.ui.dialogs;

import legacy.mer.modelo.Relacion;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * {@link AddComponentController} specialized in selecting / creating
 * {@link Relacion} instances to be added
 */
@Singleton
public class AddRelationController extends AddComponentController<Relacion> {
	/**
	 * The dialog provider to get dialogs from
	 */
	private final Provider<AddRelationDialog> dialogProvider;

	/**
	 * Creates a new {@link AddRelationController}
	 */
	@Inject
	public AddRelationController(
			final Provider<AddRelationDialog> dialogProvider) {
		this.dialogProvider = dialogProvider;
	}

	@Override
	protected AddComponentDialog<Relacion> getDialog() {
		return dialogProvider.get();
	}

}
