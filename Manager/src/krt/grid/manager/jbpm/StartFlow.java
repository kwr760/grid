package krt.grid.manager.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class StartFlow {
	
	private KnowledgeBase kbase;
	private StatefulKnowledgeSession ksession;
	@SuppressWarnings("unused")
	private KnowledgeRuntimeLogger logger;
	
	public StartFlow() {
		kbase = readKnowledgeBase();
		ksession = CreateSession(kbase);
		logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "DocumentFlow", 1000);
		
		ksession.getWorkItemManager().registerWorkItemHandler("Grid Task", new GridTaskJobHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Grid Task Error Notification", new TaskErrorJobHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Finalize Task", new FinishHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Retrieve Document", new RetrieveHandler());
	}
	
	public void RunFlow() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		ksession.startProcess("com.roy.TaskManager.SimpleGridJob", params);
		
		System.out.println("Finished creating process instances. ");
	}
	
	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}


	private KnowledgeBase readKnowledgeBase() {
		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource("SimpleGridJob.bpmn2"), ResourceType.BPMN2);
			return kbuilder.newKnowledgeBase();
		}
		catch (Exception ex) {
			
		}
		return null;
	}
	
	private StatefulKnowledgeSession CreateSession(KnowledgeBase kbase) {
		StatefulKnowledgeSession session= kbase.newStatefulKnowledgeSession();
		
		return session;
	}

}
