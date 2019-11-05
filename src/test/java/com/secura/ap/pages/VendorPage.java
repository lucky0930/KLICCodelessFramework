package com.secura.ap.pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.test.automation.repository.OR_Common;

import java.io.IOException;
import java.util.Map;

public class VendorPage extends OR_Common {

    public void createVendor(Map<String, String> row, ExtentTest test) throws IOException {/*

        try {
            se.verify().verifyEquals("Login Page is displayed", getVendorsTab().isDisplayed(), true, true, test);
            se.element().Click(getVendorsTab());
            se.element().Click(getNewVendorBtn());
            se.element().Click(getVendorTypeDD());
            se.element().Click(getVendorTypeValue());
            se.element().enterText(getCompanyName(), row.get("CompanyName"));
            se.element().enterText(getAddress(), row.get("Address"));
            se.element().enterText(getZIP(), row.get("ZIP"));
            se.element().Click(getPage());
            se.element().Click(getVendorSearch());
            Thread.sleep(3000);
            se.element().Click(getServicetype());
            Thread.sleep(3000);
            se.element().enterText(getFirstName(), row.get("FirstName"));
            se.element().enterText(getLastName(), row.get("LastName"));
            se.element().enterText(getPhone(), row.get("Phone"));
            se.element().enterText(getEmail(), row.get("Email"));
            se.element().Click(getPage());
            se.element().Click(getApproveBtn());
            se.element().enterText(getNotes(), row.get("Notes"));
            se.element().Click(getPage());
            se.element().Click(get_approve());
            Thread.sleep(2000);
            se.element().Click(getExitBtn());
            Thread.sleep(3000);
        } catch (Exception e) {
            se.verify().verifyEquals("Issue with create vendor", true, false, true, test);
        }
    */}
}
