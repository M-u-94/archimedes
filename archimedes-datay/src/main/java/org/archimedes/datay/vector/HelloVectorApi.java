//package org.archimedes.datay.vector;
//
//import jdk.incubator.vector.IntVector;
//import jdk.incubator.vector.Shapes;
//import jdk.incubator.vector.Vector;
//
//public class HelloVectorApi {
//    public static void main(String[] args) {
//        IntVector.IntSpecies<Shapes.S128Bit> species =
//                (IntVector.IntSpecies<Shapes.S128Bit>) Vector.speciesInstance(
//                        Integer.class, Shapes.S_128_BIT);
//        int val = 1;
//        IntVector<Shapes.S128Bit> hello = species.broadcast(val);
//        if (hello.sumAll() == val * species.length()) {
//            System.out.println("Hello Vector API!");
//        }
//    }
//}
//
