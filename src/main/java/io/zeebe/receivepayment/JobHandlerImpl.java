package io.zeebe.receivepayment;

import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;

class JobHandlerImpl implements JobHandler {
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
    	final Map<String, Object> variables = job.getVariablesAsMap();

		System.out.println("Process Payment for order: " + variables.get("orderId"));
		double price = 46.50;
		System.out.println("Collect money: $" + price);

		String PaymentStatus="OK";
		System.out.println("Collect PaymentStatus: " + PaymentStatus);
		

		final Map<String, Object> result = new HashMap<>();
		result.put("totalPrice", price);
		result.put("PaymentStatus",PaymentStatus);
		
		
      client.newCompleteCommand(job.getKey()).variables(result).send().join();
    }
}

