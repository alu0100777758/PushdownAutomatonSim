package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

public class PushdownTransition {
	// Entry Condition
	private State p_state;
	private String a_symbol;   // can be {epsilon}
	private String stack_symbol;
	private State transited_state;
	private String pushing_symbol;
	public PushdownTransition(State p, String a, String stack, State transited, String pushing ){
		this.p_state = p;
		this.a_symbol = a;
		this.stack_symbol = stack;
		this.transited_state = transited;
		this.pushing_symbol = pushing;
	}
}
