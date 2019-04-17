package com.jt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//当前类是一个切面
@Component
//@Aspect
public class HelloAspect {
	
	/**
	 *  切入点表达式的写法:
	 * 	1.within("包名.类名")   粗粒度
	 * 	匹配的类中的全部方法都会执行通知方法.
	 * 	
	 *  2.excution("返回值类型 包名.类名.方法名(参数列表)")
	 *  语法:
	 *   execution(* com.jt.controller.*.add*(..))
	 *   匹配返回值类型为任意, 指定包下(一级包)下的add开头的方法
	 *   execution(* com.jt.controller..*.*(..))
	 * 匹配返回值类型任意. controller包下的任意包下的任意方法
	 * 	
	 */
	//1.定义前台通知
	//@Before("within(com.jt.controller.HelloController)")
	public void before(JoinPoint joinPoint) {
		System.out.println("我是一个前置通知!!!!!!");
		String methodName = 
				joinPoint.getSignature().getName();
		System.out.println("获取目标方法名称:"+methodName);
		Class<?> target = 
				joinPoint.getTarget().getClass();
		System.out.println("获取目标方法类型:"+target);
	}
	
	/**
	 * 环绕通知配置如下
	 * 	1.必须写切入点表达式
	 * 	2.必须添加ProceedingJoinPoint,必须位于参数第一位
	 * 	3.必须配置返回值 并且为object类型
	 * @param joinPoint
	 * @return
	 */
	//定义环绕通知  环绕通知必须加object
	//@Around("within(com.jt.controller.HelloController)")
	//@Around("execution(* com.jt.controller.HelloController.add*(..))")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object object = null;
		try {
			System.out.println("环绕通知执行开始!!!!!");
			//没有对方法放行
			object = joinPoint.proceed();	//让目标方法执行
			System.out.println("环绕通知执行结束");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 后台通知 要求controller中的全部方法都执行后置通知
	 * execution(* com.jt.controller.*.add*(..)
	 * 								一级目录
	 */
	
	//@AfterReturning("execution(* com.jt.controller..*.*(..))")
	public void afterReturn(JoinPoint point) {
		
		System.out.println("我是后置通知");
	}
	
	//异常通知
	//@AfterThrowing(value="execution(* com.jt.controller..*.*(..))",throwing="able")
	public void afterThrow(JoinPoint point,Throwable able) {
		
		System.out.println(able.getMessage());
	}
	
	//AOp最终通知
	@After("execution(* com.jt.controller..*.*(..))")
	public void after() {
		
		System.out.println("我是最终通知");
	}
	
}
