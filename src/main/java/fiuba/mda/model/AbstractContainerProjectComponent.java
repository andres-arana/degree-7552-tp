package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;

import fiuba.mda.utilities.SimpleEvent.Observer;

/**
 * Base abstract class useful for implementing {@link ProjectComponent} for
 * non-leaf components
 */
public abstract class AbstractContainerProjectComponent extends
		AbstractProjectComponent {

	private List<ProjectComponent> children;

	private transient Observer<ProjectComponent> onChildrenHierarchyChanged;

	/**
	 * Creates a new @{link AbstractContainerProjectComponent} instance
	 * 
	 * @param name
	 *            the name which represents this component
	 */
	public AbstractContainerProjectComponent(final String name) {
		super(name);
	}

	@Override
	public void init() {
		super.init();
		onChildrenHierarchyChanged = new Observer<ProjectComponent>() {
			@Override
			public void notify(ProjectComponent observable) {
				hierarchyChangedEvent.raise();
			}
		};

		if (children==null) {
			children = new ArrayList<>();
		} else {
			for (ProjectComponent component : children) {
				component.init();
				component.hierarchyChangedEvent().observe(onChildrenHierarchyChanged);
			}
		}
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public List<ProjectComponent> getChildren() {
		return Collections.unmodifiableList(children);
	}

	@Override
	public Optional<ProjectComponent> findChildrenNamed(final String name) {
		for (ProjectComponent component : children) {
			if (component.getName().equals(name)) {
				return Optional.of(component);
			}
		}

		return Optional.absent();
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public void addChild(final ProjectComponent component) {
		component.setParent(this);
		component.hierarchyChangedEvent().observe(onChildrenHierarchyChanged);
		children.add(component);

		hierarchyChangedEvent.raise();
	}

	@Override
	public void removeChild(final ProjectComponent child) {
		children.remove(child);
		child.removeFromHierarchy();
		hierarchyChangedEvent.raise();
	}

	@Override
	public void removeFromHierarchy() {
		super.removeFromHierarchy();
		for (ProjectComponent child : children) {
			child.removeFromHierarchy();
		}
    }
	
	@Override
	public String[] getAccessibleProperties() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (ProjectComponent c : getChildren()) {
			result.addAll(c.getOwnProperties());
		}
		
		if (!isRoot()) {
			for (String s : getParent().getAccessibleProperties()) {
				result.add(s);
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	@Override
	public Collection<? extends String> getOwnProperties() {
		return new ArrayList<>();
	}
}
