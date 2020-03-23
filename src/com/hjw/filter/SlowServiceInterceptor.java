package com.hjw.filter;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.reflect.MethodSignature;  
import org.springframework.stereotype.Component;

import com.hjw.util.TranLogTxt;

/**
 * 检测方法执行耗时的spring切面类
 * 使用@Aspect注解的类，Spring将会把它当作一个特殊的Bean（一个切面），也就是不对这个类本身进行动态代理
 * @author blinkfox
 * @date 2016-07-04
 */
@Aspect
@Component
public class SlowServiceInterceptor {
 
	private static final String logname = "slowService";
    private static final long TIMEOUT = 5 * 1000L;
 
    // service层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的
    public static final String POINT = "execution(* com.hjw.wst.service..*.*(..))";
    
    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around(POINT)
    public Object timeAround(ProceedingJoinPoint joinPoint) {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
 
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName() 
        	+Arrays.toString(signature.getParameterTypes()).replace("[", "(").replace("]", ")").replace("class ", "").replace("java.lang.","");
        //System.out.println(methodName);
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
        	e.printStackTrace();
        	TranLogTxt.liswriteEror_to_txt("logname", "统计某方法执行耗时环绕通知出错:"+e.getMessage());
        }
 
        // 获取执行的方法名
        long endTime = System.currentTimeMillis();
        
        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);
 
        return obj;
    }
 
    /**
     * 打印方法执行耗时的信息，如果超过了一定的时间，才打印
     * @param methodName
     * @param startTime
     * @param endTime
     */
    private void printExecTime(String methodName, long startTime, long endTime) {
        long diffTime = endTime - startTime;
        if (diffTime > TIMEOUT) {
        	TranLogTxt.liswriteEror_to_txt(logname, "耗时：" + (diffTime/1000) + "s-方法名："+methodName);
        }
    }
 
}