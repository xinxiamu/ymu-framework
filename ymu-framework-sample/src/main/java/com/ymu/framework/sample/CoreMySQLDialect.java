package com.ymu.framework.sample;

import org.hibernate.dialect.MySQL57InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.IntegerType;
import org.hibernate.type.TimestampType;

/**
 * Created by lujijiang on 2017/6/3.
 */
public class CoreMySQLDialect extends MySQL57InnoDBDialect {
    public CoreMySQLDialect() {
        super();
        this.registerFunction("now",new SQLFunctionTemplate(new TimestampType(), "now()"));
        this.registerFunction("timestampdiff",new SQLFunctionTemplate(new IntegerType(), "timestampdiff(?1,?2,?3)"));
        this.registerKeyword("minute");
    }
}
