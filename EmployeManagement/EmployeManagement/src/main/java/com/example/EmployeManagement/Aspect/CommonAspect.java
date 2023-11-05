package com.example.EmployeManagement.Aspect;

import com.example.EmployeManagement.Utility.EmployeUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class CommonAspect {
    private final EmployeUtility employeUtility;

    @Autowired
    public CommonAspect(EmployeUtility employeUtility) {
        this.employeUtility = employeUtility;
    }
    @Before("execution(* com.example.EmployeManagement.Controller.*.*(..))")
    public void verifyAuthorization() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String[] publicEndpoints = {"/add", "/login"};
        boolean isPublicEndpoint = isPublicEndpoint(requestURI, publicEndpoints);
        if (!isPublicEndpoint) {
            String auth = request.getHeader("Authorization");
            employeUtility.verify(auth);
        }
    }
    private boolean isPublicEndpoint(String requestURI, String[] publicEndpoints) {
        for (String publicEndpoint : publicEndpoints) {
            if (requestURI.equals(publicEndpoint)) {
                return true;
            }
        }
        return false;
    }
}
