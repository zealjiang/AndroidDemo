package com.example.libjava.algorithm;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
 *
 * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
 *
 * 请注意，返回的 规范路径 必须遵循下述格式：
 *
 * 始终以斜杠 '/' 开头。
 * 两个目录名之间必须只有一个斜杠 '/' 。
 * 最后一个目录名（如果存在）不能 以 '/' 结尾。
 * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
 * 返回简化后得到的 规范路径 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：path = "/home/"
 * 输出："/home"
 * 解释：注意，最后一个目录名后面没有斜杠。
 * 示例 2：
 *
 * 输入：path = "/../"
 * 输出："/"
 * 解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
 * 示例 3：
 *
 * 输入：path = "/home//foo/"
 * 输出："/home/foo"
 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
 * 示例 4：
 *
 * 输入：path = "/a/./b/../../c/"
 * 输出："/c"
 *
 *
 * 方法一：栈
 *
 * 思路与算法
 *
 * 我们首先将给定的字符串 path\textit{path}path 根据 /\texttt{/}/ 分割成一个由若干字符串组成的列表，记为 names\textit{names}names。根据题目中规定的「规范路径的下述格式」，names\textit{names}names 中包含的字符串只能为以下几种：
 *
 * 空字符串。例如当出现多个连续的 /\texttt{/}/，就会分割出空字符串；
 *
 * 一个点 .\texttt{.}.；
 *
 * 两个点 ..\texttt{..}..；
 *
 * 只包含英文字母、数字或 _\texttt{\_}_ 的目录名。
 *
 * 对于「空字符串」以及「一个点」，我们实际上无需对它们进行处理，因为「空字符串」没有任何含义，而「一个点」表示当前目录本身，我们无需切换目录。
 *
 * 对于「两个点」或者「目录名」，我们则可以用一个栈来维护路径中的每一个目录名。当我们遇到「两个点」时，需要将目录切换到上一级，因此只要栈不为空，我们就弹出栈顶的目录。当我们遇到「目录名」时，就把它放入栈。
 *
 * 这样一来，我们只需要遍历 names\textit{names}names 中的每个字符串并进行上述操作即可。在所有的操作完成后，我们将从栈底到栈顶的字符串用 /\texttt{/}/ 进行连接，再在最前面加上 /\texttt{/}/ 表示根目录，就可以得到简化后的规范路径。
 *
 */
public class SimplifyPath {

    public static void main(String[] args) {
        Deque<String> stack = new ArrayDeque<String>();

        stack.offerLast("c");
        stack.offerLast("b");
        System.out.println("stack ="+stack);
        stack.offerFirst("n");
        //使用反射查看一下stack数组的元素
        try {
            //注意：在VM options里添加如下参数：
            //--add-opens=java.base/java.util=ALL-UNNAMED 或
            //--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.math=ALL-UNNAMED

            //Class cl = Class.forName("java.util.ArrayDeque");
            //Field field = cl.getDeclaredField("elements");
            Field field = ArrayDeque.class.getDeclaredField("elements");
            field.setAccessible(true);
            Object[] array = (Object[]) field.get(stack);
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i]+" ");
            }
            System.out.println();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("stack ="+stack);
        System.out.println("getLast ="+stack.getLast());
        System.out.println("pollFirst ="+stack.pollFirst());
        System.out.println("pollFirst ="+stack.pollFirst());

        System.out.println("--------------------------");
        String path = "/a/./b/../../c/";
        simplifyPath(path);
    }

    public static String simplifyPath(String path) {
        String[] names = path.split("/");
        Deque<String> stack = new ArrayDeque<String>();
        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (name.length() > 0 && !".".equals(name)) {
                stack.offerLast(name);
            }
        }
        StringBuffer ans = new StringBuffer();
        if (stack.isEmpty()) {
            ans.append('/');
        } else {
            while (!stack.isEmpty()) {
                ans.append('/');
                ans.append(stack.pollFirst());
            }
        }
        System.out.println(ans.toString());
        return ans.toString();
    }
}
