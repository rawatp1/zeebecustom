
package io.zeebe.receivepayment;

import java.time.Duration;
import java.util.Scanner;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.worker.JobWorker;

public class JobWorkerCreator {
	final static String broker = "127.0.0.1:26500";
	final static String jobType = "payment-service";

	public static void main(final String[] args) {
		final ZeebeClientBuilder builder = ZeebeClient.newClientBuilder().brokerContactPoint(broker).usePlaintext();

		try (final ZeebeClient client = builder.build()) {

			System.out.println("Opening job worker.");

			@SuppressWarnings("unused")
			final JobWorker workerRegistration = client.newWorker().jobType(jobType).handler(new JobHandlerImpl())
					.timeout(Duration.ofSeconds(10)).open();

			System.out.println("Job worker opened and receiving jobs.");

			waitUntilSystemInput("exit");
		}
	}

	private static void waitUntilSystemInput(String exitCode) {
		try (final Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNextLine()) {
				final String nextLine = scanner.nextLine();
				if (nextLine.contains(exitCode)) {
					return;
				}
			}
		}
	}
}