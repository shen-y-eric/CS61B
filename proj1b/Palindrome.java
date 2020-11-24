public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> a = new ArrayDeque<Character>();
        char[] arr = word.toCharArray();
        for (char c : arr) {
            a.addLast(c);
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        if (word.length() < 2) {
            return true;
        }

        Deque<Character> wordDeque = wordToDeque(word);

        while (wordDeque.size() > 1) {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();

            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        if (word.length() < 2) {
            return true;
        }

        Deque<Character> wordDeque = wordToDeque(word);

        while (wordDeque.size() > 1) {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();

            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}

