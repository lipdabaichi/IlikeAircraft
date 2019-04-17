package com.jt.aop;


import com.jt.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.CaCheAnnotation;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Component	//当前切面类交给spring容器管理
@Aspect		//定义切面类
public class CacheAspect {
	//如果你服务器需要时注入该对象,适用于工具类
//	@Autowired(required = false)   //类似于延迟加载
//	private ShardedJedis jedisCluster;

	//3.实现redis哨兵高可用 操作
//	@Autowired(required = false)
//	private RedisService jedisCluster;

	@Autowired(required = false)
	private JedisCluster jedisCluster;

	/**
	 * 1.@Around(value="@annotation(cacheAnno)")
	 * 	功能最为强大的环绕通知
	 * 2.其余四大通知类型能否控制目标方法执行????
	 *  @Before("切入点表达式")  //目标方法执行 之前
		@AfterReturning("切入点表达式") //目标方法执行 之后 执行
		@AfterThrowing("表达式")   //目标方法执行 异常时 执行
	    @After("表达式")			//程序马上执行结束了,才执行
	    一般用来记录程序的执行过程(日志) logger
	 * 
	 * 匹配所有的注解CaCheAnnotation
	 * @param joinPoint
	 * @param cacheAnno
	 * @return
	 */
	@Around(value="@annotation(cacheAnno)")
	//@Around("@annotation(注解类型)")
	/*@Before("切入点表达式")
	@AfterReturning("切入点表达式")
	@AfterThrowing("表达式")
	@After("表达式")*/
	public Object around(ProceedingJoinPoint joinPoint,CaCheAnnotation cacheAnno) {
		//获取key值
		String key = getKey(joinPoint,cacheAnno);
		Object object = getObject(joinPoint,cacheAnno,key);
		return object;
	}

	private Object getObject(ProceedingJoinPoint joinPoint,
							 CaCheAnnotation cacheAnno,String key) {
		Object object = null;
		//判断用户缓存 读/更新
		switch (cacheAnno.cacheType()) {
		case FIND:
			object = findCache(joinPoint,cacheAnno,key);
			break;
		case UPDATE:
			object = updateCache(joinPoint,key);
			break;
		}
		return object;
	}

	//类名.方法名.参数名称.值
	private String getKey(ProceedingJoinPoint joinPoint,CaCheAnnotation cacheAnno) {
		String targetClassName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		//转化为方法对象
		MethodSignature methodSignature = (MethodSignature)
				joinPoint.getSignature();
		String[] paramNames = methodSignature.getParameterNames();
		String paramName = paramNames[cacheAnno.index()];
		Object arg = joinPoint.getArgs()[cacheAnno.index()];
		String key = targetClassName+"."+methodName+"."+paramName+"."+arg;
		return key;
	}
	
	private Object findCache(ProceedingJoinPoint joinPoint, CaCheAnnotation cacheAnno, String key) {
		Object object = null;
		String result = jedisCluster.get(key);	//根据key查询缓存信息
		try {
			if(StringUtils.isEmpty(result)) { //表示缓存中没有数据,则执行业务层方法
				object = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(object);	//将数据转化为json串
				jedisCluster.set(key, json);	//将数据保存到redis中
				System.out.println("AOP查询业务层获取信息");
			}else {		//表示缓存中有数据,可以直接返回数据
				MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
				//获取目标方法返回值类型
				Class<?> returnClass = methodSignature.getReturnType();
				System.out.println("1111");
				System.out.println(">>>>"+returnClass);
				object = ObjectMapperUtil.toObject(result, returnClass);
				System.out.println("AOP查询缓存");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return object;
	}
	
	private Object updateCache(ProceedingJoinPoint joinPoint, String key) {
		Object object = null;
		try {
			jedisCluster.del(key);	//如果是更新操作,则直接删除缓存
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
}
