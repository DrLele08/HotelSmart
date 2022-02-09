import it.hotel.controller.api.CheckPrenoSospesa;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckPrenoSospesaTest extends Mockito
{
    private CheckPrenoSospesa controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private JSONObject object;
    @Before
    public void setUp()
    {
        controller=Mockito.spy(new CheckPrenoSospesa());
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        object=new JSONObject();
    }

    @Test
    public void testNonContieneParametro() throws Exception
    {
        ServletOutputStream mockOutput = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(mockOutput);
        controller.doGet(request,response);
        object.put("Ris",0);
        object.put("Mess","Inserisci tutti i parametri");
        verify(mockOutput).print(object.toString());
    }
}
