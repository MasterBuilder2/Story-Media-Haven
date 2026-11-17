public class SubmissionPortal {
    private static final SubmissionPortal SINGLETON = new SubmissionPortal();

    private SubmissionPortal() {}

    public static SubmissionPortal getSingleton() {
        return SINGLETON;
    }

    public void submitRequest(Publisher publisher, String title, String creator,
                              Date releaseDate, String synopsis) {
        // Later: verify publisher, validate metadata, check for duplicates,
        // then add to the admin review queue.
    }
}
