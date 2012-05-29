package esfw.testcase;

import java.util.List;

import model.business.ModelBusiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import esfw.core.framework.exception.BusinessException;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"config/applicationContext.xml"})  
public class ModeTest {

	
	    @Autowired  
	    private ModelBusiness modelBusiness;  
	    /** 
	     * {@link dao.UserDAO#selectByExample(model.User)} 的测试方法。 
	     * @throws BusinessException 
	     */  
	    @Test  
	    public final void testSelectByExample() throws BusinessException {  
	    	modelBusiness.load((long) 1);
//	        assertEquals(1,l.size());  
	    } 
	
	
	
}
