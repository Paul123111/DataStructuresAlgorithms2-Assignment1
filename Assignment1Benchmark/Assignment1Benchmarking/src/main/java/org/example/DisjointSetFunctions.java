package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class DisjointSetFunctions {

    public static int find(int[] a, int id) {
        return (a[id] <= -1 || a[id] == id) ? id : (a[id] = find(a,a[id]));
    }


    public static void union(int[] a, int p, int q) {
        //p is the one to be set to, q is changed to p
        //a[find(a, q)] = find(a, p);

        //union-by-size
        int rootp=find(a,p);
        int rootq=find(a,q);
        if (rootp==rootq) return;

        int biggerRoot=a[rootq]<a[rootp] ? rootq : rootp;
        int smallerRoot=biggerRoot==rootp ? rootq : rootp;
        int smallSize=a[smallerRoot]+1;

        a[smallerRoot]=biggerRoot;
        a[biggerRoot]+=smallSize;
    }

    public static void union2(int[] a, int p, int q) {
        //p is the one to be set to, q is changed to p
        a[find(a, q)] = find(a, p);

        //union-by-size
//        int rootp=find(a,p);
//        int rootq=find(a,q);
//        if (rootp==rootq) return;
//
//        int biggerRoot=a[rootq]<a[rootp] ? rootq : rootp;
//        int smallerRoot=biggerRoot==rootp ? rootq : rootp;
//        int smallSize=a[smallerRoot]+1;
//
//        a[smallerRoot]=biggerRoot;
//        a[biggerRoot]+=smallSize;
    }

    public static void assignRootValues(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                if (a[i] == i || a[i] < -1) {
                    a[i] = -2;
                } else {
                    if (a[find(a, i)] > -1)
                        a[find(a, i)] = -2;

                    a[find(a, i)] -= 1;
                }
            }
        }
    }

//    public static void assignRootValues2(int[] a) {
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] != -1) {
//                if (a[i] == i) {
//                    a[i] = -2;
//                } else {
//                    if (a[find(a, i)] > -1)
//                        a[find(a, i)] = -2;
//
//                    a[find(a, i)] -= 1;
//                }
//            }
//        }
//    }

    public static int countSets(HashMap<Integer, LinkedList<Integer>> hashMap) {
        return hashMap.keySet().size();
    }

    public static int displaySize(int[] a, HashMap<Integer, LinkedList<Integer>> hashMap, int initialIndex) {
        int index = find(a, initialIndex);

        if (hashMap.containsKey(index))
            return hashMap.get(index).size();

        int key = getKey(a, hashMap, index);
        if (key != index)
            return hashMap.get(getKey(a, hashMap, index)).size();

        return 1;
    }

    public static int getKey(int[] a, HashMap<Integer, LinkedList<Integer>> hashMap, int index) {
        for (Integer i : hashMap.keySet()) {
            if (hashMap.get(i).contains(index))
                return i;
        }
        return find(a, index);
    }

    public static int size(int[] a, int index) {
        return Math.abs(a[find(a, index)]+1);
    }

    public static void combineSets(int[] a, int width) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {

                if ((i+1 < a.length) && (a[i+1] != -1) && ((i+1)%width != 0)) {
                    union(a, i, i+1);
                }

                if ((i+width < a.length) && (a[i+width] != -1)) {
                    union(a, i, i+width);
                }

            }
        }

        //assignRootValues(a);
    }

    public static void combineSets2(int[] a, int width) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {

                if ((i+1 < a.length) && (a[i+1] != -1) && ((i+1)%width != 0)) {
                    union2(a, i, i+1);
                }

                if ((i+width < a.length) && (a[i+width] != -1)) {
                    union2(a, i, i+width);
                }

            }
        }

        assignRootValues(a);
    }

