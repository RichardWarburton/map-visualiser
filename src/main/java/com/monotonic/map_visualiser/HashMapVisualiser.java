package com.monotonic.map_visualiser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.stream.Stream;

public class HashMapVisualiser
{
    private static final Field thresholdField;
    private static final Field tableField;
    private static final Field nextField;
    private static final Field leftField;
    private static final Field rightField;

    static
    {
        Type node = new Type("java.util.HashMap$Node");
        Type treeNode = new Type("java.util.HashMap$TreeNode");
        Type hashMap = new Type(HashMap.class);

        thresholdField = hashMap.getField("threshold");
        tableField = hashMap.getField("table");
        nextField = node.getField("next");
        leftField = treeNode.getField("left");
        rightField = treeNode.getField("right");
    }

    private final Console console;

    public HashMapVisualiser(final Console console)
    {
        this.console = console;
    }

    public void visualise(final HashMap<?, ?> map)
    {
        Object[] table = (Object[]) Type.get(tableField, map);

        console.printf(
            "Size: %d, Resize: %s, Bin Count: %d%n",
            map.size(), Type.get(thresholdField, map), table.length);

        final IntSummaryStatistics collisions = Stream.of(table)
            .mapToInt(row ->
            {
                if (row == null)
                {
                    console.println("[]");
                    return 0;
                }

                switch (row.getClass().getSimpleName())
                {
                    case "TreeNode":
                        return visualiseTree(row);

                    case "Node":
                        return visualiseList(row);

                    default:
                        throw new IllegalArgumentException("Unknown type of row");
                }
            })
            .summaryStatistics();

        console.printf(
            "Collisions: Max: %d, Ave: %s, Total: %d%n",
            collisions.getMax(),
            collisions.getAverage(),
            collisions.getSum());
    }

    private int visualiseList(Object node)
    {
        final Object next = Type.get(nextField, node);
        if (next == null)
        {
            console.green();
            printNode(node);
            console.resetColour();
            return 0;
        }

        console.red();
        int index = 0;
        while (node != null)
        {
            console.indent(index);
            printNode(node);

            node = Type.get(nextField, node);
            index++;
        }
        console.resetColour();

        return index;
    }

    private int visualiseTree(final Object node)
    {
        console.yellow();
        final int collisions = visualiseSubTree(node, 0);
        console.resetColour();
        return collisions;
    }

    private int visualiseSubTree(final Object node, int index)
    {
        console.indent(index);
        printNode(node);

        return 1
             + visualiseBranch(node, index, leftField)
             + visualiseBranch(node, index, rightField);
    }

    private void printNode(final Object node)
    {
        console.printf("[%s]\n", node);
    }

    private int visualiseBranch(final Object node, final int index, final Field field)
    {
        Object branch = Type.get(field, node);
        if (branch != null)
        {
            return visualiseSubTree(branch, index + 1);
        }
        return 0;
    }
}
