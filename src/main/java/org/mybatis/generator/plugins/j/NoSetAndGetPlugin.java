package org.mybatis.generator.plugins.j;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

import java.util.List;

/**
 * 去掉实体的setter getter方法
 * 并在mapper中继承tk.mybatis.mapper.common.BaseMapper
 * 标记 @J
 *
 * @author J
 **/
public class NoSetAndGetPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("javax.persistence.Id");
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addImportedType("lombok.ToString");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        //链式setter
        topLevelClass.addImportedType("lombok.experimental.Accessors");
        //序列化
        topLevelClass.addImportedType("java.io.Serializable");


        //该代码表示在生成class的时候，向topLevelClass添加一个注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@ToString");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@Accessors(chain = true)");

        //序列化
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));

        return super.modelBaseRecordClassGenerated(topLevelClass,
                introspectedTable);
    }

    /**
     * 该方法在生成每一个属性的getter方法时候调用，如果不想生成getter，直接返回false即可
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     * 该方法在生成每一个属性的setter方法时候调用，如果不想生成setter，直接返回false即可
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }


    /**
     * 生成@Id注解
     */
    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {
        List<IntrospectedColumn> columnList = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn column : columnList) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                return true;
            }
        }
        return true;
    }


    /**
     * dao.java
     * =========================
     */

    /**
     * 生成dao接口
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        Context tempC = this.context;
        String modelPackage = tempC.getJavaModelGeneratorConfiguration().getTargetPackage();
        FullyQualifiedJavaType type = interfaze.getType();
        //若只是使用该插件,则使用这个
//        String entityName = type.getShortName().replace("Mapper", "").trim();
        String entityName = type.getShortName().replace("Dao", "").trim();
        //引入基础类
        interfaze.addImportedType(new FullyQualifiedJavaType("tk.mybatis.mapper.common.BaseMapper"));
        //引入对应的实体类
        interfaze.addImportedType(new FullyQualifiedJavaType(modelPackage + "." + entityName));
        //继承基础类
        interfaze.addSuperInterface(new FullyQualifiedJavaType("BaseMapper<" + entityName + ">"));
        return true;
    }


    @Override
    public boolean clientBasicCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectManyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 生成mapper.xml
     * =========================
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        //生成mapper.xml
        return true;
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    /**
     * 不生成 where的sql
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }


    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }


    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }


    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapBaseColumnListElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapBlobColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapBlobColumnListElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapResultMapWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}
