package week7rw;

public class CustomerContextHolder {
    public static final String DATA_SOURCE_A = "dataSource1";
    public static final String DATA_SOURCE_B = "dataSource2";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>(String)();

    public static void setCustomerType(String customerType){
        contextHolder.set(customerType);
    }
    public static String getCustomerType(){
        return contextHolder.get();
    }
    public static void clearCustomerType(){
        contextHolder.remove();
    }
}
