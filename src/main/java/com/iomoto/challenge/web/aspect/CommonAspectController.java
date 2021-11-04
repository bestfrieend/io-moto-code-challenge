package com.iomoto.challenge.web.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iomoto.challenge.enums.ResultCodeEnum;
import com.iomoto.challenge.exceptions.BizException;
import com.iomoto.challenge.utils.ExUtil;
import com.iomoto.challenge.utils.ResultBuilderUtil;
import com.iomoto.challenge.web.response.CommonResponse;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wasif
 */
@Component
@Aspect
public class CommonAspectController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.iomoto.challenge.web.controllers..*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) {
        Object o = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = getIpAddress(request);
        String url = request.getRequestURL().toString();
        String reqParam = preHandle(pjp, request);
        String path = request.getRequestURI();
        String servletPath = request.getServletPath();
        Long userId = null;
        String errorMsg = "";
        try {
            logger.debug("Request source IP: [{}], request cookies: [{}], request URL: [{}], request parameter: [{}], request",
                    ipAddr, JSON.toJSONString(request.getCookies()), url, reqParam);

            logger.debug("RequestURI/path={}, ServletPath={}", path, servletPath);
            o = pjp.proceed(pjp.getArgs());
            // Can cast all objects to our specific common response type in order to avoid in consistency
            if (o instanceof CommonResponse) {
                return o;
            } else {
                return ResultBuilderUtil.buildSuccess(o);
            }
//            return o;
        } catch (BizException bizException) {
            errorMsg = bizException.getMessage();
            logger.error(ExUtil.getStackTrace(bizException));
            ResultCodeEnum resultCodeEnum = bizException.getResultCodeEnum();
            if (resultCodeEnum == null) {
                resultCodeEnum = ResultCodeEnum.INTERAL_SERVER_ERROR;
            }
            return ResultBuilderUtil.buildFailure(resultCodeEnum, bizException.getMessage());
        } catch (Exception e) {
            errorMsg = e.getMessage();
            logger.error(ExUtil.getStackTrace(e));
            return ResultBuilderUtil.buildFailure(ResultCodeEnum.INTERAL_SERVER_ERROR);
        } catch (Throwable e) {
            errorMsg = e.getMessage();
            e.printStackTrace();
            return ResultBuilderUtil.buildFailure(ResultCodeEnum.INTERAL_SERVER_ERROR);
        }
    }

    public String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
            }
        }
//        else if (ip.length() > 15) {
//            String[] ips = ip.split(",");
//            for (int index = 0; index < ips.length; index++) {
//                String strIp = ips[index];
//                if (!("unknown".equalsIgnoreCase(strIp))) {
//                    ip = strIp;
//                    break;
//                }
//            }
//        }
        return ip;
    }

    /**
     * Input data
     *
     * @param joinPoint
     * @param request
     * @return
     */
    private String preHandle(ProceedingJoinPoint joinPoint, HttpServletRequest request) {

        String reqParam = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
//        Annotation[] annotations = targetMethod.getAnnotations();
//        for (Annotation annotation : annotations) {
//            if (annotation.annotationType().equals(GetMapping.class)) {
//                Enumeration<String> parameterNames = request.getParameterNames();
//                JSONObject jsonObject = new JSONObject();
//                while (parameterNames.hasMoreElements()) {
//                    String key = parameterNames.nextElement();
//                    String[] parameterValues = request.getParameterValues(key);
//                    if (parameterValues.length == 1) {
//                        jsonObject.put(key, parameterValues[0]);
//                    } else {
//                        jsonObject.put(key, parameterValues);
//                    }
//                }
//                reqParam = jsonObject.toJSONString();
//                break;
//            }
//            if (annotation.annotationType().equals(PostMapping.class)) {
//                Object[] args = joinPoint.getArgs();
//                if (args != null && args.length > 0) {
//                    for (Object o : args) {
//                        if (!(o instanceof RequestFacade)) {
//                            reqParam += JSON.toJSONString(o);
//                        }
//                    }
//
//                }
//                break;
//            }
//        }
        return reqParam;
    }

    private Map<String, Integer> systemOperationLogsMap = null;
}