//    public static void combineNearbySets(int[] a, int width, int distance) {
//        HashMap<Integer, LinkedList<Integer>> hashMap = createHashMap(a);
//        //int[] coords = new int[hashMap.keySet().size()*2];
//
//        for (Integer i : hashMap.keySet()) {
//            int[] bounds = getBounds(hashMap, i, width);
//
//            //search within a distance*distance area of top left corner
//            for (int k = bounds[0]-(distance*(width+1)); k < bounds[0]+(distance*(width+1)); k++) {
//                if (k > 0 && k < a.length && a[k] != -1 && find(a, k) != find(a, i)) {
//                    union(a, i, k);
//                }
//                if (k > 0 && k < a.length && ((k%width)==(bounds[0]%width)+distance)) {
//                    k += width-(distance*2);
//                }
//            }
//        }
//
//        assignRootValues(a);
//    }

    public static HashMap<Integer, LinkedList<Integer>> createHashMap(int[] a) {
        HashMap<Integer, LinkedList<Integer>> hashMap = new HashMap<>();

        for (int i = 0; i < a.length; i++) {
            if (a[i] != -1) {
                if (hashMap.get(find(a, i)) == null) {
                    hashMap.put(find(a, i), new LinkedList<>());
                }
                hashMap.get(find(a, i)).add(i);
            }
        }

        //System.out.println(hashMap.values());
        return hashMap;
    }

    public static int[] getBounds(HashMap<Integer, LinkedList<Integer>> hashMap, int key, int width) {
        //[0] x1, [1] y1, [2] x2, [3] y2
        int[] bounds = new int[4];
        LinkedList<Integer> cluster = hashMap.get(key);
        if (cluster == null)
            return bounds;

        bounds[0] = width-1;
        bounds[1] = Integer.MAX_VALUE;

        for (Integer i : cluster) {
            //check for whether left/rightmost pixel so far
            if (i%width < bounds[0]%width) {
                bounds[0] = i;
            }
            if (i%width > bounds[2]%width) {
                bounds[2] = i;
            }

            //check for whether highest/lowest pixel so far
            if (i/width < bounds[1]/width) {
                bounds[1] = i;
            }
            if (i/width > bounds[3]/width) {
                bounds[3] = i;
            }
        }

        //dealing with cases where only 1 pixel wide/tall
        if (bounds[0] == Integer.MAX_VALUE)
            bounds[0] = bounds[2];

        if (bounds[1] == Integer.MAX_VALUE)
            bounds[1] = bounds[3];

        //System.out.println(bounds[0] + ", " + bounds[1] + " -> " + bounds[2] +", " + bounds[3]);
        return bounds;
    }

    public static int[] getDimensions(int[] a, HashMap<Integer, LinkedList<Integer>> hashMap, int index, int width) {
        int[] bounds = DisjointSetFunctions.getBounds(hashMap, find(a, index), width);
        return new int[]{(bounds[2]%width)-(bounds[0]%width)+1, ((bounds[3]-bounds[1])/width)+1};
    }

    public static void deleteSetsUnderSize2(int[] a, int minSize) {
        //allRoots.removeIf(e->size(a,e)<minSize);

        //TODO compare to list and delete from array
        LinkedList<Integer> roots = new LinkedList<>();

        for (int i = 0; i < a.length; i++) {
            if (i == find(a, i)) {
                roots.add(i);
            } else if (size(a, i) < minSize) {
                a[i] = -1;
            }
        }

        for (Integer i : roots) {
            if (size(a, i) < minSize)
                a[i] = -1;
        }
    }

    public static int[] deleteSetsUnderSize(int[] a, HashMap<Integer, LinkedList<Integer>> hashMap, int minSize, int maxSize, int minX, int minY, int maxX, int maxY, int width, int height) {
        Iterator<Integer> it = hashMap.keySet().iterator();
        while (it.hasNext()) {
            int i = it.next();
            int[] dimensions = DisjointSetFunctions.getDimensions(a, hashMap, i, width);
            if (size(a, i) < minSize || size(a, i) > maxSize || dimensions[0] < minX || dimensions[1] < minY || (dimensions[0] > maxX) || (dimensions[1] > maxY))
                it.remove();
        }

        a = hashMapToArray(hashMap, width, height);
        return a;
    }

    public static int coordsToIndex(int x, int y, int width) {
        return x+(y*width);
    }

    public static int[] indexToCoords(int index, int width) {
        //0, 0 is top left
        return new int[]{index%width, index/width};
    }

    public static double getDistance(int index1, int index2, int width) {
        //System.out.println(Math.abs(indexToCoords(index1, width)[0]-indexToCoords(index2, width)[0]) + ", " + Math.abs(indexToCoords(index1, width)[1]-indexToCoords(index2, width)[1]));
        return Math.hypot(Math.abs(indexToCoords(index1, width)[0]-indexToCoords(index2, width)[0]),
                Math.abs(indexToCoords(index1, width)[1]-indexToCoords(index2, width)[1]));
    }

    //TODO expensive, optimise
    public static void linkNearbySets(int[] a, HashMap<Integer, LinkedList<Integer>> hashMap, int distance, int width) {
        int[] coords = new int[hashMap.keySet().size()*4];
        int count = 0;

        for (int i : hashMap.keySet()) {
            int[] bounds = getBounds(hashMap, i, width);
            for (int j = 0; j < 4; j++) {
                coords[count] = bounds[j];
                count++;
            }
        }

        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords.length; j++) {
                if (hashMap.get(DisjointSetFunctions.find(a, coords[i])) != null && hashMap.get(DisjointSetFunctions.find(a, coords[j])) != null && getDistance(coords[i], coords[j], width) <= distance && !hashMap.get(DisjointSetFunctions.find(a, coords[i])).contains(coords[j])) {
                    //System.out.println(DisjointSetFunctions.find(a, coords[i]) + " is connected to " + DisjointSetFunctions.find(a, coords[j]));

                    LinkedList<Integer> set = hashMap.get(DisjointSetFunctions.find(a, coords[i]));
                    hashMap.get(DisjointSetFunctions.find(a, coords[j])).addAll(set);
                    hashMap.remove(DisjointSetFunctions.find(a, coords[i]));
                }
            }
        }

    }

    //TODO rework and fix this method - THIS METHOD IS BEING REPLACED BY combineHashMap
