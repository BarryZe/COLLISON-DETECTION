import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BVH implements Iterable<Circle>{
    Box boundingBox;

    BVH child1;
    BVH child2;

    Circle containedCircle;

    // todo for students
    public BVH(ArrayList<Circle> circles) {
        boundingBox = buildTightBoundingBox(circles);
        if(circles == null){

        }else if(circles.size()==1){//check the circles contain more than one circle
            containedCircle = circles.get(0);
        }else{
            this.child1 = new BVH(split(circles,boundingBox)[0]);
            this.child2 = new BVH(split(circles,boundingBox)[1]);

        }
    }

    public void draw(Graphics2D g2) {
        this.boundingBox.draw(g2);
        if (this.child1 != null) {
            this.child1.draw(g2);
        }
        if (this.child2 != null) {
            this.child2.draw(g2);
        }
    }

    // todo for students
    public static ArrayList<Circle>[] split(ArrayList<Circle> circles, Box boundingBox) {
        ArrayList<Circle>[] result = new ArrayList[2];
        ArrayList arr1 = new ArrayList(circles.size());
        ArrayList arr2 = new ArrayList(circles.size());
        if(boundingBox.getHeight()> boundingBox.getWidth()){
            for(int i=0;i< circles.size();i++){
                if(circles.get(i).position.y< boundingBox.getMidY()){
                    arr1.add(circles.get(i));
                }else{
                    arr2.add(circles.get(i));
                }
            }
        }else{
            for(int i=0;i< circles.size();i++){
                if(circles.get(i).position.x< boundingBox.getMidX()){
                    arr1.add(circles.get(i));
                }else{
                    arr2.add(circles.get(i));
                }

            }
        }
        result[0] = arr1;
        result[1] = arr2;
        return result;
    }

    // returns the smallest possible box which fully encloses every circle in circles
    public static Box buildTightBoundingBox(ArrayList<Circle> circles) {
        Vector2 bottomLeft = new Vector2(Float.POSITIVE_INFINITY);
        Vector2 topRight = new Vector2(Float.NEGATIVE_INFINITY);

        for (Circle c : circles) {
            bottomLeft = Vector2.min(bottomLeft, c.getBoundingBox().bottomLeft);
            topRight = Vector2.max(topRight, c.getBoundingBox().topRight);
        }

        return new Box(bottomLeft, topRight);
    }

    // METHODS BELOW RELATED TO ITERATOR

    // todo for students
    @Override
    public Iterator<Circle> iterator() {
        return new BVHIterator(this);
    }

    public class BVHIterator implements Iterator<Circle> {

        private ArrayList circleList;
        private int listSize;
        private int currentIndex = 0;

        // todo for students
        public BVHIterator(BVH bvh) {
            circleList = depthFirstRead(bvh);
            listSize = circleList.size();
        }

        // todo for students
        @Override
        public boolean hasNext() {
            return currentIndex < listSize && circleList.get(currentIndex) != null;
        }

        // todo for students
        @Override
        public Circle next() {
            return (Circle)circleList.get(currentIndex++);
        }

        private ArrayList<Circle> depthFirstRead(BVH bvh){
            if(bvh == null) return null;
            ArrayList<Circle> result = new ArrayList<>();
            if(bvh.child1 == null && bvh.child2 == null){
                result.add(bvh.containedCircle);
                return result;
            }
            result.addAll(depthFirstRead(bvh.child1)) ;
            result.addAll(depthFirstRead(bvh.child2));

            return result;
        }
    }
}