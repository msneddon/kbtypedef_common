package us.kbase.typedobj.db;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import us.kbase.typedobj.exceptions.*;

/**
 * This abstract base class provides methods/interface for retrieving a JSON Schema document and
 * JSON Schema validators for typed objects.
 * 
 * @author msneddon
 *
 */
public abstract class TypeDefinitionDB {

	
	/**
	 * This is the factory used to create a JsonSchema object from a Json Schema
	 * document stored in the DB.
	 */
	protected JsonSchemaFactory jsonSchemaFactory; 
	
	/**
	 * The Jackson ObjectMapper which can translate a raw Json Schema document to
	 * a JsonTree that can be handled by the validator.
	 */
	protected ObjectMapper mapper;
	
	
	/**
	 * Constructor which initializes this TypeDefinitionDB with a factory for
	 * generating schema objects from schema documents
	 * @param jsonSchemaFactory
	 */
	public TypeDefinitionDB(JsonSchemaFactory jsonSchemaFactory) {
		this.jsonSchemaFactory = jsonSchemaFactory;
		this.mapper            = new ObjectMapper();
	}
	
	/**
	 * Constructor which initializes this TypeDefinitionDB with a factory for
	 * generating schema objects from schema documents, and allows you to specify a
	 * custom Jackson object mapper for mapping the schema documents to JsonTree
	 * document, used before constructing the JsonSchema object.  
	 * @param jsonSchemaFactory
	 * @param mapper
	 */
	public TypeDefinitionDB(JsonSchemaFactory jsonSchemaFactory, ObjectMapper mapper) {
		this.jsonSchemaFactory = jsonSchemaFactory;
		this.mapper            = mapper;
	}
	
	/**
	 * Given a module and a type name, return true if the type exists, false otherwise
	 * @param moduleName
	 * @param typeName
	 * @return true if valid, false otherwise
	 */
	public abstract boolean isValidType(String moduleName, String typeName);
	
	
	/**
	 * Given a moduleName and typeName, return the JSON Schema document for the type.  No version
	 * number is specified, so the latest version on record is always the schema returned if the
	 * underlying Json Schema database supports versioned typed objects.
	 * @param moduleName
	 * @param typeName
	 * @return JSON Schema document as a String
	 * @throws NoSuchTypeException
	 */
	public abstract String getJsonSchemaDocument(String moduleName, String typeName) throws NoSuchTypeException;
	
	
	/*
	 * additional methods we should support, but are not ready yet:
	 * 
	 * public abstract boolean isValidType(String moduleName, String typeName, String/int version);
	 * public abstract String getJsonSchemaDocument(String moduleName, String typeName, String/int version);
	 * public abstract String/int getLatestVersion(String moduleName, String typeName);
	 * 
	 * public abstract ArrayList<String> getAllRegisteredModules();
	 * public abstract ArrayList<String> getAllRegisteredTypedObjects(String moduleName);
	 * 
	 */
	
	
	
	/**
	 * The default implementation for getting a JsonSchema object that can be used as a validator.  This
	 * method creates a new JsonSchema object on each call.  If we implement caching of the validator
	 * objects for better performance, this is the method we would need to extend.
	 * @param moduleName
	 * @param typeName
	 * @return
	 * @throws NoSuchTypeException
	 */
	public JsonSchema getJsonSchema(String moduleName, String typeName)
			throws NoSuchTypeException, BadJsonSchemaDocumentException
	{
		// first we retrieve the Json Schema document, this can throw a NoSuchTypeException
		String jsonSchemaDocument = this.getJsonSchemaDocument(moduleName, typeName);
		
		// next we parse the document into a JsonSchema using our jsonSchemaFactory
		// if there are problems, we catch and throw up an error indicating a bad document
		final JsonSchema schema;
		try {
			final JsonNode schemaRootNode = mapper.readTree(jsonSchemaDocument);
			schema     = jsonSchemaFactory.getJsonSchema(schemaRootNode);
		} catch (Exception e) {
			throw new BadJsonSchemaDocumentException("schema for typed object '"+moduleName+"."+typeName+"' was not a valid or readable JSON document",e);
		}
		return schema;
	}
	
	
}
