package us.kbase.typedobj.core;

import java.util.ArrayList;
import java.util.Iterator;

import us.kbase.typedobj.core.validatorconfig.WsIdRefValidationBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

public class ReportUtil {

	public static final int expectedNumberOfIds = 100;
	
	
	public static final ArrayList<String> getSimpleIdList(ProcessingReport report) {
		Iterator<ProcessingMessage> mssgs = report.iterator();
		ArrayList<String> ids = new ArrayList<String>(expectedNumberOfIds);
		while(mssgs.hasNext()) {
			ProcessingMessage m = mssgs.next();
			if( m.getMessage().compareTo(WsIdRefValidationBuilder.keyword) != 0 ) {
				continue;
			}
			ids.add(m.asJson().findValue("id").asText());
		}
		return ids;
	}
	
	
	public static final ArrayList<IdForValidation> getIdList(ProcessingReport report) {
		Iterator<ProcessingMessage> mssgs = report.iterator();
		ArrayList<IdForValidation> ids = new ArrayList<IdForValidation>(expectedNumberOfIds);
		while(mssgs.hasNext()) {
			ProcessingMessage m = mssgs.next();
			if( m.getMessage().compareTo(WsIdRefValidationBuilder.keyword) != 0 ) {
				continue;
			}
			String id = m.asJson().findValue("id").asText();
			JsonNode types = m.asJson().findValue("type");
			ArrayList<String> typesList = new ArrayList<String>(types.size());
			for(int k=0; k<types.size(); k++) {
				typesList.add(types.get(k).asText());
			}
			IdForValidation idForValidation = new IdForValidation(id,typesList);
			ids.add(idForValidation);
		}
		return ids;
	}
	
	/**
	 * Wrapper class that stores a workspace ID with a list of possible typed objects
	 * that the ID points to.
	 * @author msneddon
	 *
	 */
	public static final class IdForValidation {
		
		public final String id;
		public final ArrayList <String> types;
		
		public IdForValidation(String id, ArrayList<String> types) {
			this.id=id;
			this.types=types;
		}
		
		@Override
		public String toString() {
			return id + ":"+ types;
		}
	}
	
	
}
