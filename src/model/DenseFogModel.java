package model;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

//perrywang
public class DenseFogModel {
	

	
	
    static int ran1 = 2;
    public static void setDenseFog(Rectangle wall, Path path) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(wall); // Circle is built above
        pathTransition.setPath(path);
        pathTransition.play();
    }

    public static Path renderPath(int prevX, int curX, int prevY, int curY, int d, boolean ds) {
        Path path = new Path();
        int x = prevX + 10 / 2;
        int y = curX + 10 / 2;
        int us = prevY + 25;
        int uy = curY + 25;
        if (d == 1) {
            us = us - 10;
            uy = uy - 10;
//            y = y + 15;
        } else if (d == 2) {
//            if(ds) {
//                uy = uy + 20;
//                us = us + 20;
//            }

//            x = x - 10;
            uy = uy + 10;
            us = us + 10;
        } else if (d == 3) {
            y = y - 8;
            x = x - 8;
//            us = us - 8;
//            uy = uy + 8;
        } else {
//            if(ds) {
//                uy = uy - 20;
//                us = us - 20;
//            }
            y = y + 8;
            x = x + 8;
        }



        path.getElements().add(new MoveTo(x, us));
        path.getElements().add(new LineTo(y, uy));

        return path;
    }

    public static void set(int prevX, int curX, int prevY, int curY, Rectangle wall3,
                           Rectangle wall2,
                           Rectangle wall1,
                           Rectangle wall4) {
//        int ran1 = 4;

        Path path2 = DenseFogModel.renderPath(prevX, curX, prevY, curY, 1, false);
        Path path3 = DenseFogModel.renderPath(prevX, curX, prevY, curY, 2, true);
        Path path4 = DenseFogModel.renderPath(prevX, curX, prevY, curY, 3, false);
        Path path5 = DenseFogModel.renderPath(prevX, curX, prevY, curY, 4, false);


        ran1 = (int) (Math.random() * 2 + 1 );

        System.out.println(ran1);
        if (ran1 == 1) {
            Path path = new Path();
            path.getElements().add(new MoveTo(-100, -100));
            path.getElements().add(new LineTo(-100, -100));
            DenseFogModel.setDenseFog(wall1, path2);

            DenseFogModel.setDenseFog(wall1, path2);
            DenseFogModel.setDenseFog(wall2, path3);
//            DenseFogModel.setDenseFog(wall3, path4);
            DenseFogModel.setDenseFog(wall4, path5);
            DenseFogModel.setDenseFog(wall3, path);
        } else if(ran1 == 2)   {
            Path path = new Path();
            path.getElements().add(new MoveTo(-100, -100));
            path.getElements().add(new LineTo(-100, -100));
            DenseFogModel.setDenseFog(wall1, path2);
            DenseFogModel.setDenseFog(wall2, path3);
            DenseFogModel.setDenseFog(wall3, path4);
//            DenseFogModel.setDenseFog(wall4, path5);
            DenseFogModel.setDenseFog(wall4, path);
        }



    }
}