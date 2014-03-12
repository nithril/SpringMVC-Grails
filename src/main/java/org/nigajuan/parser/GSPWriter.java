package org.nigajuan.parser;


import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;

import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.grails.web.pages.*;

/**
 * A PrintWriter used in the generation of GSP pages that allows printing to the target output
 * stream and maintains a record of the current line number during usage.
 *
 * @author Graeme Rocher
 */
public class GSPWriter extends PrintWriter {

    private int lineNumber = 1;
    private int[] lineNumbers = new int[1000];
    //private static final Pattern LINE_BREAK = Pattern.compile("\\r\\n|\\n|\\r");
    private GroovyPageParser parse;

    public GSPWriter(Writer out, GroovyPageParser parse) {
        super(out);
        this.parse = parse;
    }

    @Override
    public void write(char buf[], int off, int len) {
        super.write(buf, off, len);
    }

    public void printlnToResponse(String s) {
        printlnToResponse(GroovyPage.OUT_STATEMENT, s);
    }

    public void printlnToResponse(String outVarName, String s) {
        if (StringUtils.isEmpty(s)) {
            return;
        }

        parse.flushTagBuffering();
        super.print(outVarName);
        super.print(".print(");
        super.print(s);
        super.print(")");
        println();
    }

    public void printlnToBuffer(String s, int index) {
        if (s == null) s = "''";
        super.print("buf"+index+" << ");
        super.print(s);
        println();
    }

    @Override
    public void println() {
        addLineNumber();
        super.println();
    }

    private void addLineNumber() {
        if (lineNumber >= lineNumbers.length) {
            lineNumbers = (int[])resizeArray(lineNumbers, lineNumbers.length * 2);
        }
        else {
            lineNumbers[lineNumber - 1] = parse.getCurrentOutputLineNumber();
            lineNumber++;
        }
    }

    private Object resizeArray (Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class<?> elementType = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType,newSize);
        int preserveLength = Math.min(oldSize,newSize);
        if (preserveLength > 0) {
            System.arraycopy (oldArray,0,newArray,0,preserveLength);
        }
        return newArray;
    }

    public int getCurrentLineNumber() {
        return lineNumber;
    }

    public int[] getLineNumbers() {
        return lineNumbers;
    }
}
