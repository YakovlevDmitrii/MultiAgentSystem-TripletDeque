import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<T> implements Deque<T>, Containerable {

    private Triplet<T> firstTriplet = new Triplet<>();
    private Triplet<T> lastTriplet = firstTriplet;

    private int size = 0;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 1000;

    public Triplet<T> getFirstTriplet() {
        return firstTriplet;
    }

    public Triplet<T> getLastTriplet() {
        return lastTriplet;
    }

    public TripletDeque(int capacity) {
        this.capacity = capacity;
    }

    public TripletDeque() {
        this.capacity = DEFAULT_CAPACITY;
    }

   @Override
   public void addFirst(T t) {
       if (t == null) {
           throw new NullPointerException("Element is null");
       }
       if (size == capacity) {
           throw new IllegalStateException("Deque is overcrowded. No free space for adding new element");
       }
       firstTriplet.addElementInFirstPlace(t);
       if (firstTriplet.getPreviousTriplet()!= null) {
           firstTriplet = firstTriplet.getPreviousTriplet();
       }
       size++;
   }

    @Override
    public void addLast(T t) {
        if (t == null) {
            throw new NullPointerException("Element is null");
        }
        add(t);
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        T removedElement = firstTriplet.removeFirstElement();

        if (lastTriplet.isEmpty() && lastTriplet.getPreviousTriplet() != null) {
            lastTriplet = lastTriplet.getPreviousTriplet();
            lastTriplet.setNextTriplet(null);
        }
        if (firstTriplet.getNextTriplet()!=null && firstTriplet.getNextTriplet().getPreviousTriplet()==null){
            firstTriplet=firstTriplet.getNextTriplet();
        }
        return removedElement;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        size--;
        T removedElement = lastTriplet.removeLastElement();

        if (lastTriplet.isEmpty() && lastTriplet.getPreviousTriplet() != null) {
            lastTriplet = lastTriplet.getPreviousTriplet();
            lastTriplet.setNextTriplet(null);
        }
        return removedElement;
    }

    @Override
    public T pollFirst() {
        if (size == 0) {
            return null;
        } else {
            return removeFirst();
        }
    }

    @Override
    public T pollLast() {
        if (size == 0) {
            return null;
        } else {
            return removeLast();
        }
    }

    @Override
    public T getFirst() {
        return firstTriplet.getFirstElement();
    }

    @Override
    public T getLast() {
        return lastTriplet.getLastElement();
    }

    @Override
    public T peekFirst() {
        if (size == 0) {
            return null;
        } else {
            return getFirst();
        }
    }

    @Override
    public T peekLast() {
        if (size == 0) {
            return null;
        } else {
            return getLast();
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Element is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Triplet<T> currentTriplet = firstTriplet;
        int indexInTripletOfRemovedElement = -1;
        while (currentTriplet != null ) {
            indexInTripletOfRemovedElement = currentTriplet.findElement((T) o);
            if (indexInTripletOfRemovedElement != -1) {
                break;
            }
            currentTriplet = currentTriplet.getNextTriplet();
        }

        if (indexInTripletOfRemovedElement == -1) {
            throw new NoSuchElementException("Deque doesn't contain element: " + o);
        }
        currentTriplet.removeElementByIndex(indexInTripletOfRemovedElement);
        size--;
        if (lastTriplet.isEmpty() && lastTriplet.getPreviousTriplet() != null) {
            lastTriplet = lastTriplet.getPreviousTriplet();
            lastTriplet.setNextTriplet(null);
        }
        return true;

    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            throw new NullPointerException("Element is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Triplet<T> currentTriplet = lastTriplet;
        int indexInTripletOfRemovedElement = -1;
        while (currentTriplet != null ) {
            indexInTripletOfRemovedElement = currentTriplet.findLastElement((T) o);
            if (indexInTripletOfRemovedElement != -1) {
                break;
            }
            currentTriplet = currentTriplet.getPreviousTriplet();
        }

        if (indexInTripletOfRemovedElement == -1) {
            throw new NoSuchElementException("Deque doesn't contain element: " + o);
        }
        currentTriplet.removeElementByIndex(indexInTripletOfRemovedElement);
        size--;
        if (lastTriplet.isEmpty() && lastTriplet.getPreviousTriplet() != null) {
            lastTriplet = lastTriplet.getPreviousTriplet();
            lastTriplet.setNextTriplet(null);
        }
        return true;

    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException("Element is null");
        }
        if (size == capacity) {
            throw new IllegalStateException("Deque is overcrowded. No free space for adding new element");
        }
        if (!lastTriplet.addElement(t)) {
            lastTriplet = new Triplet<>(lastTriplet, null);
            lastTriplet.addElement(t);
            lastTriplet.getPreviousTriplet().setNextTriplet(lastTriplet);
        }
        size++;
        return true;
    }

    @Override
    public boolean offer(T t) {
        if (size == capacity) {
            return false;
        }
        return add(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c==null){
            throw new NullPointerException("Collection is null");
        }
        if ((capacity-size)<c.size()){
            throw new IllegalStateException("Size of collection "+ c.size()+ " more than empty place in deque "+(capacity-size));
        }
        for (Object o: c){
            add((T)o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation is not realize");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation is not realize");
    }

    @Override
    public void clear() {
        firstTriplet = lastTriplet = new Triplet<>();
        size = 0;
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operation is not realize");
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Element is null");
        }
        if (size == 0) {
            return false;
        }
        boolean isFoundElement = false;
        Triplet<T> currentTriplet = firstTriplet;
        while (currentTriplet != null && !isFoundElement) {
            int indexInTripletOfFoundElement = currentTriplet.findElement((T) o);
            if (indexInTripletOfFoundElement != -1) {
                isFoundElement = true;
                break;
            }
            currentTriplet = currentTriplet.getNextTriplet();
        }
        return isFoundElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Triplet<T> currentTriplet=firstTriplet;
            int currentIndex = currentTriplet.getIndexOfFirstNotNullElement()-1;
            @Override
            public boolean hasNext() {
                if (currentTriplet!=null){
                    if (currentIndex+1 < currentTriplet.getMaxIndexOfTriplet() && !currentTriplet.isEmpty()){
                        return true;
                    }else {
                        if (currentTriplet.getNextTriplet()!=null){
                            currentTriplet=currentTriplet.getNextTriplet();
                            currentIndex=-1;
                            return true;
                        }else {
                            return false;
                        }
                    }
                }
                return false;
            }

            @Override
            public T next() {
                if (!hasNext()){
                    throw new NoSuchElementException("Iterator hasn't next element");
                }
                currentIndex++;
                return currentTriplet.getByIndex(currentIndex);
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Operation is not realize");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Operation is not realize");
    }

    @Override
    public Iterator<T> descendingIterator() {
        throw new UnsupportedOperationException("Operation is not realize");
    }


    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int indexOfCurrentContainer=0;
        Triplet<T> currentTriplet=firstTriplet;
        while (currentTriplet!=null){
            if (indexOfCurrentContainer==cIndex){
                return currentTriplet.getElements();
            }
            currentTriplet=currentTriplet.getNextTriplet();
            indexOfCurrentContainer++;
        }
        return null;
    }
}
