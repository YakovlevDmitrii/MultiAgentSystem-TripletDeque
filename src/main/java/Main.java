import java.util.Deque;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        TripletDeque<Integer> tq = new TripletDeque<>();
        Triplet<Integer> firstTriplet = tq.getFirstTriplet();
        Triplet<Integer> lastTriplet = tq.getLastTriplet();
        tq.addFirst(1);
        tq.addFirst(2);
        tq.addFirst(3);
        tq.addFirst(1);
        tq.addFirst(2);
        tq.addLast(3);
        tq.addFirst(1);
        tq.addLast(2);
        tq.addFirst(3);
        tq.addFirst(1);



        Iterator<Integer> integerIterator=tq.iterator();

        while (integerIterator.hasNext()){
            System.out.println(integerIterator.next());
        }








//        tq.removeLastOccurrence(3);
//        Triplet<Integer> firstTriplet = tq.getFirstTriplet();
//        Triplet<Integer> lastTriplet = tq.getLastTriplet();
//        tq.removeLastOccurrence(3);
//         firstTriplet = tq.getFirstTriplet();
//         lastTriplet = tq.getLastTriplet();
//        tq.removeLastOccurrence(3);
//        firstTriplet = tq.getFirstTriplet();
//        lastTriplet = tq.getLastTriplet();



//        System.out.println(tq.removeFirstOccurrence(2));
//        System.out.println(tq.removeFirstOccurrence(5));
//        tq.removeFirst();
//        tq.removeFirst();

//        System.out.println(tq.size());
        System.out.println();

    }
}
