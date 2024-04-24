//Erokhin Evgenii DSAI-03
//e.erokhin@innopolis.university
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
// Answer 2.1.2. The problem of line intersection is quit common in our life. A lot of workers with different
//jobs should solute it. Let us find some examples. Navigation in different ways. Construction of something
//like houses, spaceships. Solders sold this problem when makes plans or configure some weapons. In some factories
//to make a lot of different things. Astronomer to understand where which constellation is. Billiard players when
//making kick. So the problem is often, and we need sometimes to solve it.
/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Point[] points = new Point[2 * N];
        Point[] pointsToPrint = new Point[2 * N];
        Segment[] segments = new Segment[N];
        Segment[] segmentsToPrint = new Segment[N];
        SegE[] ev = new SegE[N * 2];
        for (int i = 0; i < N; i++) {
            String line1 = br.readLine();
            String[] lineS = line1.split(" ");
            int xP = Integer.parseInt(lineS[0]);  // Read coordinates  of segment
            int yP = Integer.parseInt(lineS[1]);
            int xQ = Integer.parseInt(lineS[2]);
            int yQ = Integer.parseInt(lineS[3]);

            pointsToPrint[2 * i] = new Point(xP, yP, "left");
            pointsToPrint[2 * i + 1] = new Point(xQ, yQ, "right");
            // Segment[] to print in right way
            segmentsToPrint[i] = new Segment(pointsToPrint[2 * i], pointsToPrint[2 * i + 1]);

            if (xP < xQ) { // set flags to points
                points[2 * i] = new Point(xP, yP, "left");
                points[2 * i + 1] = new Point(xQ, yQ, "right");
                segments[i] = new Segment(points[2 * i], points[2 * i + 1]);

            } else if (xP > xQ) {
                points[2 * i] = new Point(xQ, yQ, "left");
                points[2 * i + 1] = new Point(xP, yP, "right");
                segments[i] = new Segment(points[2 * i], points[2 * i + 1]);

            } else if (yP > yQ) {
                points[2 * i] = new Point(xQ, yQ, "left");
                points[2 * i + 1] = new Point(xP, yP, "right");
                segments[i] = new Segment(points[2 * i], points[2 * i + 1]);

            } else {
                points[2 * i] = new Point(xP, yP, "left");
                points[2 * i + 1] = new Point(xQ, yQ, "right");
                segments[i] = new Segment(points[2 * i], points[2 * i + 1]);
            }


        }
        sort(points); //Sort points using quicksort


        for (int i = 0; i < segments.length; i++) {

            // add left
            SegE evNew = new SegE();
            evNew.setX(segments[i].getLeft().getX()); //set x
            evNew.setType(-1); // if point left set -1
            evNew.setId(i); //set number of segment
            segments[i].setIndex(i); //set number of segment to segment

            //add right
            ev[2 * i] = evNew;
            SegE evNew1 = new SegE();
            evNew1.setX(segments[i].getRight().getX()); //set x
            evNew1.setType(1); // if point right set 1
            evNew1.setId(i); //set number of segment
            ev[2 * i + 1] = evNew1;
        }

        sort(ev); //sort events


        Tree<Segment> tree = new Tree<Segment>(); //make new avlTree
        for (int i = 0; i < ev.length; i++) {

            //Sweep line algorithm we go from the smallest point from x to the biggest
            //If point is left point of segment we add it to avl tree. If it has successor( the closest bigger element)
            //check intersection of successor and our element. If it has predecessor( the closest smaller element)
            //check intersection of predecessor and our element. If point is right delete segment from avl tree.
            //If element we have deleted has successor and predecessor check their intersection
            //Segments are compared by Y coordinate.
            //The main idea of sweep line algorithm is scanning line x= which goes from -infinity if our segment star
            //(cross the scanning line) we add him to storage and check intersection of this segment with successor
            //and predecessor if segment does not intersect our line anymore delete segment from the storage and check
            //the intersection of predecessor and successor. Answer 2.1.3


            if (ev[i].getType() == -1) { //If left
                if (tree.root == null) {
                    tree.insert(segments[ev[i].getId()]);
                } else {
                    tree.insert(segments[ev[i].getId()]);
                    Segment temp1, temp2;
                    if (tree.next(segments[ev[i].getId()]) != null) {

                        temp1 = tree.next(segments[ev[i].getId()]);
//
                        if (intersect(temp1, segments[ev[i].getId()]).equals("yes")) {

                            //If found intersection print. End program
                            System.out.println("INTERSECTION");
                            System.out.println(segmentsToPrint[ev[i].getId()].getLeft().getX() + " " + segmentsToPrint[ev[i].getId()].getLeft().getY() + " " +
                                    segmentsToPrint[ev[i].getId()].getRight().getX() + " " + segmentsToPrint[ev[i].getId()].getRight().getY());
                            System.out.println(segmentsToPrint[temp1.getIndex()].getLeft().getX() + " " + segmentsToPrint[temp1.getIndex()].getLeft().getY() + " " +
                                    segmentsToPrint[temp1.getIndex()].getRight().getX() + " " + segmentsToPrint[temp1.getIndex()].getRight().getY());
                            System.exit(0);
                        }
                    }


                    if (tree.prev(segments[ev[i].getId()]) != null) {
                        temp2 = tree.prev(segments[ev[i].getId()]);
                        if (intersect(temp2, segments[ev[i].getId()]).equals("yes")) {

                            //If found intersection print. End program
                            System.out.println("INTERSECTION");
                            System.out.println(segmentsToPrint[ev[i].getId()].getLeft().getX() + " " + segmentsToPrint[ev[i].getId()].getLeft().getY() + " " +
                                    segmentsToPrint[ev[i].getId()].getRight().getX() + " " + segmentsToPrint[ev[i].getId()].getRight().getY());
                            System.out.println(segmentsToPrint[temp2.getIndex()].getLeft().getX() + " " + segmentsToPrint[temp2.getIndex()].getLeft().getY() + " " +
                                    segmentsToPrint[temp2.getIndex()].getRight().getX() + " " + segmentsToPrint[temp2.getIndex()].getRight().getY());
                            System.exit(0);
                        }
                    }
                }


            }
            if (ev[i].getType() == 1) {//If point is right
                Segment temp1, temp2;
                if (tree.prev(segments[ev[i].getId()]) != null && tree.next(segments[ev[i].getId()]) != null) {
                    temp1 = tree.next(segments[ev[i].getId()]);
                    temp2 = tree.prev(segments[ev[i].getId()]);
                    tree.delete(segments[ev[i].getId()]);

                    //If found intersection print. End program
                    if (intersect(temp1, temp2).equals("yes")) {
                        System.out.println("INTERSECTION");
                        System.out.println(segmentsToPrint[temp1.getIndex()].getLeft().getX() + " " + segmentsToPrint[temp1.getIndex()].getLeft().getY() + " " +
                                segmentsToPrint[temp1.getIndex()].getRight().getX() + " " + segmentsToPrint[temp1.getIndex()].getRight().getY());
                        System.out.println(segmentsToPrint[temp2.getIndex()].getLeft().getX() + " " + segmentsToPrint[temp2.getIndex()].getLeft().getY() + " " +
                                segmentsToPrint[temp2.getIndex()].getRight().getX() + " " + segmentsToPrint[temp2.getIndex()].getRight().getY());
                        System.exit(0);
                    }
                } else tree.delete(segments[ev[i].getId()]);
            }
        }
        System.out.println("NO INTERSECTIONS"); //If did not find intersection
    }

    /**
     * Intersect point. Method to understand do two segments intersect or not. Answer for 2.1.1
     *
     * @param segment1 the segment 1
     * @param segment2 the segment 2
     * @return yes if intersect else no
     */
    public static String intersect(Segment segment1, Segment segment2) {

        // Case when two segments are vertical
        if ((segment1.getLeft().getX() - segment1.getRight().getX() == 0) && (segment2.getLeft().getX() - segment2.getRight().getX() == 0)) {
            // X1=X2
            if (segment1.getLeft().getX() == segment1.getLeft().getX()) {
                // Compare with Y coordinates to understand do they have common point
                if (!((Math.max(segment1.getLeft().getY(), segment1.getRight().getY()) < Math.min(segment2.getLeft().getY(), segment2.getRight().getY())) ||
                        (Math.min(segment1.getLeft().getY(), segment1.getRight().getY()) > Math.max(segment2.getLeft().getY(), segment2.getRight().getY())))) {
                    return "yes";
                }
            }
            return "no";
        }

        // Case when left segment is vertical
        if (segment1.getLeft().getX() - segment1.getRight().getX() == 0) {

            // make line of second points find intersection point
            double xP = segment1.getLeft().getX();
            double A2 = (segment2.getLeft().getY() - segment2.getRight().getY()) / (segment2.getLeft().getX() - segment2.getRight().getX());
            double b2 = segment2.getLeft().getY() - A2 * segment2.getLeft().getX();
            double yP = A2 * xP + b2;

            //check does our intersection point lies on our segment
            if (segment2.getLeft().getX() <= xP && segment2.getRight().getX() >= xP && Math.min(segment1.getLeft().getY(),
                    segment1.getRight().getY()) <= yP &&
                    Math.max(segment1.getLeft().getY(), segment1.getRight().getY()) >= yP) {
                return "yes";
            }
            return "no";
        }

        // Case when right segment is vertical
        if (segment2.getLeft().getX() - segment2.getRight().getX() == 0) {

            // make line of first points find intersection point
            double xP = segment2.getLeft().getX();
            double A1 = (segment1.getLeft().getY() - segment1.getRight().getY()) / (segment1.getLeft().getX() - segment1.getRight().getX());
            double b1 = segment1.getLeft().getY() - A1 * segment1.getLeft().getX();
            double yP = A1 * xP + b1;

            //check does our intersection point lies on our segment
            if (segment1.getLeft().getX() <= xP && segment1.getRight().getX() >= xP && Math.min(segment2.getLeft().getY(),
                    segment2.getRight().getY()) <= yP &&
                    Math.max(segment2.getLeft().getY(), segment2.getRight().getY()) >= yP) {
                return "yes";
            }
            return "no";
        }

        // Case when both segments are not vertical. Make two line
        double A1 = (double) (segment1.getLeft().getY() - segment1.getRight().getY()) / (segment1.getLeft().getX() - segment1.getRight().getX());
        double A2 = (double) (segment2.getLeft().getY() - segment2.getRight().getY()) / (segment2.getLeft().getX() - segment2.getRight().getX());
        double b1 = segment1.getLeft().getY() - A1 * segment1.getLeft().getX();
        double b2 = segment2.getLeft().getY() - A2 * segment2.getLeft().getX();

        //Parallel lines
        if (A1 == A2)
            return "no";

        //find intersection point
        double xP = (b2 - b1) / (A1 - A2);
        double yP = A1 * xP + b1;

        //check does our intersection point lies on our segment
        if ((xP < Math.max(segment1.getLeft().getX(), segment2.getLeft().getX())) || (xP > Math.min(segment1.getRight().getX(),
                segment2.getRight().getX()))) {
            return "no";
        } else return "yes";
    }


    /**
     * Quick sort.
     *
     * @param <T>   the type parameter
     * @param array the array
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }


    /**
     * 2.1.4 Quick sort. Quick sort for comparable data. It is classic algorithm of quick sort implementation
     * The time complexity of this algorithm is O(nlog(n)). It is unstable, in-place algorithm
     *
     * @param <T>   the type parameter
     * @param array the array to be sorted
     * @param from  the star index
     * @param to    the end index
     */
    public static <T extends Comparable<T>> void sort(T[] array, int from, int to) {
        if (from < to) {
            int divIn = div(array, from, to);
            sort(array, from, divIn - 1);
            sort(array, divIn + 1, to);
        }
    }

    private static <T extends Comparable<T>> int div(T[] arr, int from, int to) {
        T fix = arr[to];
        int k = (from - 1);

        for (int i = from; i < to; i++) {
            if (arr[i].compareTo(fix) <= 0) {
                k++;
                T swapTemp = arr[k];
                arr[k] = arr[i];
                arr[i] = swapTemp;
            }
        }

        T swapTemp = arr[k + 1];
        arr[k + 1] = arr[to];
        arr[to] = swapTemp;

        return k + 1;
    }
}

