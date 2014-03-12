package org.nigajuan.parser;


/**
 * NOTE: Based on work done by on the GSP standalone project (https://gsp.dev.java.net/)
 *
 * Utility class to reverse a char sequence.
 *
 * @author Troy Heninger
 */
class Reverse implements CharSequence {
    private CharSequence text;
    private int start, end, anchor;

    Reverse(CharSequence text) {
        this(text, 0, text.length());
    }

    Reverse(CharSequence text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
        anchor = end - 1;
    }

    public char charAt(int index) {
        return text.charAt(anchor - index);
    }

    public int length() {
        return end - start;
    }

    public CharSequence subSequence(int start, int end) {
        return new Reverse(text, anchor - end, anchor - start);
    }

    @Override
    public String toString() {
        int len = length();
        StringBuilder buf = new StringBuilder(len);
        for (int ix = anchor; ix >= start; ix--) {
            buf.append(text.charAt(ix));
        }
        return buf.toString();
    }
}
