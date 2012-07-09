package org.sagebionetworks.metrics.client.model;

import java.io.Serializable;
import java.util.Collection;

public class SynapseProject implements Serializable {
    String id;
    String name;
    Collection<String> contributors;
    int score;

    private static int COUNTER = 0;
    
    public SynapseProject() {
        this.id = "synFake" + Integer.toString(COUNTER++);
    }
    
    public SynapseProject(String id, String name,
            Collection<String> contributors, int score) {
        this.id = id;
        this.name = name;
        this.contributors = contributors;
        this.score = score;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Collection<String> getContributors() {
        return contributors;
    }
    public void setContributors(Collection<String> contributors) {
        this.contributors = contributors;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    
    public String contributorsAsString() {
        StringBuilder sb = new StringBuilder();
        String newline = "";

        for (String name : contributors) {
            sb.append(newline).append(name);
            newline = "\n";
        }

        return sb.toString();
    }
}