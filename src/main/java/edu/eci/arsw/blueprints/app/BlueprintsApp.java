package edu.eci.arsw.blueprints.app;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Sebastián
 */
public class BlueprintsApp {

    public static void main( String[] args ) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pointsTriangle = new Point[]{new Point(0,0), new Point(5,5), new Point(0,10)};
        Point[] pointsSquare = new Point[]{new Point(0,0), new Point(0,5), new Point(5,5), new Point(5,0)};
        Point[] pointsRectangle = new Point[]{new Point(0,0), new Point(0,5), new Point(10,5), new Point(10,0)};
        Blueprint blueprintTriangle = new Blueprint("Sebastian","Triangle",pointsTriangle);
        Blueprint blueprintSquare = new Blueprint("Sebastian","Square",pointsSquare);
        Blueprint blueprintRectangle = new Blueprint("Santiago","Rectangle",pointsRectangle);
        try {
            bps.addNewBlueprint(blueprintTriangle);
            bps.addNewBlueprint(blueprintSquare);
            bps.addNewBlueprint(blueprintRectangle);
            System.out.println("All blueprints: " + bps.getAllBlueprints());
            System.out.println("Rectangle Blueprint: " + bps.getBlueprint("Santiago", "Rectangle"));
            System.out.println("Sebastian's blueprints: " + bps.getBlueprintsByAuthor("Sebastian"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