/**
 * The type Seg e. Class of events which contains x of point number of segment which this point belong to
 * and -1 if left point 1 if right(type)
 */
class SegE implements Comparable<SegE> {
    /**
     * The X.
     */
    long x;
    private int type, id;

    /**
     * Gets x.
     *
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
        this.type = type;
    }

    //How we should compare events
    @Override
    public int compareTo(SegE o) {
        if (this.x > o.x) {
            return 1;
        } else if (this.x == o.x) return 0;
        else return -1;

    }
}

/**
 * The type Point. Coordinates of point and flag
 */
class Point implements Comparable<Point> {
    /**
     * The Flag.
     */
    String flag;
    private long x, y;

    /**
     * Instantiates a new Point.
     *
     * @param x    the x
     * @param y    the y
     * @param flag the flag
     */
    Point(long x, long y, String flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public long getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * Gets flag.
     *
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets flag.
     *
     * @param flag the flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    //How we should compare points
    @Override
    public int compareTo(Point o) {
        if (this.x > o.x) {
            return 1;
        } else if (this.x == o.x)
            if (!this.getFlag().equals("right")) return 1;
            else return 0;
        return -1;
    }
}

/**
 * The type Segment. Class of segments. With left point, right point and number of this segment
 */
class Segment implements Comparable<Segment> {
    private final Point left;
    private final Point right;
    private int index;

