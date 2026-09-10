package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.client.CartClient;
import com.sdetcommerce.api.client.OrderClient;
import com.sdetcommerce.api.client.PaymentClient;
import com.sdetcommerce.api.client.ProductClient;
import com.sdetcommerce.api.utils.AuthHelper;

import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected String token;
    protected String adminToken;

    protected ProductClient productClient;
    protected CartClient cartClient;
    protected OrderClient orderClient;
    protected PaymentClient paymentClient;

    @BeforeClass
    public void setUp() {

        token = AuthHelper.getAuthToken();
        adminToken = AuthHelper.getAdminAuthToken();

        productClient = new ProductClient();
        cartClient = new CartClient();
        orderClient = new OrderClient();
        paymentClient = new PaymentClient();
    }
}