
////Adam Added this when trying to do the payments API to Stripe
//Fake Public Key: pk_test_51JkGb9LiLrBml3YZDf9jKApvu5SDJnA3DMAnJYWNnNMLVAt2kygpBCM6GQtJXnILH0k68HMl8Uv86Mx7TaXt3wtS00AIvnnaWm
//Fake Private Key: sk_test_51JkGb9LiLrBml3YZ5PYRgJLaXWGpq6tB3Llmrzhjwgf3F3x5Z1sUe2vcJ3dzVxJ30HblSX3Sdnj4aEXsffVjGMeY00PgPTPOI6
package com.seefoodquickly.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seefoodquickly.services.StripeService;

@Controller
public class PaymentController {
    // Reading the value from the application.properties file - Adam
	@Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;


    @RequestMapping("/homeTest")

    public String home(Model model) {
        model.addAttribute("amount", 50 * 100); // In cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "testJSP/testPayment.jsp"; //Changed to test .JSP S.YEE
    }
    
    
    @Autowired
    private StripeService stripeService;

    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public String chargeCard(HttpServletRequest request, RedirectAttributes redAttr) throws Exception {
        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token, amount);    //Commented out...not sure if it'll actually charge a card! S.YEE
        System.out.println("Payment submitted by paymentcontroller");
//        redAttr.addFlashAttribute("message", "Thank you for your payment of $" + amount);   //Nice confirmation message S. YEE
//        return "redirect:/testpayment";
		return "redirect:/checkedout";
    }
    
}