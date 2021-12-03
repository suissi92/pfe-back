package app.com.cms2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

  
  
  /*@Before("execution(public *.*.*.Controller.*(..))")
  public void log(JoinPoint joinPoint) {
    System.out.printf("Appel de %s avec %d paramètres%n",
                      joinPoint.toShortString(),
                      joinPoint.getArgs().length);
  }*/
  
	@Before(value = "execution(* *.*.*.*.*controller.*(..)) ")
	public void beforeAdvice(JoinPoint joinPoint ) {
		System.out.println("Before method:" + joinPoint.getSignature());

		
	}
  
  

}