package week7rw;

import com.sun.org.slf4j.internal.LoggerFactory;
import org.omg.PortableInterceptor.Interceptor;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class DatabaseInterceptor implements Interceptor {

    private static final Logger = LoggerFactory.getLogger(DatabaseInterceptor.class);
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable{
        try{
            Method method = methodInvocation.getMethod();
            if( method != null && method.getAnnotations(ReadOnlyDataSource.class) != null){
                DataSourceProvider.setDataSource(AvailableDataSources.READ);
            }
            return methodInvocation.proceed();
        }finally {
            DataSourceProvider.clearDataSource();
        }
    }

}
