package java.util;

public class StringTokenizer implements Enumeration<Object> {

    private String input;
    private String delimiter;
    private boolean returnDelimiter;

    public StringTokenizer(String input, String delimiter, boolean returnDelimiter) {
        this.input = input;
        this.delimiter = delimiter;
        this.returnDelimiter = returnDelimiter;
    }

    @Override
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    public boolean hasMoreTokens() {
        return input.length() > 0;
    }

    private int getNextIndex() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < delimiter.length(); i++) {
            int charPos = input.indexOf(delimiter.charAt(i));
            if (charPos != -1) {
                min = Math.min(charPos, min);
            }
        }
        return (min != Integer.MAX_VALUE) ? min : -1;
    }

    @Override
    public Object nextElement() {
        return nextToken();
    }

    public String nextToken() {
        if (hasMoreTokens()) {
            int charPos;
            do {
                charPos = getNextIndex();
                if (charPos != 0) {
                    String output = input.substring(0, charPos);
                    input = input.substring(charPos);
                    return output;
                }
                if (returnDelimiter) {
                    String output = input.substring(0, 1);
                    input = input.substring(1);
                    return output;
                } else {
                    input = input.substring(1);
                }
            } while (charPos == 0);
            return "";
        } else {
            return null;
        }
    }

    public String nextToken(String delimit) {
        delimiter = delimit;
        return nextToken();
    }

    public int countTokens() {
        StringTokenizer temp = new StringTokenizer(input, delimiter, returnDelimiter);
        int count = 0;
        while (temp.hasMoreTokens()) {
            temp.nextToken();
            count++;
        }
        return count;
    }

}