    /**
     * Instantiates a new Segment.
     *
     * @param pointLeft  the point left
     * @param pointRight the point right
     */
    Segment(Point pointLeft, Point pointRight) {
        this.left = pointLeft;
        this.right = pointRight;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets left.
     *
     * @return the left
     */
    public Point getLeft() {
        return left;
    }

    /**
     * Gets right.
     *
     * @return the right
     */
    public Point getRight() {
        return right;
    }

    //How we should compare segments
    @Override
    public int compareTo(Segment o) {

        if (this.left.getY() > o.left.getY()) {
            return 1;
        } else if (this.left.getY() == o.left.getY()) return 0;
        else return -1;
    }
}

/**
 * The type Vertice. Vertices of avl tree.
 *
 * @param <T> the type parameter
 */
class Vertice<T extends Comparable<T>> {
    /**
     * The Level.
     */
    public int level;
    private T dataOfver;
    private int height;
    private Vertice<T> left, right;


    /**
     * Instantiates a new Vertice.
     *
     * @param dataOfver the data ofver
     */
    public Vertice(T dataOfver) {
        this(dataOfver, null, null);
    }

    /**
     * Instantiates a new Vertice.
     *
     * @param dataOfver the data of vertice
     * @param left      the left vertice to ours
     * @param right     the right vertice to ours
     */
    public Vertice(T dataOfver, Vertice<T> left, Vertice<T> right) {
        this.dataOfver = dataOfver;
        this.left = left;
        this.right = right;
        if (left == null && right == null) this.height = 1;
        else if (right == null) this.height = left.height + 1; //High for vertices for future operations
        else if (left == null) this.height = right.height + 1;
        else {
            if (left.height >= right.height) this.height = left.height + 1;
            else this.height = right.height + 1;
        }

    }

