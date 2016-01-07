package ar.com.osdepym.template.web.interceptors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CacheInterceptor implements Interceptor {

private static final long serialVersionUID = 1L;

public void destroy() {}

public void init() {}

public String intercept(ActionInvocation invocation) throws Exception {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "-1");
    return invocation.invoke();
}




}
