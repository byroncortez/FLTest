package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "test.freelancer.com.fltest.dao");
        
        Entity program = schema.addEntity("Program");
        program.addIdProperty();
        program.addStringProperty("name");
        program.addStringProperty("start_time");
        program.addStringProperty("end_time");
        program.addStringProperty("channel");
        program.addStringProperty("rating");
        
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
