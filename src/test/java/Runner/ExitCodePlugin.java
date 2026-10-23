package Runner;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;

public class ExitCodePlugin implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunFinished.class, event -> {
            int exitCode = event.getResult().getStatus().isOk() ? 0 : 1;
            System.out.println("Automation exit code: " + exitCode);
        });
    }
}
