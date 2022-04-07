package TQS_HW1.HW1.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TrendIt")
public class APIController {
    // @Autowired
    // private ApiService service;

    // @Autowired
    // private TweetSearchResponseRepository tweet_rep;

    // @Autowired
    // private UserRepository user_rep;

    // @GetMapping("/all_data")
    // public List<Data> getData(@RequestParam(value = "data", required = false) String data) {
    //     return service.getAllData();
    // }

    // @GetMapping("/get_data/{id}")
    // public Data getTweetById(@PathVariable(value = "id" ) String id) {
    //     return service.getDataById(id);
    // }

    // @PostMapping("/insert")
    // public Tweet insertTweet(@Valid @RequestBody Data data){
    //     return service.saveData(data);
    // }
}