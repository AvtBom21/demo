package Final_Java.demo;

import Final_Java.demo.Data.Account;
import Final_Java.demo.Data.AccountRepo;
import Final_Java.demo.Data.Product;
import Final_Java.demo.Data.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;


@Controller
public class HomeController {
    @Autowired
    AccountRepo db;
    @Autowired
    ProductRepo productRepo;
    @Value("${upload.dir}") // Inject the upload directory path from application.properties
    private String uploadDir;
    @Value("${product.dir}") // Inject the upload directory path from application.properties
    private String productDir;
    @GetMapping("Admin")
    public String AccountManage(Model model){
        List<Account> list = new ArrayList<>();
        db.findAll().forEach(list::add);
        model.addAttribute("accounts", list);
        return "ManageAccount";
    }
    @PostMapping("Admin")
    public String Add_Account(@RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              @RequestParam("address") String address,
                              @RequestParam("image") MultipartFile image){

        if (image.isEmpty()) {
            db.save(new Account(email,generateRandomPassword(),"macdinh.jpg",name,phone,address,-1));
            return "redirect:/Admin"; // Redirect to the admin page without saving anything
        }
        Account account= new Account(email,generateRandomPassword(),"",name,phone,address,-1);
        try {
            // Ensure the upload directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            account.setImage(image.getOriginalFilename());
            // Save the file to the specified directory
            File outputFile = new File(uploadDir, image.getOriginalFilename());
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                fileOutputStream.write(image.getBytes());
            }
        db.save(account);
            // Redirect with a success message or other information
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return  "redirect:/Admin";
    }
    @GetMapping("Admin/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        db.deleteById(id);
        return  "redirect:/Admin";
    }
    @PostMapping("/Admin/delete")
    public String handleDeleteRequest(@RequestBody Map<String, List<String>> requestBody, Model model) {
        List<String> selectedIds = requestBody.get("selectedIds");

        if (selectedIds == null || selectedIds.isEmpty()) {
            return "redirect:/Admin";
        }

        // Convert the List<String> to an array if needed
        String[] idArray = selectedIds.toArray(new String[0]);

        // Process the selected IDs (e.g., delete from the database)
        for (String id : idArray) {
            int employeeId = Integer.parseInt(id.trim());
            db.deleteById(employeeId);
        }
        return "redirect:/Admin";
    }
    @PostMapping("/Admin/resendEmail")
    public String resendEmail(@RequestBody Map<String, List<String>> requestBody, Model model) {
        List<String> selectedIds = requestBody.get("selectedIds");

        if (selectedIds == null || selectedIds.isEmpty()) {
            return "redirect:/Admin";
        }

        // Convert the List<String> to an array if needed
        String[] idArray = selectedIds.toArray(new String[0]);

        // Process the selected IDs (e.g., delete from the database)
        for (String id : idArray) {
            int employeeId = Integer.parseInt(id.trim());
            db.deleteById(employeeId);
        }
        return "redirect:/Admin";
    }
    @PostMapping("/Admin/edit")
    public String EditAdmin(@RequestParam("id") String id,
                            @RequestParam("name") String name,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone,
                            @RequestParam("address") String address,
                            @RequestParam("image") MultipartFile image) {
        Account existingAccount = db.findById(Integer.parseInt(id)).orElse(null);
        if (existingAccount==null) {
            return "redirect:/Admin"; // Redirect to the admin page without saving anything
        }
        if(!name.equals("")){existingAccount.setName(name);}
        if(!email.equals("")){existingAccount.setEmail(email);}
        if(!address.equals("")){existingAccount.setAddress(address);}
        if(!phone.equals("")){existingAccount.setPhone(phone);}
        if(!image.isEmpty()){
            try {
                // Ensure the upload directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                existingAccount.setImage(image.getOriginalFilename());
                // Save the file to the specified directory
                File outputFile = new File(uploadDir, image.getOriginalFilename());
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                    fileOutputStream.write(image.getBytes());
                }
                // Redirect with a success message or other information
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        db.save(existingAccount);
        return  "redirect:/Admin";
    }
    @GetMapping("Product")
    public String GetProduct(Model model){
        List<Product> list = new ArrayList<>();
        productRepo.findAll().forEach(list::add);
        for (Product p:list
             ) {
            System.out.println(p);
        }
        model.addAttribute("products", list);
        return "ManageProducts";
    }
    @GetMapping("Product/delete/{id}")
    public String deleteProducts(@PathVariable int id, Model model){
        productRepo.deleteById(id);
        return  "redirect:/Product";
    }
    @PostMapping("/Product/delete")
    public String ProductDeleteRequest(@RequestBody Map<String, List<String>> requestBody, Model model) {
        List<String> selectedIds = requestBody.get("selectedIds");

        if (selectedIds == null || selectedIds.isEmpty()) {
            return "redirect:/Product";
        }

        // Convert the List<String> to an array if needed
        String[] idArray = selectedIds.toArray(new String[0]);

        // Process the selected IDs (e.g., delete from the database)
        for (String id : idArray) {
            int ProductId = Integer.parseInt(id.trim());
            productRepo.deleteById(ProductId);
        }
        return "redirect:/Product";
    }
    @PostMapping("Product")
    public String Add_Product(@RequestParam("addName") String name,
                              @RequestParam("addPrice") String price,
                              @RequestParam("addCategory") String category,
                              @RequestParam("addDescription") String description,
                              @RequestParam("addImage") MultipartFile image){

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        Product product= new Product("", name, Float.parseFloat(price),description,category, currentDate.format(formatter));
        try {
            // Ensure the upload directory exists
            File directory = new File(productDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            product.setImage(image.getOriginalFilename());
            // Save the file to the specified directory
            File outputFile = new File(productDir, image.getOriginalFilename());
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                fileOutputStream.write(image.getBytes());
            }
            productRepo.save(product);
            // Redirect with a success message or other information
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return  "redirect:/Product";
    }
    @PostMapping("/Product/edit")
    public String EditProduct(@RequestParam("id") String id,
                            @RequestParam("editName") String name,
                            @RequestParam("editPrice") String price,
                              @RequestParam("editDescription") String description,
                            @RequestParam("editCategory") String category,
                            @RequestParam("editImage") MultipartFile image) {
        Product existingProduct = productRepo.findById(Integer.parseInt(id)).orElse(null);
        if (existingProduct==null) {
            return "redirect:/Product"; // Redirect to the admin page without saving anything
        }
        if(!name.equals("")){existingProduct.setName(name);}
        if(!price.equals("")){existingProduct.setPrice(Float.parseFloat(price));}
        if(!description.equals("")){existingProduct.setDescription(description);}
        if(!category.equals("")){existingProduct.setCategory(category);}
        if(!image.isEmpty()){
            try {
                // Ensure the upload directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                existingProduct.setImage(image.getOriginalFilename());
                // Save the file to the specified directory
                File outputFile = new File(uploadDir, image.getOriginalFilename());
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                    fileOutputStream.write(image.getBytes());
                }
                // Redirect with a success message or other information
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
        productRepo.save(existingProduct);
        return  "redirect:/Product";
    }


    private String generateRandomPassword() {
        // Define the characters that can be used in the password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        // Define the password length
        int passwordLength = 12;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Generate random password using characters
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
