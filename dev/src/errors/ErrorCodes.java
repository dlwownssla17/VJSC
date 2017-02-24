package errors;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ErrorCodes {	
	
	
	public static final ImmutableMap<String, Integer> CODE_TO_ID;
	public static final ImmutableMap<Integer, String> ID_TO_CODE;
	
	// Do not make these public!!
	private static final String CODE_INVALID_FUNCTION = "invalid_function_id";
	private static final String CODE_CREATE_SCHEDULE_ITEM_NO_SCHEDULE = "create_schedule_item_no_schedule";
	private static final String CODE_CREATE_SCHEDULE_ITEM_INVALID_TIME = "create_schedule_item_invalid_time";
	
	static {
		String[] codes = {			
			CODE_INVALID_FUNCTION, // id = -1	
			CODE_CREATE_SCHEDULE_ITEM_NO_SCHEDULE, // -2
			CODE_CREATE_SCHEDULE_ITEM_INVALID_TIME, // -3
		};
		
		Builder<String, Integer> codeToIdBuilder = ImmutableMap.<String, Integer>builder();
		Builder<Integer, String> idToCodeBuilder = ImmutableMap.<Integer, String>builder();

		for (int i = 0; i < codes.length; i++) {
			codeToIdBuilder.put(codes[i], -1 * (i+1));
			idToCodeBuilder.put(-1 * (i+1), codes[i]);
		}
		
		CODE_TO_ID = codeToIdBuilder.build();
		ID_TO_CODE = idToCodeBuilder.build();
	}
	
	public static int getID(String code) {
		return CODE_TO_ID.get(code);
	}
	
	public static String getCode(String ID) {
		return ID_TO_CODE.get(ID);
	}
	
	// make sure these are publically available!
	public static final String ID_INVALID_FUNCTION = "" + getID(CODE_INVALID_FUNCTION);
	public static final String ID_CREATE_SCHEDULE_ITEM_NO_SCHEDULE = "" + getID(CODE_CREATE_SCHEDULE_ITEM_NO_SCHEDULE);
	public static final String ID_CREATE_SCHEDULE_ITEM_INVALID_TIME = "" + getID(CODE_CREATE_SCHEDULE_ITEM_INVALID_TIME);

}
