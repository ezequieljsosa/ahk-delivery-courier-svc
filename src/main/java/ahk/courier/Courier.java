package ahk.courier;

/** status: AVAILABLE o BUSY. */
public record Courier(String id, String name, String status, double lat, double lon) {}
