import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
/**
 *
 * @author Esraa Amr
 */
public class XmlToJson {

    public static String XML_To_JSON(String xml) {
        ArrayList<Node> arr = Xml_To_Arr(xml);
        Node node = Arr_To_Tree(arr);
        StringBuilder sb = new StringBuilder();
        Tree_To_Json(node, 0, sb);
        return "{\n" + sb + "\n}";
    }


    private static ArrayList<Node> Xml_To_Arr(String xml) {
        ArrayList<Node> arr = new ArrayList<>();
        for (int i = 0; i < xml.length(); i++) {
            if (xml.charAt(i) == ' ' || xml.charAt(i) == '\n')
                continue;
            StringBuilder sb = new StringBuilder();
            if (xml.charAt(i) == '<') {
                i++;
                boolean ct = false;
                if (xml.charAt(i) == '/') {
                    ct = true;
                    i++;
                }
                while (xml.charAt(i) != '>')
                    sb.append(xml.charAt(i++));
                Node n = new Node(ct ? NodeType.CLOSING_TAG : NodeType.OPENING_TAG, sb.toString().trim());
                arr.add(n);
            } else {
                while (xml.charAt(i) != '<')
                    sb.append(xml.charAt(i++));
                Node n = new Node(NodeType.DATA, sb.toString().trim());
                arr.add(n);
                i--;
            }
        }
        return arr;
    }
    private static Node Arr_To_Tree(ArrayList<Node> arr) {
        Stack<Node> stack = new Stack<>();
        for (Node curr : arr) {
            if (curr.type == NodeType.CLOSING_TAG) {
                Node temp = new Node(NodeType.ELEMENT, curr.data);
                Node top = stack.pop();
                while (top.type != NodeType.OPENING_TAG) {
                    temp.children.add(top);
                    top = stack.pop();
                }
                top = stack.isEmpty() ? null : stack.peek();
                if (!stack.isEmpty() && top.data.equals(curr.data)) {
                    top.type = NodeType.REPEATED_TAG;
                    if (temp.children.size() == 1)
                        top.children.add(temp.children.get(0));
                    else {
                        temp.data = "";
                        if (top.notFirst)
                            top.children.add(temp);
                        else {
                            Node ele = new Node(NodeType.ELEMENT, "");
                            ele.children = top.children;
                            top.children = new ArrayList<>();
                            top.children.add(ele);
                            top.children.add(temp);
                            top.notFirst = true;
                        }
                    }

                } else if (temp.children.size() == 1 && temp.children.get(0).type == NodeType.DATA) {
                    temp.type = NodeType.DATA_ELEMENT;
                    stack.push(temp);
                } else
                    stack.push(temp);

            } else {
                stack.push(curr);
            }

        }
        return stack.pop();
    }
    private static void Tree_To_Json(Node node, int tabCount, StringBuilder ans) {
        tabCount++;

        ans.append(repeat("    ", tabCount));
        if (node.type == NodeType.DATA) {
            ans.append("\"").append(node.data).append("\"");
            return;
        }


        if (node.type == NodeType.DATA_ELEMENT) {
            ans.append("\"").append(node.data).append("\": \"").append(node.children.get(0).data).append("\"");
            return;
        }

        if (!Objects.equals(node.data, "")) ans.append("\"").append(node.data).append("\": ");

        if (node.type == NodeType.REPEATED_TAG)
            ans.append("[\n");
        else
            ans.append("{\n");

        for (int i = 0; i < node.children.size(); i++) {
            Tree_To_Json(node.children.get(i), tabCount, ans);

            if (i < node.children.size() - 1)
                ans.append(", \n");
            else {
                ans.append('\n');
                ans.append(repeat("    ", tabCount));

                if (node.type == NodeType.REPEATED_TAG)
                    ans.append("]");
                else
                    ans.append("}");
            }
        }
    }
    private static String repeat(String string, int count){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<count; i++){
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
    private enum NodeType {OPENING_TAG, CLOSING_TAG, DATA, ELEMENT, REPEATED_TAG, DATA_ELEMENT}

    private static class Node {
        private NodeType type;
        private String data;
        private ArrayList<Node> children;
        private boolean notFirst = false;

        public Node(NodeType t, String d) {
            type = t;
            data = d;
            children = new ArrayList<>();
        }
    }
}
