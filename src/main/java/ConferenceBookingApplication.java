import org.conference.booking.Models.Slot;
import org.conference.booking.Models.User;
import org.conference.booking.Services.BookingService;
import org.conference.booking.Services.ConferenceBuildingService;
import org.conference.booking.Services.SearchService;
import org.conference.booking.constants.BookingConferenceConstant;
import org.conference.booking.interfaces.BookingInterface;
import org.conference.booking.interfaces.ConferenceBuildingInterface;
import org.conference.booking.interfaces.SearchInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.UUID;

public class ConferenceBookingApplication {

    private final BookingInterface bookingService = new BookingService();
    private final SearchInterface searchService = new SearchService();
    private final ConferenceBuildingInterface conferenceBuildingService = new ConferenceBuildingService();

    private static final Map<String, Integer> commandToLengthMap = Map.of(
            BookingConferenceConstant.ADD_BUILDING, 3,
            BookingConferenceConstant.ADD_FLOOR, 4,
            BookingConferenceConstant.ADD_CONFROOM, 5,
            BookingConferenceConstant.BOOK, 5,
            BookingConferenceConstant.CANCEL, 6,
            BookingConferenceConstant.LIST_BOOKING, 4,
            BookingConferenceConstant.SEARCH, 4);

    public static void main(String[] args) throws Exception {
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String commandString = reader.readLine();

            ConferenceBookingApplication conferenceBookingApplication = new ConferenceBookingApplication();

            if (commandString == null || commandString.length() < 3) {
                System.out.println("Please enter correct command");
            } else {
                String[] commandArray = commandString.split(" ");
                String command1 = commandArray[0].toUpperCase();
                String command2 = (command1 + " " + commandArray[1]).toUpperCase();
                if (commandToLengthMap.containsKey(command1) && commandToLengthMap.get(command1) == commandArray.length) {
                    User user = User.builder().userId(UUID.randomUUID().toString()).build();
                    String[] slotArray = commandArray[1].split(":");

                    switch (command1) {

                        case BookingConferenceConstant.BOOK:
                            conferenceBookingApplication.bookingService.book(user, commandArray[2], commandArray[3], commandArray[4],
                                    Slot.builder().start(Integer.parseInt(slotArray[0])).end(Integer.parseInt(slotArray[1])).build());
                            break;
                        case BookingConferenceConstant.CANCEL:
                            user.setUserId(commandArray[1]);
                            slotArray = commandArray[2].split(":");
                            Slot slot = Slot.builder().start(Integer.parseInt(slotArray[0])).end(Integer.parseInt(slotArray[1])).build();

                            conferenceBookingApplication.bookingService.cancel(user, commandArray[3], commandArray[4], commandArray[5],slot);
                            break;
                        case BookingConferenceConstant.SEARCH:
                            conferenceBookingApplication.searchService.searchAvailableRoom(commandArray[2], commandArray[3],
                                    Slot.builder().start(Integer.parseInt(slotArray[0])).end(Integer.parseInt(slotArray[1])).build());
                            break;
                        default:
                            System.out.println("something went wrong");
                            break;

                    }

                } else if (commandToLengthMap.containsKey(command2) && commandToLengthMap.get(command2) == commandArray.length) {
                    switch (command2) {
                        case BookingConferenceConstant.ADD_BUILDING:
                            conferenceBookingApplication.conferenceBuildingService.addBuilding(commandArray[2]);
                            break;
                        case BookingConferenceConstant.ADD_FLOOR:
                            conferenceBookingApplication.conferenceBuildingService.addFloor(commandArray[2], commandArray[3]);
                            break;
                        case BookingConferenceConstant.ADD_CONFROOM:
                            conferenceBookingApplication.conferenceBuildingService.addRoom(commandArray[2], commandArray[3], commandArray[4]);
                            break;
                        case BookingConferenceConstant.LIST_BOOKING:
                            conferenceBookingApplication.bookingService.getBookingList(commandArray[2], commandArray[3]);
                            break;
                        default:
                            System.out.println("something went wrong");
                            break;
                    }
                } else {
                    throw new Exception("Invalid command");
                }


            }

        }

    }
}
