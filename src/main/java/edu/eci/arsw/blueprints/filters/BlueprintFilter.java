package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

public interface BlueprintFilter {

	/**
	 * Filter a blueprint.
	 * @param blueprint The blueprint that is going to be filter.
	 * @return The filtered blueprint.
	 */
	public Blueprint filter(Blueprint blueprint);
	
}
