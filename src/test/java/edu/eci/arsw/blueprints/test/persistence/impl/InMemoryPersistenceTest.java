/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {


    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){

        }


    }

    @Test
    public void getBlueprintTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        Blueprint gbp = null;
        try {
            ibpp.saveBlueprint(bp);
            gbp = ibpp.getBlueprint("john","thepaint");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(gbp,bp);

    }

    @Test
    public void getAllBlueprintsTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pointsTriangle = new Point[]{new Point(0,0), new Point(5,5), new Point(0,10)};
        Point[] pointsSquare = new Point[]{new Point(0,0), new Point(0,5), new Point(5,5), new Point(5,0)};
        Point[] pointsRectangle = new Point[]{new Point(0,0), new Point(0,5), new Point(10,5), new Point(10,0)};
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        Blueprint blueprintTriangle = new Blueprint("Sebastian","Triangle",pointsTriangle);
        Blueprint blueprintSquare = new Blueprint("Sebastian","Square",pointsSquare);
        Blueprint blueprintRectangle = new Blueprint("Santiago","Rectangle",pointsRectangle);
        Set<Blueprint> blueprints = new LinkedHashSet<>();
        blueprints.add(blueprintTriangle);
        blueprints.add(bp);
        blueprints.add(blueprintRectangle);
        blueprints.add(blueprintSquare);
        Set<Blueprint> gbp = null;
        try {
            ibpp.saveBlueprint(blueprintTriangle);
            ibpp.saveBlueprint(blueprintSquare);
            ibpp.saveBlueprint(blueprintRectangle);
            gbp = ibpp.getAllBlueprints();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(gbp.size(),blueprints.size());
    }

    @Test
    public void getBlueprintsByAuthor() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        Point[] pointsTriangle = new Point[]{new Point(0,0), new Point(5,5), new Point(0,10)};
        Point[] pointsSquare = new Point[]{new Point(0,0), new Point(0,5), new Point(5,5), new Point(5,0)};
        Point[] pointsRectangle = new Point[]{new Point(0,0), new Point(0,5), new Point(10,5), new Point(10,0)};
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        Blueprint blueprintTriangle = new Blueprint("Sebastian","Triangle",pointsTriangle);
        Blueprint blueprintSquare = new Blueprint("Sebastian","Square",pointsSquare);
        Blueprint blueprintRectangle = new Blueprint("Santiago","Rectangle",pointsRectangle);
        Set<Blueprint> blueprints = new LinkedHashSet<>();
        blueprints.add(blueprintTriangle);
        blueprints.add(blueprintSquare);
        Set<Blueprint> gbp = null;
        try {
            ibpp.saveBlueprint(blueprintTriangle);
            ibpp.saveBlueprint(blueprintSquare);
            ibpp.saveBlueprint(blueprintRectangle);
            gbp = ibpp.getBlueprintsByAuthor("Sebastian");
        } catch(Exception e) {
            e.printStackTrace();
        }
        assertEquals(blueprints,gbp);
    }

    @Test
    public void filtersTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pointsTriangle = new Point[]{new Point(0,0), new Point(0,0), new Point(0,10)};
        Blueprint blueprintTriangle = new Blueprint("Sebastian","Triangle",pointsTriangle);
        Point[] p = new Point[]{new Point(0,0), new Point(0,10)};
        Blueprint bp = new Blueprint("Sebastian","Triangle",p);
        Blueprint gbp = null;
        try {
            bps.addNewBlueprint(blueprintTriangle);
            gbp = bps.getBlueprint("Sebastian","Triangle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < p.length;i++) {
            assertEquals(bp.getPoints().get(i).getX(),gbp.getPoints().get(i).getX());
        }

    }



}