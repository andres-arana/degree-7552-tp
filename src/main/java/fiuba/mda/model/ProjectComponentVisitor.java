package fiuba.mda.model;

/**
 * Visitor used to visit {@link ProjectComponent} instances
 */
public interface ProjectComponentVisitor {
	/**
	 * Indicates that the visited component was a {@link ModelPackage}
	 * 
	 * @param modelPackage
	 *            the visited {@link ModelPackage}
	 */
	void visit(final ModelPackage modelPackage);

	/**
	 * Indicates that the visited component was a {@link ModelEntity}
	 * 
	 * @param modelEntity
	 *            the visited {@link ModelEntity}
	 */
	void visit(final ModelEntity modelEntity);

	/**
	 * Indicates that the visited component was a {@link ModelAspect}
	 * 
	 * @param modelAspect
	 *            the visited {@link ModelAspect}
	 */
	void visit(final ModelAspect modelAspect);

	/**
	 * Indicates that the visited component was a {@link BehaviorDiagram}
	 * 
	 * @param behaviorDiagram
	 *            the visited {@link BehaviorDiagram}
	 */
	void visit(BehaviorDiagram behaviorDiagram);
}