    /**
     * Set high.
     *
     * @param high the high
     */
    public void setHigh(int high) {
        this.height = high;
    }


    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets left.
     *
     * @return the left
     */
    public Vertice<T> getLeft() {
        return left;
    }

    /**
     * Sets left.
     *
     * @param left the left
     */
    public void setLeft(Vertice<T> left) {
        this.left = left;
    }

    /**
     * Gets right.
     *
     * @return the right
     */
    public Vertice<T> getRight() {
        return right;
    }

    /**
     * Sets right.
     *
     * @param right the right
     */
    public void setRight(Vertice<T> right) {
        this.right = right;
    }

    /**
     * Gets data ofver.
     *
     * @return the data ofver
     */
    public T getDataOfver() {
        return dataOfver;
    }

    /**
     * Sets data ofver.
     *
     * @param dataOfver the data ofver
     */
    public void setDataOfver(T dataOfver) {
        this.dataOfver = dataOfver;
    }

}

/**
 * The type Tree. Avl tree class. 2.1.5
 *
 * @param <T> the type parameter
 */
class Tree<T extends Comparable<T>> {
    /**
     * The Root.
     */
    Vertice<T> root;

    /**
     * Instantiates a new Tree.
     */
    public Tree() {
        root = null;
    }

    //High of tree vertice with checking null
    private int high(Vertice<T> vertice) {
        if (vertice == null)
            return 0;
        return vertice.getHeight();
    }

