/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp=null;
    
    @Autowired
    BlueprintFilter bpf = null;

    /**
     * Adds a new blueprint.
     * @param bp The blueprint that is going to be added.
     * @throws BlueprintPersistenceException
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /**
     * Gets all the existing blueprints.
     * @return All the existing blueprints.
     * @throws BlueprintNotFoundException
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
    	Set<Blueprint> blueprints = new LinkedHashSet<>();
    	for(Blueprint bp : bpp.getAllBlueprints()) {
    		blueprints.add(bpf.filter(bp));
    	}
        return blueprints;
    }

    /**
     *
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException {
        return bpf.filter(bpp.getBlueprint(author,name));
    }

    /**
     *
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
    	Set<Blueprint> blueprints = new LinkedHashSet<>();
    	for(Blueprint bp : bpp.getBlueprintsByAuthor(author)) {
    		blueprints.add(bpf.filter(bp));
    	}
        return blueprints;
    }

}