package ch.verno.common.gate;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class VernoServerGate {

  private static ApplicationContext context;

  @Autowired
  public VernoServerGate(@Nonnull final ApplicationContext applicationContext) {
    context = applicationContext;
  }

  @Nonnull
  public static VernoServerGate getInstance() {
    return context.getBean(VernoServerGate.class);
  }

  @Nonnull
  public <T> T getService(@Nonnull final Class<T> serviceClass) {
    return context.getBean(serviceClass);
  }
}