//    public static int[] combineArrays(int[] a, int[] b, int width) {
//        if (a.length != b.length) {
//            System.out.println("array sizes must be equal");
//            return a;
//        }
//
//        int[] c = new int[a.length];
//
//        for (int i = 0; i < b.length; i++) {
//            if (b[i] != -1) {
//                c[i] = i;
//            } else {
//                c[i] = -1;
//            }
//        }
//
//        for (int i = 0; i < a.length; i++) {
//            if (c[i] == -1 && a[i] != -1)
//                c[i] = i;
//        }
//
//        ImageUtilities.asciiImage(c, 300);
//        combineSets(c, width);
//        ImageUtilities.asciiImage(c, 300);
//
//        return c;
//    }

    public static HashMap<Integer, LinkedList<Integer>> combineHashMaps(HashMap<Integer, LinkedList<Integer>> hashMap1, HashMap<Integer, LinkedList<Integer>> hashMap2) {
        HashMap<Integer, LinkedList<Integer>> hashMap3 = new HashMap<>();
        hashMap3.putAll(hashMap1);
        hashMap3.putAll(hashMap2);

        return hashMap3;
    }

    //TODO optimise
    public static int[] hashMapToArray(HashMap<Integer, LinkedList<Integer>> hashMap, int width, int height) {
        int[] array = new int[width*height];

//        for (int i = 0; i < array.length; i++) {
//            if (hashMap.containsKey(DisjointSetFunctions.find(a, i))) {
//                array[i] = i;
//            } else {
//                array[i] = -1;
//            }
//        }

        for (int i = 0; i < array.length; i++)
            array[i] = -1;

        Iterator<LinkedList<Integer>> it = hashMap.values().iterator();
        while (it.hasNext()) {
            LinkedList<Integer> indexes = it.next();
            for (int i : indexes) {
                array[i] = -2;
            }
        }

        combineSets(array, width);
        return array;
    }

    //TODO fix
    public static void deleteUnlinkedSets(HashMap<Integer, LinkedList<Integer>> hashMapLinked, HashMap<Integer, LinkedList<Integer>> hashMap) {
        Iterator<LinkedList<Integer>> it = hashMap.values().iterator();
        while (it.hasNext()) {
            LinkedList<Integer> i = it.next();
            if (hashMapLinked.containsValue(i))
                it.remove();
        }

        Iterator<Integer> it2 = hashMapLinked.keySet().iterator();
        while (it2.hasNext()) {
            if (!hashMap.containsKey(it2.next()))
                it2.remove();
        }
    }

    public static void nameSets(String name, HashMap<Integer, LinkedList<Integer>> hashMap, HashMap<String, LinkedList<Integer>> names) {
        Iterator<Integer> it = hashMap.keySet().iterator();
        names.put(name, new LinkedList<>());
        while (it.hasNext()) {
            names.get(name).add(it.next());
        }
    }

    public static String getName(int[] a, int index, HashMap<String, LinkedList<Integer>> names) {
        for (String s : names.keySet()) {
            if (names.get(s).contains(find(a, index)))
                return s;
        }

        return "N/A";
    }

//    {
//     int size=34,height=2;
//     int root=0x80000000 | (height << 16) | size;
//
//    size=root & 0xFFFF;
//    height=(root >>> 16) & 0x7FFF;
//    height=(root & 0x7FFF0000) >>> 16;
//        0x000FFFF
//
//    }
}

