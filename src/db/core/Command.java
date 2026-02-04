package db.core;

public class Command {

    public CommandType type;
    public Integer key;      // nullable
    public String rawValue;  // nullable
    public Long ttl;         // nullable

    public Command(CommandType type, Integer key, String rawValue, Long ttl) {
        this.type = type;
        this.key = key;
        this.rawValue = rawValue;
        this.ttl = ttl;
    }
}

