package com.estsoft.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureDaoExecutionTimeAspect {
	
	@Around("execution(* *..dao.*.*(..) )|| execution(* *..service.*.*(..) ) || execution(* *..controller.*.*(..) )")
	public Object around( ProceedingJoinPoint pjp ) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		String taskName = pjp.getTarget().getClass()+"."+pjp.getSignature().getName(); //+이후 부분은 메소드를 뱉어주는 부분.. 이라는데 뭔지 모르겠ㄴ
		
		
		Object result = pjp.proceed();
		
		
		stopWatch.stop();
		System.out.println("[Execution Time]"+taskName+" : "+stopWatch.getTotalTimeMillis( )+" millis");

		
		return result;
	}

}
