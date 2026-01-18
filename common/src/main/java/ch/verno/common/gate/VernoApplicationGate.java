package ch.verno.common.gate;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class VernoApplicationGate {

  private static ApplicationContext context;

  @Autowired
  public VernoApplicationGate(@Nonnull final ApplicationContext applicationContext) {
    context = applicationContext;
  }

  @Nonnull
  public static VernoApplicationGate getInstance() {
    return context.getBean(VernoApplicationGate.class);
  }

  @Nonnull
  public <T> T getService(@Nonnull final Class<T> serviceClass) {
    return context.getBean(serviceClass);
  }
}
