
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.inject.Singleton;

public class GuiceDemo {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppInjector());
        MyController app = injector.getInstance(MyController.class); // at this moment the data was injected
        System.out.println(app.getDatabaseData());
        app.setIntController(5);
        Main2 main2 = new Main2();
        main2.doesThisWork();
    }
}

class Main2 {
    public void doesThisWork() {
        Injector injector = Guice.createInjector(new AppInjector());
        MyController app = injector.getInstance(MyController.class); // at this moment the data was injected
        System.out.println(app.getDatabaseData());
        System.out.println("Int in guice " + app.getIntController());
    }
}

class MyController {
    @Inject //inject directly the property
    private Database db;
    //  @Inject //injcting by param with setter methode. Or you can inject the data by contructor injection
//  public void readDatabaseData(Database db) {
//    this.db = db;
//  }
    public String getDatabaseData() {
        return db.getData();
    }

    public void setIntController(int i){
        db.setInt(i);
    }
    public int getIntController() {
        return db.getInt();
    }
}

// configure which database should be used!
class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(Database.class).to(InMemoryDatabase.class);
    }
}

interface Database {

    public String getData();

    public void setInt(int i);

    public int getInt();
}

@Singleton
class InMemoryDatabase implements Database {
    static public int i;
    @Override
    public String getData() {
        return "data from InMemoryDatabase";
    }

    @Override
    public void setInt(int i) {
        this.i = i;
    }

    @Override
    public int getInt() {
        return this.i;
    }
}

@Singleton
class MySqlDatabase implements Database {
    @Override
    public String getData() {
        return "data from MySqlDatabase";
    }

    @Override
    public void setInt(int i) {

    }

    @Override
    public int getInt() {
        return 0;
    }
}