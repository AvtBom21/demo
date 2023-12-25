package Final_Java.demo;

import Final_Java.demo.Data.Account;
import Final_Java.demo.Data.AccountRepo;
import Final_Java.demo.Data.Product;
import Final_Java.demo.Data.ProductRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    private Account account;
    @Autowired
    AccountRepo db;
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/Checkout")
    public String Checkout(){
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
        return "Dashboard";
    }

    @PostMapping("/Dashboard")
    public String Dashboard_login(){

        return "Dashboard";
    }
    @GetMapping("/Cart")
    public String Cart(){
        return "Cart";
    }
    @GetMapping("/Home")
    public String Home(Model model){
        List<Product> products = new ArrayList<>();
        productRepo.findAll().forEach(products::add);
        model.addAttribute("products", products);
        return "Home";
    }
    @GetMapping("/Dashboard/logout")
    public String Logout(Model model){
        account =null;
        return Dashboard(model);
    }
    @GetMapping("/buy")
    public String Buy(Model model){
        account =null;
        return Dashboard(model);
    }
    @GetMapping("/addshop")
    public String addshop(Model model){
        account =null;
        return Dashboard(model);
    }
}
