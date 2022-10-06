import java.util.ArrayList;
import java.util.HashSet;

public class ContactFinder {
    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n^2) where n is the number of circles.
    public static HashSet<ContactResult> getContactsNaive(ArrayList<Circle> circles) {
        HashSet<ContactResult> results = new HashSet();
        for(int i=0;i< circles.size();i++){
            for(int j=0;j<circles.size();j++){
                if(circles.get(i).isContacting(circles.get(j))!=null&&circles.get(i).id!=circles.get(j).id){
                    results.add(circles.get(i).isContacting(circles.get(j)));
                }
            }
        }
        return results;
    }

    // todo for students
    // Returns a HashSet of ContactResult objects representing all the contacts between circles in the scene.
    // The runtime of this method should be O(n*log(n)) where n is the number of circles.
    public static HashSet<ContactResult> getContactsBVH(ArrayList<Circle> circles, BVH bvh) {
        HashSet<ContactResult> results= new HashSet();
        for(int i = 0; i< circles.size();i++){
            for(ContactResult result : getContactBVH(circles.get(i),bvh)){
                results.add(result);
            }
        }

        return results;

    }

    // todo for students
    // Takes a single circle c and a BVH bvh.
    // Returns a HashSet of ContactResult objects representing contacts between c
    // and the circles contained in the leaves of the bvh.
    public static HashSet<ContactResult> getContactBVH(Circle c, BVH bvh) {
        HashSet<ContactResult> results = new HashSet();
        if(bvh == null || !c.getBoundingBox().intersectBox(bvh.boundingBox)) return results;
        if(bvh.child1==null && bvh.child2==null){//if bvh is a leaf node
            if(c.getBoundingBox().intersectBox(bvh.boundingBox)&&c.id!=bvh.containedCircle.id){//if bvh is contacting the bounding box of c
                if (c.isContacting(bvh.containedCircle)!=null&&c.id!=bvh.containedCircle.id){//if bvh is contacting the c

                    results.add(c.isContacting(bvh.containedCircle));
                    return results;
                }
            }}else{
            HashSet leftResults = getContactBVH(c,bvh.child1);
            HashSet rightResults = getContactBVH(c,bvh.child2);
            results.addAll(leftResults);
            results.addAll(rightResults);
        }
//        }else if(bvh.child1 == null && bvh.child2!=null){
//            for (ContactResult r : getContactBVH(c,bvh.child2)){
//                results.add(r);
//            }
//        }else if(bvh.child1 != null && bvh.child2 == null) {
//            for (ContactResult r : getContactBVH(c,bvh.child1)){
//                results.add(r);
//            }
//        }else{
//            for (ContactResult r : getContactBVH(c,bvh.child1)){
//                results.add(r);
//            }
//            for (ContactResult r : getContactBVH(c,bvh.child2)){
//                results.add(r);
//            }
//        }
        return results;
    }
}
