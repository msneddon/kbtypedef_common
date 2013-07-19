package us.kbase.typedobj.db;


import us.kbase.typedobj.core.validatorconfig.ValidationConfigurationFactory;
import us.kbase.typedobj.exceptions.NoSuchTypeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

/**
 * Implements a TypeDefinitionDB interface which uses a simple file directory
 * of JSON Schema documents (in the directory, each module is a folder named
 * ModuleName and each type is stored in the folder in the patter TypeName.json)
 * 
 * @author msneddon
 *
 */
public class SimpleTypeDefinitionDB extends TypeDefinitionDB {

	protected File dbFolder;
	
	
	/**
	 * Set up a new DB pointing to the specified db folder.  The contents
	 * should have a separate folder for each module named with the module
	 * name, and in each folder a set of .json files with json schema
	 * documents named the same as the type names.
	 * @param dbFolderPath
	 */
	public SimpleTypeDefinitionDB(String dbFolderPath) throws FileNotFoundException {
		// initialize the base class with a null json schema factory
		super(null);
		dbFolder = new File(dbFolderPath);
		if(!dbFolder.isDirectory()) {
			throw new FileNotFoundException("Cannot create SimpleTypeDefinitionDB from given db location:"+dbFolder.getPath());
		}
		// Create the custom json schema factory for KBase typed objects and use this
		ValidationConfiguration kbcfg = ValidationConfigurationFactory.buildKBaseWorkspaceConfiguration();
		this.jsonSchemaFactory = JsonSchemaFactory.newBuilder()
									.setValidationConfiguration(kbcfg)
									.freeze();
	}
	
	
	@Override
	public boolean isValidType(String moduleName, String typeName) {
		String location = dbFolder.getAbsolutePath() + File.separatorChar + moduleName + File.separatorChar + typeName + ".json";
		File schemaDocument = new File(location);
		if(schemaDocument.canRead()) {
			return true;
		}
		return false;
	}

	
	@Override
	public String getJsonSchemaDocument(String moduleName, String typeName)
			throws NoSuchTypeException {
		// first make sure that the json schema document can be found
		String location = dbFolder.getAbsolutePath() + File.separatorChar + moduleName + File.separatorChar + typeName + ".json";
		File schemaDocument = new File(location);
		if(!schemaDocument.canRead()) {
			throw new NoSuchTypeException("Unable to locate type: '"+moduleName+"."+typeName+"'");
		}
		
		// second, read the document and get on with it
		StringBuffer schema = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(schemaDocument));
			String line;
			while ( (line=br.readLine()) != null ) {
				schema.append(line); // note that we don't care about endlines, which are dropped
			}
			br.close();
		} catch (IOException e) {
			throw new NoSuchTypeException("Unable to read type def schema: '"+moduleName+"."+typeName+"'",e);
		}
		return schema.toString();
	}

}
