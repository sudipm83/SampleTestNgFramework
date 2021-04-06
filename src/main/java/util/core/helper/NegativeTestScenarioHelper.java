package util.core.helper;

public class NegativeTestScenarioHelper {
	
	
		private static final NegativeTestScenarioHelper instance = new NegativeTestScenarioHelper();
		protected boolean negativeTestScenario = false;
		
		public static NegativeTestScenarioHelper getInstance() {
		return instance;
		}
		
		public boolean getNegativeScenario() {
		return this.negativeTestScenario;
		}
		
		
		public void setNegativeScenario(boolean negativeScenario) {
		this.negativeTestScenario = negativeScenario;
		}
		

}