    /**
     * Insert vertice.
     *
     * @param data the data
     */
    public void insert(T data) {
        root = insert(root, data);
        if (balNum(root).equals("LeftRotate")) {
            root = rotate("Left", root);
        } else if (balNum(root).equals("RightRotate")) {
            root = rotate("Right", root);
        }
    }

    /**
     * Insert vertice. To insert vertice into AVL tree after insertion check should we rebalance tree or not.
     * If we need make rotation. To insert element time complexity is O(logn) <=rotation give us constant time as tree is balanced
     * it hight is log(n).
     *
     * @param vertice   the vertice
     * @param dataOfver the data ofver
     * @return the vertice
     */
    public Vertice<T> insert(Vertice<T> vertice, T dataOfver) {
        if (vertice == null)
            return new Vertice<T>(dataOfver);
        if (vertice.getDataOfver().compareTo(dataOfver) > 0) {
            vertice = new Vertice<T>(vertice.getDataOfver(), insert(vertice.getLeft(), dataOfver), vertice.getRight());

        } else if (vertice.getDataOfver().compareTo(dataOfver) < 0) {
            vertice = new Vertice<T>(vertice.getDataOfver(), vertice.getLeft(), insert(vertice.getRight(), dataOfver));
        }
        if (balNum(vertice).equals("LeftRotate")) {
            vertice = rotate("Left", vertice);
        }
        if (balNum(vertice).equals("RightRotate")) {
            vertice = rotate("Right", vertice);
        }
        return vertice;


    }

    /**
     * Bal num string. Choose which rotation we should do
     *
     * @param ver the ver
     * @return the string
     */
    public String balNum(Vertice<T> ver) {
        int leftTreeHeight = high(ver.getLeft());
        int rightTreeHight = high(ver.getRight());
        if (leftTreeHeight - rightTreeHight <= -2)
            return "LeftRotate";
        else if (leftTreeHeight - rightTreeHight >= 2)
            return "RightRotate";
        return "NoNeedToRotate";
    }

    /**
     * Rotate left and right
     *
     * @param which   the which
     * @param vertice the vertice
     * @return the vertice
     */
    public Vertice<T> rotate(String which, Vertice<T> vertice) {
        if (which.equals("Left")) { // Left rotation
            Vertice<T> q = vertice;
            Vertice<T> p = q.getRight();
            Vertice<T> c = q.getLeft();
            Vertice<T> a = p.getLeft();
            Vertice<T> b = p.getRight();
            q = new Vertice<T>(q.getDataOfver(), c, a);
            p = new Vertice<T>(p.getDataOfver(), q, b);
            return p;
        } else if (which.equals("Right")) { // Right rotation
            Vertice<T> q = vertice;
            Vertice<T> p = q.getLeft();
            Vertice<T> c = q.getRight();
            Vertice<T> a = p.getLeft();
            Vertice<T> b = p.getRight();
            q = new Vertice<T>(q.getDataOfver(), b, c);
            p = new Vertice<T>(p.getDataOfver(), a, q);
            return p;
        }
        return null;
    }


    /**
     * Delete
     *
     * @param data the data
     */
    public void delete(T data) {
        root = delete(this.root, data);
    }

    /**
     * Gets height.
     *
     * @param vertice the vertice
     * @return the height
     */
    int getHeight(Vertice<T> vertice) {
        if (vertice == null)
            return 0;

        return vertice.getHeight();
    }

