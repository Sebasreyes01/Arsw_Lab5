package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedundanciesFilter implements BlueprintFilter{

	private List<Point> points;

    public RedundanciesFilter() {
        points = new ArrayList<>();
    }

    public Blueprint filter(Blueprint blueprint) {
        List<Point> blueprintPoints = blueprint.getPoints();
        for(int i = 0; i < (blueprintPoints.size() - 1);i++) {
            if((blueprintPoints.get(i).getX() != blueprintPoints.get(i+1).getX()) || (blueprintPoints.get(i).getY() != blueprintPoints.get(i+1).getY())) {
                if(i == (blueprintPoints.size()-2)) {
                    points.add(blueprintPoints.get(i));
                    points.add(blueprintPoints.get(i+1));
                } else {
                    points.add(blueprintPoints.get(i));
                }

            }
        }
        Point[] ps = points.toArray(new Point[points.size()]);
        Blueprint bp = new Blueprint(blueprint.getAuthor(),blueprint.getName(),ps);
        return bp;
    }
}
