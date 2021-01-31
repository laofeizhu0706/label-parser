import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 老肥猪
 * @since 2019/10/1
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OmsApplication.class)
public class DemoTest {

//    @Autowired
//    private VerS
    @Test
    public void test1() {
        StringBuilder sb = new StringBuilder(512);
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());


        sb.append(day).append(formattedDate).append(month);
        System.out.println(sb);
    }

}
