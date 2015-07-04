/* ============================================================================
 (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights to
use, copy, modify, merge,publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
============================================================================ */

package org.hp.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(200);
        
        PrintWriter writer = response.getWriter();
        
        String path = request.getServletPath();
        
        if(path != "/sendMessage") {
        	
	        
	
	        String uri = System.getenv("RABBITMQ_URL");
	        if( uri != null) {
	        
		        ConnectionFactory factory = new ConnectionFactory();
		        try {
		            factory.setUri(uri);
		        } catch (KeyManagementException e) {
		            e.printStackTrace();
		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		        } catch (URISyntaxException e) {
		            e.printStackTrace();
		        }
		        Connection connection = factory.newConnection();
		        Channel channel = connection.createChannel();
		
		        channel.queueDeclare("hello", false, false, false, null);
		        
		        String routingKey = "thekey";
		        String exchangeName = "exchange";
		
		        // Declare an exchange and bind it to the queue
		        channel.exchangeDeclare(exchangeName, "direct", true);
		        channel.queueBind("hello", exchangeName, routingKey);
		        
		        // Grab the message from the HTML form and publish it to the queue
		        String message = request.getParameter("message");
		        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		        response.sendRedirect("processMessage");
	        } else{ 
	        	writer.println("Please configure RABBITMQ_URL to valid format: amqp://{user}:{password}@{host}:{port}/%2f");
	        }
        } else {
        	response.sendError(404);
        }

        writer.close();
    }
}
