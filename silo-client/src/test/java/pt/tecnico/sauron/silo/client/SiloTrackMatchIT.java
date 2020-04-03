package pt.tecnico.sauron.silo.client;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class SiloTrackMatchIT extends BaseIT{

    // static members
    private static String HOST = "localhost";
    private static String PORT = "8080";
    private static SiloFrontend frontend = new SiloFrontend(HOST, PORT);

    private static String PERSON = "person";
	private static String PERSON_ID_VALID = "1";
	private static String PERSON_ID_INVALID = "1111a";
	private static String PERSON_PART_ID = "1*";
	private static String CAR = "car";
	private static String CAR_ID_VALID = "20SD21";
	private static String CAR_PART_BEGGINING = "*21";
	private static String CAR_PART_MIDDLE = "2*1";
	private static String CAR_PART_END = "20*";
	private static String CAR_ID_INVALID = "202122";
	private static String CAM = "camName";
    private static double CAM_LAT = 1.232;
    private static double CAM_LONG = -5.343;



    	// one-time initialization and clean-up
	@BeforeAll
	public static void oneTimeSetUp() throws ReportException, InvalidTypeException {
		List<ObservationObject> obsList = new ArrayList<>();
		obsList.add(new ObservationObject(CAR, CAR_ID_VALID,CAM));
		obsList.add(new ObservationObject(CAR, "20SZ21",CAM));


		frontend.camJoin(CAM , CAM_LAT, CAM_LONG);
		frontend.report(obsList);
    }

	@AfterAll
	public static void oneTimeTearDown() {
        frontend.ctrlClear();
		frontend.exit();
	}

	// initialization and clean-up for each test

	@BeforeEach
	public void setUp() {

	}

	@AfterEach
	public void tearDown() {

	}


    @Test
    public void successCompleteId() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, CAR_ID_VALID);
	        Assert.assertEquals(CAR, obs.get(0).getType());
	        Assert.assertEquals(CAR_ID_VALID, obs.get(0).getId());
	        Assert.assertEquals(CAM, obs.get(0).getCamName());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }


    @Test
    public void successBeginningPartId() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, CAR_PART_BEGGINING);
	        Assert.assertEquals(CAR, obs.get(0).getType());
	        Assert.assertEquals(CAR_ID_VALID, obs.get(0).getId());
	        Assert.assertEquals(CAM, obs.get(0).getCamName());

	        Assert.assertEquals(CAR, obs.get(1).getType());
	        Assert.assertEquals("20SZ21", obs.get(1).getId());
	        Assert.assertEquals(CAM, obs.get(1).getCamName());


        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    public void successMiddlePartId() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, "2*S*1");
	        Assert.assertEquals(CAR, obs.get(0).getType());
	        Assert.assertEquals(CAR_ID_VALID, obs.get(0).getId());
	        Assert.assertEquals(CAM, obs.get(0).getCamName());

	        Assert.assertEquals(CAR, obs.get(1).getType());
	        Assert.assertEquals("20SZ21", obs.get(1).getId());
	        Assert.assertEquals(CAM, obs.get(1).getCamName());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    public void successEndPartId() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, CAR_PART_END);
	        Assert.assertEquals(CAR, obs.get(0).getType());
	        Assert.assertEquals(CAR_ID_VALID, obs.get(0).getId());
	        Assert.assertEquals(CAM, obs.get(0).getCamName());

	        Assert.assertEquals(CAR, obs.get(1).getType());
	        Assert.assertEquals("20SZ21", obs.get(1).getId());
	        Assert.assertEquals(CAM, obs.get(1).getCamName());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }


    @Test
    public void successMultipleAsteriskId() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, CAR_PART_END);
	        Assert.assertEquals(CAR, obs.get(0).getType());
	        Assert.assertEquals(CAR_ID_VALID, obs.get(0).getId());
	        Assert.assertEquals(CAM, obs.get(0).getCamName());

	        Assert.assertEquals(CAR, obs.get(1).getType());
	        Assert.assertEquals("20SZ21", obs.get(1).getId());
	        Assert.assertEquals(CAM, obs.get(1).getCamName());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }

    @Test
    public void successNoObservation() {
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, "40SA21");
	        Assertions.assertTrue(obs.isEmpty());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
    }

	@Test
	public void emptyIdTest(){
	    try {
	        List<ObservationObject> obs = frontend.trackMatch(CAR, "");
	        Assertions.assertTrue(obs.isEmpty());

        } catch (InvalidTypeException | NoObservationsFoundException e) {
            fail("Should not have thrown any exception.");
        }
	}

    @Test
	public void invalidTypeTest(){
		Assertions.assertThrows(InvalidTypeException.class, () -> {
			frontend.trackMatch("object", PERSON_ID_VALID);
		});
	}

    @Test
	public void emptyTypeTest(){
		// Should throw invalid type exception
		Assertions.assertThrows(InvalidTypeException.class, () -> {
			frontend.trackMatch("", PERSON_ID_VALID);
		});
	}


}
