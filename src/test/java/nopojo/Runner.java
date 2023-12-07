package nopojo;

public class Runner {

    PetstoreHomeWorkTest petstoreHomeWorkTest = new PetstoreHomeWorkTest();

    public void runTests(){
        petstoreHomeWorkTest.aopenTest();
        petstoreHomeWorkTest.createPetTest();
        petstoreHomeWorkTest.findPetByIdTest();
    }


    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.runTests();
    }

}
