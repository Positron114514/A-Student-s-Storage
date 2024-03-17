import java.time.LocalDate;

public class Employee{
    private String name;
    private double salary;
    private String job;
    private String bankCodeNumber;
    private LocalDate hireDate;

    public Employee(String name, String job, double salary, int year, int month, int day){
        this.hireDate = LocalDate.of(year, month, day);
        this.name = name;
        this.job = job;
        this.salary = salary;
    }

    public String getName(){
        return name;
    }

    public String getJob(){
        return job;
    }

    public double getSalary(){
        return salary;
    }

    public String getBankCodeNumber(){
        return bankCodeNumber;
    }

    public LocalDate getHireDate(){
        return hireDate;
    }

    public void raiseSalary(double percent){
        double raise = salary * percent;
        salary += raise;
    }

    public void changeJob(String newJob, double newSalary){
        job = newJob;
        salary = newSalary;
    }

    public void changeBankCodeNumber(String newBankCodeNumber){
        bankCodeNumber = newBankCodeNumber;
    }
}