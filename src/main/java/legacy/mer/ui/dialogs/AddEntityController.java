package legacy.mer.ui.dialogs;

import legacy.mer.modelo.Entidad;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * {@link AddComponentController} specialized in selecting / creating
 * {@link Entidad} instances to be added
 */
@Singleton
public class AddEntityController extends AddComponentController<Entidad> {
	/**
	 * The dialog provider to get dialogs from
	 */
	private final Provider<AddEntityDialog> dialogProvider;

	/**
	 * Creates a new {@link AddEntityController}
	 */
	@Inject
	public AddEntityController(final Provider<AddEntityDialog> dialogProvider) {
		this.dialogProvider = dialogProvider;
	}

	@Override
	protected AddComponentDialog<Entidad> getDialog() {
		return dialogProvider.get();
	}

}
