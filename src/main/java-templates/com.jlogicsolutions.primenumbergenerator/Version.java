package com.jlogicsolutions.primenumbergenerator;

public final class Version {

    private final String version = "${project.version}";
    private final String groupid = "${project.groupId}";
    private final String artifactid = "${project.artifactId}";
    private final String svn = "${project.scm.developerConnection}";
    private final String svnBranch = "${scmBranch}";
    private final String revision = "${buildNumber}";
    private final String environment = System.getenv("ENVIRONMENT");

    public String getVersion() {
        return version;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getArtifactid() {
        return artifactid;
    }

    public String getSvn() {
        return svn;
    }

    public String getSvnBranch() {
        return svnBranch;
    }

    public String getRevision() {
        return revision;
    }

    public String getEnvironment() {
        return environment;
    }
}