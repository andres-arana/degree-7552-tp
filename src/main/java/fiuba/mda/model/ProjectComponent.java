package fiuba.mda.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProjectComponent {
	private String name;
	private ProjectComponent parent;
	private final List<ProjectComponent> children = new ArrayList<>();

	public ProjectComponent(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProjectComponent> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public ProjectComponent getParent() {
		return parent;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	public boolean isRoot() {
		return parent == null;
	}

	public void addComponent(ProjectComponent component) {
		component.parent = this;
		this.children.add(component);
	}
}
