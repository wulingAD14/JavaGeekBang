package week7rw;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource {
    @Override
    protected Object determineCurrentLookupKey(){
        return CustomerContextHolder.getCustomerType();
    }

}
