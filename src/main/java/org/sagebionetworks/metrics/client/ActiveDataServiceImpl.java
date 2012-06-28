package org.sagebionetworks.metrics.client;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import org.sagebionetworks.metrics.client.model.SynapseProject;
import org.sagebionetworks.metrics.client.model.SynapseUser;
import org.sagebionetworks.metrics.server.CrowdLogParser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ActiveDataServiceImpl extends RemoteServiceServlet implements
        ActiveDataService {

    private static final String CROWD_LOG_DIRECTORY_PATH = "../../../data/crowdlogs/";
    private static final String PROJECT_LOG_FILE_PATH = "../../../data/activityDump.txt";

    private Collection<SynapseUser> activeUsers = new LinkedList<SynapseUser>();
    
    public ActiveDataServiceImpl() {
        try {
            runLogParser(100);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Collection<SynapseUser> getActiveUsers() {
        return activeUsers;
    }

    public Collection<SynapseProject> getActiveProjects() {
        return null;
    }

    private void runLogParser(int window) throws IOException, ParseException {
        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime()-window*24*60*60*1000L);

        Map<String, Collection<Long>> userDays = CrowdLogParser.parseAuthenticationEvents(new File(CROWD_LOG_DIRECTORY_PATH), startDate.getTime(), endDate.getTime());
        Map<String, Collection<String>> frequentOutsideUsers = CrowdLogParser.getFrequentOutsideUsers(userDays);

        for (String domain : frequentOutsideUsers.keySet()) {
            for(String user : frequentOutsideUsers.get(domain)) {
                activeUsers.add(createUser(user, userDays.get(user)));
            }
        }
    }

    private SynapseUser createUser(String userName, Collection<Long> userDays) {
        SynapseUser user = new SynapseUser();
        String[] nameAndDomain = userName.split("@");

        user.setName(nameAndDomain[0]);
        user.setDomain(nameAndDomain[1]);
        user.setLastLogin(new Date(userDays.iterator().next()));

        return user;
    }

}
