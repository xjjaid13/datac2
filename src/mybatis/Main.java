package mybatis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.util.DBHandle;
import com.util.DataHandle;
import com.util.FileHandle;

public class Main {

	public static void main(String[] args) throws IOException, DocumentException {
		String projectPath = System.getProperty("user.dir");
		String classPre = projectPath  + "/src";
		String configPath = projectPath + "/src/mybatis/config.json";
		JSONObject jsonObject = JSONObject.parseObject(FileHandle.readFile(configPath));
		
		String dbIp = jsonObject.getString("dbIp");
		String user = jsonObject.getString("user");
		String password = jsonObject.getString("password");
		String dbName = jsonObject.getString("dbName");
		JSONArray detail = jsonObject.getJSONArray("detail");
		
		JSONObject templateJSONObject = jsonObject.getJSONObject("template");
		String daoString = templateJSONObject.getString("daoString");
		
		String xmlString = templateJSONObject.getString("xmlString");
		String poString = templateJSONObject.getString("poString");
		
		DBHandle db = new DBHandle();
		db.openConnMysql(dbIp, user, password);
		if(!DataHandle.isNullOrEmpty(detail)){
			for(Object object : detail){
				
				JSONObject detailObject = (JSONObject)object;
				String tableName = detailObject.getString("tableName");
				String daoPath = detailObject.getString("daoPath");
				String xmlPath = detailObject.getString("xmlPath");
				String entityPath = detailObject.getString("entityPath");
				PackageInfo daoInfo = IbatisTemplateHandle.returnPackageInfo(daoPath, classPre);
				PackageInfo xmlInfo = IbatisTemplateHandle.returnPackageInfo(xmlPath, classPre);
				PackageInfo entityInfo = IbatisTemplateHandle.returnPackageInfo(entityPath, classPre);
				
				String priKey = "";
				String schSelectAllcolumn = "SELECT column_name,column_key from information_schema.columns WHERE table_schema = '"+dbName+"' AND table_name = '"+tableName+"';";
				String[][] schSelectAllColumn = db.executeQuery(schSelectAllcolumn);
				List<FieldVo> fieldList = new ArrayList<FieldVo>();
				for(int c = 0; schSelectAllColumn != null && c < schSelectAllColumn.length; c++){
					if("PRI".equals(schSelectAllColumn[c][1])){
						priKey = schSelectAllColumn[c][0];
					}
					FieldVo fieldVo = new FieldVo();
					fieldVo.setFieldName(schSelectAllColumn[c][0]);
					int fieldType = db.getColumnType(tableName,schSelectAllColumn[c][0] );
					fieldVo.setFieldType(fieldType);
					fieldList.add(fieldVo);
				}
				Map<String,String> replaceMap = new HashMap<String,String>();
				replaceMap.put("daoPathPre", daoInfo.getPackagePath());
				replaceMap.put("daoName", daoInfo.getFileName());
				replaceMap.put("daoImport", daoInfo.getImportPath());
				replaceMap.put("entityPathPre", entityInfo.getPackagePath());
				replaceMap.put("entityImport", entityInfo.getImportPath());
				
				IbatisTemplateHandle.daoAndEntityHandle(daoString, fieldList, daoInfo.getFilePath() + "/" + daoInfo.getFileNameWithSuffix(),tableName,replaceMap,false);
				IbatisTemplateHandle.daoAndEntityHandle(poString, fieldList, entityInfo.getFilePath() + "/" + entityInfo.getFileNameWithSuffix(),tableName,replaceMap,true);
				IbatisTemplateHandle.xmlHandle(templateJSONObject, xmlString, fieldList,  xmlInfo.getFilePath() + "/" + xmlInfo.getFileNameWithSuffix(), tableName, replaceMap,priKey);
				
			}
		}
		db.closeConn();
		System.out.println("done.");
	}
	
	static class IbatisTemplateHandle {
		
		public static PackageInfo returnPackageInfo(String classPath,String pre){
			int index = classPath.lastIndexOf(".");
			String suffix = classPath.substring(index);
			String temp = classPath.substring(0,index);
			
			PackageInfo packageInfo = new PackageInfo();
			packageInfo.setImportPath(temp);
			
			index = temp.lastIndexOf(".");
			packageInfo.setPackagePath(temp.substring(0,index));
			packageInfo.setFileName(temp.substring(index + 1));
			packageInfo.setFileNameWithSuffix(packageInfo.getFileName() + suffix);
			
			String path = classPath.substring(0,index).replaceAll("\\.", "/");
			packageInfo.setFilePath(pre + "/" + path);
			FileHandle.createPath(pre + "/" + path);
			return packageInfo;
		}
		
		public static void daoAndEntityHandle(String daoTemplate,List<FieldVo> fieldVoList,String path,String tableName,Map<String,String> replaceMap,boolean cover){
			StringBuilder result = new StringBuilder();
			String content = returnContent(daoTemplate, result, fieldVoList,tableName,"");
			content = transferOther(content, replaceMap);
			FileHandle.write(path, content ,cover);
		}
		
