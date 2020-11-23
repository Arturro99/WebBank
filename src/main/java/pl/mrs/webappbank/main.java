package pl.mrs.webappbank;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.DataType;
import pl.mrs.webappbank.model.Client;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.repositories.ClientRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main( String[] args )
    {
//        AccountManager accountManager = new AccountManager();
//        accountManager.registerCommonAccount("94121291829", "Arek","Remplewicz",21);
//        accountManager.registerCurrencyAccount("94121291829", "Arek","Remplewicz",21, Currency.USD);
//        accountManager.registerCommonAccount("01129215812", "Jacke","Bakiewicz",30);
//        accountManager.registerCommonAccount("99121292812", "Wojtek","Cosiek",11);
//        System.out.println(accountManager.getInfo(DataType.ACCOUNTS));
//        System.out.println(accountManager.getInfo(DataType.CLIENTS));
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Podaj nrkonta wysylajacego: ");
//        String account1 = scanner.nextLine();
//        System.out.println("Podaj nrkonta odbierajacego: ");
//        String account2 = scanner.nextLine();
//        System.out.println("Podaj kwote: ");
//        double amount = Double.parseDouble(scanner.nextLine());
//        accountManager.payInto(account1,amount*10);
//        try {
//            accountManager.transferMoney(account1,account2,amount);
//        } catch (NonexistentAccountException | NotEnoughMoneyException e) {
//            e.printStackTrace();
//        }
//        System.out.println(accountManager.getInfo(DataType.ACCOUNTS));
    }
}
