package week8.hmilybank.bank1;

@FeignClient(value = "seata-demo-bank2", fallback = Bank2Fallback.class)
public interface Bank2Client {

    @GetMapping("/bank2/transfer")
    @Hmily
    Boolean transfer(@RequestParam("amount") Double amount);
}