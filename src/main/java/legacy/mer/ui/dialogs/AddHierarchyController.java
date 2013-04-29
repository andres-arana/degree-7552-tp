package legacy.mer.ui.dialogs;

import legacy.mer.modelo.Jerarquia;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * {@link AddComponentController} specialized in selecting / creating
 * {@link Jerarquia} instances to be added
 */
@Singleton
public class AddHierarchyController extends AddComponentController<Jerarquia> {
	/**
	 * The dialog provider to get dialogs from
	 */
	private final Provider<AddHierarchyDialog> dialogProvider;

	/**
	 * Creates a new {@link AddHierarchyController}
	 */
	@Inject
	public AddHierarchyController(
			final Provider<AddHierarchyDialog> dialogProvider) {
		this.dialogProvider = dialogProvider;
	}

	@Override
	protected AddComponentDialog<Jerarquia> getDialog() {
		return dialogProvider.get();
	}

}
