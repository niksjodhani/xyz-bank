package com.bank.testsuite;

import com.bank.pages.*;
import com.bank.testbase.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.testdata.TestData;

public class BankTest extends TestBase {


    AccountPage accountPage;
    AddCustomerPage addCustomerPage;
    BankManagerLoginPage bankManagerLoginPage;
    CustomerLoginPage customerLoginPage;
    CustomersPage customersPage;
    HomePage homePage;
    OpenAccountPage openAccountPage;


    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        accountPage = new AccountPage();
        addCustomerPage = new AddCustomerPage();
        bankManagerLoginPage = new BankManagerLoginPage();
        customerLoginPage = new CustomerLoginPage();
        customersPage = new CustomersPage();
        homePage = new HomePage();
        openAccountPage = new OpenAccountPage();
    }


    @Test(priority = 1, groups = {"sanity", "regression"})
    public void bankManagerShouldAddCustomerSuccessfully() {
        homePage.clickBankManagerLoginButton();
        bankManagerLoginPage.clickAddCustomerButton();
        addCustomerPage.enterFirstName("Ramesh");
        addCustomerPage.enterLastName("kothari");
        addCustomerPage.enterPostCode("ha97bk");
        addCustomerPage.clickAddCustomerButton();
        String expectedMessage = "Customer added successfully";
        String actualMessage = addCustomerPage.textFromAlert();
        Assert.assertEquals(expectedMessage, actualMessage, "Actual text does not match expected text");
        addCustomerPage.clickOkOnAlert();
    }

    @Test(priority = 2, groups = {"sanity", "smoke", "regression"})
    public void bankManagerShouldOpenAccountSuccessfully() {
        addCustomerPage.clickHomeButton();
        homePage.clickBankManagerLoginButton();
        bankManagerLoginPage.clickOpenAccountButton();
        openAccountPage.selectCustomerJustAddedDropDown("Rameshkothari");
        openAccountPage.selectCurrencyDropDown("Pound");
        openAccountPage.clickProcessButton();
        String expectedMessage = "Account created successfully";
        String actualMessage = openAccountPage.textFromAlert();
        Assert.assertEquals(expectedMessage, actualMessage, "Actual text does not match expected text");
        openAccountPage.clickOkOnAlert();
    }

    @Test(priority = 3, groups = {"smoke", "regression"})
    public void customerShouldLoginAndLogoutSuccessfully() {
        openAccountPage.clickHomeButton();
        homePage.clickCustomerLoginBtnButton();
        customerLoginPage.selectNameFromDropDownMenu("Ramesh kothari");
        customerLoginPage.clickLoginButton();
        String expectedMessage = "logout";
        String actualMessage = customersPage.verifyLogoutButton();
        Assert.assertEquals(expectedMessage, actualMessage, "User has not logged in successfully");
        customersPage.clickLogoutButton();
        String expectedMessage1 = "Your Name";
        String actualMessage1 = customerLoginPage.getYourNameText();
        Assert.assertEquals(expectedMessage1, actualMessage1, "User has not logged out successfully");
    }

    @Test(priority = 4, groups = {"smoke", "regression"})
    public void customerShouldDepositMoneySuccessfully() {
        customerLoginPage.clickHomeButton();
        homePage.clickCustomerLoginBtnButton();
        customerLoginPage.selectNameFromDropDownMenu("Ramesh kothari");
        customerLoginPage.clickLoginButton();
        accountPage.clickDepositButton();
        accountPage.enterDepositAmount("100");
        accountPage.clickSmallDepositButton();
        String expectedMessage = "Deposit Successful";
        String actualMessage = accountPage.verifyDepositSuccessfulText();
        Assert.assertEquals(expectedMessage, actualMessage, "Actual text does not match expected text");
    }

    @Test(priority = 5, groups = {"regression"})
    public void customerShouldWithdrawMoneySuccessfully() throws InterruptedException {
        accountPage.clickHomeButton();
        homePage.clickCustomerLoginBtnButton();
        customerLoginPage.selectNameFromDropDownMenu("Ramesh kothari");
        customerLoginPage.clickLoginButton();
        accountPage.clickWithdrawalButton();
        accountPage.enterWithdrawalAmount("50");
        accountPage.clickSmallWithdrawalButton();
        String expectedMessage = "Transaction successful";
        String actualMessage = accountPage.verifyWithdrawalSuccessfulText();
        Assert.assertEquals(expectedMessage, actualMessage, "Actual text does not match expected text");
    }
}