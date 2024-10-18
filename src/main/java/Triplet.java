

public class Triplet<T> {

    private Triplet<T> previousTriplet;
    private Triplet<T> nextTriplet;


    private final Object[] elements = new Object[5];
    private int currentSize = 0;

    public Triplet() {
    }

    public Triplet(Triplet<T> previousTriplet, Triplet<T> nextTriplet) {
        this.previousTriplet = previousTriplet;
        this.nextTriplet = nextTriplet;
    }


    public void setNextTriplet(Triplet<T> nextTriplet) {
        this.nextTriplet = nextTriplet;
    }

    public Triplet<T> getPreviousTriplet() {
        return previousTriplet;
    }

    public Triplet<T> getNextTriplet() {
        return nextTriplet;
    }

    public Object[] getElements() {
        return elements;
    }

    public boolean addElement(T element) {
        if (currentSize == elements.length) {
            return false;
        }
        elements[currentSize] = element;
        currentSize += 1;
        return true;
    }

    public boolean addElementInFirstPlace(T element) {
        if (currentSize == 0) {
            elements[currentSize] = element;
            currentSize++;
            return true;
        }
        if (currentSize < elements.length) {
            if (elements[0] != null) {
                Object oldElement = elements[0];
                elements[0] = element;
                for (int i = 1; i < currentSize + 1; i++) {
                    Object buff = elements[i];
                    elements[i] = oldElement;
                    oldElement = buff;
                }
            }else {
                elements[elements.length-currentSize-1]=element;
            }
            currentSize++;
        } else {
            previousTriplet = new Triplet<>(null, this);
            previousTriplet.addElementInLastPlace(element);
        }
        return true;
    }

    private void addElementInLastPlace(T element) {
        elements[elements.length - 1] = element;
        currentSize++;
    }


    public T getFirstElement() {
        if (elements[0]==null){
            return (T) elements[elements.length-currentSize];
        }else {
            return (T) elements[0];
        }
    }

    public T getLastElement() {
        return (T) elements[currentSize - 1];
    }


    public T removeFirstElement() {
        if (elements[0]==null){ // если индексация в контейнере начинается с конца
            T removedElement = (T) elements[elements.length-currentSize];
            if (currentSize==1){ // если это единственный элемент в контейнере то удаляем контейнер
                this.nextTriplet.previousTriplet=null;
                return removedElement;
            }
            elements[elements.length-currentSize]=null;
            currentSize--;
            return removedElement;
        }else{
            return removeElementByIndex(0);
        }
    }

    public T removeElementByIndex(int index) {
        Object removedElement = elements[index];
        for (int i = index + 1; i < currentSize; i++) {
            elements[i - 1] = elements[i];
        }
        if (nextTriplet != null) {
            elements[currentSize - 1] = nextTriplet.removeElementByIndex(0);
            if (nextTriplet.isEmpty()) {
                nextTriplet = null;
                return (T) removedElement;
            }
        } else {
            elements[currentSize - 1] = null;
            currentSize--;
        }
        return (T) removedElement;
    }


    public T removeLastElement() {
        Object removedElement = elements[currentSize - 1];
        elements[currentSize - 1]=null;
        currentSize--;
        return (T) removedElement;
    }

    public int findElement(T necessaryElement) {
        for (int i = 0; i < currentSize; i++) {
            if (elements[i]!=null && elements[i].equals(necessaryElement)) {
                return i;
            }
        }
        return -1;
    }

    public int findLastElement(T necessaryElement) {
        for (int i = currentSize - 1; i >= 0; i--) {
            if (elements[i]!=null && elements[i].equals(necessaryElement)) {
                return i;
            }
        }
        return -1;
    }


    public T getByIndex(int index) {
        return (T) elements[index];
    }

    public int getMaxIndexOfTriplet() {
        if (elements[0]==null) { // если индексация в контейнере начинается с конца
            return elements.length;
        }else{
            return currentSize;
        }
    }

    public int getIndexOfFirstNotNullElement(){
        for (int i = 0; i < currentSize; i++) {
            if (elements[i]!=null) {
                return i;
            }
        }
        return -1;
    }


    public boolean isEmpty() {
        return currentSize == 0;
    }


}
