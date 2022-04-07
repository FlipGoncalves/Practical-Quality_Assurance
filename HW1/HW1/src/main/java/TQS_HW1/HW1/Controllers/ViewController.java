package TQS_HW1.HW1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	// @Autowired
	// private UserRepository user_rep;
	// @Autowired
	// private ApiService service;

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
