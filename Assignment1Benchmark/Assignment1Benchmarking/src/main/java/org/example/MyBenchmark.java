/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.example;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import javax.swing.*;
import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Measurement(
        iterations = 5
)
@Warmup(
        iterations = 5
)
@Fork(1)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MyBenchmark {

    private int[] a = new int[]{
            -1, -1, -1, -1, -1, -1, -5, 6,
            -1, -5, 9, -1, -1, -1, 6, 6,
            -1, 9, 9, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -2, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -7, 53, 53,
            -1, -1, -1, -1, -1, 53, 53, 53};
    private int[] b = new int[]{
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -4, -1, -1, -1, -1,
            -1, -1, 19, 19, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -2, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -7, 53, 53,
            -1, -1, -1, -1, -1, 53, 53, 53};
    HashMap<Integer, LinkedList<Integer>> hashMap1 = DisjointSetFunctions.createHashMap(a);
    HashMap<Integer, LinkedList<Integer>> hashMap2 = DisjointSetFunctions.createHashMap(b);
    HashMap<Integer, LinkedList<Integer>> hashMapLinked = DisjointSetFunctions.createHashMap(a);
    HashMap<String, LinkedList<Integer>> names = new HashMap<>();

    @Setup
    public void prepare() {
        DisjointSetFunctions.linkNearbySets(a, hashMapLinked, 2, 8);
        DisjointSetFunctions.nameSets("b", hashMap1, names);
    }

    public static void main(String[] args) throws RunnerException, IOException {
        Main.main(args);
    }

    @Benchmark
    public void combineSetsUnionBySize() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.

        DisjointSetFunctions.combineSets(a, 8);
    }

    @Benchmark
    public void combineSetsQuickUnion() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        DisjointSetFunctions.combineSets2(a, 8);
    }

    @Benchmark
    public void createHashMap() {
        HashMap<Integer, LinkedList<Integer>> hashMap = DisjointSetFunctions.createHashMap(a);
    }

    @Benchmark
    public void combineHashMaps() {
        HashMap<Integer, LinkedList<Integer>> hashMap = DisjointSetFunctions.combineHashMaps(hashMap1, hashMap2);
    }

    @Benchmark
    public void hashMapToArray() {
        int[] c = DisjointSetFunctions.hashMapToArray(hashMap1, 8, 8);
    }

    @Benchmark
    public void deleteSetsUnderSize() {
        int[] c = DisjointSetFunctions.deleteSetsUnderSize(a, hashMap1, 5, 1000, 0, 0, 1000, 1000, 8, 8);
    }

    @Benchmark
    public void deleteSetsUnderSize2() {
        DisjointSetFunctions.deleteSetsUnderSize2(a, 5);
    }

    @Benchmark
    public void getKey() {
        int c = DisjointSetFunctions.getKey(a, hashMap1, 63);
    }

    @Benchmark
    public void size() {
        int c = DisjointSetFunctions.size(a, 63);
    }

    @Benchmark
    public void displaySize() {
        int c = DisjointSetFunctions.displaySize(a, hashMap1, 63);
    }

    @Benchmark
    public void getBounds() {
        int[] c = DisjointSetFunctions.getBounds(hashMap1, 63, 8);
    }

    @Benchmark
    public void getDistance() {
        double c = DisjointSetFunctions.getDistance(6, 63, 8);
    }

    @Benchmark
    public void coordsToIndex() {
        int c = DisjointSetFunctions.coordsToIndex(7, 7, 8);
    }

    @Benchmark
    public void indexToCoords() {
        int[] c = DisjointSetFunctions.indexToCoords(63, 8);
    }

    @Benchmark
    public void getDimensions() {
        int[] c = DisjointSetFunctions.getDimensions(a, hashMap1, 63, 8);
    }

    @Benchmark
    public void linkNearbySets() {
        DisjointSetFunctions.linkNearbySets(a, hashMap1, 2, 8);
    }

    @Benchmark
    public void deleteUnlinkedSets() {
        DisjointSetFunctions.deleteUnlinkedSets(hashMapLinked, hashMap1);
    }

    @Benchmark
    public void nameSets() {
        DisjointSetFunctions.nameSets("a", hashMap1, names);
    }

    @Benchmark
    public void getName() {
        DisjointSetFunctions.getName(a, 63, names);
    }

    @Benchmark
    public void countSets() {
        DisjointSetFunctions.countSets(hashMap1);
    }

    @Benchmark
    public void analyseImage() {
        ImageUtilities.analyseImage(new Image("C:\\Users\\miche\\OneDrive - South East Technological University (Waterford Campus)\\Year 2, Semester 2\\Data Structures and Algorithms 2\\Assignments\\Assignment1Images\\Pills2.png"), 80, 0.3, 0.7, 5, 0.1, 0.1);
    }

}
