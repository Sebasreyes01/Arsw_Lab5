package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.ArrayList;
import java.util.List;

public class RedundanciesFilter {

    private Blueprint blueprint;
    private List<Point> points;

    public RedundanciesFilter(Blueprint blueprint) {
        this.blueprint = blueprint;
        points = new ArrayList<>();
    }

    public Blueprint filter() {
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
        Object[] ps = blueprintPoints.toArray();
        Blueprint bp = new Blueprint(blueprint.getAuthor(),blueprint.getName(),ps);
    }
}
