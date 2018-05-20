package com.ymu.framework.web;

import org.apache.commons.lang.StringEscapeUtils;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEscapeEditor extends PropertyEditorSupport {

    private boolean escapeHTML = true;
    private boolean escapeJavaScript = true;
    private boolean escapeSQL = true;
    private boolean escapeStyle = true;
    
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String REGEX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式

    public StringEscapeEditor() {
    	super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
    	this(escapeHTML, escapeJavaScript, escapeSQL, false);
    }
    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL, boolean escapeStyle) {
    	super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
        this.escapeStyle = escapeStyle;
}

    public void setAsText(String text) {
            if (text == null) {
                    setValue(null);
            } else {
                    String value = text;
                    if (escapeHTML) {
                            value = escapeHtml(value);
                    }
                    if (escapeJavaScript) {
                            value = escapeJavaScript(value);
                    }
                    if (escapeSQL) {
                            value = StringEscapeUtils.escapeSql(value);
                    }
                    if (escapeStyle) {
                        value = escapeStyle(value);
                }
                    setValue(value);
            }
    }

    public String getAsText() {
            Object value = getValue();
            return value != null ? value.toString() : "";
    }
    
    private static String escapeHtml(String value) {
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(value);
        return m_html.replaceAll(""); // 过滤html标签
    }
    
    private static String escapeJavaScript(String value) {
        Pattern p_html = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(value);
        return m_html.replaceAll(""); // 过滤script标签
    }
    
    private static String escapeStyle(String value) {
        Pattern p_html = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(value);
        return m_html.replaceAll(""); // 过滤style标签
    }
}
