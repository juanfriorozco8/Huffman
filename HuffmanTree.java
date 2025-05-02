import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, String> codes = new HashMap<>();
    private Map<Character, Integer> frequencies = new HashMap<>();

    public void build(String text) {
        frequencies.clear();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (var entry : frequencies.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.add(new HuffmanNode('\0', left.frequency + right.frequency, left, right));
        }

        root = pq.poll();
        generateCodes(root, "");
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node.isLeaf()) {
            codes.put(node.character, code);
        } else {
            generateCodes(node.left, code + "0");
            generateCodes(node.right, code + "1");
        }
    }

    public String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }

    public String decode(String encoded) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode current = root;
        for (char bit : encoded.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            if (current.isLeaf()) {
                decoded.append(current.character);
                current = root;
            }
        }
        return decoded.toString();
    }

    public Map<Character, Integer> getFrequencies() {
        return frequencies;
    }
}

