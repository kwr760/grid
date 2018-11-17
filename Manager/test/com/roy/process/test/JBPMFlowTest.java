package com.roy.process.test;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.junit.Test;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import krt.grid.api.frame.GridFactory;
import krt.grid.manager.jbpm.FinishHandler;
import krt.grid.manager.jbpm.GridTaskJobHandler;
import krt.grid.manager.jbpm.RetrieveHandler;
import krt.grid.manager.jbpm.TaskErrorJobHandler;

public class JBPMFlowTest {

	@Test
	public void jbpmFlowCreationTestGood() {
		
		String result = "Result not initalized.";
		
		try {
			Date startDate = new Date();

			GridFactory.setInstance(new SimulatedGrid(false));
			
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = CreateSession(kbase);
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
			
			ksession.getWorkItemManager().registerWorkItemHandler("Grid Task", new GridTaskJobHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Grid Task Error Notification", new TaskErrorJobHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Finalize Task", new FinishHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Retrieve Document", new RetrieveHandler());
			
			Map<String, Object> params;
			params = new HashMap<String, Object>();
			params.put("documentid", (long)273);
			ProcessInstance processInstance = ksession.startProcess("com.roy.TaskManager.SimpleGridJob", params);
			
			System.out.println("Finished creating process instances. ");
			
			int waitcount = 0;
			while (processInstance.getState() != ProcessInstance.STATE_COMPLETED)
			{
				Thread.sleep(500);
				System.out.println("Waiting on results: " + waitcount++);
			}
			
			logger.close();
			
			System.out.println("Start Time: " + startDate.toString() + " End Time: " + (new Date()).toString());
			
			result = ((WorkflowProcessInstance) processInstance).getVariable("result").toString();
			System.out.println("result: " + result);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void jbpmFlowCreationTestBad() {
		
		String result = "Result not initalized.";
		
		try {
			Date startDate = new Date();

			GridFactory.setInstance(new SimulatedGrid(false));
			
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = CreateSession(kbase);
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
			
			ksession.getWorkItemManager().registerWorkItemHandler("Grid Task", new GridTaskJobHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Grid Task Error Notification", new TaskErrorJobHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Finalize Task", new FinishHandler());
			ksession.getWorkItemManager().registerWorkItemHandler("Retrieve Document", new RetrieveHandler());
			
			Map<String, Object> params;
			params = new HashMap<String, Object>();
			params.put("documentid", (long)273);
			ProcessInstance processInstance = ksession.startProcess("com.roy.TaskManager.SimpleGridJob", params);
			
			System.out.println("Finished creating process instances. ");
			
			int waitcount = 0;
			while (processInstance.getState() != ProcessInstance.STATE_COMPLETED)
			{
				Thread.sleep(500);
				System.out.println("Waiting on results: " + waitcount++);
			}
			
			logger.close();
			
			System.out.println("Start Time: " + startDate.toString() + " End Time: " + (new Date()).toString());
			
			result = ((WorkflowProcessInstance) processInstance).getVariable("result").toString();
			System.out.println("result: " + result);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("SimpleGridJob.bpmn2"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
	
	private static StatefulKnowledgeSession CreateSession(KnowledgeBase kbase) {
		StatefulKnowledgeSession session= kbase.newStatefulKnowledgeSession();
		
		return session;
	}

	public class GridTaskTestWorkItemHandler implements WorkItemHandler {
		private String documentError;

		public GridTaskTestWorkItemHandler(String documentError) {
			super();

			this.documentError = documentError;
		}

		public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		}

		public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
			Integer documentID = (Integer) workItem.getParameter("documentid");
			String documentName = (String) workItem.getParameter("documentname");
			
			System.out.println("Execute work item called:  " + documentName + ": " + documentID);
			
			GridSimulationThread simulation = new GridSimulationThread("gridSimulationThread", documentError, workItem, manager);
			simulation.start();
			System.out.println("Done with work item execution:  waiting for task completion");
		}
	}

	public class GridTaskErrorTestWorkItemHandler implements WorkItemHandler {
		public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		}

		public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
			String docError = (String) workItem.getParameter("documentError");
			
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("result", "Simulated wo error call result: " + docError);
			
			manager.completeWorkItem(workItem.getId(), mp);
		}
	}

	public class GridSimulationThread extends Thread {
		WorkItem workitem;
		WorkItemManager manager;
		String documentError;
		
		public GridSimulationThread(String name, String documentError, WorkItem workitem, WorkItemManager manager) {
			  super(name);
			  this.workitem = workitem;
			  this.manager = manager;
			  this.documentError = documentError;
		}
		
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("result", "Simulated grid result");
			mp.put("documentError", documentError);
			
			manager.completeWorkItem(workitem.getId(), mp);
		}
	}
	
}
