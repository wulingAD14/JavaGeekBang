package week8.hmilybank.bank1;


public class Bank1Controller {
    @Autowired
    AccountInfoService accountInfoService;
    @RequestMapping("/transfer")
    public String test(@RequestParam("amount") Double amount) {
        this.accountInfoService.updateAccountBalance("1", amount);
        return "cn/itcast/dtx/tccdemo/bank1" + amount;
    }

}

