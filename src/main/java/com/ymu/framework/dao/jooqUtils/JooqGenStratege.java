package com.ymu.framework.dao.jooqUtils;

import org.jooq.tools.StringUtils;
import org.jooq.util.CatalogDefinition;
import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.jooq.util.SchemaDefinition;

/**
 * jooq代码生成策略。
 */
public class JooqGenStratege extends DefaultGeneratorStrategy {


    public String getJavaClassName(Definition definition, Mode mode) {
        String name = getFixedJavaClassName(definition);
        return name != null ? name : this.getJavaClassName0(definition, mode);
    }

    private String getJavaClassName0(Definition definition, Mode mode) {
        StringBuilder result = new StringBuilder();
//        result.insert(0,"Q");//类名以”Q"开头
        result.append(StringUtils.toCamelCase(definition.getOutputName().replace(' ', '_').replace('-', '_').replace('.', '_')));
        if (mode == Mode.RECORD) {
            result.append("Record");
        } else if (mode == Mode.DAO) {
            result.append("JqDao");
        } else if (mode == Mode.POJO) {
            result.append("JqVo");
        } else if (mode == Mode.INTERFACE) {
            result.insert(0, "I");
        }

        return result.toString();
    }

    final String getFixedJavaClassName(Definition definition) {
        if (definition instanceof CatalogDefinition && ((CatalogDefinition) definition).isDefaultCatalog()) {
            return "DefaultCatalog";
        } else {
            return definition instanceof SchemaDefinition && ((SchemaDefinition) definition).isDefaultSchema() ? "DefaultSchema" : null;
        }
    }
}
