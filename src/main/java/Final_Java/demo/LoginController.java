package Final_Java.demo;

import Final_Java.demo.Data.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    private Account account;
    @Autowired
    AccountRepo db;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    DetailTranRepo detailTranRepo;
    @Autowired
    CustomerRepo customerRepo;
    private List<DetailTran> detailTrans = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @GetMapping("/Checkout")
    public String Checkout(Model model){
        int count=0;
        double Total=0;
        for (DetailTran detail:detailTrans
             ) {
            count = count + detail.getQuantity();
            Total = Total + detail.getTotalPrice();
        }
        model.addAttribute("count",count);
        model.addAttribute("Total",Total);
        return "Checkout";
    }
    @PostMapping("/Checkout")
    public String Checkout_add(@RequestParam("firstname") String firstname,
                               @RequestParam("phone") String phone,
                               @RequestParam("address") String address,
                               @RequestParam("cardname") String cardname,
                               @RequestParam("cardnumber") String cardnumber,
                               @RequestParam("expmonth") String expmonth,
                               @RequestParam("expyear") String expyear,
                               @RequestParam("cvv") String cvv,
                               @RequestParam(name = "sameadr", defaultValue = "false") boolean sameAddress){

        Customer customer = new Customer(firstname, address, phone);
        for (DetailTran detail:detailTrans
             ) {
            detail.setPhone(phone);
            detail.setStatus("Đang chuẩn bị hàng");
            if(!cardname.isEmpty()){
                detail.setTypePayment("Card");
            } else if (sameAddress) {
                detail.setTypePayment("Cash");
            }else{
                detail.setTypePayment("QR-Code");
            }
            detailTranRepo.save(detail);
        }
        customerRepo.save(customer);
        return "Checkout";
    }
    @GetMapping("/")
    public String get_index(Model model){
        if(account!=null){
            return "redirect:/Dashboard";
        }
        return "IndexWithNoLogin";
    }
    @PostMapping("/")
    public String login(@RequestParam String uname,
                        @RequestParam String psw,
                        @RequestParam(required = false) boolean remember,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {

        // Validate user credentials (you may need to modify this based on your authentication mechanism)
        account = db.findByEmail(uname+"@gmail.com");
        if (account!=null) {
            // Authentication successful
            if(account.getPass().equals(psw)){
                // Set the remember-me cookie if the user has checked the checkbox
                if (remember) {
                    Cookie cookie = new Cookie("remember-me", "true");
                    cookie.setMaxAge(30 * 24 * 60 * 60); // Cookie will last for 30 days
                    response.addCookie(cookie);
                }
                if (account.getAdmin()==-1) {
                    account = null;
                    model.addAttribute("error", "Account is not agree");
                }
                return "redirect:/Dashboard";
            } else{
                account=null;
                model.addAttribute("error","Password is wrong");
            }

        } else {
            model.addAttribute("error","Account is not valid");
        }
        return "redirect:/";
    }
    @GetMapping("/Dashboard")
    public String Dashboard(Model model){
        if(account==null){
            return "redirect:/";
        }
        model.addAttribute("account",account);
        List<DetailTran> detailTranList = new ArrayList<>();
        detailTranRepo.findAll().forEach(detailTranList::add);
        model.addAttribute("products", products);
        model.addAttribute("detailTranList", detailTranList);
        return "Dashboard";
    }

    @PostMapping("/Dashboard")
    public String Dashboard_login(){

        return "Dashboard";
    }
    @GetMapping("/Cart")
    public String Cart(Model model){
        model.addAttribute("products",products);
        model.addAttribute("detailTrans", detailTrans);
        int count=0;
        double Total=0;
        for (DetailTran detail:detailTrans
        ) {
            count = count + detail.getQuantity();
            Total = Total + detail.getTotalPrice();
        }
        model.addAttribute("count", count);
        model.addAttribute("Total", Total);
        return "Cart";
    }
    @GetMapping("/Home")
    public String Home(Model model){
        if(products.isEmpty()){
            productRepo.findAll().forEach(products::add);
        }
        if(account!=null){
            model.addAttribute("account", account);
        }
        model.addAttribute("products", products);
        return "Home";
    }
    @GetMapping("/Dashboard/logout")
    public String Logout(Model model){
        account =null;
        return Dashboard(model);
    }
    @GetMapping("/buy")
    public String Buy(@RequestParam("id") int productId, Model model){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DetailTran detailTran = new DetailTran("",productId,1,1,
                1,1,"đang mua","", currentDate.format(formatter));
        Product product1 = productRepo.findById(productId).orElse(null);
        detailTran.setUnitPrice(product1.getPrice());
        detailTran.setTotalPrice((double) detailTran.getQuantity()*detailTran.getUnitPrice());
        detailTrans.add(detailTran);
        model.addAttribute("products",products);
        model.addAttribute("detailTrans", detailTrans);
        int count=0;
        double Total=0;
        for (DetailTran detail:detailTrans
             ) {
            count = count + detail.getQuantity();
            Total = Total + (double)detail.getQuantity()*detail.getUnitPrice();
        }
        model.addAttribute("count", count);
        model.addAttribute("Total", Total);
        detailTranRepo.save(detailTran);
        return "Cart";
    }
    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("detailTranId") String detailTranId,
                                                 @RequestParam("quantity") String newQuantity) {
        // Retrieve the DetailTran entity by detailTranId
        DetailTran detailTran = detailTranRepo.findById(Integer.parseInt(detailTranId)).orElse(null);
        DetailTran detailTran1 = detailTrans.get(detailTrans.indexOf(detailTran));
        detailTran1.setQuantity(Integer.parseInt(newQuantity));
        detailTran.setQuantity(Integer.parseInt(newQuantity));
        detailTran.setTotalPrice(Double.parseDouble(newQuantity)*detailTran.getUnitPrice());
        detailTran1.setTotalPrice(Double.parseDouble(newQuantity)*detailTran.getUnitPrice());
        detailTranRepo.save(detailTran);
        return "redirect:/Cart";
    }
    @GetMapping("/addshop")
    public String addshop(@RequestParam("id") int productId, Model model){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DetailTran detailTran = new DetailTran("",productId,1,1,
                1,1,"đang mua","", currentDate.format(formatter));
        Product product1 = productRepo.findById(productId).orElse(null);
        detailTran.setUnitPrice(product1.getPrice());
        detailTran.setTotalPrice((double) detailTran.getQuantity()*detailTran.getUnitPrice());
        detailTrans.add(detailTran);
        model.addAttribute("addsuccess","Thêm sản phẩm thành công");
        detailTranRepo.save(detailTran);
        return "redirect:/Home";
    }
    @GetMapping("historyorder")
    public String showhistory(@RequestParam("id") int detailid, Model model){
        DetailTran detailTran = detailTranRepo.findById(detailid).orElse(null);
        Product product = productRepo.findById(detailTran.getProductID()).orElse(null);
        model.addAttribute("detailTran", detailTran);
        model.addAttribute("product", product);
        return "Historyorder";
    }
}