    /**
     * Delete vertice. Delete vertice from AVL graph. Time complexity for delete element is O(log(n)) as rotation give us constant time as tree is balanced
     * it height is log(n)
     *
     * @param vertice the vertice
     * @param data    the data
     * @return the vertice
     */
    public Vertice<T> delete(Vertice<T> vertice, T data) {

        if (vertice == null) return null;

        if (data.compareTo(vertice.getDataOfver()) < 0) {
            vertice.setLeft(delete(vertice.getLeft(), data));
        } else if (data.compareTo(vertice.getDataOfver()) > 0) {
            vertice.setRight(delete(vertice.getRight(), data));
        } else {

            if (vertice.getLeft() == null) {
                vertice = vertice.getRight();
            } else if (vertice.getRight() == null) {
                vertice = vertice.getLeft();
            } else {
                Vertice<T> leftsMin = vertice.getLeft();
                while (leftsMin.getRight() != null) {
                    leftsMin = leftsMin.getRight();
                }

                T inorderSuccessorValue = leftsMin.getDataOfver();
                vertice.setDataOfver(inorderSuccessorValue);
                vertice.setLeft(delete(vertice.getLeft(), inorderSuccessorValue));


            }
        }


        if (vertice == null) {
            return null;
        }
        if (getHeight(vertice.getRight()) > getHeight(vertice.getLeft()))
            vertice.setHigh(getHeight(vertice.getRight()));
        else if (getHeight(vertice.getRight()) < getHeight(vertice.getLeft()))
            vertice.setHigh(getHeight(vertice.getLeft()));

        String balance = balNum(vertice);

        if (balance.equals("RightRotate")) {
            int bal;
            bal = getHeight(vertice.getLeft()) - getHeight(vertice.getRight());

            if (bal >= 0) {
                vertice = rotate("Right", vertice);
            } else {
                vertice.setLeft(rotate("(Left", vertice.getLeft()));
                vertice = rotate("Right", vertice);
            }
        } else if (balance.equals("LeftRotate")) {
            int bal;
            bal = getHeight(vertice.getLeft()) - getHeight(vertice.getRight());
            if (bal <= 0) {
                vertice = rotate("Left", vertice);
            } else {
                vertice.setRight(rotate("Right", vertice.getRight()));
                vertice = rotate("Left", vertice);
            }
        }
        return vertice;
    }


    /**
     * Next t. Find successor to our element. In first step we chose to go left otr right that split our element in two
     * parts as we know tree is balanced so find element we need will be in log(n)
     *
     * @param data the data
     * @return the t
     */
    public T next(T data) {
        Vertice<T> rootFollowing = root;
        Vertice<T> par = null;
        if (data.compareTo(rootFollowing.getDataOfver()) >= 0) {
            while (rootFollowing.getRight() != null && data.compareTo(rootFollowing.getDataOfver()) >= 0)
                rootFollowing = rootFollowing.getRight();
            par = rootFollowing;
            boolean vis = false;
            while (rootFollowing.getLeft() != null) {
                vis = true;
                rootFollowing = rootFollowing.getLeft();
            }
            if (rootFollowing.getDataOfver().compareTo(data) <= 0 && vis) return par.getDataOfver();
            else if (rootFollowing.getDataOfver().compareTo(data) <= 0 && !vis) {
                return null;
            } else
                return rootFollowing.getDataOfver();
        } else {
            while (rootFollowing.getLeft() != null && data.compareTo(rootFollowing.getDataOfver()) < 0) {
                par = rootFollowing;
                rootFollowing = rootFollowing.getLeft();
            }
            while (rootFollowing.getRight() != null)
                rootFollowing = rootFollowing.getRight();
            if (rootFollowing.getDataOfver().compareTo(data) <= 0) return par.getDataOfver();
            else
                return rootFollowing.getDataOfver();
        }
    }

    /**
     * Next t. Find predecessor to our element. In first step we chose to go left otr right that split our element in two
     * parts as we know tree is balanced so find element we need will be in log(n)
     * @param data the data
     * @return the t
     */
    public T prev(T data) {
        Vertice<T> rootFollowing = root;
        Vertice<T> par = null;
        if (rootFollowing == null) return null;
        if (data.compareTo(rootFollowing.getDataOfver()) > 0) {
            while (rootFollowing.getRight() != null && data.compareTo(rootFollowing.getDataOfver()) > 0) {
                par = rootFollowing;
                rootFollowing = rootFollowing.getRight();
            }
            while (rootFollowing.getLeft() != null) {

                rootFollowing = rootFollowing.getLeft();
            }
            if (rootFollowing.getDataOfver().compareTo(data) >= 0) return par.getDataOfver();
            else
                return rootFollowing.getDataOfver();
        } else {

            while (rootFollowing.getLeft() != null && data.compareTo(rootFollowing.getDataOfver()) <= 0) {
                rootFollowing = rootFollowing.getLeft();
            }
            par = rootFollowing;
            boolean vis = false;
            while (rootFollowing.getRight() != null) {
                vis = true;
                rootFollowing = rootFollowing.getRight();
            }

            if (rootFollowing.getDataOfver().compareTo(data) >= 0 && vis) return par.getDataOfver();
            else if (rootFollowing.getDataOfver().compareTo(data) >= 0 && !vis) {
                return null;
            } else
                return rootFollowing.getDataOfver();
        }
    }
}
