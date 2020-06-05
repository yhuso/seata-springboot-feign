package generator;

import com.google.common.base.CaseFormat;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;
import tk.mybatis.mapper.generator.MapperCommentGenerator;
import tk.mybatis.mapper.generator.MapperPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MyMapperPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		tableConfiguration.setInsertStatementEnabled(false);
		tableConfiguration.setSelectByPrimaryKeyStatementEnabled(false);
		tableConfiguration.setSelectByExampleStatementEnabled(false);
		tableConfiguration.setUpdateByExampleStatementEnabled(false);
		tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(false);
		tableConfiguration.setDeleteByExampleStatementEnabled(false);
		tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(false);
		tableConfiguration.setCountByExampleStatementEnabled(false);

		// String tableName = tableConfiguration.getTableName();
		// if (tableName.startsWith("h_")) {
		// 	tableName = tableName.replaceAll("h_", "");
		// }
		// String objectName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
		// objectName += "Entity";
		// tableConfiguration.setDomainObjectName(objectName);
		return true;
	}


}
