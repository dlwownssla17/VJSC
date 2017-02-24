import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ErrorCodes {	
	
	
	public static final ImmutableMap<String, Integer> CODE_TO_ID;
	public static final ImmutableMap<Integer, String> ID_TO_CODE;
	
	private static final String INVALID_FUNCTION_CODE = "invalid_function_id";
	
	static {
		String[] codes = {
			INVALID_FUNCTION_CODE, // -1	
		};
		
		Builder<String, Integer> codeToIdBuilder = ImmutableMap.<String, Integer>builder();
		Builder<Integer, String> idToCodeBuilder = ImmutableMap.<Integer, String>builder();

		for (int i = 0; i < codes.length; i++) {
			codeToIdBuilder.put(codes[i], -1 * i);
			idToCodeBuilder.put(-1 * i, codes[i]);
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
	
	public static final String INVALID_FUNCTION_ID = "" + getID(INVALID_FUNCTION_CODE);
}
