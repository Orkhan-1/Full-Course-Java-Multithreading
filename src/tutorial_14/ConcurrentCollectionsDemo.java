package src.tutorial_14;

import java.util.concurrent.*;
import java.util.*;

public class ConcurrentCollectionsDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new ConcurrentHashMap<>();
        List<String> list = new CopyOnWriteArrayList<>();

        Runnable writer = () -> {
            for (int i = 0; i < 5; i++) {
                map.put("key" + i, "value" + i);
                list.add("item" + i);
            }
        };

        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(writer);

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Map: " + map);
        System.out.println("List: " + list);
    }
}
/*
Concurrent Collections in Java

Map: ConcurrentHashMap, ConcurrentSkipListMap
Set: ConcurrentSkipListSet, CopyOnWriteArraySet, ConcurrentHashMap.newKeySet()
Queue: ConcurrentLinkedQueue, ConcurrentLinkedDeque, LinkedBlockingQueue,
       LinkedBlockingDeque, ArrayBlockingQueue, PriorityBlockingQueue,
       DelayQueue, SynchronousQueue, LinkedTransferQueue
List: CopyOnWriteArrayList

 */