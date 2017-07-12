package com.ymu.framework.utils.time;

/**
 * 时间样式.
 * @author mutou
 *
 */
public enum JDateTimeStyle {  
      
    YYYY_MM("YYYY-MM", false),  
    YYYY_MM_DD("YYYY-MM-DD", false),  
    YYYY_MM_DD_HH_MM("YYYY-MM-DD hh:mm", false),  
    YYYY_MM_DD_HH_MM_SS("YYYY-MM-DD hh:mm:ss", false),  
      
    YYYY_MM_EN("YYYY/MM", false),  
    YYYY_MM_DD_EN("YYYY/MM/DD", false),  
    YYYY_MM_DD_HH_MM_EN("YYYY/MM/DD hh:mm", false),  
    YYYY_MM_DD_HH_MM_SS_EN("YYYY/MM/DD hh:mm:ss", false),  
      
    YYYY_MM_CN("YYYY年MM月", false),  
    YYYY_MM_DD_CN("YYYY年MM月DD日", false),  
    YYYY_MM_DD_HH_MM_CN("YYYY年MM月DD日 hh:mm", false),  
    YYYY_MM_DD_HH_MM_SS_CN("YYYY年MM月DD日 hh:mm:ss", false),  
      
    HH_MM("hh:mm", true),  
    HH_MM_SS("hh:mm:ss", true),  
      
    MM_DD("MM-DD", true),  
    MM_DD_HH_MM("MM-DD hh:mm", true),  
    MM_DD_HH_MM_SS("MM-DD hh:mm:ss", true),  
      
    MM_DD_EN("MM/DD", true),  
    MM_DD_HH_MM_EN("MM/DD hh:mm", true),  
    MM_DD_HH_MM_SS_EN("MM/DD hh:mm:ss", true),  
      
    MM_DD_CN("MM月DD日", true),  
    MM_DD_HH_MM_CN("MM月DD日 hh:mm", true),  
    MM_DD_HH_MM_SS_CN("MM月DD日 hh:mm:ss", true);  
      
    private String value;  
      
    private boolean isShowOnly;  
      
    JDateTimeStyle(String value, boolean isShowOnly) {  
        this.value = value;  
        this.isShowOnly = isShowOnly;  
    }  
      
    public String getValue() {  
        return value;  
    }  
      
    public boolean isShowOnly() {  
        return isShowOnly;  
    }  
}  