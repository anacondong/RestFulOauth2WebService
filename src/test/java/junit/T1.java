package junit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class T1 {
	@Test
	public void test1() {
			System.out.println("JunitTest case :1");
	}

}