		@SuppressWarnings("unchecked")
		public static void xmlHandle(JSONObject templateJSONObject,String xmlString,List<FieldVo> fieldVoList,String path,String tableName,Map<String,String> replaceMap,String priKey) throws IOException, DocumentException{
			File file = new File(path);
			if(file.exists()){

				SAXReader reader = new SAXReader();  
				Document document = reader.read(new File(path));  
				Element rootEle = document.getRootElement();
				Element resultMapEle = rootEle.element("resultMap");
				//如果下面存在collection，则不更新
				Element collectionEle = resultMapEle.element("collection");
				if(collectionEle == null){
					resultMapEle.clearContent();
					resultMapEle.setText("#resultMapResult#");
				}
				List<Element> selectEles = rootEle.elements("select");
				for(Element e : selectEles){
					String id = e.attributeValue("id");
					if("select".equals(id)){
						e.clearContent();
						e.setText("#selectResult#");
					}else if("selectList".equals(id)){
						e.clearContent();
						e.setText("#selectListResult#");
					}else if("selectCount".equals(id)){
						e.clearContent();
						e.setText("#selectCountResult#");
					}else if("maxId".equals(id)){
						e.clearContent();
						e.setText("#maxIdResult#");
					}else{
						continue;
					}
				}
				List<Element> insertEles = rootEle.elements("insert");
				for(Element e : insertEles){
					String id = e.attributeValue("id");
					if("insert".equals(id)){
						e.clearContent();
						e.setText("#insertResult#");
					}else{
						continue;
					}
				}
				List<Element> updateEles = rootEle.elements("update");
				for(Element e : updateEles){
					String id = e.attributeValue("id");
					if("update".equals(id)){
						e.clearContent();
						e.setText("#updateResult#");
					}else{
						continue;
					}
				}
				List<Element> deleteEles = rootEle.elements("delete");
				for(Element e : deleteEles){
					String id = e.attributeValue("id");
					if("delete".equals(id)){
						e.clearContent();
						e.setText("#deleteResult#");
					}else{
						continue;
					}
				}
				List<Element> deleteByIdsEles = rootEle.elements("deleteByIds");
				for(Element e : deleteByIdsEles){
					String id = e.attributeValue("id");
					if("deleteByIds".equals(id)){
						e.clearContent();
						e.setText("#deleteByIdsResult#");
					}else{
						continue;
					}
				}
				XMLWriter writer = new XMLWriter(new FileOutputStream(new File(path)));  
		        writer.write(document);  
		        writer.close(); 
		        String templateContent = FileHandle.readFile(path);
		        
		        StringBuilder result = new StringBuilder();
		        
		        templateContent = templateContent.replace("#resultMapResult#", returnContent(templateJSONObject.getString("resultMapString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#selectResult#", returnContent(templateJSONObject.getString("selectString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#selectListResult#", returnContent(templateJSONObject.getString("selectListString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#selectCountResult#", returnContent(templateJSONObject.getString("selectCountString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#maxIdResult#", returnContent(templateJSONObject.getString("maxIdString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#insertResult#", returnContent(templateJSONObject.getString("insertString"),result,fieldVoList,tableName,priKey));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#updateResult#", returnContent(templateJSONObject.getString("updateString"),result,fieldVoList,tableName,priKey));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#deleteResult#", returnContent(templateJSONObject.getString("deleteString"),result,fieldVoList,tableName,""));
		        result.delete(0, result.length());
		        templateContent = templateContent.replace("#deleteByIdsResult#", returnContent(templateJSONObject.getString("deleteByIdsString"),result,fieldVoList,tableName,""));
		        FileHandle.write(path, templateContent);
		        formatXMLFile(path);
			}else{
				StringBuilder result = new StringBuilder();
				xmlString = returnContent(xmlString, result, fieldVoList,tableName,"");
				xmlString = transferOther(xmlString, replaceMap);
				FileHandle.write(path, xmlString);
				formatXMLFile(path);
			}
			
		}
		
		
		/** 解析栏目模板文件 */
		public static String returnContent(String content,StringBuilder result,List<FieldVo> fieldVoList,String tableName,String priKey){
			int start = content.indexOf("{for}");
			int end = content.indexOf("{endfor}");
			if(start == -1){
				result.append(transferContent(content,tableName,"",""));
				return result.toString();
			}
			result.append(transferContent(content.substring(0,start),tableName,"",""));
			String forContent = content.substring(start+5,end);
			
			int i = 0;
			for(FieldVo fieldVo : fieldVoList){
				i++;
				if(fieldVo.getFieldName().equals(priKey)){
					continue;
				}
				String forTemContent  = transferContent(forContent,tableName,fieldVo.getFieldName(),fieldVo.getFieldString());
				if(forTemContent.endsWith(",") && i == fieldVoList.size()){
					forTemContent = forTemContent.substring(0,forTemContent.length() - 1);
				}
				result.append(forTemContent);
				
			}
			
			return returnContent(content.substring(end + 8),result,fieldVoList,tableName,priKey);
		}
		
		/** 解析表模板文件 */
		public static String returnTableContent(String content,StringBuilder result,List<FieldVo> fieldList){
			int start = content.indexOf("{for}");
			int end = content.indexOf("{endfor}");
			if(start == -1){
				result.append(transferContent(content,"","",""));
				return result.toString();
			}
			result.append(transferContent(content.substring(0,start),"","",""));
			String forContent = content.substring(start+5,end);
			
			int i = 0;
			for(FieldVo fieldVo : fieldList){
				i++;
				String forTemContent = transferContent(forContent,fieldVo.getFieldName(),"","");
				if(forTemContent.endsWith(",") && i == fieldList.size()){
					forTemContent = forTemContent.substring(0,forTemContent.length() - 1);
				}
				result.append(forTemContent);
			}
			return returnTableContent(content.substring(end + 8),result,fieldList);
		}
		
		/** 解析特定字符串 */
		public static String transferContent(String content,String tableName,String field,String type){
			if(!"".equals(tableName)){
				content = content.replaceAll("\\{TableName\\}", firstUppercase(transfer(tableName)));
				content = content.replaceAll("\\{tableName\\}", transfer(tableName));
				content = content.replaceAll("\\{tablename\\}", tableName);
			}
			if(!"".equals(field)){
				content = content.replaceAll("\\{columnName\\}", transfer(field));
				content = content.replaceAll("\\{columnname\\}", field);
				content = content.replaceAll("\\{#columnname\\}", "#{"+field+"}");
//				if("String".equals(type)){
//					content = content.replaceAll("\\{#columnname\\}", "'#{"+field+"}'");
//				}else{
//					content = content.replaceAll("\\{#columnname\\}", "#{"+field+"}");
//				}
				content = content.replaceAll("\\{Columnname\\}", firstUppercase(field));
				content = content.replaceAll("\\{ColumnName\\}", firstUppercase(transfer(field)));
				content = content.replaceAll("\\{type\\}", type);
			}
			return content;
		}
		
		public static String transferOther(String content,Map<String,String> replaceMap){
			Set<String> keySet = replaceMap.keySet();
			for(String key : keySet){
				String map = replaceMap.get(key);
				if(!DataHandle.isNullOrEmpty(map)){
					content = content.replaceAll("\\{"+key+"\\}", map);
				}
			}
//			content = content.replaceAll("\\{daoPathPre\\}", replaceMap.get("daoPathPre"));
//			content = content.replaceAll("\\{entityImport\\}", replaceMap.get("entityImport"));
			return content;
		}
		
		/**
		 * 将数据库写法转成驼峰写法
		 * */
		public static String transfer(String content){
			int index = content.indexOf("_");
			if(index != -1){
				String temStr = content.substring(index+1,index+2);
				content = content.replaceAll("_"+temStr, temStr.toUpperCase());
			}else{
				return content;
			}
			return transfer(content);
		}
		
		/**
		 * 首字母大写
		 * */
		public static String firstUppercase(String content){
			return content.substring(0,1).toUpperCase() + content.substring(1);
		}

		public static int formatXMLFile(String filename) {
			int returnValue = 0;
			try {
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(new File(filename));
				XMLWriter output = null;
				/** 格式化输出,类型IE浏览一样 */
				OutputFormat format = OutputFormat.createPrettyPrint();
				/** 指定XML字符集编码 */
				format.setEncoding("GBK");
				output = new XMLWriter(new FileWriter(new File(filename)), format);
				output.write(document);
				output.close();
				/** 执行成功,需返回1 */
				returnValue = 1;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return returnValue;
		}
	}
	
	public static class PackageInfo{
		//绝对路径
		private String filePath;
		//文件（不带后缀）
		private String fileName;
		//文件名（带后缀）
		private String fileNameWithSuffix;
		//文件作为包名
		private String packagePath;
		//导入的名称
		private String importPath;
		
		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getPackagePath() {
			return packagePath;
		}

		public void setPackagePath(String packagePath) {
			this.packagePath = packagePath;
		}

		public String getFileNameWithSuffix() {
			return fileNameWithSuffix;
		}

		public void setFileNameWithSuffix(String fileNameWithSuffix) {
			this.fileNameWithSuffix = fileNameWithSuffix;
		}

		public String getImportPath() {
			return importPath;
		}

		public void setImportPath(String importPath) {
			this.importPath = importPath;
		}

	}
	
	public static class FieldVo{
		
		private String fieldName;
		
		private int fieldType;
		
		private String fieldString;
		
		private int isQuate;

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public int getFieldType() {
			return fieldType;
		}

		public void setFieldType(int fieldType) {
			this.fieldType = fieldType;
			if(fieldType == Types.TINYINT || fieldType == Types.SMALLINT || fieldType == Types.INTEGER
			|| fieldType == Types.DOUBLE || fieldType == Types.NUMERIC){
				setFieldString("Integer");
			}else{
				setFieldString("String");
			}
		}

		public String getFieldString() {
			return fieldString;
		}

		public void setFieldString(String fieldString) {
			this.fieldString = fieldString;
		}

		public int getIsQuate() {
			return isQuate;
		}

		public void setIsQuate(int isQuate) {
			this.isQuate = isQuate;
		}
		
	}
	
}
