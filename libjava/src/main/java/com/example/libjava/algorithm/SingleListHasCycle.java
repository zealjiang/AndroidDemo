package com.example.libjava.algorithm;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleListHasCycle {

    public static void main(String[] args) {

        SingleListHasCycle obj = new SingleListHasCycle();
        String[] array = new String[]{"a", "b", "c", "d", "e"};
        Node head = obj.createCycleNode(array, true);
        //Node head = obj.createCycleNode(array, false);
        //obj.hasCycleMethod1(head);
        obj.hasCycleMethod2(head);
    }

    public Node createCycleNode(String[] array, boolean isCycle) {
        Node head = null;
        Node pre = null;
        Node cycleNode = null;
        int cycleIndex = -1;
        if (isCycle) {
            if (array.length > 1) {
                cycleIndex = new Random().nextInt(array.length -1);
            }
        }
        for (int i = 0; i < array.length; i++) {
            Node curNode = new Node(array[i], null);
            if (head == null) {
                head = curNode;
            } else {
                pre.next = curNode;
            }
            pre = curNode;

            if (cycleIndex > -1 && i == cycleIndex) {
                cycleNode = curNode;
            }
        }

        if (cycleNode != null) {
            pre.next = cycleNode;
            System.out.println("createCycleNode data ="+cycleNode.data);
        }

        return head;
    }

    class Node<T> {
        T data;
        Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * 通过快慢两个指针来发现环
     * 快的指针一次向后走两步，慢的指针一次向后走一步，除第一次快慢指针都指向头结点外，
     * 每走一次，判断快的指针或快指针的下一结点与慢的指针是否是同一结点，
     * 如果相同，说明发现环了，因为只有环才可能出现某一次快指针慢于或等于慢指针
     * @param head
     * @return
     */
    public boolean hasCycleMethod1(Node head) {
        Node fast = head;
        Node slow = head;
        int i = 0;
        Object data = null;
        boolean hasCycle = false;
        while(fast != null && slow != null) {
            //fast 前进两步
            if (fast.next == null || fast.next.next == null) {
                hasCycle = false;
                break;
            }

            if (i > 0 && (fast == slow || fast.next == slow)) {
                data = fast.data;
                hasCycle = true;
                break;
            }


            fast = fast.next.next;
            //slow 前进一步
            slow = slow.next;

            i++;
        }

        if (hasCycle) {
            System.out.println("hasCycleMethod1 find cycle, data = "+data+",  i ="+i+"时发现了环");
        } else {
            System.out.println("hasCycleMethod1 no cycle");
        }
        return hasCycle;
    }

    /**
     * 每个结点设置一个计数器，遍历一次，计算器加1，
     * 如果发现某次遍历某个结点的计数器为2，说明存在环
     * 如何给结点添加计数器，因为结点是提前声明好的，所以不能给Node结点添加新的属性，
     * 可以通过创建一个ArrayList<Node>,如果某个结点不存在，则添加，存在说明有环
     * @param head
     * @return
     */
    public boolean hasCycleMethod2(Node head) {
        ArrayList<Node> nodeList = new ArrayList();
        boolean hasCycle = false;
        while(head != null) {
            if (!nodeList.contains(head)) {
                nodeList.add(head);
            } else {
                //存在环
                hasCycle = true;
                break;
            }
            head = head.next;
        }

        if (hasCycle) {
            System.out.println("hasCycleMethod2 find cycle, data = "+head.data);
        } else {
            System.out.println("hasCycleMethod1 no cycle");
        }
        return hasCycle;
    }
}
