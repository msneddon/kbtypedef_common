package us.kbase.typedobj.drivers;


import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;

import us.kbase.typedobj.core.*;
import us.kbase.typedobj.core.validatorconfig.ValidationConfigurationFactory;
import us.kbase.typedobj.db.*;




public class SimpleTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		// Create a simple db
		TypeDefinitionDB db            = new SimpleTypeDefinitionDB("schema_db");
		
		// Create a simple validator
		TypedObjectValidator validator = new TypedObjectValidator(db);
		
		// Validate some stuff
		String instance1 = "{\"id\":\"f1\",\"name\":\"cheA\",\"start\":131,\"end\":582,\"other_ids\":[\"f8\",\"f99\"]," +
				"\"sizes\":{\"f33\":\"f33.2\",\"f44\":\"f44.1\"}}";
		ProcessingReport report = validator.validate(instance1, "ID", "Feature");
		System.out.println("INSTANCE 1\n"+report.toString());
		
		
	}
	
	

}
